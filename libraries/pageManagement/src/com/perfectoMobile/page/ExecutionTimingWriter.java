package com.perfectoMobile.page;

import java.io.File;

// TODO: Auto-generated Javadoc
/**
 * The Interface ExecutionTimingWriter responsible for writing out timed executions on completion.
 */
public interface ExecutionTimingWriter
{
	
	/**
	 * Sets the file.
	 *
	 * @param rootFolder the new file
	 */
	public void setFile( File rootFolder );
	/**
	 * Start writing.
	 */
	public void startWriting();
	
	/**
	 * Write timing.
	 *
	 * @param executionTiming the execution timing
	 */
	public void writeTiming( ExecutionTiming executionTiming );
	
	/**
	 * Stop writing.
	 */
	public void stopWriting();
}
