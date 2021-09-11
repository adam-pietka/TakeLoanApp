package com.example.takeloanapp.domain.dto;

import lombok.Data;


@Data
public class IbanDto {

    private boolean valid;
    private String iban;
}
