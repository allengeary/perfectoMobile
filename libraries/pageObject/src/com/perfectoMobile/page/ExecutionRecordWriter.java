package com.perfectoMobile.page;

import java.io.File;

/**
 * The Interface ExecutionTimingWriter responsible for writing out timed executions on completion.
 */
public interface ExecutionRecordWriter
{
	
	/**
	 * Sets the file.
	 *
	 * @param rootFolder the new file
	 */
	public void setFile( File rootFolder );
	
	/**
	 * Start writing.
	 *
	 * @param keyName the key name
	 */
	public void startWriting( String keyName );
	
	/**
	 * Write timing.
	 *
	 * @param exeuctionRecord the exeuction record
	 */
	public void writeRecord( ExecutionRecord exeuctionRecord );
	
	/**
	 * Stop writing.
	 *
	 * @param keyName the key name
	 */
	public void stopWriting( String keyName );
}
