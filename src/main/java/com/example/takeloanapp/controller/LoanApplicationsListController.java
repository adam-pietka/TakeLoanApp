package com.example.takeloanapp.controller;

import com.example.takeloanapp.controller.exception.LoanApplicationsListNotFoundException;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.dto.LoanApplicationsListDto;
import com.example.takeloanapp.mapper.LoanApplicationsListMapper;
import com.example.takeloanapp.service.LoanApplicationListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/takeLoan/loanApplist")
public class LoanApplicationsListController {

    @Autowired
    private LoanApplicationsListMapper loanAppListMapper;

    @Autowired
    private LoanApplicationListService loanAppListService;


    @PostMapping(value = "registerLoanApplication", consumes = MediaType.APPLICATION_JSON_VALUE)
    public LoanApplicationsList registerLoanApplication(@RequestBody LoanApplicationsListDto loanApplicationsListDto) throws LoanApplicationsListNotFoundException {
        if (loanAppListService.checkThatAppHasMandatoryFields(loanApplicationsListDto)){
        LoanApplicationsList loanApp = loanAppListMapper.mapToLoanApplicationsList(loanApplicationsListDto);
        return loanAppListService.saveLoanApp(loanApp);
        }
        throw new LoanApplicationsListNotFoundException("Loan application is not saved.");
    }

    @GetMapping(value = "findLoanAppById")
    public LoanApplicationsListDto getCustomer(@RequestParam Long loanAppId) throws LoanApplicationsListNotFoundException {
        LoanApplicationsList loanApplicationsList = loanAppListService.getLoanApplicationById(loanAppId).orElseThrow(() -> new LoanApplicationsListNotFoundException("Loan application is not exist in DB."));
        return loanAppListMapper.matToLoanApplicationsListDto(loanApplicationsList) ;
    }

    @GetMapping(value = "getAllLoansApp")
    public List<LoanApplicationsListDto> getAllLoans()throws LoanApplicationsListNotFoundException {
        List<LoanApplicationsList> loansAppList = loanAppListService.getAllLoanApp();
        return loanAppListMapper.mapToLoansDtoList(loansAppList);
    }

    @PutMapping(value = "updateLoanApp")
    public LoanApplicationsListDto updateLoan(@RequestBody LoanApplicationsListDto loansAppDto ) throws LoanApplicationsListNotFoundException {
        if (!loanAppListService.checkByIdThatLoanAppIsExist(loansAppDto.getId())){
            throw new LoanApplicationsListNotFoundException("Loan application is not exist in DB - UPDATE is failed.");
        }
        LoanApplicationsList loanApplicationsList = loanAppListMapper.mapToLoanApplicationsList(loansAppDto);
        LoanApplicationsList savedLoanAppList = loanAppListService.saveLoanApp(loanApplicationsList);
        return loanAppListMapper.matToLoanApplicationsListDto(savedLoanAppList);
    }

    @DeleteMapping(value = "removeLoanAppFromDB")
    public boolean deleteLoan(@RequestParam Long loanAppId) throws LoanApplicationsListNotFoundException {
        if (loanAppListService.checkByIdThatLoanAppIsExist(loanAppId)) {
            return loanAppListService.deleteLoanApp(loanAppId);
        }
        return false;
    }
}