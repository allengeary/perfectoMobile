package com.perfectoMobile.device.data;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;
import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.SimpleDevice;

/**
 * The Class CSVDataProvider.
 */
public class NamedDataProvider implements DataProvider
{
	private Log log = LogFactory.getLog( NamedDataProvider.class );
	
	private String[] namedResources = null; 
	private DriverType driverType;
	
	/**
	 * Instantiates a new named data provider.
	 *
	 * @param resourceName A comma separated list of resource names
	 * @param driverType the driver type
	 */
	public NamedDataProvider( String resourceName, DriverType driverType )
	{
		namedResources = resourceName.split( "," );
		this.driverType = driverType;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.data.DataProvider#readData()
	 */
	public void readData()
	{
		for ( String device : namedResources )
		{
			Handset handset = PerfectoMobile.instance().devices().getDevice( device );
			
			String driverName = "";
			switch( driverType )
			{
				case APPIUM:
					if ( handset.getOs().equals( "iOS" ) )
						driverName = "IOS";
					else if ( handset.getOs().equals( "Android" ) )
						driverName = "ANDROID";
					else
						throw new IllegalArgumentException( "Appium is not supported on the following OS " + handset.getOs() );
					break;
					
				case PERFECTO:
					driverName = "PERFECTO";
					break;
					
				case WEB:
					driverName = "WEB";
					break;
			}
			
			DeviceManager.instance().registerDevice( new SimpleDevice( device, handset.getManufacturer(), handset.getModel(), handset.getOs(), handset.getOsVersion(), null, null, 1, driverName, true, device ) );

			
			
		}
	}
}
