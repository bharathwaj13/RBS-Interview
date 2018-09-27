package stepdefinition;

import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

public class Steps {

	public static ChromeDriver driver;

	@Given("Launch the application using the URL as (.*)")
	public void launch(String data) {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver=new ChromeDriver();
		driver.get(data);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		if(driver.findElementByXPath("//a[@title='My Store']/img").isDisplayed())
			System.out.println("Login page displays - Pass");
		else
			System.out.println("Login page does not display - Fail");
		
	}

	@Given("Login using username as (.*) and Password as (.*)")
	public void login(String username,String password) {
		driver.findElementByXPath("//a[@class='login']").click();
		driver.findElementByXPath("//input[@id='email']").sendKeys(username);
		driver.findElementByXPath("//input[@id='passwd']").sendKeys(password);
		driver.findElementByXPath("//button[@id='SubmitLogin']").click();
		if(driver.findElementByXPath("//a[@class='logout']").isDisplayed())
			System.out.println("Login is successful - Pass");
		else
			System.out.println("Login is unsuccessful - Fail");
		
	}
	@And("Select the Tshirt by adding to Cart")
	public void selectTShirt() {
		String strSectionHeader;
		driver.findElementByXPath("(//a[@title='T-shirts'])[2]").click();
		strSectionHeader=driver.findElementByXPath("//span[@class='cat-name']").getText();
		if(strSectionHeader.contains("T-SHIRTS"))
			System.out.println("T-Shirt section appears - Pass");
		else
			System.out.println("T-Shirt section does not appear - Fail");
		WebElement tShirt = driver.findElementByXPath("//a[@title='Faded Short Sleeve T-shirts']");

		Actions builder=new Actions(driver);
		builder.moveToElement(tShirt).click(driver.findElementByXPath("//a[@title='Add to cart']")).perform();

		WebDriverWait wait=new WebDriverWait(driver,30);
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@title='Proceed to checkout']/span")));
		driver.findElementByXPath("//a[@title='Proceed to checkout']/span").click();
		if(driver.findElementByXPath("//h1[text()[contains(.,'Shopping-cart summary')]]").isDisplayed())
			System.out.println("Shopping Cart Summary page displays - Pass");
		else
			System.out.println("Shopping Cart Summary page does not display - Fail");
	}

	@When("Checkout the Selected Tshirt by completing Payment")
	public void completeCheckout() {
		driver.findElementByXPath("(//a[@title='Proceed to checkout'])[2]").click();
		driver.findElementByXPath("//button[@name='processAddress']").click();
		driver.findElementByXPath("//input[@id='cgv']").click();
		driver.findElementByXPath("//button[@name='processCarrier']").click();
		driver.findElementByXPath("//a[@class='cheque']").click();
		if(driver.findElementByXPath("//h3[text()='Check payment']").isDisplayed())
			System.out.println("Check Payment page displays - Pass");
		else
			System.out.println("Check Payment page does not display - Fail");
		driver.findElementByXPath("//span[text()[contains(.,'I confirm my order')]]").click();
		if(driver.findElementByXPath("//p[text()[contains(.,'Your order on My Store is complete.')]]").isDisplayed())
			System.out.println("Order successfuly placed - Pass");
		else
			System.out.println("Order unsuccessful - Fail");
	}

	@Then("Verify Order in Order History")
	public void verifyOrderHistory() throws InterruptedException {
		String reference = driver.findElementByXPath("//div[@class='box order-confirmation']").getText();
		//		System.out.println(reference);
		String patt="Do not forget to include your order reference [A-Z]{9}";
		Pattern p=Pattern.compile(patt);
		Matcher m =p.matcher(reference);
		if(m.find())
			System.out.println(m.group(0));
		String order = m.group(0).split("Do not forget to include your order reference ")[1].trim();
		System.out.println(order);
		driver.findElementByXPath("//a[text()='Back to orders']").click();
		/*Thread.sleep(1000);
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("return window.stop");*/
		String orderTable = driver.findElementByXPath("//table[@id='order-list']//tbody/tr[1]/td/a").getText().trim();
		if(order.equalsIgnoreCase(orderTable))
			System.out.println("Order is present in order history - Pass");
		else
			System.out.println("Order is not present in order history - Fail");
	}

	@And("Logout")
	public void logout() {
		driver.findElementByXPath("//a[@class='logout']").click();
		if(driver.findElementByXPath("//a[@class='login']").isDisplayed())
			System.out.println("Logout is successful - Pass");
		else
			System.out.println("Logout is unsuccessful - Fail");
		driver.close();
	}

	@And("Click My Personal Information Link")
	public void clickPersonalInfo() {
		driver.findElementByXPath("//a[@title='Information']/span").click();
		if(driver.findElementByXPath("//h1[text()[contains(.,'Your personal information')]]").isDisplayed())
			System.out.println("Personal Information page is displayed - Pass");
		else
			System.out.println("Personal Information page is not displayed - Fail");
	}

	@When("Update FirstName as (.*) using Password as (.*)")
	public void updateFirstName(String data,String pwd) {
		driver.findElementByXPath("//input[@id='firstname']").clear();
		driver.findElementByXPath("//input[@id='firstname']").sendKeys(data);
		driver.findElementByXPath("//input[@id='old_passwd']").sendKeys(pwd);
		driver.findElementByXPath("//button[@name='submitIdentity']").click();
	}

	@Then("Successfully updated message appears")
	public void verifySuccessMessage() {

		if(driver.findElementByXPath("//p[text()[contains(.,'Your personal information has been successfully updated.')]]").isDisplayed())
			System.out.println("Successfully updated message appears - Pass");
		else
			System.out.println("Successfully updated message does not appear - Fail");
	}

}
