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
        var firstCardInfo = DataHelper.getInfoFirstCard();
        var secondCardInfo = DataHelper.getInfoSecondCard();

        if (firstBalanceCard > secondBalanceCard) {
            cardPage.changeCard(1);
            transferValueCard.transferValue(firstCardInfo, alignmentBalance);
        } else {
            cardPage.changeCard(0);
            transferValueCard.transferValue(secondCardInfo, alignmentBalance);
        }
    }
}
