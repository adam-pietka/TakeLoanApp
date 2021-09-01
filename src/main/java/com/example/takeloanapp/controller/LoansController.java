package com.example.takeloanapp.controller;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.CustomerDto;
import com.example.takeloanapp.domain.dto.LoansDto;
import com.example.takeloanapp.mapper.CustomerMapper;
import com.example.takeloanapp.mapper.LoansMapper;
import com.example.takeloanapp.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/takeLoan/loans")
public class LoansController {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private LoansMapper loansMapper;

    @Autowired
    private LoanService loanService;

    @PostMapping(value = "registerLoan", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void registerLoan(@RequestBody LoansDto loansDto) throws LoanNotFoundException{
        Loans loan = loansMapper.mapToLoan(loansDto);
        loanService.saveLoan(loan);
    }

    @GetMapping(value = "findLoanById")
    public LoansDto getCustomer(@RequestParam Long loanId) throws LoanNotFoundException {

        Loans loan = loanService.getLoanById(loanId).orElseThrow(()-> new LoanNotFoundException(""));
        return loansMapper.matToLoanDto(loan);
    }

    @GetMapping(value = "getAllLoans")
    public List<LoansDto> getAllLoans(){
        List<Loans> loansList = loanService.getAllLoans();
        return  loansMapper.mapToLoansDtoList(loansList);
    }

    @PutMapping(value = "updateLoan")
    public LoansDto updateLoan(@RequestBody LoansDto loansDto ) throws LoanNotFoundException {

        if (loanService.getLoanById(loansDto.getId()).isPresent()){
            Loans loans = loansMapper.mapToLoan(loansDto);
            Loans savedLoan = loanService.saveLoan(loans);
            return loansMapper.matToLoanDto(savedLoan);
        }
        throw new LoanNotFoundException("UPDATE customer operation  is aborted, customer is not exist in DB.");
    }

    @DeleteMapping(value = "removeLoanFromDB")
    public boolean deleteLoan(@RequestParam Long loanId) throws LoanNotFoundException{
        if (loanService.getLoanById(loanId).isPresent()){
            loanService.deleteLoan(loanId);
            return true;
        }
        return false;
    }
}