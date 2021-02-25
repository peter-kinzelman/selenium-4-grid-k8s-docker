package com.burakkaygusuz.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Scanner;

public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class.getName());
    private static WebDriverWait wait;
    private RemoteWebDriver driver;

    private static final String HUB_URL = getHubUrl();

    public RemoteWebDriver getWebDriver(DriverType driverType, DesiredCapabilities capabilities) {
        if (driver == null) {
            try {
                switch (driverType) {
                    case CHROME:
                        driver = DriverType.CHROME.getDriverWithOptions(new URL(HUB_URL), capabilities);
                        break;
                    case FIREFOX:
                        driver = DriverType.FIREFOX.getDriverWithOptions(new URL(HUB_URL), capabilities);
                        break;
                    case EDGE:
                        driver = DriverType.EDGE.getDriverWithOptions(new URL(HUB_URL), capabilities);
                    default:
                        throw new IllegalStateException(String.format("An unexpected driver has been attempted to init: \n %s", driverType.toString()));
                }
            } catch (MalformedURLException e) {
                log.error(String.format("Malformed URL has occurred: \n %s", ExceptionUtils.getStackTrace(e)));
            }
        }

        log.info(String.format("%s driver initialized", driverType.toString().toUpperCase()));
        return driver;
    }

    public WebDriverWait getWebDriverWait(RemoteWebDriver driver, int seconds, int milliSeconds) {
        if (wait == null)
            wait = new WebDriverWait(driver, Duration.ofSeconds(seconds), Duration.ofMillis(milliSeconds));
        return wait;
    }

    public void quitWebDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }
    }

    private static String getHubUrl() {
        String hubUrl = null;
        try (InputStream inputStream = Runtime.getRuntime().exec("minikube service selenium-router-deployment --url").getInputStream();
             Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
            hubUrl = s.hasNext() ? s.next() : null;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return hubUrl;
    }
}
