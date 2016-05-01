package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * <b>Keyword(s):</b> <code>BREAK</code><br>
 * The break keyword is used in conjunction with a the loop keyword.  This allows you to exit out of loop without failing a test.<br><br>
 * <b>Attributes:</b> Attributes defined here are changes to the base attribute contract
 * <ul>
 * <li><i>name</i>: In this context, name is just used for logging purposes
 * <li><i>page</i>: In this context, page is unused
 * </ul><br><br>
 * <b>Parameters:</b> No parameters are required <br>
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
 * <li> This example will break out of a loop (the name is just for logging in a break statement)<br>
 * {@literal <step name="breakOut" type="BREAK"> }<br>
 * </li>
 * </ul>
 */
public class KWSBreak extends AbstractKeyWordStep
{

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	@Override
	public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
	{
		throw new KWSLoopBreak();
	}

}
