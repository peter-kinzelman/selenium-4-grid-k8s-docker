package com.burakkaygusuz.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;
import org.openqa.selenium.support.ui.Select;

public class HomePage extends LoadableComponent<HomePage> {

    protected RemoteWebDriver driver;
    private static final String BASE_URL = "https://wikipedia.org";

    @FindBy(id = "searchInput")
    private WebElement searchInput;

    @FindBy(xpath = "//button[@class='pure-button pure-button-primary-progressive']")
    private WebElement searchButton;

    @FindBy(id = "searchLanguage")
    private WebElement selectLanguage;

    private HomePage(RemoteWebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public static HomePage getHomePage(RemoteWebDriver driver) {
        return new HomePage(driver);
    }

    @Override
    protected void load() {
        driver.get(BASE_URL);
    }

    @Override
    protected void isLoaded() throws Error {
        Assertions.assertTrue(driver.getCurrentUrl().contains(BASE_URL), "Wiki page is not loaded");
    }

    public HomePage selectLanguage(String language) {
        Select dropdownList = new Select(selectLanguage);
        dropdownList.selectByVisibleText(language);
        return this;
    }

    public HomePage clearAndType(String input) {
        searchInput.clear();
        searchInput.sendKeys(input);
        return this;
    }

    public HomePage submitButton() {
        searchButton.click();
        return this;
    }
}
