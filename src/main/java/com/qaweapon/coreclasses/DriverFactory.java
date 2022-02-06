package com.qaweapon.coreclasses;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.BasicConfigurator;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.qaweapon.util.WebConfiguration;

import io.github.bonigarcia.wdm.WebDriverManager;

public class DriverFactory {

	public static WebDriver driver;

	public DriverFactory() {
	}

	public static MutableCapabilities setup(String env) {

		switch (env) {
		case "local":
			return setupLocal();
		case "remote":
			return setupRemote();
		default:
			return null;
		}
	}

	public static WebDriver build(String env, MutableCapabilities caps) throws IOException, InterruptedException {

		switch (env) {
		case "local":
			return buildLocal(caps);
		case "remote":
			return buildRemote(caps);
		default:
			return null;
		}
	}

	public static MutableCapabilities setupLocal() {

		String browser = WebConfiguration.getBrowserName();
		MutableCapabilities caps = null;

		switch (browser.toLowerCase()) {
		case "chrome":
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.addArguments("--start-maximized");
			caps = chromeOptions;
			break;
		case "edge":
			caps = new EdgeOptions();
			break;
		case "firefox":
			caps = new FirefoxOptions();
			break;
		default:
			break;
		}
		return caps;
	}

	public static MutableCapabilities setupRemote() {
		return new DesiredCapabilities();
	}

	public static WebDriver buildLocal(MutableCapabilities caps) throws IOException, InterruptedException {

		String browser = WebConfiguration.getBrowserName();

		if (WebConfiguration.getrunOnDocker().equalsIgnoreCase("true")) {

			driver = runOnDocker(browser);
		} else {
			switch (browser.toLowerCase()) {
			case "chrome":
				WebDriverManager.chromedriver().setup();
				driver = new ChromeDriver((ChromeOptions) caps);
				driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
				break;
			case "edge":
				WebDriverManager.edgedriver().setup();
				driver = new EdgeDriver((EdgeOptions) caps);
				break;
			case "firefox":
				WebDriverManager.firefoxdriver().setup();
				driver = new FirefoxDriver((FirefoxOptions) caps);
				break;
			default:
				throw new IllegalArgumentException("Provided browser '" + browser + "' is not supported.");
			}
		}

		return driver;
	}

	public static WebDriver buildRemote(MutableCapabilities caps) throws MalformedURLException {
		return new RemoteWebDriver(new URL(WebConfiguration.getRemoteURL()), caps);
	}

	public static void DestroyDriver() {
		driver.quit();
	}

	public static WebDriver runOnDocker(String browser) throws IOException, InterruptedException {

		BasicConfigurator.configure();

		if (browser.equalsIgnoreCase("firefox")) {

			System.out.println("Initializing Firefox Browser in Docker Container.");
			FirefoxProfile fprofile = new FirefoxProfile();
			fprofile.setPreference("browser.download.dir", "/home/seluser/Downloads");
			fprofile.setPreference("browser.download.folderList", 2);
			fprofile.setPreference("browser.download.manager.showWhenStarting", false);
			fprofile.setPreference("browser.helperApps.neverAsk.saveToDisk",
					"application/zip,application/octet-stream,application/x-zip-compressed,multipart/x-zip,application/x-rar-compressed, application/octet-stream,application/msword,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/rtf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xls,application/msword,text/csv,application/vnd.ms-excel.sheet.binary.macroEnabled.12,text/plain,text/csv/xls/xlsb,application/csv,application/download,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/octet-stream");
			fprofile.setPreference("browser.helperApps.neverAsk.openFile",
					"application/zip,application/octet-stream,application/x-zip-compressed,multipart/x-zip,application/x-rar-compressed, application/octet-stream,application/msword,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/rtf,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/vnd.ms-excel,application/vnd.ms-word.document.macroEnabled.12,application/vnd.openxmlformats-officedocument.wordprocessingml.document,application/xls,application/msword,text/csv,application/vnd.ms-excel.sheet.binary.macroEnabled.12,text/plain,text/csv/xls/xlsb,application/csv,application/download,application/vnd.openxmlformats-officedocument.presentationml.presentation,application/octet-stream");// MIME
																																																																																																																																																																																																																																																																																	// type
			fprofile.setPreference("browser.helperApps.alwaysAsk.force", false);
			fprofile.setPreference("browser.download.manager.alertOnEXEOpen", false);
			fprofile.setPreference("pdfjs.disabled", true);
			fprofile.setPreference("browser.download.panel.shown", false);
			FirefoxOptions options = new FirefoxOptions();
			options.setProfile(fprofile);

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

		} else if (browser.equalsIgnoreCase("chrome")) {
			System.out.println("Initializing Chrome Browser in Docker Container.");
			ChromeOptions options = new ChromeOptions();
			HashMap<String, Object> chromePref = new HashMap<>();
			chromePref.put("plugins.always_open_pdf_externally", true);
			chromePref.put("profile.default_content_settings.popups", 0);
			chromePref.put("download.prompt_for_download", false);
			chromePref.put("plugins.plugins_list", new String[] { "Chrome PDF Viewer" });
			chromePref.put("download.directory_upgrade", true);
			chromePref.put("download.extensions_to_open", "applications/pdf");
			chromePref.put("open_pdf_in_system_reader", false);
			chromePref.put("download_restrictions", 0);
			chromePref.put("download.default_directory", "/home/seluser/Downloads");

			options.setExperimentalOption("prefs", chromePref);

			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-notifications");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--start-maximized");

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

		} else {
			System.out.println("Initializing Edge Browser in Docker Container.");
			EdgeOptions options = new EdgeOptions();
			options.addArguments("disable-infobars");
			options.addArguments("--disable-extensions");
			options.addArguments("--disable-popup-blocking");
			options.addArguments("--disable-notifications");
			options.addArguments("enable-automation");
			options.addArguments("--no-sandbox");
			options.addArguments("--start-maximized");
			Map<String, Object> prefs = new HashMap<>();
			prefs.put("download.default_directory", "/home/seluser/Downloads");
			options.setExperimentalOption("prefs", prefs);

			driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), options);

		}
		return driver;
	}
}
