package com.example.dogWorld.crawling.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebDriverConfig {

    @Bean
    public WebDriver webDriver() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sunba\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setHeadless(true);
//        chromeOptions.addArguments("--lang=ko");
//        chromeOptions.addArguments("--no-sandbox");
//        chromeOptions.addArguments("--disable-dev-shm-usage");
//        chromeOptions.addArguments("--disable-gpu");
//        chromeOptions.addArguments("--headless");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu","-no-sandbox");
        options.addArguments("window-size=1920x1080");
//        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/121.0.0.0 Safari/537.36");

        options.addArguments("lang=ko_KR");


        return new ChromeDriver(options);
    }
}
