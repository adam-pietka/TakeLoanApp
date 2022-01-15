package com.example.takeloanapp.service;

import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.Loans;
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

    public void depositInstalment(Loans loans, BigDecimal instalmentAmount){
        LOGGER.info("Starting deposit amount: " + instalmentAmount.setScale(2, RoundingMode.HALF_UP) + ", instalment on loan id: " + loans.getId());
        LoanCashFlow instalment = new LoanCashFlow();
        instalment.setLoans(loans);
        instalment.setRepaymentAmount(instalmentAmount);
        instalment.setAnInstallment(true);
        instalment.setAccountNumber(loans.getLoanAccountNumber());
        instalment.setTransactionTimeStamp(LocalDateTime.now());
//            LOGGER.info("Starting update payed amount on LOAN table. Loan ID: " + loans.getId());
        cashFlowService.saveTransaction(instalment);
    }

    public void updatePayment(LoanCashFlow updatedPayment){
        LOGGER.info("Starting updating payment id: " + updatedPayment.getTransactionId());
        cashFlowService.saveTransaction(updatedPayment);
        LOGGER.info("Payment updating has been finished - payment id: " + updatedPayment.getTransactionId());
    }
}
