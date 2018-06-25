package testclasses;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.AfterClass;

import pageclasses.SignUpPageFactory;
import utilities.Constants;
import utilities.ExcelUtility;

public class TestNG_SignUp {
	private WebDriver driver;
	private String baseUrl;
	SignUpPageFactory spf;

	@BeforeClass
	public void beforeClass() throws Exception {
		driver = new ChromeDriver();
		baseUrl = "https://signup.shipt.com/#/enterEmail";
		spf = new SignUpPageFactory(driver);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get(baseUrl);
		ExcelUtility.setExcelFile(Constants.File_Path + Constants.File_Name, "SignUp");
	}
	
	//Use data providers to load data from Excel into 2 dimensional arrays
	@DataProvider(name = "emailForm")
	public Object[][] dataProvider1() {
		Object[][] testData = ExcelUtility.getTestData("emailData");
		return testData;
	}
	
	@DataProvider(name = "registerForm")
	public Object[][] dataProvider2() {
		Object[][] testData = ExcelUtility.getTestData("registerData");
		return testData;
	}

	//Test email form with invalid entries, using multiple rows of input data from Excel
	@Test(dataProvider="emailForm")
	public void test1_InvalidEmailForm(String email, String zip, String field, String expError) throws Exception {
		spf.clearEmailForm();
		spf.enterEmailForm(email, zip);
		spf.clickGoButton();
		//Get error message then evaluate actual error vs. expected error
		String errorMsg = spf.getErrorMessage(field, expError);
		Assert.assertEquals(errorMsg, expError);		
	}
	
	//Test email form with valid entries and a zip code that is out of range
	@Test
	public void test2_ZipOutOfRange() throws Exception {
		spf.clearEmailForm();
		spf.enterEmailForm("charliebrown@fakecharlie.net", "94706");
		spf.clickGoButton();
		spf.clickOkButton();
	}
	
	//Test email form with valid entries and a zip code that is in range
	@Test
	public void test3_ZipInRange() throws Exception {
		spf.clearEmailForm();
		spf.enterEmailForm("charliebrown@fakecharlie.net", "35209");
		spf.clickGoButton();
		spf.clickAnnualBox();
		
	}
	
	//Test register form with invalid entries, using multiple rows of input data from Excel
	@Test(dataProvider="registerForm")
	public void test4_InvalidRegisterForm(String name, String phone, String password, String card,
			String expDate, String cvc, String postal, String field, String expError) throws Exception {
		spf.clearRegisterForm();
		spf.enterRegisterForm(name, phone, password, card, expDate, cvc, postal);
		spf.clickStartButton();
		//Get error message then evaluate actual error vs. expected error
		String errorMsg = spf.getErrorMessage(field, expError);
		Assert.assertEquals(errorMsg, expError);		
	}
	
	@AfterClass
	public void afterClass() {
		driver.quit();
	}
	
}