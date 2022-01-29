package com.example.takeloanapp.service;

import com.example.takeloanapp.calculator.ArrearsCalculator;
import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.LoansInstalmentsController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoansDto;
import com.example.takeloanapp.mail.MailContentTemplates;
import com.example.takeloanapp.mapper.LoansMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanClosingProcessServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanClosingProcessServices.class);

    @Autowired
    private LoansController loansController;
    @Autowired
    private LoanArrearsService loanArrearsService;
    @Autowired
    private LoansMapper loansMapper;
    @Autowired
    private LoansInstalmentsController loansInstalmentsController;
    @Autowired
    private MailContentTemplates mailContentTemplates;
    @Autowired
    private ArrearsCalculator arrearsCalculator;

    public void checkLoansToBeClosed() throws LoanNotFoundException {
        LOGGER.info("Starting checking loans to be closed......");

        List<LoansDto> loansDtoList = loansController.getAllLoans().stream()
                .filter(LoansDto::isActive)
                .filter(record -> record.getEndDate().isEqual(LocalDate.now()))
                .collect(Collectors.toList());
        if(loansDtoList.isEmpty()){
            LOGGER.info("They are not loans to be closed today.");
        } else {
            for (LoansDto checkedLoan : loansDtoList) {
                Loans tmpLoan = loansMapper.mapToLoan(checkedLoan);
                BigDecimal dueAmount = arrearsCalculator.checkInstallmentsDue(tmpLoan);
                BigDecimal sumOfAllPayments = arrearsCalculator.sumAmountOfPayedInstallments(loansInstalmentsController.loanRepaymentByLoanId(tmpLoan.getId())).getBigDecimalAmount();
                BigDecimal sumofAllPayedArrears = arrearsCalculator.sumOfArrears(loansInstalmentsController.loanRepaymentByLoanId(tmpLoan.getId()));
                BigDecimal sumOfAllPayedInstallments = sumOfAllPayments.subtract(sumofAllPayedArrears).setScale(2, RoundingMode.HALF_UP);
                if (sumOfAllPayedInstallments.compareTo(dueAmount) == 0 && !tmpLoan.isHasArrears()) {
                    LOGGER.info("Sum of all done payment is equal to due amount");
                    doLoanClose(tmpLoan);
                    mailContentTemplates.sendCustomerMailAboutClosedLoan(tmpLoan.getCustomer().getMailAddress());
                }
                if (sumOfAllPayedInstallments.compareTo(dueAmount) == -1) {
                    LOGGER.info("sum ar all done payment is smaller that due amount. we can not close loan id: " + tmpLoan.getId());
                    String loanInfo = loanInformationToString(tmpLoan, dueAmount, sumOfAllPayedInstallments);
                    mailContentTemplates.sentInformationAboutOutstandingsAmountOnClosedLoan(loanInfo);
                }
                if (sumOfAllPayedInstallments.compareTo(dueAmount) == 1) {
                    LOGGER.info("sum of all done payment is higher that due amount.");
                    String loanInfo = loanInformationToString(tmpLoan, dueAmount, sumOfAllPayedInstallments);
                    mailContentTemplates.sentInformationAboutOverpaymentOnClosedLoan(loanInfo);
                    mailContentTemplates.sendCustomerMailAboutClosedLoan(tmpLoan.getCustomer().getMailAddress());
                }
            }
        }
    }

    public void doLoanClose(Loans loansToBeClose) throws LoanNotFoundException {
        LOGGER.info("Starting closing loan ID: " + loansToBeClose.getId());
        loansToBeClose.setClosed(true);
        loansController.updateLoan(loansMapper.matToLoanDto(loansToBeClose));
        LOGGER.info("Flag CLOSED has been set for loan ID: " + loansToBeClose.getId());
    }

    public String loanInformationToString(Loans loans, BigDecimal dueAmount, BigDecimal allPayedInstalments){
        String loanInfo = "loan id: " + loans.getId() +
                "\n\t- due amount: " + dueAmount +
                "\n\t- sum of all payments: " + allPayedInstalments + ".";
        if(loans.isHasArrears()) loanInfo = loanInfo + "\n\t- arrears sum is: " + loans.getPenaltyInterestAmount();
        return loanInfo;
    }
}