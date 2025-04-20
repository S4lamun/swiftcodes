package org.example.swiftapi.dto;

import org.example.swiftapi.model.Headquarter;
import org.example.swiftapi.model.Branch;

import java.util.List;
import java.util.stream.Collectors;

public class HeadquarterDTO {
    private String swiftCode;
    private String bankName;
    private String countryISO2;
    private String countryName;
    private String address;
    private boolean isHeadquarter;
    private List<BranchDTO> branches;

    // Constructors
    public HeadquarterDTO() {
        this.isHeadquarter = true;
    }

    public HeadquarterDTO(Headquarter headquarter) {
        this.swiftCode = headquarter.getSwiftCode();
        this.bankName = headquarter.getBankName();
        this.countryISO2 = headquarter.getCountryISO2();
        this.countryName = headquarter.getCountryName();
        this.address = headquarter.getAddress();
        this.isHeadquarter = headquarter.isHeadquarter();
        this.branches = headquarter.getBranches().stream()
                .map(BranchDTO::new)
                .collect(Collectors.toList());

    }

    // Getters and setters
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    public void setHeadquarter(boolean headquarter) {
        isHeadquarter = headquarter;
    }

    public List<BranchDTO> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchDTO> branches) {
        this.branches = branches;
    }
}