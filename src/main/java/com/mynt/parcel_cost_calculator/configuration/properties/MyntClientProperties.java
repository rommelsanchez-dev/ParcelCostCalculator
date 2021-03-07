package com.mynt.parcel_cost_calculator.configuration.properties;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mynt")
public class MyntClientProperties {

    @NonNull
    private String baseUrl;

    @NonNull
    private String voucherUrl;

    @NonNull
    private String key;
}
