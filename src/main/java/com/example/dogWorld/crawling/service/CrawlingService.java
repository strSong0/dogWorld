package com.example.dogWorld.crawling.service;

import com.example.dogWorld.crawling.dto.CrawlingDto;
import com.example.dogWorld.crawling.dto.CrawlingResDto;
import com.example.dogWorld.crawling.entity.AnimalHospital;
import com.example.dogWorld.crawling.repository.AnimalHospitalRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class CrawlingService {
    private final Crawling crawling;
    private final AnimalHospitalRepository animalHospitalRepository;

    public List<CrawlingResDto> crawlingProcess(String cityName) throws InterruptedException {
        List<AnimalHospital> animalHospitals = animalHospitalRepository.findAllByCityName(cityName);

        if (animalHospitals.isEmpty()) {
            return crawling.process(cityName);
        } else {
            List<CrawlingResDto> hospitalInfoList = animalHospitals.stream()
                    .map(CrawlingResDto::fromEntity)
                    .collect(Collectors.toList());
            return hospitalInfoList;
        }
    }
}
