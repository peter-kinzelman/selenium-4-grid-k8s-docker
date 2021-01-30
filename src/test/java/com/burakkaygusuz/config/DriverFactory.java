package com.burakkaygusuz.config;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.UnreachableBrowserException;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class.getName());
    private static WebDriverWait wait;
    private RemoteWebDriver driver;

    private final String HUB_URL = "http://192.168.1.29:4444/wd/hub";

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
                    default:
                        throw new IllegalStateException(String.format("An unexpected driver has been attempted to init: \n %s", driverType.toString()));
                }
            } catch (MalformedURLException e) {
                log.error(String.format("Malformed URL has occurred: \n %s", ExceptionUtils.getStackTrace(e)));
            } catch (UnreachableBrowserException e) {
                log.error(String.format("An error occurred that communicating with the preferred browser or the selenium server: \n %s", ExceptionUtils.getStackTrace(e)));
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
}
