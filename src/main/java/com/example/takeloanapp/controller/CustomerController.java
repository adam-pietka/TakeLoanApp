package com.example.takeloanapp.controller;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.domain.Customer;
import com.example.takeloanapp.domain.dto.CustomerDto;
import com.example.takeloanapp.mapper.CustomerMapper;
import com.example.takeloanapp.repository.CustomerRepository;
import com.example.takeloanapp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/takeLoan/customers")
public class CustomerController {

    @Autowired
    private CustomerMapper mapper;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CustomerRepository customerRepository;

    @PostMapping(value = "createCustomer", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createCustomer(@RequestBody CustomerDto customerDto) throws CustomerNotFoundException {
        Customer customer = mapper.mapToCustomer(customerDto);
        boolean customerHasMandatoryData = customerService.validateCustomerDataBeforeSaveInDb(customer);

        if (customerService.checkThatCustomerExistInDb(customer)){
            if (customerHasMandatoryData){
                customerService.saveUser(customer);
            }
        }
    }

    @GetMapping(value = "getCustomerByInternalId")
    public CustomerDto getCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {
        Customer customer = customerService.getCustomerById(customerId).orElseThrow(() -> new CustomerNotFoundException("Customer is not exist in data base."));
        return mapper.mapToCustomerDto(customer);
    }

    @GetMapping(value = "getAllCustomes")
    public List<CustomerDto> getAllCustomers(){
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
        boolean answer = false;

        if (customerService.getCustomerById(customerId).isPresent()){
        customerService.deleteUser(customerId);
        answer = true;
        }
        return answer;
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