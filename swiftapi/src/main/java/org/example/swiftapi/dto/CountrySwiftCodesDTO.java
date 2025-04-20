package org.example.swiftapi.dto;

import java.util.List;

public class CountrySwiftCodesDTO {
    private String countryISO2;
    private String countryName;
    private List<Object> swiftCodes;

    // Constructors
    public CountrySwiftCodesDTO() {}

    public CountrySwiftCodesDTO(String countryISO2, String countryName, List<Object> swiftCodes) {
        this.countryISO2 = countryISO2;
        this.countryName = countryName;
        this.swiftCodes = swiftCodes;
    }

    // Getters and setters
    public String getCountryISO2() {
        return countryISO2;
    }

    public void setCountryISO2(String countryISO2) {
        this.countryISO2 = countryISO2;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public List<Object> getSwiftCodes() {
        return swiftCodes;
    }

    public void setSwiftCodes(List<Object> swiftCodes) {
        this.swiftCodes = swiftCodes;
    }
}