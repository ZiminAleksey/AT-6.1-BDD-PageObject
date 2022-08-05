package ru.netology.page;

import static com.codeborne.selenide.Condition.*;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class CardPage {
    private ElementsCollection buttonCards = $$("[data-test-id='action-deposit']");
    private ElementsCollection cards = $$(".list__item div");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";

    public CardPage() {
    }

    public TransferValueCard changeCard(int id) {
        buttonCards.get(id).click();
        return new TransferValueCard();
    }

    public int getCardBalance(String id) {
        val text = cards.get(Integer.parseInt(id)).text();
        // TODO: перебрать все карты и найти по атрибуту data-test-id
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public void assertNegativeBalance() {
        var firstCardInfo = DataHelper.getInfoFirstCard().getId();
        var secondCardInfo = DataHelper.getInfoSecondCard().getId();
        val firstBalance = getCardBalance(firstCardInfo);
        val secondBalance = getCardBalance(secondCardInfo);

        if (firstBalance < 0) {
            throw new RuntimeException("После перевода на карте " + (Integer.parseInt(firstCardInfo) + 1) + " баланс равен отрицательному: " + firstBalance);
        } else if (secondBalance < 0) {
            throw new RuntimeException("После перевода на карте " + (Integer.parseInt(secondCardInfo) + 1) + " баланс равен отрицательному: " + secondBalance);
        }
    }

    public void checkBalanceCard() {
        var transferValueCard = new TransferValueCard();
        var firstCardInfo = DataHelper.getInfoFirstCard().getId();
        var secondCardInfo = DataHelper.getInfoSecondCard().getId();
        val firstBalance = getCardBalance(firstCardInfo);
        val secondBalance = getCardBalance(secondCardInfo);

        int amount;
        if (firstBalance > secondBalance) {
            buttonCards.get(1).click();
            amount = firstBalance - ((firstBalance + secondBalance) / 2);
            transferValueCard.updateBalanceCard(String.valueOf(amount), DataHelper.getInfoFirstCard().getCardNumber());
        } else {
            buttonCards.get(0).click();
            amount = secondBalance - ((firstBalance + secondBalance) / 2);
            transferValueCard.updateBalanceCard(String.valueOf(amount), DataHelper.getInfoSecondCard().getCardNumber());
        }
    }
}
