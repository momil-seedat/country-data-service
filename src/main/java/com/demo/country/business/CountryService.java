package com.demo.country.business;

import com.demo.country.common.Utils;
import com.demo.country.model.CountryInfoDto;
import com.demo.country.model.ExchangeRateResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class CountryService {

    @Value("${country.info.url}")
    private String countryInfoUrl;

    @Value("${exchange.rates.url}")
    private String exchangeRateUrl;


    public Object countryLookup(String name){


        RestTemplate restTemplate=new RestTemplate();

        List<LinkedHashMap> country=restTemplate.getForObject(countryInfoUrl+name, ArrayList.class);

        if(country==null)
            return Utils.ErrCountry;

        List<CountryInfoDto> infoList=new ArrayList<>();
        AtomicReference<String> currencySymbols= new AtomicReference<>("IDR");


       country.stream().forEach(info-> {

            LinkedHashMap<String,Object> fullName=Utils.convertObjectToClass(info.get("name"),LinkedHashMap.class);

            CountryInfoDto countryInfoDto=new CountryInfoDto();
            countryInfoDto.setName(fullName.get("official"));
            countryInfoDto.setPopulation(info.get("population").toString());

            countryInfoDto.setCurrencies(Utils.convertObjectToClass(info.get("currencies"),LinkedHashMap.class)
                    .keySet().toString().replaceAll("\\[|\\]",""));

            currencySymbols.set(currencySymbols + "," + countryInfoDto.getCurrencies());

            infoList.add(countryInfoDto);


        });


       setExchangeRate(infoList,exchangeRateUrl+currencySymbols,restTemplate);


        return infoList;


    }
    private void setExchangeRate(List<CountryInfoDto> infoList,String url,RestTemplate restTemplate ){


        ExchangeRateResponse exchangeRate=restTemplate.getForObject(url, ExchangeRateResponse.class);

        double conversion=1/exchangeRate.getRates().get("IDR");
        int count=0;

        exchangeRate.getRates().entrySet().stream()
                .filter(f-> f.getKey()!="IDR")
                .forEach(rates->{

                        infoList.get(count).setExchangeRate(rates.getValue()*conversion);

                       });

    }
}
