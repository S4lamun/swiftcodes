package org.example.swiftapi.model;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "branch")
public class Branch {

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
    protected boolean isHeadquarter = false;

    @Column(name = "adress")
    private String address; // Fixed typo from "adress" to "address"

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "headquarter_swift")
    private Headquarter headquarter;

    public Branch() {
    }

    public Branch(String address, String bankName, String countryISO2, String countryName, String swiftCode) {
        this.address = address;
        this.bankName = bankName != null ? bankName.toUpperCase() : null;
        this.countryISO2 = countryISO2 != null ? countryISO2.toUpperCase() : null;
        this.countryName = countryName;
        this.swiftCode = swiftCode != null ? swiftCode.toUpperCase() : null;
        this.isHeadquarter = false;
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

    public Headquarter getHeadquarter() {
        return headquarter;
    }

    protected void setHeadquarterInternal(Headquarter headquarter) {
        this.headquarter = headquarter;
    }

    public void setHeadquarter(Headquarter headquarter) {
        if (this.headquarter != null) {
            this.headquarter.getBranches().remove(this);
        }
        this.headquarter = headquarter;
        if (headquarter != null && !headquarter.getBranches().contains(this)) {
            headquarter.getBranches().add(this);
        }
    }

    public boolean isHeadquarter() {
        return isHeadquarter;
    }

    @Override
    public String toString() {
        return getBankName() + " " + getCountryISO2() + " " + getSwiftCode() + " " + getAddress();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Branch branch = (Branch) o;
        return Objects.equals(swiftCode, branch.swiftCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(swiftCode);
    }
}