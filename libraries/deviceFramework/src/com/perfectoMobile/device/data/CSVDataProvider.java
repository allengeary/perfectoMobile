package com.perfectoMobile.device.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.SimpleDevice;

/**
 * The Class CSVDataProvider.
 */
public class CSVDataProvider implements DataProvider
{
	private Log log = LogFactory.getLog( CSVDataProvider.class );
	private File fileName;
	private String resourceName;
	private DriverType driverType;

	/**
	 * Instantiates a new CSV data provider.
	 *
	 * @param fileName            the file name
	 * @param driverType the driver type
	 */
	public CSVDataProvider( File fileName, DriverType driverType )
	{
		this.fileName = fileName;
		this.driverType = driverType;
	}

	/**
	 * Instantiates a new CSV data provider.
	 *
	 * @param resourceName            the resource name
	 * @param driverType the driver type
	 */
	public CSVDataProvider( String resourceName, DriverType driverType )
	{
		this.resourceName = resourceName;
		this.driverType = driverType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.perfectoMobile.device.data.DataProvider#readData()
	 */
	public void readData()
	{
		if (fileName == null)
		{
			if (log.isInfoEnabled())
				log.info( "Reading Device Data from Resource " + resourceName );

			readData( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				readData( new FileInputStream( fileName ) );
			}
			catch (Exception e)
			{
				log.fatal( "Could mot read from " + fileName, e );
			}
		}
	}

	private void readData( InputStream inputStream )
	{
		BufferedReader fileReader = null;

		try
		{
			fileReader = new BufferedReader( new InputStreamReader( inputStream ) );

			String currentLine = null;
			while (( currentLine = fileReader.readLine() ) != null)
			{
				if (log.isDebugEnabled())
					log.debug( "Read [" + currentLine + "]" );

				String[] lineData = currentLine.split( "," );
				
				
				
				String driverName = "";
				switch( driverType )
				{
					case APPIUM:
						if ( lineData[3].toUpperCase().equals( "IOS" ) )
							driverName = "IOS";
						else if ( lineData[3].toUpperCase().equals( "ANDROID" ) )
							driverName = "ANDROID";
						else
							throw new IllegalArgumentException( "Appium is not supported on the following OS " + lineData[3].toUpperCase() );
						break;
						
					case PERFECTO:
						driverName = "PERFECTO";
						break;
						
					case WEB:
						driverName = "WEB";
						break;
				}
				
				Device currentDevice = new SimpleDevice( lineData[0], lineData[1], lineData[2], lineData[3], lineData[4], lineData[5], lineData[6], Integer.parseInt( lineData[7] ), driverName, Boolean.parseBoolean( lineData[8] ), null );

				if ( currentDevice.isActive() )
				{				
					if (log.isDebugEnabled())
						log.debug( "Extracted: " + currentDevice );
	
					DeviceManager.instance().registerDevice( currentDevice );
				}
			}

		}
		catch (Exception e)
		{
			log.fatal( "Error reading device data", e );
		}
		finally
		{
			try
			{
				fileReader.close();
			}
			catch (Exception e)
			{
			}
		}
	}
}
