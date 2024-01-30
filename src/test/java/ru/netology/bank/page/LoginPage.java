package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.Keys;
import ru.netology.bank.data.DataHelper;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class LoginPage {
    private final SelenideElement login = $("[data-test-id='login'] input");
    private final SelenideElement password = $("[data-test-id='password'] input");
    private final SelenideElement button = $("[data-test-id='action-login']");
    private final SelenideElement errorText = $("[data-test-id='error-notification'] " +
            ".notification__content");

    public void verifyErrorNotification(String expectedText) {
        errorText.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public VerificationPage validLogin(DataHelper.AuthInfo info) {
        login.setValue(info.getLogin());
        password.setValue(info.getPassword());
        button.click();
        return new VerificationPage();
    }

    public void cleanLoginAndPassword() {
        login.doubleClick().sendKeys(Keys.DELETE);
        password.doubleClick().sendKeys(Keys.DELETE);
    }
}
