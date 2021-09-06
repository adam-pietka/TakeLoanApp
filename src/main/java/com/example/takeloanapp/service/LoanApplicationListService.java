package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.controller.exception.LoanApplicationsListNotFoundException;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoanApplicationsListDto;
import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import com.example.takeloanapp.repository.LoanRepository;
import com.example.takeloanapp.validator.LoanApplicationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationListService {

    @Autowired
    private LoanApplicationListRepository loanAppRepository;

    @Autowired
    private LoanApplicationValidator loanApplicationValidator;

    public LoanApplicationsList saveLoanApp(LoanApplicationsList loanApplicationsList) throws LoanApplicationsListNotFoundException{
        LoanApplicationsList savedRecord = loanAppRepository.save(loanApplicationsList);
        if (loanAppRepository.findById(savedRecord.getId()).isEmpty()){
            throw new LoanApplicationsListNotFoundException("Loan Application is not saved in database");
        }

        savedRecord.setDateOfRegistrationOfApplication(LocalDate.now());
        savedRecord.setApplicationAccepted(loanApplicationValidator.validateBasicData(savedRecord));

        loanAppRepository.save(savedRecord);
        return savedRecord;
    }

    public Optional<LoanApplicationsList> getLoanApplicationById(Long loanAppId)throws LoanApplicationsListNotFoundException {
        if (checkByIdThatLoanAppIsExist(loanAppId)) {
            return  loanAppRepository.findById(loanAppId);
        }
        throw new LoanApplicationsListNotFoundException("Loan Application is not exist in table.");
    }

    public List<LoanApplicationsList> getAllLoanApp() throws LoanApplicationsListNotFoundException {
        if (loanAppRepository.findAll().isEmpty()){
            throw new LoanApplicationsListNotFoundException("Loan application table is empty.");
        }
        return   loanAppRepository.findAll();
    }

    public boolean deleteLoanApp(Long loanAppId) throws LoanApplicationsListNotFoundException{
        if (checkByIdThatLoanAppIsExist(loanAppId)) {
            loanAppRepository.deleteById(loanAppId);
            return true;
        }
        throw new LoanApplicationsListNotFoundException("Loan Application is not exist in table.");
    }

    public boolean checkByIdThatLoanAppIsExist(Long loanAppId) throws LoanApplicationsListNotFoundException {
        if (loanAppRepository.findById(loanAppId).isEmpty()){
            throw new LoanApplicationsListNotFoundException("Loan application of specified number ID does not exist in DB.");
        }
        return true;
    }

    public boolean checkThatAppHasMandatoryFields(LoanApplicationsListDto loanAppListDto) throws LoanApplicationsListNotFoundException {
        if (loanAppListDto.getCustomerId() == null){
            throw new LoanApplicationsListNotFoundException("For loan application is missing mandatory field like Customer ID");
        }
        if (loanAppListDto.getLoanAmount() == null ||  loanAppListDto.getIncomeAmount() == null || loanAppListDto.getOtherLiabilities() == null){
            throw new LoanApplicationsListNotFoundException("For loan application are missing fields, like: loan amount or income or liabilities.");
        }
        if (loanAppListDto.getEmployerAddress().isEmpty() || loanAppListDto.getEmployerName().isEmpty() || loanAppListDto.getEmployerPhoneNumber().isEmpty()){
            throw new LoanApplicationsListNotFoundException("For loan application are missing fields, like: Employer name, phone number or address.");
        }
        return true;
    }
}