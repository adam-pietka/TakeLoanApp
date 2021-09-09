package com.example.takeloanapp.service;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository repository;

    public Customer saveUser(Customer customer){
        Customer savedCustomer = customer;
        if (customer.getRegistrationDate() == null){
        savedCustomer.setRegistrationDate(LocalDate.now());
        savedCustomer.setActive(true);
        }
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

    public  boolean validateCustomerDataBeforeSaveInDb(Customer customer) throws  CustomerNotFoundException{

        if (customer.getName().isEmpty() || customer.getName().isBlank() || customer.getSurname().isEmpty() ||  customer.getSurname().isBlank() || customer.getSurname() == null){
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like: NAME.");
        }
        if (customer.getPeselNumber().isEmpty() || customer.getPeselNumber().isBlank()){
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like: PESEL NUMBER.");
        }
        if (customer.getIdType().isEmpty() || customer.getIdType().isBlank()){
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like ID TYPE.");
        }
        if (customer.getIdNumber().isEmpty() || customer.getIdNumber().isBlank()){
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like ID NUMBER.");
        }
        if (customer.getPhoneNumber().isEmpty() || customer.getPhoneNumber().isBlank()){
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like PHONE NUMBER.");
        }

        return true;
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
