package com.perfectoMobile.page.keyWord.step.spi;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
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
			return true;
		}
		catch( Exception e )
		{
			throw new IllegalStateException( "Function Call Failed ", e );
		}
	}

}
