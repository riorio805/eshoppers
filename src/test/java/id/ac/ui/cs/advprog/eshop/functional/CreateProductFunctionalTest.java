package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class CreateProductFunctionalTest {
    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void testCreateProductPage(ChromeDriver driver) throws Exception {
        driver.get(baseUrl + "/product/create");
        WebElement nameInput = driver.findElement(By.id("nameInput"));
        WebElement quantityInput = driver.findElement(By.id("quantityInput"));

        nameInput.clear();
        nameInput.sendKeys("Sampo Cap Bambang");

        quantityInput.clear();
        quantityInput.sendKeys("123");

        // create product
        driver.findElement(By.tagName("button")).click();

        // get first item information
        List<WebElement> elements = driver.findElement(By.tagName("tbody"))
                                          .findElement(By.tagName("tr"))
                                          .findElements(By.tagName("td"));


        // check name
        assertEquals("Sampo Cap Bambang", elements.get(0).getText());
        // check quantity
        assertEquals("123", elements.get(1).getText());
    }
}
