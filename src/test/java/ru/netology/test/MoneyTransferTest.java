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
        var loginPage = new LoginPage();
        var authInfo = DataHelper.getAuthInfo();
        var verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        loginPage.validLogin(authInfo).validVerify(verificationCode);
    }


    @Test
    void shouldTransferMoneyFirstCard() {
        var testsMethods = new TestMethods();
        int value = 100;
        var donorInfo = DataHelper.getInfoSecondCard();

        testsMethods.updateBalanceCard();
        var cardPage = new CardPage();
        int firstBalanceFirstCard = cardPage.getCardBalance("0");
        int firstBalanceSecondCard = cardPage.getCardBalance("1");

        cardPage.changeCard(0).transferValue(donorInfo, value);
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertTrue(secondBalanceFirstCard > 0 && secondBalanceSecondCard > 0);
        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard + value);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard - value);
    }

    @Test
    void shouldTransferMoneySecondCard() {
        var testsMethods = new TestMethods();
        int value = 50;
        var donorInfo = DataHelper.getInfoFirstCard();

        testsMethods.updateBalanceCard();
        var cardPage = new CardPage();
        int firstBalanceFirstCard = cardPage.getCardBalance("0");
        int firstBalanceSecondCard = cardPage.getCardBalance("1");

        cardPage.changeCard(1).transferValue(donorInfo, value);
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertTrue(secondBalanceFirstCard > 0 && secondBalanceSecondCard > 0);
        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard - value);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard + value);
    }

    @Test
    void shouldTransferMoneyCardLowAmount() {

        int value = 1;
        var donorInfo = DataHelper.getInfoFirstCard();

        var cardPage = new CardPage();
        int firstBalanceFirstCard = cardPage.getCardBalance("0");
        int firstBalanceSecondCard = cardPage.getCardBalance("1");

        cardPage.changeCard(1).transferValue(donorInfo, value);
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");

        assertTrue(secondBalanceFirstCard > 0 && secondBalanceSecondCard > 0);
        assertEquals(secondBalanceFirstCard, firstBalanceFirstCard - value);
        assertEquals(secondBalanceSecondCard, firstBalanceSecondCard + value);
    }
}






