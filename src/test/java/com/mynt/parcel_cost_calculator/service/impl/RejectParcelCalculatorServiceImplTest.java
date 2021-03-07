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
public class RejectParcelCalculatorServiceImplTest {

    @Autowired
    private RejectParcelCalculatorServiceImpl rejectParcelCalculatorService;

    @Test
    void testCalculate() {
        ParcelDto parcelDto = ParcelDto.builder()
                .weight(new BigDecimal(51))
                .height(new BigDecimal(500))
                .width(BigDecimal.ONE)
                .length(BigDecimal.ONE)
                .voucherCode("GFI")
                .build();

        ParcelCostDto parcelCostDto = ParcelCostDto.builder()
                .cost(BigDecimal.ZERO)
                .currency(null)
                .comment("Not applicable")
                .build();

        BigDecimal volume = parcelDto.getHeight().multiply(parcelDto.getLength()).multiply(parcelDto.getWidth());

        ParcelCostDto resultDto = rejectParcelCalculatorService.calculate(parcelDto, null);

        assertThat(resultDto).isEqualTo(parcelCostDto);
    }
}
