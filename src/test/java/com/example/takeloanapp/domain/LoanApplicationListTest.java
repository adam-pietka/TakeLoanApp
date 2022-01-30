package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoanApplicationListTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanApplicationListRepository loanApplicationListRepository;

    public Customer kazimierzTest = new Customer("Kazimierz", "Wielki", "+48 707 505 908", "Wawelska", "85/58", "55-885", "Kraków", "74120416713", "PASSPORT", "aa858585", "mail@wp.pl");
    public LoanApplicationsList appTest01 = new LoanApplicationsList( kazimierzTest, "B2B", BigDecimal.valueOf(2500.05), "SeniorConsult s.a.", "4926877058", "ul. Morska 5", "+48 101 202 303", BigDecimal.ZERO, BigDecimal.valueOf(5005.0), 10, "PL78964709874964709554519416");

    @Test
    void saveNewLoanApplication(){
        // G //W
        Customer custOne = customerRepository.save(kazimierzTest);
        LoanApplicationsList loanAppTest =  loanApplicationListRepository.save(appTest01);
        System.out.println(" + " + loanAppTest.getId()  + " " + loanAppTest.getIncomeAmount());

        // T
        assertEquals(1, loanApplicationListRepository.findAll().size());

        // clenup
        loanApplicationListRepository.deleteAll();
        customerRepository.deleteAll();
    }

    @Test
    void getAllLoanApplication(){

    }
}