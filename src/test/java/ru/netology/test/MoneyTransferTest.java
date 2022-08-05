package ru.netology.test;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataHelper;
import ru.netology.page.CardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferValueCard;

public class MoneyTransferTest {

    @BeforeEach
    void form() {
        open("http://localhost:9999");
    }

    @Test
    void shouldTransferMoneyFirstCard() {
        var loginPage = new LoginPage();
        var cardPage = new CardPage();
        int value = 100;
        var idFirstCard = DataHelper.getInfoFirstCard().getId();
        var idSecondCard = DataHelper.getInfoSecondCard().getId();
        var transferInfo = DataHelper.getAmountInfoSecondCard(String.valueOf(value));
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        verificationPage.validVerify(verificationCode);
        cardPage.checkBalanceCard();
        int firstBalanceFirstCard = cardPage.getCardBalance("0");
        int firstBalanceSecondCard = cardPage.getCardBalance("1");

        cardPage.changeCard(0).transferValueExeption(transferInfo, firstBalanceSecondCard);
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard + value);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard - value);
    }

    @Test
    void shouldTransferMoneySecondCard() {
        var loginPage = new LoginPage();
        var cardPage = new CardPage();
        int value = 50;
        var transferInfo = DataHelper.getAmountInfoFirstCard(String.valueOf(value));
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

        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard - value);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard + value);
    }

    @Test
    void shouldTransferMoneyCardLowAmount() {
        var loginPage = new LoginPage();
        var cardPage = new CardPage();
        int value = 1;
        var transferInfo = DataHelper.getAmountInfoFirstCard(String.valueOf(value));
        var authInfo = DataHelper.getAuthInfo();
        var verificationPage = loginPage.validLogin(authInfo);
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);

        verificationPage.validVerify(verificationCode);
        int firstBalanceFirstCard = cardPage.getCardBalance("0");
        int firstBalanceSecondCard = cardPage.getCardBalance("1");

        cardPage.changeCard(1).transferValueExeption(transferInfo, firstBalanceFirstCard);
        cardPage.assertNegativeBalance();
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard - value);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard + value);
    }
}






