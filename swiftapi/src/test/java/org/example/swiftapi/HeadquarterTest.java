package org.example.swiftapi;

import org.example.swiftapi.model.Branch;
import org.example.swiftapi.model.Headquarter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

@DisplayName("Headquarter")
class HeadquarterTest {

    @Test
    @DisplayName("Should create a Headquarter object with correct initial values")
    void shouldCreateHeadquarterWithCorrectValues() {
        // Arrange
        String address = "Al. Centralna 1, 31-999 Kraków";
        String bankName = "Centralny Bank Polski";
        String countryISO2 = "PL";
        String countryName = "Poland";
        String swiftCode = "CBPKPLPX";

        // Act
        Headquarter headquarter = new Headquarter(address, bankName, countryISO2, countryName, swiftCode);

        // Assert
        assertAll("Headquarter properties",
                () -> assertEquals(address, headquarter.getAddress(), "Address should match"),
                () -> assertEquals(bankName.toUpperCase(), headquarter.getBankName(), "Bank name should be uppercase"),
                () -> assertEquals(countryISO2.toUpperCase(), headquarter.getCountryISO2(), "Country ISO2 should be uppercase"),
                () -> assertEquals(countryName, headquarter.getCountryName(), "Country name should match"),
                () -> assertEquals(swiftCode.toUpperCase(), headquarter.getSwiftCode(), "Swift code should be uppercase"),
                () -> assertTrue(headquarter.isHeadquarter(), "Should be a headquarter by default"),
                () -> assertTrue(headquarter.getBranches().isEmpty(), "Branches list should be empty initially")
        );
    }

    @Test
    @DisplayName("Should correctly add and remove branches")
    void shouldAddAndRemoveBranches() {
        // Arrange
        Headquarter headquarter = new Headquarter("HQ Address", "HQ Bank", "US", "United States", "HQBANKUS");
        Branch branch1 = new Branch("Branch 1 Address", "Branch 1 Bank", "GB", "United Kingdom", "BRNCHGB01");
        Branch branch2 = new Branch("Branch 2 Address", "Branch 2 Bank", "DE", "Germany", "BRNCHDE02");

        // Act & Assert - Adding branches
        headquarter.addBranch(branch1);
        headquarter.addBranch(branch2);
        assertAll("After adding branches",
                () -> assertEquals(2, headquarter.getBranches().size(), "Should contain two branches"),
                () -> assertTrue(headquarter.getBranches().contains(branch1), "Should contain branch 1"),
                () -> assertTrue(headquarter.getBranches().contains(branch2), "Should contain branch 2"),
                () -> assertEquals(headquarter, branch1.getHeadquarter(), "Branch 1 should have this headquarter set"),
                () -> assertEquals(headquarter, branch2.getHeadquarter(), "Branch 2 should have this headquarter set")
        );

        // Act & Assert - Removing a branch
        headquarter.removeBranch(branch1);
        assertAll("After removing branch 1",
                () -> assertEquals(1, headquarter.getBranches().size(), "Should contain one branch"),
                () -> assertFalse(headquarter.getBranches().contains(branch1), "Should not contain branch 1"),
                () -> assertTrue(headquarter.getBranches().contains(branch2), "Should still contain branch 2"),
                () -> assertNull(branch1.getHeadquarter(), "Branch 1 should have headquarter set to null")
        );

        // Act & Assert - Removing the remaining branch
        headquarter.removeBranch(branch2);
        assertTrue(headquarter.getBranches().isEmpty(), "Branches list should be empty after removing the last branch");
        assertNull(branch2.getHeadquarter(), "Branch 2 should have headquarter set to null");
    }

    @Test
    @DisplayName("Should not add the same branch twice")
    void shouldNotAddSameBranchTwice() {
        // Arrange
        Headquarter headquarter = new Headquarter("HQ Address", "HQ Bank", "US", "United States", "HQBANKUS");
        Branch branch = new Branch("Branch Address", "Branch Bank", "GB", "United Kingdom", "BRNCHGB01");

        // Act
        headquarter.addBranch(branch);
        headquarter.addBranch(branch);

        // Assert
        assertEquals(1, headquarter.getBranches().size(), "Should contain only one branch");
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode based on swiftCode")
    void shouldImplementEqualsAndHashCodeBasedOnSwiftCode() {
        // Arrange
        Headquarter hq1 = new Headquarter("Address 1", "Bank 1", "PL", "Poland", "SWIFTABC1");
        Headquarter hq2 = new Headquarter("Address 2", "Bank 2", "DE", "Germany", "SWIFTABC1");
        Headquarter hq3 = new Headquarter("Address 3", "Bank 3", "FR", "France", "SWIFTDEF2");

        // Assert - Equality
        assertEquals(hq1, hq2, "Headquarters with the same Swift code should be equal");
        assertNotEquals(hq1, hq3, "Headquarters with different Swift codes should not be equal");

        // Assert - HashCode
        assertEquals(hq1.hashCode(), hq2.hashCode(), "Hash codes of equal headquarters should be the same");
        assertNotEquals(hq1.hashCode(), hq3.hashCode(), "Hash codes of non-equal headquarters should be different");
    }
}