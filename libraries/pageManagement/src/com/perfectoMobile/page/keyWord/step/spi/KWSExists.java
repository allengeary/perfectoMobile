package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * <b>Keyword(s):</b> <code>EXISTS</code><br>
 * The exists keyword verifies that an element is present.  It does not check vo visibility it just verifies that it is in the XML structure<br><br>
 * <br><b>Example(s): </b><ul>
 * <li> This example will locate element named 'TEST_ELEMENT' from TEST_PAGE<br>
 * {@literal <step name="TEST_ELEMENT" type="EXISTS" page="TEST_PAGE" /> }<br>
 * </li>
 * </ul>
 */
public class KWSExists extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		if ( pageObject == null || getElement( pageObject, contextMap, webDriver, dataMap ) == null )
			throw new IllegalStateException( "The Element " + getName() + " is not defined.  Ensure it exists on your Page object" );
		return getElement( pageObject, contextMap, webDriver, dataMap ).isPresent();
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
	 */
	public boolean isRecordable()
	{
		return false;
	}


}
