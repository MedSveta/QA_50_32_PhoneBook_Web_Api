package ui_tests;

import dto.User;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.ContactPage;
import pages.HomePage;
import pages.LoginPage;
import utils.RetryAnalyser;
import utils.TestNGListener;

import static utils.PropertiesReader.*;
@Listeners(TestNGListener.class)

public class LoginTests extends AppManager {
    @Owner("Sveta Med")
    @Description("login with positive data")
    @Test(retryAnalyzer = RetryAnalyser.class)
    public void loginPositiveTest() {
        // System.out.println("first test");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        // loginPage.typeLoginRegistrationForm("sveta548@smd.com","Password123!");
        loginPage.typeLoginRegistrationForm(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        Assert.assertTrue(new ContactPage(getDriver())
                .isTextInBtnAddPresent("ADD"));
    }

    @Test(groups = {"smoke", "user"})
    public void loginPositiveTestWithUser() {
        User user = new User(getProperty("base.properties", "login"),
                getProperty("base.properties", "password"));
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();
        Assert.assertTrue(new ContactPage(getDriver()).isTextInBtnSignOutPresent("Sign Out"));
    }

    @Test(groups = "negative")
    public void loginNegativeTest_WrongEmail() {
        User user = new User("familymail.ru", "Family123!");
        HomePage homePage = new HomePage(getDriver());
        homePage.clickBtnLogin();
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.typeLoginRegistrationFormWithUser(user);
        loginPage.clickBtnLoginForm();
        Assert.assertEquals(loginPage.closeAlertReturnText(),
                "Wrong email or password");
    }
}
