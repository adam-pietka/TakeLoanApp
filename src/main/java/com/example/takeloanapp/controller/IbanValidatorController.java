package com.example.takeloanapp.controller;


import com.example.takeloanapp.client.IbanClient;
import com.example.takeloanapp.domain.dto.IbanValidatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/takeLoan/iban")
@RequiredArgsConstructor
public class IbanValidatorController {

    private final IbanClient ibanClient;

    @GetMapping("getIbanValidator")
    public void getIbanValidator(){
        IbanValidatorDto ibanValidatorDtos = ibanClient.getIbanValidator();
        System.out.println(ibanValidatorDtos + " *******************");
    }

}
