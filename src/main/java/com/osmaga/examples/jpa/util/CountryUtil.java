package com.osmaga.examples.jpa.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class CountryUtil {

    @Value("${countries.list}")
    private String countriesListProperty;

    private static List<String> countriesList;

    private CountryUtil() {
        String[] countriesArray = countriesListProperty.split(",");
        countriesList = Arrays.asList(countriesArray);
    }

    public static List<String> getCountriesList() {
        if (countriesList == null) {
            new CountryUtil();
        }
        return countriesList;
    }
}