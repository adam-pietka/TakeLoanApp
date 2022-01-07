package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.dto.LoansDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class LoanArrears {

    @Autowired
    private LoansController loansController;

    public boolean checkArrearsOnLoan (){
        List<BigDecimal> allRepaymentsOnLoan;

        return true;
    }

    public BigDecimal checkInstallmentsDue(Long loanId) throws LoanNotFoundException {

        LoansDto loansDtoTmp = loansController.getLoanById(loanId) ;
        int repaidInstalmentNumber = calculateNumbersOfMonthsFromStart( loansDtoTmp.getStartDate());

        if (repaidInstalmentNumber > 0) {
            BigDecimal installment ;
            installment = loansDtoTmp.getNextInstalmentInterestRepayment().add(loansDtoTmp.getNextInstalmentCapitalRepayment());
            return installment.multiply(BigDecimal.valueOf(repaidInstalmentNumber)).setScale(2, RoundingMode.HALF_UP);
        }
        return BigDecimal.ZERO;
    }

    public int calculateNumbersOfMonthsFromStart(LocalDate startDate){
        Period diff = Period.between(startDate, LocalDate.now());
        int years = diff.getYears();
        int months = diff.getMonths();
        return years * 12 + months;
    }
}
