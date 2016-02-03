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

public class ServicesTest
{
    //
    // Class Data
    //

    private static final String USER_NAME = "userName";
    private static final String PASSWORD = "password";
    private static final String URL = "url";

    //
    // Instance Data
    //

    private Properties props = null;
    private String userName = null;
    private String password = null;
    private String url = null;
    
    //
    // CTOR
    //

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

    @BeforeMethod
    public void setup()
    {
        PerfectoMobile.instance().setBaseUrl( url );
        PerfectoMobile.instance().setUserName( userName );
        PerfectoMobile.instance().setPassword( password );
    }

    //
    // Test Methods
    //

    @Test
    public void testDeviceList()
    {
        Devices deviceHolder = PerfectoMobile.instance().devices();
        HandsetCollection deviceList = deviceHolder.getDevices();
        
        Assert.assertTrue( ( deviceList.getHandsetList().size() > 0 ), "The are devices in the list" );
    }

    @Test
    public void testSamsungDeviceList()
    {
        Devices deviceHolder = PerfectoMobile.instance().devices();
        HandsetCollection deviceList = deviceHolder.getDevices( "Samsung" );
        
        Assert.assertTrue( ( deviceList.getHandsetList().size() > 0 ), "The are Samsung devices in the list" );
    }

    @Test
    public void testScriptList()
    {
        Repositories svc = PerfectoMobile.instance().repositories();
        ItemCollection scriptSet = svc.getRepositorys( Repositories.RepositoryType.SCRIPTS );
        
        Assert.assertTrue( ( scriptSet.getItemList().size() > 0 ), "The are scripts in the list" );
    }

    @Test
    public void testMediaList()
    {
        Repositories svc = PerfectoMobile.instance().repositories();
        ItemCollection scriptSet = svc.getRepositorys( Repositories.RepositoryType.MEDIA );
        
        Assert.assertTrue( ( scriptSet.getItemList().size() > 0 ), "The are media files in the list" );
    }

    @Test
    public void testDataTablesList()
    {
        Repositories svc = PerfectoMobile.instance().repositories();
        ItemCollection scriptSet = svc.getRepositorys( Repositories.RepositoryType.DATATABLES );
        
        Assert.assertTrue( ( scriptSet.getItemList().size() > 0 ), "The are data tables in the list" );
    }
}