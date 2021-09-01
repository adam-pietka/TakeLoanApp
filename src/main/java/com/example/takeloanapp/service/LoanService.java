package com.example.takeloanapp.service;

import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {

    @Autowired
    private LoanRepository loanRepository;

    public Optional<Loans> getLoanById(Long loanId){
        return loanRepository.findById(loanId);
    }

    public List<Loans> getAllLoans(){
        return loanRepository.findAll();
    }

    public Loans saveLoan(Loans loans){
        return loanRepository.save(loans) ;
    }

    public void deleteLoan(Long loanId){
        loanRepository.deleteById(loanId);
    }

}
