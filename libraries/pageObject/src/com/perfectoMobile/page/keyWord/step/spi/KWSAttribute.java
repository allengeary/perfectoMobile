package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The Class KWSAttribute.
 */
public class KWSAttribute extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		if ( pageObject == null )
			throw new IllegalStateException( "Page Object was not defined" );
		
		String attributeValue = null;
		Object compareTo = null;
		
		if ( getParameterList().size() == 1 )
			attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getAttribute( getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "" );
		else
		{
			compareTo = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap );
			attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getAttribute( getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "" );
			if ( !attributeValue.equals( compareTo ) )
				return false;
		}
		
		if ( !validateData( attributeValue + "" ) )
			return false;
		
		if ( getContext() != null )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Setting Context Data to [" + attributeValue + "] for [" + getContext() + "]" );
			contextMap.put( getContext(), attributeValue );
		}
		
		return true;
	}

}
