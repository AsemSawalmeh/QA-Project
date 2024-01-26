package com.example.currency_converter;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;
import java.util.List;

import static java.time.Duration.ofSeconds;
import static org.junit.jupiter.api.Assertions.*;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

public class Selenium_based_currency_test {

    public static ChromeDriver driver = new ChromeDriver();


    @BeforeEach
    public void setUp() throws Exception {
        WebDriverManager.chromedriver().setup();

        driver.get("http://localhost:8081/");
        Thread.sleep(1000);
    }

    //----------------------------------------------------------------------------

    //checking input amount

    //-------------------------------------------------------------------------------
    @Test
    @DisplayName("check null input")
    void null_amount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));

        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-1")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement errorText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorText")));
        String alertText = errorText.getText();

        assertEquals("Please type in a valid amount!", alertText);
    }

    @Test
    @DisplayName("check invalid characters")
    void test_invalid_characters() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("aam.55");


        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-1")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement errorText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorText")));
        String alertText = errorText.getText();

        assertEquals("Please type in a valid amount!", alertText);
    }

    @Test
    @DisplayName("check negative input")
    void test_negative_input() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("-0.5");


        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-1")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement errorText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorText")));
        String alertText = errorText.getText();

        assertEquals("Please type in a valid amount!", alertText);
    }

    @Test
    @DisplayName("check float number using comma")
    void test_check_float_number_comma() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("4,3");

        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-1")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement resultField = driver.findElement(By.id("resultField"));
        String result = resultField.getText();

        assertNotEquals("",result);
    }


    @Test
    @DisplayName("check Integer value input")
    void test_check_Intege_value_input() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("5");

        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-1")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement resultField = driver.findElement(By.id("resultField"));
        String result = resultField.getText();

        assertNotEquals("",result);
    }

    @Test
    @DisplayName("check float number with the dot ")
    void test_check_float_number() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("3.81");

        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-1")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement resultField = driver.findElement(By.id("resultField"));
        String result = resultField.getText();

        assertNotEquals("",result);
    }

    //----------------------------------------------------------------------------

    //checking the currencies selected

//-------------------------------------------------------------------------------


    @Test
    @DisplayName("check same inputs")
    void test_check_same_inputs() {

        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("55.2");


        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-5")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement errorText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorText")));
        String alertText = errorText.getText();

        assertEquals("Please Select Two Valid Currencies!", alertText);
    }

    @Test
    @DisplayName("check illegal currency selected ")
    void test_llegal_currency() {

        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("12");


        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-5"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-4")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement errorText = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("errorText")));
        String alertText = errorText.getText();

        assertEquals("Please Select Two Valid Currencies!", alertText);;

    }

    @Test
    @DisplayName("check normal currencies selected")
    void addTest() {
        WebDriverWait wait = new WebDriverWait(driver, ofSeconds(2));

        WebElement amountField = driver.findElement(By.id("input-vaadin-text-field-15"));
        amountField.sendKeys("3.81");

        WebElement comboBox1 = driver.findElement(By.id("comboBox1"));
        comboBox1.click();
        WebElement option1 = driver.findElement(By.id("vaadin-combo-box-item-12"));
        option1.click();

        WebElement comboBox2 = driver.findElement(By.id("comboBox2"));
        comboBox2.click();
        WebElement option2 = wait.until(ExpectedConditions.elementToBeClickable(By.id("vaadin-combo-box-item-2")));
        option2.click();

        WebElement convertButton = driver.findElement(By.id("convertButton"));
        convertButton.click();

        WebElement resultField = driver.findElement(By.id("resultField"));
        String result = resultField.getText();

        assertNotEquals("",result);
    }

/*@AfterAll
  public void closeBrowser() {
    driver.close();
    System.out.println("The driver has been closed.");
  }*/


}