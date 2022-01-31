package com.demo.country.model;

import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class ExchangeRateResponse {
    private LinkedHashMap<String,Double> rates;
}
