package ru.netology.bank.data;

import com.github.javafaker.Faker;
import lombok.Value;
import org.checkerframework.checker.units.qual.A;

import java.util.Locale;

public class DataHelper {
    private static final Faker faker = new Faker(new Locale("en"));
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
         String login;
         String password;
    }

    public static AuthInfo getAuthInfoWithDatabase() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getLoginWithDatabase() {
        return new AuthInfo("vasya", generateRandomPassword());
    }

    private static String generateRandomLogin() {
        return faker.name().username();
    }

    private static String generateRandomPassword() {
        return faker.internet().password();
    }

    public static AuthInfo generateRandomUser() {
        return new AuthInfo(generateRandomLogin(), generateRandomPassword());
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode generateRandomVerificationCode() {
        return new VerificationCode(faker.numerify("######"));
    }



}