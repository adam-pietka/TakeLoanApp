package com.example.takeloanapp.mapper;

import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoansDto;
import com.example.takeloanapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoansMapper {

    @Autowired
    private CustomerRepository customerRepository;

    public Loans mapToLoan(final LoansDto loansDto){
        return new Loans(
                loansDto.getId(),
                loansDto.getProductName(),
                loansDto.getPeriodInMonth(),
                loansDto.getStartDate(),
                loansDto.getEndDate(),
                loansDto.getDayOfInstalmentRepayment(),
                loansDto.getLoanAmount(),
                loansDto.getLoanRate(),
                loansDto.getLoanTotalInterest(),
                loansDto.getNextInstalmentInterestRepayment(),
                loansDto.getNextInstalmentCapitalRepayment(),
                loansDto.isActive(),
                loansDto.getRegistrationDate(),
                loansDto.isHasArrears(),
                loansDto.getCounterDaysArrears(),
                loansDto.getPenaltyInterest(),
                loansDto.getPenaltyInterestAmount(),
                loansDto.getCustomer(),
                loansDto.getLoanAccountNumber(),
                loansDto.isClosed()
        );
    }

    private LoansDto matToLoanDto(final Loans loans){

        return new LoansDto(
                loans.getId(),
                loans.getProductName(),
                loans.getPeriodInMonth(),
                loans.getStartDate(),
                loans.getEndDate(),
                loans.getDayOfInstalmentRepayment(),
                loans.getLoanAmount(),
                loans.getLoanRate(),
                loans.getLoanTotalInterest(),
                loans.getNextInstalmentInterestRepayment(),
                loans.getNextInstalmentCapitalRepayment(),
                loans.isActive(),
                loans.getRegistrationDate(),
                loans.isHasArrears(),
                loans.getCounterDaysArrears(),
                loans.getPenaltyInterest(),
                loans.getPenaltyInterestAmount(),
                loans.getCustomer(),
                loans.getLoanAccountNumber(),
                loans.isClosed()
                );
    }



}
