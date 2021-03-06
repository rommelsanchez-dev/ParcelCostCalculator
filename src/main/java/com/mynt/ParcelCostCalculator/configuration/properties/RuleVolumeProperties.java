package com.mynt.ParcelCostCalculator.configuration.properties;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "rule.volume")
public class RuleVolumeProperties {

    @NonNull
    private String small;

    @NonNull
    private String medium;

}
