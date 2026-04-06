package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;
import java.util.Random;

public class FormFieldsPage extends BasePage {

    @FindBy(id = "name-input")
    public WebElement nameInput;

    @FindBy(xpath  = "//input[@type='password']")
    public WebElement passwordInput;


    @FindBy(xpath = "//input[@type='checkbox']")
    public List<WebElement> drinkCheckboxs;

    @FindBy(xpath = "//input[@type='radio']")
    public List<WebElement> radioButtonsColors;

    // Выпадающий список
    @FindBy(id = "automation")
    public WebElement automationDropdown;

    @FindBy(xpath = "//select/option[position() > 1]")
    public List<WebElement> automationSelectOption;

    @FindBy(xpath = "//*[@id=\"feedbackForm\"]/ul/li[position() > 0]")
    public List<WebElement> automationToolsList;

    @FindBy(id = "email")
    public WebElement emailInput;

    @FindBy(id = "message")
    public WebElement messageTextarea;

    // Кнопка Submit
    @FindBy(id = "submit-btn")
    public WebElement submitButton;

    public FormFieldsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Заполнить поле {nameInput} значением '{text}'")
    public FormFieldsPage enterName(WebElement input, String nameInput, String text) {
        scrollToElement(input);
        wait.until(ExpectedConditions.visibilityOf(input)).sendKeys(text);
        return this;
    }


    @Step("Выбирать напиток '{drink}' из списка 'What is your favorite drink?'")
    public FormFieldsPage selectDrink(String drink) {

        WebElement selectCheckbox = null;
        wait.until(driver ->!drinkCheckboxs.isEmpty() && drinkCheckboxs.get(0).isDisplayed());
        for (WebElement drinkCheckbox : drinkCheckboxs) {
            if (drink.equals(drinkCheckbox.getAttribute("value"))) {
                selectCheckbox = drinkCheckbox;
                break;
            }
        }
        if (selectCheckbox == null) {
            throw new RuntimeException("Напиток " + drink + " отсутствует в списке");
        }
        selectCheckbox.click();
        return this;
    }

    @Step("Выбирать цвет '{color}' из списка 'What is your favorite color?'")
    public FormFieldsPage selectColor(String color) {
        wait.until(driver ->!radioButtonsColors.isEmpty() && radioButtonsColors.get(0).isDisplayed());
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
        selectRadioButton.click();
        return this;
    }
    @Step("Выбрать в меню Do you like automation? любой вариант")
    public FormFieldsPage selectRandomAutomationOption(WebElement randomElement) {
        wait.until(ExpectedConditions.visibilityOf(automationDropdown));
        wait.until(ExpectedConditions.elementToBeClickable(automationDropdown));
        scrollToElement(automationDropdown);
        automationDropdown.click();
        wait.until(ExpectedConditions.visibilityOfAllElements(automationSelectOption));
        wait.until(ExpectedConditions.elementToBeClickable(randomElement)).click();
        return this;
    }

    public FormFieldsPage selectAutomation()  {
        Random random = new Random();
        WebElement randomElement = automationSelectOption.get(random.nextInt(automationSelectOption.size()));
        return selectRandomAutomationOption(randomElement);
    }

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


    public void clickSubmit() {
        scrollToElement(submitButton);
        submitButton.click();
    }
}

