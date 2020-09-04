package com.automation.config;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    private static final Logger log = LogManager.getLogger(DriverFactory.class);
    private static WebDriverWait wait;
    private final DriverType selectedDriverType;
    private RemoteWebDriver driver;

    private final boolean useRemoteWebDriver = Boolean.getBoolean("remoteDriver");

    public DriverFactory() {
        DriverType driverType = DriverType.CHROME;
        String browser = System.getProperty("browser", driverType.toString()).toUpperCase();
        DriverManagerType driverManagerType = DriverManagerType.valueOf(browser.toUpperCase());

        try {
            driverType = DriverType.valueOf(browser);
            WebDriverManager.getInstance(driverManagerType).setup();
        } catch (IllegalArgumentException | NullPointerException ignored) {
            log.error(String.format("Unknown driver or no driver specified, defaulting to '%s'...", driverType));
        } catch (WebDriverManagerException e) {
            log.error(String.format("Setup failed: %s", e.getMessage()));
        }

        selectedDriverType = driverType;
    }

    public RemoteWebDriver getStoredDriver() {
        return driver;
    }

    public RemoteWebDriver getWebDriver() {
        if (driver == null)
            instantiateWebDriver(selectedDriverType);
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

    private void instantiateWebDriver(DriverType driverType) {
        DesiredCapabilities caps = new DesiredCapabilities();

        log.info("Local Operating System      : " + System.getProperty("os.name").toUpperCase());
        log.info("Local Version               : " + System.getProperty("os.arch"));
        log.info("Local Architecture          : " + System.getProperty("os.version"));
        log.info("Browser                     : " + selectedDriverType.toString().toUpperCase());
        log.info("Connecting to Selenium Grid : " + useRemoteWebDriver);

        if (useRemoteWebDriver) {
            String desiredBrowserVersion = System.getProperty("desiredBrowserVersion");
            String desiredPlatform = System.getProperty("desiredPlatform");

            if (desiredPlatform != null && !desiredPlatform.isEmpty())
                caps.setPlatform(Platform.valueOf(desiredPlatform.toUpperCase()));

            if (desiredBrowserVersion != null && !desiredBrowserVersion.isEmpty())
                caps.setVersion(desiredBrowserVersion);

            caps.setBrowserName(selectedDriverType.toString());

            try {
                driver = new RemoteWebDriver(new URL("http://127.0.0.1:4444/wd/hub"), caps);
            } catch (MalformedURLException e) {
                log.error(String.format("An error occurred while the driver instantiates: %s", e.getMessage()));
            }
        } else {
            driver = driverType.getDriverOptions(caps);
        }
    }
}
