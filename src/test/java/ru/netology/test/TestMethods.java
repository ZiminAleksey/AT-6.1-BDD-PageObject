package ru.netology.test;


import lombok.val;
import ru.netology.data.DataHelper;
import ru.netology.page.CardPage;
import ru.netology.page.LoginPage;
import ru.netology.page.TransferValueCard;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestMethods {
    public void updateBalanceCard() {
        var transferValueCard = new TransferValueCard();
        var cardPage = new CardPage();
        val firstBalanceCard = cardPage.getCardBalance("0");
        val secondBalanceCard = cardPage.getCardBalance("1");
        val alignmentBalance = firstBalanceCard - ((firstBalanceCard + secondBalanceCard) / 2);
        var firstCardInfo = DataHelper.getInfoFirstCard(alignmentBalance);
        var secondCardInfo = DataHelper.getInfoSecondCard(alignmentBalance);

        if (firstBalanceCard > secondBalanceCard) {
            cardPage.changeCard(1);
            transferValueCard.transferValue(firstCardInfo);
        } else {
            cardPage.changeCard(0);
            transferValueCard.transferValue(secondCardInfo);
        }
    }

    public void assertNegativeBalance() {

        var cardPage = new CardPage();
        int secondBalanceFirstCard = cardPage.getCardBalance("0");
        int secondBalanceSecondCard = cardPage.getCardBalance("1");
        assertTrue(secondBalanceFirstCard > 0 && secondBalanceSecondCard > 0);
    }

    public void assertLogin() {

        var loginPage = new LoginPage();
        loginPage.checkError();
    }

}
