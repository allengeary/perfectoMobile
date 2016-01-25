package com.morelandLabs.spi.driver;


import org.openqa.selenium.WebDriver;

/**
 * The Interface NativeDriverProvider.
 */
public interface NativeDriverProvider
{
	
	/**
	 * Gets the native driver.
	 *
	 * @return the native driver
	 */
	public WebDriver getNativeDriver();
}
