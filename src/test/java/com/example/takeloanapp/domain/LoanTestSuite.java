package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import com.example.takeloanapp.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional
public class LoanTestSuite {

    @Autowired
    private LoanRepository loanRepo;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanApplicationListRepository loanApplRepo;

    public Customer markTest = new Customer("Mark", "Spilberh", "+44 858 855 699", "Shallow", "558", "88-8855", "Detroid", "71091501370", "Personal ID", "APR977995", "test@test.com");
    public LoanApplicationsList appTest01 = new LoanApplicationsList( markTest, "B2B", BigDecimal.valueOf(2500.05), "SeniorConsult s.a.", "4926877058", "ul. Morska 5", "+48 101 202 303", BigDecimal.ZERO, BigDecimal.valueOf(5005.0), 10, "PL78964709874964709554519416");


    @Test
    void addLoanTest(){
        // G
        Loans loanTest = new Loans(24, 8, BigDecimal.TEN);
        loanRepo.save(loanTest);
        // W
        List<Loans> loansList =  loanRepo.findAll();
        // T
        assertEquals(1, loansList.size());
//        clenup
        loanRepo.deleteById(loanTest.getId());
    }

    @Test
    void findByIdTest(){
//        G
//        W
//        T

    }

    @Test
    void getAllloanTest(){

    }

    @Test
    void removeLoanTest(){

    }
}

/*
    Optional<Loans> findById(Long id);

    @Override
    List<Loans> findAll();

    void deleteById(Long id);
*/