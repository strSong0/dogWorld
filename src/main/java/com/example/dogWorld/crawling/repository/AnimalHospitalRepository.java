package com.example.dogWorld.crawling.repository;

import com.example.dogWorld.crawling.entity.AnimalHospital;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface AnimalHospitalRepository extends JpaRepository<AnimalHospital, Long> {
    Optional<AnimalHospital> findByCityName(String cityName);

    List<AnimalHospital> findAllByCityName(String cityName);
}
