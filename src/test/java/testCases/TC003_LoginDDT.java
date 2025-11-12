package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.MyAccountPage;
import pageObjects.loginPage;
import testBase.BaseClass;

/*
Data Scenarios:

Data is valid   --> login successful --> test pass --> logout
Data is valid   --> login failed     --> test fail

Data is invalid --> login successful --> test fail --> logout
Data is invalid --> login failed     --> test pass
*/

public class TC003_LoginDDT extends BaseClass {

    @Test(dataProvider = "LoginData", dataProviderClass = utilities.DataProviders.class, groups = ("Regression"))
    public void verify_login(String email, String pwd, String exp) throws Exception {

        try {
            logger.info("***** Starting LoginDDT *****");

            // Home Page actions
            HomePage hp = new HomePage(driver);
            hp.clickOnMyAccount();
            logger.info("Clicked on My Account link");

            hp.clickOnLogin();
            logger.info("Clicked on Login link");

            // Login Page actions
            loginPage lp = new loginPage(driver);
            lp.enterEmail(email);
            logger.info("Provided email");

            lp.enterPassword(pwd);
            logger.info("Provided password");

            lp.clickOnLoginButton();
            logger.info("Clicked on Login button");

            // My Account Page validation
            MyAccountPage ap = new MyAccountPage(driver);
            boolean targetPage = ap.getMyAccountPageHeader();

            if (exp.equalsIgnoreCase("Valid")) {
                if (targetPage) {
                    logger.info("Login DDT passed");
                    ap.clickOnLogout();
                    Assert.assertTrue(true);
                } else {
                    logger.error("Login DDT failed");
                    Assert.assertTrue(false);
                }
            } else if (exp.equalsIgnoreCase("Invalid")) {
                if (targetPage) {
                    logger.error("Login DDT failed");
                    ap.clickOnLogout();
                    Assert.assertTrue(false);
                } else {
                    logger.info("Login DDT passed");
                    Assert.assertTrue(true);
                }
            }

        } catch (Exception e) {
            Assert.fail();
        }

        logger.info("*********** Finished TC003_LoginDDT ***********");
    }
}
