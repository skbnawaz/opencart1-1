package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class MyAccountPage extends BasePage {

	public MyAccountPage(WebDriver driver) {
		super(driver);
		// TODO Auto-generated constructor stub
	}
	
	@FindBy(xpath="//h2[normalize-space()='My Account']") public WebElement myAccountHeader;
	
	@FindBy(xpath="//div[@class=\"list-group\"]//a[text()='Logout']") public WebElement out ;
	
	
	public boolean	 getMyAccountPageHeader() {
		
		try {
			return(myAccountHeader.isDisplayed());
		} catch (Exception e) {
			return(false);
		}
		
	}
	
	public void clickOnLogout() {
		out.click();
	}

}


