package com.perfectoMobile.device.factory.spi;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.cloud.CloudRegistry;
import com.perfectoMobile.device.factory.AbstractDriverFactory;
import com.perfectoMobile.device.factory.DeviceWebDriver;

import io.appium.java_client.ios.IOSDriver;

/**
 * A factory for creating IOSDriver objects.
 */
public class IOSDriverFactory extends AbstractDriverFactory
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.factory.AbstractDriverFactory#_createDriver(com.perfectoMobile.device.Device)
	 */
	@Override
	protected DeviceWebDriver _createDriver( Device currentDevice )
	{
		DeviceWebDriver webDriver = null;
		try
		{
			DesiredCapabilities dc = new DesiredCapabilities( "", "", Platform.ANY );
			URL hubUrl = new URL( CloudRegistry.instance().getCloud().getCloudUrl() );
	
			if ( currentDevice.getDeviceName() != null && !currentDevice.getDeviceName().isEmpty() )
			{
				dc.setCapability( ID, currentDevice.getDeviceName() );
			}
			else
			{
				dc.setCapability( PLATFORM_NAME, currentDevice.getOs() );
				dc.setCapability( PLATFORM_VERSION, currentDevice.getOsVersion() );
				dc.setCapability( MODEL, currentDevice.getModel() );
			}
			
			dc.setCapability( USER_NAME, CloudRegistry.instance().getCloud().getUserName() );
			dc.setCapability( PASSWORD, CloudRegistry.instance().getCloud().getPassword() );
			
			for ( String name : currentDevice.getCabilities().keySet() )
				dc.setCapability( name, currentDevice.getCabilities().get( name ) );
			
			for ( String name : ApplicationRegistry.instance().getAUT().getCapabilities().keySet() )
				dc.setCapability( name, currentDevice.getCabilities().get( name ) );
			
			dc.setCapability( AUTOMATION_NAME, "Appium" );
			if( ApplicationRegistry.instance().getAUT().getAppleIdentifier() != null && !ApplicationRegistry.instance().getAUT().getAppleIdentifier().isEmpty() )
				dc.setCapability( BUNDLE_ID, ApplicationRegistry.instance().getAUT().getAppleIdentifier() );
			
			
			if ( log.isInfoEnabled() )
				log.info( "Acquiring Device as: \r\n" + capabilitiesToString( dc ) );
			
			webDriver = new DeviceWebDriver( new IOSDriver( hubUrl, dc ), DeviceManager.instance().isCachingEnabled(), currentDevice );

			webDriver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );
			
			Capabilities caps = ( (IOSDriver) webDriver.getWebDriver() ).getCapabilities();
			webDriver.setExecutionId( caps.getCapability( "executionId" ).toString() );
			webDriver.setReportKey( caps.getCapability( "reportKey" ).toString() );
			webDriver.setDeviceName( caps.getCapability( "deviceName" ).toString() );
			webDriver.setWindTunnelReport( caps.getCapability( "windTunnelReportUrl" ).toString() );
			
			return webDriver;
		}
		catch( Exception e )
		{
			log.fatal( "Could not connect to Cloud instance for " + currentDevice + "[" + e.getMessage() + "]" );
			if ( webDriver != null )
			{
				try { webDriver.close(); } catch( Exception e2 ) {}
				try { webDriver.quit(); } catch( Exception e2 ) {}
			}
			return null;
		}
	}
}
