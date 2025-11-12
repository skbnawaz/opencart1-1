package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class AccountRegistration extends BasePage {
	
	public AccountRegistration(WebDriver driver) {
		super(driver);
	}

	@FindBy(xpath="//input[@id='input-firstname']") WebElement txtFirstName;
	
	@FindBy(xpath="//input[@id='input-lastname']") WebElement txtLastName;
	
	@FindBy(xpath="//input[@id='input-email']") WebElement txtEmail;
	
	@FindBy(xpath="//input[@id='input-telephone']") WebElement txtTelephone;
	
	@FindBy(xpath="//input[@id='input-password']") WebElement txtPassword;
	
	@FindBy(xpath="//input[@id='input-confirm']") WebElement txtConfirmPassword;
	
	@FindBy(xpath="//input[@name='agree']") WebElement chkPolicy;
	
	@FindBy(xpath="//input[@value='Continue']") WebElement btnContinue;
	
	@FindBy(xpath="//h1[normalize-space()='Your Account Has Been Created!']") 
	public WebElement msgConfirmation;
	
	public void enterFirstName(String firstName) {
		txtFirstName.sendKeys(firstName);
	}
	
	public void enterLastName(String lastName) {
		txtLastName.sendKeys(lastName);
	}
	
	public void enterEmail(String email) {
		txtEmail.sendKeys(email);
	}
	
	public void enterTelephone(String telephone) {
		txtTelephone.sendKeys(telephone);
	}
	
	public void enterPassword(String password) {
		txtPassword.sendKeys(password);
	}
	
	public void enterConfirmPassword(String confirmPassword) {
		txtConfirmPassword.sendKeys(confirmPassword);
	}
	public void clickOnPrivacyPolicyCheckbox() {
		chkPolicy.click();
	}
	public void clickOnContinueButton() {
		//solution1
		//btnContinue.click();
		
		//solution2
		btnContinue.submit();
		
		//solution3
		//JavascriptExecutor js = (JavascriptExecutor)driver;
		//js.executeScript("arguments[0].click();", btnContinue);
		
		//solution4
		//Actions actions = new Actions(driver);
		//actions.moveToElement(btnContinue).click().perform();
		
		//solution5
		//WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		//wait.until(ExpectedConditions.elementToBeClickable(btnContinue)).click();
		
		//solution6
		//btnContinue.sendKeys(Keys.ENTER);
	}
	
	public String getConfirmationMsg() {
		try {
			return msgConfirmation.getText();
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	

}
