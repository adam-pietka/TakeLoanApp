package com.example.takeloanapp.service;


import com.example.takeloanapp.controller.LoansController;
import com.example.takeloanapp.controller.exception.LoanNotFoundException;
import com.example.takeloanapp.domain.dto.LoansDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootTest
public class LoanArrearsServiceTestSuite {

    @Autowired
    private LoanArrearsService loanArrearsService;

    @Autowired
    private LoansController loansController;

    @Test
    void checkInstallmentsDueTest(){
        LoansDto loansDto = new LoansDto(
                        1L,
                "productNaLongme",
                48,
                LocalDate.of(2020,01,01),
                LocalDate.of(2025,01,01),
                5,
                new BigDecimal("20000"),
                new BigDecimal("0.2"),
                new BigDecimal("2000"),
                new BigDecimal("230"),
                new BigDecimal("21"),
                null,
                null,
                true,
                LocalDate.of(2020,01,01),
                false,
                0,
                null,
                null,
                151L,
                "PL74808010010000000000000036",
                false,
                new ArrayList<Long>()
        );
        try {
            loansController.registerLoan(loansDto);

        } catch (LoanNotFoundException e) {
            e.printStackTrace();
        }
//        List<LoansDto> loansList = loansController.getAllLoans();
        System.out.printf("test *** START ***");
    }
}