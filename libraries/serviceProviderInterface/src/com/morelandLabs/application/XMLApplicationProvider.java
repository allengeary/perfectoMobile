package com.morelandLabs.application;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.morelandLabs.application.xsd.Application;
import com.morelandLabs.application.xsd.DeviceCapability;
import com.morelandLabs.application.xsd.ObjectFactory;
import com.morelandLabs.application.xsd.RegistryRoot;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLApplicationProvider.
 */
public class XMLApplicationProvider extends AbstractApplicationProvider
{
	/** The file name. */
	private File fileName;
	
	/** The resource name. */
	private String resourceName;
	
	/**
	 * Instantiates a new XML application provider.
	 *
	 * @param fileName the file name
	 */
	public XMLApplicationProvider( File fileName )
	{
		this.fileName = fileName;
		readData();
	}
	
	/**
	 * Instantiates a new XML application provider.
	 *
	 * @param resourceName the resource name
	 */
	public XMLApplicationProvider( String resourceName )
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
	
	/**
	 * Read elements.
	 *
	 * @param inputStream the input stream
	 */
	private void readElements( InputStream inputStream )
	{
		
		try
		{
		
		    JAXBContext jc = JAXBContext.newInstance( ObjectFactory.class );
            Unmarshaller u = jc.createUnmarshaller();
            JAXBElement<?> rootElement = (JAXBElement<?>)u.unmarshal( inputStream );
            
            RegistryRoot rRoot = (RegistryRoot)rootElement.getValue();

			for ( Application app : rRoot.getApplication() )
				parseApplication( app );

			
		}
		catch( Exception e )
		{
			log.fatal( "Error reading CSV Element File", e );
		}
	}
	
	/**
	 * Parses the application.
	 *
	 * @param appNode the app node
	 */
	private void parseApplication( Application app )
	{
	    Map<String,String> capabilities = new HashMap<String,String>( 10 );
	    if ( app.getCapability() != null )
	    {
	        for ( DeviceCapability cap : app.getCapability() )
	            capabilities.put( cap.getName(), cap.getValue() );
	    }
	    
	    ApplicationRegistry.instance().addApplicationDescriptor( new ApplicationDescriptor( app.getName(), "", app.getAppPackage(), app.getBundleId(), app.getUrl(), app.getIosInstall(), app.getAndroidInstall(), capabilities ) );
	}
	
}
