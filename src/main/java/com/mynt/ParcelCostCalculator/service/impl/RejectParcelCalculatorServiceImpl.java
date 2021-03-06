package com.mynt.ParcelCostCalculator.service.impl;

import com.mynt.ParcelCostCalculator.dto.ParcelCostDto;
import com.mynt.ParcelCostCalculator.dto.ParcelDto;
import com.mynt.ParcelCostCalculator.dto.VoucherDto;
import com.mynt.ParcelCostCalculator.service.WeightParcelCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class RejectParcelCalculatorServiceImpl implements WeightParcelCalculatorService {

    @Override
    public ParcelCostDto calculate(ParcelDto parcelDto, VoucherDto voucherDto) {
        log.info("Parcel is rejected.");
        return ParcelCostDto.builder().cost(BigDecimal.ZERO).comment("Not applicable").build();
    }
}
