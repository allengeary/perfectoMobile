package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * <b>Keyword(s):</b> <code>ATTRIBUTE</code><br>
 * The Attribute keyword allows the developer to extract a named attributes value from an Element and either store it or compare it to provided value. <br><br>
 * <b>Attributes:</b> Attributes defined here are changes to the base attribute contract
 * <ul>
 * <li><i>name</i>: In this context, name defines the attribute name
 * </ul><br><br>
 * 
 * <b>Parameters:</b> Parameters can be supplied in as either a single parameter or a set of 2 parameters <br>
 * <i>Extraction Only</i><br>
 * <ul>
 * <li>Attribute Name: This specifies the name of the attribute that you want to extract</li>
 * </ul>
 * <i>Extraction and Comparison</i><br>
 * <ul>
 * <li>Compare To: This specifies the value that you want to compare the attribute to</li>
 * <li>Attribute Name: This specifies the name of the attribute that you want to extract</li>
 * </ul> 
 * <br><b>Example(s): </b><ul>
 * <li> This example will extract the value of the 'name' attribute and compare it to the static text 'myelement' from the element named 'TEST_ELEMENT'<br>
 * {@literal <step name="TEST_ELEMENT" type="ATTRIBUTE" page="TEST_PAGE"> }<br>
 * &nbsp;&nbsp;&nbsp;{@literal 	<parameter type="static" value="myelement" /> }<br>
 * &nbsp;&nbsp;&nbsp;{@literal 	<parameter type="static" value="name" /> }<br>
 * {@literal </step> }
 * </li>
 * </ul>
 */
public class KWSAttribute extends AbstractKeyWordStep
{
    
    /** The Constant STYLE. */
    private static final String STYLE = "style.";
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
	{
		if ( pageObject == null )
			throw new IllegalStateException( "Page Object was not defined" );
		
		String attributeValue = null;
		Object compareTo = null;
		
		if ( getParameterList().size() == 1 )
		{
		    String attributeName = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "";
		    if ( attributeName.toLowerCase().startsWith( STYLE ) )
		        attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getStyle( attributeName.substring( STYLE.length() ) );
		    else
		        attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getAttribute( attributeName );
			
		}
		else
		{
		    String attributeName = getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "";
            if ( attributeName.toLowerCase().startsWith( STYLE ) )
                attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getStyle( attributeName.substring( STYLE.length() ) );
            else
                attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getAttribute( attributeName );
            
			compareTo = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap );
			attributeValue = getElement( pageObject, contextMap, webDriver, dataMap ).getAttribute( getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "" );
			if ( !attributeValue.equals( compareTo ) )
				throw new IllegalStateException( "ATTRIBUTE Expected [" + compareTo + "] but found [" + attributeValue + "]" );
		}
		
		if ( !validateData( attributeValue + "" ) )
			throw new IllegalStateException( "GET Expected a format of [" + getValidationType() + "(" + getValidation() + ") for [" + attributeValue + "]" );
		
		if ( getContext() != null )
		{
			if ( log.isDebugEnabled() )
				log.debug( "Setting Context Data to [" + attributeValue + "] for [" + getContext() + "]" );
			contextMap.put( getContext(), attributeValue );
		}
		
		return true;
	}

}
