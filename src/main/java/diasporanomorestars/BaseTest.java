package diasporanomorestars;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Screenshots;
import com.google.common.io.Files;
import org.junit.After;
import org.junit.Before;
import ru.yandex.qatools.allure.annotations.Attachment;

import java.io.File;
import java.io.IOException;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.executeJavaScript;
import static com.codeborne.selenide.Selenide.open;
import static diasporanomorestars.DiasporaNoMoreStars.logIn;
import static diasporanomorestars.DiasporaNoMoreStarsLogin.getLogin;
import static diasporanomorestars.DiasporaNoMoreStarsLogin.getPassword;

public class BaseTest {

    static {
        Configuration.timeout = 50000;
    }

    @Before
    public void openPage() {
        open("https://pod.nomorestars.com");
        logIn(getLogin, getPassword);
    }

    @After
    public void postScreenshotAndLogOut() throws IOException {
        screenshot();
        executeJavaScript("localStorage.clear()");
        $(".user-menu-trigger").click();
        $(byText("Log out")).click();
    }

    @Attachment(type = "image/png")
    public byte[] screenshot() throws IOException {
        File screenshot = Screenshots.getScreenShotAsFile();
        return Files.toByteArray(screenshot);
    }
}
