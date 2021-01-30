package com.burakkaygusuz;

import com.burakkaygusuz.config.DriverFactory;
import com.burakkaygusuz.config.DriverType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.junit.jupiter.api.*;
import org.openqa.selenium.remote.DesiredCapabilities;
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

    public RemoteWebDriver driver;
    public WebDriverWait wait;

    public BaseTest() {
        context.setConfigLocation(new File("src/test/resources/log4j2.properties").toURI());
    }

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
        log.info(String.format("Test: %s started", testInfo.getDisplayName()));
    }

    @AfterEach
    public void afterEach(TestInfo testInfo) {
        log.info(String.format("Test: %s finished", testInfo.getDisplayName()));
    }

    public static RemoteWebDriver getWebDriver(DriverType driverType, DesiredCapabilities capabilities) {
        return driverFactoryThread.get().getWebDriver(driverType, capabilities);
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
