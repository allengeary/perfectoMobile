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
		if ( getParameterList().size() < 2 )
			throw new IllegalArgumentException( "You must provide a parameter to compare against and a paramter for the named attribute" );
		
		Object compareTo = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap );
		
		if ( log.isDebugEnabled() )
			log.debug( "Validation value for comparison [" + compareTo + "]" );
		
		String attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getAttribute( getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "" );

		if ( getContext() != null )
			contextMap.put( getContext(), attributeValue );
		
		return attributeValue.equals( compareTo );
	}

}
