package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import org.openqa.selenium.WebDriver;
import com.morelandLabs.page.StepStatus;
import com.morelandLabs.spi.driver.CachingDriver;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;



public class KWSReport extends AbstractKeyWordStep
{

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com
	 * .perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map,
	 * java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
	{
	    long startTime = System.currentTimeMillis();
		if ( log.isDebugEnabled() )
			log.debug( "Executing Device Action " + getName() + " using " + getParameterList() );
		
		StringBuilder reportData = new StringBuilder();
		if ( getName() != null && !getName().isEmpty() )
		    reportData.append( getName() ).append( "\t" );
		
		for ( int i=0; i<getParameterList().size(); i++ )
		    reportData.append( getParameterValue( getParameterList().get( i ), contextMap, dataMap ) ).append( "\t" );
		
		PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), reportData.toString(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, StepStatus.REPORT, "", null, getThreshold(), getDescription(), false );
		
		return true;
	}
	
	@Override
	public boolean isRecordable()
	{
	    return false;
	}

}
