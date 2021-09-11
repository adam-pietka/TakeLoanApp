package com.example.takeloanapp.client;

import com.example.takeloanapp.domain.dto.IbanDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@SpringBootTest
public class IbanClientTestSuite {

    @Autowired
    private IbanClient ibanClient;

    @Test
    void testIbanCalc(){
        // G
        IbanDto ibanDto = ibanClient.getIbanCalculator("00881");

        // W & T
        System.out.println("calculated IBAN: " + ibanDto.getIban());
        assertTrue(ibanDto.isValid());
        assertEquals(28, ibanDto.getIban().length());
    }

    @Test
    void testIBANcalculator(){
        // G
        IbanDto calculatedIban = ibanClient.getIbanValidator("PL54808010010000000000000881");

        // W & T
        System.out.println(" " + calculatedIban.isValid());
        assertTrue( calculatedIban.isValid());
        assertEquals("PL54808010010000000000000881", calculatedIban.getIban());
    }
}
