package com.perfectoMobile.page.keyWord.step;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.WebDriver;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.KeyWordDriver;
import com.perfectoMobile.page.keyWord.KeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class ElementFork.
 */
public class StepSync
{
	
	/** The log. */
	private Log log = LogFactory.getLog( StepSync.class );
	
	/** The thread pool. */
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private WebDriver webDriver;
	private Map<String, Object> contextMap;
	private Map<String,PageData> dataMap;
	private Map<String,Page> pageMap;
	private boolean stepsSuccessful = false;
	private List<StepThread> stepThreads = new ArrayList<StepThread>( 10 );
	
	public StepSync( WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap, KeyWordStep[] stepArray )
    {
        this.webDriver = webDriver;
        this.contextMap = contextMap;
        this.dataMap = dataMap;
        this.pageMap = pageMap;

        for ( KeyWordStep step : stepArray )
        {
            StepThread s = new StepThread( step );
            stepThreads.add( s );
            threadPool.submit( s );
            try
            {
                Thread.sleep( 250 );
            }
            catch( Exception e )
            {
                
            }
        }
    }

	
	
	public boolean stepsComplete()
	{
	    for ( StepThread step : stepThreads )
	    {
	        if ( step.isRunning() )
	            return false;
	    }
	    
	    return true;
	}
	
	public boolean stepsSuccessful()
    {
        return stepsSuccessful;
    }

    
	
	/**
	 * The Class ElementThread.
	 */
	private class StepThread implements Runnable
	{
		private KeyWordStep step;
		private boolean running = true;
		
		/**
		 * Instantiates a new element thread.
		 *
		 * @param currentElement the current element
		 * @param index the index
		 * @param timeOut the time out
		 */
		public StepThread( KeyWordStep step )
		{
			this.step = step;
		}
		
		
		
		public boolean isRunning()
        {
            return running;
        }

        /* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run()
		{
			try
			{
			    Page page = null;
	            if ( step.getPageName() != null )
	            {
	                page = pageMap.get( step.getPageName() );
	                if ( page == null )
	                {
	                    page = PageManager.instance().createPage( KeyWordDriver.instance().getPage( step.getPageName() ), webDriver );
	                    pageMap.put( step.getPageName(), page );
	                }
	            }
	            
	            if ( !step.executeStep( page, webDriver, contextMap, dataMap, pageMap ) )
	            {
	                if ( log.isWarnEnabled() )
                        log.warn( "***** Step [" + step.getName() + "] Failed" );
                    stepsSuccessful = false;
	            }
			}
			catch( Exception e )
			{
			    if ( log.isWarnEnabled() )
                    log.warn( "***** Step [" + step.getName() + "] Failed", e );
                stepsSuccessful = false;
			}
			
			running = false;
		}
	}
}
