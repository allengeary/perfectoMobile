package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * <b>Keyword(s):</b> <code>EXISTS</code><br>
 * The exists keyword verifies that an element is present.  It does not check vo visibility it just verifies that it is in the XML structure<br><br>
 * <br><b>Example(s): </b><ul>
 * <li> This example will locate element named 'TEST_ELEMENT' from TEST_PAGE<br>
 * {@literal <step name="TEST_ELEMENT" type="EXISTS" page="TEST_PAGE" /> }<br>
 * </li>
 * </ul>
 */
public class KWSAt extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
	{
		if ( pageObject == null )
			throw new IllegalStateException( "There was no Page Object defined" );
		
		Element currentElement = getElement( pageObject, contextMap, webDriver, dataMap );
		
		Point at = ( (WebElement) currentElement.getNative() ).getLocation();
        Dimension size = ( (WebElement) currentElement.getNative() ).getSize();
        
        
        System.out.println( at );
        System.out.println( size );
		
		if ( getContext() != null )
        {
            if ( log.isDebugEnabled() )
                log.debug( "Setting Context Data to [" + currentElement.getValue() + "] for [" + getContext() + "]" );
            
            //Point at = ( (WebElement) currentElement.getNative() ).getLocation();
            //Dimension size = ( (WebElement) currentElement.getNative() ).getSize();
            
            
            System.out.println( at );
            System.out.println( size );
            
            contextMap.put( getContext(), ( (WebElement) currentElement.getNative() ).getLocation() );
        }
		
		return currentElement.isPresent();
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
	 */
	public boolean isRecordable()
	{
		return false;
	}


}
