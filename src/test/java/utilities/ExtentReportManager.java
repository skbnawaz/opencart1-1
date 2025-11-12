


package utilities;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
//import java.net.URL;
import java.text.SimpleDateFormat;
//import java.util.Arrays;
//import java.util.Comparator;
import java.util.Date;

//import org.apache.commons.mail.DefaultAuthenticator;
//import org.apache.commons.mail.ImageHtmlEmail;
//import org.apache.commons.mail.resolver.DataSourceUrlResolver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;



public class ExtentReportManager implements ITestListener {
    public ExtentSparkReporter sparkReporter;
    public ExtentReports extent;
    public ExtentTest test;

    String repName;

    public void onStart(ITestContext testContext) {
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
        repName = "Test-Report-" + timeStamp + ".html";
        
        sparkReporter = new ExtentSparkReporter("./reports/" + repName);
        
        sparkReporter.config().setDocumentTitle("opencart Automation Report");
        sparkReporter.config().setReportName("opencart Functional Testing");
        sparkReporter.config().setTheme(Theme.DARK);
        
        extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // Set system info
        extent.setSystemInfo("Application", "OpenCart");
        extent.setSystemInfo("Operating System", System.getProperty("os.name"));
        extent.setSystemInfo("User Name", System.getProperty("user.name"));
        extent.setSystemInfo("Environment", "QA");
    }

    public void onTestSuccess(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups()); // display groups in report
        test.log(Status.PASS, result.getName() + " got successfully executed");
    }

 
    public void onTestFailure(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.FAIL, result.getName() + " got failed");
        test.log(Status.INFO, result.getThrowable().getMessage());

        try {
            String imgPath = new BaseClass().captureScreen(result.getName());
            test.addScreenCaptureFromPath(imgPath);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }


    public void onTestSkipped(ITestResult result) {
        ExtentTest test = extent.createTest(result.getTestClass().getName());
        test.assignCategory(result.getMethod().getGroups());
        test.log(Status.SKIP, result.getName() + " got skipped");
        test.log(Status.INFO, result.getThrowable().getMessage());
    }


	public void onFinish(ITestContext testContext) {
        extent.flush();
        
        String pathOfExtentReport = System.getProperty("user.dir") + "/reports/" + repName;
        File extentReport = new File(pathOfExtentReport);
        
        // Open report in browser
        try {
            Desktop.getDesktop().browse(extentReport.toURI());
        } catch (IOException e) {
            e.printStackTrace();
        }
/*
        try {
          //  URL url = new URL("file:///" + System.getProperty("user.dir") + "/reports/" + repName);
            URL url = new File(System.getProperty("user.dir"), "reports/" + repName).toURI().toURL();


            // Create the email message
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.gmail.com");
            email.setSmtpPort(465);
            email.setAuthenticator(new DefaultAuthenticator("pavanoltraining@gmail.com", "password"));
            email.setSSLOnConnect(true);
            email.setFrom("pavanoltraining@gmail.com"); // Sender
            email.setSubject("Test Results");
            email.setMsg("Please find Attached Report...");
            email.addTo("pavankumar.busyqa@gmail.com"); // Receiver
            email.attach(url, "extent report", "Please check report...");
            email.send(); // send the email
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
     /*   try {
            // Construct the report file URL safely
            String repName = "ExtentReport.html"; // 🔹 replace with your actual report filename
            URL url = new File(System.getProperty("user.dir"), "reports/" + repName).toURI().toURL();

            // Create the email message
        
            // Attach the extent report
            email.attach(url, "Extent Report", "Automation test execution results.");

            // Send the email
            email.send();

            System.out.println("✅ Email sent successfully with report attachment!");

        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
     /*   try {
        	//URL url = new URL("file:///" + System.getProperty("user.dir") + "/reports/" + repName);
            URL url = new File(System.getProperty("user.dir"), "reports/" + repName).toURI().toURL();
            
            // Folder path where reports are stored
            File reportsDir = new File(System.getProperty("user.dir") + "/reports");

            // Get all files in the reports folder
            File[] files = reportsDir.listFiles((dir, name) -> name.endsWith(".html"));

            if (files == null || files.length == 0) {
                System.out.println("❌ No report files found in: " + reportsDir.getAbsolutePath());
                return;
            }

            // Sort by last modified time — latest first
            Arrays.sort(files, Comparator.comparingLong(File::lastModified).reversed());

            // Get the latest report file
            File latestReport = files[0];
            System.out.println("✅ Latest report found: " + latestReport.getName());

            // Create and send email
            ImageHtmlEmail email = new ImageHtmlEmail();
            email.setDataSourceResolver(new DataSourceUrlResolver(url));
            email.setHostName("smtp.gmail.com");      // Gmail SMTP server
            email.setSmtpPort(465);                   // SSL port
            email.setAuthenticator(new DefaultAuthenticator(
             //   "skbnawaz3@gmail.com"
            		"",                // 🔹 your Gmail address
                "bsdbbptjtawxphiq"                   // 🔹 app password (NOT your normal Gmail password)
            ));
            email.setSSLOnConnect(true);
          //  email.setFrom("nskb126@gmail.com", "Automation Reports"); // 🔹 sender info
            email.setSubject("Automation Test Results");                // 🔹 email subject
            email.setMsg("Hello,\n\nPlease find the attached automation test report.\n\nRegards,\nAutomation System");
           // email.addTo("skbnawaz3@gamil.com");                        // 🔹 recipient email(s)

            // Attach the latest report file
           // email.attach(latestReport, "Extent Report", "Automation test results");
            email.attach(latestReport.toURI().toURL(), "Extent Report", "Automation test results");


            // Send the email
            email.send();

            System.out.println("✅ Email sent successfully with attachment: " + latestReport.getName());

        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        
        

    }
}