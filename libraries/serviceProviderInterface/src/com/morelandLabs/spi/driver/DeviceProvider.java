package com.morelandLabs.spi.driver;


import com.morelandLabs.spi.Device;

/**
 * The Interface NativeDriverProvider.
 */
public interface DeviceProvider
{
	
	/**
	 * Gets the native driver.
	 *
	 * @return the native driver
	 */
	public Device getDevice();
}
