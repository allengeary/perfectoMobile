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
 * <b>Keyword(s):</b> <code>CALL</code><br>
 * The Call keyword allows the developer to execute a function or test defined in XML.  The test or function will inherit any dataProvider and dataDriver
 * data that was provided to the calling test/function.  If a function is called and the funcvtino defined a dataProvider that did not exist on the calling test/function
 * then it will be added during execution.  It is possible to override the name of a dataProvider/dataDriver using a parameter as defined below.  This allows a function 
 * to use a single dataProvider name while other calling function may use dataProvider/dataDrivers with different names<br><br>
 * <b>Attributes:</b> Attributes defined here are changes to the base attribute contract
 * <ul>
 * <li><i>name</i>: In this context, name is the function or test name
 * <li><i>page</i>: In this context, page is unused
 * </ul><br><br>
 * <b>Parameters:</b> The only parameter available for the call keyword are the dataProvider/dataDriver overrides.  Each parameter will define a single override and there can be many<br>
 * <i>Extraction Only</i><br>
 * <ul>
 * <li>Data Override: The specifies a single data override in the format of to=from</li>
 * </ul>
 * <br><b>Example(s): </b><ul>
 * <li> This example will call a function name LOGIN<br>
 * {@literal <step name="LOGIN" type="CALL" /> }<br>
 * </li>
 * <li> This example will call a function name LOGIN and override the systemLogin with authData (the function expects authData)<br>
 * {@literal <step name="LOGIN" type="CALL" /> }<br>
 * &nbsp;&nbsp;&nbsp;{@literal  <parameter type="static" value="authData=systemLogin" /> }<br>
 * {@literal </step> }
 * 
 * </ul>
 */
public class KWSCall extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap ) throws Exception
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
		
		return KeyWordDriver.instance().executionFunction( getName(), webDriver, dataMap, pageMap );
	}

}
