package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerService.class);

    @Autowired
    private CustomerRepository repository;

    public Customer saveUser(Customer customer){
        Customer savedCustomer = customer;
        if (customer.getRegistrationDate() == null){
            savedCustomer.setRegistrationDate(LocalDate.now());
            savedCustomer.setActive(true);
        }
        LOGGER.info("new customer has been saved in data base,"  + ", eMail: " + savedCustomer.getMailAddress());
        return repository.save(savedCustomer);
    }

    public Optional<Customer> getCustomerById(Long customerId) throws CustomerNotFoundException{
        if (repository.findById(customerId).isEmpty()){
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like: NAME.");
        }
        return repository.findById(customerId);
    }

    public List<Customer> getAllCustomers()throws  CustomerNotFoundException {
        if (repository.findAll().isEmpty()){
            throw new CustomerNotFoundException("Table do not contain any records.");
        }
        return repository.findAll();
    }

    public void deleteUser(Long customerId){
        repository.deleteById(customerId);
    }

    public Optional<Customer> getCustomerByPeselNumber(String peselNumber){
        return repository.findByPeselNumber(peselNumber);
    }

    public Optional<Customer> getCustomerByPersonalId(String idNumber){
        return repository.findByIdNumber(idNumber);
    }


    public boolean checkThatCustomerExistInDb(Customer customer) throws  CustomerNotFoundException{
        if (repository.findByPeselNumber(customer.getPeselNumber()).isPresent()){
            throw new CustomerNotFoundException("\n" +
                    "Customer with the same PESEL NUMBER is existing in data base.\n" +
                    "It's not possible to create new account, pleas update existing.");
        }
        return true;
    }

}
