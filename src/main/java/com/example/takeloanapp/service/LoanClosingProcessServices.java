package com.example.takeloanapp.service;

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
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanClosingProcessServices {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanClosingProcessServices.class);

    @Autowired
    LoansController loansController;
    @Autowired
    LoanArrearsService loanArrearsService;
    @Autowired
    LoansMapper loansMapper;
    @Autowired
    private LoansInstalmentsController loansInstalmentsController;
    @Autowired
    private MailContentTemplates mailContentTemplates;

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
                BigDecimal dueAmount = loanArrearsService.checkInstallmentsDue(tmpLoan);
                BigDecimal sumOfAllPayments = loanArrearsService.sumAmountOfPayedInstallments(loansInstalmentsController.loanRepaymentByLoanId(tmpLoan.getId())).getBigDecimalAmount();
                if (sumOfAllPayments.compareTo(dueAmount) == 0 && !tmpLoan.isHasArrears()) {
                    LOGGER.info("sum ar all done payment is equal to due amount");
                    doLoanClose(tmpLoan);
                    mailContentTemplates.sendCustomerMailAboutClosedLoan(tmpLoan.getCustomer().getMailAddress());
                }
                if (sumOfAllPayments.compareTo(dueAmount) == -1) {
                    LOGGER.info("sum ar all done payment is smaller that due amount. we can not close loan id: " + tmpLoan.getId());
                }
                if (sumOfAllPayments.compareTo(dueAmount) == 1) {
                    LOGGER.info("sum of all done payment is higher that due amount.");
                    String loanInfo = "loan id: " + tmpLoan.getId() + ", due amount: " + dueAmount + ", sum of all payments: " + sumOfAllPayments;
                    mailContentTemplates.sentInformationAboutOverpaymentOnClosedLoan(loanInfo);
                }
            }
        }
    }

    public void doLoanClose(Loans loansToBeclose) throws LoanNotFoundException {
        LOGGER.info("Starting closing loan ID: " + loansToBeclose.getId());
        loansToBeclose.setClosed(true);
        loansController.updateLoan(loansMapper.matToLoanDto(loansToBeclose));
        LOGGER.info("Flag CLOSED has been set for loan ID: " + loansToBeclose.getId());
    }
}