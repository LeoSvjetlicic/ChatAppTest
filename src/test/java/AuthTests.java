import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

class AuthTests {
    // The tests are done with existing users because the app is connected to Firebase firestore
    AndroidDriver driver;
    WebElement usernameField;
    WebElement passwordField;
    WebElement loginButton;
    WebElement changeScreenState;

    @BeforeClass()
    void setUp() throws MalformedURLException {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("app", "D:\\ChatApp\\ChatApp\\app\\build\\outputs\\apk\\debug\\app-debug.apk");
        capabilities.setCapability("VERSION", "9.0");
        capabilities.setCapability("deviceName", "TestPhone");
        capabilities.setCapability("platformName", "Android");
        driver = new AndroidDriver(new URL("http://0.0.0.0:4723/wd/hub"), capabilities);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        passwordField = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[2]"));
        usernameField = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.EditText[1]"));
        loginButton = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.Button"));
        changeScreenState = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.TextView[3]"));

        usernameField.sendKeys("leo.svjetlicic@gmail.com");
        passwordField.sendKeys(""); // removed because the repository of the app being tested is public
    }

    @Test
    void registerTestFail() {
        changeScreenState.click();
        loginButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

        Assert.assertTrue(passwordField.isDisplayed());
    }
    @Test
    void loginTestFail() {
        usernameField.sendKeys("leo.svjetlicic@l.com");
        loginButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

//        Stays on the same screen
        Assert.assertTrue(usernameField.isDisplayed());
    }

    @Test
    void loginTestSuccess() {
        driver.manage().timeouts().implicitlyWait(2000, TimeUnit.MILLISECONDS);
        loginButton.click();
        driver.manage().timeouts().implicitlyWait(500, TimeUnit.MILLISECONDS);

//        Element on the next screen
        WebElement nextScreenElement = driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/androidx.compose.ui.platform.ComposeView/android.view.View/android.view.View/android.view.View/android.view.View/android.view.View/android.widget.TextView[3]"));
        Assert.assertTrue(nextScreenElement.isDisplayed());
    }
    public void teardown() {
        driver.quit();
    }
}