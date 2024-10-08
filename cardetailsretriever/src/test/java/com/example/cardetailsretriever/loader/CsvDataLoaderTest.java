package com.example.cardetailsretriever.loader;

import com.example.cardetailsretriever.entity.Car;
import com.example.cardetailsretriever.repository.CarRepository;
import com.example.cardetailsretriever.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class CsvDataLoaderTest {

    @Mock
    private CarService carService;

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CsvDataLoader csvDataLoader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRun_DataLoadedSuccessfully() throws Exception {
        // Mock the carService to return an empty list, indicating no data is present
        when(carService.getAllCars()).thenReturn(Collections.emptyList());

        // Mock the input CSV data
        BufferedReader reader = new BufferedReader(new InputStreamReader(new ClassPathResource("CAR_DETAILS_DATA.csv").getInputStream()));
        when(new BufferedReader(any(InputStreamReader.class))).thenReturn(reader);

        // Run the data loader
        csvDataLoader.run();

        // Verify that the data was loaded into the repository
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testRun_DataAlreadyExists() throws Exception {
        // Mock the carService to return a non-empty list, indicating data is already present
        when(carService.getAllCars()).thenReturn(Collections.singletonList(new Car()));

        // Run the data loader
        csvDataLoader.run();

        // Verify that the repository save method was never called
        verify(carRepository, never()).save(any(Car.class));
    }
}
