package com.mynt.parcel_cost_calculator.service.impl;

import com.mynt.parcel_cost_calculator.constants.CurrencyCode;
import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.VoucherDto;
import com.mynt.parcel_cost_calculator.configuration.properties.RuleBaseCostProperties;
import com.mynt.parcel_cost_calculator.service.VolumeParcelCalulatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class LargeParcelCalculatorServiceImpl implements VolumeParcelCalulatorService {

    @Autowired
    RuleBaseCostProperties ruleBaseCostProperties;

    @Override
    public ParcelCostDto calculate(BigDecimal volume, VoucherDto voucherDto) {
        log.info("Parcel is large.");
        BigDecimal cost;

        cost = volume.multiply(new BigDecimal(ruleBaseCostProperties.getLargeParcel()));

        if (voucherDto != null) {
            BigDecimal lessAmount = cost.multiply(voucherDto.getDiscount().movePointLeft(2));
            cost = cost.subtract(lessAmount);
        }

        return ParcelCostDto.builder().cost(cost.setScale(2, BigDecimal.ROUND_CEILING)).currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("").build();
    }
}
