package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoanTestSuite {

    public Customer testCustomer01 = new Customer("name", "s", "85888", "street", "8558/58", "55-885", "City", "858-88-88", "idNumber", "mail");
    private Loans testLoan = new Loans("loanNAme", 12, LocalDate.of(2011,1,1), LocalDate.of(2012,1,1), 22,new BigDecimal("150500"), new BigDecimal("2.1"), new BigDecimal("4.5"), new BigDecimal("454.5"), new BigDecimal("805.5"), true, LocalDate.of(2011, 1, 1), false, 0, new BigDecimal("4.5"), new BigDecimal("4.5"),testCustomer01 , "00 8585 0000 1111 1111 1111 111 ", false);
    private Loans testLoanSecond = new Loans("loanName2", 15, LocalDate.of(2011,1,1), LocalDate.of(2012,1,1), 22,new BigDecimal("150500"), new BigDecimal("2.1"), new BigDecimal("4.5"), new BigDecimal("454.5"), new BigDecimal("805.5"), true, LocalDate.of(2011, 1, 1), false, 0, new BigDecimal("4.5"), new BigDecimal("4.5"),testCustomer01 , "00 8585 0000 1111 1111 1111 111 ", false);

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void addLoan(){
        // G
        System.out.println( " TEST **************  " + loanRepository.findAll().size());

        // W
        customerRepository.save(testCustomer01);
        loanRepository.save(testLoan);
        loanRepository.save(testLoanSecond);

        System.out.println( " TEST **************  " + loanRepository.findAll().size());

//        loanRepository.deleteById(testLoan.getId());

        // T
        System.out.println( " TEST **************  " + loanRepository.findAll().size());

        //clenUp
        loanRepository.deleteAll();
        customerRepository.deleteById(testCustomer01.getId());
        System.out.println( " TEST ************** CUST  " + customerRepository.findAll().size());
        System.out.println( " TEST ************** LOAN " + loanRepository.findAll().size());
    }
}