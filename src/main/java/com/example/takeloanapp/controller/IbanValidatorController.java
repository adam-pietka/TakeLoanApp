package com.example.takeloanapp.controller;


import com.example.takeloanapp.client.IbanClient;
import com.example.takeloanapp.domain.dto.IbanDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/takeLoan/iban")
@RequiredArgsConstructor
public class IbanValidatorController {

    private final IbanClient ibanClient;

    @GetMapping("getIbanValidator")
    public IbanDto getIbanValidator(@RequestParam String ibanNumber){
        IbanDto ibanValidated = ibanClient.getIbanValidator(ibanNumber);
        return ibanValidated;
    }

    @GetMapping("getIbanCalculator")
    public IbanDto getIbanCalculator(@RequestParam String accNumber){
        IbanDto ibanCalculated = ibanClient.getIbanCalculator(accNumber);
        return ibanCalculated;
    }
}
