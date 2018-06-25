package pageclasses;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SignUpPageFactory {
	WebDriver driver;
	private static final Logger log = LogManager.getLogger(SignUpPageFactory.class.getName());
	
	//FIND ELEMENTS
	@FindBy(id = "email")
	WebElement email;
	
	@FindBy(id = "zip")
	WebElement zip;
	
	@FindBy(xpath = "//button//span[contains(text(),'Go')]")
	WebElement goButton;
	
	@FindBy(xpath = "//h3[contains(text(),'Oh no!')]")
	WebElement outOfAreaModal;
	
	@FindBy(xpath = "//button[contains(text(),'Ok')]")
	WebElement oKButton;
	
	@FindBy(xpath = "//a[@class='annual-box']")
	WebElement annualBoxLink;
	
	@FindBy(xpath = "//p[contains(@class,'error')]")
	WebElement pTagError;
	
	@FindBy(id = "name")
	WebElement name;
	
	@FindBy(id = "phone")
	WebElement phone;
	
	@FindBy(id = "password")
	WebElement password;
	
	@FindBy(name = "cardnumber")
	WebElement card;
	
	@FindBy(name = "exp-date")
	WebElement exp;
	
	@FindBy(name = "cvc")
	WebElement cvc;
	
	@FindBy(name = "postal")
	WebElement postal;
	
	@FindBy(xpath = "//iframe[@name='__privateStripeFrame4']")
	WebElement iFrameCard;
	
	@FindBy(xpath = "//iframe[@name='__privateStripeFrame5']")
	WebElement iFrameExp;
	
	@FindBy(xpath = "//iframe[@name='__privateStripeFrame6']")
	WebElement iFrameCvc;
	
	@FindBy(xpath = "//iframe[@name='__privateStripeFrame7']")
	WebElement iFrameZip;
	
	@FindBy(xpath = "//button[contains(text(),'Start Membership')]")
	WebElement startButton;
	
	
	//CONSTRUCTOR
	public SignUpPageFactory(WebDriver driver) {
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	
	//MANIPULATE ELEMENTS
	public void enterEmail(String emailAddress) {
		email.sendKeys(emailAddress);
		log.info("Enter email as " + emailAddress);
	}
	
	public void enterZip(String zipCode) {
		zip.sendKeys(zipCode);
		log.info("Enter zip as " + zipCode);
	}
	
	public void clearEmailForm() {
		zip.clear();
		email.clear();
		log.info("Email form cleared");
	}

	public void clickGoButton() {
		goButton.click();
		log.info("Go button clicked");
	}
	
	public void clickOkButton() {
		oKButton.click();
		log.info("Ok button clicked");
	}
	
	public void clickAnnualBox() {
		annualBoxLink.click();
		log.info("Annual box link clicked");
	}
	
	public void clickStartButton() {
		startButton.click();
		log.info("Start button clicked");
	}
	
	public void clearRegisterForm() {
		name.clear();
		phone.clear();
		password.clear();
		log.info("Register form cleared");
	}
	
	public void enterName(String fullName) {
		name.sendKeys(fullName);
		log.info("Enter name as " + fullName);
	}
	
	public void enterPhone(String phoneNum) {
		phone.sendKeys(phoneNum);
		log.info("Enter phone as " + phoneNum);
	}
	
	public void enterPassword(String passwordVal) {
		password.sendKeys(passwordVal);
		log.info("Enter password as " + passwordVal);
	}
	
	public void enterCard(String cardNum) {
		driver.switchTo().frame(iFrameCard);
		card.clear();
		card.sendKeys(cardNum);
		log.info("Enter card as " + cardNum);
		driver.switchTo().parentFrame(); 
	}
	
	public void enterCvc(String cvcNum) {
		driver.switchTo().frame(iFrameCvc);
		cvc.clear();
		cvc.sendKeys(cvcNum);
		log.info("Enter card as " + cvcNum);
		driver.switchTo().parentFrame(); 
	}

	public void enterExp(String expDate) {
		driver.switchTo().frame(iFrameExp);
		exp.clear();
		exp.sendKeys(expDate);
		log.info("Enter exp as " + expDate);
		driver.switchTo().parentFrame(); 
	}
	
	public void enterPostal(String zip) {
		driver.switchTo().frame(iFrameZip);
		postal.clear();
		postal.sendKeys(zip);
		log.info("Enter zip as " + zip);
		driver.switchTo().parentFrame(); 
	}
	
	public void enterEmailForm(String email, String zip) {
		enterEmail(email);
		enterZip(zip);
	}
	
	public void enterRegisterForm(String name, String phone, String password, String card,
			String expDate, String cvc, String postal) {
		enterName(name);
		enterPhone(phone);
		enterPassword(password);
		enterCard(card);
		enterExp(expDate);
		enterCvc(cvc);
		enterPostal(postal);		
	}
	
	public String getErrorMessage(String field, String expError) {
		String errorMsg = "";
		//If actual error message is expected in the p tag, get text of error
		if (field.equals("p")) {
			errorMsg = pTagError.getText();
		//If actual error message is expected from input fields, get error message from validation message
		} else {
			WebElement elField = driver.findElement(By.id(field));
			errorMsg = elField.getAttribute("validationMessage");
		}
		return errorMsg;
	}

}