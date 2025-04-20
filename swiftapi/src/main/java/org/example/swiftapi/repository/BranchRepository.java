package org.example.swiftapi.repository;

import org.example.swiftapi.model.Branch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BranchRepository extends JpaRepository<Branch, String> {
    List<Branch> findByCountryISO2(String countryISO2);
}
