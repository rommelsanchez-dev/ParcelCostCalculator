package com.mynt.ParcelCostCalculator.configuration.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "mynt")
public class MyntClientProperties {
    private String baseUrl;
    private String voucherUrl;
    private String key;
}
