package pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.pagefactory.AjaxElementLocatorFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage extends BasePage{
    @FindBy(xpath = "//input[@name='email']") // эта строка использует аннотацию @FindBy для поиска веб-элемента на веб-странице
            // Найденный элемент сохраняется в перемменной emailField типа WebElement
    WebElement emailField;

    @FindBy(xpath = "//button[@name='registration']")
    WebElement registrationButton;

    @FindBy(xpath = "//input[@name='password']")
    WebElement passwordField;

    @FindBy(xpath = "//button[@name='login']")
    WebElement loginButton;


    public LoginPage(WebDriver driver){
        setDriver(driver);
        PageFactory.initElements(new AjaxElementLocatorFactory(driver,20),this);
    }
    public LoginPage fillEmailField(String email){ // этот метод заполняет поле электронной почты на веб-странице
        // он принимает строку email, переданную в качестве аргумента, и использует метод sendKeys(),
        // чтобы ввести эту строку в поле emailField
        emailField.sendKeys(email);
        return this; // затем метод возвращает обьект LoginPage, что позволяет использовать этот метод в цепочке вызовов
    }

    public LoginPage clickByRegistrationButton(){ // Этот метод кликает по кнопке регистрации на веб-странице
        // он вызывает метод click() для registrationButton
        registrationButton.click();
        return this; // затем он также возвращает обьект LoginPage, чтобы этот метод также можно было использовать в цепочке
    }


    public LoginPage fillPasswordField(String password){
        passwordField.sendKeys(password);
        return this;
    }

    public BasePage clickByLoginButton(){
        loginButton.click();
    Alert alert = getAlertIfPresent();
    if(alert != null){
    alert.accept();
    return new LoginPage(driver);
}else { return new ContactsPage(driver);
    }
    }

    private Alert getAlertIfPresent(){
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(5000));
            return wait.until(ExpectedConditions.alertIsPresent());
        }catch(TimeoutException e) {
            System.out.println("Alert issue "+e);
            return null;
        }
    }

}
