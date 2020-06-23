package com.automation.config;

import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.logging.Level;
import java.util.logging.Logger;

public enum DriverType implements DriverOptions {

    CHROME {
        @Override
        public RemoteWebDriver getDriverOptions(DesiredCapabilities capabilities) {
            System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);  //To get rid of chrome driver logs

            final ChromeOptions options = new ChromeOptions();
            options.merge(capabilities);

            options.addArguments("test-type");
            options.addArguments("disable-popup-blocking");
            options.addArguments("ignore-certificate-errors");
            options.addArguments("disable-translate");
            options.addArguments("--start-maximized");
            options.addArguments("--no-sandbox");
            options.addArguments("--enable-automation");
            options.addArguments("--headless"); //Headless mode

            return new ChromeDriver(options);
        }
    },

    FIREFOX {
        @Override
        public RemoteWebDriver getDriverOptions(DesiredCapabilities capabilities) {
            System.setProperty(FirefoxDriver.SystemProperty.DRIVER_USE_MARIONETTE, "true");
            System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, "/dev/null");
            Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE); //To get rid of firefox driver logs

            final FirefoxBinary firefoxBinary = new FirefoxBinary();
            final FirefoxOptions options = new FirefoxOptions();
            final FirefoxProfile profile = new FirefoxProfile();

            profile.setAcceptUntrustedCertificates(true);
            profile.setAssumeUntrustedCertificateIssuer(false);
            profile.setPreference("network.proxy.type", 0); //No proxy
            profile.setPreference("dom.webnotifications.enabled", false); //Disable pop-up

            firefoxBinary.addCommandLineOptions("--headless"); //Headless mode

            options.setBinary(firefoxBinary);
            options.setCapability(FirefoxDriver.Capability.PROFILE, profile);

            return new FirefoxDriver(options);
        }
    };

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
