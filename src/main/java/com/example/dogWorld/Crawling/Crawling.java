package com.example.dogWorld.Crawling;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class Crawling {
    private WebDriver driver;
    private static final String url = "https://map.naver.com/p/search/24%EC%8B%9C%20%EB%8F%99%EB%AC%BC%EB%B3%91%EC%9B%90?c=15.00,0,0,2,dh";

    public void process() throws InterruptedException {

        // 크롬 드라이버 경로
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\sunba\\Downloads\\chromedriver-win64\\chromedriver-win64\\chromedriver.exe");

        driver = new ChromeDriver();

        // URL 로 이동
        driver.get(url);

        // 아이프레임으로 전환
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.cssSelector("iframe#searchIframe")));

        List<WebElement> elements = driver.findElements(By.cssSelector(".place_bluelink.C6RjW"));
        System.out.println("elements.size() = " + elements.size());

        List<String> addresses = new ArrayList<>();

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

            System.out.println("주소*******************************************");
            System.out.println("사이즈 : " + placeSectionContents.size());

            for (WebElement placeSectionContent : placeSectionContents) {
                System.out.println(placeSectionContent.getText());
                String address = placeSectionContent.getText();
                addresses.add(address);
            }

            // (병원명 크롤링) **********************
            List<WebElement> nameSectionContents = driver.findElements(By.cssSelector(".Fc1rA"));
            for (WebElement nameSectionContent : nameSectionContents) {
                System.out.println(nameSectionContent.getText());
            }

            // (전화번호 크롤링) **********************
            List<WebElement> phoneSectionContents = driver.findElements(By.cssSelector(".xlx7Q"));
            for (WebElement phoneSectionContent : phoneSectionContents) {
                System.out.println(phoneSectionContent.getText());
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

        for (String address : addresses) {
            System.out.println(address);
        }
    }
}
