package testbase;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;
import org.apache.logging.log4j.LogManager;//log4j
import org.apache.logging.log4j.Logger;//log4j

public class BaseClass 
{
//public  WebDriver driver;	
public static WebDriver driver;//for extent report we have to make driver static

public Logger logger;//Log4j
public Properties p;
	
	@BeforeClass(groups= {"Sanity","Regression","Master"})
	@Parameters({"os","browser"})
    public  void setup(String os,String br) throws IOException 
     {
		//Loading config.properties file
		FileReader file=new FileReader("./src//test//resources//config.properties");
		p=new Properties();
		p.load(file);
		
		
		
		
	logger=LogManager.getLogger(this.getClass());//this condition stmt will acess log files from src/test/resources 
	
	//for remote execution selenium grid condtn
	if(p.getProperty("execution_env").equalsIgnoreCase("remote"))
	{
		DesiredCapabilities capabilities = new DesiredCapabilities();
		
		//we should check for OS
		if(os.equalsIgnoreCase("windows"))
		{
			capabilities.setPlatform(Platform.WIN10);
		}
		else if(os.equalsIgnoreCase("mac"))
		{
			capabilities.setPlatform(Platform.WIN10);
		}
		else
		{
			System.out.println("No matching OS");
			return;
		}
		
		//we should check for browser
		switch(br.toLowerCase())
		{
		case "chrome" : capabilities.setBrowserName("chrome");
		break;
		
		case "edge" : capabilities.setBrowserName("MicrosoftEdge");
		break;
		
		default : System.out.println("No matching browser");
		break;
		}
		
		driver=new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"),capabilities);
	}
	
	//local selenium grid set up
	
	if(p.getProperty("execution_env").equalsIgnoreCase("local"))
	{
		switch(br.toLowerCase())//run on multiple browser condition
		{
		case "chrome" :driver=new ChromeDriver();break;
		case "edge" :driver=new EdgeDriver();break;
		case "firefox" :driver=new FirefoxDriver();break;
		default:System.out.println("invalid browser name....");return;
		}	
	}
	
	
	
  /*switch(br.toLowerCase())//run on multiple browser condition
	{
	case "chrome" :driver=new ChromeDriver();break;
	case "edge" :driver=new EdgeDriver();break;
	case "firefox" :driver=new FirefoxDriver();break;
	default:System.out.println("invalid browser name....");return;
	}*/
	
	
	// driver =new ChromeDriver();
	 driver.manage().deleteAllCookies();
	 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
	 
	 //driver.get("https://tutorialsninja.com/demo/");
	 driver.get(p.getProperty("appURL"));
	 driver.manage().window().maximize();
     }
     
	@AfterClass(groups= {"Sanity","Regression","Master"})
    public void tearDown() 
     {
    	 driver.quit();
     }
	 public String randomString() 
	    {
	    	String generatedstring=RandomStringUtils.randomAlphabetic(5);
	    	return generatedstring;
	    }
	    
	    public String randomNumber() 
	    {
	    	String generatednum=RandomStringUtils.randomNumeric(10);
	    	return generatednum;
	    }
	    
	    public String randomAlphaNumeric() 
	    {
	    	String generatedstring=RandomStringUtils.randomAlphabetic(3);
	    	String generatednum=RandomStringUtils.randomNumeric(3);
	    	return (generatedstring+"@"+generatednum);
	    }
	    public String captureScreen(String tname) throws IOException {

			String timeStamp = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
					
			TakesScreenshot takesScreenshot = (TakesScreenshot) driver;
			File sourceFile = takesScreenshot.getScreenshotAs(OutputType.FILE);
			
			String targetFilePath=System.getProperty("user.dir")+"\\screenshots\\" + tname + "_" + timeStamp + ".png";
			File targetFile=new File(targetFilePath);
			
			sourceFile.renameTo(targetFile);
				
			return targetFilePath;

		}
}
