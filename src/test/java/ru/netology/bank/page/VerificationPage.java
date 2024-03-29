package ru.netology.bank.page;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class VerificationPage {
    private final SelenideElement code = $("[data-test-id='code'] input");
    private final SelenideElement button = $("[data-test-id='action-verify']");
    private final SelenideElement errorText = $("[data-test-id='error-notification'] " +
            ".notification__content");

    public void verifyVerificationPageVisibility() {
        code.shouldBe(visible);
    }

    public void verifyErrorNotification(String expectedText) {
        errorText.shouldHave(exactText(expectedText)).shouldBe(visible);
    }

    public DashboardPage validVerify(String verificationCode) {
        verify(verificationCode);
        return new DashboardPage();
    }

    public void verify(String verificationCode) {
        code.setValue(verificationCode);
        button.click();
    }
}
