package com.perfectoMobile.page.recordWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import com.morelandLabs.spi.Device;
import com.morelandLabs.spi.RunDetails;
import com.perfectoMobile.page.ExecutionRecord;
import com.perfectoMobile.page.ExecutionRecordWriter;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.PageManager.StepStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class HTMLExecutionRecordWriter.
 */
public class HTMLExecutionRecordWriter implements ExecutionRecordWriter
{
	private static DateFormat dateFormat = new SimpleDateFormat( "MM-dd_HH-mm-ss");
	
	/** The root folder. */
	private File rootFolder;
	
	/** The output writer. */
	private Writer outputWriter;
	
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionRecordWriter#setFile(java.io.File)
	 */
	@Override
	public void setFile( File rootFolder )
	{
		this.rootFolder = rootFolder;
	}
	
	/**
	 * Instantiates a new HTML execution record writer.
	 */
	public HTMLExecutionRecordWriter()
	{
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * Instantiates a new HTML execution record writer.
	 *
	 * @param rootFolder the root folder
	 */
	public HTMLExecutionRecordWriter( File rootFolder )
	{
		this.rootFolder = rootFolder;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionRecordWriter#startWriting(java.lang.String)
	 */
	@Override
	public void startWriting( String keyName, Device device, String testName )
	{
	    String localFolder = keyName;
	    if ( device != null && testName != null )
	        localFolder = keyName + System.getProperty( "file.separator" ) + device.getKey() + System.getProperty( "file.separator" );
	    
	    String runKey = keyName;
	    StringBuffer stringBuffer = new StringBuffer();
	    if ( device != null )
	    {
    	    stringBuffer.append( "<html><body><table><tr><td align='right'><b>Device Name:</b></td><td>" ).append( device.getManufacturer() ).append( " " ).append( device.getModel() ).append( " (" ).append( device.getKey() ).append(")</td></tr>");
    	    stringBuffer.append( "<tr><td align='right'><b>OS:</b></td><td>" ).append( device.getOs() ).append( " (" ).append( device.getOsVersion() ).append(")</td></tr>");
    	    stringBuffer.append( "<tr><td align='right'><b>Test Name:</b></td><td>" ).append( testName ).append("</td></tr></table><table><br><br>");
	    }
	    else
	        stringBuffer.append( "<html><body><table cellspacing='0'>").append( keyName );
	    
		try
		{
		    File baseFolder = new File( RunDetails.instance().getRootFolder(), localFolder );
		    baseFolder = new File( rootFolder, baseFolder.getPath() );
		    baseFolder.mkdirs();
			outputWriter = new BufferedWriter( new FileWriter( new File( baseFolder, runKey + ".html" ), false ) );
			outputWriter.write( stringBuffer.toString() );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionRecordWriter#writeRecord(com.perfectoMobile.page.ExecutionRecord)
	 */
	@Override
	public void writeRecord( ExecutionRecord executionRecord )
	{
		try
		{
			if ( outputWriter != null )
			{
				outputWriter.write( executionRecord.toHTML() );
			}
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionRecordWriter#stopWriting(java.lang.String)
	 */
	@Override
	public void stopWriting( String keyName, Map<String,String> additionalUrls, boolean success )
	{
		try
		{
			if ( !success )
			{
				outputWriter.write( "<tr><td colSpan='6' align='center'><br/><br/><br/><a href='deviceLog.xml'>Device Log</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><a href='failureDom.xml'>DOM at Failure</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><a href='EXECUTION_REPORT_PDF.pdf'>Exeuction Report</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><a href='../../../testNg/index.html'>Test NG Results</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><img height='500' src='failure-screenshot.png'/></td></tr>" );
				
				for ( String key : additionalUrls.keySet() )
					outputWriter.write( "<tr><td colSpan='6' align='center'><a href='" + additionalUrls.get(key) + "'>" + key + "</a></td></tr>" );
				
			}
			
			outputWriter.write( "</TABLE></BODY></HTML>" );
			outputWriter.flush();
			outputWriter.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}
