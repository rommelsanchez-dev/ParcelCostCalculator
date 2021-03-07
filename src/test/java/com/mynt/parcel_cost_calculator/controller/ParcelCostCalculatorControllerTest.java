package com.mynt.parcel_cost_calculator.controller;

import com.mynt.parcel_cost_calculator.constants.CurrencyCode;
import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.ParcelDto;
import com.mynt.parcel_cost_calculator.service.impl.ParcelCalculatorServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static com.mynt.parcel_cost_calculator.util.Json.toJson;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class ParcelCostCalculatorControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ParcelCalculatorServiceImpl parcelCalculatorService;

    @Test
    void testValidCalculate() throws Exception {
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
