package com.example.takeloanapp.client;

import com.example.takeloanapp.config.CoreConfiguration;
import com.example.takeloanapp.domain.dto.IbanDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IbanClient {


    private static final Logger LOGGER = LoggerFactory.getLogger(IbanClient.class);
    private static final String KNR_NUMBER = "80801001/";
    private final RestTemplate restTemplate;
    private final CoreConfiguration configuration;

    public IbanDto getIbanValidator(String ibanNumber){
        URI url = UriComponentsBuilder.fromHttpUrl(configuration.getIbanApiEndpoint() + "/validate/" + ibanNumber)
                .build().encode().toUri() ;

        try {
            IbanDto ibanRespond = restTemplate.getForObject(url, IbanDto.class);
            LOGGER.info("IBAN has been validated. account number: " +  ibanRespond.getIban() + ". Is valid? - " + ibanRespond.isValid());
            return Optional.ofNullable(ibanRespond).orElse( new IbanDto());

        } catch (RestClientException e) {
            LOGGER.error("getIbanValidator - " + e.getMessage());
            return new IbanDto();
        }
    }

    public IbanDto getIbanCalculator(String accNumber){
        URI url = UriComponentsBuilder.fromHttpUrl(configuration.getIbanApiEndpoint() + "/calculate/PL/" + KNR_NUMBER + accNumber)
                .build().encode().toUri() ;

        try {
            IbanDto ibanRespond = restTemplate.getForObject(url, IbanDto.class);
            LOGGER.info("IBAN has been created. Loan account number: " +  ibanRespond.getIban());
            return Optional.ofNullable(ibanRespond).orElse( new IbanDto());

        } catch (RestClientException e) {
            LOGGER.error("getIbanCalculator - "+ e.getMessage());
            return new IbanDto();
        }
    }
}
