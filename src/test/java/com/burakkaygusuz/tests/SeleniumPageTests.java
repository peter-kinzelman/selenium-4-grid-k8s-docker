package com.burakkaygusuz.tests;

import com.burakkaygusuz.config.DriverBase;
import com.burakkaygusuz.enums.Browsers;
import com.burakkaygusuz.pages.HomePage;
import com.burakkaygusuz.pages.SeleniumPage;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.burakkaygusuz.pages.HomePage.getHomePage;
import static com.burakkaygusuz.pages.SeleniumPage.getSeleniumPage;

@Execution(ExecutionMode.CONCURRENT)
@DisplayName("Selenium Wikipedia Page Tests")
public class SeleniumPageTests extends DriverBase {

    @ParameterizedTest(name = "#{index} - Run with => {0}")
    @EnumSource(value = Browsers.class, names = {"CHROME", "FIREFOX"})
    public void navigateToSeleniumWikiPage(Browsers browser) {

        RemoteWebDriver driver = DriverBase.getDriver(browser);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10), Duration.ofMillis(1000));

        final HomePage homePage = getHomePage(driver);
        final SeleniumPage wikiPage = getSeleniumPage(driver);

        driver.navigate().to("https://wikipedia.org");
        homePage.goToSeleniumWikiPage();

        wait.until(ExpectedConditions.urlContains("https://en.wikipedia.org/wiki/Selenium_(software)"));

        Assertions.assertThat(driver.getTitle())
                .withFailMessage(String.format("Expected page title is 'Selenium (software) - Wikipedia' but was '%s'", driver.getTitle()))
                .isEqualTo("Selenium (software) - Wikipedia");

        Assertions.assertThat(wikiPage.getShortDescription())
                .withFailMessage("Short description is different")
                .startsWith("This article is about the software testing framework");
    }

    @Test
    @DisplayName("Verify The Github Url Is Correct")
    public void verifyTheGithubPageUrlIsCorrect() {

        RemoteWebDriver driver = DriverBase.getDriver(Browsers.EDGE);
        driver.navigate().to("https://en.wikipedia.org/wiki/Selenium_(software)");

        final SeleniumPage wikiPage = getSeleniumPage(driver);

        Assertions.assertThat(wikiPage.getGitHubPageUrl())
                .withFailMessage(String.format("Expected Github URL is 'github.com/SeleniumHQ/' but was '%s'", wikiPage.getGitHubPageUrl()))
                .isEqualTo("github.com/SeleniumHQ/");
    }
}
