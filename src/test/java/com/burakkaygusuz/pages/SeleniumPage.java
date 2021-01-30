package com.burakkaygusuz.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class SeleniumPage {

    @FindBy(xpath = "//div[@class='hatnote navigation-not-searchable']")
    private WebElement definition;

    private SeleniumPage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public static SeleniumPage getSeleniumPage(RemoteWebDriver driver) {
        return new SeleniumPage(driver);
    }

    public String getDefinitionText() {
        return definition.getText();
    }
}
