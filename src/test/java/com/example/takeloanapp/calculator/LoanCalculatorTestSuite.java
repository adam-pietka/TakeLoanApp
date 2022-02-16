package com.example.takeloanapp.calculator;

import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.LoanApplicationsList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class LoanCalculatorTestSuite {

    @Autowired
    ArrearsCalculator arrearsCalculator;
    @Autowired
    LoanCalculator loanCalculator;

    private static Customer customerHoracy = new Customer("Horacy", "surnameTest", "+48 800-700-500", "streetNarrow", "88/458", "55-885", "City", "858-88-88", "PASSPORT", "aa858585", "best4it.ap@gmail.com");
    private static LoanApplicationsList loanApp = new LoanApplicationsList( customerHoracy, "Permanent", BigDecimal.TEN, "Test inc.", "858-858-88-88", "Narrow 25, 88-888 NY", "+48 555 222", BigDecimal.ZERO, BigDecimal.valueOf(10000), 12, "56565656565656565" );

    @DisplayName("Test of calculation Monthly Interest Rate")
    @Test
    void calculatingMonthlyInterestRateTest(){
        // G W
        BigDecimal  result = loanCalculator.calculateMonthlyInterestRate();
        // T
        assertEquals(BigDecimal.valueOf(0.016500).setScale(6) ,result);

    }

    @DisplayName("Test of calculation amount of monthly interest")
    @Test
    void monthlyInterest(){
        // G W
        BigDecimal result = loanCalculator.monthlyInterest(loanApp, BigDecimal.valueOf(0.0166));
        // T
        assertEquals(BigDecimal.valueOf(166.00).setScale(2), result);
    }

    @DisplayName("Test of calculation amount of monthly capital")
    @Test
    void monthlyCapitalTest(){
        // G W
        BigDecimal result = loanCalculator.monthlyCapital(loanApp);
        // T
        assertEquals(BigDecimal.valueOf(833.33), result);
    }

    @DisplayName("Test of calculation amount of monthly instalment")
    @Test
    void monthlyinstalmentAmountTest(){
        // G W
        BigDecimal result = loanCalculator.monthlyInstalment(loanApp, BigDecimal.valueOf(0.0165));
        // T
        assertEquals(BigDecimal.valueOf(998.33), result);
    }

    @DisplayName("Test of calculation amount of Total loan payments")
    @Test
    void totalLoanPaymentsTest(){
        // G W
        BigDecimal result = loanCalculator.totalLoanPayments(loanApp, BigDecimal.valueOf(998.33));

        // T
        assertEquals(BigDecimal.valueOf(11979.96), result);
    }
}
