package com.example.takeloanapp.mapper;

import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.dto.LoanApplicationsListDto;
import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanApplicationsListMapper {
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanRepository loanRepository;

    public LoanApplicationsList mapToLoanApplicationsList(final LoanApplicationsListDto loanApplicationsListDto){

        return new LoanApplicationsList(
                loanApplicationsListDto.getId(),
                loanApplicationsListDto.getCustomerId() !=null ? customerRepository.findById(loanApplicationsListDto.getCustomerId()).orElse(null) : null,
                loanApplicationsListDto.getLoansId() != null ? loanRepository.findById(loanApplicationsListDto.getLoansId()).orElse(null) : null,
                loanApplicationsListDto.getEmploymentForm(),
                loanApplicationsListDto.getIncomeAmount(),
                loanApplicationsListDto.getEmployerName(),
                loanApplicationsListDto.getEmployerNipNumber(),
                loanApplicationsListDto.getEmployerAddress(),
                loanApplicationsListDto.getEmployerPhoneNumber(),
                loanApplicationsListDto.getOtherLiabilities(),
                loanApplicationsListDto.getLoanAmount(),
                loanApplicationsListDto.getRepaymentPeriodInMonth(),
                loanApplicationsListDto.isApplicationAccepted(),
                loanApplicationsListDto.getDateOfRegistrationOfApplication(),
                loanApplicationsListDto.getDataOfClosedOfApplication(),
                loanApplicationsListDto.getAccountNumberForPaymentOfLoan(),
                loanApplicationsListDto.isPayoutsDone(),
                loanApplicationsListDto.getDateOfPayout(),
                loanApplicationsListDto.isClosed());
    }

    public LoanApplicationsListDto matToLoanApplicationsListDto(final LoanApplicationsList loanApplicationsList){

        return new  LoanApplicationsListDto(
                loanApplicationsList.getId(),
                loanApplicationsList.getCustomer() != null ? loanApplicationsList.getCustomer().getId() : null ,
                loanApplicationsList.getLoans() != null ? loanApplicationsList.getLoans().getId() : null,
                loanApplicationsList.getEmploymentForm(),
                loanApplicationsList.getIncomeAmount(),
                loanApplicationsList.getEmployerName(),
                loanApplicationsList.getEmployerNipNumber(),
                loanApplicationsList.getEmployerAddress(),
                loanApplicationsList.getEmployerPhoneNumber(),
                loanApplicationsList.getOtherLiabilities(),
                loanApplicationsList.getLoanAmount(),
                loanApplicationsList.getRepaymentPeriodInMonth(),
                loanApplicationsList.isApplicationAccepted(),
                loanApplicationsList.getDateOfRegistrationOfApplication(),
                loanApplicationsList.getDataOfClosedOfApplication(),
                loanApplicationsList.getAccountNumberForPaymentOfLoan(),
                loanApplicationsList.isPayoutsDone(),
                loanApplicationsList.getDateOfPayout(),
                loanApplicationsList.isClosed()

        );
    }

    public List<LoanApplicationsListDto> mapToLoansDtoList(List<LoanApplicationsList> loanApplicationsLists){
        return loanApplicationsLists.stream()
                .map(this::matToLoanApplicationsListDto)
                .collect(Collectors.toList());
    }


}
