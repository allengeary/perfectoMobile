/*
 * 
 */
package com.perfectoMobile.device.factory;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.ContextAware;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.ExecuteMethod;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.morelandLabs.spi.Device;
import com.morelandLabs.spi.PropertyProvider;
import com.morelandLabs.spi.driver.DeviceProvider;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.artifact.Artifact;
import com.perfectoMobile.device.artifact.ArtifactProducer;
import com.perfectoMobile.device.factory.spi.PerfectoWebDriver;

import io.appium.java_client.AppiumDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class DeviceWebDriver.
 */
public class DeviceWebDriver implements WebDriver, ContextAware, ExecuteMethod, ArtifactProducer, NativeDriverProvider, PropertyProvider, TakesScreenshot, DeviceProvider
{
	
	/** The web driver. */
	protected WebDriver webDriver;
	
	/** The execution id. */
	private String executionId;
	
	/** The report key. */
	private String reportKey;
	
	/** The wind tunnel report. */
	private String windTunnelReport;
	
	/** The device name. */
	private String deviceName;
	
	/** The current device. */
	private Device currentDevice;
	
	/** The current context. */
	private String currentContext;
	
	/** The context handles. */
	private Set<String> contextHandles;
	
	/** The artifact producer. */
	private ArtifactProducer artifactProducer;
	
	/** The log. */
	protected Log log = LogFactory.getLog( DeviceWebDriver.class );
	
	/** The caching enabled. */
	private boolean cachingEnabled = true;
	
	/** The x path factory. */
	private XPathFactory xPathFactory = XPathFactory.newInstance();
	
	/** The cached document. */
	private Document cachedDocument = null;
	
	/** The context switch supported. */
	private boolean contextSwitchSupported = true;
	
	/** The Constant EXECUTION_ID. */
	private static final String EXECUTION_ID = "EXECUTION_ID";
	
	/** The Constant REPORT_KEY. */
	private static final String REPORT_KEY = "REPORT_KEY";
	
	/** The Constant DEVICE_NAME. */
	private static final String DEVICE_NAME = "DEVICE_NAME";
	
	/** The Constant WIND_TUNNEL. */
	private static final String WIND_TUNNEL = "WIND_TUNNEL";
	
	/* (non-Javadoc)
	 * @see com.morelandLabs.spi.driver.DeviceProvider#getDevice()
	 */
	@Override
	public Device getDevice()
	{
		return currentDevice;
	}
	
	/**
	 * Cache data.
	 */
	public void cacheData()
	{
		if ( !cachingEnabled )
			return;
		
		if (log.isDebugEnabled())
			log.debug( "Caching page data" );
		String pageSource = webDriver.getPageSource();

		try
		{
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			cachedDocument = dBuilder.parse( new ByteArrayInputStream( pageSource.getBytes() ) );
			cachingEnabled = true;
		}
		catch (Exception e)
		{
			log.warn( "CACHING HAS BEEN DISABLED" );
			cachingEnabled = false;
			cachedDocument = null;
		}
	}

	/**
	 * Instantiates a new device web driver.
	 *
	 * @param webDriver the web driver
	 * @param cachingEnabled the caching enabled
	 * @param currentDevice the current device
	 */
	public DeviceWebDriver( WebDriver webDriver, boolean cachingEnabled, Device currentDevice )
	{
		this.webDriver = webDriver;
		this.cachingEnabled = cachingEnabled;
		this.currentDevice = currentDevice;
	}
	
	/**
	 * Sets the artifact producer.
	 *
	 * @param artifactProducer the new artifact producer
	 */
	public void setArtifactProducer( ArtifactProducer artifactProducer )
	{
		this.artifactProducer = artifactProducer;
	}
	
	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId()
	{
		return executionId;
	}

	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId( String executionId )
	{
		this.executionId = executionId;
		DeviceManager.instance().setExecutionId( executionId );
		
		if ( log.isInfoEnabled() )
			log.info( "Execution ID recorded as [" + executionId + "]" );
	}

	/**
	 * Gets the report key.
	 *
	 * @return the report key
	 */
	public String getReportKey()
	{
		return reportKey;
	}

	/**
	 * Sets the report key.
	 *
	 * @param reportKey the new report key
	 */
	public void setReportKey( String reportKey )
	{
		this.reportKey = reportKey;
	}
	
	

	/**
	 * Gets the wind tunnel report.
	 *
	 * @return the wind tunnel report
	 */
	public String getWindTunnelReport() 
	{
		return windTunnelReport;
	}

	/**
	 * Sets the wind tunnel report.
	 *
	 * @param windTunnelReport the new wind tunnel report
	 */
	public void setWindTunnelReport(String windTunnelReport) 
	{
		this.windTunnelReport = windTunnelReport;
	}

	/**
	 * Gets the device name.
	 *
	 * @return the device name
	 */
	public String getDeviceName()
	{
		return deviceName;
	}

	/**
	 * Sets the device name.
	 *
	 * @param deviceName the new device name
	 */
	public void setDeviceName( String deviceName )
	{
		this.deviceName = deviceName;
	}

	
	
	/**
	 * Gets the web driver.
	 *
	 * @return the web driver
	 */
	public WebDriver getWebDriver()
	{
		return webDriver;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.factory.NativeDriverProvider#getNativeDriver()
	 */
	@Override
	public WebDriver getNativeDriver()
	{
		return webDriver;
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#get(java.lang.String)
	 */
	public void get( String url )
	{
		webDriver.get( url );
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getCurrentUrl()
	 */
	public String getCurrentUrl()
	{
		return webDriver.getCurrentUrl();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getTitle()
	 */
	public String getTitle()
	{
		return webDriver.getTitle();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElements(org.openqa.selenium.By)
	 */
	public List<WebElement> findElements( By by )
	{
		if ( cachingEnabled && cachedDocument == null )
			cacheData();
		
		if (cachingEnabled && cachedDocument != null)
		{
			try
			{
				XPath xPath = xPathFactory.newXPath();
				String path = by.toString();
				path = path.substring( path.indexOf( ": " ) + 2 );
				System.out.println( path );
				NodeList nodes = ( NodeList ) xPath.evaluate( path, cachedDocument, XPathConstants.NODESET );

				List<WebElement> elementList = new ArrayList<WebElement>( 10 );

				for (int i = 0; i < nodes.getLength(); i++)
					elementList.add( new CachedWebElement( webDriver, by, nodes.item( i ) ) );

				return elementList;
			}
			catch (Exception e)
			{
				log.warn( "Error reading from cache " + e.getMessage() );
				cachingEnabled = false;
				cachedDocument = null;
			}
		}
		
		
		return webDriver.findElements( by );
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#findElement(org.openqa.selenium.By)
	 */
	public WebElement findElement( By by )
	{
		if ( cachingEnabled && cachedDocument == null )
			cacheData();
		
		if (cachingEnabled && cachedDocument != null)
		{
			try
			{
				XPath xPath = xPathFactory.newXPath();
				String path = by.toString();
				path = path.substring( path.indexOf( ": " ) + 2 );
				Node node = ( Node ) xPath.evaluate( path, cachedDocument, XPathConstants.NODE );

				return new CachedWebElement( webDriver, by, node );
			}
			catch (Exception e)
			{
				log.warn( "Error reading from cache " + e.getMessage() );
				cachingEnabled = false;
				cachedDocument = null;
			}
		}
		return webDriver.findElement( by );
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getPageSource()
	 */
	public String getPageSource()
	{
		return webDriver.getPageSource();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#close()
	 */
	public void close()
	{
		if ( webDriver != null && webDriver instanceof AppiumDriver )
			try { ( (AppiumDriver) webDriver ).closeApp(); } catch( Exception e ) {}
		webDriver.close();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#quit()
	 */
	public void quit()
	{
		webDriver.quit();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandles()
	 */
	public Set<String> getWindowHandles()
	{
		return webDriver.getWindowHandles();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#getWindowHandle()
	 */
	public String getWindowHandle()
	{
		return webDriver.getWindowHandle();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#switchTo()
	 */
	public TargetLocator switchTo()
	{
		return webDriver.switchTo();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#navigate()
	 */
	public Navigation navigate()
	{
		return webDriver.navigate();
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.WebDriver#manage()
	 */
	public Options manage()
	{
		return webDriver.manage();
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.ContextAware#context(java.lang.String)
	 */
	public WebDriver context( String newContext )
	{
		if ( !contextSwitchSupported )
			return webDriver;
		
		if ( newContext == null || newContext.equals( currentContext ) )
			return webDriver;
		
		if ( webDriver != null )
		{
			if ( webDriver instanceof PerfectoWebDriver )
				return null;
			else if ( webDriver instanceof RemoteWebDriver )
			{
				RemoteExecuteMethod executeMethod = new RemoteExecuteMethod( (RemoteWebDriver) webDriver );
		        Map<String,String> params = new HashMap<String,String>( 5 );
		        params.put("name", newContext );
		        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
			}
			else if ( webDriver instanceof AppiumDriver )
			{
				( (AppiumDriver) webDriver ).context( newContext );
			}
			
			
			if ( newContext.equals( _getContext() ) )
				currentContext = newContext;
			else
				throw new IllegalStateException( "Could not change context to " + newContext + " against " + webDriver );
		}
		
		return webDriver;
	}
	
	/* (non-Javadoc)
	 * @see org.openqa.selenium.ContextAware#getContext()
	 */
	public String getContext()
	{
		if ( currentContext != null )
			return currentContext;
		
		currentContext = _getContext();

		return currentContext;
	}
	
	/**
	 * _get context.
	 *
	 * @return the string
	 */
	private String _getContext()
	{
		if ( webDriver != null )
		{
			try
			{
				if ( webDriver instanceof RemoteWebDriver )
				{
					RemoteExecuteMethod executeMethod = new RemoteExecuteMethod( (RemoteWebDriver) webDriver );
					return (String) executeMethod.execute( DriverCommand.GET_CURRENT_CONTEXT_HANDLE, null );
				}
				else if ( webDriver instanceof AppiumDriver )
				{
					return ( (AppiumDriver) webDriver ).getContext();
				}
			}
			catch( Exception e )
			{
				log.warn( "Context Switches are not supported - " + e.getMessage() );
				contextSwitchSupported = false;
			}
		}
		
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.ContextAware#getContextHandles()
	 */
	public Set<String> getContextHandles()
	{
		if ( contextHandles != null )
			return contextHandles;
		
		RemoteExecuteMethod executeMethod = new RemoteExecuteMethod( (RemoteWebDriver) webDriver );
		List<String> handleList = (List<String>) executeMethod.execute( DriverCommand.GET_CONTEXT_HANDLES, null );
		
		contextHandles = new HashSet<String>( 10 );
		contextHandles.addAll( handleList );
		return contextHandles;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.remote.ExecuteMethod#execute(java.lang.String, java.util.Map)
	 */
	public Object execute( String commandName, Map<String, ?> parameters )
	{
		if ( webDriver instanceof RemoteWebDriver )
		{
			//RemoteExecuteMethod executeMethod = new RemoteExecuteMethod( (RemoteWebDriver) webDriver );
			return ( ( RemoteWebDriver ) webDriver ).executeScript(commandName, parameters );
			//return executeMethod.execute( commandName, parameters );
		}
		else
			throw new IllegalArgumentException( "Attempting to execution a remote command on an unsupported driver" );
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.ArtifactProducer#getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, com.perfectoMobile.device.ConnectedDevice)
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice )
	{
		if ( artifactProducer != null )
		{
			Map<String,String> parameterMap = new HashMap<String,String>( 3 );
			parameterMap.put( EXECUTION_ID, executionId );
			parameterMap.put( REPORT_KEY, reportKey );
			parameterMap.put( DEVICE_NAME, deviceName );
			parameterMap.put( WIND_TUNNEL, windTunnelReport );
			return artifactProducer.getArtifact( webDriver, aType, parameterMap, connectedDevice );
		}
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.ArtifactProducer#getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, java.util.Map, com.perfectoMobile.device.ConnectedDevice)
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, Map<String, String> parameterMap, ConnectedDevice connectedDevice )
	{
		if ( artifactProducer != null )
		{
			if ( parameterMap == null )
				parameterMap = new HashMap<String,String>( 3 );
			
			parameterMap.put( EXECUTION_ID, executionId );
			parameterMap.put( REPORT_KEY, reportKey );
			parameterMap.put( DEVICE_NAME, deviceName );
			parameterMap.put( WIND_TUNNEL, windTunnelReport );
			
			return artifactProducer.getArtifact( webDriver, aType, parameterMap, connectedDevice );
		}
		else
			return null;
	}

	/* (non-Javadoc)
	 * @see com.morelandLabs.spi.PropertyProvider#getProperty(java.lang.String)
	 */
	@Override
	public String getProperty( String propertyName )
	{
		switch( propertyName )
		{
			case EXECUTION_ID:
				return executionId;
				
			case REPORT_KEY:
				return reportKey;
				
			case DEVICE_NAME:
				return deviceName;
				
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see org.openqa.selenium.TakesScreenshot#getScreenshotAs(org.openqa.selenium.OutputType)
	 */
	@Override
	public <X> X getScreenshotAs( OutputType<X> target ) throws WebDriverException
	{
		if ( webDriver instanceof TakesScreenshot )
			return ( (TakesScreenshot) webDriver ).getScreenshotAs( target );
		else
			throw new IllegalArgumentException( "Screenshot functionality not supported" );
	}
	
	
	

}
