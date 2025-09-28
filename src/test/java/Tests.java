import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.qa_project.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


public class Tests {
    //Драйвер файрфокса
    //private FirefoxDriver driver;

    //Драйвер хрома
    private ChromeDriver driver;

    public static Object[][] data() {
        return new Object[][] {
                {"Юрий", "Санеев", "Москва", "89999561122", "28.09.2026", "1 сутки", "Черный", "Спасибо за работу"},
                {"Валерий", "Костяков", "Москва", "84321234365", "01.11.2029", "7 суток", "Серый", "Всегда пожалуйста"},
        };
    }



    @BeforeEach
    public void setUp() {
        //Драйвер хрома
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //Драйвер файрфокса
        //WebDriverManager.firefoxdriver().setup();
        //driver = new FirefoxDriver();



        BasePage page = new BasePage(driver);
        page.openPage();
    }

    //Тест на проверку кнопки заказа в верхней части страницы
    @Test
    public void checkUpperOrderButtonIsWork() {
        BasePage page = new BasePage(driver);
        page.clickButton(page.orderButtonUpperPage);
    }

    //Тест на проверку кнопки заказа в нижней части страницы
    @Test
    public void checkBottomOrderButtonIsWork() {
        BasePage page = new BasePage(driver);
        page.clickButton(page.orderButtonBottomPage);
    }

    //Тест создающий заказ с разными параметрами
    @ParameterizedTest
    @MethodSource("data")
    public void checkSuccessfulOrder(String name,
                                     String surname,
                                     String address,
                                     String phoneNumber,
                                     String date,
                                     String days,
                                     String color,
                                     String comment) {

        BasePage basePage = new BasePage(driver);
        OrderPage orderPage = new OrderPage(driver);

        // Кликаем по кнопке "Заказать"
        basePage.clickButton(basePage.orderButtonUpperPage);

        // Заполняем личную информацию
        orderPage.fillPersonalInfo(name, surname, address);

        // Кликаем "Далее"
        orderPage.clickButton(orderPage.buttonOrder);

        // Заполняем номер телефона
        orderPage.fillContactInfo(phoneNumber);

        // Заполняем детали заказа
        orderPage.fillOrderDetails(date, days, color, comment);

        // Ждём появления элемента успешного заказа и проверяем его наличие
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        try {
            wait.until(ExpectedConditions.visibilityOf(orderPage.SuccessfulOrder));
            boolean actualResult = orderPage.checkSuccessfulOrder();
            assertTrue(actualResult);
        } catch (TimeoutException e) {
            fail("Элемент успешного заказа не отображается");
        }
    }

    //Тест на проверку открытия ответов на вопросы в разделе "Вопросы о важном"
    @Test
    public void checkFooterElementsIsOpened() {
        BasePage page = new BasePage(driver);
        for (int i = 1; i < 9; i++) {
            driver.executeScript("arguments[0].scrollIntoView();", page.getItemListTitle(i));
            page.getItemListTitle(i).click();
        }
    }

    @AfterEach
    public void teardown() {
        // Закрой браузер
        driver.quit();
    }
}