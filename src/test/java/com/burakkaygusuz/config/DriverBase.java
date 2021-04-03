package com.burakkaygusuz;

import com.burakkaygusuz.config.DriverFactory;
import com.burakkaygusuz.enums.Browsers;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Execution(ExecutionMode.CONCURRENT)
public class DriverBase {

    public static final List<DriverFactory> webDriverThreadPool = Collections.synchronizedList(new ArrayList<>());
    public static ThreadLocal<DriverFactory> driverFactoryThread;

    private static final Logger logger = LogManager.getLogger(DriverBase.class.getName());

    protected RemoteWebDriver driver;
    protected WebDriverWait wait;

    @BeforeAll
    public static void setUp() {
        driverFactoryThread = ThreadLocal.withInitial(() -> {
            DriverFactory driverFactory = new DriverFactory();
            webDriverThreadPool.add(driverFactory);
            return driverFactory;
        });
    }

    @BeforeEach
    public void beforeEach(TestInfo testInfo) {
        logger.info(String.format("Test: %s started", testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        logger.info(String.format("Test: %s finished", testInfo.getDisplayName()));
    }

    public static RemoteWebDriver getWebDriver(Browsers browser) {
        return driverFactoryThread.get().getWebDriver(browser);
    }

    public static WebDriverWait getDriverWait(RemoteWebDriver driver) {
        return driverFactoryThread.get().getWebDriverWait(driver, 10, 0);
    }

    @AfterAll
    public static void tearDown() {
        for (DriverFactory driverFactory : webDriverThreadPool) {
            driverFactory.quitWebDriver();
        }
    }
}
