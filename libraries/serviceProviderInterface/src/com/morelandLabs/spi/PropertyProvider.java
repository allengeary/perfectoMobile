package com.morelandLabs.spi;

/**
 * The Interface PropertyProvider.
 */
public interface PropertyProvider
{
	
	/**
	 * Returns a single proprety value.
	 *
	 * @param propertyName the property name
	 * @return the native driver
	 * A proeprty value
	 */
	public String getProperty( String propertyName );
}
