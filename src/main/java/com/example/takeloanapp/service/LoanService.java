package com.example.takeloanapp.service;

import com.example.takeloanapp.calculator.LoanCalculator;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanService.class);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private LoanCalculator loanCalculator;

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

    public Loans registerNewLoan(LoanApplicationsList loanAppl,  BigDecimal monthlyInterestRate, BigDecimal monthlyPayment, BigDecimal totalLoanAmount){
        LOGGER.info("Starting fill data to new loan.");
        Loans newLoan = new Loans();
        newLoan.setPeriodInMonth(loanAppl.getRepaymentPeriodInMonth());
        newLoan.setStartDate(loanAppl.getDateOfRegistrationOfApplication());
        newLoan.setEndDate(loanAppl.getDateOfRegistrationOfApplication().plusMonths(loanAppl.getRepaymentPeriodInMonth()));
        newLoan.setDayOfInstalmentRepayment(loanAppl.getDateOfRegistrationOfApplication().getDayOfMonth());
        newLoan.setLoanAmount(loanAppl.getLoanAmount());
        newLoan.setLoanTotalInterest(totalLoanAmount.subtract(loanAppl.getLoanAmount()));
        newLoan.setNextInstalmentInterestRepayment(monthlyInterestRate);
        newLoan.setNextInstalmentCapitalRepayment(monthlyPayment.subtract(monthlyInterestRate));
        newLoan.setActive(true);
        newLoan.setRegistrationDate(LocalDate.now());
        newLoan.setCustomer(loanAppl.getCustomer());
        newLoan.setClosed(false);
        Loans allDataLoan =  loanCalculator.setStaticDataToLoan(newLoan);

        Loans savedRecord = saveLoan(allDataLoan);
        LOGGER.info("Starting fill data to new loan, id is: "  + savedRecord.getId());
        return savedRecord;
    }
}