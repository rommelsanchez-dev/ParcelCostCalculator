package com.mynt.parcel_cost_calculator.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyntVoucherClientException extends RuntimeException{

    private final String message;

    @Builder
    public MyntVoucherClientException(String message) {
        this.message = message;
    }
}
