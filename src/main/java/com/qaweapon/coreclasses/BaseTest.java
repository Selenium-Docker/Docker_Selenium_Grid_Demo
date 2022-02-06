/**
 * BaseTest is super class for all test classes.
 */
package com.qaweapon.coreclasses;

import java.io.IOException;
import java.lang.reflect.Method;

import org.apache.log4j.PropertyConfigurator;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentTest;
import com.qaweapon.util.ExtentReportGenerator;
import com.qaweapon.util.WebConfiguration;

/**
 * @author vivek.parmar
 *
 */
public class BaseTest {

	ExtentReportGenerator extentreportgenerator;
	DriverFactory driverfactory;
	public WebPageGenerator webPageGenerator;
	public ExtentTest test;

	public BaseTest() {
		extentreportgenerator = new ExtentReportGenerator();
	}

	@BeforeSuite
	public void beforeSuite() {

		extentreportgenerator.startExtentReport();
		PropertyConfigurator.configure("log4j.properties");

	}

	@BeforeTest
	public void beforeTest() throws IOException, InterruptedException {

		DriverFactory.build(WebConfiguration.getDriverEnvironment(),
				DriverFactory.setup(WebConfiguration.getDriverEnvironment()));
		webPageGenerator = new WebPageGenerator(DriverFactory.driver);
		DriverFactory.driver.get(WebConfiguration.getRemoteURL());

	}

	@BeforeMethod
	public void beforeMethod(Method method) {
		test = extentreportgenerator.createExtentReport(method);
	}

	@AfterMethod
	public void afterMethod(ITestResult result) {

		extentreportgenerator.captureTestResult(result);
	}

	@AfterTest
	public void afterTest() {

	}

	@AfterSuite
	public void afterSuite() {

		extentreportgenerator.endExtentReport();
		DriverFactory.DestroyDriver();
	}

}
