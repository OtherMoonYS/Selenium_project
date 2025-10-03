import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.qa_project.*;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;


public class QuestionsBlockTests {
    //Драйвер файрфокса
    //private FirefoxDriver driver;

    //Драйвер хрома
    private ChromeDriver driver;


    public static Object[][] data() {
        return new Object[][] {
                {"1", "Сутки — 400 рублей. Оплата курьеру — наличными или картой."},
                {"2", "Пока что у нас так: один заказ — один самокат. Если хотите покататься с друзьями, можете просто сделать несколько заказов — один за другим."},
                {"3", "Допустим, вы оформляете заказ на 8 мая. Мы привозим самокат 8 мая в течение дня. Отсчёт времени аренды начинается с момента, когда вы оплатите заказ курьеру. Если мы привезли самокат 8 мая в 20:30, суточная аренда закончится 9 мая в 20:30."},
                {"4", "Только начиная с завтрашнего дня. Но скоро станем расторопнее."},
                {"5", "Пока что нет! Но если что-то срочное — всегда можно позвонить в поддержку по красивому номеру 1010."},
                {"6", "Самокат приезжает к вам с полной зарядкой. Этого хватает на восемь суток — даже если будете кататься без передышек и во сне. Зарядка не понадобится."},
                {"7", "Да, пока самокат не привезли. Штрафа не будет, объяснительной записки тоже не попросим. Все же свои."},
                {"8", "Да, обязательно. Всем самокатов! И Москве, и Московской области."},
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


    //Тест на проверку открытия ответов на вопросы в разделе "Вопросы о важном"
    @ParameterizedTest
    @MethodSource("data")
    public void checkFooterElementsIsOpened(int numberOfQuestion, String expectedText) {
        BasePage page = new BasePage(driver);

        //Скроллим до нужного вопроса
        driver.executeScript("arguments[0].scrollIntoView();", page.getItemListTitle(numberOfQuestion));

        //Кликаем по нему
        page.getItemListTitle(numberOfQuestion).click();

        String actualResult = page.getAnswerQuestion(numberOfQuestion);
        assertEquals(expectedText, actualResult);
    }

    @AfterEach
    public void tearDown() {
        // Закрой браузер
        driver.quit();
    }
}