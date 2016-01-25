package com.perfectoMobile.page.timingWriter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;

import com.perfectoMobile.page.ExecutionTiming;
import com.perfectoMobile.page.ExecutionTimingWriter;

/**
 * The Class CSVExecutionTimingWriter.
 */
public class CSVExecutionTimingWriter implements ExecutionTimingWriter
{
	private File outputFile;
	private Writer outputWriter;
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionTimingWriter#setFile(java.io.File)
	 */
	public void setFile( File outputFile )
	{
		this.outputFile = outputFile;
	}
	
	/**
	 * Instantiates a new CSV execution timing writer.
	 */
	public CSVExecutionTimingWriter()
	{

	}
	
	/**
	 * Instantiates a new CSV execution timing writer.
	 *
	 * @param outputFile the output file
	 */
	public CSVExecutionTimingWriter( File outputFile )
	{
		this.outputFile = outputFile;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionTimingWriter#startWriting()
	 */
	@Override
	public void startWriting()
	{
		try
		{
			outputWriter = new BufferedWriter( new FileWriter( outputFile, true ) );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionTimingWriter#writeTiming(com.perfectoMobile.page.ExecutionTiming)
	 */
	@Override
	public void writeTiming( ExecutionTiming executionTiming )
	{
		try
		{
			if ( outputWriter != null )
				outputWriter.write( executionTiming.toString() + "\r\n" );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.ExecutionTimingWriter#stopWriting()
	 */
	@Override
	public void stopWriting()
	{
		try
		{
			outputWriter.flush();
			outputWriter.close();
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}

}
