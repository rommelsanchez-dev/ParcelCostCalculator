package com.mynt.ParcelCostCalculator.integration;

import com.mynt.ParcelCostCalculator.dto.VoucherDto;
import com.mynt.ParcelCostCalculator.exception.MyntVoucherClientException;
import com.mynt.ParcelCostCalculator.configuration.properties.MyntClientProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Locale;

@Slf4j
@Component
public class MyntVoucherClient {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private MyntClientProperties myntClientProperties;

    public VoucherDto getMyntVoucherCode(String voucherCode) {
        VoucherDto voucherDto;
        try {
            StringBuilder uriStrBuilder = new StringBuilder();
            uriStrBuilder.append(myntClientProperties.getBaseUrl());
            uriStrBuilder.append(myntClientProperties.getVoucherUrl());
            uriStrBuilder.append(voucherCode.toUpperCase(Locale.ROOT));
            uriStrBuilder.append("?key=");
            uriStrBuilder.append(myntClientProperties.getKey());
            URI uri = new URI(uriStrBuilder.toString());

            voucherDto = restTemplate.getForObject(uri,VoucherDto.class);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            throw MyntVoucherClientException.builder().message(ex.getMessage()).build();
        }

        return voucherDto;
    }
}
