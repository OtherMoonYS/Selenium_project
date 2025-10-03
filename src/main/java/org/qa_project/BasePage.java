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

public class BasePage {

    WebDriver driver;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    // Локатор главной страницы
    public final String pageUrl = "https://qa-scooter.praktikum-services.ru";

    // Локатор кнопки "Заказать" сверху страницы
    @FindBy (xpath = "(//div[@class='Header_Nav__AGCXC']//button[.='Заказать'])")
    public WebElement orderButtonUpperPage;

    // Локатор кнопки "Заказать" снизу страницы
    @FindBy (xpath = "(//div[@class='Home_FinishButton__1_cWm']//button[.='Заказать'])")
    public WebElement orderButtonBottomPage;

    //Локатор для выбора элемента в списке вопросов футера
    public String itemListTitle = "(//div[@class='accordion__button'])[%d]";


    // Открытие страницы по ссылке
    public void openPage() {
        driver.get(pageUrl);
    }


    //Открываем страницу оформления заказа
    public void clickButton(WebElement button) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", button);
        try {
            wait.until(ExpectedConditions.elementToBeClickable(button)).click();
        } catch (Exception e) {
            System.out.println("Не удалось кликнуть по кнопке: " + e.getMessage());
        }
    }

    public WebElement getItemListTitle(int index) {
        return driver.findElement(By.xpath(String.format(itemListTitle, index)));
    }

    public String getAnswerQuestion(int index) {
        return driver.findElement(By.xpath(String.format("(//div[@class='accordion__button'])[%d]//following::p[1]", index))).getText();
    }
}
