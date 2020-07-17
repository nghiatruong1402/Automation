package automationfc;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Exercise_Xpath_TC {

	WebDriver driver;

	@BeforeClass
	public void beforeClass() {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	@Test
	public void TC_01_Login_with_empty_email_Password() {

		// Mở trang URL livedemoguru
		driver.get("http://live.demoguru99.com/index.php");

		// Click my account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();

		// Leave email and password blank
		// input[@id='email'] - UNIQUE email param
		//// input[@id='pass'] - UNIQUE password param
		driver.findElement(By.id("email")).sendKeys("");
		driver.findElement(By.id("pass")).sendKeys("");

		// Bấm vào nút login
		driver.findElement(By.name("send")).click();

		// Verify
		// Actual Message : Hàm getText từ sever trả về
		//// div[@id='advice-required-entry-email'] (để so sánh với text có trong
		// document"This is a required field.")
		driver.findElement(By.xpath("//div[@id='advice-required-entry-email']")).getText();

		// Expected message = text mong muốn nhận được trong SRS
		// "This is a required field."
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-required-entry-email']")).getText(),
				"This is a required field.");
	}

	@Test
	public void TC_02_Login_with_Invalid_email() {

		driver.get("http://live.demoguru99.com/index.php");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("123123@123");
		driver.findElement(By.id("pass")).sendKeys("231215124");
		driver.findElement(By.name("send")).click();
		driver.findElement(By.xpath("//div[@id='advice-validate-email-email']")).getText();
		Assert.assertEquals(driver.findElement(By.xpath("//div[@id='advice-validate-email-email']")).getText(),
				"Please enter a valid email address. For example johndoe@domain.com.");
	}

	@Test
	public void TC_03_Login_with_Invalid_Password() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("nghia.truong@asiantech.vn");
		driver.findElement(By.id("pass")).sendKeys("1234");
		driver.findElement(By.name("send")).click();
		driver.findElement(By.className("validation-advice")).getText();
		Assert.assertEquals(driver.findElement(By.className("validation-advice")).getText(),
				"Please enter 6 or more characters without leading or trailing spaces.");
	}

	@Test
	public void TC_04_Login_with_Incorrect_Password() {
		driver.get("http://live.demoguru99.com/index.php");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.id("email")).sendKeys("nghia.truong@asiantech.vn");
		driver.findElement(By.id("pass")).sendKeys("12828761824");
		driver.findElement(By.name("send")).click();
		driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText();
		Assert.assertEquals(driver.findElement(By.xpath("//li[@class='error-msg']//span")).getText(),
				"Invalid login or password.");

	}

	@Test
	public void TC_05_Login_with_valid_Emailpassword() throws InterruptedException {
		driver.get("http://live.demoguru99.com/index.php");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.className("validate-email")).sendKeys("automation_13@gmail.com");
		driver.findElement(By.name("login[password]")).sendKeys("123123");
		driver.findElement(By.xpath("//button[@title='Login']")).click();

		// Verify các field cần được verify
		driver.findElement(By.xpath("//h1[text()='My Dashboard']")).isDisplayed();
		driver.findElement(By.xpath("//strong[text()='Hello, Automation Testing!']")).isDisplayed();
		driver.findElement(By.xpath("//div[@class='box-content']//p[contains(text(),'Automation')]")).isDisplayed();
		driver.findElement(By.xpath("//div[@class='box-content']//p[contains(.,'@gmail.com')]")).isDisplayed();

		// Click account then click log out
		driver.findElement(By.xpath("//div[@class='skip-links']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//div[@id='header-account']//li[last()]/a")).click();

	}

	@Test
	public void TC_06_Create_an_account() {

		// Open brower/Điền thông tin/Click đăng kí
		driver.get("http://live.demoguru99.com/index.php");
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		driver.findElement(By.xpath("//span[text()='Create an Account']")).click();
		driver.findElement(By.name("firstname")).sendKeys("Nghia");
		driver.findElement(By.id("lastname")).sendKeys("Truong Minh");
		String email = "nghia.truong" +randomNumber()+ "@gmail.com";
		driver.findElement(By.className("validate-email")).sendKeys(email);
		driver.findElement(By.xpath("//input[@title='Password']")).sendKeys("pig0703");
		driver.findElement(By.id("confirmation")).sendKeys("pig0703");
		driver.findElement(By.xpath("//button[@title='Register']")).click();

		// Verify Firstname/Lastname/email hiển thị ở trang Dashboard
		String Successmessage = driver.findElement(By.xpath("//li[@class='success-msg']")).getText();

		// Kiểm tra Text "Thank you for registering with Main Website Store."
		Assert.assertEquals(Successmessage, "Thank you for registering with Main Website Store.");

		// Kiếm tra First/Lastname
		driver.findElement(By.xpath("//div[@class='box']//p[contains(text(),'Nghia Truong Minh')]")).isDisplayed();
		driver.findElement(By.xpath("//div[@class='box']//p[contains(.,'" + email + "')]")).isDisplayed();

		// Click account then click logout
		driver.findElement(By.xpath("//header[@id='header']//span[text()='Account']")).click();
		driver.findElement(By.xpath("//a[@title='Log Out']")).click();

		// Chờ element(image) hiển thị trên trang
		driver.findElement(By.xpath("//img[contains(@src,'logo.png')]")).isDisplayed();

	}

	public static int randomNumber() {
		Random rand = new Random();
		return rand.nextInt(99);
	}

	@AfterClass // Post-codition
	public void afterClass() {
		driver.quit();
	}
}
