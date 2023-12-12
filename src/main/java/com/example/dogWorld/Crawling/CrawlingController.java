package com.example.dogWorld.Crawling;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api")
public class CrawlingController {

    @Autowired
    private CrawlingService crawlingService;

    @GetMapping("/crawl/{cityName}")
    public List<CrawlingDto> crawlAndReturnInfo(@PathVariable String cityName) throws InterruptedException {
        return crawlingService.crawlingProcess(cityName);
    }
}
