package com.mynt.parcel_cost_calculator.service.impl;

import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.ParcelDto;
import com.mynt.parcel_cost_calculator.dto.VoucherDto;
import com.mynt.parcel_cost_calculator.exception.MyntVoucherClientException;
import com.mynt.parcel_cost_calculator.integration.MyntVoucherClient;
import com.mynt.parcel_cost_calculator.configuration.properties.RuleVolumeProperties;
import com.mynt.parcel_cost_calculator.configuration.properties.RuleWeightProperties;
import com.mynt.parcel_cost_calculator.service.ParcelCalculatorService;
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
    private RuleVolumeProperties ruleVolumeProperties;

    @Autowired
    private RuleWeightProperties ruleWeightProperties;

    @Autowired
    private RejectParcelCalculatorServiceImpl rejectParcelCalculatorImpl;

    @Autowired
    private HeavyParcelCalculatorServiceImpl heavyParcelCalculator;

    @Autowired
    private SmallParcelCalculatorServiceImpl smallParcelCalculatorImpl;

    @Autowired
    private MediumParcelCalculatorServiceImpl mediumParcelCalculator;

    @Autowired
    private LargeParcelCalculatorServiceImpl largeParcelCalculator;

    @Autowired
    private MyntVoucherClient myntVoucherClient;

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
