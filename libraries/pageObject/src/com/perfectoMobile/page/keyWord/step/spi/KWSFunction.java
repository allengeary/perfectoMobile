package com.perfectoMobile.page.keyWord.step.spi;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The Class KWSFunction.
 */
public class KWSFunction extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		long startTime = System.currentTimeMillis();
		if ( pageObject == null )
			throw new IllegalStateException( "Page Object was not defined" );
		try
		{
			Object[] parameterArray = getParameters( contextMap, dataMap );
			Method method = findMethod( pageObject.getClass(), getName(), parameterArray );
			method.invoke( pageObject, parameterArray );
			PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), "FUNCTION", System.currentTimeMillis(), System.currentTimeMillis() - startTime, true, "", null );
			
			return true;
		}
		catch( Exception e )
		{
			PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), "FUNCTION", System.currentTimeMillis(), System.currentTimeMillis() - startTime, false, e.getMessage(), e );
			log.error( "Error executing function [" + getName() + "] on page [" + getPageName() + "]", e );
			return false;
		}
	}

}
