package com.example.takeloanapp.service;

import com.example.takeloanapp.calculator.ArrearsCalculator;
import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.LoansInstalmentsController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoanAmountAndDataDTO;
import com.example.takeloanapp.domain.dto.LoanCashFlowDto;
import com.example.takeloanapp.mapper.LoansMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
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
    @Autowired
    private ArrearsCalculator arrearsCalculator;
    @Autowired
    private LoansMapper loansMapper;

    public void checkArrearsOnLoan () throws LoanNotFoundException {
        LOGGER.info("STARTING checkArrearsOnLoan.");
        List<Loans> allLoans = loanService.getAllLoans().stream()
                .filter(loans -> loans.isClosed() == false)
                .filter(loans -> loans.getDayOfInstalmentRepayment() == LocalDate.now().getDayOfMonth())
                .collect(Collectors.toList());
        if (!allLoans.isEmpty()){
            LOGGER.info("Let go and do, there is/are: " + allLoans.size() + " loan/s to check.");
            for (Loans loanToCheck : allLoans) {
                LOGGER.info("Working with LOAN ID: " + loanToCheck.getId()) ;
                BigDecimal dueAmount = checkInstallmentsDue(loanToCheck);
                LoanAmountAndDataDTO repaidAmountAndDate = sumAmountOfPayedInstallments(loansInstalmentsController.loanRepaymentByLoanId(loanToCheck.getId()));
                if (dueAmount.compareTo(repaidAmountAndDate.getBigDecimalAmount()) == -1){
                    LOGGER.info("There is overpayment amount." + repaidAmountAndDate.getBigDecimalAmount().subtract(dueAmount).setScale(2, RoundingMode.HALF_UP));
                }
                if (dueAmount.compareTo(repaidAmountAndDate.getBigDecimalAmount()) == 0){
                    LOGGER.info("There are not outstandings amount.");
                }
                if (dueAmount.compareTo(repaidAmountAndDate.getBigDecimalAmount()) == 1){
                    LOGGER.info("There is outstandings amount. The outstanding amount is - " + dueAmount.subtract(repaidAmountAndDate.getBigDecimalAmount()) + " PLN.");
                    BigDecimal outstandingAmountOnLoan = arrearsCalculator.calculatingArrearsAmount(calculatingNumberOfArrearsDays(repaidAmountAndDate), loanToCheck.getLoanAmount());
                    loanToCheck.setHasArrears(true);
                    LOGGER.info("Loan has arrears, change flag HasArrears to true.");
                    loanToCheck.setPenaltyInterestAmount(outstandingAmountOnLoan);
                    LOGGER.info("Loan has arrears, setPenaltyInterestAmount: " + outstandingAmountOnLoan);
                    loansController.updateLoan(loansMapper.matToLoanDto(loanToCheck));
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

    public LoanAmountAndDataDTO sumAmountOfPayedInstallments(List<LoanCashFlowDto> installmentsList){
        LOGGER.info("Start - sumAmountOfPayedInstallments.");
            BigDecimal result = BigDecimal.ZERO;
            LocalDateTime lastRepayment = LocalDateTime.MIN;
        if (!installmentsList.isEmpty()){
            for (LoanCashFlowDto n: installmentsList) {
                if (n.isAnInstallment()){
                    result = result.add(n.getRepaymentAmount());
                    if (n.getTransactionTimeStamp().isAfter(lastRepayment)) lastRepayment = n.getTransactionTimeStamp();
                }
            }
            LOGGER.info("SUM of all done installments is: " + result);
            LOGGER.info("Timestamp of last repayment transaction is: " + lastRepayment);
            return new LoanAmountAndDataDTO(result, lastRepayment);
        }
        return new LoanAmountAndDataDTO(result, lastRepayment);
    }

    public int calculatingNumberOfArrearsDays(LoanAmountAndDataDTO amountAndDate){
        LOGGER.info("Starting calculating numbers of days");
        Period diff = Period.between(amountAndDate.getLocalDateTime().toLocalDate(), LocalDate.now());
        int years = diff.getYears();
        int months = diff.getMonths();
        int days = diff.getDays();
        int result =  years * 365 + months * 30 + days ;
        LOGGER.info("Result is: \t" + result + " days.");
        return result;
    }
}
