package com.perfectoMobile.device.factory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.data.DataManager;

/**
 * A factory for creating AbstractDriver objects.
 */
public abstract class AbstractDriverFactory implements DriverFactory
{

	/** The log. */
	protected Log log = LogFactory.getLog( DriverFactory.class );
	
	/**
	 * _create driver.
	 *
	 * @param currentDevice the current device
	 * @return the device web driver
	 */
	protected abstract DeviceWebDriver _createDriver( Device currentDevice );
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.factory.DriverFactory#createDriver(com.perfectoMobile.device.Device)
	 */
	public DeviceWebDriver createDriver( Device currentDevice )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Creating Driver for " + getClass().getSimpleName() );
		
		DeviceWebDriver webDriver = _createDriver( currentDevice ); 
		
		if ( webDriver != null )
			webDriver.setArtifactProducer( DataManager.instance().getArtifactProducer() );
		
		return webDriver;
	}
	
	/**
	 * Capabilities to string.
	 *
	 * @param caps the caps
	 * @return the string
	 */
	protected String capabilitiesToString( Capabilities caps )
	{
		StringBuilder capData = new StringBuilder();
		
		for ( String keyName : caps.asMap().keySet() )
		{
			capData.append( keyName ).append( "=" ).append( caps.getCapability( keyName ) + "\r\n" );
		}
		
		return capData.toString();
	}
	
	protected void addDeviceCapabilities( Device currentDevice, DesiredCapabilities caps )
	{
		for ( String name : currentDevice.getCabilities().keySet() )
			caps.setCapability( name, currentDevice.getCabilities().get( name ) );
		
		
	}

}
