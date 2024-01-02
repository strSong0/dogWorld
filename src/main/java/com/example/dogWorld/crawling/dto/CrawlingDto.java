package com.example.dogWorld.crawling.dto;

import com.example.dogWorld.crawling.entity.AnimalHospital;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Data
@AllArgsConstructor
@ToString
@NoArgsConstructor
public class CrawlingDto {
    private String address;
    private String name;
    private String phoneNumber;
    private double x;
    private double y;
    private LocalDate crawlingDate;
    private String cityName;


    public AnimalHospital toEntity() {
        return AnimalHospital.builder()
                .address(address)
                .name(name)
                .phoneNumber(phoneNumber)
                .x(x)
                .y(y)
                .crawlingDate(crawlingDate)
                .cityName(cityName)
                .build();
    }



}
