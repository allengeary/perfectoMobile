package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * <b>Keyword(s):</b> <code>CLICK</code><br>
 * The click keyword allows you to locate an element on the screen and click on it <br><br>
 * <br><b>Example(s): </b><ul>
 * <li> This example will click on an element named 'TEST_ELEMENT' from TEST_PAGE<br>
 * {@literal <step name="TEST_ELEMENT" type="CLICK" page="TEST_PAGE" /> }<br>
 * </li>
 * </ul>
 */
public class KWSClick extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		if ( pageObject == null || getElement( pageObject, contextMap, webDriver, dataMap ) == null )
			throw new IllegalStateException( "The Element " + getName() + " is not defined.  Ensure it exists on your Page object" );
		
		getElement( pageObject, contextMap, webDriver, dataMap ).click();
		
		return true;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
	 */
	public boolean isRecordable()
	{
		return false;
	}


}
