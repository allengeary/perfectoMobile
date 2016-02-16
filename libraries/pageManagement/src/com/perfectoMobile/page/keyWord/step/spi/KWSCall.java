package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.KeyWordDriver;
import com.perfectoMobile.page.keyWord.KeyWordParameter;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSCall.
 */
public class KWSCall extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap ) throws Exception
	{
		if ( log.isDebugEnabled() )
			log.debug( "Execution Function " + getName() );
		
		if ( getParameterList() != null && !getParameterList().isEmpty() )
		{
			for ( KeyWordParameter param : getParameterList() )
			{
				String dataProvider = getParameterValue( param, contextMap, dataMap ) + "";
				String[] dpArray = dataProvider.split( "=" );
				if ( dpArray.length == 2 )
				{
					PageData pageData = dataMap.get( dpArray[1] );
					if ( pageData != null )
						dataMap.put( dpArray[0], pageData );
				}
			}
		}
		
		return KeyWordDriver.instance().executionFunction( getName(), webDriver, dataMap );
	}

}
