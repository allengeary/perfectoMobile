package com.perfectoMobile.page.element;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

/**
 * The Interface Element.
 */
public interface Element 
{
	
	/** The Constant CONTEXT_ELEMENT. */
	public static final String CONTEXT_ELEMENT = "_CONTEXT.ELEMENT";
	
	/** The Constant CONTEXT_INDEX. */
	public static final String CONTEXT_INDEX = "_CONTEXT.ELEMENT.INDEX";
	
	/**
	 * Gets the native.
	 *
	 * @return the native
	 */
	public Object getNative();
	
	/**
	 * Gets the value.
	 *
	 * @return the value
	 */
	public String getValue();
	
	/**
	 * Checks if is visible.
	 *
	 * @return true, if is visible
	 */
	public boolean isVisible();
	
	/**
	 * Checks if is present.
	 *
	 * @return true, if is present
	 */
	public boolean isPresent();
	
	/**
	 * Wait for visible.
	 *
	 * @param timeOut the time out
	 * @param timeUnit the time unit
	 * @return true, if successful
	 */
	public boolean waitForVisible( long timeOut, TimeUnit timeUnit  );
	
	/**
	 * Wait for present.
	 *
	 * @param timeOut the time out
	 * @param timeUnit the time unit
	 * @return true, if successful
	 */
	public boolean waitForPresent( long timeOut, TimeUnit timeUnit  );
	
	/**
	 * Gets the attribute.
	 *
	 * @param attributeName the attribute name
	 * @return the attribute
	 */
	public String getAttribute( String attributeName );
	
	/**
	 * Sets the value.
	 *
	 * @param currentValue the new value
	 */
	public void setValue( String currentValue  );
	
	/**
	 * Click.
	 */
	public void click();
	
	/**
	 * Sets the driver.
	 *
	 * @param webDriver the new driver
	 */
	public void setDriver( Object webDriver );
	
	/**
	 * Sets the timed.
	 *
	 * @param timed the new timed
	 */
	public void setTimed( boolean timed );
	
	/**
	 * Checks if is timed.
	 *
	 * @return true, if is timed
	 */
	public boolean isTimed();

	/**
	 * Gets the all.
	 *
	 * @return the all
	 */
	public Element[] getAll();
	
	/**
	 * Gets the index.
	 *
	 * @return the index
	 */
	public int getIndex();
	
	/**
	 * Gets the count.
	 *
	 * @return the count
	 */
	public int getCount();
	
	/**
	 * Gets the context.
	 *
	 * @return the context
	 */
	public Element getContext();
	
	/**
	 * Sets the context.
	 *
	 * @param context the new context
	 */
	public void setContext( Element context );
	
	
	/**
	 * The Enum TAG.
	 */
	public enum TAG
	{
		
		/** The input. */
		INPUT ( "input" ),
		
		/** The img. */
		IMG ( "img" );
		
		private String tagName;
		
		/**
		 * Instantiates a new tag.
		 *
		 * @param tagName the tag name
		 */
		TAG( String tagName )
		{
			this.tagName = tagName;
		}
		
	}
	
	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId();
	
	/**
	 * Gets the device name.
	 *
	 * @return the device name
	 */
	public String getDeviceName();
	
	/**
	 * Adds the token.
	 *
	 * @param tokenName the token name
	 * @param tokenValue the token value
	 */
	public void addToken( String tokenName, String tokenValue );
	
	/**
	 * Adds the token.
	 *
	 * @param tokenPairValue the token pair value
	 */
	public void addToken( String tokenPairValue );
	
	/**
	 * Clone element.
	 *
	 * @return the element
	 */
	public Element cloneElement();
}
