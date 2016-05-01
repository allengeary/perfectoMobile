package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.openqa.selenium.WebDriver;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.KeyWordStep;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;
import com.perfectoMobile.page.keyWord.step.StepSync;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSFork.
 */
public class KWSSync extends AbstractKeyWordStep
{
    
    /** The thread pool. */
    private static ExecutorService threadPool = Executors.newCachedThreadPool();
	/**
	 * Instantiates a new KWS fork.
	 */
	public KWSSync()
	{
		setFork( true );
	}
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap ) throws Exception
	{
			
	    if ( getStepList().isEmpty() )
	        throw new IllegalArgumentException( "There were no steps to execute" );
	    
		StepSync stepSync = new StepSync( webDriver, contextMap, dataMap, pageMap, getStepList().toArray( new KeyWordStep[ 0 ] ) );
		
		while ( !stepSync.stepsComplete() )
		{
		    try
		    {
		        Thread.sleep( 500 );
		    }
		    catch( Exception e )
		    {
		        
		    }
		}
		
		return stepSync.stepsSuccessful();
	    
	}

}
