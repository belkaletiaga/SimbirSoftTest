package test;

import Utils.RandomUtils;
import io.qameta.allure.AllureId;
import org.junit.jupiter.api.*;
import pages.FormFieldsPage;

public class WebFormTest extends BaseTest {
    FormFieldsPage formPage;

    @Test
    @AllureId("TC-1")
    @Tag("Positive")
    @DisplayName("Проверка отправки формы с заполнением всех полей")
    void checkSubmitTitleTest()  {
        formPage = new FormFieldsPage(driver,wait);

        formPage.enterName(formPage.nameInput, "Name", "Test")
                .enterName(formPage.passwordInput, "Password", RandomUtils.randomPasswords(10))
                .selectDrink("Milk")
                .selectDrink("Coffee")
                .selectColor("Yellow")
                .selectAutomationOption()
                .enterName(formPage.emailInput, "Email", "name@example.com")
                .enterName(formPage.messageTextarea, "Message", formPage.generateMessage())
                .clickSubmit();
        String submitAlert = driver.switchTo().alert().getText();
        Assertions.assertEquals("Message received!", submitAlert);
    }

    @Test
    @AllureId("TC-2")
    @Tag("Positive")
    @DisplayName("Проверка отправки формы с заполнением только поля Name")
    void checkSubmitFormWithNameOnlyTest() {
        formPage = new FormFieldsPage(driver,wait);
        formPage.enterName(formPage.nameInput, "Name", "Test425")
                .clickSubmit();

        String submitAlert = driver.switchTo().alert().getText();
        Assertions.assertEquals("Message received!", submitAlert);
    }
    @Test
    @AllureId("TC-3")
    @Tag("Negative")
    @DisplayName("Проверка отправки формы с пустым полем Name")
    void SubmitFormWithEmptyRequiredFieldsTest() {
        formPage = new FormFieldsPage(driver,wait);
        formPage.clickSubmit();

        boolean isNameFieldValid = (boolean) ((org.openqa.selenium.JavascriptExecutor) driver)
                .executeScript("return arguments[0].checkValidity();",
                        driver.findElement(org.openqa.selenium.By.id("name-input")));

        Assertions.assertFalse(isNameFieldValid, "Поле Name не заполнено");
    }

}