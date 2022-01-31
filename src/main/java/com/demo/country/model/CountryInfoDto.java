package com.demo.country.model;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CountryInfoDto {
    private Object name;
    private String population;
    private String currencies;
    private Double exchangeRate;
}
