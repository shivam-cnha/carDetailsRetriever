package com.example.cardetailsretriever.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.cardetailsretriever.entity.Car;
import com.example.cardetailsretriever.repository.CarRepository;

@Service
public class CarService  
{
    @Autowired
    private CarRepository carRepository;

    public Car updateCar(Long id,Car carDetails)
    {
        Car car=carRepository.findById(id).orElse(null);
        if(car!=null)
        {
            car.setModelName(carDetails.getModelName());
            car.setYear(carDetails.getYear());
            car.setPrice(carDetails.getPrice());
            car.setDistance(carDetails.getDistance());
            car.setEngineType(carDetails.getEngineType());
            car.setSellerType(carDetails.getSellerType());
            car.setTransmission(carDetails.getTransmission());
            car.setOwner(carDetails.getOwner());
            return carRepository.save(car);
        }
        return null;
        
    }

    public Car getCarById(Long id)
    {
        return carRepository.findById(id).orElse(null);
    }

    public List<Car> getAllCars()
    {
        return carRepository.findAll();
    }

    public void deleteCarById(Long id)
    {
        carRepository.deleteById(id);
    }

    public List<Car>getTopNCarsByPrice(int n)
    {
       return carRepository.findAll().stream().sorted((car1,car2)->car2.getPrice().compareTo(car1.getPrice())).limit(n).toList();
    }

     

}
