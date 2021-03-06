package com.mynt.ParcelCostCalculator.service;

import com.mynt.ParcelCostCalculator.dto.ParcelCostDto;
import com.mynt.ParcelCostCalculator.dto.ParcelDto;
import com.mynt.ParcelCostCalculator.dto.VoucherDto;

import java.math.BigDecimal;

public interface VolumeParcelCalulatorService {

    public ParcelCostDto calculate(BigDecimal volume, VoucherDto voucherDto);
}
