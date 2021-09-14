package com.example.takeloanapp.mapper;

import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoansDto;
import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanCashFlowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LoansMapper {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanCashFlowRepository loanCashFlowRepository;

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
                loansDto.getCustomerId() !=null ? customerRepository.findById(loansDto.getCustomerId()).orElse(null) :null ,
                loansDto.getLoanAccountNumber(),
                loansDto.isClosed(),
                loansDto.getLoanCashFlowId().stream()
                        .map(loanCashFlowRepository::findById).map(Optional::orElseThrow)
                        .collect(Collectors.toList())
        );
    }

    public LoansDto matToLoanDto(final Loans loans){

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
                loans.getCustomer() != null ? loans.getCustomer().getId() : null,
                loans.getLoanAccountNumber(),
                loans.isClosed(),
                loans.getLoanCashFlows().stream()
                        .map(LoanCashFlow::getTransactionId)
                        .collect(Collectors.toList())
                );
    }

    public List<LoansDto> mapToLoansDtoList(List<Loans> loansList){
        return loansList.stream()
                .map(this::matToLoanDto)
                .collect(Collectors.toList());
    }
}
