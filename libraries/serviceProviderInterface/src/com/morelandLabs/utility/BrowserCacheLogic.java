package com.morelandLabs.utility;

import java.util.Map;
import java.util.HashMap;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DriverCommand;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserCacheLogic
{

    public static void clearSafariIOSCache( RemoteWebDriver driver )
        throws Exception
    {
        HashMap<String, Object> params = new HashMap();

        switchToContext(driver, "NATIVE_APP");

        //
        // get os version
        //
        
        params.put("property", "osVersion");
        String osVerStr = (String) driver.executeScript("mobile:handset:info", params);
        int[] osVer = parseVersion( osVerStr );
        

        //
        // Launch Settings Application on it's main page
        //
        
        params.clear();
        params.put("name", "Settings");
        try {
            driver.executeScript("mobile:application:close", params);
        } catch (Exception e) {}
        driver.executeScript("mobile:application:open", params);
        sleep(1000);

        //
        // Scroll to Top
        //
        
        params.clear();
        params.put("location", "60%,1%");
        driver.executeScript("mobile:touch:tap", params);

        //
        // swipe to expose safari
        //
        
        params.clear();
        params.put("start", "50%,90%");
        params.put("end", "50%,10%");
        driver.executeScript("mobile:touch:swipe", params);

        //
        // click Safari
        //

        params.clear();
        params.put("value", "//cell[@name='Safari']");
        params.put("framework", "perfectoMobile");
        driver.executeScript("mobile:application.element:click", params);
        
        //
        // swipe to bottom
        //
        
        params.clear();
        params.put("start", "50%,90%");
        params.put("end", "50%,10%");
        for (int i=0;i<3;i++){
            driver.executeScript("mobile:touch:swipe", params);
        }

        //
        // clear Cache
        //
        
        params.clear();
        params.put("value", "//*[starts-with(text(),'Clear History')]");
        params.put("framework", "perfectoMobile");
        driver.executeScript("mobile:application.element:click", params);
        params.put("value", "//*[(@class='UIAButton' or @class='UIATableCell') and starts-with(@label,'Clear') and @isvisible='true']");
        driver.executeScript("mobile:application.element:click", params);
        
        //
        // below version 8 need to clear also data
        //
        
        if ( osVer[0] < 8 )
        {
            params.put("value", "//*[starts-with(text(),'Clear Cookies')]");
            driver.executeScript("mobile:application.element:click", params);
            params.put("value", "//*[(@class='UIAButton' or @class='UIATableCell') and starts-with(@label,'Clear') and @isvisible='true']");
            driver.executeScript("mobile:application.element:click", params);
        }

        //
        // Close Settings
        //
        
        params.clear();
        params.put("name", "Settings");
        
        driver.executeScript("mobile:application:close", params);
    }

    public static void clearChromeAndroidCache( RemoteWebDriver driver )
        throws Exception
    {
        HashMap<String, Object> params = new HashMap();
     

        switchToContext(driver, "NATIVE_APP");

        //
        // get os version
        //
        
        params.put("property", "osVersion");
        String osVerStr = (String) driver.executeScript("mobile:handset:info", params);
        int[] osVer = parseVersion( osVerStr );

        if ( osVer[0] >= 5 )
        {
            //
            // Launch Chrome
            //
        
            params.clear();
            params.put("name", "Chrome");
            try {
                driver.executeScript("mobile:application:close", params);
            } catch (Exception e) {}
            driver.executeScript("mobile:application:open", params);
            sleep(1000);

            //
            // select chrome menu
            //
            // resource-id: com.android.chrome:id/document_menu_button -- contains menu_button
            // content-desc: More options
            //

            params.clear();
            params.put("value", "//*[starts-with(@resource-id,'menu_button')]");
            params.put("framework", "perfectoMobile");
            driver.executeScript("mobile:application.element:click", params);

            //
            // select history
            //
            // text: History
            // resource-id: com.android.chrome:id/menu_item_text
            // content-desc: History
            //

            params.clear();
            params.put("value", "//*[starts-with(text(),'History')]");
            params.put("framework", "perfectoMobile");
            driver.executeScript("mobile:application.element:click", params);

            //
            // select clear browsing data
            //
            // text: Clear browsing data...
            // id: clear-browsing-data
            // cssSelector: #clear-browsing-data
            //

            params.clear();
            params.put("value", "//*[starts-with(text(),'Clear browsing')]");
            params.put("framework", "perfectoMobile");
            driver.executeScript("mobile:application.element:click", params);

            //
            // do it!
            //
            // text: Clear
            //

            params.clear();
            params.put("value", "//*[starts-with(text(),'Clear')]");
            params.put("framework", "perfectoMobile");
            driver.executeScript("mobile:application.element:click", params);

            //
            // Close Chrome
            //
        
            params.clear();
            params.put("name", "Chrome");
        
            driver.executeScript("mobile:application:close", params);
        }
        else
        {
            throw new IllegalStateException( "not supported before Android 5.0" );
        }
    }

    //
    // Helpers
    //

    private static void switchToContext(RemoteWebDriver driver, String context)
    {
        RemoteExecuteMethod executeMethod = new RemoteExecuteMethod(driver);
        
        Map<String,String> params = new HashMap<>();
        params.put("name", context);
        
        executeMethod.execute(DriverCommand.SWITCH_TO_CONTEXT, params);
    }

    private static void sleep(long millis) 
    {
        try 
        {
            Thread.sleep(millis);
        } 
        catch (InterruptedException e) 
        {}
    }

    private static int[] parseVersion( String str )
    {
        String[] tokens = str.split( "\\." );
        int[] rtn = new int[ tokens.length ];

        for( int i = 0; i < tokens.length; ++i )
        {
            rtn[ i ] = parseToken( tokens[ i ] );
        }

        return rtn;
    }

    private static int parseToken( String str )
    {
        StringBuilder buff = new StringBuilder();

        for( int i = 0; i < str.length(); ++i )
        {
            char thisChar = str.charAt( i );

            if ( Character.isDigit( thisChar ))
            {
                buff.append( thisChar );
            }
        }

        return Integer.parseInt( buff.toString() );
    }

}
