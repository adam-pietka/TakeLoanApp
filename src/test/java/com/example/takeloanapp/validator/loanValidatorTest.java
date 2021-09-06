package com.example.takeloanapp.validator;


import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.LoanApplicationsList;
import com.example.takeloanapp.domain.Loans;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class loanValidatorTest {

    public Customer testCustomer01 = new Customer("name", "surname", "+48 858888", "street", "lok. 58/58", "55-885", "City", "85031315756", "idTyp","idNumber", "mail");
    public BigDecimal amount = new BigDecimal("25");
    public LocalDate dataOf = LocalDate.of(2021, 4,25 );
//
    public LoanApplicationsList appTest01 = new LoanApplicationsList( testCustomer01, null, "self", amount, "SDC comp", "858-858-858", "emp address", "+45 7785", amount, amount, 24, false, dataOf, dataOf, "PL85 2525 0000 0000 7474", false, dataOf, false);
    public LoanApplicationsList appTest02 = new LoanApplicationsList( testCustomer01, null, "self some", amount, "XYZ comp", "858-858-858", "emp address", "+45 7785", amount, amount, 24, false, dataOf, dataOf, "PL85 0005 3300 3003 0024", false, dataOf, false);

    public LoanApplicationValidator validatorRate;

    @Test
    void testAge(){
//        System.out.println(  validatorRate.isAgeIncorrect(testCustomer01) + " ============");

        System.out.println("*****: " + testCustomer01.getPeselNumber());
        System.out.println("////\n" +
                testCustomer01.getPeselNumber().substring(0, 4) + "\n" +
                testCustomer01.getPeselNumber().substring(2, 3));
    }

    @Test
    void testIncome(){
//        validatorRate.isIncomeToLow(appTest01);
//        System.out.println(validatorRate.isIncomeToLow(appTest01) + " ******************");
    }
}
