package com.example.takeloanapp.controller;

import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.dto.LoanCashFlowDto;
import com.example.takeloanapp.mapper.LoanInstalmentMapper;
import com.example.takeloanapp.service.LoanCashFlowService;
import com.example.takeloanapp.service.LoansInstalmentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/takeLoan/instalmentRepayment")
public class LoansInstalmentsController {

    @Autowired
    private LoanCashFlowService cashFlowService;
    @Autowired
    private LoanInstalmentMapper instalmentMapper;
    @Autowired
    private LoansInstalmentsService instalmentsService;

    @PostMapping(value = "insalmentRepayment", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void instalmentRepayment(@RequestBody LoanCashFlowDto cashFlowDto){
        LoanCashFlow cashFlow = instalmentMapper.mapToLoanCashFlow(cashFlowDto);
        instalmentsService.depositInstalment(cashFlow.getLoans(), cashFlow.getRepaymentAmount());

    }

    @GetMapping(value = "getAllCashRepayment")
    public List<LoanCashFlowDto>getAllCashRepayment(){
        List<LoanCashFlow> loanCashFlow = cashFlowService.getAllCashTransaction();
        return instalmentMapper.mapToListLoanCashFlowDto(loanCashFlow);
    }

}