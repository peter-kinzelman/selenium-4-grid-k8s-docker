package com.automation;

import com.automation.config.DriverFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BaseTest {

    public static final List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    public static ThreadLocal<DriverFactory> driverFactoryThread;

    public static final Logger log = LogManager.getLogger(BaseTest.class);
    private static final LoggerContext context = (LoggerContext) LogManager.getContext(false);

    public BaseTest() {
        context.setConfigLocation(new File("src/test/resources/log4j2.properties").toURI()); // Log configuration
    }

    @BeforeAll
    public static void setUp() {
        driverFactoryThread = ThreadLocal.withInitial(() -> {
            DriverFactory driverFactory = new DriverFactory();
            webDriverThreadPool.add(driverFactory);
            return driverFactory;
        });

        log.info("The web driver initialized");

        getWebDriver().get("https://wikipedia.org/");

        log.info("The web page opened");
    }

    public static RemoteWebDriver getWebDriver() {
        return driverFactoryThread.get().getWebDriver();
    }

    public static RemoteWebDriver getStoredDriver() {
        return driverFactoryThread.get().getStoredDriver();
    }

    public static WebDriverWait getDriverWait() {
        return driverFactoryThread.get().getWebDriverWait(getStoredDriver(), 10, 0);
    }

    @AfterAll
    public static void tearDown() {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitWebDriver();
        }

        log.info("The web driver closed");
    }
}
