package com.mynt.parcel_cost_calculator.service.impl;

import com.mynt.parcel_cost_calculator.constants.CurrencyCode;
import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.ParcelDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class MediumParcelCalculatorServiceImplTest {

    @Autowired
    private MediumParcelCalculatorServiceImpl mediumParcelCalculatorService;

    @Test
    void testCalculate() {
        ParcelDto parcelDto = ParcelDto.builder()
                .weight(new BigDecimal(1))
                .height(new BigDecimal(1500))
                .width(BigDecimal.ONE)
                .length(BigDecimal.ONE)
                .voucherCode("GFI")
                .build();

        ParcelCostDto parcelCostDto = ParcelCostDto.builder()
                .cost(new BigDecimal("60.00"))
                .currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("Volume is less than 2500 cm3")
                .build();

        BigDecimal volume = parcelDto.getHeight().multiply(parcelDto.getLength()).multiply(parcelDto.getWidth());

        ParcelCostDto resultDto =  mediumParcelCalculatorService.calculate(volume, null);

        assertThat(resultDto).isEqualTo(parcelCostDto);

    }
}
