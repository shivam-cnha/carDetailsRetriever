package com.example.cardetailsretriever.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.cardetailsretriever.entity.Car;
import com.example.cardetailsretriever.service.CarService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/cars")
public class CarController 
{
    @Autowired
    private CarService carService;

    @GetMapping("/{id}")
    public ResponseEntity<Car> getCarById(@PathVariable Long id)
    {
        Car car= carService.getCarById(id);
        return car!=null ? ResponseEntity.ok(car):ResponseEntity.notFound().build();
    }
    @PutMapping("/{id}")
    public ResponseEntity<Car> updateCarById(@PathVariable Long id, @RequestBody Car carDetails) {
      Car updatedCar=carService.updateCar(id, carDetails);
      return updatedCar!=null ?ResponseEntity.ok(updatedCar):ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCarById(@PathVariable Long id) {
        carService.deleteCarById(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/")
    public List<Car> getAllCars() {
        return carService.getAllCars();
    }
 
    @GetMapping("/top/{n}")
    public ResponseEntity<List<Car>> getTopCarsByPrice(@PathVariable int n) {
        List<Car> cars= carService.getTopNCarsByPrice(n);
        return ResponseEntity.ok(cars);
    }

}