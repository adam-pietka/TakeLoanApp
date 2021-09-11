package com.example.takeloanapp.client;

import com.example.takeloanapp.config.CoreConfiguration;
import com.example.takeloanapp.domain.dto.IbanValidatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class IbanClient {

    private final RestTemplate restTemplate;
    private final CoreConfiguration configuration;



    public IbanValidatorDto getIbanValidator(){
        URI url = UriComponentsBuilder.fromHttpUrl(configuration.getIbanApiEndpoint() + "/validate/PL74808010010000000532013123")
                .build().encode().toUri() ;

        IbanValidatorDto ibanRespond = restTemplate.getForObject(url, IbanValidatorDto.class);
    return Optional.ofNullable(ibanRespond).orElse( new IbanValidatorDto());

//        return Optional.ofNullable(ibanRespond)
//                .map(Arrays::asList)
//                .orElse(Collections.emptyList());
    }
}
