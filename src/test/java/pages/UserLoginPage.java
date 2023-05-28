package pages;

import helpers.Waiters;
import helpers.WebElementExtended;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class UserLoginPage {

    private final WebDriver driver;

    @FindBy(css = "[type='email']")
    private WebElement memberEmailField;

    @FindBy(css = "[type='password']")
    private WebElement memberPasswordField;

    @FindBy(css = ".btn-primary")
    private WebElement loginButton;

    @FindBy(xpath = "//input[@type='email']/parent::*//div[@class='inline-error']")
    private WebElement emailError;

    @FindBy(css = ".password-container .inline-error")
    private WebElement passwordError;

    @FindBy(css = ".bodyMainBold")
    private WebElement incorrectDataIcon;

    public UserLoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @Step("Вставили логин {email} в поле ввода email")
    public UserLoginPage insertMemberEmail(String email) {
        memberEmailField.sendKeys(email);
        return this;
    }

    @Step("Вставили пароль {password} в поле ввода пароля")
    public UserLoginPage insertMemberPassword(String password) {
        memberPasswordField.sendKeys(password);
        return this;
    }

    @Step("Нажали кнопку логина")
    public UserLoginPage clickLoginButton() {
        loginButton.click();
        return this;
    }

    public String getErrorEmailText() {
        return emailError.getText();
    }

    public String getErrorPasswordText() {
        return passwordError.getText();
    }

    public boolean alertIconAppeared() {
        Waiters.waitForVisibility(driver, incorrectDataIcon)
                .isDisplayed();
        return false;
    }

    @Step("переместили фокус на поля ввода пароля и логина")
    public UserLoginPage clickLoginFields() {
        Waiters.waitForVisibility(driver,memberEmailField)
                .click();
        Waiters.waitForVisibility(driver,memberPasswordField)
                .click();
        return this;
    }

    public UserLoginPage assertEmailErrorText(String text) {
        Assert.assertEquals(getErrorEmailText(),text);
        return this;
    }

    public UserLoginPage assertPasswordErrorText(String text) {
        Assert.assertEquals(getErrorPasswordText(),text);
        return this;
    }
}

