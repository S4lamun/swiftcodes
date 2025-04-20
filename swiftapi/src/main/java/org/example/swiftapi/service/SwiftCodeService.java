package org.example.swiftapi.service;

import org.example.swiftapi.dto.BranchDTO;
import org.example.swiftapi.dto.CountrySwiftCodesDTO;
import org.example.swiftapi.dto.HeadquarterDTO;
import org.example.swiftapi.model.Branch;
import org.example.swiftapi.model.Headquarter;
import org.example.swiftapi.excel.ExcelReader;

import org.example.swiftapi.repository.BranchRepository;
import org.example.swiftapi.repository.HeadquarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SwiftCodeService {

    private final BranchRepository branchRepository;
    private final HeadquarterRepository headquarterRepository;

    @Autowired
    public SwiftCodeService(BranchRepository branchRepository, HeadquarterRepository headquarterRepository) {
        this.branchRepository = branchRepository;
        this.headquarterRepository = headquarterRepository;
    }

    public Object getSwiftCodeDetails(String swiftCode) {
        Optional<Headquarter> headquarterOpt = headquarterRepository.findById(swiftCode);
        if (headquarterOpt.isPresent()) {
            return new HeadquarterDTO(headquarterOpt.get());
        }

        Optional<Branch> branchOpt = branchRepository.findById(swiftCode);
        if (branchOpt.isPresent()) {
            return new BranchDTO(branchOpt.get());
        }

        return null;
    }

    public CountrySwiftCodesDTO getSwiftCodesByCountry(String countryISO2) {
        List<Branch> branches = branchRepository.findByCountryISO2(countryISO2.toUpperCase());
        List<Headquarter> headquarters = headquarterRepository.findByCountryISO2(countryISO2.toUpperCase());

        List<Object> swiftCodes = new ArrayList<>(); // Zmieniono typ na List<Object>

        // Add all branches
        swiftCodes.addAll(branches.stream().map(BranchDTO::new).collect(Collectors.toList()));

        // Add all headquarters
        swiftCodes.addAll(headquarters.stream().map(HeadquarterDTO::new).collect(Collectors.toList()));

        // Get country name
        String countryName = !branches.isEmpty() ? branches.get(0).getCountryName() :
                (!headquarters.isEmpty() ? headquarters.get(0).getCountryName() : "Unknown");

        return new CountrySwiftCodesDTO(countryISO2.toUpperCase(), countryName, swiftCodes);
    }

    @Transactional
    public String addSwiftCode(BranchDTO branchDTO) {
        String swiftCode = branchDTO.getSwiftCode().toUpperCase();

        // Check if the swift code already exists
        if (branchRepository.existsById(swiftCode)) {
            return "SWIFT code already exists.";
        }

        if (branchDTO.isHeadquarter()) {
            Headquarter headquarter = new Headquarter(
                    branchDTO.getAddress(),
                    branchDTO.getBankName(),
                    branchDTO.getCountryISO2(),
                    branchDTO.getCountryName(),
                    swiftCode
            );
            headquarterRepository.save(headquarter);
            return "Headquarter with SWIFT code " + swiftCode + " successfully added.";
        } else {
            Branch branch = new Branch(
                    branchDTO.getAddress(),
                    branchDTO.getBankName(),
                    branchDTO.getCountryISO2(),
                    branchDTO.getCountryName(),
                    swiftCode
            );
            branchRepository.save(branch);
            return "Branch with SWIFT code " + swiftCode + " successfully added.";
        }
    }

    @Transactional
    public String deleteSwiftCode(String swiftCode) {
        swiftCode = swiftCode.toUpperCase();

        // Check if it's a headquarter
        Optional<Headquarter> headquarterOpt = headquarterRepository.findById(swiftCode);
        if (headquarterOpt.isPresent()) {
            Headquarter headquarter = headquarterOpt.get();
            // Check if there are associated branches
            if (!headquarter.getBranches().isEmpty()) {
                return "Cannot delete headquarter with associated branches.";
            }
            headquarterRepository.delete(headquarter);
            return "Headquarter with SWIFT code " + swiftCode + " successfully deleted.";
        }

        // Check if it's a branch
        Optional<Branch> branchOpt = branchRepository.findById(swiftCode);
        if (branchOpt.isPresent()) {
            Branch branch = branchOpt.get();
            branchRepository.delete(branch);
            return "Branch with SWIFT code " + swiftCode + " successfully deleted.";
        }

        return "SWIFT code " + swiftCode + " not found.";
    }

    @Transactional
    public int importDataFromExcel(ExcelReader.BranchesAndHeadquarters<List<Branch>, List<Headquarter>> data) {
        int count = 0;

        // Save all headquarters first
        for (Headquarter headquarter : data.headquarters()) {
            if (!headquarterRepository.existsById(headquarter.getSwiftCode())) {
                headquarterRepository.save(headquarter);
                count++;
            }
        }

        // Save all branches
        for (Branch branch : data.branches()) {
            if (!branchRepository.existsById(branch.getSwiftCode())) {
                branchRepository.save(branch);
                count++;
            }
        }

        return count;
    }
}