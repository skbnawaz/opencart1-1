package testCases;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.HomePage;
import pageObjects.MyAccountPage;
import pageObjects.loginPage;
import testBase.BaseClass;

public class TC002_Login_Test extends BaseClass {
	
	@Test(groups= {"Sanity","Master"})
	public void test_login() throws IOException {
		logger.info("***** Starting TC002_Login_Test *****");
	try {
		HomePage hp = new HomePage(driver);
		hp.clickOnMyAccount();
		logger.info("Clicked on My Account link");
		
		hp.clickOnLogin();
		logger.info("Clicked on Login link");
		
		//Login Page
		loginPage lp = new loginPage(driver);
		FileReader file= new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		lp.enterEmail(p.getProperty("email"));
		logger.info("Provided email");
		lp.enterPassword(p.getProperty("password"));
		logger.info("Provided password");
		lp.clickOnLoginButton();
		logger.info("Clicked on Login button");
		
		//My Account Page
		MyAccountPage ap=new MyAccountPage(driver);
		/*if( ap.myAccountHeader.isDisplayed()) {
			logger.info("Login successful Account header is displayed");
			Assert.assertTrue(true);
		} else {
			logger.error("Login failed");
			Assert.fail();
		}*/
		//Assert.assertTrue(ap.getMyAccountPageHeader(),"Login failed");
		boolean targetpage=ap.getMyAccountPageHeader();
		Assert.assertEquals(targetpage, true,"Login failed");
		logger.info("Login test passed");
		
	}catch(Exception e) {
		logger.fatal("Login test failed due to exception: " + e.getMessage());
		Assert.fail();
	}
		
		logger.info("***** Finished TC002_Login_Test *****");
	}

}
