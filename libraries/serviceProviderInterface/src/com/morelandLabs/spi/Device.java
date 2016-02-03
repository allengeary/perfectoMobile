package com.morelandLabs.spi;

import java.util.concurrent.Semaphore;

/**
 * The Interface Device.
 */
public interface Device
{
	
	/**
	 * Gets the manufacturer.
	 *
	 * @return the manufacturer
	 */
	String getManufacturer();
	
	/**
	 * Gets the model.
	 *
	 * @return the model
	 */
	String getModel();
	
	/**
	 * Gets the os.
	 *
	 * @return the os
	 */
	String getOs();
	
	/**
	 * Gets the os version.
	 *
	 * @return the os version
	 */
	String getOsVersion();
	
	/**
	 * Gets the browser name.
	 *
	 * @return the browser name
	 */
	String getBrowserName();
	
	/**
	 * Gets the browser version.
	 *
	 * @return the browser version
	 */
	String getBrowserVersion();
	
	/**
	 * Gets the available devices.
	 *
	 * @return the available devices
	 */
	int getAvailableDevices();
	
	/**
	 * Gets the key.
	 *
	 * @return the key
	 */
	String getKey();
	
	/**
	 * Gets the lock.
	 *
	 * @return the lock
	 */
	Semaphore getLock();
	
	/**
	 * Gets the driver type.
	 *
	 * @return the driver type
	 */
	String getDriverType();
	
	/**
	 * To short string.
	 *
	 * @return the string
	 */
	String toShortString();
	
	/**
	 * Gets the device name.
	 *
	 * @return the device name
	 */
	String getDeviceName();
	
	/**
	 * Checks if is active.
	 *
	 * @return true, if is active
	 */
	boolean isActive();
}
