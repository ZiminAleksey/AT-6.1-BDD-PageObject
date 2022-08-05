package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import ru.netology.data.DataHelper;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static org.openqa.selenium.Keys.BACK_SPACE;

public class TransferValueCard {
    private SelenideElement amountTransfer = $("[data-test-id='amount'] .input__control");
    private SelenideElement numberCard = $("[data-test-id='from'] .input__control");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");


    public CardPage transferValueExeption(DataHelper.AmountTransfer info, int borderBalance) {

        clearPage();
        val amount = Integer.parseInt(info.getAmount());
        if (borderBalance >= amount) {
            amountTransfer.setValue(info.getAmount());
        } else {
            throw new RuntimeException("Баланс карты, с которой осуществляется перевод недостаточен");
        }
        numberCard.setValue(info.getCardNumber());
        transferButton.click();
        return new CardPage();
    }

    public CardPage transferValue(DataHelper.AmountTransfer info) {
        clearPage();
        assertForm();
        amountTransfer.setValue(info.getAmount());
        numberCard.setValue(info.getCardNumber());
        transferButton.click();
        return new CardPage();
    }


    public void assertForm() {
        amountTransfer.shouldHave(Condition.exactValue(""));
        numberCard.shouldHave(Condition.exactValue(""));
    }

    public void updateBalanceCard(String amount, String cardNumber) {
        amountTransfer.setValue(amount);
        numberCard.setValue(cardNumber);
        transferButton.click();
    }

    private void clearPage() {
        amountTransfer.sendKeys(Keys.CONTROL + "A");
        amountTransfer.sendKeys(BACK_SPACE);
        numberCard.sendKeys(Keys.CONTROL + "A");
        numberCard.sendKeys(BACK_SPACE);
    }

}


