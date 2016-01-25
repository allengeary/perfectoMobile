package com.perfectoMobile.page.keyWord.step.spi;

import java.lang.reflect.Method;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The Class KWSReturn.
 */
public class KWSReturn extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		if ( pageObject == null )
			throw new IllegalStateException( "Page Object was not defined" );
		if ( getParameterList().size() < 1 )
			throw new IllegalArgumentException( "You must provide a parameter 1 parameter to a step in which the type is Validation" );
		
		Object compare = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap );
		
		if ( log.isDebugEnabled() )
			log.debug( "Validation value for comparison [" + compare + "]" );
		
		try
		{
			Object[] parameterArray = getParameters( contextMap, dataMap );
			Method method = findMethod( pageObject.getClass(), getName(), new Object[ 0 ] );
			Object returnValue = method.invoke( pageObject, new Object[ 0 ] );
			
			return returnValue.equals( compare );
		}
		catch( Exception e )
		{
			log.error( "Error executing function for validation [" + getName() + "] on page [" + getPageName() + "]", e );
			return false;
		}
	}

}
