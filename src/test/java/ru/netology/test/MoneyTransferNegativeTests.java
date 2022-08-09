package ru.netology.test;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardPage;
import ru.netology.page.LoginPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.*;

public class MoneyTransferNegativeTests {

    @BeforeEach
    void form() {
        open("http://localhost:9999");
    }

    @Test
    void shouldNegativeTransfer() {
        var loginPage = new LoginPage();
        int value = 15000;
        var donorInfo = DataHelper.getInfoSecondCard();
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        loginPage.validLogin(authInfo).validVerify(verificationCode);
        var cardPage = new CardPage();
        cardPage.changeCard(0).checkBalance(donorInfo, value);
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertTrue(secondBalanceFirstCard > 0 && secondBalanceSecondCard > 0);
    }

    @Test
    void replenishmentFromOneNumber() {
        var loginPage = new LoginPage();
        int value = 150;
        var donorInfo = DataHelper.getInfoSecondCard();
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        loginPage.validLogin(authInfo).validVerify(verificationCode);
        var cardPage = new CardPage();
        int firstBalanceFirstCard = cardPage.getCardBalance("0");
        int firstBalanceSecondCard = cardPage.getCardBalance("1");

        cardPage.changeCard(1).transferValue(donorInfo, value);
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertTrue(secondBalanceFirstCard > 0 && secondBalanceSecondCard > 0);
        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard);
    }

    @Test
    void authOtherUser() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var authOtherInfo = DataHelper.getOtherUserInfo(authInfo);

        loginPage.validLogin(authOtherInfo);
        loginPage.checkError();
    }

    @Test
    void authValidUserInvalidPassword() {
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var authOtherInfo = DataHelper.getOtherPasswordInfo(authInfo);

        loginPage.validLogin(authOtherInfo);
        loginPage.checkError();
    }
}
