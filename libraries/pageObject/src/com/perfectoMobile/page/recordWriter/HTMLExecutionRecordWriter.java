package com.perfectoMobile.page.recordWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.perfectoMobile.page.ExecutionRecord;
import com.perfectoMobile.page.ExecutionRecordWriter;

/**
 * The Class HTMLExecutionRecordWriter.
 */
public class HTMLExecutionRecordWriter implements ExecutionRecordWriter
{
	private File rootFolder;
	private Writer outputWriter;
	
	/** The success. */
	boolean success = false;
	
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
	public void startWriting( String keyName )
	{
		try
		{
			outputWriter = new BufferedWriter( new FileWriter( new File( rootFolder, keyName + ".html" ), false ) );
			outputWriter.write( "<HTML><BODY><H2>" + keyName + "</H2><TABLE cellSpacing='0'>" );
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
			success = executionRecord.isStatus();
			if ( outputWriter != null )
				outputWriter.write( executionRecord.toHTML() );
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
	public void stopWriting( String keyName )
	{
		try
		{
			if ( !success )
			{
				outputWriter.write( "<tr><td colSpan='6' align='center'><br/><br/><br/><a href='" + keyName + "/deviceLog.xml'>Device Log</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><a href='" + keyName + "/failureDom.xml'>DOM at Failure</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><a href='" + keyName + "/EXECUTION_REPORT_PDF.pdf'>Exeuction Report</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><a href='testNg/index.html'>Test NG Results</a></td></tr>" );
				outputWriter.write( "<tr><td colSpan='6' align='center'><img height='500' src='" + keyName + "-screenshot.png'/></td></tr>" );
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
