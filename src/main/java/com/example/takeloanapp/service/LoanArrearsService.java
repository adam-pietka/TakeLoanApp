package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.LoansInstalmentsController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoanCashFlowDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanArrearsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanArrearsService.class);

    @Autowired
    private LoansController loansController;
    @Autowired
    private LoanService loanService;
    @Autowired
    private LoansInstalmentsController loansInstalmentsController;

    public void checkArrearsOnLoan () throws LoanNotFoundException {
        LOGGER.info("STARTING checkArrearsOnLoan.");
        List<Loans> allLoans = loanService.getAllLoans().stream()
                .filter(loans -> loans.isClosed() == false)
                .filter(loans -> loans.getDayOfInstalmentRepayment() == LocalDate.now().getDayOfMonth())
                .collect(Collectors.toList());
        if (!allLoans.isEmpty()){
            LOGGER.info("Let go and do, there is/are: " + allLoans.size() + " loan/s to check.");
            for (Loans i : allLoans) {
                LOGGER.info("Working with LOAN ID: " + i.getId()) ;
                BigDecimal dueAmount = checkInstallmentsDue(i);
                BigDecimal repaidAmount = sumAmountOfPayedInstallments(loansInstalmentsController.loanRepaymentByLoanId(i.getId()));
                if (dueAmount.compareTo(repaidAmount) == -1){
                    LOGGER.info("They is overpayment amount.");
                }
                if (dueAmount.compareTo(repaidAmount) == 0){
                    LOGGER.info("They are not outstandings amount.");
                }
                if (dueAmount.compareTo(repaidAmount) == 1){
                    LOGGER.info("They is outstandings amount. The outstanding amount is - " + dueAmount.subtract(repaidAmount) + " PLN.");
                }
            }
        } else LOGGER.info("No loans to check today.");
    }

    public BigDecimal checkInstallmentsDue(Loans loans) throws LoanNotFoundException {

        int repaidInstalmentNumber = calculateNumbersOfMonthsFromStart( loans.getStartDate());
        LOGGER.info("Result 'repaidInstalmentNumber' is: " + repaidInstalmentNumber);
        if (repaidInstalmentNumber > 0) {
            BigDecimal installment ;
            installment = loans.getNextInstalmentInterestRepayment().add(loans.getNextInstalmentCapitalRepayment());
            LOGGER.info( "For loan ID: " +loans.getId()+ ", installment is equal:\t" + installment + " PLN.");
            BigDecimal dueAmount;
            dueAmount = installment.multiply(BigDecimal.valueOf(repaidInstalmentNumber)).setScale(2, RoundingMode.HALF_UP) ;
            LOGGER.info("In total, it should be paid off: \t" + dueAmount + " amount for capital and interests.");
            return dueAmount;
        }
        LOGGER.info("Loan ID: " + + loans.getId() + " is too young. There shouldn't be payed any instalments.");
        return BigDecimal.ZERO;
    }

    public int calculateNumbersOfMonthsFromStart(LocalDate startDate){
        LOGGER.info("Starting calculating how many months/instalments was payed");
        Period diff = Period.between(startDate, LocalDate.now());
        int years = diff.getYears();
        int months = diff.getMonths();
        return years * 12 + months;
    }

    public BigDecimal sumAmountOfPayedInstallments(List<LoanCashFlowDto> installmentsList){
        LOGGER.info("Start - sumAmountOfPayedInstallments.");
        if (!installmentsList.isEmpty()){
            BigDecimal result = BigDecimal.ZERO;
            for (LoanCashFlowDto n: installmentsList) {
                if (n.isAnInstallment()) result = result.add(n.getRepaymentAmount());
            }
            LOGGER.info("SUM of all done installments is: " + result);
            return result;
        }
        return BigDecimal.ZERO;
    }

    public int calculatingNumberOfArrearsDays(){

        return 0;
    }
}
