package com.burakkaygusuz.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {

    private static final Logger log = LogManager.getLogger(HomePage.class.getName());

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@class='pure-button pure-button-primary-progressive']")
    private WebElement searchButton;

    @FindBy(id = "searchLanguage")
    private WebElement selectLanguage;

    private HomePage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public static HomePage getHomePage(RemoteWebDriver driver) {
        return new HomePage(driver);
    }

    public HomePage selectLanguage(String language) {
        Select dropdownList = new Select(selectLanguage);
        dropdownList.selectByVisibleText(language);
        log.info(String.format("The %s language is selected", language));
        return this;
    }

    public HomePage clearAndType(String input) {
        searchInput.clear();
        searchInput.sendKeys(input);
        log.info(String.format("'%s' has been written in %s", input, searchInput));
        return this;
    }

    public HomePage submitButton() {
        searchButton.click();
        log.info(String.format("%s button has been submitted", searchButton));
        return this;
    }
}
