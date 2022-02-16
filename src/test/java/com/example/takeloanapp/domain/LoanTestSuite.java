package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import com.example.takeloanapp.repository.LoanRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
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
    public Loans loanTestOne = new Loans(28, 8, BigDecimal.TEN);


    @Test
    void addLoanTest(){
        // G
        Loans resultLoans =  loanRepo.save(loanTestOne);
        // W
        List<Loans> loansList =  loanRepo.findAll();
        // T
        System.out.println("loan id: " + resultLoans.getId());
        assertEquals(1, loansList.size());
//        clenup
        loanRepo.findById(resultLoans.getId());
        loanRepo.deleteById(resultLoans.getId());
//        loanRepo.deleteById(211L);
//        loanRepo.deleteAll();
    }

    @Test
    void findByIdTest(){
//        G W
        Loans loansFoundById =  loanRepo.save(loanTestOne);

//        T
        loanRepo.findById(loansFoundById.getId());
        System.out.println("Loan Id " + loansFoundById.getId()  + " period + " + loansFoundById.getPeriodInMonth());
        assertEquals(BigDecimal.TEN, loansFoundById.getLoanAmount());
    }

    @Test
    void getAllloanTest(){

        Loans loanTestSecond = new Loans(28, 8, BigDecimal.TEN);

        Loans loOne =  loanRepo.save(loanTestSecond);
        Loans loSec =  loanRepo.save(loanTestOne);
        List<Loans> loansList = loanRepo.findAll();
        System.out.println("SIZE: " + loansList.size());
        assertEquals(2,loansList.size());

        // clen up
//        loanRepo.deleteAll();
        loanRepo.deleteById(loOne.getId());
        loanRepo.deleteById(loSec.getId());
    }

    @Test
    void removeLoanTest(){
        // G W
        Loans loanToRemove = loanRepo.save(loanTestOne);
        List<Loans> loansList = loanRepo.findAll();

        loanRepo.deleteById(loanToRemove.getId());
        List<Loans> loansListAfter = loanRepo.findAll();
        // T
        System.out.println( "before: " + loansList.size());
        System.out.println( "After: " + loansListAfter.size());

        assertEquals(loansList.size() - 1, loansListAfter.size());
    }
}