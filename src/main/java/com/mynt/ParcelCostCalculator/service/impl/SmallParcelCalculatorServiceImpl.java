package com.mynt.ParcelCostCalculator.service.impl;

import com.mynt.ParcelCostCalculator.constants.CurrencyCode;
import com.mynt.ParcelCostCalculator.dto.ParcelCostDto;
import com.mynt.ParcelCostCalculator.dto.VoucherDto;
import com.mynt.ParcelCostCalculator.configuration.properties.RuleBaseCostProperties;
import com.mynt.ParcelCostCalculator.configuration.properties.RuleVolumeProperties;
import com.mynt.ParcelCostCalculator.service.VolumeParcelCalulatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class SmallParcelCalculatorServiceImpl implements VolumeParcelCalulatorService {

    @Autowired
    RuleBaseCostProperties ruleBaseCostProperties;

    @Autowired
    RuleVolumeProperties ruleVolumeProperties;

    @Override
    public ParcelCostDto calculate(BigDecimal volume, VoucherDto voucherDto) {
        log.info("Parcel is small");
        BigDecimal cost;

        cost = volume.multiply(new BigDecimal(ruleBaseCostProperties.getSmallParcel()));

        if (voucherDto != null) {
            BigDecimal lessAmount = cost.multiply(voucherDto.getDiscount().movePointLeft(2));
            cost = cost.subtract(lessAmount);
        }
        return ParcelCostDto.builder().cost(cost.setScale(2, BigDecimal.ROUND_CEILING))
                .currency(CurrencyCode.CURRENCY_CODE_PHP)
                .comment("Volume is less than " + ruleVolumeProperties.getSmall() + " cm3").build();
    }
}
