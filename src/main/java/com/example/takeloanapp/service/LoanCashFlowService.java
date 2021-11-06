package com.example.takeloanapp.service;

import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.repository.LoanCashFlowRepository;
import com.example.takeloanapp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanCashFlowService {
    @Autowired
    private LoanCashFlowRepository loanCashFlowRepository;
    @Autowired
    private LoanRepository loanRepository;

    public LoanCashFlow saveTransaction(LoanCashFlow loanCashFlow){
        return loanCashFlowRepository.save(loanCashFlow);
    }

    public List<LoanCashFlow> getAllCashTransaction(){
        return loanCashFlowRepository.findAll();
    }

    public List<LoanCashFlow> getAllCashTransactionByLoanId(Long loanId){
        return loanCashFlowRepository.findAllByLoans(loanRepository.findById(loanId)).stream()
                .filter(LoanCashFlow::isAnInstallment)
                .collect(Collectors.toList());
//        return allRepayments;
    }
}