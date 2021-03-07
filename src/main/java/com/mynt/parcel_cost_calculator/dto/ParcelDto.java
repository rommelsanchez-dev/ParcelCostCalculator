package com.mynt.parcel_cost_calculator.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ParcelDto implements Serializable {

    @NotNull
    private BigDecimal weight;
    @NotNull
    private BigDecimal height;
    @NotNull
    private BigDecimal width;
    @NotNull
    private BigDecimal length;
    private String voucherCode;
}
