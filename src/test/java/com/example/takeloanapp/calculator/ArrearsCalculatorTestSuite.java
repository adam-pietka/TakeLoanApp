package com.example.takeloanapp.calculator;

import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.LoanAmountAndDataDTO;
import com.example.takeloanapp.domain.dto.LoanCashFlowDto;
import com.example.takeloanapp.repository.LoanRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class ArrearsCalculatorTestSuite {

    @Autowired
    private  ArrearsCalculator arrearsCalc;
    @Autowired
    private LoanRepository loanRepo;

    private static Loans loansTest = new Loans(1L, null, 12, LocalDate.of(2022,01,01), LocalDate.of(2023,01,01), 28, BigDecimal.valueOf(10_000), null, null, BigDecimal.valueOf(300), BigDecimal.valueOf(800), null, null, true, null, true, 0, null, null, null, null, false, null);

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

    @DisplayName("Test of sum all repayment of instalments - empty list")
    @Test
    void sumAmountOfPayedInstallmentsTestOfEmptyList(){
//        G
        List<LoanCashFlowDto> loanCashFlowDtosList = new ArrayList<>();
//        W
        LoanAmountAndDataDTO loanAmountAndDataDTO = arrearsCalc.sumAmountOfPayedInstallments(loanCashFlowDtosList);
//        T
        assertEquals(BigDecimal.ZERO, loanAmountAndDataDTO.getBigDecimalAmount());
        assertEquals(LocalDateTime.of(-999999999,01,01,00,00), loanAmountAndDataDTO.getLocalDateTime());
    }

    @DisplayName("Test of sum all repayment of instalments - list with records")
    @Test
    void sumAmountOfPayedInstallmentsTestOfList(){
//        G
        Loans savedLoan = loanRepo.save(loansTest);
        LoanCashFlowDto loanCashFlSec= new LoanCashFlowDto(null, savedLoan.getId(), BigDecimal.valueOf(400),  BigDecimal.valueOf(400), null, true, false, null, LocalDateTime.of(2022,02,10,22,00), null);
        LoanCashFlowDto loanCashFlOne = new LoanCashFlowDto(null, savedLoan.getId(), BigDecimal.valueOf(500),  BigDecimal.valueOf(500), null, true, false, null, LocalDateTime.of(2022,02,01,22,00), null);
        List<LoanCashFlowDto> loanCashFlowDtosList = new ArrayList<>();
        loanCashFlowDtosList.add(loanCashFlOne);
        loanCashFlowDtosList.add(loanCashFlSec);
//        W
        LoanAmountAndDataDTO loanAmountAndDataDTO = arrearsCalc.sumAmountOfPayedInstallments(loanCashFlowDtosList);
        System.out.println("X - " + loanAmountAndDataDTO.getBigDecimalAmount() + "\n" +
                "Y - " + loanAmountAndDataDTO.getLocalDateTime());
//        T
        assertEquals(BigDecimal.valueOf(900), loanAmountAndDataDTO.getBigDecimalAmount());
        assertEquals(LocalDateTime.of(2022,02,10,22,00), loanAmountAndDataDTO.getLocalDateTime());
//        clen up
        loanRepo.deleteById(savedLoan.getId());
    }

    @Test
    void calculatingNumberOfArrearsDaysTest(){
//        G W
        LoanAmountAndDataDTO loanDto = new LoanAmountAndDataDTO(BigDecimal.valueOf(55.55), LocalDateTime.of(2020,02,01,10,00));
        int  result = arrearsCalc.calculatingNumberOfArrearsDays(loanDto);
        System.out.println("REsult is " + result);

//        T
        assertEquals(746,result);
    }

    @DisplayName("Test of sum all payed arrears - empty list")
    @Test
    void sumOfArrearsTestOfEmptyList(){
//      G W
        List<LoanCashFlowDto> paymentsList = new ArrayList<>();
        BigDecimal result =  arrearsCalc.sumOfArrears(paymentsList);
//        T
        assertEquals(BigDecimal.ZERO, result);
    }

    @DisplayName("Test of sum all payed arrears - list with records")
    @Test
    void sumOfArrearsTestOfList(){
//      G
        LoanCashFlowDto loanCashFlSec= new LoanCashFlowDto(null, 1L, BigDecimal.valueOf(400),  null, BigDecimal.valueOf(300), false, true, null, LocalDateTime.of(2022,02,10,22,00), null);
        LoanCashFlowDto loanCashFlOne = new LoanCashFlowDto(null, 1L, BigDecimal.valueOf(500),  null,BigDecimal.valueOf(205), true, false, null, LocalDateTime.of(2022,02,01,22,00), null);
        List<LoanCashFlowDto> paymentsList = new ArrayList<>();
        paymentsList.add(loanCashFlOne);
        paymentsList.add(loanCashFlSec);
//        W
        BigDecimal result =  arrearsCalc.sumOfArrears(paymentsList);
        System.out.println( "result: " + result);
//        T
        assertEquals(BigDecimal.valueOf(505), result);
    }
}
