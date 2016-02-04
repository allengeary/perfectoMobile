package com.perfectoMobile.device.data.perfectoMobile;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.SimpleDevice;
import com.perfectoMobile.device.cloud.CloudRegistry;
import com.perfectoMobile.device.data.DataProvider;

/**
 * The Class PerfectoMobileDataProvider.
 */
public class PerfectoMobileDataProvider implements DataProvider
{
	
	
	
	private Log log = LogFactory.getLog( PerfectoMobileDataProvider.class );
	private PerfectoMobileHandsetValidator pmValidator;
	private DriverType driverType;
	
	/**
	 * Instantiates a new perfecto mobile data provider.
	 *
	 * @param pmValidator the pm validator
	 * @param driverType the driver type
	 */
	public PerfectoMobileDataProvider( PerfectoMobileHandsetValidator pmValidator, DriverType driverType )
	{
		this.pmValidator = pmValidator;
		this.driverType = driverType;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.data.DataProvider#readData()
	 */
	public void readData()
	{
		try
		{
			URL deviceURL = new URL( "https://" + CloudRegistry.instance().getCloud().getHostName() + "/services/handsets?operation=list&user=" + CloudRegistry.instance().getCloud().getUserName() + "&password=" + CloudRegistry.instance().getCloud().getPassword() );
			
			if ( log.isInfoEnabled() )
				log.info( "Reading Devices from " + deviceURL.toString() );
			
			URLConnection urlConnection = deviceURL.openConnection();
			urlConnection.setDoInput( true );
			
			InputStream inputStream = urlConnection.getInputStream();
				
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse( inputStream );
			
			NodeList handSets = doc.getElementsByTagName( "handset" );
			
			if ( log.isInfoEnabled() )
				log.info( "Analysing handsets using [" + pmValidator.getClass().getSimpleName() + "]" );
			
			boolean deviceFound = false;
			
			for ( int i=0; i<handSets.getLength(); i++ )
			{
				
				if ( pmValidator.validate( handSets.item(  i  ) ) )
				{
					String driverName = "";
					switch( driverType )
					{
						case APPIUM:
							String osType = getValue( handSets.item(  i  ), "os" );
							if ( osType.equals( "iOS" ) )
								driverName = "IOS";
							else if ( osType.equals( "Android" ) )
								driverName = "ANDROID";
							else
								throw new IllegalArgumentException( "Appium is not supported on the following OS " + osType );
							break;
							
						case PERFECTO:
							driverName = "PERFECTO";
							break;
							
						case WEB:
							driverName = "WEB";
							break;
					}
					
					deviceFound = true;
					DeviceManager.instance().registerDevice( new SimpleDevice( getValue( handSets.item(  i  ), "deviceId" ), getValue( handSets.item(  i  ), "manufacturer" ), getValue( handSets.item(  i  ), "model" ), getValue( handSets.item(  i  ), "os" ), getValue( handSets.item(  i  ), "osVersion" ), null, null, 1, driverName, true, getValue( handSets.item(  i  ), "deviceId" ) ) );
				}
				
			}
			
			if ( !deviceFound )
				log.warn( pmValidator.getMessage() );
			
			inputStream.close();
			
		}
		catch( Exception e )
		{
			e.printStackTrace( );
		}
	}
	
	private String getValue( Node handSet, String tagName )
	{
		NodeList params = handSet.getChildNodes();
		
		for ( int i=0; i<params.getLength(); i++ )
		{
			if ( params.item( i ).getNodeName().toLowerCase().equals( tagName.toLowerCase() ) )
			{
				return params.item( i ).getTextContent();
			}
		}
		
		return null;
	}
	

}
