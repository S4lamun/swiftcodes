package org.example.swiftapi.excel;

import org.example.swiftapi.model.Branch;
import org.example.swiftapi.model.Headquarter;
import org.example.swiftapi.repository.BranchRepository;
import org.example.swiftapi.repository.HeadquarterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Component
public class DataLoader implements CommandLineRunner {
    private static final Logger LOGGER = Logger.getLogger(DataLoader.class.getName());

    private final BranchRepository branchRepository;
    private final HeadquarterRepository headquarterRepository;

    @Autowired
    public DataLoader(BranchRepository branchRepository, HeadquarterRepository headquarterRepository) {
        this.branchRepository = branchRepository;
        this.headquarterRepository = headquarterRepository;
    }

    @Override
    public void run(String... args) {
        try {
            // Try to load from classpath first
            String excelFilePath;
            try {
                Resource resource = new ClassPathResource("Interns_2025_SWIFT_CODES.xlsx");
                excelFilePath = resource.getFile().getAbsolutePath();
            } catch (IOException e) {
                // Fallback to file system
                try {
                    File file = ResourceUtils.getFile("classpath:Interns_2025_SWIFT_CODES.xlsx");
                    excelFilePath = file.getAbsolutePath();
                } catch (IOException ex) {
                    // If all else fails, use the hardcoded path
                    excelFilePath = "C:\\Users\\kacpe\\OneDrive\\Pulpit\\Projekty\\" +
                            "RemiltySwift\\Interns_2025_SWIFT_CODES.xlsx";
                    LOGGER.warning("Using hardcoded file path: " + excelFilePath);
                }
            }

            // Read and process Excel data
            List<List<String>> data = ExcelReader.readExcel(excelFilePath);
            ExcelReader.BranchesAndHeadquarters<List<Branch>, List<Headquarter>> result = ExcelReader.processData(data);

            // Save headquarters first
            headquarterRepository.saveAll(result.headquarters());
            LOGGER.info("Saved " + result.headquarters().size() + " headquarters");

            // Then save branches
            branchRepository.saveAll(result.branches());
            LOGGER.info("Saved " + result.branches().size() + " branches");

        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Error loading data from Excel file", e);
        }
    }
}
