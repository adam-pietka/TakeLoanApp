package com.example.takeloanapp.calculator;

import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ArrearsCalculatorTestSuite {

    @Autowired
    private  ArrearsCalculator arrearsCalc;

    @Test
    void calculatingArrearsAmountTest(){
        //G W
        BigDecimal result =  arrearsCalc.calculatingArrearsAmount(5, BigDecimal.valueOf(10000));
        // T
        assertEquals(BigDecimal.valueOf(27.50).setScale(2), result);
    }

    @Test
    void checkInstallmentsDueTest(){
        //G W
        Loans loansTest = new Loans(1L, null, 12, LocalDate.of(2022,01,01), LocalDate.of(2023,01,01), 28, BigDecimal.valueOf(10_000), null, null, BigDecimal.valueOf(300), BigDecimal.valueOf(800), null, null, true, null, true, 0, null, null, null, null, false, null);
        BigDecimal result = null;
        try {
            result = arrearsCalc.checkInstallmentsDue(loansTest);
        } catch (LoanNotFoundException e) {
            e.printStackTrace();
        }
        // T
        assertEquals(BigDecimal.valueOf(1100.00).setScale(2), result);
    }

//    calculateNumbersOfMonthsFromStart

    @Test
    void calculateNumbersOfMonthsFromStartTest(){
        //G W
        int result =  arrearsCalc.calculateNumbersOfMonthsFromStart(LocalDate.of(2021,10,10));
        // T
        assertEquals(4,result);
    }

}
