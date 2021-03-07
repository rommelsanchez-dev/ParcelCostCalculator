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
@ConfigurationProperties(prefix = "rule.base-cost")
public class RuleBaseCostProperties {

    @NonNull
    private String reject;

    @NonNull
    private String heavyParcel;

    @NonNull
    private String smallParcel;

    @NonNull
    private String mediumParcel;

    @NonNull
    private String largeParcel;

}
