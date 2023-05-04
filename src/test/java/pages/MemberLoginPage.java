package pages;

import helpers.Waiters;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

public class MemberLoginPage {

    private final WebDriver driver;

    @FindBy(css = "[type='email']")
    private WebElement memberEmailField;

    @FindBy(css = "[type='password']")
    private WebElement memberPasswordField;

    @FindBy(css = ".btn-primary")
    private WebElement loginButton;

    @FindAll(@FindBy(css = ".inline-error"))
    private List<WebElement> listOfMessage;

    @FindBy(css = ".bodyMainBold")
    private WebElement incorrectDataIcon;

    public MemberLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Вставили логин {email} в поле ввода email")
    public MemberLoginPage insertMemberEmail(String email) {
        Waiters.waitForVisibility(10, driver, memberEmailField);
        memberEmailField.sendKeys(email);
        return this;
    }

    @Step("Вставили пароль {password} в поле ввода пароля")
    public MemberLoginPage insertMemberPassword(String password) {
        memberPasswordField.sendKeys(password);
        return this;
    }

    @Step("Нажали кнопку логина")
    public MemberLoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    public String getAlertMessageText(int index) {
        return Waiters.waitForVisibility(10, driver, listOfMessage
                        .get(index))
                .getText();
    }

    public boolean alertIconAppeared() {
        return Waiters.waitForVisibility(10, driver, incorrectDataIcon)
                .isDisplayed();
    }

    @Step("переместили фокус на поля ввода пароля и логина")
    public MemberLoginPage clickLoginFields() {
        memberEmailField.click();
        memberPasswordField.click();
        return this;
    }
}

