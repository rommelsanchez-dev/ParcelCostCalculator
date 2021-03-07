package com.mynt.parcel_cost_calculator.service;

import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.ParcelDto;

public interface ParcelCalculatorService {

    public ParcelCostDto calculate(ParcelDto parcelDto);

}
