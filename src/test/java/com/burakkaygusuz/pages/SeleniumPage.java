package com.burakkaygusuz.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;

public class SeleniumPage extends BasePage {

    @FindBy(xpath = "//div[@class='hatnote navigation-not-searchable']")
    private WebElement definition;

    @FindBy(xpath = "//div[@class='plainlist']//ul//li//span[@class='url']")
    private WebElement gitHubPageUrl;

    public SeleniumPage(RemoteWebDriver driver) {
        super(driver);
    }

    public static SeleniumPage getSeleniumPage(RemoteWebDriver driver) {
        return new SeleniumPage(driver);
    }

    public String getShortDescription() {
        return definition.getText();
    }

    public String getGitHubPageUrl() {
        return gitHubPageUrl.getText();
    }
}
