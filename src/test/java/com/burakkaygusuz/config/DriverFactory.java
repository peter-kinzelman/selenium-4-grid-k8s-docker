package com.burakkaygusuz.config;

import com.burakkaygusuz.enums.Browsers;
import com.burakkaygusuz.exceptions.UnSupportedBrowserException;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import static com.burakkaygusuz.config.DriverType.*;

public class DriverFactory {

    private static final Logger LOGGER = LogManager.getLogger(DriverFactory.class.getName());
    private RemoteWebDriver driver;

    protected static final String HUB_URL = getHubUrl();

    protected RemoteWebDriver getDriver(Browsers browser) {
        if (driver == null) {
            try {
                switch (browser) {
                    case CHROME:
                        driver = CHROME.getDriver();
                        break;
                    case FIREFOX:
                        driver = FIREFOX.getDriver();
                        break;
                    case EDGE:
                        driver = EDGE.getDriver();
                    default:
                        throw new UnSupportedBrowserException(String.format("An unexpected driver has been attempted to init: \n %s", browser));
                }
            } catch (Exception e) {
                LOGGER.error(String.format("An unexpected error has occurred: \n %s", ExceptionUtils.getMessage(e)));
            }
        }

        LOGGER.info(String.format("Browser : %s", driver.getCapabilities().getBrowserName()));
        LOGGER.info(String.format("Version : %s", driver.getCapabilities().getBrowserVersion()));

        return driver;
    }

    protected void quitWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static String getHubUrl() {
        String hubUrl = null;
        try (InputStream inputStream = Runtime.getRuntime().exec("minikube service selenium-router-deployment --url").getInputStream();
             Scanner scanner = new Scanner(inputStream).useDelimiter("\\A")) {
            hubUrl = scanner.hasNext() ? scanner.next() : null;
        } catch (IOException e) {
            LOGGER.error(String.format("An error occurred while getting the URL value: \n %s", ExceptionUtils.getMessage(e)));
        }
        return hubUrl;
    }
}
