package com.morelandLabs.application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * The Class CSVApplicationProvider.
 */
public class CSVApplicationProvider extends AbstractApplicationProvider
{
	private File fileName;
	private String resourceName;
	
	/**
	 * Instantiates a new CSV application provider.
	 *
	 * @param fileName the file name
	 */
	public CSVApplicationProvider( File fileName )
	{
		this.fileName = fileName;
		readData();
	}
	
	/**
	 * Instantiates a new CSV application provider.
	 *
	 * @param resourceName the resource name
	 */
	public CSVApplicationProvider( String resourceName )
	{
		this.resourceName = resourceName;
		readData();
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.application.ApplicationProvider#readData()
	 */
	public void readData()
	{
		if ( fileName == null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Reading from CLASSPATH as " + resourceName );
			readElements( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				if ( log.isInfoEnabled() )
					log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				readElements( new FileInputStream( fileName ) );
			}
			catch( FileNotFoundException e )
			{
				log.fatal( "Could not read from " + fileName, e );
			}
		}
	}
	
	private void readElements( InputStream inputStream )
	{
		BufferedReader fileReader = new BufferedReader( new InputStreamReader( inputStream ) );
		String currentLine = null;
		
		try
		{
			while ( ( currentLine = fileReader.readLine() ) != null )
			{
				if ( log.isDebugEnabled() )
					log.debug( "Attempting to parse current line as [" + currentLine + "]" );
				
				String[] lineData = currentLine.split( "," );
				
				ApplicationRegistry.instance().addApplicationDescriptor( new ApplicationDescriptor( lineData[ 0 ], lineData[ 1 ], lineData[ 2 ], lineData[ 3 ], lineData[ 4 ], lineData[ 5 ], lineData[ 6 ] ) );
			}
		}
		catch( Exception e )
		{
			log.fatal( "Error reading CSV Element File", e );
		}
	}
}
