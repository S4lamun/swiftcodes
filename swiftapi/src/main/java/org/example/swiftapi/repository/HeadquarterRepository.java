package org.example.swiftapi.repository;

import org.example.swiftapi.model.Headquarter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeadquarterRepository extends JpaRepository<Headquarter, String> {
    List<Headquarter> findByCountryISO2(String countryISO2);
}