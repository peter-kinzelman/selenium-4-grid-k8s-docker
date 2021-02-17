package com.burakkaygusuz.config;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public enum DriverType implements DriverOptions {

    CHROME {
        @Override
        public RemoteWebDriver getDriverWithOptions(URL url, DesiredCapabilities capabilities) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

            Map<String, Object> prefs = new HashMap<>();
            prefs.put("profile.default_content_setting_values.notifications", 1);

            final ChromeOptions options = new ChromeOptions();

            options.addArguments("enable-automation");
            options.addArguments("--start-maximized");
            options.addArguments("--window-size=1920,1080");
            options.addArguments("--disable-gpu");
            options.addArguments("disable-translate");
            options.setHeadless(true);
            options.setAcceptInsecureCerts(true);
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            options.setExperimentalOption("prefs", prefs);

            capabilities.setCapability(ChromeOptions.CAPABILITY, options);

            return new RemoteWebDriver(url, capabilities);
        }
    },

    FIREFOX {
        @Override
        public RemoteWebDriver getDriverWithOptions(URL url, DesiredCapabilities capabilities) {
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

            final FirefoxOptions options = new FirefoxOptions();
            final FirefoxProfile profile = new FirefoxProfile();

            profile.setPreference(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, true);

            options.setAcceptInsecureCerts(true);
            options.addPreference("dom.webnotifications.enabled", false);
            options.addPreference("gfx.direct2d.disabled", true);
            options.addPreference("layers.acceleration.disabled", true);
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
            options.setHeadless(true);
            options.setProfile(profile);

            capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);

            return new RemoteWebDriver(url, capabilities);
        }
    },

    EDGE {
        @Override
        public RemoteWebDriver getDriverWithOptions(URL url, DesiredCapabilities capabilities) {
            System.setProperty(EdgeDriverService.EDGE_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

            final EdgeOptions options = new EdgeOptions();

            options.setAcceptInsecureCerts(true);
            options.addArguments("disable-gpu");
            options.setHeadless(true);
            options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            capabilities.setCapability(EdgeOptions.CAPABILITY, options);

            return new RemoteWebDriver(url, capabilities);
        }
    };

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
