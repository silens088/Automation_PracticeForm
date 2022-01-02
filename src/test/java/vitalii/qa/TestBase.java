package vitalii.qa;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;

import helpers.Attach;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.github.javafaker.Faker;
import pages.RegistrationPage;

import java.util.Locale;

//Файл с общими вещами
public class TestBase {

    RegistrationPage registrationPage = new RegistrationPage();

    //добавили фейкер
    Faker faker = new Faker(new Locale("ru")); //выставили локализацию фейкера
    String randomName = faker.name().firstName();
    String randomTelephone = faker.number().digits(10);

    @BeforeAll
    static void beforeAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide()); //добавили лисенер для аллюр
        Configuration.startMaximized = true;
        //Configuration.startMaximized = false;
        //Configuration.browserSize = "1366x768";

        //отключить для локального запуска тестов
        Configuration.remote = "https://user1:1234@selenoid.autotests.cloud/wd/hub/"; //для удаленного запуска тестов на селениде - ресурс школы

        //конфигурация селеноида
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("enableVNC", true);
        capabilities.setCapability("enableVideo", true);

        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    public void tearDown() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
    }
}
