package ru.netology.data;

import lombok.Value;

public class DataHelper {

    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    public static AuthInfo getOtherUserInfo(AuthInfo info) {
        return new AuthInfo("sara", "qwerty123");
    }

    public static AuthInfo getOtherPasswordInfo(AuthInfo info) {
        return new AuthInfo("vasya", "123qwerty");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getVerificationCodeFor(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardTransfer {
        private String id;
        private String cardNumber;
    }

    public static CardTransfer getInfoFirstCard() {
        return new CardTransfer("0", "5559 0000 0000 0001");
    }

    public static CardTransfer getInfoSecondCard() {
        return new CardTransfer("1", "5559 0000 0000 0002");
    }

    @Value
    public static class AmountTransfer {
        private String amount;
        private String cardNumber;
    }

    public static AmountTransfer getAmountInfoFirstCard(String amount) {
        return new AmountTransfer(amount, "5559 0000 0000 0001");
    }

    public static AmountTransfer getAmountInfoSecondCard(String amount) {
        return new AmountTransfer(amount, "5559 0000 0000 0002");
    }


}
