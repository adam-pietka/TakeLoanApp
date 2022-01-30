package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerTestSuite {

    @Autowired
    private CustomerRepository customerRepository;
    public Customer testCustomer01 = new Customer("Horacy", "surnameTest", "+48 800-700-500", "streetNarrow", "88/458", "55-885", "City", "858-88-88", "PASSPORT", "aa858585", "best4it.ap@gmail.com");

    @Test
    void testSaveCustomer(){
        // G
        Customer customerTest02 = new Customer("Heniek", "Test", "+58 8658", null, null, null, null, null, null, null, null);
        // W
        Customer custOne =  customerRepository.save(testCustomer01);
        Customer custSecond =  customerRepository.save(customerTest02);
        System.out.println(" customerRepository.findAll().size(): " +  customerRepository.findAll().size() + " *********************");
        System.out.println("cust id: " + custOne.getId() );
        System.out.println("cust id: " + custSecond.getId() );
        // T
        assertEquals(customerRepository.findAll().size(), customerRepository.findAll().size());

        // clenup
        customerRepository.deleteById(custOne.getId());
        customerRepository.deleteById(custSecond.getId());
    }

    @Test
    void deleteCustomerById(){
        // W
        customerRepository.save(testCustomer01);
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("All saved customer counter: " + customerRepository.findAll().size());
        // G
        customerRepository.deleteById(testCustomer01.getId());
        List<Customer> customerListAfter = customerRepository.findAll();
        // T
        assertEquals(customerList.size() - 1, customerListAfter.size() );
    }

    @Test
    void findCustomerById(){
        // W
         Customer testCust =  customerRepository.save(testCustomer01);
        // G
         Optional<Customer> expectedCustomer = customerRepository.findById(testCust.getId());
        // T
        assertEquals("Horacy", expectedCustomer.get().getName());
        // clenup
        customerRepository.deleteAll();
    }
}
