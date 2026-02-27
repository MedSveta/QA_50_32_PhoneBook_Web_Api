package ui_tests;

import dto.Contact;
import manager.AppManager;
import org.checkerframework.checker.units.qual.A;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import pages.*;
import utils.ContactFactory;

import static utils.HeaderMenuItem.LOGIN;
import static utils.PropertiesReader.getProperty;

public class EditContactTests extends AppManager {
    HomePage homePage;
    LoginPage loginPage;
    ContactPage contactPage;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = BasePage.clickButtonHeader(LOGIN);
        loginPage.typeLoginRegistrationForm
                (getProperty("base.properties", "login"),
                        getProperty("base.properties", "password"));
        loginPage.clickBtnLoginForm();
        contactPage = new ContactPage(getDriver());
    }

    @Test
    public void editFirstContactPositiveTest() {
        Contact contact = ContactFactory
                .positiveContact();
        contactPage.typeEditForm(contact);
        contactPage.pause(3);
        Assert.assertTrue(contactPage
                .isContactPresent(contact));
    }



}
