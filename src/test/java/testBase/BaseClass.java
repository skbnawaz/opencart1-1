package testBase;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;//log4j2
import org.apache.logging.log4j.Logger;//log4j2
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
//import org.apache.logging.log4j.core.tools.picocli.CommandLine.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

//import com.beust.jcommander.Parameters;



public class BaseClass {
    public static WebDriver driver;
    public WebDriverWait wait;
    public Logger logger;
    public Properties p;
    
    @BeforeClass(groups= {"Sanity","Regression","Master"})
    @Parameters({"os" ,"br"})
    public void setup(String os, String browser) throws IOException {
    	//setup properties file
    	
//    	FileReader file=new FileReader("./src//test//resources//config.properties");
    	FileReader file = new FileReader(System.getProperty("user.dir") + "/src/test/resources/config.properties");

    	p=new Properties();
    	p.load(file);
    	
    	
    	System.out.println("Operating System: " + os + ", Browser: " + browser);
    	logger=LogManager.getLogger(this.getClass());//log4j2
		
//		 System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/drivers/chromedriver"); 
    	
    	
    	if(p.getProperty("execution_env").equalsIgnoreCase("remote")) {
			System.out.println("Executing on remote environment");
			DesiredCapabilities cap=new DesiredCapabilities();
		//	cap.setPlatform(Platform.WIN11);
		//	cap.setBrowserName("chrome");
			//os
			if(os.equalsIgnoreCase("windows")) {
				cap.setPlatform(Platform.WIN11);
			}else if(os.equalsIgnoreCase("mac")) {
				cap.setPlatform(Platform.MAC);
			}else if(os.equalsIgnoreCase("linux")) {
				cap.setPlatform(Platform.LINUX);
			}else {
				System.out.println("Please provide correct os name");
				
			}
			//browser
			switch(browser.toLowerCase()) {
			case "chrome":
			cap.setBrowserName("chrome");
			break;
			case "firefox":
			cap.setBrowserName("firefox");
			break;
			case "edge":
			cap.setBrowserName("edge");
			break;
			case "safari":
				cap.setBrowserName("safari");
				break;
			default:System.out.println("Please provide correct browser name");
						
			}
			//driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),cap);
			URI uri = URI.create("http://localhost:4444/wd/hub");
			driver = new RemoteWebDriver(uri.toURL(), cap);

			
		} else {
			System.out.println("Executing on local environment");
			switch(browser.toLowerCase()) {
			case "edge":
				System.setProperty("webdriver.edge.driver", "C:\\Users\\Nawaz\\eclipse-workspace\\seleniumTestEnvironment\\drivers\\msedgedriver.exe");
			    driver = new EdgeDriver();
			    break;
			case "chrome":
				driver=new ChromeDriver();
				break;
			case "firefox":
				driver=new FirefoxDriver();
				break;
				default:System.out.println("Please provide a valid browser name");return;
	    	}
		}
    	
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.get(p.getProperty("appUrl"));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }
     
    
     
    @AfterClass(groups= {"Sanity","Regression","Master"})
    public void tearDown() {
        if(driver != null) {
            driver.quit();
        }
    }

    public String generateRandomString(int num) {
        return RandomStringUtils.randomAlphabetic(num);
    }
     
    public String randomNumber(int num) {
        return RandomStringUtils.randomNumeric(num);
    }
    
    public String AlphaNumber() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        String generateNumber = RandomStringUtils.randomNumeric(5);
        return generatedString + "@SKBn" + generateNumber;
    }
    
    // Additional method for random email
    public String generateRandomEmail() {
        return RandomStringUtils.randomAlphabetic(8) + "@gmail.com";
    }
    
    public String captureScreen(String tname) throws IOException {
        
        String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
        
        TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
        File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
        
        String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + tname + "_" + timeStamp + ".png";
        File targetFile = new File(targetFilePath);
        
        sourceFile.renameTo(targetFile);
        
        return targetFilePath;
    }

}