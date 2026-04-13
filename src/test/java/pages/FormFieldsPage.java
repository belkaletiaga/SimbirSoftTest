package pages;

import Utils.RandomUtils;
import Utils.WaitUtils;
import io.qameta.allure.Allure;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;

public class FormFieldsPage extends BasePage {

    // Поле Name
    @FindBy(id = "name-input")
    public WebElement nameInput;

    // Поле Password
    @FindBy(xpath  = "//input[@type='password']")
    public WebElement passwordInput;

    // Чек-боксы для выбора напитка 'drink'
    @FindBy(xpath = "//input[@type='checkbox']")
    public List<WebElement> drinkCheckboxs;

    // Радиокнопки цвета 'color'
    @FindBy(xpath = "//input[@type='radio']")
    public List<WebElement> radioButtonsColors;

    // Дропдаун меню
    @FindBy(id = "automation")
    public WebElement automationDropdown;

    // Варианты дропдаун меню, position() = 0 - это дефолтное значение
    @FindBy(xpath = "//select/option[position() > 1]")
    public List<WebElement> automationSelectOption;

    // Cписок Automation tools
    @FindBy(xpath = "//*[@id=\"feedbackForm\"]/ul/li[position() > 0]")
    public List<WebElement> automationToolsList;

    // Поле Email
    @FindBy(id = "email")
    public WebElement emailInput;

    // Поле Message
    @FindBy(id = "message")
    public WebElement messageTextarea;

    // Кнопка Submit
    @FindBy(id = "submit-btn")
    public WebElement submitButton;

    public FormFieldsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    /**
     * Универсальный метод для ввода текста в поле
     */
    @Step("Заполнить поле {nameInput} значением '{text}'")
    public FormFieldsPage enterName(WebElement input, String nameInput, String text) {
        scrollToElement(input);
        WaitUtils.waitForVisible(wait, input);
        input.sendKeys(text);
        return this;
    }

    /**
     * Универсальный метод для выбора напитка в чек-боксе
     */
    @Step("Выбирать напиток '{drink}' из списка 'What is your favorite drink?'")
    public FormFieldsPage selectDrink(String drink) {

        WebElement selectCheckbox = null;
        WaitUtils.waitForListVisible(wait, drinkCheckboxs);
        for (WebElement drinkCheckbox : drinkCheckboxs) {
            if (drink.equals(drinkCheckbox.getAttribute("value"))) {
                selectCheckbox = drinkCheckbox;
                break;
            }
        }
        if (selectCheckbox == null) {
            throw new RuntimeException("Напиток " + drink + " отсутствует в списке");
        }
        WaitUtils.waitForVisibleAndClickable(wait,selectCheckbox);
        selectCheckbox.click();
        return this;
    }

    /**
     * Универсальный метод для выбора любого цвета
     */
    @Step("Выбирать цвет '{color}' из списка 'What is your favorite color?'")
    public FormFieldsPage selectColor(String color) {
        WaitUtils.waitForListVisible(wait, radioButtonsColors);
        WebElement selectRadioButton = null;
        for (WebElement c : radioButtonsColors) {
            if (color.equals(c.getAttribute("value"))) {
                selectRadioButton = c;
                break;
            }
        }
        if (selectRadioButton == null) {
            throw new RuntimeException("Цвет " + color + " отсутствует в списке");
        }
        scrollToElement(selectRadioButton);
        WaitUtils.waitForVisibleAndClickable(wait,selectRadioButton);
        selectRadioButton.click();
        return this;
    }

    /**
     * Универсальный метод для рандомного значения в dropdown меню
     */
    @Step("Выбрать в меню Do you like automation? любой вариант ")
    public FormFieldsPage selectAutomationOption() {
        scrollToElement(automationDropdown);
        WaitUtils.waitForVisibleAndClickable(wait,automationDropdown);
        automationDropdown.click();
        WaitUtils.waitForListVisible(wait,automationSelectOption);
        WebElement element = RandomUtils.selectRandomAutomation(automationSelectOption);
        String getElement = element.getText();
        Allure.step("Выбрана выбран: " + getElement);
        WaitUtils.waitForVisibleAndClickable(wait, element);
        element.click();
        return this;
    }


    /**
     * Метод для поиска слова из списка Automation tools, содержащий наибольшее количество символов
     */
    public String generateMessage() {
        if (automationToolsList.isEmpty()) {
            throw new RuntimeException("Список инструментов пуст");
        }
        int countTools = automationToolsList.size();
        String longestTool = "";
        for (WebElement tool : automationToolsList) {
            String toolName = tool.getText();
            if (toolName.length() > longestTool.length()) {
                longestTool = toolName;
            }
        }
        return longestTool;
    }

    /**
     * Метод для клика по кнопке [Submit]
     */
    public void clickSubmit() {
        scrollToElement(submitButton);
        WaitUtils.waitForVisibleAndClickable(wait, submitButton);
        submitButton.click();
    }
}

