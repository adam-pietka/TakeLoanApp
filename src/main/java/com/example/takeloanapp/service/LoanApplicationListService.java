package com.example.takeloanapp.service;

import com.example.takeloanapp.calculator.LoanCalculator;
import com.example.takeloanapp.controller.exception.LoanApplicationsListNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoanApplicationsListDto;
import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import com.example.takeloanapp.validator.LoanApplicationValidator;
import com.example.takeloanapp.validator.LoanConditionsValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class LoanApplicationListService {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplicationListService.class);

    @Autowired
    private LoanApplicationListRepository loanAppRepository;

    @Autowired
    private LoanApplicationValidator loanApplicationValidator;

    @Autowired
    private LoanConditionsValidator loanConditionsValidator;

    @Autowired
    private LoanCalculator loanCalculator;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private LoanService loanService;

    public void saveLoanApp(LoanApplicationsList loanApplicationsList) throws LoanApplicationsListNotFoundException{
        LOGGER.info("Starting saving loan application.");
        LoanApplicationsList savedRecord = loanApplicationsList;
        savedRecord.setDateOfRegistrationOfApplication(LocalDate.now());

        boolean isAccepted = loanApplicationValidator.validateBasicData(savedRecord)
                && loanConditionsValidator.validLoanData(savedRecord);

        if (isAccepted){
            startPreparingSimulation(savedRecord);
        } else {
            LOGGER.info("Loan application has NOT been accepted.");
            savedRecord.setDataOfClosedOfApplication(LocalDate.now());
            savedRecord.setClosed(true);
        }
        savedRecord.setApplicationAccepted(isAccepted);

        loanAppRepository.save(savedRecord);
        if (loanAppRepository.findById(savedRecord.getId()).isEmpty()){
            LOGGER.error("LOAN APPLICATION HAS NOT BEEN SAVED IN DATE BASE.");
            throw new LoanApplicationsListNotFoundException("Loan Application is not saved in database");
        } else {
            LOGGER.info("Loan application has been saved in data base.");
        }
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

    public void startPreparingSimulation(LoanApplicationsList loanAppl){
        LOGGER.info("Loan application has been accepted, starting prepare SIMULATION for LOAN");
        BigDecimal monthlyInterestRate = loanCalculator.calculateMonthlyInterestRate(loanAppl);
        BigDecimal monthlyPayment =  loanCalculator.monthlyPayment(loanAppl, monthlyInterestRate);
        BigDecimal totalLoanAmount = loanCalculator.totalLoanPayments(loanAppl, monthlyPayment);

        Loans newLoan = new Loans();
        newLoan.setPeriodInMonth(loanAppl.getRepaymentPeriodInMonth());
        newLoan.setStartDate(loanAppl.getDateOfRegistrationOfApplication());
        newLoan.setEndDate(loanAppl.getDateOfRegistrationOfApplication().plusMonths(loanAppl.getRepaymentPeriodInMonth()));
        newLoan.setDayOfInstalmentRepayment(loanAppl.getDateOfRegistrationOfApplication().getDayOfMonth());
        newLoan.setLoanAmount(loanAppl.getLoanAmount());
        newLoan.setLoanTotalInterest(totalLoanAmount.subtract(loanAppl.getLoanAmount()));
        newLoan.setNextInstalmentInterestRepayment(monthlyInterestRate);
        newLoan.setActive(true);
        newLoan.setRegistrationDate(LocalDate.now());
        newLoan.setCustomer(loanAppl.getCustomer());
        newLoan.setClosed(false);

        boolean simulationAnswer =  loanApplicationValidator.simulationOfCredit(loanAppl, monthlyPayment);
        if (simulationAnswer){
            LOGGER.info("Simulation for Loan looks fine, so we can go to next step.");
            Loans allDataLoan =  loanCalculator.setStaticDataToLoan(newLoan);
            loanService.saveLoan(allDataLoan);
        } else {
            LOGGER.info("Simulation for Loan looks badly, loan at this parameters can not be accepted, sorry.");
        }
    }
}