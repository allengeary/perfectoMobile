/*
 * 
 */
package com.perfectoMobile.device.artifact.api;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.morelandLabs.artifact.ArtifactType;
import com.morelandLabs.page.ExecutionRecord;
import com.morelandLabs.page.StepStatus;
import com.morelandLabs.spi.Device;
import com.morelandLabs.spi.RunDetails;
import com.morelandLabs.utility.XMLEscape;
//import com.morelandLabs.utility.XMLEscape;
import com.morelandLabs.wcag.WCAGRecord;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.artifact.AbstractArtifactProducer;
import com.perfectoMobile.device.artifact.Artifact;
import com.perfectoMobile.device.cloud.CloudRegistry;
import com.perfectoMobile.device.data.DataManager;
import com.perfectoMobile.device.factory.DeviceWebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class PerfectoArtifactProducer.
 */
public class PerfectoArtifactProducer extends AbstractArtifactProducer
{
    
	/** The Constant REPORT_KEY. */
	private static final String REPORT_KEY = "REPORT_KEY";
	
	/** The Constant WIND_TUNNEL. */
	private static final String WIND_TUNNEL = "WIND_TUNNEL";
	
	/** The Constant FORMAT. */
	private static final String FORMAT = "format";
	
	/** The Constant DEFAULT_FORMAT. */
	private static final String DEFAULT_FORMAT = "pdf";
	

	/**
	 * Instantiates a new perfecto artifact producer.
	 */
	public PerfectoArtifactProducer()
	{
		// TODO Auto-generated constructor stub
	}

	/**
	 * Instantiates a new perfecto artifact producer.
	 *
	 * @param reportFormat the report format
	 */
	public PerfectoArtifactProducer( String reportFormat )
	{
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.AbstractArtifactProducer#_getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, com.perfectoMobile.device.ConnectedDevice)
	 */
	@Override
	protected Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice, String testName )
	{
		return null;
	}
	
	private Artifact generateExecutionReport( String operation, Map<String,String> parameterMap, String reportFormat, String rootFolder, ArtifactType aType)
	{
	    try
        {
            StringBuilder urlBuilder = new StringBuilder();
            urlBuilder.append( "https://" ).append( CloudRegistry.instance().getCloud().getHostName() ).append( "/services/reports/" ).append( parameterMap.get( REPORT_KEY ) );
            urlBuilder.append( "?operation=" ).append( operation ).append( "&user=" ).append( CloudRegistry.instance().getCloud().getUserName() ).append( "&password=" ).append( CloudRegistry.instance().getCloud().getPassword() );

            String format = parameterMap.get( FORMAT );
            if (format == null)
            {
                if (reportFormat == null)
                    format = DEFAULT_FORMAT;
                else
                    format = reportFormat;
            }

            urlBuilder.append( "&format=" ).append( format );

            return new Artifact( rootFolder + aType + "." + format, getUrl( new URL( urlBuilder.toString() ) ) );
        }
        catch (Exception e)
        {
            log.error( "Error download artifact data", e );
            return null;
        }
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.AbstractArtifactProducer#_getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, java.util.Map, com.perfectoMobile.device.ConnectedDevice)
	 */
	@Override
	protected Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, Map<String, String> parameterMap, ConnectedDevice connectedDevice, String testName )
	{
	    String rootFolder = testName + System.getProperty( "file.separator" ) + connectedDevice.getDevice().getKey() + System.getProperty( "file.separator" );
		switch (aType)
		{
			case EXECUTION_REPORT:
			case EXECUTION_REPORT_PDF:
			    return generateExecutionReport( "download", parameterMap, "pdf", rootFolder, aType );

			case FAILURE_SOURCE:
			    return new Artifact( rootFolder + "failureDOM.xml", webDriver.getPageSource() != null ? XMLEscape.toXML( webDriver.getPageSource() ).getBytes() : null );

			case CONSOLE_LOG:
			    Artifact consoleArtifact = new Artifact( rootFolder + "console.txt", DeviceManager.instance().getLog().getBytes() );
			    DeviceManager.instance().clearLog();
			    return consoleArtifact;
				
			case DEVICE_LOG:
				try
				{
					StringBuilder urlBuilder = new StringBuilder();
					urlBuilder.append( "https://" ).append( CloudRegistry.instance().getCloud().getHostName() ).append( "/services/executions/" ).append( DeviceManager.instance().getExecutionId() );
					urlBuilder.append( "?operation=command&user=" ).append( CloudRegistry.instance().getCloud().getUserName() ).append( "&password=" ).append( CloudRegistry.instance().getCloud().getPassword() );
					urlBuilder.append( "&command=device&subcommand=log&responseFormat=xml&param.tail=1000&param.handsetId=" ).append( connectedDevice.getDevice().getDeviceName() );
					return new Artifact( rootFolder + "deviceLog.xml", getUrl( new URL( urlBuilder.toString() ) ) );
				}
				catch( Exception e )
				{
					log.error( "Error download device log data", e );
				}
				return null;
				
			case EXECUTION_REPORT_CSV:
			    return generateExecutionReport( "download", parameterMap, "csv", rootFolder, aType );
				
			case EXECUTION_REPORT_HTML:
			    return generateExecutionReport( "download", parameterMap, "html", rootFolder, aType );
			
			case EXECUTION_REPORT_XML:
			    return generateExecutionReport( "download", parameterMap, "xml", rootFolder, aType );
				
			case EXECUTION_RECORD_CSV:
			    return generateCSVRecord( connectedDevice.getDevice(), testName, rootFolder );
			    
			case EXECUTION_RECORD_HTML:
                return generateHTMLRecord( connectedDevice.getDevice(), testName, rootFolder, webDriver );
				
			case WCAG_REPORT:
			    return generateWCAG( connectedDevice.getDevice(), testName, rootFolder );
				
			default:
				return null;

		}

		
	}

	/**
	 * Gets the url.
	 *
	 * @param currentUrl the current url
	 * @return the url
	 */
	public byte[] getUrl( URL currentUrl )
	{
		if (log.isDebugEnabled())
			log.debug( "Executing " + currentUrl.toString() );
		InputStream inputStream = null;
		try
		{
			ByteArrayOutputStream resultBuilder = new ByteArrayOutputStream();

			HttpURLConnection y = ( HttpURLConnection ) currentUrl.openConnection();

			inputStream = y.getInputStream();
			byte[] buffer = new byte[1024];
			int bytesRead = 0;

			while (( bytesRead = inputStream.read( buffer ) ) > 0)
				resultBuilder.write( buffer, 0, bytesRead );

			return resultBuilder.toByteArray();
		}
		catch (Exception e)
		{
			log.error( "Error performing GET request", e );
			return null;
		}
		finally
		{
			try
			{
				inputStream.close();
			}
			catch (Exception e)
			{
			}
		}

	}
}
