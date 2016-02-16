package com.perfectoMobile.device.factory.spi;

import java.net.URL;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.interactions.HasTouchScreen;
import org.openqa.selenium.interactions.TouchScreen;
import org.openqa.selenium.remote.RemoteTouchScreen;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * The Class DeviceRemoteWebDriver.
 */
public class DeviceRemoteWebDriver extends RemoteWebDriver implements HasTouchScreen
{
	private RemoteTouchScreen touchScreen;
	
	/**
	 * Instantiates a new device remote web driver.
	 *
	 * @param remoteAddress the remote address
	 * @param desiredCapabilities the desired capabilities
	 */
	public DeviceRemoteWebDriver( URL remoteAddress, Capabilities desiredCapabilities )
	{
		super( remoteAddress, desiredCapabilities );
		touchScreen = new RemoteTouchScreen( getExecuteMethod() );
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.interactions.HasTouchScreen#getTouch()
	 */
	@Override
	public TouchScreen getTouch()
	{
		return touchScreen;
	}
}
