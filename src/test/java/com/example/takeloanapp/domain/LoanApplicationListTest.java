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
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoanApplicationListTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private LoanApplicationListRepository loanApplicationListRepository;

    public Customer testCustomer01 = new Customer("name", "s", "85888", "street", "8558/58", "55-885", "City", "858-88-88", "idNumber", "mail");
    public BigDecimal amount = new BigDecimal("25");
    public LocalDate dataOf = LocalDate.of(2021, 4,25 );

    public LoanApplicationsList appTest01 = new LoanApplicationsList( testCustomer01, null, "self", amount, "SDC comp", "858-858-858", "emp address", "+45 7785", amount, amount, 24, false, dataOf, dataOf, "PL85 2525 0000 0000 7474", false, dataOf, false);
    public LoanApplicationsList appTest02 = new LoanApplicationsList( testCustomer01, null, "self some", amount, "XYZ comp", "858-858-858", "emp address", "+45 7785", amount, amount, 24, false, dataOf, dataOf, "PL85 0005 3300 3003 0024", false, dataOf, false);

    @Test
    void newLoanApplication(){
        // G
        LoanApplicationsList appFirst = new LoanApplicationsList( testCustomer01, null, "self some", amount, "XYZ comp", "858-858-858", "emp address", "+45 7785", amount, amount, 24, false, dataOf, dataOf, "PL85 0005 3300 3003 0024", false, dataOf, false);

        // W
        customerRepository.save(testCustomer01);
        loanApplicationListRepository.save(appFirst);

        List<LoanApplicationsList> loanApplicationsListList =  loanApplicationListRepository.findAll();

        // T
        System.out.println("*********" + loanApplicationsListList.size());
        loanApplicationsListList.stream()
                .forEach(i-> System.out.println("ID: " + i.getId() + "  ========="));

        assertEquals(1,loanApplicationsListList.size());

        // Clen-up
        customerRepository.deleteById(testCustomer01.getId());
        loanApplicationListRepository.deleteById(appFirst.getId());

    }

    @Test
    void getAllLoanApplication(){
        // G
        customerRepository.save(testCustomer01);
        loanApplicationListRepository.save(appTest01);
        loanApplicationListRepository.save(appTest02);
        // W

        List<LoanApplicationsList> loanApplicationsListList =  loanApplicationListRepository.findAll();

        // T
        System.out.println("*********" + loanApplicationsListList.size());
        loanApplicationsListList.stream()
                .forEach(i-> System.out.println("ID: " + i.getId() + "  ========= " + i.getIncomeAmount()));

        assertEquals(1,loanApplicationsListList.size());

        // Clen-up
        customerRepository.deleteById(testCustomer01.getId());
        loanApplicationListRepository.deleteAll();
    }
}