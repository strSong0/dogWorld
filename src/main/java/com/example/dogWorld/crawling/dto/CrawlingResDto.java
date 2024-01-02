package com.example.dogWorld.crawling.dto;


import com.example.dogWorld.crawling.entity.AnimalHospital;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.ToString;

@Data
@AllArgsConstructor
@ToString

public class CrawlingResDto {
    private Long id;
    private String address;
    private String name;
    private String phoneNumber;
    private double x;
    private double y;

    public static CrawlingResDto fromEntity(AnimalHospital animalHospital) {
        return new CrawlingResDto(
                animalHospital.getHospitalId(),
                animalHospital.getAddress(),
                animalHospital.getName(),
                animalHospital.getPhoneNumber(),
                animalHospital.getX(),
                animalHospital.getY()
        );
    }
}
