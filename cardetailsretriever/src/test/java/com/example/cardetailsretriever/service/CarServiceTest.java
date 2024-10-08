package com.example.cardetailsretriever.service;

import com.example.cardetailsretriever.entity.Car;
import com.example.cardetailsretriever.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CarServiceTest {

    @Mock
    private CarRepository carRepository;

    @InjectMocks
    private CarService carService;

    private Car car;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        car = new Car(1L, "Model S", "2022", 79999.99, 5000.0, "Electric", "Dealer", "Automatic", "First Owner");
    }

    @Test
    public void testUpdateCar_Success() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));
        when(carRepository.save(any(Car.class))).thenReturn(car);

        Car updatedCar = carService.updateCar(1L, car);

        assertEquals("Model S", updatedCar.getModelName());
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any(Car.class));
    }

    @Test
    public void testUpdateCar_NotFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        Car updatedCar = carService.updateCar(1L, car);

        assertNull(updatedCar);
        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, never()).save(any(Car.class));
    }

    @Test
    public void testGetCarById_Success() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.of(car));

        Car foundCar = carService.getCarById(1L);

        assertEquals("Model S", foundCar.getModelName());
        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetCarById_NotFound() {
        when(carRepository.findById(anyLong())).thenReturn(Optional.empty());

        Car foundCar = carService.getCarById(1L);

        assertNull(foundCar);
        verify(carRepository, times(1)).findById(anyLong());
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> foundCars = carService.getAllCars();

        assertEquals(1, foundCars.size());
        assertEquals(car, foundCars.get(0));
        verify(carRepository, times(1)).findAll();
    }

    @Test
    public void testDeleteCarById() {
        doNothing().when(carRepository).deleteById(anyLong());

        carService.deleteCarById(1L);

        verify(carRepository, times(1)).deleteById(anyLong());
    }

    @Test
    public void testGetTopNCarsByPrice() {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        when(carRepository.findAll()).thenReturn(cars);

        List<Car> topCars = carService.getTopNCarsByPrice(1);

        assertEquals(1, topCars.size());
        assertEquals(car, topCars.get(0));
        verify(carRepository, times(1)).findAll();
    }
}
