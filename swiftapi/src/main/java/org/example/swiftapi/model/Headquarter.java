package org.example.swiftapi.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "headquarter")
public class Headquarter {

    @Id
    @Column(name = "swift_code", length = 11)
    private String swiftCode;

    @Column(name = "bank_name")
    private String bankName;

    @Column(name = "country_ISO2", length = 2)
    private String countryISO2;

    @Column(name = "country_name")
    private String countryName;

    @Column(name = "is_headquarter", nullable = false)
    private boolean isHeadquarter = true;

    @Column(name = "adress")
    private String address; // Fixed typo from "adress" to "address"

    @OneToMany(mappedBy = "headquarter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Branch> branches = new ArrayList<>();

    public Headquarter() {
    }

    public Headquarter(String address, String bankName, String countryISO2, String countryName, String swiftCode) {
        this.address = address;
        this.bankName = bankName != null ? bankName.toUpperCase() : null;
        this.countryISO2 = countryISO2 != null ? countryISO2.toUpperCase() : null;
        this.countryName = countryName;
        this.swiftCode = swiftCode != null ? swiftCode.toUpperCase() : null;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    // Legacy getter for backward compatibility
    public String getAdress() {
        return address;
    }

    // Legacy setter for backward compatibility
    public void setAdress(String address) {
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

    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void addBranch(Branch branch) {
        if (branch != null && !this.branches.contains(branch)) {
            this.branches.add(branch);
            branch.setHeadquarterInternal(this);
        }
    }

    public void removeBranch(Branch branch) {
        if (branch != null && this.branches.contains(branch)) {
            this.branches.remove(branch);
            branch.setHeadquarterInternal(null);
        }
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("HEADQUARTER: ")
                .append(getBankName()).append(" ")
                .append(getCountryName()).append(" [")
                .append(getCountryISO2()).append("] ")
                .append(getSwiftCode()).append(" ")
                .append(getAddress());
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Headquarter that = (Headquarter) o;
        return Objects.equals(swiftCode, that.swiftCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(swiftCode);
    }
}