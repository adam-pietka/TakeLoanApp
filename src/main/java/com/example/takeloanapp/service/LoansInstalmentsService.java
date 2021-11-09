package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoansDto;
import com.example.takeloanapp.mapper.LoansMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Service
public class LoansInstalmentsService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoansInstalmentsService.class);

    @Autowired
    private LoanCashFlowService cashFlowService;
    @Autowired
    private LoansController loansController;
    @Autowired
    private LoansMapper loansMapper;
    @Autowired
    private LoanService loanService;

    public void depositInstalment(Loans loans, BigDecimal instalmentAmount){
        LOGGER.info("Starting deposit amount: " + instalmentAmount.setScale(2, RoundingMode.HALF_UP) + ", instalement on loan id: " + loans.getId());
        LoanCashFlow instalment = new LoanCashFlow();
        instalment.setLoans(loans);
        instalment.setRepaymentAmount(instalmentAmount);
        instalment.setAnInstallment(true);
        instalment.setAccountNumber(loans.getLoanAccountNumber());
        instalment.setTransactionTimeStamp(LocalDateTime.now());
        instalment.setOverpaymentAmount(checkIsOverpayment(loans, instalmentAmount));
        instalment.setUnderpaymentAmount(checkIsUnderpayment(loans, instalmentAmount));
//        *********
        try {
            LOGGER.info("Starting update payed amount on LOAN table. Loan ID: " + loans.getId());
            loanService.updatePayedAmount(loans, instalmentAmount);
            cashFlowService.saveTransaction(instalment);
        } catch (LoanNotFoundException e) {
            e.printStackTrace();
        }
    }

    public BigDecimal checkIsOverpayment(Loans loans, BigDecimal instalmentAmount){
        BigDecimal monthlyInstalment =  loans.getNextInstalmentCapitalRepayment().add(loans.getNextInstalmentInterestRepayment());
        if (monthlyInstalment.compareTo(instalmentAmount) == -1 ){
            LOGGER.info("Deposited amount is higher that monthly instalment.");
            return instalmentAmount.subtract(monthlyInstalment).setScale(2,RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    public BigDecimal checkIsUnderpayment(Loans loans, BigDecimal instalmentAmount){
        BigDecimal monthlyInstalment =  loans.getNextInstalmentCapitalRepayment().add(loans.getNextInstalmentInterestRepayment());
        if (monthlyInstalment.compareTo(instalmentAmount) == 1 ){
            LOGGER.info("Deposited amount is smaller that monthly instalment.");
            return monthlyInstalment.subtract(instalmentAmount).setScale(2,RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }
}
