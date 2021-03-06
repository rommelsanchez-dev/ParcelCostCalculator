package com.mynt.ParcelCostCalculator.controller;

import com.mynt.ParcelCostCalculator.constants.CurrencyCode;
import com.mynt.ParcelCostCalculator.dto.ParcelCostDto;
import com.mynt.ParcelCostCalculator.dto.ParcelDto;
import com.mynt.ParcelCostCalculator.service.impl.ParcelCalculatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.mynt.ParcelCostCalculator.util.Json.toJson;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.assertj.core.api.Assertions.assertThat;

@WebMvcTest
public class ParcelCostCalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcelCalculatorServiceImpl parcelCalculatorService;

    @Test
    public void testValidCalculate() throws Exception {
        ParcelDto parcelDto = ParcelDto.builder()
                .weight(BigDecimal.ONE)
                .height(new BigDecimal(11))
                .width(BigDecimal.ONE)
                .length(BigDecimal.ONE)
                .voucherCode("GFI")
                .build();

        ParcelCostDto parcelCostDto = ParcelCostDto.builder()
                .cost(new BigDecimal("60"))
                .currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("Volume is less than 2500 cm3")
                .build();

        given(parcelCalculatorService.calculate(parcelDto))
                .willReturn(parcelCostDto);

        mockMvc.perform(get("/parcel/calculate")
                .content(toJson(parcelDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(toJson(parcelCostDto)));
    }

}
