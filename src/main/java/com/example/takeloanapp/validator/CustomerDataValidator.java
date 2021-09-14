package com.example.takeloanapp.validator;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CustomerDataValidator {
    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerDataValidator.class);

    public  boolean validateCustomerDataBeforeSaveInDb(Customer customer) throws CustomerNotFoundException {

        if (customer.getName().isEmpty() || customer.getName().isBlank() || customer.getSurname().isEmpty() ||  customer.getSurname().isBlank() || customer.getSurname() == null){
            LOGGER.warn("Customer has not fill all mandatory fields, like: NAME or SURNAME.");
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like: NAME.");
        }
        if (customer.getPeselNumber().isEmpty() || customer.getPeselNumber().isBlank()){
            LOGGER.warn("Customer has not fill all mandatory fields, like: PESEL NUMBER.");
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like: PESEL NUMBER.");
        }
        if (customer.getIdType().isEmpty() || customer.getIdType().isBlank()){
            LOGGER.warn("Customer has not fill all mandatory fields, like ID TYPE.");
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like ID TYPE.");
        }
        if (customer.getIdNumber().isEmpty() || customer.getIdNumber().isBlank()){
            LOGGER.warn("Customer has not fill all mandatory fields, like ID NUMBER.");
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like ID NUMBER.");
        }
        if (customer.getPhoneNumber().isEmpty() || customer.getPhoneNumber().isBlank()){
            LOGGER.warn("Customer has not fill all mandatory fields, like PHONE NUMBER.");
            throw new CustomerNotFoundException("Customer has not fill all mandatory fields, like PHONE NUMBER.");
        }

        return true;
    }

}
