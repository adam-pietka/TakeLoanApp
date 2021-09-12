package com.example.takeloanapp.service;

import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.repository.LoanCashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoanCashFlowService {
    @Autowired
    private LoanCashFlowRepository loanCashFlowRepository;

    public LoanCashFlow saveTransaction(LoanCashFlow loanCashFlow){
        return loanCashFlowRepository.save(loanCashFlow);
    }

    public List<LoanCashFlow> getAllCashTransaction(){
        return loanCashFlowRepository.findAll();
    }

    public List<LoanCashFlow> getAllCashTransactionForLoan (Loans loan){
        return  loanCashFlowRepository.findAllByLoans(loan);
    }
}