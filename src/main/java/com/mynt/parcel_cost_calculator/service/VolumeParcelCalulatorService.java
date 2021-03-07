package com.mynt.parcel_cost_calculator.service;

import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.VoucherDto;

import java.math.BigDecimal;

public interface VolumeParcelCalulatorService {

    public ParcelCostDto calculate(BigDecimal volume, VoucherDto voucherDto);
}
