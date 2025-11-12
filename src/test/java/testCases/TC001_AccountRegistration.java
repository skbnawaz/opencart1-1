package testCases; 
import static org.testng.Assert.fail;

//import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import pageObjects.AccountRegistration;
import pageObjects.HomePage;
import testBase.BaseClass;
//import org.apache.logging.log4j.Logger;//log4j2

public class TC001_AccountRegistration extends BaseClass {
//	  public Logger logger;
    @Test(groups= {"Regression","Master"})
    public void verify_account_registration() throws InterruptedException {
         
    	logger.info("**Starting TC001_AccountRegistration**");
    	
        try {
            HomePage hp = new HomePage(driver);
            hp.clickOnMyAccount();
            logger.info("Clicked on My Account link");
            hp.clickOnRegister();
            logger.info("Clicked on Register link");
            
            AccountRegistration regpage = new AccountRegistration(driver);
            logger.info("Providing customer data");
            // Generate test data
            String firstName = generateRandomString(5);
            String lastName = generateRandomString(10);
            String email = generateRandomEmail(); // Using the new method
            String telephone = randomNumber(10);
            String pwd = AlphaNumber();
            
            System.out.println("Generated test data:");
            System.out.println("First Name: " + firstName);
            System.out.println("Last Name: " + lastName);
            System.out.println("Email: " + email);
            System.out.println("Telephone: " + telephone);
            System.out.println("Password: " + pwd);
            
            // Fill registration form
            regpage.enterFirstName(firstName);
            regpage.enterLastName(lastName);
            regpage.enterEmail(email);
            regpage.enterTelephone(telephone);
            regpage.enterPassword(pwd);
            regpage.enterConfirmPassword(pwd);
            regpage.clickOnPrivacyPolicyCheckbox();
            regpage.clickOnContinueButton();
            logger.info("Clicked on Continue button");
            // Wait for confirmation and verify
//            Thread.sleep(3000); // Wait for page to load
            String confrm = regpage.getConfirmationMsg();
            logger.info("Validating account registration");
            
            if(confrm.equals("Your Account Has Been Created!")) {
				logger.info("Account registration successful");
				Assert.assertTrue(true);
			} else {
				logger.debug("debug log");
				logger.error("Account registration failed");
				throw new Exception("Account registration failed"); // This will go to catch
			}
            
        } catch (Exception e) {
          logger.fatal("Account registration test failed due to exception: " + e.getMessage());
			Assert.fail("Test failed due to exception: " + e.getMessage());
        }
        logger.info("**Finished TC001_AccountRegistration**");
    }
}