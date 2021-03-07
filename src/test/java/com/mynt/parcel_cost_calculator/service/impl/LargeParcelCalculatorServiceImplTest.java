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
public class LargeParcelCalculatorServiceImplTest {

    @Autowired
    private LargeParcelCalculatorServiceImpl largeParcelCalculatorService;

    @Test
    void testCalculate() {
        ParcelDto parcelDto = ParcelDto.builder()
                .weight(new BigDecimal(1))
                .height(new BigDecimal(2500))
                .width(BigDecimal.ONE)
                .length(BigDecimal.ONE)
                .voucherCode("GFI")
                .build();

        ParcelCostDto parcelCostDto = ParcelCostDto.builder()
                .cost(new BigDecimal("125.00"))
                .currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("")
                .build();

        BigDecimal volume = parcelDto.getHeight().multiply(parcelDto.getLength()).multiply(parcelDto.getWidth());

        ParcelCostDto resultDto =  largeParcelCalculatorService.calculate(volume, null);

        assertThat(resultDto).isEqualTo(parcelCostDto);

    }
}
