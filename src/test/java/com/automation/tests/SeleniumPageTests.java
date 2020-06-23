package com.automation.tests;

import com.automation.BaseTest;
import com.automation.pages.HomePage;
import com.automation.pages.SeleniumPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.automation.pages.HomePage.getHomePage;
import static com.automation.pages.SeleniumPage.getSeleniumPage;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Selenium Page Tests")
public class SeleniumPageTests extends BaseTest {

    private final HomePage homePage = getHomePage();
    private final SeleniumPage wikiPage = getSeleniumPage();

    @Test
    @DisplayName("Get the wiki page of Selenium")
    public void getSeleniumWikiPage() {

        homePage.selectLanguage("English")
                .clearAndType("Selenium (software)")
                .submitButton();

        assertAll("Selenium wiki page should open as successfully",
                () -> assertEquals("Selenium (software) - Wikipedia", getStoredDriver().getTitle()),
                () -> assertTrue(wikiPage.getDefinitionText().startsWith("This article is about the software testing framework")));
    }
}
