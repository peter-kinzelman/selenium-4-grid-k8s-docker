package com.burakkaygusuz.pages;

import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {

    public BasePage(RemoteWebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
