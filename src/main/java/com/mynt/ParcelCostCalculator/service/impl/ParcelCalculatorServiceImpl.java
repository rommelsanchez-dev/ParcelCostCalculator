package com.mynt.ParcelCostCalculator.service.impl;

import com.mynt.ParcelCostCalculator.dto.ParcelCostDto;
import com.mynt.ParcelCostCalculator.dto.ParcelDto;
import com.mynt.ParcelCostCalculator.dto.VoucherDto;
import com.mynt.ParcelCostCalculator.exception.MyntVoucherClientException;
import com.mynt.ParcelCostCalculator.integration.MyntVoucherClient;
import com.mynt.ParcelCostCalculator.configuration.properties.RuleBaseCostProperties;
import com.mynt.ParcelCostCalculator.configuration.properties.RuleVolumeProperties;
import com.mynt.ParcelCostCalculator.configuration.properties.RuleWeightProperties;
import com.mynt.ParcelCostCalculator.service.ParcelCalculatorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
@Service
public class ParcelCalculatorServiceImpl implements ParcelCalculatorService {

    @Autowired
    RuleBaseCostProperties ruleBaseCostProperties;

    @Autowired
    RuleVolumeProperties ruleVolumeProperties;

    @Autowired
    RuleWeightProperties ruleWeightProperties;

    @Autowired
    RejectParcelCalculatorServiceImpl rejectParcelCalculatorImpl;

    @Autowired
    HeavyParcelCalculatorServiceImpl heavyParcelCalculator;

    @Autowired
    SmallParcelCalculatorServiceImpl smallParcelCalculatorImpl;

    @Autowired
    MediumParcelCalculatorServiceImpl mediumParcelCalculator;

    @Autowired
    LargeParcelCalculatorServiceImpl largeParcelCalculator;

    @Autowired
    MyntVoucherClient myntVoucherClient;

    private static final String DATE_FORMAT_YYYY_DD_MM = "yyyy-MM-dd";

    @Override
    public ParcelCostDto calculate(ParcelDto parcelDto) {
        log.info("Calculating...");

        BigDecimal volume;
        BigDecimal mediumVolumeLimit;
        VoucherDto voucherDto = null;

        try {
            voucherDto = retrieveAndValidateVoucherCode(parcelDto.getVoucherCode());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (parcelDto.getWeight().compareTo(new BigDecimal(ruleWeightProperties.getReject())) > 0 ) {
            return rejectParcelCalculatorImpl.calculate(parcelDto, voucherDto);
        }

        if (parcelDto.getWeight().compareTo(new BigDecimal(ruleWeightProperties.getHeavy())) > 0) {
            return heavyParcelCalculator.calculate(parcelDto, voucherDto);
        }

        volume = parcelDto.getHeight().multiply(parcelDto.getLength()).multiply(parcelDto.getWidth());

        if (volume.compareTo(new BigDecimal(ruleVolumeProperties.getSmall())) < 0) {
            return smallParcelCalculatorImpl.calculate(volume, voucherDto);
        }

        mediumVolumeLimit = new BigDecimal(ruleVolumeProperties.getMedium());

        if (volume.compareTo(mediumVolumeLimit) < 0) {
            return mediumParcelCalculator.calculate(volume, voucherDto);
        }

        if (volume.compareTo(mediumVolumeLimit) >= 0) {
            return largeParcelCalculator.calculate(volume, voucherDto);
        }

        return null;
    }

    private VoucherDto retrieveAndValidateVoucherCode(String voucherCode) throws ParseException {
        log.info("Checking voucher code...");

        if (voucherCode == null) {
            log.info("Voucher code not found.");
            return null;
        }

        VoucherDto voucherDto = null;

        try {
            voucherDto = myntVoucherClient.getMyntVoucherCode(voucherCode);
        } catch (MyntVoucherClientException ex) {
            log.error(ex.getMessage());
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_YYYY_DD_MM);
        Date voucherExpiry = sdf.parse(voucherDto.getExpiry());
        Date today = new Date();
        if (today.after(voucherExpiry)) {
            log.info("Voucher is expired.");
            return null;
        }

        return voucherDto;


    }
}
