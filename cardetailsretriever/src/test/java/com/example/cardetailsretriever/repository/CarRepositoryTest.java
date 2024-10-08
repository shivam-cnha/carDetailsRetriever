package com.example.cardetailsretriever.repository;

import com.example.cardetailsretriever.entity.Car;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@DataJpaTest
public class CarRepositoryTest {

    @Mock
    private CarRepository carRepository;

    private Car car;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        car = new Car(1L, "Model S", "2022", 79999.99, 5000.0, "Electric", "Dealer", "Automatic", "First Owner");
    }

    @Test
    public void testSaveCar() {
        when(carRepository.save(car)).thenReturn(car);

        Car savedCar = carRepository.save(car);

        assertEquals(car.getModelName(), savedCar.getModelName());
        verify(carRepository, times(1)).save(car);
    }

    @Test
    public void testFindById_Success() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        Optional<Car> foundCar = carRepository.findById(1L);

        assertTrue(foundCar.isPresent());
        assertEquals(car.getModelName(), foundCar.get().getModelName());
        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testFindById_NotFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Car> foundCar = carRepository.findById(1L);

        assertTrue(foundCar.isEmpty());
        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testDeleteCarById() {
        doNothing().when(carRepository).deleteById(anyLong());

        carRepository.deleteById(1L);

        verify(carRepository, times(1)).deleteById(anyLong());
    }
}

 