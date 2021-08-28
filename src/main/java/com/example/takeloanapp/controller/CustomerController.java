package com.example.takeloanapp.controller;

import com.example.takeloanapp.controller.exception.CustomerNotFoundException;
import com.example.takeloanapp.domain.Loans;
import com.example.takeloanapp.domain.dto.CustomerDto;
import com.example.takeloanapp.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/takeLoan/customers")
public class CustomerController {

    @Autowired
    private CustomerMapper customerMapper;

    @GetMapping(value = "getCustomer")
    public CustomerDto getCustomer(@RequestParam Long customerId) throws CustomerNotFoundException {

        List<Long> loansListTMP = new ArrayList<>();

        return new CustomerDto(
                1L,
                "name",
                "sud",
                "dsfs",
                "sd",
                "sdsd",
                "fs",
                "sfdsd",
                "sfdsdf",
                "sdf",
                true,
                LocalDate.of(2021, 5, 5),
                LocalDate.of(2021, 5, 5),
                loansListTMP
        );
    }


}
