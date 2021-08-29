package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerTestSuite {

    public Customer testCustomer01 = new Customer("name", "s", "85888", "street", "8558/58", "55-885", "City", "858-88-88", "idNumber", "mail");

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void testSaveCustomer(){
        // G
        Customer customerTest02 = new Customer();
        // W
        customerRepository.save(testCustomer01);
        customerRepository.save(customerTest02);
        // T
        assertEquals(2, customerRepository.findAll().size());

        // clenup
        customerRepository.deleteAll();

    }

    @Test
    public void deleteCustomerById(){
        // W
        customerRepository.save(testCustomer01);
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("All saved customer counter:" + customerRepository.findAll().size());

        // G
        customerRepository.deleteById(testCustomer01.getId());
        List<Customer> customerListAfter = customerRepository.findAll();

        // T
        assertEquals(customerList.size() - 1, customerListAfter.size() );
    }
}