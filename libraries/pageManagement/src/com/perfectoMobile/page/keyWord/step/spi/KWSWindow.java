package com.perfectoMobile.page.keyWord.step.spi;

import java.util.Map;
import java.util.Set;
import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

public class KWSWindow extends AbstractKeyWordStep
{

    private enum SwitchType
    {
        BY_WINTITLE, BY_WINURL, BY_FRAME, BY_PARENTFRAME, BY_DEFAULT, BY_WINCLOSE, BY_ALERT;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#_executeStep(com
     * .perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map,
     * java.util.Map)
     */
    @Override
    public boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap, Map<String, Page> pageMap ) throws Exception
    {
        if ( log.isDebugEnabled() )
            log.debug( "Execution Function " + getName() );

        if ( getParameterList().size() < 1 )
            throw new IllegalArgumentException( "First Parameter Switchtype should be provided with values BY_WINTITLE| BY_WINURL|BY_FRAME|BY_PARENTFRAME|BY_DEFAULT" );

        try
        {
            // Verify if the parameter-1 values are correct
            String switchType = getParameterValue( getParameterList().get( 0 ), contextMap, dataMap ) + "";
            String switchExpValue = "";

            switch ( SwitchType.valueOf( switchType ) )
            {
                case BY_WINTITLE:
                    if ( getParameterList().size() < 2 )
                        throw new IllegalArgumentException( "Please provide the title for the window as a parameter" );
                    switchExpValue = getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "";
                    return verifySwitchWindow( webDriver, switchType, switchExpValue );
                case BY_WINURL:
                    if ( getParameterList().size() < 2 )
                        throw new IllegalArgumentException( "Please provide the URL for the window as a parameter" );
                    switchExpValue = getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "";
                    return verifySwitchWindow( webDriver, switchType, switchExpValue );
                case BY_FRAME:
                    if ( getParameterList().size() < 2 )
                        throw new IllegalArgumentException( "Please provide the Frame id for the Frame as a parameter" );
                    switchExpValue = getParameterValue( getParameterList().get( 1 ), contextMap, dataMap ) + "";
                    webDriver.switchTo().frame( switchExpValue );
                    break;
                case BY_PARENTFRAME:
                    webDriver.switchTo().parentFrame();
                    break;
                case BY_DEFAULT:
                    webDriver.switchTo().defaultContent();
                    break;
                case BY_WINCLOSE:
                    webDriver.close();
                    break;
                case BY_ALERT:
                    WebDriverWait alertWait = new WebDriverWait( webDriver, 5 );
                    alertWait.until( ExpectedConditions.alertIsPresent() );
                    Alert alert = webDriver.switchTo().alert();
                    alert.accept();
                    break;
                default:
                    throw new IllegalArgumentException( "Parameter switchtype should be BY_WINTITLE| BY_WINURL|BY_FRAME|BY_PARENTFRAME|BY_DEFAULT|BY_ALERT" );
            }

        }
        catch ( Exception e )
        {
            log.error( "Error executing function for validation [" + getName() + "] on page [" + getPageName() + "]", e );
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep#isRecordable()
     */
    public boolean isRecordable()
    {
        return false;
    }

    /**
     * 
     * @param webDriver
     * @param byTitleUrl
     * @return
     */
    private boolean verifySwitchWindow( WebDriver webDriver, String byTitleOrUrl, String winExpValue )
    {

        boolean bSwitchWindow = false;
        String winActValue = "";
        Set<String> availableWindows = webDriver.getWindowHandles();
        if ( !availableWindows.isEmpty() )
        {
            for ( String windowId : availableWindows )
            {
                if ( byTitleOrUrl.equalsIgnoreCase( "BY_WINTITLE" ) )
                {
                    winActValue = webDriver.switchTo().window( windowId ).getTitle().trim().toLowerCase();
                }
                else
                {
                    winActValue = webDriver.switchTo().window( windowId ).getCurrentUrl().trim().toLowerCase();
                }

                winExpValue = winExpValue.trim().toLowerCase();
                System.out.println( "Exp value  " + winExpValue + "Actual value :" + winActValue );
                if ( winActValue.contains( winExpValue ) )
                {
                    bSwitchWindow = true;
                    break;
                }
            }
        }

        return bSwitchWindow;
    }

}
