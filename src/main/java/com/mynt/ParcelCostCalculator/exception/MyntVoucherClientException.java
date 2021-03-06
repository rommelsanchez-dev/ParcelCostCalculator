package com.mynt.ParcelCostCalculator.exception;

import lombok.Builder;
import lombok.Getter;

@Getter
public class MyntVoucherClientException extends RuntimeException{

    private String message;

    @Builder
    public MyntVoucherClientException(String message) {
        this.message = message;
    }
}
