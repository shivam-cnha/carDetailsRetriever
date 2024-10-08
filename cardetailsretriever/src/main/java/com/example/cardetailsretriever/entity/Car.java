package com.example.cardetailsretriever.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Getter
@Setter
@Table(name = "carDetails")
public class Car 
{
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String modelName;
    private String year;
    private Double price;
    private Double distance;
    private String engineType;
    private String sellerType;
    private String transmission;
    private String owner;



}
