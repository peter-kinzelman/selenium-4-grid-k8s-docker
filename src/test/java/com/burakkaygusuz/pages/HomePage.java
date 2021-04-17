package com.burakkaygusuz.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    private static final Logger LOGGER = LogManager.getLogger(HomePage.class.getName());

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(id = "searchLanguage")
    private WebElement selectLanguage;

    private HomePage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public static HomePage getHomePage(RemoteWebDriver driver) {
        return new HomePage(driver);
    }

    private HomePage selectLanguage() {
        Select dropdownList = new Select(selectLanguage);
        dropdownList.selectByVisibleText("English");
        LOGGER.info(String.format("The English language was selected"));
        return this;
    }

    private HomePage searchTheItem() {
        searchInput.clear();
        searchInput.sendKeys("Selenium (software)" + Keys.ENTER);
        LOGGER.info(String.format("'Selenium (software)' was written in %s input", searchInput.getAttribute("name")));
        return this;
    }

    public void goToSeleniumWikiPage() {
        selectLanguage().searchTheItem();
    }

}
