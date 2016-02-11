package com.perfectoMobile.device.factory.spi;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Platform;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.cloud.CloudDescriptor;
import com.perfectoMobile.device.cloud.CloudRegistry;
import com.perfectoMobile.device.factory.AbstractDriverFactory;
import com.perfectoMobile.device.factory.DeviceWebDriver;

import io.appium.java_client.ios.IOSDriver;

/**
 * A factory for creating WEBDriver objects.
 */
public class WEBDriverFactory extends AbstractDriverFactory
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.perfectoMobile.device.factory.AbstractDriverFactory#_createDriver(com
	 * .perfectoMobile.device.Device)
	 */
	@Override
	protected DeviceWebDriver _createDriver( Device currentDevice )
	{
		String os = currentDevice.getOs();
		if ( os != null )
		{
			os = os.toUpperCase();
			switch ( os )
			{
				case "IOS":
				case "ANDROID":
					return _createMobileDriver( currentDevice );
					
				case "WINDOWS":
				case "MAC":
				case "UNIX":
				case "WIN8":
				case "WIN8_1":
				case "XP":
					return _createDesktopDriver( currentDevice );
					
				default:
					return _createMobileDriver( currentDevice );	
			}
		}
		else
			return _createMobileDriver( currentDevice );

	}

	private DeviceWebDriver _createDesktopDriver( Device currentDevice )
	{
		DeviceWebDriver webDriver = null;
		try
		{
			DesiredCapabilities dc = new DesiredCapabilities();

			CloudDescriptor useCloud = CloudRegistry.instance().getCloud();

			if (useCloud.getGridInstance() != null && !useCloud.getGridInstance().isEmpty())
			{
				useCloud = CloudRegistry.instance().getCloud( useCloud.getGridInstance() );
				if (useCloud == null)
				{
					useCloud = CloudRegistry.instance().getCloud();
					log.warn( "A seperate grid instance was specified but it does not exist in your cloud registry [" + useCloud.getGridInstance() + "] - using the Cloud instance" );
				}
			}

			URL hubUrl = new URL( "http://" + useCloud.getHostName() + "/wd/hub" );
			dc.setPlatform( Platform.ANY );
			dc.setCapability( PLATFORM_NAME, currentDevice.getOs() );
			if ( currentDevice.getOsVersion() != null && !currentDevice.getOsVersion().isEmpty() )
				dc.setCapability( PLATFORM_VERSION, currentDevice.getOsVersion() );
			dc.setCapability( BROWSER_NAME, currentDevice.getBrowserName().toLowerCase() );
			if ( currentDevice.getBrowserVersion() != null && !currentDevice.getBrowserVersion().isEmpty() )
				dc.setCapability( BROWSER_VERSION, currentDevice.getBrowserVersion() );

			if (useCloud.getUserName() != null && !useCloud.getUserName().isEmpty())
				dc.setCapability( USER_NAME, useCloud.getUserName() );

			if (useCloud.getPassword() != null && !useCloud.getPassword().isEmpty())
				dc.setCapability( PASSWORD, useCloud.getPassword() );

			if (currentDevice.getManufacturer() != null && !currentDevice.getManufacturer().isEmpty())
				dc.setCapability( MANUFACTURER, currentDevice.getManufacturer() );
			
			if (currentDevice.getModel() != null && !currentDevice.getModel().isEmpty())
				dc.setCapability( MODEL, currentDevice.getManufacturer() );
			
			for ( String name : currentDevice.getCabilities().keySet() )
				dc.setCapability( name, currentDevice.getCabilities().get( name ) );
			
			for ( String name : ApplicationRegistry.instance().getAUT().getCapabilities().keySet() )
				dc.setCapability( name, currentDevice.getCabilities().get( name ) );
			
			if ( log.isInfoEnabled() )
				log.info( "Acquiring Device as: \r\n" + capabilitiesToString( dc ) );
			
			webDriver = new DeviceWebDriver( new RemoteWebDriver( hubUrl, dc ), DeviceManager.instance().isCachingEnabled(), currentDevice );

			webDriver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );

			Capabilities caps = ( ( RemoteWebDriver ) webDriver.getWebDriver() ).getCapabilities();
			webDriver.setExecutionId( ( ( RemoteWebDriver ) webDriver.getWebDriver() ).getSessionId().toString() );
			webDriver.setDeviceName( ( ( RemoteWebDriver ) webDriver.getWebDriver() ).getSessionId().toString() );

			if (ApplicationRegistry.instance().getAUT().getUrl() != null)
				webDriver.get( ApplicationRegistry.instance().getAUT().getUrl() );

			return webDriver;
		}
		catch (Exception e)
		{
			log.fatal( "Could not connect to Cloud instance for " + currentDevice, e );
			if (webDriver != null)
			{
				try
				{
					webDriver.close();
				}
				catch (Exception e2)
				{
				}
				try
				{
					webDriver.quit();
				}
				catch (Exception e2)
				{
				}
			}
			return null;
		}
	}

	private DeviceWebDriver _createMobileDriver( Device currentDevice )
	{
		DeviceWebDriver webDriver = null;
		try
		{
			DesiredCapabilities dc = null;
			if (currentDevice.getBrowserName() != null && !currentDevice.getBrowserName().isEmpty())
				dc = new DesiredCapabilities( currentDevice.getBrowserName(), "", Platform.ANY );
			else
				dc = new DesiredCapabilities( "", "", Platform.ANY );

			URL hubUrl = new URL( CloudRegistry.instance().getCloud().getCloudUrl() );

			if (currentDevice.getDeviceName() != null && !currentDevice.getDeviceName().isEmpty())
			{
				dc.setCapability( ID, currentDevice.getDeviceName() );
				dc.setCapability( USER_NAME, CloudRegistry.instance().getCloud().getUserName() );
				dc.setCapability( PASSWORD, CloudRegistry.instance().getCloud().getPassword() );
			}
			else
			{
				dc.setCapability( PLATFORM_NAME, currentDevice.getOs() );
				dc.setCapability( PLATFORM_VERSION, currentDevice.getOsVersion() );
				dc.setCapability( MODEL, currentDevice.getModel() );
				dc.setCapability( USER_NAME, CloudRegistry.instance().getCloud().getUserName() );
				dc.setCapability( PASSWORD, CloudRegistry.instance().getCloud().getPassword() );
			}

			webDriver = new DeviceWebDriver( new RemoteWebDriver( hubUrl, dc ), DeviceManager.instance().isCachingEnabled(), currentDevice );
			webDriver.manage().timeouts().implicitlyWait( 10, TimeUnit.SECONDS );

			Capabilities caps = ( ( RemoteWebDriver ) webDriver.getWebDriver() ).getCapabilities();
			webDriver.setExecutionId( caps.getCapability( "executionId" ).toString() );
			webDriver.setReportKey( caps.getCapability( "reportKey" ).toString() );
			webDriver.setDeviceName( caps.getCapability( "deviceName" ).toString() );
			webDriver.setWindTunnelReport( caps.getCapability( "windTunnelReportUrl" ).toString() );

			if (ApplicationRegistry.instance().getAUT().getUrl() != null)
				webDriver.get( ApplicationRegistry.instance().getAUT().getUrl() );

			return webDriver;
		}
		catch (Exception e)
		{
			log.fatal( "Could not connect to Cloud instance for " + currentDevice, e );
			if (webDriver != null)
			{
				try
				{
					webDriver.close();
				}
				catch (Exception e2)
				{
				}
				try
				{
					webDriver.quit();
				}
				catch (Exception e2)
				{
				}
			}
			return null;
		}
	}
}
