package com.mynt.ParcelCostCalculator.service;

import com.mynt.ParcelCostCalculator.dto.ParcelCostDto;
import com.mynt.ParcelCostCalculator.dto.ParcelDto;
import com.mynt.ParcelCostCalculator.dto.VoucherDto;

public interface ParcelCalculatorService {

    public ParcelCostDto calculate(ParcelDto parcelDto);

}
