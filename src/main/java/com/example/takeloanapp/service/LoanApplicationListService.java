package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.exception.LoanApplicationsListNotFoundException;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import com.example.takeloanapp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationListService {

    @Autowired
    private LoanApplicationListRepository loanAppRepository;

    public LoanApplicationsList saveLoanApp(LoanApplicationsList loanApplicationsList){
        return loanAppRepository.save(loanApplicationsList);
    }

    public Optional<LoanApplicationsList> getLoanApplicationById(Long loanAppId){
        return  loanAppRepository.findById(loanAppId);
    }

    public List<LoanApplicationsList> getAllLoanApp() {
        return   loanAppRepository.findAll();
    }

    public void deleteLoanApp(Long loanAppId){
        loanAppRepository.deleteById(loanAppId);
    }

    public boolean checkByIdThatLoanAppIsExist(Long loanAppId) throws LoanApplicationsListNotFoundException {
        if (loanAppRepository.findById(loanAppId).isEmpty()){
            throw new LoanApplicationsListNotFoundException("Loan application of specified number ID does not exist in DB.");
        }
        return true;
    }
}