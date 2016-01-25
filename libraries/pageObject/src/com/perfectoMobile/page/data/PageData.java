package com.perfectoMobile.page.data;

/**
 * Represents a single row in a page data table.
 */
public interface PageData
{
	
	/**
	 * Gets the data.
	 *
	 * @param fieldName the field name
	 * @return the data
	 */
	public String getData( String fieldName );
	
	/**
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType();
	
	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Checks if the record is active
	 * @return
	 */

	public boolean isActive();
}
