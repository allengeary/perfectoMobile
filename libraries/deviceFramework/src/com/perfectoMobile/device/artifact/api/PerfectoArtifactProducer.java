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
import com.morelandLabs.spi.Device;
import com.morelandLabs.wcag.WCAGRecord;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.artifact.AbstractArtifactProducer;
import com.perfectoMobile.device.artifact.Artifact;
import com.perfectoMobile.device.cloud.CloudRegistry;

// TODO: Auto-generated Javadoc
/**
 * The Class PerfectoArtifactProducer.
 */
public class PerfectoArtifactProducer extends AbstractArtifactProducer
{
    private static DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss.SSS");
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

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.AbstractArtifactProducer#_getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, java.util.Map, com.perfectoMobile.device.ConnectedDevice)
	 */
	@Override
	protected Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, Map<String, String> parameterMap, ConnectedDevice connectedDevice, String testName )
	{
	    String rootFolder = testName + System.getProperty( "file.separator" ) + connectedDevice.getDevice().getKey() + System.getProperty( "file.separator" );
	    
		String reportFormat = null;
		String operation = null;
		switch (aType)
		{
			case EXECUTION_REPORT:
			case EXECUTION_REPORT_PDF:
				operation = "download";
				reportFormat = "pdf";
				break;

			case EXECUTION_VIDEO:
				operation = "video";
				break;

			case FAILURE_SOURCE:
				return new Artifact( rootFolder + "failureDOM.xml", webDriver.getPageSource() != null ? webDriver.getPageSource().getBytes() : null );

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
				operation = "download";
				reportFormat = "csv";
				break;
				
			case EXECUTION_REPORT_HTML:
				operation = "download";
				reportFormat = "html";
				break;
			
			case EXECUTION_REPORT_XML:
				operation = "download";
				reportFormat = "xml";
				break;
				
			case WCAG_REPORT:
			    StringBuilder wcagBuffer = new StringBuilder();
			    Device device = connectedDevice.getDevice();
			    
			    wcagBuffer.append( "<html><body><table><tr><td align='right'><b>Device Name:</b></td><td>" ).append( device.getManufacturer() ).append( " " ).append( device.getModel() ).append( " (" ).append( device.getKey() ).append(")</td></tr>");
			    wcagBuffer.append( "<tr><td align='right'><b>OS:</b></td><td>" ).append( device.getOs() ).append( " (" ).append( device.getOsVersion() ).append(")</td></tr>");
			    wcagBuffer.append( "<tr><td align='right'><b>Test Name:</b></td><td>" ).append( testName ).append("</td></tr><tr><td align='center' colspan='2'><h2>WCAG Report</h2></td></tr></table><table cellpadding='5' cellspacing='0'><br><br>");
			    wcagBuffer.append(  "<tr><th>Page</th><th>Element</th><th>Type</th><th>Time</th><th>Duration</th><th>Success</th><th>Expected</th><th>Actual</th></tr>" );
			    
			    for ( Object item : DeviceManager.instance().getArtifacts( ArtifactType.WCAG_REPORT ) )
			    {
			        WCAGRecord wcagItem = (WCAGRecord) item;
			        
			        String backgroundColor = null;
			        if ( !wcagItem.getStatus() )
			            backgroundColor = " bgcolor='#ff3333' ";
			        else
			            backgroundColor = " bgcolor='#66FF99' ";
			            

			        wcagBuffer.append( "<tr " + backgroundColor + "><td>" ).append( wcagItem.getPageName() ).append( "</td>" );
	                wcagBuffer.append( "<td>" ).append( wcagItem.getElementName() ).append( "</td>" );
	                wcagBuffer.append( "<td>" ).append( wcagItem.getType() ).append( "</td>" );
	                wcagBuffer.append( "<td>" ).append( timeFormat.format( new Date( wcagItem.getTimeStamp() ) ) ).append( "</td>" );
	                wcagBuffer.append( "<td>" ).append( wcagItem.getDuration() ).append( "</td>" );
	                wcagBuffer.append( "<td>" ).append( wcagItem.getStatus() ).append( "</td>" );
	                wcagBuffer.append( "<td>" ).append( wcagItem.getExpectedValue() ).append( "</td>" );
	                wcagBuffer.append( "<td>" ).append( wcagItem.getActualValue() ).append( "</td></tr>" );
	                wcagBuffer.append( "<tr " + backgroundColor + "><td colspan='6'><i>" ).append( wcagItem.getFailureMessage() ).append( "</i></td>" );
	                wcagBuffer.append( "<td colspan='2'><img height='100' src='file://" ).append( wcagItem.getImageLocation() ).append( "'/></td></tr>" );
			    }
			    
			    wcagBuffer.append( "</table></body></html>" );
			    
                return new Artifact( rootFolder + "wcag.html", wcagBuffer.toString().getBytes() );
				
			default:
				operation = "download";
				break;

		}

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
