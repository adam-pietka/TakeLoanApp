package com.example.takeloanapp.controller;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.dto.CustomerDto;
import com.example.takeloanapp.mail.MailContentTemplates;
import com.example.takeloanapp.mapper.CustomerMapper;
import com.example.takeloanapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/takeLoan/customers")
public class CustomerController {
    private static final String SUBJECT = "Dear customer, we just has been created in our company.";
    private static final String MESSAGE = "Dear customer, \nWelcome in our company.\nBest regards,\nBest4 IT";

    @Autowired
    private MailContentTemplates mailContentTemplates;

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private CustomerService customerService;

    @PostMapping(value = "createCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
        Customer customer = mapper.mapToCustomer(customerDto);
        boolean customerHasMandatoryData = customerService.validateCustomerDataBeforeSaveInDb(customer);

        if (customerService.checkThatCustomerExistInDb(customer)){
            if (customerHasMandatoryData){
                customerService.saveUser(customer);
                mailContentTemplates.sentMailForNewCustomer(customer);
            }
        }
    }

    @GetMapping(value = "getCustomerByInternalId")
    public CustomerDto getCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer is not exist in data base."));
        return mapper.mapToCustomerDto(customer);
    }

    @GetMapping(value = "getAllCustomes")
    public List<CustomerDto> getAllCustomers() throws CustomerNotFoundException {
        List<Customer> customerList = customerService.getAllCustomers();
        return mapper.mapToCustomerDtoList(customerList);
    }

    @PutMapping(value = "updateCustomer")
    public CustomerDto updateUser(@RequestBody CustomerDto customerDto ) throws CustomerNotFoundException {

        if (customerService.getCustomerById(customerDto.getId()).isPresent()){
            Customer customer = mapper.mapToCustomer(customerDto);
            Customer savedCustomer = customerService.saveUser(customer);
            return mapper.mapToCustomerDto(savedCustomer);
        }
        throw  new CustomerNotFoundException("UPDATE customer operation  is aborted, customer is not exist in DB.");
    }

    @DeleteMapping(value = "removeCustomerFromDB")
    public boolean deleteCustomer(@RequestParam Long customerId) throws CustomerNotFoundException{
        if (customerService.getCustomerById(customerId).isPresent()){
            customerService.deleteUser(customerId);
            return  true;
        }
        return false;
    }

    @GetMapping(value = "getCustomerByPesel")
    public CustomerDto getCustomerByPeselNumber(@RequestParam String peselNumber) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerByPeselNumber(peselNumber).orElseThrow(() -> new CustomerNotFoundException("Customer PESEL number is not exist in data base."));
        return mapper.mapToCustomerDto(customer);
    }

    @GetMapping(value = "getCustomerByPersonalId")
    public CustomerDto getCustomerByCustomerId(@RequestParam String idNumber) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerByPersonalId(idNumber).orElseThrow(() -> new CustomerNotFoundException("Customer ID NUMBER number is not exist in data base."));
        return mapper.mapToCustomerDto(customer);
    }
}