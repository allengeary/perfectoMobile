package com.perfectoMobile.device.factory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The Class DriverManager.
 */
public class DriverManager
{
	private static DriverManager singleton = new DriverManager();
	
	/**
	 * Instance.
	 *
	 * @return the driver manager
	 */
	public static DriverManager instance()
	{
		return singleton;
	}

	private DriverManager()
	{

	}

	private Log log = LogFactory.getLog( DriverManager.class );
	private Map<String,DriverFactory> driverMap = new HashMap<String,DriverFactory>( 20 );
	
	
	/**
	 * Gets the driver factory.
	 *
	 * @param driverName the driver name
	 * @return the driver factory
	 */
	public synchronized DriverFactory getDriverFactory( String driverName )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Getting Driver Factory for " + driverName );
		
		DriverFactory driverFactory = driverMap.get( driverName );
		
		if ( driverFactory == null )
		{
			String className = DriverFactory.class.getPackage().getName() + ".spi." + driverName + "DriverFactory";
			
			if ( log.isInfoEnabled() )
				log.info( "Creating Driver Factory as " + className );
			
			try
			{
				driverFactory = (DriverFactory)Class.forName( className ).newInstance();
				driverMap.put( driverName, driverFactory );
			}
			catch( Exception e )
			{
				log.fatal( "Could not create DriverFactory for " + className, e );
			}
		}
		
		return driverFactory;
	}
}
