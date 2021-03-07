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
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ParcelCalculatorServiceImplTest {

    @Autowired
    private ParcelCalculatorServiceImpl parcelCalculatorService;

    @Test
    void testCalculateHeavyParcel() {
        ParcelDto parcelDto = ParcelDto.builder()
                .weight(new BigDecimal(31))
                .height(new BigDecimal(11))
                .width(BigDecimal.ONE)
                .length(BigDecimal.ONE)
                .voucherCode("GFI")
                .build();

        ParcelCostDto parcelCostDto = ParcelCostDto.builder()
                .cost(new BigDecimal("620.00"))
                .currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("Weight exceeds 10 kg")
                .build();

        ParcelCostDto resultDto = parcelCalculatorService.calculate(parcelDto);
        assertThat(resultDto).isEqualTo(parcelCostDto);
    }

    @Test
    void testCalculateLargeParcel() {
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

        ParcelCostDto resultDto =  parcelCalculatorService.calculate(parcelDto);

        assertThat(resultDto).isEqualTo(parcelCostDto);
    }

    @Test
    void testCalculateMediumParcel() {
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

        ParcelCostDto resultDto =  parcelCalculatorService.calculate(parcelDto);

        assertThat(resultDto).isEqualTo(parcelCostDto);
    }

    @Test
    void testCalculateRejectParcel() {
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

        ParcelCostDto resultDto = parcelCalculatorService.calculate(parcelDto);

        assertThat(resultDto).isEqualTo(parcelCostDto);
    }

    @Test
    void testCalculateSmallParcel() {
        ParcelDto parcelDto = ParcelDto.builder()
                .weight(new BigDecimal(1))
                .height(new BigDecimal(500))
                .width(BigDecimal.ONE)
                .length(BigDecimal.ONE)
                .voucherCode("GFI")
                .build();

        ParcelCostDto parcelCostDto = ParcelCostDto.builder()
                .cost(new BigDecimal("15.00"))
                .currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("Volume is less than 1500 cm3")
                .build();

        BigDecimal volume = parcelDto.getHeight().multiply(parcelDto.getLength()).multiply(parcelDto.getWidth());

        ParcelCostDto resultDto =  parcelCalculatorService.calculate(parcelDto);

        assertThat(resultDto).isEqualTo(parcelCostDto);
    }
}
