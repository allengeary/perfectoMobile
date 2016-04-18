package com.morelandLabs.driver;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import org.testng.TestNG;
import org.openqa.selenium.WebDriver;
import com.morelandLabs.Initializable;
import com.morelandLabs.utility.SeleniumSessionManager;
import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.application.CSVApplicationProvider;
import com.morelandLabs.application.ExcelApplicationProvider;
import com.morelandLabs.application.XMLApplicationProvider;
import com.morelandLabs.artifact.ArtifactType;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.rest.bean.factory.BeanManager;
import com.morelandLabs.integrations.rest.bean.factory.XMLBeanFactory;
import com.morelandLabs.spi.CSVRunListener;
import com.morelandLabs.spi.RunDetails;
import com.perfectoMobile.content.ContentManager;
import com.perfectoMobile.content.provider.ExcelContentProvider;
import com.perfectoMobile.content.provider.XMLContentProvider;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.cloud.CSVCloudProvider;
import com.perfectoMobile.device.cloud.CloudRegistry;
import com.perfectoMobile.device.cloud.ExcelCloudProvider;
import com.perfectoMobile.device.cloud.XMLCloudProvider;
import com.perfectoMobile.device.data.CSVDataProvider;
import com.perfectoMobile.device.data.DataManager;
import com.perfectoMobile.device.data.DataProvider.DriverType;
import com.perfectoMobile.device.data.ExcelDataProvider;
import com.perfectoMobile.device.data.NamedDataProvider;
import com.perfectoMobile.device.data.XMLDataProvider;
import com.perfectoMobile.device.data.perfectoMobile.AvailableHandsetValidator;
import com.perfectoMobile.device.data.perfectoMobile.PerfectoMobileDataProvider;
import com.perfectoMobile.device.data.perfectoMobile.ReservedHandsetValidator;
import com.perfectoMobile.device.factory.DriverManager;
import com.perfectoMobile.device.logging.ThreadedFileHandler;
import com.perfectoMobile.device.property.PropertyAdapter;
import com.perfectoMobile.device.ng.AbstractSeleniumTest;
import com.perfectoMobile.gesture.GestureManager;
import com.perfectoMobile.gesture.device.action.DeviceActionManager;
import com.perfectoMobile.gesture.device.action.spi.perfecto.PerfectoDeviceActionFactory;
import com.perfectoMobile.gesture.factory.spi.PerfectoGestureFactory;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageDataManager;
import com.perfectoMobile.page.data.provider.ExcelPageDataProvider;
import com.perfectoMobile.page.data.provider.XMLPageDataProvider;
import com.perfectoMobile.page.element.provider.CSVElementProvider;
import com.perfectoMobile.page.element.provider.ExcelElementProvider;
import com.perfectoMobile.page.element.provider.XMLElementProvider;
import com.perfectoMobile.page.keyWord.KeyWordDriver;
import com.perfectoMobile.page.keyWord.KeyWordTest;
import com.perfectoMobile.page.keyWord.provider.XMLKeyWordProvider;

public class TestDriver
{
	private static final String[] CLOUD = new String[] { "cloudRegistry.provider", "cloudRegistry.fileName", "cloudRegistry.cloudUnderTest" };
	private static final String[] APP = new String[] { "applicationRegistry.provider", "applicationRegistry.fileName", "applicationRegistry.applicationUnderTest" };
	private static final String[] ARTIFACT = new String[] { "artifactProducer.parentFolder" };
	private static final String[] PAGE = new String[] { "pageManagement.siteName", "pageManagement.provider", "pageManagement.fileName" };
	private static final String[] DATA = new String[] { "pageManagement.pageData.provider", "pageManagement.pageData.fileName" };
	private static final String[] CONTENT = new String[] { "pageManagement.content.provider", "pageManagement.content.fileName" };
	private static final String[] DEVICE = new String[] { "deviceManagement.provider", "deviceManagement.driverType" };
	private static final String[] DRIVER = new String[] { "driver.frameworkType", "driver.configName" };

	public static void main( String[] args )
	{
		if ( args.length != 1 )
		{
			System.err.println( "Usage: TestDriver [configurationFile]" );
			System.exit( -1 );
		}
		
		File configFile = new File( args[ 0 ] );
		if ( !configFile.exists() )
		{
			System.err.println( "[" + configFile.getAbsolutePath() + "] could not be located" );
			System.exit( -1 );
		}
		
		Properties configProperties = new Properties();
		try
		{
			DeviceActionManager.instance().setDeviceActionFactory( new PerfectoDeviceActionFactory() );
			configProperties.load( new FileInputStream( configFile ) );

			System.out.println( "Reading Cloud Configuration" );
			configureCloudRegistry( configProperties );
			
			if ( CloudRegistry.instance().getCloud().getProxyHost() != null && !CloudRegistry.instance().getCloud().getProxyHost().isEmpty() && Integer.parseInt( CloudRegistry.instance().getCloud().getProxyPort() ) > 0 )
			{
			    System.setProperty( "http.proxyHost", CloudRegistry.instance().getCloud().getProxyHost() );
			    System.setProperty( "https.proxyHost", CloudRegistry.instance().getCloud().getProxyHost() );
			    System.setProperty( "http.proxyPort", CloudRegistry.instance().getCloud().getProxyPort() );
			    System.setProperty( "https.proxyPort", CloudRegistry.instance().getCloud().getProxyPort() );
			}
			
			System.out.println( "Reading Application Configuration" );
			configureApplicationRegistry( configProperties );
			
			configureThirdParty( configProperties );
			
			System.out.println( "Reading Artifact Configuration" );
			configureArtifacts( configProperties );
			
			System.out.println( "Reading Page Configuration" );
			configurePageManagement( configProperties );
			
			System.out.println( "Reading Device Configuration" );
			configureDeviceManagement( configProperties );
			
			System.out.println( "Configuring Driver" );
			validateProperties( configProperties, DRIVER );
			
			String propertyAdapters = configProperties.getProperty( "driver.propertyAdapters" );
			if ( propertyAdapters != null && !propertyAdapters.isEmpty() )
			{
			    String[] adapterList = propertyAdapters.split( "," );
			    for ( String adapterName : adapterList )
			    {
			        try
			        {
			            DeviceManager.instance().registerPropertyAdapter( (PropertyAdapter) Class.forName( adapterName ).newInstance() ); 
			        }
			        catch( Exception e )
			        {
			            System.err.println( "Property Adapter [" + adapterName + "] coudl not be created" );
			            System.exit( -1 );
			        }
			    }
			}
			
			DeviceManager.instance().setConfigurationProperties( configProperties );
			DeviceManager.instance().notifyPropertyAdapter( configProperties );
			
			GestureManager.instance().setGestureFactory( new PerfectoGestureFactory() );
			
			String personaNames = configProperties.getProperty( "driver.personas" );
			if ( personaNames != null && !personaNames.isEmpty() )
			{
				DataManager.instance().setPersonas( personaNames );
				PageManager.instance().setWindTunnelEnabled( true );
			}
			
			DeviceManager.instance().setCachingEnabled( Boolean.parseBoolean( configProperties.getProperty( "driver.enableCaching" ) ) );
			
			String interruptString = configProperties.getProperty( "driver.deviceInterrupts" );
			if ( interruptString != null && !interruptString.isEmpty() )
			    DeviceManager.instance().setDeviceInterrupts( interruptString );
			
			switch( configProperties.getProperty( DRIVER[ 0 ] ).toUpperCase() )
			{
				case "XML":
					KeyWordDriver.instance().loadTests( new XMLKeyWordProvider( new File( configProperties.getProperty( DRIVER[ 1 ] ) ) ) );
					
					List<String> testArray = new ArrayList<String>( 10 );
					
					//
					// Extract any named tests
					//
					String testNames = configProperties.getProperty( "driver.testNames" );
					if ( testNames != null && !testNames.isEmpty() )
					    testArray.addAll( Arrays.asList( testNames ) );
					
	                //
                    // Extract any tagged tests
                    //
					String tagNames = configProperties.getProperty( "driver.tagNames" );
                    if ( tagNames != null && !tagNames.isEmpty() )
                    {
                        Collection<KeyWordTest> testList = KeyWordDriver.instance().getTaggedTests( tagNames.split( "," ) );
                        
                        if ( testList.isEmpty() )
                        {
                            System.err.println( "No tests contianed the tag(s) [" + tagNames + "]" );
                            System.exit( -1 );
                        }
                        
                        for ( KeyWordTest t : testList )
                            testArray.add( t.getName() );
                    }
                    
                    if ( testArray.size() == 0 )
						DataManager.instance().setTests( KeyWordDriver.instance().getTestNames() );
                    else
                        DataManager.instance().setTests( testArray.toArray( new String[ 0 ] ) );
                    
					break;
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		boolean dryRun = false;
		String validateConfiguration = configProperties.getProperty( "driver.validateConfiguration" );
		if ( validateConfiguration != null )
			dryRun = Boolean.parseBoolean( validateConfiguration );
		
		DeviceManager.instance().setDryRun( dryRun );
		
		if ( dryRun )
			System.out.println( "Performing Dry Run" );
		else
			System.out.println( "Executing Test Cases" );
		
		switch( configProperties.getProperty( DRIVER[ 0 ] ).toUpperCase() )
		{
			case "XML":
			    
			    RunDetails.instance().setStartTime();
			    
				TestNG testNg = new TestNG( true );
				testNg.setOutputDirectory( configProperties.getProperty( ARTIFACT[ 0 ] ) + System.getProperty( "file.separator" ) + "testNg" );
				testNg.setTestClasses( new Class[] { XMLTestDriver.class } );
				testNg.run();
				
				if ( testNg.hasFailure() )
					System.exit( -1 );
				else
					System.exit( 0 );
		}

		
		
	}
	
	private static void configureCloudRegistry( Properties configProperties )
	{
		validateProperties( configProperties, CLOUD );
		
		switch( ( configProperties.getProperty( CLOUD[ 0 ] ) ).toUpperCase() )
		{
			case "XML":
				CloudRegistry.instance().setCloudProvider( new XMLCloudProvider( new File( configProperties.getProperty( CLOUD[ 1 ] ) ) ) );
				break;
				
			case "CSV":
				CloudRegistry.instance().setCloudProvider( new CSVCloudProvider( new File( configProperties.getProperty( CLOUD[ 1 ] ) ) ) );
				break;
				
			case "EXCEL":
				validateProperties( configProperties, new String[] { "cloudRegistry.tabName" } );
				CloudRegistry.instance().setCloudProvider( new ExcelCloudProvider( new File( configProperties.getProperty( CLOUD[ 1 ] ) ), configProperties.getProperty( "cloudRegistry.tabName" ) ) );
				break;
		}
		
		CloudRegistry.instance().setCloud( configProperties.getProperty( CLOUD[ 2 ] ) );
		
		BeanManager.instance().setBeanFactory( new XMLBeanFactory() );
		PerfectoMobile.instance().setUserName( CloudRegistry.instance().getCloud().getUserName() );
		PerfectoMobile.instance().setPassword( CloudRegistry.instance().getCloud().getPassword() );
		PerfectoMobile.instance().setBaseUrl( "https://" + CloudRegistry.instance().getCloud().getHostName() );
		
	}
	
	private static void configureThirdParty( Properties configProperties )
	{
		String thirdParty = configProperties.getProperty( "integrations.import" );
		if ( thirdParty != null && !thirdParty.isEmpty() )
		{
			String[] partyArray = thirdParty.split( "," );
			for ( String party : partyArray )
			{
				String className = configProperties.getProperty( party + ".initialization" );
				try
				{
					System.out.println( "Configuring Third Party support for " + party + " as " + className );
					
					Initializable newExtension = (Initializable) Class.forName( className ).newInstance();
					newExtension.initialize( party, configProperties );
					
				}
				catch( Exception e )
				{
					e.printStackTrace();
				}
			}
		}
	}
	
	private static void configureApplicationRegistry( Properties configProperties )
	{
		validateProperties( configProperties, APP );
		
		switch( ( configProperties.getProperty( APP[ 0 ] ) ).toUpperCase() )
		{
			case "XML":
				ApplicationRegistry.instance().setApplicationProvider( new XMLApplicationProvider( new File( configProperties.getProperty( APP[ 1 ] ) ) ) );
				break;
				
			case "CSV":
				ApplicationRegistry.instance().setApplicationProvider( new CSVApplicationProvider( new File( configProperties.getProperty( APP[ 1 ] ) ) ) );
				break;
				
			case "EXCEL":
				validateProperties( configProperties, new String[] { "applicationRegistry.tabName" } );
				ApplicationRegistry.instance().setApplicationProvider( new ExcelApplicationProvider( new File( configProperties.getProperty( APP[ 1 ] ) ), configProperties.getProperty( "applicationRegistry.tabName" ) ) );
				break;
		}
		
		ApplicationRegistry.instance().setAUT( configProperties.getProperty( APP[ 2 ] ) );
	}
	
	private static void configureArtifacts( Properties configProperties )
	{
		validateProperties( configProperties, ARTIFACT );

		
		DataManager.instance().setReportFolder( new File( configProperties.getProperty( ARTIFACT[ 0 ] ) ) );
		String storeImages = configProperties.getProperty( "artifactProducer.storeImages" );
		if ( storeImages != null && !storeImages.isEmpty() )
			PageManager.instance().setStoreImages( Boolean.parseBoolean( storeImages ) );
		
		String imageLocation = configProperties.getProperty( "artifactProducer.imageLocation" );
		if ( imageLocation != null && !imageLocation.isEmpty() )
			PageManager.instance().setImageLocation( imageLocation );
		
		String automated = configProperties.getProperty( "artifactProducer.automated" );
		if ( automated != null && !automated.isEmpty() )
		{
			String[] auto = automated.split( "," );
			List<ArtifactType> artifactList = new ArrayList<ArtifactType>( 10 );
			
			for ( String type : auto )
			{
				try
				{
					artifactList.add(  ArtifactType.valueOf( type ) );
					if ( ArtifactType.valueOf( type ).equals( ArtifactType.CONSOLE_LOG ) )
					{
					    String logLevel = configProperties.getProperty( "artifactProducer.logLevel" );
					    if ( logLevel == null )
					        logLevel = "INFO";
					    ThreadedFileHandler threadedHandler = new ThreadedFileHandler();
					    threadedHandler.configureHandler( Level.parse( logLevel.toUpperCase() ) );
					}
				}
				catch( Exception e )
				{
					System.out.println( "No Artifact Type exists as " + type );
				}
			}
			
			DataManager.instance().setAutomaticDownloads( artifactList.toArray( new ArtifactType[ 0 ] ) );
			
		}
	}
	
	private static void configurePageManagement( Properties configProperties )
	{
		validateProperties( configProperties, PAGE );
		
		PageManager.instance().setSiteName( configProperties.getProperty( PAGE[ 0 ] ) );
		
		switch( ( configProperties.getProperty( PAGE[ 1 ] ) ).toUpperCase() )
		{
			case "XML":
				PageManager.instance().setElementProvider( new XMLElementProvider( new File( configProperties.getProperty( PAGE[ 2 ] ) ) ) );
				break;
				
			case "CSV":
				PageManager.instance().setElementProvider( new CSVElementProvider( new File( configProperties.getProperty( PAGE[ 2 ] ) ) ) );
				break;
				
			case "EXCEL":
				PageManager.instance().setElementProvider( new ExcelElementProvider( new File( configProperties.getProperty( PAGE[ 2 ] ) ), configProperties.getProperty( PAGE[ 0 ] ) ) );
				break;
		}
		

		String data = configProperties.getProperty( DATA[ 0 ] );
		
		if ( data != null && !data.isEmpty() )
		{
			validateProperties( configProperties, DATA );
			
			switch( ( configProperties.getProperty( DATA[ 0 ] ) ).toUpperCase() )
			{
				case "XML":
					PageDataManager.instance().setPageDataProvider( new XMLPageDataProvider( new File( configProperties.getProperty( DATA[ 1 ] ) ) ) );
					break;
				
				case "EXCEL":
					validateProperties( configProperties, new String[] { "pageManagement.pageData.tabNames" } );
					PageDataManager.instance().setPageDataProvider( new ExcelPageDataProvider( new File( configProperties.getProperty( DATA[ 1 ] ) ), configProperties.getProperty( "pageManagement.pageData.tabNames" ) ) );
					break;
					
			}
		}
		
		String content = configProperties.getProperty( CONTENT[ 0 ] );
		if ( content != null && !content.isEmpty() )
		{
			validateProperties( configProperties, CONTENT );
			
			switch( ( configProperties.getProperty( CONTENT[ 0 ] ) ).toUpperCase() )
			{
				case "XML":
					ContentManager.instance().setContentProvider( new XMLContentProvider( new File( configProperties.getProperty( CONTENT[ 1 ] ) ) ) );
					break;
				
				case "EXCEL":
					validateProperties( configProperties, new String[] { "pageManagement.content.tabName", "pageManagement.content.keyColumn", "pageManagement.content.lookupColumns" } );
					
					int keyColumn = Integer.parseInt( configProperties.getProperty( "pageManagement.content.keyColumn" ) );
					String[] lookupString = configProperties.getProperty( "pageManagement.content.lookupColumns" ).split( "," );
					
					int[] lookupColumns = new int[ lookupString.length ];
					for ( int i=0; i<lookupString.length; i++ )
						lookupColumns[ i ] = Integer.parseInt( lookupString[ i ].trim() );
					
					ContentManager.instance().setContentProvider( new ExcelContentProvider( new File( configProperties.getProperty( CONTENT[ 1 ] ) ), configProperties.getProperty( "pageManagement.content.tabName" ), keyColumn, lookupColumns ) );
					break;
					
			}
		}

                //
                // add in support for multiple devices
                //

                PageManager.instance().setAlternateWebDriverSource( new SeleniumSessionManager()
                    {
                        public WebDriver getAltWebDriver( String name )
                        {
                            WebDriver rtn = null;
                            
                            ConnectedDevice device = AbstractSeleniumTest.getConnectedDevice( name );

                            if ( device != null )
                            {
                                rtn = device.getWebDriver();
                            }

                            return rtn;
                        }

                        public void registerAltWebDriver( String name )
                        {
                            AbstractSeleniumTest.registerSecondaryDeviceOnName( (String) name );
                        }

                    } );
	}
	
	private static void configureDeviceManagement( Properties configProperties )
	{
		validateProperties( configProperties, DEVICE );
		
		switch( ( configProperties.getProperty( DEVICE[ 0 ] ) ).toUpperCase() )
		{
			case "RESERVED":
				DataManager.instance().readData( new PerfectoMobileDataProvider( new ReservedHandsetValidator(), DriverType.valueOf( configProperties.getProperty( DEVICE[ 1 ] ) ) ) );
				break;
			
			case "AVAILABLE":
				DataManager.instance().readData( new PerfectoMobileDataProvider( new AvailableHandsetValidator(), DriverType.valueOf( configProperties.getProperty( DEVICE[ 1 ] ) ) ) );
				break;
				
			case "CSV":
				String fileName = configProperties.getProperty( "deviceManagement.fileName" );
				if ( fileName == null )
				{
					System.err.println( "******* Property [deviceManagement.fileName] was not specified" );
					System.exit( -1 );
				}
				DataManager.instance().readData( new CSVDataProvider( new File( fileName ), DriverType.valueOf( configProperties.getProperty( DEVICE[ 1 ] ) ) ) );
				break;
				
			case "XML":
				String xmlFileName = configProperties.getProperty( "deviceManagement.fileName" );
				if ( xmlFileName == null )
				{
					System.err.println( "******* Property [deviceManagement.fileName] was not specified" );
					System.exit( -1 );
				}
				DataManager.instance().readData( new XMLDataProvider( new File( xmlFileName ), DriverType.valueOf( configProperties.getProperty( DEVICE[ 1 ] ) ) ) );
				break;
				
			case "EXCEL":
				validateProperties( configProperties, new String[] { "deviceManagement.tabName", "deviceManagement.fileName" } );
				String excelFile = configProperties.getProperty( "deviceManagement.fileName" );

				DataManager.instance().readData( new ExcelDataProvider( new File( excelFile ), configProperties.getProperty( "deviceManagement.tabName" ), DriverType.valueOf( configProperties.getProperty( DEVICE[ 1 ] ) ) ) );
				break;
				
			case "NAMED":
				String deviceList = configProperties.getProperty( "deviceManagement.deviceList" );
				if ( deviceList == null )
				{
					System.err.println( "******* Property [deviceManagement.deviceList] was not specified" );
					System.exit( -1 );
				}
				DataManager.instance().readData( new NamedDataProvider( deviceList, DriverType.valueOf( configProperties.getProperty( DEVICE[ 1 ] ) ) ) );
				break;
				
			default:
				System.err.println( "Unknown Device Data Provider [" + ( configProperties.getProperty( DEVICE[ 0 ] ) ).toUpperCase() + "]" );
				System.exit( -1 );
		}
		
		String executionReport = configProperties.getProperty( "deviceManagement.executionLog.fileName" );
		if ( executionReport != null )
			DeviceManager.instance().addRunListener( new CSVRunListener( new File( executionReport ) ) );
		DeviceManager.instance().addRunListener( RunDetails.instance() );
	}
	
	private static boolean validateProperties( Properties configProperties, String[] propertyNames )
	{
		for ( String name : propertyNames )
		{
			String value = configProperties.getProperty( name );
			
			if ( value == null || value.isEmpty() )
			{
				System.err.println( "******* Property [" + name + "] was not specified" );
				System.exit( -1 );
			}
		}
		
		return true;
	}
}
