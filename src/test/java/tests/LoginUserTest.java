package tests;

import helpers.AllureListeners;
import helpers.PropertiesProvider;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.UserLoginPage;

@Listeners(AllureListeners.class)
public class LoginUserTest extends BaseTest {

    private static final String INCORRECT_LOGIN = "FTC_MaxSh";
    @DataProvider(name = "authorization")
    public Object[][] authorizationData() {
        return new Object[][]{{PropertiesProvider.getProperty("correctEmail"),
                PropertiesProvider.getProperty("correctPassword"), 1},
                {PropertiesProvider.getProperty("correctEmail"), "", 2},
                {"", PropertiesProvider.getProperty("correctPassword"), 3},
                {INCORRECT_LOGIN, PropertiesProvider.getProperty("correctPassword"), 4},
                {"", "", 5}};
    }

    @Epic("way2automation Site")
    @Feature("Страница авторизации")
    @Story("Проверка авторизации разных комбинаций")
    @Test(dataProvider = "authorization")
    @Severity(SeverityLevel.BLOCKER)
    public void loginMemberTest(String email, String password, int id) {
        UserLoginPage memberLoginPage = new UserLoginPage(driver);
        memberLoginPage.insertMemberEmail(email)
                .insertMemberPassword(password)
                .clickLoginFields();
        switch (id) {
            case (1):
                memberLoginPage.clickLoginButton();
                Assert.assertNotEquals(PropertiesProvider.getProperty("w2aSiteURL"),
                        driver.getCurrentUrl());
                break;
            case (2):
                memberLoginPage.assertPasswordErrorText("This field is required");
                break;
            case (3):
                memberLoginPage.assertEmailErrorText("This field is required");
                break;
            case (4):
                memberLoginPage.assertEmailErrorText("Please provide a valid email address");
                break;
            case (5):
                memberLoginPage.assertEmailErrorText("This field is required")
                        .assertPasswordErrorText("This field is required")
                        .clickLoginButton();
                Assert.assertTrue(memberLoginPage.alertIconAppeared());
                break;
            default:
                throw new IllegalArgumentException("Неверные вводные данные");
        }
    }
}

