/**
 * 
 */
package com.quantum.steps;

import static com.qmetry.qaf.automation.step.CommonStep.getText;
import static com.qmetry.qaf.automation.step.CommonStep.verifyText;
import static com.qmetry.qaf.automation.step.CommonStep.waitForVisible;
import static com.quantum.utils.DeviceUtils.closeApp;
import static com.quantum.utils.DeviceUtils.getCurrentContext;
import static com.quantum.utils.DeviceUtils.getQAFDriver;
import static com.quantum.utils.DeviceUtils.goToHomeScreen;
import static com.quantum.utils.DeviceUtils.installApp;
import static com.quantum.utils.DeviceUtils.startApp;
import static com.quantum.utils.DeviceUtils.switchToContext;
import static com.quantum.utils.DeviceUtils.uninstallApp;
import static com.quantum.utils.DeviceUtils.waitForPresentTextVisual;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qmetry.qaf.automation.step.QAFTestStepProvider;
import com.qmetry.qaf.automation.ui.WebDriverTestBase;
import com.qmetry.qaf.automation.ui.webdriver.QAFExtendedWebElement;
import com.qmetry.qaf.automation.util.StringUtil;
import com.quantum.utils.AppiumUtils;
import com.quantum.utils.ConfigurationUtils;
import com.quantum.utils.ConsoleUtils;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;

/**
 * @author chirag.jayswal
 *
 */
@QAFTestStepProvider
public class MetLifeStepsDefs
{

    @When ( "I debug")
    public void debugStop()
    {
        System.out.println( "Stopping for debug" );
    }

    @When ( "I set \"(.*?)\" to \"(.*?)\"")
    public void clearAndSetField( String loc, String val )
    {
        QAFExtendedWebElement el = new QAFExtendedWebElement( loc );
        el.clear();
        el.sendKeys( val );
    }

    @When ( "I close android keyboard")
    public void closeAndroidKeyboard()
    {
        Map params = new HashMap<String, String>();
        params.put( "mode", "off" );
        new WebDriverTestBase().getDriver().executeScript( "mobile:keyboard:display", params );
    }

    @Then ( "Verify \"(.*?)\" equals \"(.*?)\"")
    public void VerifyTextAtLocation( String loc, String val )
    {

        String s = getText( loc );
        System.out.println( "loc=" + loc + "   text found=/" + s + "/" );
        verifyText( loc, val );
    }

    @When ( "I reset and start metlife application")
    public void resetMetLifeApplication()
    {
        Map<String, Object> param = new HashMap<>();
        param.put( "format", "identifier" );
        String appList;
        boolean isCorrectVersion = false;
        String MetLife_App_Install_File;
        String MetLife_App_Installed_Name;
        String MetLife_App_Start_Name;
        String MetLife_App_Version;

        // get device op sys
        Map<String, Object> param2 = new HashMap<>();
        param2.put( "property", "os" );
        String deviceOS = (String) getQAFDriver().executeScript( "mobile:handset:info", param2 );
        deviceOS = deviceOS.toLowerCase();

        if ( deviceOS.equals( "ios" ) )
        {
            MetLife_App_Install_File = "PUBLIC:MetLife App Binaries\\MetLife.ipa";
            MetLife_App_Installed_Name = "com.metlife.usbusiness";
            MetLife_App_Start_Name = "MetLife";
            MetLife_App_Version = "1.9.8.2";
        }
        else
        {
            MetLife_App_Install_File = "PUBLIC:MetLife App Binaries\\MetLife.apk";
            MetLife_App_Installed_Name = "com.metlifeapps.metlifeus";
            MetLife_App_Start_Name = "MetLife";
            MetLife_App_Version = "1.9.8.2";
        }

        goToHomeScreen();

        param.put( "format", "identifier" );
        appList = (String) getQAFDriver().executeScript( "mobile:application:find", param );
        System.out.println( "APPLIST=" + appList );

        // if app is installed then close it, reopen it and check its version
        if ( appList.indexOf( MetLife_App_Installed_Name ) > -1 )
        {
            try
            {
                closeApp( MetLife_App_Start_Name, "name" );
            }
            catch ( Exception e )
            {
            }

            startApp( MetLife_App_Start_Name, "name" );
            switchToContext( "WEBVIEW" );

            try
            {
                QAFExtendedWebElement el = new QAFExtendedWebElement( "xpath=//*[contains(text(),'Version:')]" );
                el.waitForVisible( 15 * 1000 );
                String version = el.getText();
    
                System.out.println( "Currently installed " + MetLife_App_Start_Name + " application is " + version );
                isCorrectVersion = version.endsWith( MetLife_App_Version );
            }
            catch( Exception e )
            {
                isCorrectVersion = false;
            }

            if ( !isCorrectVersion )
                System.out.println( "Reinstalling app to required version " + MetLife_App_Version );
        }

        if ( !isCorrectVersion )
        {
            try
            {
                closeApp( MetLife_App_Start_Name, "name" );
                uninstallApp( MetLife_App_Start_Name, "name" );
            }
            catch ( Exception e )
            {
                System.out.println( "Attempt to uninstall " + MetLife_App_Start_Name + " failed. Will attempt to reinstall anyway." );
                e.printStackTrace();
            }

            installApp( MetLife_App_Install_File, true );

            startApp( MetLife_App_Start_Name, "name" );
            switchToContext( "WEBVIEW" );

            QAFExtendedWebElement el = new QAFExtendedWebElement( "xpath=//*[contains(text(),'Version:')]" );
            el.waitForVisible( 15 * 1000 );
            String version = el.getText();

            System.out.println( "Currently installed " + MetLife_App_Start_Name + " application is " + version );
        }

        switchToContext( "NATIVE_APP" );
    }

    @Then ( "result should be \"(.+)\"")
    public void resultShouldBe( long l1 )
    {
        verifyText( "input.box", "in:" + String.valueOf( l1 ) );
    }

    @Then ( "I switch to frame \"(.*?)\"")
    public static void switchToFrame( String nameOrIndex )
    {
        if ( StringUtil.isNumeric( nameOrIndex ) )
        {
            int index = Integer.parseInt( nameOrIndex );
            new WebDriverTestBase().getDriver().switchTo().frame( index );
        }
        else
        {
            new WebDriverTestBase().getDriver().switchTo().frame( nameOrIndex );
        }
    }

    @Then ( "I switch to \"(.*?)\" frame by element")
    public static void switchToFrameByElement( String loc )
    {
        new WebDriverTestBase().getDriver().switchTo().frame( new QAFExtendedWebElement( loc ) );
    }

    @When ( "^I log out of MetLife$" )
    public static void logout()
    {
        switchToContext( "WEBVIEW" );
        QAFExtendedWebElement u1 = new QAFExtendedWebElement( "button.dropdownMenu" );
        u1.click();
        
        waitForVisible( "link.logOut" );
        QAFExtendedWebElement u2 = new QAFExtendedWebElement( "link.logOut" );
        u2.click();
        
        switchToContext( "NATIVE_APP" );

        QAFExtendedWebElement u3 = new QAFExtendedWebElement( "button.logout" );
        u3.click();
    }
    
    @When ( "^I log out of MetLife Online$" )
    public static void logoutMLO()
    {
        try 
        { 
            Thread.sleep( 5000 );
        }
        catch( Exception e )
        {
            
        }
        switchToContext( "WEBVIEW" );
        QAFExtendedWebElement userName = new QAFExtendedWebElement( "mlo.text.userid" );
        if ( !userName.isPresent() )
        {
            QAFExtendedWebElement logout = new QAFExtendedWebElement( "mlo.link.logout" );
            logout.click();
            userName.waitForPresent( 30000 );
        }
    }

    
    @When ( "^I log into MetLife using \"(.*?)\" and \"(.*?)\" and I am \"(.*?)\"$")
    public void logIntoMetLife( String userName, String password, String expectedName )
    {
        switchToContext( "WEBVIEW" );
        waitForVisible( "textfield.userid" );
        QAFExtendedWebElement u1 = new QAFExtendedWebElement( "textfield.userid" );
        QAFExtendedWebElement p1 = new QAFExtendedWebElement( "textfield.password" );

        u1.clear();
        String uName = userName;
        String pWord = password;
        u1.sendKeys( uName );

        p1.clear();
        p1.sendKeys( pWord );

        QAFExtendedWebElement b1 = new QAFExtendedWebElement( "button.login" );
        b1.click();
        waitForPresentTextVisual( "Hello, " + expectedName, 30 );
    }

}
