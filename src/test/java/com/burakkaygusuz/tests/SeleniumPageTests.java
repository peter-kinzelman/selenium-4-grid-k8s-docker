package com.burakkaygusuz.tests;

import com.burakkaygusuz.BaseTest;
import com.burakkaygusuz.config.DriverType;
import com.burakkaygusuz.pages.HomePage;
import com.burakkaygusuz.pages.SeleniumPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;

import static com.burakkaygusuz.pages.HomePage.getHomePage;
import static com.burakkaygusuz.pages.SeleniumPage.getSeleniumPage;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Selenium Page Tests")
public class SeleniumPageTests extends BaseTest {

    @ParameterizedTest(name = "#{index} - Run with => {0}")
    @EnumSource(value = DriverType.class, names = {"CHROME", "FIREFOX"})
    public void getSeleniumWikiPage(DriverType driverType) {

        DesiredCapabilities capabilities = new DesiredCapabilities();
        driver = getWebDriver(driverType, capabilities);
        wait = getDriverWait(driver);

        final HomePage homePage = getHomePage(driver);
        final SeleniumPage wikiPage = getSeleniumPage(driver);

        driver.navigate().to("https://wikipedia.org");

        homePage.selectLanguage("English")
                .clearAndType("Selenium (software)")
                .submitButton();

        wait.until(ExpectedConditions.urlContains("https://en.wikipedia.org/wiki/Selenium_(software)"));

        assertAll("Selenium wiki page should open as successfully",
                () -> assertEquals("Selenium (software) - Wikipedia", driver.getTitle()),
                () -> assertTrue(wikiPage.getDefinitionText().startsWith("This article is about the software testing framework")));
    }
}
