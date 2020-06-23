package com.automation.config;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public interface DriverOptions {

    RemoteWebDriver getDriverOptions(DesiredCapabilities capabilities);
}
