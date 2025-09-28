package org.qa_project;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;


public class OrderPage extends BasePage {


    public OrderPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    // Кнопка ввода имени
    @FindBy(xpath = ".//input[@placeholder='* Имя']")
    public WebElement inputName;

    // Кнопка ввода Фамилии
    @FindBy(xpath = ".//input[@placeholder='* Фамилия']")
    public WebElement inputSurname;

    // Кнопка ввода адреса
    @FindBy(xpath = ".//input[@placeholder='* Адрес: куда привезти заказ']")
    public WebElement inputAddress;

    // Кнопка ввода станции метро
    @FindBy(xpath = ".//input[@class='select-search__input']")
    public WebElement inputStation;

    // Кнопка ввода номера телефона
    @FindBy(xpath = ".//input[@placeholder='* Телефон: на него позвонит курьер']")
    public WebElement inputPhoneNumber;

    // Кнопка подтверждения заказа - "Далее"
    @FindBy(xpath = ".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']")
    public WebElement buttonOrder;

    //Кнопка выбора даты заказа
    @FindBy(xpath = ".//input[@placeholder='* Когда привезти самокат']")
    public WebElement inputDate;

    //Кнопка выбора срока аренды
    @FindBy(xpath = ".//span[@class='Dropdown-arrow']")
    public WebElement inputRentTime;

    //Кнопка выбора 1 суток
    @FindBy(xpath = ".//div[@text()='сутки']")
    public WebElement inputOneDay;

    //Кнопка выбора 7 суток
    @FindBy(xpath = ".//div[@text()='семеро суток']")
    public WebElement inputSevenDay;

    //Кнопка выбора черного цвета самоката
    @FindBy(xpath = ".//input[@id='black']")
    public WebElement inputBlackColor;

    //Кнопка выбора серого цвета самоката
    @FindBy(xpath = ".//input[@id='grey']")
    public WebElement inputGrayColor;

    //Кнопка указания комментария к заказу
    @FindBy(xpath = ".//input[@placeholder='Комментарий для курьера']")
    public WebElement inputComment;

    //Кнопка сделать заказ
    @FindBy(xpath = ".//button[@text()='Заказать']")
    public WebElement buttonMakeOrder;

    //Кнопка подтверждения заказа
    @FindBy(xpath = ".//button[@text()='Да']")
    public WebElement buttonConfirmOrder;

    //Текст с успешным заказом
    @FindBy(className = "Order_ModalHeader__3FDaJ")
    public WebElement SuccessfulOrder;


    public void fillPersonalInfo(String name,String surname,String address) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(inputName)).sendKeys(name);
        wait.until(ExpectedConditions.visibilityOf(inputSurname)).sendKeys(surname);
        wait.until(ExpectedConditions.visibilityOf(inputAddress)).sendKeys(address);
    }

    public void fillContactInfo(String station,String phoneNumber) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(inputStation)).sendKeys(station);
        wait.until(ExpectedConditions.visibilityOf(inputPhoneNumber)).sendKeys(phoneNumber);
        clickButton(buttonOrder);
    }

    public void fillOrderDetails(String date,String days,String color,String comment) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(inputDate)).sendKeys(date);
        clickButton(inputRentTime);
        if (days == "1 сутки") {
            clickButton(inputOneDay);
        } else if (days == "7 суток") {
            clickButton(inputSevenDay);
        }
        if (color == "Черный") {
            clickButton(inputBlackColor);
        } else if (color == "Серый") {
            clickButton(inputGrayColor);
        }
        inputComment.sendKeys(comment);
        clickButton(buttonMakeOrder);
        clickButton(buttonConfirmOrder);
    }

    public boolean checkSuccessfulOrder() {
        if (SuccessfulOrder.isDisplayed()) {
            System.out.println("Заказ успешно оформлен");
        }
        return SuccessfulOrder.isDisplayed();}
}
