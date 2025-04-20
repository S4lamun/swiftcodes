package org.example.swiftapi.controller;

import org.example.swiftapi.dto.BranchDTO;
import org.example.swiftapi.dto.CountrySwiftCodesDTO;
import org.example.swiftapi.dto.MessageResponseDTO;
import org.example.swiftapi.excel.ExcelReader;
import org.example.swiftapi.model.Branch;
import org.example.swiftapi.model.Headquarter;
import org.example.swiftapi.excel.*;
import org.example.swiftapi.service.SwiftCodeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.List;

@RestController
@RequestMapping("/v1/swift-codes")
public class SwiftCodeController {

    private final SwiftCodeService swiftCodeService;

    @Autowired
    public SwiftCodeController(SwiftCodeService swiftCodeService) {
        this.swiftCodeService = swiftCodeService;
    }

    @GetMapping("/{swiftCode}")
    public ResponseEntity<?> getSwiftCodeDetails(@PathVariable String swiftCode) {
        Object result = swiftCodeService.getSwiftCodeDetails(swiftCode);
        if (result == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(result);
    }

    @GetMapping("/country/{countryISO2code}")
    public ResponseEntity<CountrySwiftCodesDTO> getSwiftCodesByCountry(@PathVariable String countryISO2code) {
        CountrySwiftCodesDTO result = swiftCodeService.getSwiftCodesByCountry(countryISO2code);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<MessageResponseDTO> addSwiftCode(@RequestBody BranchDTO branchDTO) {
        String message = swiftCodeService.addSwiftCode(branchDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new MessageResponseDTO(message));
    }

    @DeleteMapping("/{swiftCode}")
    public ResponseEntity<MessageResponseDTO> deleteSwiftCode(@PathVariable String swiftCode) {
        String message = swiftCodeService.deleteSwiftCode(swiftCode);
        if (message.contains("not found")) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponseDTO(message));
        }
        return ResponseEntity.ok(new MessageResponseDTO(message));
    }

    @PostMapping("/upload")
    public ResponseEntity<MessageResponseDTO> uploadExcelFile(@RequestParam("file") MultipartFile file) {
        try {
            // Create a temporary file
            Path tempFile = Files.createTempFile("swift-codes-", ".xlsx");
            Files.copy(file.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);

            // Process the Excel file
            List<List<String>> data = ExcelReader.readExcel(tempFile.toString());
            ExcelReader.BranchesAndHeadquarters<List<Branch>, List<Headquarter>> result =
                    ExcelReader.processData(data);

            // Update the database
            int count = swiftCodeService.importDataFromExcel(result);

            // Clean up
            Files.deleteIfExists(tempFile);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new MessageResponseDTO("Successfully imported " + count + " records from Excel file."));
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponseDTO("Error importing Excel file: " + e.getMessage()));
        }
    }
}