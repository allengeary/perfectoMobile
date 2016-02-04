package com.perfectoMobile.device.data;

/**
 * The Interface DataProvider.
 */
public interface DataProvider
{
	/**
	 * The Enum DriverType.
	 */
	public enum DriverType
	{
		
		/** The web. */
		WEB,
		
		/** The perfecto. */
		PERFECTO,
		
		/** The appium. */
		APPIUM;
	}
	/**
	 * Read data.
	 */
	public void readData();
}
