package com.morelandLabs.driver;

import java.util.HashMap;
import java.util.Map;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.Test;
import com.morelandLabs.spi.RunDetails;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.data.DataManager;
import com.perfectoMobile.device.factory.DeviceWebDriver;
import com.perfectoMobile.device.ng.AbstractSeleniumTest;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.keyWord.KeyWordDriver;
import com.perfectoMobile.page.keyWord.KeyWordTest;

public class XMLTestDriver extends AbstractSeleniumTest
{
	@Test( dataProvider = "deviceManager")
	public void testDriver( TestName testName ) throws Throwable
	{
		String deviceOs = getDevice().getOs();
		
		PageManager.instance().clearExecutionLog();
		
		KeyWordTest test = KeyWordDriver.instance().getTest( testName.getTestName() );
		if ( test == null )
			throw new IllegalArgumentException( "The Test Name " + testName + " does not exist" );
		
		if ( test.getOs() != null && deviceOs != null )
		{
			if ( !deviceOs.toUpperCase().equals(  test.getOs().toUpperCase() ) )
				throw new SkipException( "This test is not designed to work on a device with [" + deviceOs + "]  It needs [" + test.getOs() + "]" );
		}
		
		if ( DeviceManager.instance().isDryRun() )
		{
			log.info( "This would have executed " +  testName.getTestName() );
			return;
		}
		
		boolean returnValue = KeyWordDriver.instance().executeTest( testName.getTestName(), getWebDriver() );
		
		Map<String,String> additionalUrls = new HashMap<String,String>( 10 );
		String wtUrl = ( (DeviceWebDriver) getWebDriver() ).getWindTunnelReport();
		if ( wtUrl != null && !wtUrl.isEmpty() )
			additionalUrls.put( "Wind Tunnel Report", wtUrl );
		
		
		PageManager.instance().writeExecutionRecords( additionalUrls, getDevice(), testName.getTestName(), testName.getTestName() + ( ( testName.getPersonaName() != null && !testName.getPersonaName().isEmpty() ) ? "." + testName.getPersonaName() : "" ) );
		PageManager.instance().writeExecutionTimings();
		RunDetails.instance().writeHTMLIndex( DataManager.instance().getReportFolder() );
		
		
		if ( returnValue )
			return;
		
		if ( PageManager.instance().getThrowable() != null )
			throw PageManager.instance().getThrowable();
		
		
		Assert.assertTrue( returnValue );
	} 
	
	@AfterSuite
	public void afterSuite() throws Throwable
	{
	    RunDetails.instance().writeHTMLIndex( DataManager.instance().getReportFolder() );
	}
}
