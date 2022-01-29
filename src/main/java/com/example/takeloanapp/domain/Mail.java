package com.example.takeloanapp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.hql.spi.id.cte.CteValuesListDeleteHandlerImpl;

import java.util.Optional;

@AllArgsConstructor
@Getter
public class Mail {
    private final String mailTo;
    private final String subject;
    private final String message;
    private final String toCc;
}
