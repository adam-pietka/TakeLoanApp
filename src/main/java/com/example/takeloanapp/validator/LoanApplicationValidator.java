package com.example.takeloanapp.validator;

import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.LoanApplicationsList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
public class LoanApplicationValidator {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoanApplicationValidator.class);

    public boolean validateBasicData(LoanApplicationsList loanAppl){
        LOGGER.info("Starting checking incom, T-recommendation...");
        boolean responseAge = true;
        boolean responseIncom = true;
        boolean responseRating = true;

        if(isAgeIncorrect(loanAppl.getCustomer())){
            responseAge = false;
        }
        if(isIncomeToLow(loanAppl)){
            responseIncom = false;
        }
        if(isCreditRatingBad(loanAppl) ){
            responseRating = false;
        }
        LOGGER.info("Checking all ingredients (AGE/INCOME/CREDIT RATE) is complete, all condition are met - " + responseAge + "/" + responseIncom + "/" + responseRating + ". General rating: " + (responseAge && responseIncom && responseRating) );
        return responseAge && responseIncom && responseRating ;
    }

    public boolean isAgeIncorrect(Customer customer){
        LOGGER.info("Starting checking AGE.");
        int checkMonth = Integer.parseInt(customer.getPeselNumber().substring(2,3));
        int checkYear = Integer.parseInt(customer.getPeselNumber().substring(0,2));
        int currentYear =  LocalDate.now().getYear();

        if(checkMonth == 2 || checkMonth == 3){
            checkYear = checkYear + 2000;
        }
        if(checkMonth == 0 || checkMonth == 1){
            checkYear = checkYear + 1900;
        }
        if (currentYear - checkYear >= 65 || currentYear - checkYear < 18){
            LOGGER.info("Checking AGE is complete with NEGATIVE result.");
            return true;
        }
        LOGGER.info("Checking AGE is complete with POSITIVE result.");
        return false;
    }

    public boolean isIncomeToLow(LoanApplicationsList loanAppl){
        LOGGER.info("Starting checking MINIMUM INCOME.");
        BigDecimal minIncomeAmount = new BigDecimal("2000.00");
        BigDecimal customerIncome = loanAppl.getIncomeAmount();
        if (customerIncome.compareTo(minIncomeAmount) == 1 ){
            LOGGER.info("Checking INCOME is complete - with POSITIVE result.");
            return false;
        }
        LOGGER.info("Checking INCOME is complete with NEGATIVE result.");
        return true;
    }

    public boolean isCreditRatingBad(LoanApplicationsList loanAppl){
        LOGGER.info("Starting checking CREDIT RATE.");

        BigDecimal otherLiabilities = loanAppl.getOtherLiabilities().setScale(2, RoundingMode.HALF_UP);
        BigDecimal customerIncome = loanAppl.getIncomeAmount().setScale(2, RoundingMode.HALF_UP);
        BigDecimal percentageThreshold = new BigDecimal("0.5").setScale(2, RoundingMode.HALF_UP);
        LOGGER.info("otherLiabilities: " + otherLiabilities);
        LOGGER.info("customerIncome: " + customerIncome);
        LOGGER.info("Threshold: "  + percentageThreshold);

        BigDecimal calulatedPercent =  otherLiabilities.divide(customerIncome, 3, RoundingMode.HALF_UP);

        if(calulatedPercent.compareTo(percentageThreshold) ==  1){
            LOGGER.info("Checking CREDIT RATE is complete with NEGATIVE result: FALSE value: "  + calulatedPercent);
            return true;
        }
        LOGGER.info("Checking CREDIT RATE is complete with POSITIVE result: TRUE - value: " + calulatedPercent);
        return false;
    }
}