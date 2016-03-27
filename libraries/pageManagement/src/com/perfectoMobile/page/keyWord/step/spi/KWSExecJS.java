package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.JavascriptExecutor;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

// TODO: Auto-generated Javadoc
/**
 * The Class KWSExecJS.
 */
public class KWSExecJS extends AbstractKeyWordStep
{

    /* (non-Javadoc)
     * @see com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
     */
    @Override
    public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap )
    {
        if ( pageObject == null )
        {
            throw new IllegalStateException( "Page Object was not defined" );
        }

        Object script = null;
                
        if ( getParameterList().size() == 1 )
        {
            script = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap );
            if ( !( script instanceof String ) )
                throw new IllegalStateException( "Script value must be of type String" );
        }

        if ( !( webDriver instanceof JavascriptExecutor ))
        {
            throw new IllegalStateException( "Web driver (" + webDriver.getClass().getName() + ") doesn't support Javascript execution" );
        }
		
        Object result = ((JavascriptExecutor) webDriver).executeScript( (String) script );
		
        if (( result instanceof String ) &&
            ( !validateData( result + "" )) )
        {
            throw new IllegalStateException( "ExecJS Expected a format of [" + getValidationType() + "(" + getValidation() + ") for [" + result + "]" );
        }
		
        if ( getContext() != null )
        {
            if ( log.isDebugEnabled() )
                log.debug( "Setting Context Data to [" + result + "] for [" + getContext() + "]" );
            contextMap.put( getContext(), result );
        }
		
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
