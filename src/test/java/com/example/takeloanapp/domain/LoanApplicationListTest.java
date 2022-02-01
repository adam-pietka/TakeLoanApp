package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.repository.LoanApplicationListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class LoanApplicationListTest {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private LoanApplicationListRepository loanApplicationListRepository;

    public Customer kazimierzTest = new Customer("Kazimierz", "Wielki", "+48 707 505 908", "Wawelska", "85/58", "55-885", "Kraków", "74120416713", "PASSPORT", "aa858585", "testLoanApp@wp.pl");
    public LoanApplicationsList appTest01 = new LoanApplicationsList( kazimierzTest, "B2B", BigDecimal.valueOf(2500.05), "SeniorConsult s.a.", "4926877058", "ul. Morska 5", "+48 101 202 303", BigDecimal.ZERO, BigDecimal.valueOf(5005.0), 10, "PL78964709874964709554519416");

    @Test
    void saveNewLoanApplication(){
        // G
        Customer bogusTest = new Customer("Bogumił", "Szlachetny", "+48 707 505 908", "Gnieznieńska", "7/8", "55-885", "Opole", "74120416714", "PASSPORT", "aa777775", "testLoanApp@wp.pl");
        // W
        customerRepository.save(bogusTest);
        Optional<Customer> tmpCust = customerRepository.findById(bogusTest.getId());
        LoanApplicationsList loanApplicationsList = new LoanApplicationsList( tmpCust.get(), "B2B", BigDecimal.valueOf(5_500.05), "SeniorConsult s.a.", "4926877058", "ul. Morska 5", "+48 101 202 303", BigDecimal.ZERO, BigDecimal.valueOf(5005.0), 10, "PL78964709874964709554519416");
        LoanApplicationsList loanAppTest =  loanApplicationListRepository.save(loanApplicationsList);
        System.out.println("Loan application: loanAppTest.getId() : " + loanAppTest.getId()  + " getIncomeAmount: " + loanAppTest.getIncomeAmount());

        // T
        assertEquals(1 , loanApplicationListRepository.findAll().size());

        // clenup
        loanApplicationListRepository.deleteById(loanAppTest.getId());
        customerRepository.deleteById(bogusTest.getId());

    }

    @Test
    void getLoanApplicationById(){
        // G
        loanApplicationListRepository.save(appTest01);
        // W
        Optional<LoanApplicationsList> result = loanApplicationListRepository.findById(appTest01.getId());
        // T
        assertEquals(appTest01.getId(), result.get().getId());

        // clenup
        loanApplicationListRepository.findById(appTest01.getId());
    }

    @Test
    void getAllLoanApplications(){
        // G
        customerRepository.save(kazimierzTest);
        LoanApplicationsList appTest02 = new LoanApplicationsList( kazimierzTest, "PERMANENT", BigDecimal.valueOf(8_500.05), "Pepsico s.a.", "4926877058", "ul. Morska 5", "+48 101 202 303", BigDecimal.ZERO, BigDecimal.valueOf(255_005.0), 25, "PL78964709874964709554519416");
        loanApplicationListRepository.save(appTest01);
        loanApplicationListRepository.save(appTest02);
        // W
        List<LoanApplicationsList> listsResult =  loanApplicationListRepository.findAll();
        // T
        assertEquals(2, listsResult.size());

        //        clenup
        for (LoanApplicationsList n: listsResult) {
            loanApplicationListRepository.deleteById(n.getId());
        }
        customerRepository.deleteById(kazimierzTest.getId());
    }

    @Test
    void deleteLoanAppById(){
        // G
        customerRepository.save(kazimierzTest);
        loanApplicationListRepository.save(appTest01);

        // W
        List<LoanApplicationsList> listAfterSave =  loanApplicationListRepository.findAll();
        assertEquals(1, listAfterSave.size());
        loanApplicationListRepository.deleteById(appTest01.getId());
        customerRepository.deleteById(kazimierzTest.getId());
        List<LoanApplicationsList> listAfterDelete =  loanApplicationListRepository.findAll();
        // T
        assertEquals(0, listAfterDelete.size());
    }
}