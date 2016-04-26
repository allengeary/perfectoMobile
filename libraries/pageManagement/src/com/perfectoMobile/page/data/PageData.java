package com.perfectoMobile.page.data;

import java.util.List;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * Represents a single row in a page data table.
 */
public interface PageData
{
	public static Pattern SELECTOR = Pattern.compile( "\\|(\\w+):([^\\|]+)\\|" );
	public static Pattern VALUES = Pattern.compile( "(?:\\[(\\w+)=[']([^']+)[']\\])" );
	public static String DEF = ".def";
	public static String TREE_MARKER = "|";
	
	/**
	 * Gets the named field from this page data object
	 *
	 * @param fieldName the field name
	 * @return The value to return in String format
	 */
	public String getData( String fieldName );
	
	/**
	 * Gets the object record type
	 *
	 * @return the type
	 */
	public String getType();
	
	/**
	 * Gets the name of this object value
	 *
	 * @return the name
	 */
	public String getName();
	
	/**
	 * Checks if the record is active.
	 *
	 * @return true, if is active
	 */

	public boolean isActive();
	
	public List<PageData> getPageData( String fieldName );
	
	public boolean containsChildren();
	
	public void populateTreeStructure();
}
