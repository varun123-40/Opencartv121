package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage_TC2;
import pageObjects.MyAccountPage_TC2;
import testbase.BaseClass;

public class TC002_LoginTest extends BaseClass
{
	@Test(groups={"Sanity","Master"})
      public void verify_login() 
      {
    	logger.info("*****Starting TC002_LoginTest*****"); 
    	
    	try
    	{
    	//homepage
    	HomePage hp=new HomePage(driver);
    	hp.clickMyAccount();
    	hp.clickLogin();
    	
    	//Login Page
    	LoginPage_TC2 lp=new LoginPage_TC2(driver);
    	lp.setEmail(p.getProperty("email"));
    	lp.setPassword(p.getProperty("password"));
    	lp.clickLogin();
    	
    	//My AccountPage
    	MyAccountPage_TC2 macc=new MyAccountPage_TC2(driver);
    	boolean targetpage=macc.isMyAccountPageExists();
    	
    	//Assert.assertEquals(targetpage, true,"Login failed");
    	Assert.assertTrue(targetpage);
    	}
    	catch(Exception e)
    	{
    		Assert.fail();
    	}
    	
    	logger.info("*****Finished TC002_LoginTest*****"); 
      }
}
