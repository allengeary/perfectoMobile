package com.perfectoMobile.device;

import java.util.concurrent.Semaphore;

import com.morelandLabs.spi.Device;

/**
 * The Class SimpleDevice.
 */
public class SimpleDevice implements Device
{

	private String key;
	private String manufacturer;
	private String model;
	private String os;
	private String osVersion;
	private String browserName;
	private String browserVersion;
	private int availableDevices;
	private Semaphore deviceLock;
	private String driverType;
	private String deviceName;
	private boolean active;
	
	/* (non-Javadoc)
	 * @see com.morelandLabs.spi.Device#isActive()
	 */
	public boolean isActive()
	{
		return active;
	}


	private String cachedString;
	
	/**
	 * Instantiates a new simple device.
	 *
	 * @param deviceName the device name
	 * @param driverType the driver type
	 */
	public SimpleDevice( String deviceName, String driverType )
	{
		this.deviceName = deviceName;
		key = deviceName;
		availableDevices = 1;
		deviceLock = new Semaphore( availableDevices );
		this.driverType = driverType;
	}
	
	/**
	 * Instantiates a new simple device.
	 *
	 * @param key the key
	 * @param manufacturer the manufacturer
	 * @param model the model
	 * @param os the os
	 * @param osVersion the os version
	 * @param browserName the browser name
	 * @param browserVersion the browser version
	 * @param availableDevices the available devices
	 * @param driverType the driver type
	 * @param active the active
	 * @param deviceName the device name
	 */
	public SimpleDevice( String key, String manufacturer, String model, String os, String osVersion, String browserName, String browserVersion, int availableDevices, String driverType, boolean active, String deviceName )
	{
		super();
		this.key = key;
		this.manufacturer = manufacturer;
		this.model = model;
		this.os = os;
		this.osVersion = osVersion;
		this.browserName = browserName;
		this.browserVersion = browserVersion;
		this.availableDevices = availableDevices;
		this.driverType = driverType;
		this.deviceName = deviceName;
		this.active = active;
		deviceLock = new Semaphore( availableDevices );
		
		cachedString = manufacturer + " " + model + " [" + key + "] --> ";
	}
	
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getDeviceName()
	 */
	public String getDeviceName()
	{
		return deviceName;
	}
	
	//public String toString()
	//{
	//	return cachedString;
	//}

	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#toShortString()
	 */
	public String toShortString()
	{
		return cachedString;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return manufacturer + " " + model + " (" + deviceName + ")";
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getKey()
	 */
	public String getKey()
	{
		return key;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getDriverType()
	 */
	public String getDriverType()
	{
		return driverType;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getManufacturer()
	 */
	public String getManufacturer()
	{
		return manufacturer;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getModel()
	 */
	public String getModel()
	{
		return model;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getOs()
	 */
	public String getOs()
	{
		return os;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getOsVersion()
	 */
	public String getOsVersion()
	{
		return osVersion;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getBrowserName()
	 */
	public String getBrowserName()
	{
		return browserName;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getBrowserVersion()
	 */
	public String getBrowserVersion()
	{
		return browserVersion;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getAvailableDevices()
	 */
	public int getAvailableDevices()
	{
		return availableDevices;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.Device#getLock()
	 */
	public Semaphore getLock()
	{
		return deviceLock;
	}
}
