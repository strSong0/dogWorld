package com.example.dogWorld.crawling.controller;

import com.example.dogWorld.crawling.dto.CrawlingResDto;
import com.example.dogWorld.crawling.service.CrawlingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CrawlingController {

    private final CrawlingService crawlingService;

    public CrawlingController(CrawlingService crawlingService) {
        this.crawlingService = crawlingService;
    }

    @GetMapping("/crawl/{cityName}")
    public List<CrawlingResDto> crawlAndReturnInfo(@PathVariable String cityName) throws InterruptedException {
        return crawlingService.crawlingProcess(cityName);
    }
}
