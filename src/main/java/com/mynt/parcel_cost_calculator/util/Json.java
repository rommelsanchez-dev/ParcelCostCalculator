package com.mynt.parcel_cost_calculator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {

    private Json () {
        throw new IllegalStateException("Utility class");
    }

    public static String toJson(Object object) {

        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return "";
    }
}
