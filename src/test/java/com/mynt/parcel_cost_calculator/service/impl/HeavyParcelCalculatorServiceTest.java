package com.mynt.parcel_cost_calculator.service.impl;

import com.mynt.parcel_cost_calculator.constants.CurrencyCode;
import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.ParcelDto;
import com.mynt.parcel_cost_calculator.dto.VoucherDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
class HeavyParcelCalculatorServiceTest {

    @Autowired
    private HeavyParcelCalculatorServiceImpl heavyParcelCalculatorService;

    @Test
    void testCalculate() {
        ParcelDto parcelDto = ParcelDto.builder()
                .weight(new BigDecimal(31))
                .height(new BigDecimal(11))
                .width(BigDecimal.ONE)
                .length(BigDecimal.ONE)
                .voucherCode("GFI")
                .build();

        VoucherDto voucherDto = VoucherDto.builder()
                .code("GFI")
                .discount(new BigDecimal("7.5"))
                .expiry("2020-03-07")
                .build();

        ParcelCostDto parcelCostDto = ParcelCostDto.builder()
                .cost(new BigDecimal("573.50"))
                .currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("Weight exceeds 10 kg")
                .build();
        ParcelCostDto resultDto =  heavyParcelCalculatorService.calculate(parcelDto, voucherDto);

        assertThat(resultDto).isEqualTo(parcelCostDto);

    }
}
