package com.burakkaygusuz.config;

import com.burakkaygusuz.enums.Browsers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DriverBase {

    private static final Logger LOGGER = LogManager.getLogger(DriverBase.class.getName());
    private static final List<DriverFactory> threadPool = Collections.synchronizedList(new ArrayList<>());
    private static ThreadLocal<DriverFactory> driverThread;

    @BeforeAll
    public void instantiateDriverObject() {

        LOGGER.info(String.format("Operating System : %s", System.getProperty("os.name").toUpperCase()));
        LOGGER.info(String.format("Version          : %s", System.getProperty("os.version")));
        LOGGER.info(String.format("Arch             : %s", System.getProperty("os.arch")));
        LOGGER.info(String.format("Grid URL         : %s", DriverFactory.HUB_URL));
        LOGGER.info(String.format("Tests running on %d cores...)", Runtime.getRuntime().availableProcessors()));

        driverThread = ThreadLocal.withInitial(() -> {
            DriverFactory factory = new DriverFactory();
            threadPool.add(factory);
            return factory;
        });
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        LOGGER.info(String.format("Test: %s started", testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        LOGGER.info(String.format("Test: %s finished", testInfo.getDisplayName()));
    }

    @AfterAll
    public void closeDriverObject() {
        threadPool.forEach(DriverFactory::quitWebDriver);
        driverThread.remove();
    }

    public static RemoteWebDriver getDriver(Browsers browser) {
        return driverThread.get().getDriver(browser);
    }
}
