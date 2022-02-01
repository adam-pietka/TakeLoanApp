package com.example.takeloanapp.domain;

import com.example.takeloanapp.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;


//@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class CustomerTestSuite {

    @Autowired
    private CustomerRepository customerRepository;

    public Customer testCustomer01 = new Customer("Horacy", "surnameTest", "+48 800-700-500", "streetNarrow", "88/458", "55-885", "City", "858-88-88", "PASSPORT", "aa858585", "best4it.ap@gmail.com");

    @Test
    void testSaveCustomerAp(){
        // G W
         customerRepository.save(testCustomer01);
        // T
        assertEquals(1L, customerRepository.findAll().size());

        // clenup
        customerRepository.deleteById(testCustomer01.getId());
    }

    @Test
    void deleteCustomerById(){
        // W
        customerRepository.save(testCustomer01);
        List<Customer> customerList = customerRepository.findAll();
        System.out.println("All saved customer counter: " + customerRepository.findAll().size());
        assertEquals(1L, customerList.size() );
        // G
        customerRepository.deleteById(testCustomer01.getId());
        List<Customer> customerListAfter = customerRepository.findAll();
        // T
        assertEquals(0L, customerListAfter.size() );
    }

    @Test
    void findCustomerById(){
        // W
        customerRepository.save(testCustomer01);
        // G
        Optional<Customer> tmpCust =  customerRepository.findById(testCustomer01.getId());
        // T
        assertEquals("Horacy", tmpCust.get().getName());
        // clenup
        customerRepository.deleteById(tmpCust.get().getId());
    }
}
