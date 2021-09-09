package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.exception.LoanNotFoundException;
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

    public Loans saveLoan(Loans loans){
        return loanRepository.save(loans) ;
    }

    public Optional<Loans> getLoanById(Long loanId){
        return loanRepository.findById(loanId);
    }

    public List<Loans> getAllLoans(){
        return loanRepository.findAll();
    }

    public void deleteLoan(Long loanId){
        loanRepository.deleteById(loanId);
    }

    public boolean checkByIdThatLoanExist(Long loanId) throws  LoanNotFoundException{
        if (loanRepository.findById(loanId).isEmpty()){
            throw new LoanNotFoundException("Loan of specified number ID does not exist in DB.");
        }
        return true;
    }

    public boolean validateLoanMandatoryDate(Loans loans) throws LoanNotFoundException {
        if (loans.getLoanAmount() ==  null|| loans.getLoanRate() == null){
            throw  new LoanNotFoundException("Please correct loan amount or loan rate.");
        }

        return true;
    }
}