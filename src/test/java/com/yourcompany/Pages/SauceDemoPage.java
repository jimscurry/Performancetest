package com.yourcompany.Pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.JavascriptExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SauceDemoPage {

    @FindBy(xpath = "//input[@data-test='username']")
    private WebElement usernameElem;

    @FindBy(xpath = "//input[@data-test='password']")
    private WebElement passwordElem;

  //  @FindBy(xpath = "//input[@value='LOGIN']") //  xpath=(.//*[normalize-space(text()) and normalize-space(.)='Accepted usernames are:'])[1]/preceding::input[1] another way /input[@type=‘submit’]
  //  @FindBy(xpath = ".//*[normalize-space(text()) and normalize-space(.)='Accepted usernames are:'])[1]/preceding::input[1]")
    @FindBy(css = "[type='submit']")    
    private WebElement loginElem;

    public WebDriver driver;
    public JavascriptExecutor jsDriver;
    public static String url = "https://www.saucedemo.com";

    public static SauceDemoPage visitPage(WebDriver driver) {
        SauceDemoPage page = new SauceDemoPage(driver);
        page.visitPage("/");
        return page;
    }

    public SauceDemoPage(WebDriver driver) {
        this.driver = driver;
        this.jsDriver = (JavascriptExecutor)driver;
        PageFactory.initElements(driver, this);
    }

    public void visitPage(String pageurl) {
        this.driver.get(this.url + pageurl);
    }

    public void loginUser() {
        String username = System.getenv("PERF_USERNAME") != null ? System.getenv("PERF_USERNAME") : "standard_user";
        this.enterUsername(username);
        this.enterPassword("secret_sauce");
        loginElem.click();
    }

    public void enterPassword(String password) {
        passwordElem.click();
        passwordElem.sendKeys(password);
    }

    public void enterUsername(String username) {
        usernameElem.click();
        usernameElem.sendKeys(username);
    }

    public void sleep(long msecs) {
        try {
            Thread.sleep(msecs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public Map getPerformance() {
        Map<String, Object> logType = new HashMap();
        logType.put("type","sauce:performance");
        return (Map<String, Object>) this.jsDriver.executeScript("sauce:log", logType);
    }

    public List getNetwork() {
        Map<String, Object> logType = new HashMap();
        logType.put("type","sauce:network");
        return (List<Map<String, Object>>) this.jsDriver.executeScript("sauce:log", logType);
    }

    public Map getMetrics() {
        Map<String, Object> logType = new HashMap();
        logType.put("type","sauce:metrics");
        return (Map<String, Object>) this.jsDriver.executeScript("sauce:log", logType);
    }

    public Map getTiming() {
        Map<String, Object> logType = new HashMap();
        logType.put("type","sauce:timing");
        return (Map<String, Object>) this.jsDriver.executeScript("sauce:log", logType);
    }

    public Map assertPerformancePageLoad(String name) {
        Map<String, Object> logType = new HashMap();
        String myArray[] = { "load" };
        logType.put("name",name);
        logType.put("metrics", myArray);
        return (Map<String, Object>) this.jsDriver.executeScript("sauce:performance", logType);
    }

    public Map assertPerformancePageWeight(String name) {
        Map<String, Object> logType = new HashMap();
        String myArray[] = { "pageWeight" };
        logType.put("name",name);
        logType.put("metrics", myArray);
        return (Map<String, Object>) this.jsDriver.executeScript("sauce:performance", logType);
    }

    public boolean isKeyValueExists(List<Map<String, Object>> list, String key, String value) {
        for(Map<String, Object> pair:list){
            if(pair.get(key).toString().contains(value)) {
                return true;
            }
        }
        return false;
    }
}
