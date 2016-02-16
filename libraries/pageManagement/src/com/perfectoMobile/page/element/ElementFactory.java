package com.perfectoMobile.page.element;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectoMobile.page.BY;


// TODO: Auto-generated Javadoc
/**
 * A factory for creating Element objects.
 */
public class ElementFactory 
{
	
	/** The Constant DEFAULT_IMPL. */
	private static final String DEFAULT_IMPL = "Selenium";
	
	/** The log. */
	private Log log = LogFactory.getLog( getClass() );
	
	/**
	 * The Enum ElementType.
	 */
	public enum ElementType
	{
		
		/** The Selenium. */
		Selenium( 0, "Selenium" );
		
		/** The id. */
		private int id;
		
		/** The desc. */
		private String desc;
		
		/**
		 * Instantiates a new element type.
		 *
		 * @param id the id
		 * @param desc the desc
		 */
		ElementType( int id, String desc )
		{
			this.id = id;
			this.desc = desc;
		}

		/**
		 * Gets the id.
		 *
		 * @return the id
		 */
		public int getId()
		{
			return id;
		}

		/**
		 * Gets the desc.
		 *
		 * @return the desc
		 */
		public String getDesc()
		{
			return desc;
		}
		
		
	}
	
	/** The singleton. */
	private static ElementFactory singleton = new ElementFactory();
    

    /**
     * Instance.
     *
     * @return the element factory
     */
    public static ElementFactory instance()
    {
        return singleton;
    }

    /**
     * Instantiates a new element factory.
     */
    private ElementFactory() 
    {
    	
    }
    
    /**
     * Creates a new Element object.
     *
     * @param by the by
     * @param keyName the key name
     * @param fieldName the field name
     * @param pageName the page name
     * @param contextName the context name
     * @return the element
     */
    public Element createElement( BY by, String keyName, String fieldName, String pageName, String contextName )
    {
    	String implName = System.getProperty( "elementImplementation" );
    	
    	if ( implName == null )
    	{
    		if ( log.isDebugEnabled() )
    			log.debug( "No element implementation was provider - Using the default [" + DEFAULT_IMPL + "]" );
    		implName = DEFAULT_IMPL;
    	}
    	
    	String implementationName = this.getClass().getPackage().getName() + "." + implName + "Element";
    	
    	if ( log.isInfoEnabled() )
    		log.info( "Creating Element by [" + by + " as [" + keyName + "] as [" + implementationName + "]" );
    	
    	Element currentElement = null;
    	
    	try
    	{
    		currentElement = (Element) Class.forName( implementationName ).getConstructor( BY.class, String.class, String.class, String.class, String.class ).newInstance( new Object[] { by, keyName, fieldName, pageName, contextName } );
    	}
    	catch( Exception e )
    	{
    		if ( log.isFatalEnabled() )
    			log.fatal( "Could not create the element implementation of " + implementationName + "].  Please make sure that the elementImplementation property has been set", e );
    	}
    	
    	return currentElement;
    }
    
    
    
}
