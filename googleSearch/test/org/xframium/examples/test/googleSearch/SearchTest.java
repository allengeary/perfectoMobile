/*******************************************************************************
 * xFramium
 *
 * Copyright 2016 by Moreland Labs LTD (http://www.morelandlabs.com)
 *
 * Some open source application is free software: you can redistribute 
 * it and/or modify it under the terms of the GNU General Public 
 * License as published by the Free Software Foundation, either 
 * version 3 of the License, or (at your option) any later version.
 *  
 * Some open source application is distributed in the hope that it will 
 * be useful, but WITHOUT ANY WARRANTY; without even the implied warranty 
 * of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *  
 * You should have received a copy of the GNU General Public License
 * along with xFramium.  If not, see <http://www.gnu.org/licenses/>.
 *
 * @license GPL-3.0+ <http://spdx.org/licenses/GPL-3.0+>
 *******************************************************************************/
package org.xframium.examples.test.googleSearch;

import java.io.File;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import org.xframium.examples.page.googleSearch.Search;
import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.application.XMLApplicationProvider;
import com.morelandLabs.artifact.ArtifactType;
import com.morelandLabs.spi.RunDetails;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.cloud.CloudRegistry;
import com.perfectoMobile.device.cloud.XMLCloudProvider;
import com.perfectoMobile.device.data.DataManager;
import com.perfectoMobile.device.data.DataProvider;
import com.perfectoMobile.device.data.DataProvider.DriverType;
import com.perfectoMobile.device.data.perfectoMobile.PerfectoMobileDataProvider;
import com.perfectoMobile.device.data.perfectoMobile.ReservedHandsetValidator;
import com.perfectoMobile.device.logging.ThreadedFileHandler;
import com.perfectoMobile.device.ng.AbstractSeleniumTest;
import com.perfectoMobile.gesture.GestureManager;
import com.perfectoMobile.gesture.factory.spi.PerfectoGestureFactory;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.element.Element.WAIT_FOR;
import com.perfectoMobile.page.element.provider.ExcelElementProvider;
import com.perfectoMobile.page.listener.LoggingExecutionListener;

public class SearchTest extends AbstractSeleniumTest
{
    @BeforeSuite
    public void setupSuite()
    {
        //
        // Passing a configuration file name indicates it is in the class path - passing a File object indicates the file system
        //
        CloudRegistry.instance().setCloudProvider( new XMLCloudProvider( "cloudRegistry.xml" ) );
        CloudRegistry.instance().setCloud( "allstate" );
        
        //
        // We are running in using the RemoteWebDriver with any device reserved to me
        //
        DataProvider dataProvider = new PerfectoMobileDataProvider( new ReservedHandsetValidator(), DriverType.WEB );
        
        //DataProvider dataProvider = new XMLDataProvider( "deviceRegistry.xml", DriverType.WEB );
        
        //
        // The factory for generating devices Gestures
        //
        GestureManager.instance().setGestureFactory( new PerfectoGestureFactory() );
        
        //
        // Application - Passing a configuration file name indicates it is in the class path - passing a File object indicates the file system
        //
        ApplicationRegistry.instance().setApplicationProvider( new XMLApplicationProvider( "applicationRegistry.xml" ) );
        ApplicationRegistry.instance().setAUT( "Google" );
        
        //
        // Optional parameters to automatically download reports upon completion
        //
        DataManager.instance().setReportFolder( new File( "test-output" ) );
        DataManager.instance().setAutomaticDownloads( new ArtifactType[] { ArtifactType.EXECUTION_REPORT_XML, ArtifactType.FAILURE_SOURCE, ArtifactType.EXECUTION_RECORD_HTML, ArtifactType.CONSOLE_LOG, ArtifactType.EXECUTION_RECORD_CSV } );
        DataManager.instance().readData( dataProvider );
        
        
        PageManager.instance().setSiteName( "Google" );
        PageManager.instance().registerExecutionListener( new LoggingExecutionListener() );
        PageManager.instance().setElementProvider( new ExcelElementProvider( "googleSearch.xlsx", "Google" ) );
        
        
        //
        // Configure the thread logger to separate test case log files
        //
        ThreadedFileHandler threadedHandler = new ThreadedFileHandler();
        threadedHandler.configureHandler( Level.INFO );
        
        //
        // The RunDetail singleton can be registered to track all runs for the consolidated output report
        //
        DeviceManager.instance().addRunListener( RunDetails.instance() );
        
    }
    
    @Test( dataProvider = "deviceManager")
    public void testSearchPage( TestName testName ) throws Exception
    {
        //
        // Gets a proxied instance to your page implementation
        //
        Search searchPage = (Search) PageManager.instance().createPage( Search.class, getWebDriver() );
        
        searchPage.getElement( Search.SEARCH ).setValue( "Perfecto" );
        
        searchPage.getElement( Search.SEARCH_BUTTON ).click();
        
        searchPage.getElement( Search.RESULTS ).waitFor( 30, TimeUnit.SECONDS, WAIT_FOR.VISIBLE, "" );
        
        
    } 
}
