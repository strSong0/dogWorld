package com.example.dogWorld.crawling.entity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Table(name = "Animal_Hospital")
public class AnimalHospital {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hospitalId;

    private String name;
    private String address;
    private String phoneNumber;
    private Double x;
    private Double y;
    private LocalDate crawlingDate;
    private String cityName;


    @Builder
    public AnimalHospital(String name, String address, String phoneNumber, Double x, Double y, LocalDate crawlingDate, String cityName) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.x = x;
        this.y = y;
        this.crawlingDate = crawlingDate;
        this.cityName = cityName;
    }


    public AnimalHospital() {
    }
}
