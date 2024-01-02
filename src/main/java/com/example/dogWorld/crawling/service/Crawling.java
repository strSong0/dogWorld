package com.example.dogWorld.crawling.service;

import com.example.dogWorld.crawling.dto.CrawlingDto;
import com.example.dogWorld.crawling.dto.CrawlingResDto;
import com.example.dogWorld.crawling.entity.AnimalHospital;
import com.example.dogWorld.crawling.repository.AnimalHospitalRepository;
import lombok.AllArgsConstructor;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class Crawling {
    private WebDriver driver;
    private final AnimalHospitalRepository animalHospitalRepository;

    public List<CrawlingResDto> process(String cityName) throws InterruptedException {

        // 크롬 드라이버 경로
        //System.setProperty("webdriver.chrome.driver", "C:\\Users\\sunba\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        //driver = new ChromeDriver();
        System.out.println(cityName);
        String url = "https://map.naver.com/";

        // URL 로 이동
        driver.get(url);

        // 도시명 검색
        WebElement searchBox = driver.findElement(By.cssSelector(".input_search"));
        searchBox.sendKeys(cityName + " 24시 동물병원", Keys.RETURN);

        System.out.println("검색어 입력: " + searchBox.getAttribute("value"));  // 검색어 출력
        Thread.sleep(5000);

        // 아이프레임으로 전환
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe#searchIframe")));


        List<WebElement> elements = driver.findElements(By.cssSelector(".place_bluelink.C6RjW"));
        System.out.println("elements.size() = " + elements.size());

        List<CrawlingResDto> hospitalInfoList = new ArrayList<>();

        for (int i = 0; i < elements.size(); i++) {
            WebElement element = elements.get(i);

            // 요소를 클릭하기 전에 해당 요소가 클릭 가능한 상태가 될 때까지 대기
            wait.until(ExpectedConditions.elementToBeClickable(element));

            element.click();

            // 대기 후에 iframe으로 이동
            driver.switchTo().defaultContent();  // 최상위 컨텐츠로 이동

            Thread.sleep(1000);
            // entryIframe을 찾을 때까지 대기
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe#entryIframe")));

            Thread.sleep(1000);

            // entryIframe 내에서 작업 수행
            // (주소 크롤링) **********************
            List<WebElement> placeSectionContents = driver.findElements(By.cssSelector(".PkgBl"));
            List<WebElement> nameSectionContents = driver.findElements(By.cssSelector(".Fc1rA"));
            List<WebElement> phoneSectionContents = driver.findElements(By.cssSelector(".xlx7Q"));

            System.out.println("요소 확인*******************************************");
            System.out.println("사이즈 : " + placeSectionContents.size());
            System.out.println("사이즈 : " + nameSectionContents.size());
            System.out.println("사이즈 : " + phoneSectionContents.size());

            int minSize = Math.min(placeSectionContents.size(), Math.min(nameSectionContents.size(), phoneSectionContents.size()));

            for (int j = 0; j < minSize; j++) {
                CrawlingDto crawlingDto = new CrawlingDto();
                crawlingDto.setAddress(placeSectionContents.get(j).getText());
                crawlingDto.setName(nameSectionContents.get(j).getText());
                crawlingDto.setPhoneNumber(phoneSectionContents.get(j).getText());

                // 좌표 설정
                Coordinate coordinate = GeoCoding.geocode(crawlingDto.getAddress());
                if (coordinate != null) {
                    crawlingDto.setX(coordinate.getX());
                    crawlingDto.setY(coordinate.getY());
                }

                crawlingDto.setCityName(cityName);

                LocalDate now = LocalDate.now();
                crawlingDto.setCrawlingDate(now);

                AnimalHospital animalHospital = crawlingDto.toEntity();
                AnimalHospital savedHospital = animalHospitalRepository.save(animalHospital);
                CrawlingResDto crawlingResDto = CrawlingResDto.fromEntity(savedHospital);

                hospitalInfoList.add(crawlingResDto);
            }

            // 최상위 컨텐츠로 이동
            driver.switchTo().defaultContent();

            // searchIframe으로 다시 이동
            wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe#searchIframe")));

            // 다음 요소를 찾기 전에 명시적인 대기 추가
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".place_bluelink.C6RjW")));

            // 다음 요소 찾기
            driver.findElements(By.cssSelector(".place_bluelink.C6RjW"));
        }
        return hospitalInfoList;
    }
}
