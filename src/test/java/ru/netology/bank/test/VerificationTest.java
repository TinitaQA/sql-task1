package ru.netology.bank.test;

import org.junit.jupiter.api.*;
import ru.netology.bank.data.DataHelper;
import ru.netology.bank.data.SQLHelper;
import ru.netology.bank.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static ru.netology.bank.data.SQLHelper.cleanAuthCode;
import static ru.netology.bank.data.SQLHelper.cleanDatabase;

public class VerificationTest {
    LoginPage loginPage;

    @AfterEach
    void tearDown() {
        cleanAuthCode();
    }

    @AfterAll
    static void tearDownAll() {
        cleanDatabase();
    }

    @BeforeEach
    void setUp() {
        loginPage = open("http://localhost:9999", LoginPage.class);
    }

    @Test
    void shouldSuccessLogin() {
        var authInfo = DataHelper.getAuthInfoWithDatabase();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = SQLHelper.getVerificationCode();
        verificationPage.validVerify(verificationCode.getCode());
    }

    @Test
    void shouldGetErrorIfLoginWithRandomUserNotAddingInDatabase() {
        var authInfo = DataHelper.generateRandomUser();
        loginPage.validLogin(authInfo);
        loginPage.verifyErrorNotification("Ошибка! \nНеверно указан логин или пароль");
    }

    @Test
    void shouldGetErrorIfUserExistInBaseAndRandomCode() {
        var authInfo = DataHelper.getAuthInfoWithDatabase();
        var verificationPage = loginPage.validLogin(authInfo);
        verificationPage.verifyVerificationPageVisibility();
        var verificationCode = DataHelper.generateRandomVerificationCode();
        verificationPage.verify(verificationCode.getCode());
        verificationPage.verifyErrorNotification("Ошибка! \nНеверно указан код! Попробуйте ещё раз.");
    }

    @Test
    void shouldBlockWhenEnteredWrongPasswordThreeTimes() {
        var authInfo = DataHelper.getLoginWithDatabase();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginAndPassword();

        authInfo = DataHelper.getLoginWithDatabase();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginAndPassword();

        authInfo = DataHelper.getLoginWithDatabase();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginAndPassword();

        authInfo = DataHelper.getLoginWithDatabase();
        loginPage.validLogin(authInfo);
        loginPage.cleanLoginAndPassword();
        loginPage.verifyErrorNotification("Ошибка! \nПароль введен неверно 3 раза, " +
                "пользователь закблокирован");
    }
}
