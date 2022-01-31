package com.demo.country.common;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

    public static final String ErrCountry="Invalid Country Name";

    public static  <T> T convertObjectToClass(Object object, Class<T> tClass) {
        ObjectMapper oMapper = new ObjectMapper();
        return   oMapper.convertValue(object, tClass);
    }
}
