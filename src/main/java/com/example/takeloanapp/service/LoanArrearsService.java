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

    public void checkArrearsOnLoan() throws LoanNotFoundException {
        LOGGER.info("STARTING checkArrearsOnLoan.");
        List<Loans> allLoans = loanService.getAllLoans().stream()
                .filter(loans -> !loans.isClosed())
                .filter(loans -> loans.getDayOfInstalmentRepayment() == LocalDate.now().getDayOfMonth())
                .collect(Collectors.toList());
        if (allLoans.isEmpty()) {
            LOGGER.info("No loans to check today.");
        } else {
            LOGGER.info("Let go and do, there is/are: " + allLoans.size() + " loan/s to check.");
            for (Loans loanToCheck : allLoans) {
                LOGGER.info("***************\tWorking with LOAN ID: " + loanToCheck.getId() + "\t******************************");
                BigDecimal dueAmount = arrearsCalculator.checkInstallmentsDue(loanToCheck);
                List<LoanCashFlowDto> allPaymentsForLoanList = loansInstalmentsController.loanRepaymentByLoanId(loanToCheck.getId());
                LoanAmountAndDataDTO sumOffAllRepaidTransactionsAndDate = arrearsCalculator.sumAmountOfPayedInstallments(allPaymentsForLoanList);
                BigDecimal repaidArrears = arrearsCalculator.sumOfArrears(allPaymentsForLoanList.stream().filter(c -> c.getPostingsAsArrears() != null).collect(Collectors.toList()));
                if (loanToCheck.isHasArrears()) {
                    LOGGER.info("Loan has set flag HasArrears, before postings penalty amount is " + loanToCheck.getPenaltyInterestAmount());
                    repaidArrears.add(loanToCheck.getPenaltyInterestAmount());
                }
                BigDecimal sumAllPayedInstallments = sumOffAllRepaidTransactionsAndDate.bigDecimalAmount.subtract(repaidArrears);

                if (dueAmount.compareTo(sumAllPayedInstallments) == -1) {
                    LOGGER.info("Start handle due amount is higher that repaid");
                    handleDueAmountHigherThatRepaid(loanToCheck, sumOffAllRepaidTransactionsAndDate, dueAmount);
                }
                if (dueAmount.compareTo(sumAllPayedInstallments) == 0) {
                    LOGGER.info("Start handle due amount is equal to repayment");
                    handleDueAmountEqualToRepayment(loanToCheck, allPaymentsForLoanList);
                }
                if (dueAmount.compareTo(sumAllPayedInstallments) == 1) {
                    LOGGER.info("Start handle due amount is lower that repaid");
                    handleDueAmountLowerThatRepaid(loanToCheck, allPaymentsForLoanList, sumOffAllRepaidTransactionsAndDate, dueAmount);
                }
            }
        }
    }

    public void handleDueAmountEqualToRepayment(Loans loan, List<LoanCashFlowDto> allPaymentsForLoanList) throws LoanNotFoundException {
        LOGGER.info("There aren't outstandings/overpayments amount.");
        handleRepaymentsPostings(loan, allPaymentsForLoanList);
    }

    public void handleDueAmountHigherThatRepaid(Loans loanToCheck, LoanAmountAndDataDTO repaidAmountAndDate, BigDecimal dueAmount) throws LoanNotFoundException {
        LOGGER.info("Payed amount: " + repaidAmountAndDate.getBigDecimalAmount().subtract(dueAmount).setScale(2, RoundingMode.HALF_UP) + " in higher that due amount, checking outstanding amount.");
        BigDecimal instalmentAndOutstandingAmount = loanToCheck.getPenaltyInterestAmount().add(repaidAmountAndDate.getBigDecimalAmount());
        if (loanToCheck.isHasArrears() && repaidAmountAndDate.getBigDecimalAmount().compareTo(instalmentAndOutstandingAmount) <= 0) {
            loanToCheck.setPenaltyInterestAmount(BigDecimal.ZERO);
            loanToCheck.setHasArrears(false);
            loansController.updateLoan(loansMapper.matToLoanDto(loanToCheck));
            LOGGER.info("Loan has arrears, change flag HasArrears to FALSE.");
            LOGGER.info("The penalty interest has been paid and the flag 'HasArrears' has been set to FALSE.");
        } else {
            LOGGER.info("Loan has overpayment amount.");
        }
    }

    public void handleDueAmountLowerThatRepaid(Loans loanToCheck, List<LoanCashFlowDto> allPaymentsForLoanList, LoanAmountAndDataDTO repaidAmountAndDate, BigDecimal dueAmount) throws LoanNotFoundException {
        BigDecimal arrearsAmoutn = dueAmount.subtract(repaidAmountAndDate.getBigDecimalAmount());
        LOGGER.info("There is outstandings amount. The amount of arrears is - " + arrearsAmoutn + " PLN.");
        loanToCheck.setHasArrears(true);
        BigDecimal outstandingAmountOnLoan = arrearsCalculator.calculatingArrearsAmount(arrearsCalculator.calculatingNumberOfArrearsDays(repaidAmountAndDate), loanToCheck.getLoanAmount());
        BigDecimal existingArrears = BigDecimal.ZERO;
        if (loanToCheck.getPenaltyInterestAmount() != null) {
            existingArrears = loanToCheck.getPenaltyInterestAmount();
        }
        loanToCheck.setPenaltyInterestAmount(outstandingAmountOnLoan.add(existingArrears));
        LOGGER.info("Loan has arrears, change flag HasArrears to true.");
        LOGGER.info("Loan has arrears, setPenaltyInterestAmount: " + outstandingAmountOnLoan);
        loansController.updateLoan(loansMapper.matToLoanDto(loanToCheck));
        handleRepaymentsPostings(loanToCheck, allPaymentsForLoanList);
    }

    public void handleRepaymentsPostings(Loans loans, List<LoanCashFlowDto> repaymentsList) throws LoanNotFoundException {
        LOGGER.info("Start method 'handleIfHasArrears'.... ");
        List<LoanCashFlowDto> tempListNotPostedPayments = repaymentsList.stream()
                .filter(i -> i.getPostingsTimeStamp() == null)
                .collect(Collectors.toList());
        for (LoanCashFlowDto payment : tempListNotPostedPayments) {
            BigDecimal tmpArrears;
            if (loans.getPenaltyInterestAmount() == null) {
                tmpArrears = BigDecimal.ZERO;
            } else {
                tmpArrears = loansController.getLoanById(payment.getLoansId()).getPenaltyInterestAmount();
            }
            if (payment.getRepaymentAmount().compareTo(tmpArrears) >= 0) {
                payment.setPostingsAsArrears(tmpArrears);
                payment.setPostingsAsInstalment(payment.getRepaymentAmount().subtract(tmpArrears));
                loans.setPenaltyInterestAmount(BigDecimal.ZERO);
                loans.setHasArrears(false);
            } else {
                payment.setPostingsAsArrears(payment.getRepaymentAmount());
                loans.setPenaltyInterestAmount(tmpArrears.subtract(payment.getRepaymentAmount()));
            }
            payment.setPostingsTimeStamp(LocalDateTime.now());
            loansInstalmentsController.updatePayment(payment);
            loansController.updateLoan(loansMapper.matToLoanDto(loans));
            LOGGER.info("Repayment ID: " + payment.getTransactionId() + " has been posted on loan.");
        }
    }
}