package com.example.cardetailsretriever.controller;

import com.example.cardetailsretriever.entity.Car;
import com.example.cardetailsretriever.service.CarService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class CarControllerTest {

    @Mock
    private CarService carService;

    @InjectMocks
    private CarController carController;

    private Car car;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        car = new Car(1L, "Model S", "2022", 79999.99, 5000.0, "Electric", "Dealer", "Automatic", "First Owner");
    }

    @Test
    public void testGetCarById_Success() {
        when(carService.getCarById(anyLong())).thenReturn(car);

        ResponseEntity<Car> response = carController.getCarById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(car, response.getBody());
        verify(carService, times(1)).getCarById(anyLong());
    }

    @Test
    public void testGetCarById_NotFound() {
        when(carService.getCarById(anyLong())).thenReturn(null);

        ResponseEntity<Car> response = carController.getCarById(1L);

        assertEquals(404, response.getStatusCodeValue());
        verify(carService, times(1)).getCarById(anyLong());
    }

    @Test
    public void testUpdateCarById_Success() {
        when(carService.updateCar(anyLong(), any(Car.class))).thenReturn(car);

        ResponseEntity<Car> response = carController.updateCarById(1L, car);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(car, response.getBody());
        verify(carService, times(1)).updateCar(anyLong(), any(Car.class));
    }

    @Test
    public void testUpdateCarById_NotFound() {
        when(carService.updateCar(anyLong(), any(Car.class))).thenReturn(null);

        ResponseEntity<Car> response = carController.updateCarById(1L, car);

        assertEquals(404, response.getStatusCodeValue());
        verify(carService, times(1)).updateCar(anyLong(), any(Car.class));
    }

    @Test
    public void testDeleteCarById() {
        doNothing().when(carService).deleteCarById(anyLong());

        ResponseEntity<Void> response = carController.deleteCarById(1L);

        assertEquals(200, response.getStatusCodeValue());
        verify(carService, times(1)).deleteCarById(anyLong());
    }

    @Test
    public void testGetAllCars() {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        when(carService.getAllCars()).thenReturn(cars);

        List<Car> response = carController.getAllCars();

        assertEquals(1, response.size());
        assertEquals(car, response.get(0));
        verify(carService, times(1)).getAllCars();
    }

    @Test
    public void testGetTopCarsByPrice() {
        List<Car> cars = new ArrayList<>();
        cars.add(car);
        when(carService.getTopNCarsByPrice(anyInt())).thenReturn(cars);

        ResponseEntity<List<Car>> response = carController.getTopCarsByPrice(1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals(car, response.getBody().get(0));
        verify(carService, times(1)).getTopNCarsByPrice(anyInt());
    }
}
