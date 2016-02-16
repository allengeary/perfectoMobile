package com.morelandLabs.integrations.perfectoMobile.rest.services;

import java.util.*;

import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.services.*;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.*;

// TODO: Auto-generated Javadoc
/**
 * The Class ServicesTest.
 */
public class ServicesTest
{
    //
    // Class Data
    //

    /** The Constant USER_NAME. */
    private static final String USER_NAME = "userName";
    
    /** The Constant PASSWORD. */
    private static final String PASSWORD = "password";
    
    /** The Constant URL. */
    private static final String URL = "url";
    
    /** The Constant POLL_INTERVAL_MS. */
    private static final long POLL_INTERVAL_MS = 500;

    //
    // Instance Data
    //

    /** The props. */
    private Properties props = null;
    
    /** The user name. */
    private String userName = null;
    
    /** The password. */
    private String password = null;
    
    /** The url. */
    private String url = null;

    //
    // Test Data
    //

    /** The Constant DEFAULT_DEVICE. */
    private static final String DEFAULT_DEVICE = "3219D3B1";
    
    /** The device. */
    private String device = null;
    
    /** The Constant DEFAULT_SCRIPT. */
    private static final String DEFAULT_SCRIPT = "GROUP:cameraTest.xml";
    
    //
    // CTOR
    //

    /**
     * Instantiates a new services test.
     */
    public ServicesTest()
    {
        try
        {
            props = new Properties();
            props.load(ServicesTest.class.getResourceAsStream("/cloud.properties"));

            userName = props.getProperty( "userName" );
            password = props.getProperty( "password" );
            url = props.getProperty( "url" );
        }
        catch( Throwable e )
        {
            e.printStackTrace();
        }
    }

    //
    // Test Setup
    //

    /**
     * Setup.
     */
    @BeforeMethod
        public void setup()
    {
    	System.out.println( "\nsetup\n" );
    	
        PerfectoMobile.instance().setBaseUrl( url );
        PerfectoMobile.instance().setUserName( userName );
        PerfectoMobile.instance().setPassword( password );
    }

    //
    // Test Methods
    //

    /**
     * Test device list.
     */
    @Test
    public void testDeviceList()
    {
    	System.out.println( "\ntestDeviceList\n" );

        Devices deviceHolder = PerfectoMobile.instance().devices();
        HandsetCollection deviceList = deviceHolder.getDevices();
        
        Assert.assertTrue( ( deviceList.getHandsetList().size() > 0 ), "The are devices in the list" );

        findAnAvailableDevice( deviceList );
    }

    /**
     * Test samsung device list.
     */
    @Test
    public void testSamsungDeviceList()
    {
    	System.out.println( "\ntestSamsungDeviceList\n" );

        Devices deviceHolder = PerfectoMobile.instance().devices();
        HandsetCollection deviceList = deviceHolder.getDevices( "Samsung" );
        
        Assert.assertTrue( ( deviceList.getHandsetList().size() > 0 ), "The are Samsung devices in the list" );
    }

    /**
     * Test script list.
     */
    @Test
    public void testScriptList()
    {
    	System.out.println( "\ntestScriptList\n" );

        Repositories svc = PerfectoMobile.instance().repositories();
        ItemCollection scriptSet = svc.getRepositorys( Repositories.RepositoryType.SCRIPTS );
        
        Assert.assertTrue( ( scriptSet.getItemList().size() > 0 ), "The are scripts in the list" );
    }

    /**
     * Test media list.
     */
    @Test
    public void testMediaList()
    {
    	System.out.println( "\ntestMediaList\n" );

        Repositories svc = PerfectoMobile.instance().repositories();
        ItemCollection scriptSet = svc.getRepositorys( Repositories.RepositoryType.MEDIA );
        
        Assert.assertTrue( ( scriptSet.getItemList().size() > 0 ), "The are media files in the list" );
    }

    /**
     * Test data tables list.
     */
    @Test
    public void testDataTablesList()
    {
    	System.out.println( "\ntestDataTablesList\n" );

        Repositories svc = PerfectoMobile.instance().repositories();
        ItemCollection scriptSet = svc.getRepositorys( Repositories.RepositoryType.DATATABLES );
        
        Assert.assertTrue( ( scriptSet.getItemList().size() > 0 ), "The are data tables in the list" );
    }

    /**
     * Test script run.
     */
    @Test
    public void testScriptRun()
    {
    	System.out.println( "\ntestScriptRun\n" );

        Execution theRun = PerfectoMobile.instance().executions().execute( DEFAULT_SCRIPT, getTestDevice() );
        String execId = theRun.getExecutionId();

        Assert.assertTrue( ( execId != null ), "Have an exec id: " + execId );

        do
        {
            sleep( POLL_INTERVAL_MS );
        }
        while( !PerfectoMobile.instance().executions().status( execId ).getCompleted() );

        ExecutionResult finalResult = PerfectoMobile.instance().executions().status( execId );

        Assert.assertTrue( ( finalResult.getReportKey() != null ), "Have an report id: " + finalResult.getReportKey() );

        //
        // Gather results to return
        //

        ExecutionReport rep = PerfectoMobile.instance().reports().download( finalResult.getReportKey(), 
                                                                            Reports.ReportFormat.xml );
        Assert.assertTrue( ( rep != null ), "Have an exec report" );

        if ( rep != null )
        {
            Assert.assertTrue( (( rep.getActionList() != null ) && ( rep.getActionList().size() > 0 )),
                               "Have Actions to report" );

            if (( rep.getActionList() != null ) && ( rep.getActionList().size() > 0 ))
            {
                Iterator actions = rep.getActionList().iterator();
                    
                while( actions.hasNext() )
                {
                    Action action = (Action) actions.next();

                    Assert.assertTrue( ( action.getName() != null ), "Action is named: " + action );
                }
            }
        }
    }

    /**
     * Test device operations.
     */
    @Test
    public void testDeviceOperations()
    {
    	System.out.println( "\ntestDeviceOperations\n" );
    	
    	String execId = null;
    	
    	try
    	{
    		Execution exec = PerfectoMobile.instance().executions().startExecution();

    		execId = exec.getExecutionId();

    		Assert.assertTrue( ( execId != null ), "Have an exec id: " + execId );

    		Execution result = PerfectoMobile.instance().device().open( execId, getTestDevice() );
    		
    		System.out.println( "zoom: " + result );

    		Assert.assertTrue( ( result.getStatus().equals( "Success" ) ), "Open Succeeded: " + result.getStatus() );

    		result = PerfectoMobile.instance().device().rotate( execId, getTestDevice(), Device.ScreenOrientation.landscape );

    		Assert.assertTrue( ( result.getStatus().equals( "Success" ) ), "Rotate Succeeded: " + result.getStatus() );
    	}
    	catch ( Throwable e)
    	{
    		e.printStackTrace();
    	}
    	finally
    	{
    		Execution result = PerfectoMobile.instance().device().close( execId, getTestDevice() );
    		
    		Assert.assertTrue( ( result.getStatus().equals( "Success" ) ), "Close Succeeded: " + result.getStatus() );

    		result = PerfectoMobile.instance().executions().endExecution( execId );
    	}
    }

    //
    // Helpers
    //

    /**
     * Gets the test device.
     *
     * @return the test device
     */
    private String getTestDevice()
    {
        return DEFAULT_DEVICE; //(( device != null ) ? device : DEFAULT_DEVICE );
    }

    /**
     * Find an available device.
     *
     * @param deviceList the device list
     */
    private void findAnAvailableDevice( HandsetCollection deviceList )
    {
        Iterator devices = deviceList.getHandsetList().iterator();

        while( devices.hasNext() )
        {
            Handset handset = (Handset) devices.next();

            if ( !handset.getInUse() )
            {
                device = handset.getDeviceId();
            }
        }
    }

    /**
     * Sleep.
     *
     * @param duration the duration
     */
    private void sleep( long duration )
    {
        try
        {
            Thread.currentThread().sleep( duration );
        }
        catch( Throwable e ) 
        {}
    }
}