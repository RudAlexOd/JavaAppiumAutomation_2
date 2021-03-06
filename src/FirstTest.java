import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.List;

public class FirstTest {
    private AppiumDriver driver;

    @Before
    public void setUp() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android" );
        capabilities.setCapability("deviceName", "AndroidTestDevice" );
        capabilities.setCapability("platformVersion", "8.0" );
        capabilities.setCapability("automationName", "Appium" );
        capabilities.setCapability("appPackage", "org.wikipedia" );
        capabilities.setCapability("appActivity", ".main.MainActivity" );
        capabilities.setCapability("app", "C:\\Users\\ORud\\IdeaProjects\\JavaAppiumAutomation_2\\apks\\org.wikipedia.apk" );

        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub" ), capabilities);
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void cancelSearchTest() {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container" ),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]" ),
                "Java",
                "Cannot find search input",
                5
        );
        assertElementHasText(
               By.xpath("//*[contains(@text, 'Java') and contains(@text, 'Java (programming language)')]"),
                "Java (programming language)",
                "Title with this text not found",
                5
        );
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_close_btn"),
                "Cannot find X to cancel search",
                5

        );
        assertElementHasText(
                By.xpath("//*[contains(@text, 'Search…')]"),
                "Search…",
                "Title with this text not found",
                5
        );

    }
    @Test
    public void checkingWorldsInSearch()
    {
        waitForElementAndClick(
                By.id("org.wikipedia:id/search_container" ),
                "Cannot find 'Search Wikipedia' input",
                5
        );
        waitForElementAndSendKeys(
                By.xpath("//*[contains(@text, 'Search…')]" ),
                "Java",
                "Cannot find search input",
                5
        );
        assertElementHasText(
                By.xpath("//*[contains(@text, 'Java') and contains(@text, 'Java (programming language)')]"),
                "Java (programming language)",
                "Title with this text not found",
                5
        );

        List <WebElement> list = driver.findElements(By.id("org.wikipedia:id/page_list_item_title"));

        for (WebElement i : list)
        {
            String element = i.getText();
            String substring = "java";
            if (element.toLowerCase().contains(substring)) {
                System.out.println(element);
                System.out.println("ok");
            } else {
                Assert.assertEquals(
                        "Article isn`t contains 'java'",
                        substring,
                        element
                );
            }
        }

    }
        private WebElement waitForElementPresent (By by, String error_message,long timeoutInSeconds)
        {
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.withMessage(error_message + "\n" );
            return wait.until(
                    ExpectedConditions.presenceOfElementLocated(by)
            );
        }
        private WebElement waitForElementPresent (By by, String error_message)
        {
            return waitForElementPresent(by, error_message, 5);
        }
        private WebElement waitForElementAndClick (By by, String error_message,long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
            element.click();
            return element;
        }
        private WebElement waitForElementAndSendKeys (By by, String value, String error_message,long timeoutInSeconds)
        {
            WebElement element = waitForElementPresent(by, error_message, timeoutInSeconds);
            element.sendKeys(value);
            return element;
        }
        private void assertElementHasText(By by, String text, String error_massage, long timeoutInSeconds)
        {
        WebElement waitSearchLine = waitForElementPresent(by, error_massage, timeoutInSeconds);
        String value = waitSearchLine.getAttribute("text");
        Assert.assertEquals(error_massage, text, value);
        }



    }


