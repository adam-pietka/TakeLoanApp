package com.example.takeloanapp.service;

import com.example.takeloanapp.calculator.LoanCalculator;
import com.example.takeloanapp.controller.exception.LoanApplicationsListNotFoundException;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.LoanCashFlow;
import com.example.takeloanapp.domain.Loans;
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
    private LoanService loanService;
    @Autowired
    private LoanCashFlowService loanCashFlowService;

    public void saveLoanApp(LoanApplicationsList loanApplicationsList) throws LoanApplicationsListNotFoundException{
        LOGGER.info("Starting saving loan application.");
        LoanApplicationsList savedRecord = loanApplicationsList;
        if (savedRecord.getId() != null){
            disbursementOfLoan(savedRecord);
            loanAppRepository.save(savedRecord);
        } else {
            savedRecord.setDateOfRegistrationOfApplication(LocalDate.now());
            boolean isAccepted = loanApplicationValidator.validateBasicData(savedRecord)
                    && loanConditionsValidator.validLoanData(savedRecord);
            savedRecord.setApplicationAccepted(isAccepted);

            Long loanId = null;
            if (isAccepted) {
                Loans registeredLoans = startPreparingSimulation(savedRecord);
                loanId = registeredLoans.getId();
                savedRecord.setLoans(registeredLoans);
            } else {
                LOGGER.info("Loan application has NOT been accepted.");
                savedRecord.setDataOfClosedOfApplication(LocalDate.now());
                savedRecord.setClosed(true);
            }

            loanAppRepository.save(savedRecord);
            if (loanAppRepository.findById(savedRecord.getId()).isEmpty()) {
                LOGGER.error("LOAN APPLICATION HAS NOT BEEN SAVED IN DATE BASE.");
                throw new LoanApplicationsListNotFoundException("Loan Application is not saved in database");
            } else {
                LOGGER.info("Loan application has been saved in data base.");
                if (loanId != null) {
                    String generatedIban = loanService.generateAccountForRepayment(savedRecord.getId());
                    loanService.setGeneratedAccount(loanId, generatedIban);
                }
            }
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

    public Loans startPreparingSimulation(LoanApplicationsList loanAppl){
        LOGGER.info("Loan application has been accepted, starting prepare SIMULATION for LOAN");
        BigDecimal monthlyInterestRate = loanCalculator.calculateMonthlyInterestRate();
        BigDecimal monthlyPayment =  loanCalculator.monthlyInstalment(loanAppl, monthlyInterestRate);
        BigDecimal totalLoanAmount = loanCalculator.totalLoanPayments(loanAppl, monthlyPayment);

        boolean simulationAnswer =  loanApplicationValidator.simulationOfCredit(loanAppl, monthlyPayment);
        if (simulationAnswer){
            LOGGER.info("Simulation for Loan looks fine, so we can go to next step.");
            BigDecimal monthlyInterestOfPayment = loanCalculator.monthlyInterest(loanAppl, monthlyInterestRate);
            BigDecimal monthlyCapitalOfPayment = loanCalculator.monthlyCapital(loanAppl);
            return loanService.registerNewLoan(loanAppl, monthlyInterestRate, monthlyInterestOfPayment, monthlyCapitalOfPayment ,monthlyPayment, totalLoanAmount);
        }
            LOGGER.info("Simulation for Loan looks badly, loan at this parameters can not be accepted, sorry.");
        return null;
    }

    public void disbursementOfLoan(LoanApplicationsList loanAppl) throws LoanApplicationsListNotFoundException{
        LoanApplicationsList disbursementLoan = loanAppl;
        LoanApplicationsList beforePut = loanAppRepository.findById(loanAppl.getId()).get();
        if (!beforePut.isPayoutsDone() && loanAppl.isPayoutsDone()) {
            LOGGER.info("Disbursement of loan is starting...");
            disbursementLoan.setPayoutsDone(true);
            disbursementLoan.setDateOfPayout(LocalDate.now());

            LoanCashFlow cashDisbursement = new LoanCashFlow();
            cashDisbursement.setRepaymentAmount(disbursementLoan.getLoanAmount());
            cashDisbursement.setLoans(disbursementLoan.getLoans());
            cashDisbursement.setDisbursement(true);
            cashDisbursement.setAccountNumber(disbursementLoan.getAccountNumberForPaymentOfLoan());
            cashDisbursement.setTransactionTimeStamp(LocalDateTime.now());
            loanCashFlowService.saveTransaction(cashDisbursement);
            loanService.setLoanActiveForDisbursement(loanAppl.getLoans().getId());
        }
    }
}