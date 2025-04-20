package org.example.swiftapi.dto;

import org.example.swiftapi.model.Branch;

public class BranchDTO {
    private String address;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private boolean isHeadquarter;
    private String swiftCode;

    // Constructors
    public BranchDTO() {}

    public BranchDTO(Branch branch) {
        this.address = branch.getAddress();
        this.bankName = branch.getBankName();
        this.countryISO2 = branch.getCountryISO2();
        this.countryName = branch.getCountryName();
        this.isHeadquarter = branch.isHeadquarter();
        this.swiftCode = branch.getSwiftCode();
    }

    // Getters and setters
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

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

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}