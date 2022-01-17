package com.example.takeloanapp.calculator;

import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoanAmountAndDataDTO;
import com.example.takeloanapp.domain.dto.LoanCashFlowDto;
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
public class ArrearsCalculator {
    private static final Logger LOGGER = LoggerFactory.getLogger(ArrearsCalculator.class);
    private static final BigDecimal ARREARS_DAILY_INTEREST_RATE = new BigDecimal("0.00055");
    @Autowired
    private LoansController loansController;

    public BigDecimal calculatingArrearsAmount(int numbersOfDays, BigDecimal amountOfLoan) {
        BigDecimal numbersOfDaysBigDecimal = new BigDecimal(numbersOfDays);
        LOGGER.info("Starting calculating ArrearsAmount for " + numbersOfDaysBigDecimal + " numbers of delay days.");
        BigDecimal oustandingsAmount = ARREARS_DAILY_INTEREST_RATE.multiply(amountOfLoan).setScale(2, RoundingMode.HALF_UP);
        oustandingsAmount = oustandingsAmount.multiply(numbersOfDaysBigDecimal).setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("Outstanding amount is: " + oustandingsAmount + " PLN.");
        return oustandingsAmount;
    }

    public BigDecimal checkInstallmentsDue(Loans loans) throws LoanNotFoundException {
        int repaidInstalmentNumber = calculateNumbersOfMonthsFromStart(loans.getStartDate());
        LOGGER.info("Result 'repaidInstalmentNumber' is: " + repaidInstalmentNumber);
        if (repaidInstalmentNumber > 0) {
            BigDecimal installment;
            installment = loans.getNextInstalmentInterestRepayment().add(loans.getNextInstalmentCapitalRepayment());
            LOGGER.info("For loan ID: " + loans.getId() + ", installment is equal:\t" + installment + " PLN.");
            BigDecimal dueAmount;
            dueAmount = installment.multiply(BigDecimal.valueOf(repaidInstalmentNumber)).setScale(2, RoundingMode.HALF_UP);
            LOGGER.info("In total, it should be paid off: \t" + dueAmount + " amount for capital and interests.");
            return dueAmount;
        }
        LOGGER.info("Loan ID: " + +loans.getId() + " is too young. There shouldn't be payed any instalments.");
        return BigDecimal.ZERO;
    }

    public int calculateNumbersOfMonthsFromStart(LocalDate startDate) {
        LOGGER.info("Starting calculating how many months/instalments was payed");
        Period diff = Period.between(startDate, LocalDate.now());
        int years = diff.getYears();
        int months = diff.getMonths();
        return years * 12 + months;
    }

    public LoanAmountAndDataDTO sumAmountOfPayedInstallments(List<LoanCashFlowDto> installmentsList) {
        LOGGER.info("Start - sumAmountOfPayedInstallments.");
        BigDecimal result = BigDecimal.ZERO;
        LocalDateTime lastRepayment = LocalDateTime.MIN;
        if (!installmentsList.isEmpty()) {
            try {
                lastRepayment = LocalDateTime.of(loansController.getLoanById(installmentsList.get(0).getLoansId()).getStartDate(), LocalTime.ofSecondOfDay(0));
            } catch (LoanNotFoundException e) {
                e.printStackTrace();
            }
            for (LoanCashFlowDto n : installmentsList) {
                if (n.isAnInstallment()) {
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

    public int calculatingNumberOfArrearsDays(LoanAmountAndDataDTO amountAndDate) {
        LOGGER.info("Starting calculating numbers of days");
        Period diff = Period.between(amountAndDate.getLocalDateTime().toLocalDate(), LocalDate.now());
        int years = diff.getYears();
        int months = diff.getMonths();
        int days = diff.getDays();
        int result = years * 365 + months * 30 + days;
        LOGGER.info("Result is: \t" + result + " days.");
        return result;
    }

    public BigDecimal sumOfArrears(List<LoanCashFlowDto> paymentsList) {
        LOGGER.info("Start - sumOfArrears...");
        BigDecimal result = BigDecimal.ZERO;
        List<LoanCashFlowDto> arrearsList = paymentsList.stream()
                .filter(l -> l.getPostingsAsArrears() != null && l.isAnInstallment())
                .collect(Collectors.toList());
        if (!arrearsList.isEmpty()) {
            for (LoanCashFlowDto n : paymentsList) {
                result = result.add(n.getPostingsAsArrears());
            }
            LOGGER.info("SUM of all done payed arrears is: " + result);

            return result;
        }
        return result;
    }
}