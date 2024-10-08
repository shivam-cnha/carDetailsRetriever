package com.example.cardetailsretriever.loader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
 
import java.io.BufferedReader;
import java.io.InputStreamReader;
 
import com.example.cardetailsretriever.entity.Car;
import com.example.cardetailsretriever.repository.CarRepository;
import com.example.cardetailsretriever.service.CarService;
 
@Component
public class CsvDataLoader implements CommandLineRunner {
 
    @Autowired
    private CarService carService;

    @Autowired
    private CarRepository carRepository;
 
    @Override
    public void run(String... args) throws Exception {
        // Check if the data is already present in the database
        if (carService.getAllCars().isEmpty()) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("CAR_DETAILS_DATA.csv").getInputStream()));
 
            String line;
            reader.readLine(); // Skip the header
 
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                Car carDetails = new Car(
                    null,
                    fields[0],
                    fields[1],
                    Double.valueOf(fields[2]),
                    Double.valueOf(fields[3]),
                    fields[4],
                    fields[5],
                    fields[6],
                    fields[7]
                );
                carRepository.save(carDetails);
            }
 
            System.out.println("Data Loaded Successfully!");
        } else {
            System.out.println("Data already exists in the database. Skipping loading.");
        }
    }
}
