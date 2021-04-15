package com.burakkaygusuz.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.AbstractDriverOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.burakkaygusuz.config.DriverFactory.*;

public enum DriverType {

    CHROME {
        @Override
        public RemoteWebDriver getDriver() throws MalformedURLException {
            return new RemoteWebDriver(new URL(HUB_URL), CHROME.getOptions());
        }

        @Override
        public ChromeOptions getOptions() {
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 2);

            final ChromeOptions chromeOptions = new ChromeOptions();

            chromeOptions.setCapability(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            chromeOptions.setCapability(ChromeDriverService.CHROME_DRIVER_VERBOSE_LOG_PROPERTY, "true");
            chromeOptions.setAcceptInsecureCerts(true);
            chromeOptions.setHeadless(true);
            chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            chromeOptions.addArguments("enable-automation");
            chromeOptions.addArguments("disable-translate");
            chromeOptions.addArguments("--disable-gpu");
            chromeOptions.addArguments("--start-maximized");
            chromeOptions.setExperimentalOption("prefs", prefs);

            return chromeOptions;
        }
    },

    FIREFOX {
        @Override
        public RemoteWebDriver getDriver() throws MalformedURLException {
            return new RemoteWebDriver(new URL(HUB_URL), FIREFOX.getOptions());
        }

        @Override
        public FirefoxOptions getOptions() {
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

            final FirefoxOptions firefoxOptions = new FirefoxOptions();
            final FirefoxProfile firefoxProfile = new FirefoxProfile();

            firefoxProfile.setPreference(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, true);
            firefoxOptions.setAcceptInsecureCerts(true);
            firefoxOptions.addPreference("dom.webnotifications.enabled", false);
            firefoxOptions.addPreference("gfx.direct2d.disabled", true);
            firefoxOptions.addPreference("layers.acceleration.disabled", true);
            firefoxOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            firefoxOptions.setHeadless(true);
            firefoxOptions.setProfile(firefoxProfile);

            return firefoxOptions;
        }
    },

    EDGE {
        @Override
        public RemoteWebDriver getDriver() throws MalformedURLException {
            return new RemoteWebDriver(new URL(HUB_URL), EDGE.getOptions());
        }

        @Override
        public EdgeOptions getOptions() {

            final EdgeOptions edgeOptions = new EdgeOptions();

            edgeOptions.setCapability(EdgeDriverService.EDGE_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            edgeOptions.setCapability(EdgeDriverService.EDGE_DRIVER_VERBOSE_LOG_PROPERTY, "true");
            edgeOptions.addArguments("--start-maximized");
            edgeOptions.addArguments("disable-gpu");
            edgeOptions.setHeadless(true);
            edgeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            edgeOptions.setAcceptInsecureCerts(true);

            return edgeOptions;
        }
    };

    protected abstract RemoteWebDriver getDriver() throws MalformedURLException;

    protected abstract AbstractDriverOptions<?> getOptions();
}
