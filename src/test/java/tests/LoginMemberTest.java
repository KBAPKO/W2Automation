package tests;

import helpers.PropertiesProvider;
import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.MemberLoginPage;


public class LoginMemberTest extends BaseTest {

    private static final String INCORRECT_LOGIN = "FTC_MaxSh";
    @DataProvider(name = "authorization")
    public Object[][] authorizationData() {
        return new Object[][]{{PropertiesProvider.getProperty("correctEmail"),
                PropertiesProvider.getProperty("correctPassword")},
                {PropertiesProvider.getProperty("correctEmail"), ""},
                {"", PropertiesProvider.getProperty("correctPassword")},
                {INCORRECT_LOGIN,
                        PropertiesProvider.getProperty("correctPassword")},
                {"", ""}};
    }

    @Epic("way2automation Site")
    @Feature("Страница авторизации")
    @Story("Проверка авторизации разных комбинаций")
    @Test(dataProvider = "authorization")
    @Severity(SeverityLevel.BLOCKER)
    public void loginMemberTest(String email, String password) {
        MemberLoginPage memberLoginPage = new MemberLoginPage(driver);
        memberLoginPage.insertMemberEmail(email)
                .insertMemberPassword(password)
                .clickLoginFields();
        if (email.isEmpty() && password.isEmpty()) {
            Assert.assertEquals(memberLoginPage
                    .getAlertMessageText(0), "This field is required");
            Assert.assertEquals(memberLoginPage
                    .getAlertMessageText(1), "This field is required");
            memberLoginPage.clickLoginButton();
            Assert.assertTrue(memberLoginPage.alertIconAppeared());
        }

        if (email.equals(INCORRECT_LOGIN) && !password.isEmpty()) {
            Assert.assertEquals(memberLoginPage
                            .getAlertMessageText(0),
                    "Please provide a valid email address");
        }
        if ((email.isEmpty() && !password.isEmpty())
                || (!email.isEmpty() && password.isEmpty())) {
            Assert.assertEquals(memberLoginPage
                            .getAlertMessageText(0),
                    "This field is required");
        }
        if (email.equals(PropertiesProvider.getProperty("correctEmail"))
                && password.equals(PropertiesProvider
                .getProperty("correctPassword"))) {
            memberLoginPage.clickLoginButton();
            Assert.assertNotEquals(PropertiesProvider.getProperty("w2aSiteURL"),
                    driver.getCurrentUrl());
        }
    }


}

