/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.perfectoMobile.page;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import com.perfectoMobile.page.element.provider.ElementProvider;
import com.perfectoMobile.page.factory.DefaultPageFactory;
import com.perfectoMobile.page.factory.PageFactory;
import com.perfectoMobile.page.listener.ExecutionListener;

/**
 * The Class PageManager.
 *
 * @author ageary
 */
public class PageManager
{
    private static PageManager singleton = new PageManager();
    private String siteName;
    private ElementProvider elementProvider;
    private List<ExecutionListener> executionListeners = new ArrayList<ExecutionListener>( 20 );

    private Map<String,ExecutionTiming> timingMap = new HashMap<String,ExecutionTiming>( 20 );
    private Map<String,List<ExecutionRecord>> executionLog = new HashMap<String,List<ExecutionRecord>>( 20 );
    private ThreadLocal<Throwable> localException = new ThreadLocal<Throwable>();
    
    private ExecutionTimingWriter timingWriter = null;
    private ExecutionRecordWriter recordWriter = null;
    
    private PageFactory pageFactory = new DefaultPageFactory();
    
    private boolean storeImages = false;
    private String imageLocation;

	public boolean isStoreImages() {
		return storeImages;
	}

	public void setStoreImages(boolean storeImages) {
		this.storeImages = storeImages;
	}
    
    public String getImageLocation() {
		return imageLocation;
	}

	public void setImageLocation(String imageLocation) {
		this.imageLocation = imageLocation;
	}
	
	public void writeImage( BufferedImage image, String fileName )
	{
		try
		{
			File outputFile = null;
			if ( imageLocation != null )
				outputFile = new File( imageLocation, fileName );
			else
				outputFile = new File( fileName );
			
			ImageIO.write( image, "png", outputFile );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
	}



	public enum StepStatus
    {
    	SUCCESS,
    	FAILURE,
    	FAILURE_IGNORED;
    }

    /**
     * Instance to the Page Manager singleton.
     *
     * @return the page manager
     */
    public static PageManager instance()
    {
        return singleton;
    }

    private PageManager() {}
    
    /**
     * Sets the page factory.
     *
     * @param pageFactory the new page factory
     */
    public void setPageFactory( PageFactory pageFactory )
    {
        this.pageFactory = pageFactory;
    }
    
    /**
     * Gets the site name.
     *
     * @return the site name
     */
    public String getSiteName()
    {
    	return siteName;
    }
    
    /**
     * Sets the site name.
     *
     * @param siteName the new site name
     */
    public void setSiteName( String siteName )
    {
    	this.siteName = siteName;
    }

    /**
     * Gets the element provider.
     *
     * @return the element provider
     */
    public ElementProvider getElementProvider()
	{
		return elementProvider;
	}

	/**
	 * Sets the element provider.
	 *
	 * @param elementProvider the new element provider
	 */
	public void setElementProvider( ElementProvider elementProvider )
	{
		this.elementProvider = elementProvider;
	}

	/**
	 * Register execution listener.
	 *
	 * @param l the executionListener
	 */
	public void registerExecutionListener( ExecutionListener l )
	{
		executionListeners.add( l );
	}
	
	/**
	 * Before execution.
	 *
	 * @param keyName the key name
	 */
	public void beforeExecution(String keyName )
	{
		for ( ExecutionListener l : executionListeners )
			l.beforeExecution( keyName );
	}
	
	/**
	 * After execution.
	 *
	 * @param keyName the key name
	 * @param runLength the run length
	 */
	public void afterExecution( String keyName, long runLength )
	{
		for ( ExecutionListener l : executionListeners )
			l.afterExecution( keyName, runLength );
	}
	
	/**
	 * Creates the page by locating the page from the page factory.
	 *
	 * @param pageInterface the page interface
	 * @param webDriver the web driver
	 * @return the page
	 */
	public Page createPage( Class pageInterface, Object webDriver )
    {
        if ( pageFactory == null )
            throw new IllegalArgumentException( "A Service Factory has not been initialized" );

        return pageFactory.createPage( pageInterface, webDriver );
    }

    /**
     * Creates the page by locating the page from the page factory.
     *
     * @param pageInterface the page interface
     * @param pageFactory the page factory
     * @param webDriver the web driver
     * @return the page
     */
    public Page createPage( Class pageInterface, PageFactory pageFactory, Object webDriver )
    {
        if ( pageFactory == null )
            throw new IllegalArgumentException( "This Service Factory has not been initialized" );

        return pageFactory.createPage( pageInterface, webDriver );
    }
    
    /**
     * Adds the execution timing.
     *
     * @param methodName the method name
     * @param runLength the run length
     */
    public void addExecutionTiming( String methodName, long runLength )
    {
    	ExecutionTiming eTime = timingMap.get(  methodName  );
    	if ( eTime == null )
    	{
    		eTime = new ExecutionTiming( methodName );
    		timingMap.put( methodName, eTime );
    	}
    	
    	eTime.addRun( runLength );
    }
    
    /**
     * Adds the execution log.
     *
     * @param executionId the execution id
     * @param deviceName the device name
     * @param group the group
     * @param name the name
     * @param type the type
     * @param timestamp the timestamp
     * @param runLength the run length
     * @param status the status
     * @param detail the detail
     * @param t the t
     */
    public void addExecutionLog( String executionId, String deviceName, String group, String name, String type, long timestamp, long runLength, StepStatus status, String detail, Throwable t )
    {
    	String keyName = new String( executionId + "-" + deviceName );
    	
    	List<ExecutionRecord> recordList = executionLog.get( keyName );
    	
    	if ( recordList == null )
    	{
    		recordList = new ArrayList<ExecutionRecord>( 10 );
    		executionLog.put( keyName, recordList );
    	}
    	
    	recordList.add( new ExecutionRecord( group, name, type, timestamp, runLength, status, detail, t ) );
    }
    
    /**
     * Gets the execution timing.
     *
     * @param methodName the method name
     * @return the execution timing
     */
    public ExecutionTiming getExecutionTiming( String methodName )
    {
    	return timingMap.get( methodName );
    }
    
    /**
     * Gets the timed methods.
     *
     * @return the timed methods
     */
    public Collection<ExecutionTiming> getTimedMethods()
    {
    	return timingMap.values();
    }
    
    /**
     * Sets the execution timing writer.
     *
     * @param timingWriter the new execution timing writer
     */
    public void setExecutionTimingWriter( ExecutionTimingWriter timingWriter )
    {
    	this.timingWriter = timingWriter;
    }
    
    /**
     * Write execution timings.
     */
    public void writeExecutionTimings()
    {
    	if ( timingWriter != null )
    	{
    		timingWriter.startWriting();
    		for ( ExecutionTiming e : getTimedMethods() )
    			timingWriter.writeTiming( e );
    		timingWriter.stopWriting();
    	}
    }
    
    /**
     * Sets the execution timing writer.
     *
     * @param recordWriter the new execution record writer
     */
    public void setExecutionRecordWriter( ExecutionRecordWriter recordWriter )
    {
    	this.recordWriter = recordWriter;
    }
    
    /**
     * Write execution timings.
     */
    public void writeExecutionRecords()
    {
    	if ( recordWriter != null )
    	{
    		for ( String keyName : executionLog.keySet() )
    		{
    			recordWriter.startWriting( keyName );
        		for ( ExecutionRecord e : executionLog.get( keyName ) )
        			recordWriter.writeRecord( e );
        		recordWriter.stopWriting( keyName );
    		}
    	}
    }
    
    /**
     * Sets the throwable.
     *
     * @param t the new throwable
     */
    public void setThrowable( Throwable t )
    {
    	localException.set( t );
    }
    
    /**
     * Gets the throwable.
     *
     * @return the throwable
     */
    public Throwable getThrowable()
    {
    	return localException.get();
    }
}
