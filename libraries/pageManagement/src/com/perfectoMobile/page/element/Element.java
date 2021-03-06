package com.perfectoMobile.page.element;

import java.awt.Image;
import java.util.concurrent.TimeUnit;

import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution;


// TODO: Auto-generated Javadoc
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
	 * The Enum WAIT_FOR.
	 */
	public enum WAIT_FOR
    {
        
        /** The clickable. */
        CLICKABLE,
        
        /** The selectable. */
        SELECTABLE,
        
        /** The invisible. */
        INVISIBLE,
        
        /** The present. */
        PRESENT,
        
        /** The text present. */
        TEXT_PRESENT,
        
        /** The text value present. */
        TEXT_VALUE_PRESENT,
        
        /** The title contains. */
        TITLE_CONTAINS,
        
        /** The title is. */
        TITLE_IS,
        
        /** The visible. */
        VISIBLE;
    }
	
	/**
	 * Move to.
	 *
	 * @return true, if successful
	 */
	public boolean moveTo();
	
	/**
	 * Press.
	 *
	 * @return true, if successful
	 */
	public boolean press();
	
	/**
	 * Release.
	 *
	 * @return true, if successful
	 */
	public boolean release();
	
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
	 * Gets the style.
	 *
	 * @param styleProperty the style property
	 * @return the style
	 */
	public String getStyle( String styleProperty );
	
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
	
	
	public boolean isFocused();
	/**
	 * Wait for visible.
	 *
	 * @param timeOut the time out
	 * @param timeUnit the time unit
	 * @return true, if successful
	 */
	public boolean waitForVisible( long timeOut, TimeUnit timeUnit  );
	
	/**
	 * Wait for.
	 *
	 * @param timeout the timeout
	 * @param timeUnit the time unit
	 * @param waitType the wait type
	 * @param value the value
	 * @return true, if successful
	 */
	public boolean waitFor( long timeout, TimeUnit timeUnit, WAIT_FOR waitType, String value );
	
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
		
		/** The tag name. */
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
	
	
	/**
	 * Locates an element and extracts it as an image given the element 
	 * had specified coordinates and size.
	 *
	 * @param imageResolution the image resolution
	 * @return the element
	 */
	public Image getImage( Resolution imageResolution );
}
