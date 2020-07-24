package SeleniumPractice;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class LinkedIn {
	WebDriver driver;

	@BeforeTest
	public void initDriver() {
		WebDriverManager.chromedriver().setup();
		driver=new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	@Test(dataProvider="searchData")
	public void LinkedInSearch(String username,String password) throws InterruptedException {
		driver.get("https://www.linkedin.com");
		driver.findElement(By.xpath("//a[contains(text(),'Sign in')]")).click();
		//Sign in to LinkedIn with Email Address and Password
	    Thread.sleep(2000);
		WebElement emailAddress = driver.findElement(By.xpath("//*[@id='username']"));
		emailAddress.sendKeys(username);
		WebElement Password = driver.findElement(By.xpath("//*[@id='password']"));
		Password.sendKeys(password);
		driver.findElement(By.xpath("//button[contains(text(),'Sign in')]")).click();
		WebElement mouseOverTo = driver.findElement(By.xpath("//input[@class='search-global-typeahead__input always-show-placeholder']"));
		Actions action = new Actions(driver);
		action.moveToElement(mouseOverTo).click().build().perform();
		//In the Suggested Searches Click Sales
		mouseOverTo.sendKeys("Sales" + "\n");
		driver.findElement(By.xpath("//header[@class='msg-overlay-bubble-header']")).click();
		driver.findElement(By.xpath("//button[@aria-controls='experience-level-facet-values']")).click();
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//input[@type='checkbox' and @id='experience-4']")));
		driver.findElement(By.xpath("//div[@id='experience-level-facet-values']//button/following-sibling::button")).click();
		System.out.println("Experience Choosed as Mid Senior Level & Click Ok");
		WebElement numberOfFilters=driver.findElement(By.xpath("//button[@data-control-name='clear_filters']/span/span"));
		String Actual =numberOfFilters.getText();
		String Expected="1";
		Assert.assertEquals(Expected, Actual);
		driver.findElement(By.xpath("//button[@data-control-name='all_filters']/span")).click();
		jse.executeScript("arguments[0].click()", driver.findElement(By.xpath("//input[@type='checkbox' and @id='jobType-F']")));
		System.out.println("asserted the number in clear filters as 1");
		WebElement AllFilterClear=driver.findElement(By.xpath("//button[@data-control-name='all_filters_clear']/span/span"));
		String Actual1 =AllFilterClear.getText();
		String Expected1="2";
		Assert.assertEquals(Expected1, Actual1);
		WebElement img=driver.findElement(By.xpath("//img[@class='nav-item__profile-member-photo nav-item__icon ember-view']"));
		action.moveToElement(img).click().build().perform();
		System.out.println("Asserted the number in clear filters as 2");
		WebElement signOut =driver.findElement(By.xpath("//a[@id='ember38']"));
		action.moveToElement(signOut).click().build().perform();

}
	@DataProvider(name="searchData")
	public Object[][] credentials() {
		return new Object[][] { {"sainath0056@gmail.com","linkedin25"} };
	}
	@AfterTest
	public void quitBrowswer() {
		driver.quit();
	}

}
