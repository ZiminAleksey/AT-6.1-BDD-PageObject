package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MoneyTransferNegativeTests {

    @BeforeEach
    void form() {
        open("http://localhost:9999");
    }

    @Test
    void shouldNegativeTransfer() {
        var loginPage = new LoginPage();
        var cardPage = new CardPage();
        int value = 15000;
        var transferInfo = DataHelper.getAmountInfoSecondCard(String.valueOf(value));
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        verificationPage.validVerify(verificationCode);
        cardPage.checkBalanceCard();
        cardPage.changeCard(0).transferValue(transferInfo);
        cardPage.assertNegativeBalance();
    }

    @Test
    void replenishmentFromOneNumber() {
        var loginPage = new LoginPage();
        var cardPage = new CardPage();
        int value = 150;
        var transferInfo = DataHelper.getAmountInfoSecondCard(String.valueOf(value));
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        verificationPage.validVerify(verificationCode);
        cardPage.checkBalanceCard();
        int firstBalanceFirstCard = cardPage.getCardBalance("0");
        int firstBalanceSecondCard = cardPage.getCardBalance("1");

        cardPage.changeCard(1).transferValueExeption(transferInfo, firstBalanceFirstCard);
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard);
    }

    @Test
    void authOtherUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var authOtherInfo = DataHelper.getOtherUserInfo(authInfo);
        loginPage.invalidLogin(authOtherInfo);
    }

    @Test
    void authValidUserInvalidPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var authOtherInfo = DataHelper.getOtherPasswordInfo(authInfo);
        loginPage.invalidLogin(authOtherInfo);
    }
}
