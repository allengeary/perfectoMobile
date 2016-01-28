package com.perfectoMobile.device;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.factory.DeviceWebDriver;

/**
 * The Class ConnectedDevice.
 */
public class ConnectedDevice
{
	private DeviceWebDriver webDriver;
	private Device device;
	
	/**
	 * Instantiates a new connected device.
	 *
	 * @param webDriver the web driver
	 * @param device the device
	 */
	public ConnectedDevice( DeviceWebDriver webDriver, Device device )
	{
		super();
		this.webDriver = webDriver;
		this.device = device;
	}

	/**
	 * Instantiates a new connected device.
	 */
	public ConnectedDevice()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Gets the web driver.
	 *
	 * @return the web driver
	 */
	public WebDriver getWebDriver()
	{
		return webDriver;
	}
	
	/**
	 * Sets the web driver.
	 *
	 * @param webDriver the new web driver
	 */
	public void setWebDriver( DeviceWebDriver webDriver )
	{
		this.webDriver = webDriver;
	}
	
	/**
	 * Gets the device.
	 *
	 * @return the device
	 */
	public Device getDevice()
	{
		return device;
	}
	
	/**
	 * Sets the device.
	 *
	 * @param device the new device
	 */
	public void setDevice( Device device )
	{
		this.device = device;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return device.toShortString();  
	}
	
}
