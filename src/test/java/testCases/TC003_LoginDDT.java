package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage_TC2;
import pageObjects.MyAccountPage_TC2;
import testbase.BaseClass;
import utilities.DataProviders;

/*
 Data is valid -->login success-->test Pass-->logout
 Data is valid-->Login failed-->test fail
 
 Data is invalid--login success-->test fail-->logout
 Data is invalid--login failed--test pass
 */
public class TC003_LoginDDT extends BaseClass
{
	
	@Test(dataProvider="LoginData",dataProviderClass=DataProviders.class,groups="Datadriven")//getting data provider from different class
    public void verify_loginDDT(String email,String pwd,String exp) 
    {
		logger.info("*****Starting TC003_LoginDDT*****");
		
		try
		{
    	//homepage
    	HomePage hp=new HomePage(driver);
    	hp.clickMyAccount();
    	hp.clickLogin();
    	
    	//Login Page
    	LoginPage_TC2 lp=new LoginPage_TC2(driver);
    	lp.setEmail(email);
    	lp.setPassword(pwd);
    	lp.clickLogin();
    	
    	//My AccountPage
    	MyAccountPage_TC2 macc=new MyAccountPage_TC2(driver);
    	boolean targetpage=macc.isMyAccountPageExists();
    	
    	
    	/*
    	 Data is valid -->login success-->test Pass-->logout
    	 Data is valid-->Login failed-->test fail
    	 
    	 Data is invalid--login success-->test fail-->logout
    	 Data is invalid--login failed--test pass
    	 */
    	
    	
    	if(exp.equalsIgnoreCase("Valid"))
    	{
    		if(targetpage==true)
    		{
    			macc.clickLogout();
    			Assert.assertTrue(true);
    		}
    		else
    		{
    			Assert.assertTrue(false);
    		}
    	}
    	if(exp.equalsIgnoreCase("Invalid"))
    	{
    		if(targetpage==true)
    		{
    			macc.clickLogout();
    			Assert.assertTrue(false);
    		}
    		else
    		{
    			Assert.assertTrue(true);
    		}
    	}
    	
		} 
		
		catch(Exception e)
		{
			Assert.fail();
		}
		
    	logger.info("*****Finished TC003_LoginDDT*****");
    }
}
