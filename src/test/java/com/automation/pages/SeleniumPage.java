package com.automation.pages;

import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.LoadableComponent;

import static com.automation.BaseTest.getStoredDriver;

public class SeleniumPage extends LoadableComponent<SeleniumPage> {

    private static final String searchInput = "Selenium_(software)";
    private LoadableComponent<HomePage> parent;
    private static final RemoteWebDriver driver = getStoredDriver();

    @FindBy(xpath = "//div[@class='hatnote navigation-not-searchable']")
    private WebElement definition;

    public SeleniumPage(LoadableComponent<HomePage> parent) {
        this.parent = parent;
    }

    private SeleniumPage() {
        PageFactory.initElements(driver, this);
    }

    public static SeleniumPage getSeleniumPage() {
        return new SeleniumPage();
    }

    @Override
    protected void load() {
        parent.get()
                .selectLanguage("English")
                .clearAndType(searchInput)
                .submitButton();
    }

    @Override
    protected void isLoaded() throws Error {
        Assertions.assertTrue(driver.getCurrentUrl().endsWith(searchInput), "Selenium page is not loaded");
    }

    public String getDefinitionText(){
        return definition.getText();
    }
}
