package ui_tests;

import data_providers.ContactDataProvider;
import dto.Contact;
import manager.AppManager;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import utils.HeaderMenuItem;

import static pages.BasePage.clickButtonHeader;
import static utils.ContactFactory.*;

public class AddNewContactTests extends AppManager {
    SoftAssert softAssert = new SoftAssert();
    HomePage homePage;
    LoginPage loginPage;
    ContactPage contactPage;
    AddPage addPage;
    int countOfContacts;

    @BeforeMethod
    public void login() {
        homePage = new HomePage(getDriver());
        loginPage = clickButtonHeader(HeaderMenuItem.LOGIN);
        loginPage.typeLoginRegistrationForm
                ("sveta548@smd.com", "Password123!");
        loginPage.clickBtnLoginForm();
        contactPage = new ContactPage(getDriver());
        countOfContacts = contactPage.getCountOfContacts();
        addPage = clickButtonHeader(HeaderMenuItem.ADD);
    }

    @Test
    public void addNewContactPositiveTest() {
        addPage.typeContactForm(positiveContact());
        int countOfContactsAfterAdd = contactPage.getCountOfContacts();
        Assert.assertEquals(countOfContactsAfterAdd, countOfContacts + 1);
    }

    @Test(dataProvider = "dataProviderFromFile", dataProviderClass = ContactDataProvider.class)
    public void addNewContactPositiveTest_WithDataProvider(Contact contact) {
        addPage.typeContactForm(contact);
        int countOfContactsAfterAdd = contactPage.getCountOfContacts();
        Assert.assertEquals(countOfContactsAfterAdd, countOfContacts + 1);
    }

    @Test
    public void addNewContactPositiveTest_ClickLastContact() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        // contactPage.clickLastContact();
        Assert.assertTrue(contactPage.isContactPresent(contact));
    }

    @Test
    public void addNewContactPositiveTest_ScrollToLastContact() {
        Contact contact = positiveContact();
        addPage.typeContactForm(contact);
        contactPage.scrollToLastContact();
        contactPage.clickLastContact();
        String text = contactPage.getTextInContact();
        System.out.println(text);
        softAssert.assertTrue(text.contains(contact.getName()),
                "validate Name in DetailCard");
        softAssert.assertTrue(text.contains(contact.getEmail()),
                "validate Email in DetailCard");
        softAssert.assertTrue(text.contains(contact.getPhone()),
                "validate Phone in DetailCard");
        softAssert.assertAll();
    }
}
