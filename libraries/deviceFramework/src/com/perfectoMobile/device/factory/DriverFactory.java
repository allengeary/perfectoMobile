/*
 * 
 */
package com.perfectoMobile.device.factory;

import com.morelandLabs.spi.Device;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating Driver objects.
 */
public interface DriverFactory
{
	
	/** The Constant USER_NAME. */
	public static final String USER_NAME = "user";
	
	/** The Constant PASSWORD. */
	public static final String PASSWORD = "password";
	
	/** The Constant PLATFORM_NAME. */
	public static final String PLATFORM_NAME = "platformName";
	
	/** The Constant PLATFORM_VERSION. */
	public static final String PLATFORM_VERSION = "platformVersion";
	
	/** The Constant MANUFACTURER. */
	public static final String MANUFACTURER = "manufacturer";
	
	/** The Constant MODEL. */
	public static final String MODEL = "model";
	
	/** The Constant ID. */
	public static final String ID = "deviceName";
	
	/** The Constant RESOLUTION. */
	public static final String RESOLUTION = "resolution";
	
	/** The Constant NETWORK. */
	public static final String NETWORK = "network";
	
	/** The Constant LOCATION. */
	public static final String LOCATION = "location";
	
	/** The Constant DESCRIPTION. */
	public static final String DESCRIPTION = "description";
	
	/** The Constant AUTOMATION_NAME. */
	public static final String AUTOMATION_NAME = "automationName";
	
	/** The Constant APPLICATION_ACTIVITY. */
	public static final String APPLICATION_ACTIVITY = "appActivity";
	
	/** The Constant APPLICATION_PACKAGE. */
	public static final String APPLICATION_PACKAGE = "appPackage";
	
	/** The Constant APPLICATION_PACKAGE. */
	public static final String APPLICATION_NAME = "appName";
	
	/** The Constant BUNDLE_ID. */
	public static final String BUNDLE_ID = "bundleId";
	
	/** The Constant BROWSER NAME. */
	public static final String BROWSER_NAME = "browserName";
	
	/** The Constant BROWSER VERSION. */
	public static final String BROWSER_VERSION = "browserVersion";
	
	/** The Constant BROWSER VERSION. */
    public static final String DEVICE_INTERRUPT = "deviceInterrupt";
	
	/**
	 * Creates a new Driver object.
	 *
	 * @param currentDevice the current device
	 * @return the device web driver
	 */
	public DeviceWebDriver createDriver( Device currentDevice );
}
