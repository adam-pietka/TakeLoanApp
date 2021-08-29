package com.example.takeloanapp.controller;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.domain.dto.CustomerDto;
import com.example.takeloanapp.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/takeLoan/loan")
public class LoansController {

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping(value = "findLoan")
    public CustomerDto getCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {

        List<Long> loansListTMP = new ArrayList<>();
        return new CustomerDto(
                1L,
                "name",
                "surname",
                "+48 558585",
                "street JAVA",
                "858/55",
                "00-855",
                "City",
                "pesel 8585858",
                "NIP 858-965",
                "passport",
                "pass85858",
                "mail@sds",
                true,
                LocalDate.of(2021, 5, 5),
                LocalDate.of(2021, 5, 5),
                loansListTMP
        );
    }

    @GetMapping(value = "getAllCustomes")
    public List<CustomerDto> getAllCustomers(){
        List<CustomerDto> customerDtoList = new ArrayList<>();
        CustomerDto customerDto1 = new CustomerDto();
        List<Long> loansListTMP = new ArrayList<>();
        CustomerDto customerDto2 = new CustomerDto(
                1L,
                "name",
                "surname",
                "+48 558585",
                "street JAVA",
                "858/55",
                "00-855",
                "City",
                "pesel 8585858",
                "NIP 858-965",
                "passport",
                "pass85858",
                "mail@sds",
                true,
                LocalDate.of(2021, 5, 5),
                LocalDate.of(2021, 5, 5),
                loansListTMP
        );

        customerDtoList.add(customerDto1);
        customerDtoList.add(customerDto2);
        return customerDtoList;
    }

    @PutMapping(value = "updateCustomer")
    public CustomerDto updateUser(@RequestBody CustomerDto customerDto ) throws CustomerNotFoundException {

        if (customerDto.getId() == 0){
            return new CustomerDto(
                    1L,
                    "name UPDATE",
                    "surname UDATE",
                    "+48 558585",
                    "street JAVA",
                    "858/55",
                    "00-855",
                    "City",
                    "pesel 8585858",
                    "NIP 858-965",
                    "passport",
                    "pass85858",
                    "mail@sds",
                    true,
                    LocalDate.of(2021, 5, 5),
                    LocalDate.of(2021, 5, 5),
                    new ArrayList<Long>(0)
            );
        } else {
            throw new CustomerNotFoundException("Please check customer number, customer is not exist in data base.");
        }
    }

    @DeleteMapping(value = "removeCustomerFromDB")
    public boolean deleteCustomer(@RequestParam Long customerId) throws CustomerNotFoundException{

        if (customerId == 100){
            throw new  CustomerNotFoundException("Customer not found, please check customer ID.");
        } else {
            return true;
        }
    }
}