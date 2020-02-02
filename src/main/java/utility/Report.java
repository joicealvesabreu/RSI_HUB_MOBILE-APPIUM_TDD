package utility;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;



public class Report {

	public static WebDriver driver;
	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public static ExtentReports setExtent() {

		htmlReporter = new ExtentHtmlReporter(System.getProperty("user.dir") + "/target/Report.html");
		htmlReporter.config().setDocumentTitle("Relatorio de Automacao Mobile"); // Titulo do report
		htmlReporter.config().setReportName("Relatorio Funcional"); // Nome do report
		htmlReporter.config().setTheme(Theme.DARK); // Seleciona o tema

		extent = new ExtentReports();

		extent.attachReporter(htmlReporter);
		
		return extent;
	}

	/* Fecha o relatorio */
	public static void closeReport(ExtentReports extent) {
		extent.flush();
	}

	/* Cria o Report */
	public static ExtentTest setUp(String report) {
		test = extent.createTest(report);
		return test;
	}

	/* Verifica se o teste passou, falhou ou pulou e tira screenshot */
	public static void tearDown(ITestResult result, ExtentTest test, WebDriver driver) throws IOException {
		String screenshotPath = Utility.getScreenshot(driver);
		if (result.getStatus() == ITestResult.FAILURE) {
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getName()); 
			test.log(Status.FAIL, "TEST CASE FAILED IS " + result.getThrowable()); 
			test.addScreenCaptureFromPath(screenshotPath); 

		} else if (result.getStatus() == ITestResult.SKIP) {
			test.log(Status.SKIP, "Test Case SKIPPED IS " + result.getName());
			test.addScreenCaptureFromPath(screenshotPath); 

		} else if (result.getStatus() == ITestResult.SUCCESS) {
			test.log(Status.PASS, "Test Case PASSED IS " + result.getName());
			test.addScreenCaptureFromPath(screenshotPath);
		}
	}
}
