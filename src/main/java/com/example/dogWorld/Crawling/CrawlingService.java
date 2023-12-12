package com.example.dogWorld.Crawling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CrawlingService {
    public List<CrawlingDto> crawlingProcess(String cityName) throws InterruptedException {
        Crawling crawling = new Crawling();
        return crawling.process(cityName);
    }

}
