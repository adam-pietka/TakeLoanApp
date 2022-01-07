package com.example.takeloanapp.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class LoanArrearsTestSuite {

    @Autowired
    private  LoanArrears loanArrears;

    @Test
    void checkInstallmentsDueTest(){
//        LoanArrears loanArrears = new LoanArrears();
        System.out.printf("test");
    }
}