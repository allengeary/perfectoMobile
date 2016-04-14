/*
 * 
 */
package com.perfectoMobile.device.artifact.api;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.Map;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import com.morelandLabs.artifact.ArtifactType;
import com.morelandLabs.utility.XMLEscape;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.artifact.AbstractArtifactProducer;
import com.perfectoMobile.device.artifact.Artifact;

// TODO: Auto-generated Javadoc
/**
 * The Class PerfectoArtifactProducer.
 */
public class SeleniumArtifactProducer extends AbstractArtifactProducer
{

    /**
     * Instantiates a new perfecto artifact producer.
     */
    public SeleniumArtifactProducer()
    {
        // TODO Auto-generated constructor stub
    }

    /**
     * Instantiates a new perfecto artifact producer.
     *
     * @param reportFormat
     *            the report format
     */
    public SeleniumArtifactProducer( String reportFormat )
    {
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.perfectoMobile.device.artifact.AbstractArtifactProducer#_getArtifact(
     * org.openqa.selenium.WebDriver,
     * com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType,
     * com.perfectoMobile.device.ConnectedDevice)
     */
    @Override
    protected Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice, String testName )
    {
        return null;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.perfectoMobile.device.artifact.AbstractArtifactProducer#_getArtifact(
     * org.openqa.selenium.WebDriver,
     * com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType,
     * java.util.Map, com.perfectoMobile.device.ConnectedDevice)
     */
    @Override
    protected Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, Map<String, String> parameterMap, ConnectedDevice connectedDevice, String testName )
    {
        String rootFolder = testName + System.getProperty( "file.separator" ) + connectedDevice.getDevice().getKey() + System.getProperty( "file.separator" );
        switch ( aType )
        {
            case FAILURE_SOURCE:
                return new Artifact( rootFolder + "failureDOM.xml", webDriver.getPageSource().getBytes() );
    
            case CONSOLE_LOG:
                Artifact consoleArtifact = new Artifact( rootFolder + "console.txt", DeviceManager.instance().getLog().getBytes() );
                DeviceManager.instance().clearLog();
                return consoleArtifact;
    
            case DEVICE_LOG:
    
                try
                {
                    LogEntries logEntries = webDriver.manage().logs().get( LogType.BROWSER );
                    if ( logEntries != null )
                    {
                        StringBuilder logBuilder = new StringBuilder();
                        for ( LogEntry logEntry : logEntries )
                            logBuilder.append( dateFormat.format( new Date( logEntry.getTimestamp() ) ) ).append( ": " ).append( logEntry.getMessage() ).append( "\r\n" );
    
                        return new Artifact( rootFolder + "deviceLog.txt", logBuilder.toString().getBytes() );
                    }
                    return null;
                }
                catch ( Exception e )
                {
                    log.info( "Could not generate device logs" );
                    return null;
                }
    
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
     * @param currentUrl
     *            the current url
     * @return the url
     */
    public byte[] getUrl( URL currentUrl )
    {
        if ( log.isDebugEnabled() )
            log.debug( "Executing " + currentUrl.toString() );
        InputStream inputStream = null;
        try
        {
            ByteArrayOutputStream resultBuilder = new ByteArrayOutputStream();

            HttpURLConnection y = (HttpURLConnection) currentUrl.openConnection();

            inputStream = y.getInputStream();
            byte[] buffer = new byte[1024];
            int bytesRead = 0;

            while ( (bytesRead = inputStream.read( buffer )) > 0 )
                resultBuilder.write( buffer, 0, bytesRead );

            return resultBuilder.toByteArray();
        }
        catch ( Exception e )
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
            catch ( Exception e )
            {
            }
        }

    }
}
