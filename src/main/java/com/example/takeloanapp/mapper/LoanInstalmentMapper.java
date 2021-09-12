package com.example.takeloanapp.mapper;

import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.dto.LoanCashFlowDto;
import com.example.takeloanapp.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LoanInstalmentMapper {
    @Autowired
    private LoanRepository loanRepository;

    public LoanCashFlow mapToLoanCashFlow(LoanCashFlowDto cashFlowDto){
        return  new LoanCashFlow(
                cashFlowDto.getTransactionId(),
                cashFlowDto.getLoansId() != null ? loanRepository.findById(cashFlowDto.getLoansId()).orElse(null) : null ,
                cashFlowDto.getRepaymentAmount(),
                cashFlowDto.getOverpaymentAmount(),
                cashFlowDto.getUnderpaymentAmount(),
                cashFlowDto.isAnInstallment(),
                cashFlowDto.isDisbursement(),
                cashFlowDto.getAccountNumber(),
                cashFlowDto.getTransactionTimeStamp()
        );

    }

    public LoanCashFlowDto mapToLoanCashFlowDto(LoanCashFlow cashFlow){
        return new LoanCashFlowDto(
                cashFlow.getTransactionId(),
                cashFlow.getLoans() != null ? cashFlow.getLoans().getId() : null,
                cashFlow.getRepaymentAmount(),
                cashFlow.getOverpaymentAmount(),
                cashFlow.getUnderpaymentAmount(),
                cashFlow.isAnInstallment(),
                cashFlow.isDisbursement(),
                cashFlow.getAccountNumber(),
                cashFlow.getTransactionTimeStamp()
        );
    }

    public List<LoanCashFlowDto> mapToListLoanCashFlowDto(List<LoanCashFlow> cashFlowList){
        return cashFlowList.stream()
                .map(this::mapToLoanCashFlowDto)
                .collect(Collectors.toList());
    }
}
