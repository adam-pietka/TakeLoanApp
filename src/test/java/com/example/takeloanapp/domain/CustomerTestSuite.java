package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.*;

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
        customerRepository.deleteById(testCustomer01.getId());
        customerRepository.deleteById(customerTest02.getId());
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

    @Test
    public void findCustomerById(){
        // W
         Customer customerTmp =  customerRepository.save(testCustomer01);
          customerRepository.findById(customerTmp.getId());

          assertTrue(customerRepository.findById(customerTmp.getId()).isPresent());


        List<Customer> customerList = customerRepository.findAll();
        System.out.println("All saved customer counter:" + customerRepository.findAll().size());

        // G
        customerRepository.deleteById(testCustomer01.getId());
        List<Customer> customerListAfter = customerRepository.findAll();

        // T
        assertEquals(customerList.size() - 1, customerListAfter.size() );

        // CLEAN UP
        customerList.stream()
                .forEach(e->customerRepository.deleteById(e.getId()));
//                .forEach(o-> System.out.println( "cust: " + o.getId() + " name " +o.getName() ))


    }
}
