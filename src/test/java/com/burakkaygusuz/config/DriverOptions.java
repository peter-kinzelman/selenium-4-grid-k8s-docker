package com.burakkaygusuz.config;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

public interface DriverOptions {
    RemoteWebDriver getDriverWithOptions(URL url, DesiredCapabilities capabilities);
}
