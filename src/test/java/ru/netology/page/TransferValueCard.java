package ru.netology.page;

import static com.codeborne.selenide.Condition.*;

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
    private SelenideElement errorMessage = $("[data-test-id=error-notification]");

    public CardPage transferValue(DataHelper.CardTransfer info) {
        clearPage();
        amountTransfer.setValue(String.valueOf(info.getAmount()));
        numberCard.setValue(info.getCardNumber());
        transferButton.click();
        return new CardPage();
    }

    public void checkBalance(DataHelper.CardTransfer info) {
        transferValue(info);
        errorMessage.shouldHave(exactText("На карте № " + info.getCardNumber() + " недостаточно средств")).shouldBe(visible);
    }

    private void clearPage() {
        amountTransfer.sendKeys(Keys.CONTROL + "A");
        amountTransfer.sendKeys(BACK_SPACE);
        numberCard.sendKeys(Keys.CONTROL + "A");
        numberCard.sendKeys(BACK_SPACE);
    }
}


