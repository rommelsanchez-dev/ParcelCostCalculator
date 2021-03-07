package com.mynt.parcel_cost_calculator.controller;

import com.mynt.parcel_cost_calculator.dto.ParcelCostDto;
import com.mynt.parcel_cost_calculator.dto.ParcelDto;
import com.mynt.parcel_cost_calculator.service.impl.ParcelCalculatorServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;

@RequestMapping(path = "/parcel")
@RestController
public class ParcelCostCalculatorController {

    @Autowired
    ParcelCalculatorServiceImpl parcelCalculatorImpl;

    @GetMapping(path = "/calculate")
    public ParcelCostDto calculateParcelCost(@Valid @RequestBody ParcelDto parcelDto) {

        return parcelCalculatorImpl.calculate(parcelDto);
    }
}
