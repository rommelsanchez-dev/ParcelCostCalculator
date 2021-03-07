package com.mynt.parcel_cost_calculator.service;

import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.ParcelDto;
import com.mynt.parcel_cost_calculator.dto.VoucherDto;

public interface WeightParcelCalculatorService {

    public ParcelCostDto calculate(ParcelDto parcelDto, VoucherDto voucherDto);
}
