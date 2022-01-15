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
import java.time.LocalTime;
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
                .filter(loans -> !loans.isClosed())
                .filter(loans -> loans.getDayOfInstalmentRepayment() == LocalDate.now().getDayOfMonth())
                .collect(Collectors.toList());
        if (!allLoans.isEmpty()){
            LOGGER.info("Let go and do, there is/are: " + allLoans.size() + " loan/s to check.");
            for (Loans loanToCheck : allLoans) {
                LOGGER.info("***************\tWorking with LOAN ID: " + loanToCheck.getId() + "\t******************************");
                BigDecimal dueAmount = checkInstallmentsDue(loanToCheck);
                List<LoanCashFlowDto> allPaymentsForLoanList = loansInstalmentsController.loanRepaymentByLoanId(loanToCheck.getId());
                LoanAmountAndDataDTO repaidAmountAndDate = sumAmountOfPayedInstallments(allPaymentsForLoanList);

                if (dueAmount.compareTo(repaidAmountAndDate.getBigDecimalAmount()) == -1){
                    LOGGER.info("Payed amount: " +  repaidAmountAndDate.getBigDecimalAmount().subtract(dueAmount).setScale(2, RoundingMode.HALF_UP) + " in higher that due amount, checking outstanding amount.");
                    // tutaj obługa dla nadpłat wersus wpłaty które pokrywały kary
                    BigDecimal instalmentAndOutstandingAmount = loanToCheck.getPenaltyInterestAmount().add(repaidAmountAndDate.getBigDecimalAmount());
                    if (loanToCheck.isHasArrears() && repaidAmountAndDate.getBigDecimalAmount().compareTo(instalmentAndOutstandingAmount) <= 0 ){
                        loanToCheck.setPenaltyInterestAmount(BigDecimal.ZERO);
                        loanToCheck.setHasArrears(false);
                        loansController.updateLoan(loansMapper.matToLoanDto(loanToCheck));
                        LOGGER.info("Loan has arrears, change flag HasArrears to FALSE.");
                        LOGGER.info("The penalty interest has been paid and the flag 'HasArrears' has been set to FALSE.");
                    } else {
                        LOGGER.info("Loan has overpayment amount.");
                    }
                }
                if (dueAmount.compareTo(repaidAmountAndDate.getBigDecimalAmount()) == 0){
                    LOGGER.info("There are not outstandings amount or overpayments.");
//                    allPaymentsForLoanList.stream()
//                            .filter(i-> i.getPostingsTimeStamp() == null)
//                            .collect(Collectors.toList());
                    for (LoanCashFlowDto payment: allPaymentsForLoanList) {
                        if(payment.getPostingsTimeStamp() == null){ // ********************************************************* to remove / improve

                        payment.setPostingsTimeStamp(LocalDateTime.now());
                        payment.setPostingsAsInstalment(payment.getRepaymentAmount());
                        loansInstalmentsController.updatePayment(payment);
                        }
                    }
                }
                if (dueAmount.compareTo(repaidAmountAndDate.getBigDecimalAmount()) == 1){
                    BigDecimal arrearsAmoutn = dueAmount.subtract(repaidAmountAndDate.getBigDecimalAmount());
                    LOGGER.info("There is outstandings amount. The amount of arrears is - " + arrearsAmoutn + " PLN.");
                    loanToCheck.setHasArrears(true);
                    BigDecimal outstandingAmountOnLoan = arrearsCalculator.calculatingArrearsAmount(calculatingNumberOfArrearsDays(repaidAmountAndDate), loanToCheck.getLoanAmount());
                    BigDecimal existingArrears = BigDecimal.ZERO;
                    if (loanToCheck.getPenaltyInterestAmount() != null) {
                        existingArrears = loanToCheck.getPenaltyInterestAmount();
                    }
                    loanToCheck.setPenaltyInterestAmount(outstandingAmountOnLoan.add(existingArrears));
                    LOGGER.info("Loan has arrears, change flag HasArrears to true.");
                    LOGGER.info("Loan has arrears, setPenaltyInterestAmount: " + outstandingAmountOnLoan);
                    loansController.updateLoan(loansMapper.matToLoanDto(loanToCheck));

//                    allPaymentsForLoanList.stream()
//                            .filter(i -> i.getPostingsTimeStamp()==null)
//                            .collect(Collectors.toList());

                    for (LoanCashFlowDto payment: allPaymentsForLoanList) {

                    if(payment.getPostingsTimeStamp() == null){ // ********************************************************* to remove / improve
                        payment.setPostingsTimeStamp(LocalDateTime.now());
                        BigDecimal tmpArrears = loansController.getLoanById(payment.getLoansId()).getPenaltyInterestAmount();
                        if (payment.getRepaymentAmount().compareTo(tmpArrears) >= 0){
                            payment.setPostingsAsArrears(tmpArrears);
                            payment.setPostingsAsInstalment(payment.getRepaymentAmount().subtract(tmpArrears));
                            payment.setPostingsTimeStamp(LocalDateTime.now());
                            loanToCheck.setPenaltyInterestAmount(BigDecimal.ZERO);
                        } else {
                            payment.setPostingsAsArrears(payment.getRepaymentAmount());
                            payment.setPostingsTimeStamp(LocalDateTime.now());
                            loanToCheck.setPenaltyInterestAmount(tmpArrears.subtract(payment.getRepaymentAmount()));
                        }
                        loansInstalmentsController.updatePayment(payment);
                        loansController.updateLoan(loansMapper.matToLoanDto(loanToCheck));
                    } }
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
        if (!installmentsList.isEmpty()) {
            try {
                lastRepayment = LocalDateTime.of(loansController.getLoanById(installmentsList.get(0).getLoansId()).getStartDate(), LocalTime.ofSecondOfDay(0));
            } catch (LoanNotFoundException e) {
                e.printStackTrace();
            }
            for (LoanCashFlowDto n: installmentsList) {
                if (n.isAnInstallment()){
                    result = result.add(n.getRepaymentAmount());
                    if (n.getTransactionTimeStamp().isAfter(lastRepayment)) lastRepayment = n.getTransactionTimeStamp();
                }
            }
            LOGGER.info("SUM of all done repayments is: " + result);
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