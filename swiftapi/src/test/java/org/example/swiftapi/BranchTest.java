package org.example.swiftapi;

import org.example.swiftapi.model.Branch;
import org.example.swiftapi.model.Headquarter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Branch")
class BranchTest {

    @Test
    @DisplayName("Should create a Branch object with correct initial values")
    void shouldCreateBranchWithCorrectValues() {
        // Arrange
        String address = "ul. Bankowa 1, 00-123 Warszawa";
        String bankName = "Bank Polski";
        String countryISO2 = "PL";
        String countryName = "Poland";
        String swiftCode = "BPOLPLPW";

        // Act
        Branch branch = new Branch(address, bankName, countryISO2, countryName, swiftCode);

        // Assert
        assertAll("Branch properties",
                () -> assertEquals(address, branch.getAddress(), "Address should match"),
                () -> assertEquals(bankName.toUpperCase(), branch.getBankName(), "Bank name should be uppercase"),
                () -> assertEquals(countryISO2.toUpperCase(), branch.getCountryISO2(), "Country ISO2 should be uppercase"),
                () -> assertEquals(countryName, branch.getCountryName(), "Country name should match"),
                () -> assertEquals(swiftCode.toUpperCase(), branch.getSwiftCode(), "Swift code should be uppercase"),
                () -> assertFalse(branch.isHeadquarter(), "Should not be a headquarter by default"),
                () -> assertNull(branch.getHeadquarter(), "Headquarter should be null by default")
        );
    }

    @Test
    @DisplayName("Should correctly set and unset the headquarter relationship")
    void shouldSetAndUnsetHeadquarter() {
        // Arrange
        Branch branch = new Branch("Branch Address", "Branch Bank", "PL", "Poland", "BRANCHPL");
        Headquarter hq = new Headquarter("HQ Address", "HQ Bank", "US", "United States", "HQBANKUS");

        // Act & Assert - Setting headquarter
        branch.setHeadquarter(hq);
        assertAll("After setting headquarter",
                () -> assertEquals(hq, branch.getHeadquarter(), "Headquarter should be set"),
                () -> assertTrue(hq.getBranches().contains(branch), "Headquarter should contain the branch")
        );

        // Act & Assert - Removing headquarter
        branch.setHeadquarter(null);
        assertAll("After removing headquarter",
                () -> assertNull(branch.getHeadquarter(), "Headquarter should be null after removal"),
                () -> assertFalse(hq.getBranches().contains(branch), "Headquarter should not contain the branch after removal")
        );
    }

    @Test
    @DisplayName("Should correctly implement equals and hashCode based on swiftCode")
    void shouldImplementEqualsAndHashCodeBasedOnSwiftCode() {
        // Arrange
        Branch branch1 = new Branch("Address 1", "Bank 1", "PL", "Poland", "SWIFT123");
        Branch branch2 = new Branch("Address 2", "Bank 2", "DE", "Germany", "SWIFT123");
        Branch branch3 = new Branch("Address 3", "Bank 3", "FR", "France", "SWIFT456");

        // Assert - Equality
        assertEquals(branch1, branch2, "Branches with the same Swift code should be equal");
        assertNotEquals(branch1, branch3, "Branches with different Swift codes should not be equal");

        // Assert - HashCode
        assertEquals(branch1.hashCode(), branch2.hashCode(), "Hash codes of equal branches should be the same");
        assertNotEquals(branch1.hashCode(), branch3.hashCode(), "Hash codes of non-equal branches should be different");
    }
}