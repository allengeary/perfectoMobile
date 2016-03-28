/*
 * 
 */
package com.perfectoMobile.device.artifact;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import com.morelandLabs.artifact.ArtifactType;
import com.morelandLabs.page.ExecutionRecord;
import com.morelandLabs.page.StepStatus;
import com.morelandLabs.spi.Device;
import com.morelandLabs.spi.RunDetails;
import com.morelandLabs.wcag.WCAGRecord;
import com.perfectoMobile.device.ConnectedDevice;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.data.DataManager;
import com.perfectoMobile.device.factory.DeviceWebDriver;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractArtifactProducer.
 */
public abstract class AbstractArtifactProducer implements ArtifactProducer
{
    protected static DateFormat timeFormat = new SimpleDateFormat( "HH:mm:ss.SSS");
    protected static DateFormat dateFormat = new SimpleDateFormat( "MM-dd_HH-mm-ss");
    private static final String COMMA = ",";
    
	/** The log. */
	protected Log log = LogFactory.getLog( ArtifactProducer.class );
	
	/**
	 * _get artifact.
	 *
	 * @param webDriver the web driver
	 * @param aType the a type
	 * @param connectedDevice the connected device
	 * @return the artifact
	 */
	protected abstract Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice, String testName );
	
	/**
	 * _get artifact.
	 *
	 * @param webDriver the web driver
	 * @param aType the a type
	 * @param parameterMap the parameter map
	 * @param connectedDevice the connected device
	 * @return the artifact
	 */
	protected abstract Artifact _getArtifact( WebDriver webDriver, ArtifactType aType, Map<String,String> parameterMap, ConnectedDevice connectedDevice, String testName );
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.ArtifactProducer#getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, com.perfectoMobile.device.ConnectedDevice)
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, ConnectedDevice connectedDevice, String testName )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Acquiring an Artifact of type " + aType );
		return _getArtifact( webDriver, aType, connectedDevice, testName );
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.device.artifact.ArtifactProducer#getArtifact(org.openqa.selenium.WebDriver, com.perfectoMobile.device.artifact.ArtifactProducer.ArtifactType, java.util.Map, com.perfectoMobile.device.ConnectedDevice)
	 */
	public Artifact getArtifact( WebDriver webDriver, ArtifactType aType, Map<String, String> parameterMap, ConnectedDevice connectedDevice, String testName )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Acquiring an Artifact of type " + aType + " using " + parameterMap );
		return _getArtifact( webDriver, aType, parameterMap, connectedDevice, testName );
	}
	
	protected Artifact generateHTMLRecord( Device device, String testName, String rootFolder, WebDriver webDriver )
	{
	    StringBuffer stringBuffer = new StringBuffer();
        stringBuffer = new StringBuffer();
        if ( device != null )
        {
            stringBuffer.append( "<html><body><table><tr><td align='right'><b>Device Name:</b></td><td>" ).append( device.getManufacturer() ).append( " " ).append( device.getModel() ).append( " (" ).append( device.getKey() ).append(")</td></tr>");
            stringBuffer.append( "<tr><td align='right'><b>OS:</b></td><td>" ).append( device.getOs() ).append( " (" ).append( device.getOsVersion() ).append(")</td></tr>");
            stringBuffer.append( "<tr><td align='right'><b>Test Name:</b></td><td>" ).append( testName ).append("</td></tr></table><table><br><br>");
        }
        else
            stringBuffer.append( "<html><body><table cellspacing='0'>").append( DeviceManager.instance().getExecutionId() );
        
        boolean success = true;
        if ( DeviceManager.instance().getArtifacts( ArtifactType.EXECUTION_RECORD ) != null && !DeviceManager.instance().getArtifacts( ArtifactType.EXECUTION_RECORD ).isEmpty() )
        {
            for ( Object item : DeviceManager.instance().getArtifacts( ArtifactType.EXECUTION_RECORD ) )
            {
                ExecutionRecord eItem = (ExecutionRecord) item;
                if ( eItem.getStatus().equals( StepStatus.FAILURE ) )
                    success = false;
                stringBuffer.append( eItem.toHTML() );
            }
        }
        
        if ( !success && DataManager.instance().isArtifactEnabled( ArtifactType.DEVICE_LOG ) )
            stringBuffer.append( "<tr><td align='center' colspan='2'><br/><br/><br/><a href='deviceLog.xml'>Device Log</a></td><td rowspan='7' colspan='4' align='center'><img height='500' src='failure-screenshot.png'/></td></tr>" );
        
        if ( !success && DataManager.instance().isArtifactEnabled( ArtifactType.FAILURE_SOURCE ) )
            stringBuffer.append( "<tr><td align='center' colspan='2'><a href='failureDom.xml'>DOM at Failure</a></td></tr>" );
        
        if ( DataManager.instance().isArtifactEnabled( ArtifactType.CONSOLE_LOG ) )
            stringBuffer.append( "<tr><td align='center' colspan='2'><a href='console.txt'>Console Output</a></td></tr>" );
        
        if ( DataManager.instance().isArtifactEnabled( ArtifactType.EXECUTION_RECORD_CSV ) )
            stringBuffer.append( "<tr><td align='center' colspan='2'><a href='" + testName + ".csv'>Execution CSV</a></td></tr>" );
        
        if ( DataManager.instance().isArtifactEnabled( ArtifactType.EXECUTION_REPORT ) || DataManager.instance().isArtifactEnabled( ArtifactType.EXECUTION_REPORT_PDF ) )
            stringBuffer.append( "<tr><td align='center' colspan='2'><a href='EXECUTION_REPORT_PDF.pdf'>Exeuction Report</a></td></tr>" );
        
        if ( DataManager.instance().isArtifactEnabled( ArtifactType.WCAG_REPORT ) )
            stringBuffer.append( "<tr><td align='center' colspan='2'><a href='wcag.html'>WCAG Report</a></td></tr>" );
        
        String wtUrl = ( (DeviceWebDriver) webDriver ).getWindTunnelReport();
        if ( wtUrl != null && !wtUrl.isEmpty() )
        
        stringBuffer.append( "</TABLE></BODY></HTML>" );
        
        //
        // Write out the index file for all tests
        //
        RunDetails.instance().writeHTMLIndex( DataManager.instance().getReportFolder() );
        
        return new Artifact( rootFolder + testName + ".html", stringBuffer.toString().getBytes() );
	}
	
	protected Artifact generateWCAG( Device device, String testName, String rootFolder )
	{
	    StringBuilder wcagBuffer = new StringBuilder();
        
        wcagBuffer.append( "<html><body><table><tr><td align='right'><b>Device Name:</b></td><td>" ).append( device.getManufacturer() ).append( " " ).append( device.getModel() ).append( " (" ).append( device.getKey() ).append(")</td></tr>");
        wcagBuffer.append( "<tr><td align='right'><b>OS:</b></td><td>" ).append( device.getOs() ).append( " (" ).append( device.getOsVersion() ).append(")</td></tr>");
        wcagBuffer.append( "<tr><td align='right'><b>Test Name:</b></td><td>" ).append( testName ).append("</td></tr><tr><td align='center' colspan='2'><h2>WCAG Report</h2></td></tr></table><table cellpadding='5' cellspacing='0'><br><br>");
        wcagBuffer.append(  "<tr><th>Page</th><th>Element</th><th>Type</th><th>Time</th><th>Duration</th><th>Success</th><th>Expected</th><th>Actual</th></tr>" );
        
        if ( DeviceManager.instance().getArtifacts( ArtifactType.WCAG_REPORT ) != null && !DeviceManager.instance().getArtifacts( ArtifactType.WCAG_REPORT ).isEmpty() )
        {
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
        }
        
        wcagBuffer.append( "</table></body></html>" );
        
        return new Artifact( rootFolder + "wcag.html", wcagBuffer.toString().getBytes() );
	}
	
	protected Artifact generateCSVRecord( Device device, String testName, String rootFolder )
	{
        StringBuffer stringBuffer = new StringBuffer();
        
        if ( DeviceManager.instance().getArtifacts( ArtifactType.EXECUTION_RECORD ) != null && !DeviceManager.instance().getArtifacts( ArtifactType.EXECUTION_RECORD ).isEmpty() )
        {
            for ( Object item : DeviceManager.instance().getArtifacts( ArtifactType.EXECUTION_RECORD ) )
            {
                ExecutionRecord eItem = (ExecutionRecord) item;
                
                stringBuffer.append( device.getManufacturer() ).append( COMMA ).append( device.getModel() ).append( COMMA );
                stringBuffer.append( device.getOs() ).append( COMMA ).append( device.getOsVersion()).append( COMMA );
                stringBuffer.append( eItem.getGroup() ).append( COMMA ).append( eItem.getName()).append( COMMA );
                stringBuffer.append( eItem.getType() ).append( COMMA ).append( dateFormat.format( new Date( eItem.getTimeStamp() ) ) ).append( COMMA );
                stringBuffer.append( eItem.getRunTime() ).append( COMMA ).append( eItem.getStatus() ).append( COMMA ).append( "\r\n" );
            }
        }
        
        return new Artifact( rootFolder + testName + ".csv", stringBuffer.toString().getBytes() );
	}

}
