package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import pages.BasePage;

import java.time.Duration;

public class BaseTest { // Эта строка объявляет начало определения класса BaseTest. Класс является шаблоном или чертежом для создания объектов.
    private static final ThreadLocal<WebDriver> driverThreadLocal
            = new ThreadLocal<>(); //Эта строка объявляет статическое приватное поле driverThreadLocal, которое является объектом класса ThreadLocal. Он используется для хранения объектов типа WebDriver в потоке исполнения.
    // ThreadLocal - это класс в Java, который позволяет создавать локальные переменные, специфичные для каждого потока.
    // Каждый поток имеет свою собственную копию переменной, хранящейся в ThreadLocal, и доступ может быть получен только из соответствующего потока.
    // Это полезно, когда вам нужно создать объект, который будет уникальным для каждого потока, и при этом изолировать состояние между потоками.

    public static WebDriver getDriver(){
        return driverThreadLocal.get();
    } //Этот метод возвращает экземпляр WebDriver из объекта driverThreadLocal. Он используется для получения веб-драйвера, установленного для текущего потока


    // Эти строки аннотируют метод setUp как метод, который должен быть выполнен перед каждым тестовым методом (@BeforeMethod), и указывают, что он принимает параметр browser из файла конфигурации.
    // Аннотация @Optional("firefox") означает, что значение по умолчанию для browser - это "firefox".
    @BeforeMethod
    @Parameters("browser")
    public void setUp(@Optional("firefox") String browser){

        // Этот блок кода проверяет, является ли значение параметра browser равным "chrome".
        // Если да, то он настраивает ChromeDriver и добавляет опции для запуска браузера на английском языке.
        if(browser.equalsIgnoreCase("chrome")){
            WebDriverManager.chromedriver().setup();
            ChromeOptions options = new ChromeOptions();
            options.addArguments("--lang=en");
            // options.addArguments("--headless");
            driverThreadLocal.set(new ChromeDriver(options));
        }
        // Аналогично предыдущему блоку, но если browser равен "firefox",
        // то настраивается FirefoxDriver и добавляются опции для запуска браузера на английском языке.
        else if(browser.equalsIgnoreCase("firefox")){
            WebDriverManager.firefoxdriver().setup();
            FirefoxOptions options = new FirefoxOptions();
        //    options.addPreference("intl.accept_languages", "en");
            driverThreadLocal.set(new FirefoxDriver(options));
        }
        else{throw new IllegalArgumentException("Invalid browser "+browser); }

        // Этот блок кода получает веб-драйвер с помощью метода getDriver(), максимизирует окно браузера,
        // устанавливает время ожидания загрузки страницы и неявного ожидания, а затем устанавливает этот драйвер для BasePage.
        WebDriver driver = getDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofMillis(20000));
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(20000));
        BasePage.setDriver(driver);
    }

    @AfterMethod
    public void tearDown(){
        WebDriver driver = getDriver();
        if(driver != null){
            driver.quit();
            driverThreadLocal.remove();
        }
    }

}