package com.perfectoMobile.page.data.provider;

import java.util.*;

import com.morelandLabs.utility.SQLUtil;

import com.perfectoMobile.page.BY;
import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.data.DefaultPageData;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.element.ElementFactory;


/**
 * The Class SQLDataProvider.
 */
public class SQLPageDataProvider extends AbstractPageDataProvider
{
    //
    // class data
    //

    private static final String DEF_QUERY =
        "SELECT DT.NAME, DT.LOCK_RECORDS, \n" +
        "       PG.NAME, PG.ACTIVE, \n" +
        "       AT.NAME, AT.VALUE \n" +
        "FROM PERFECTO_PAGE_DATA_TYPE DT \n" +
        "     INNER JOIN PERFECTO_PAGE_DATA PG ON PG.TYPE_NAME = DT.NAME \n" +
        "     INNER JOIN PERFECTO_PAGE_DATA_ATTRS ON AT.TYPE_NAME = DT.NAME AND AT.RECORD_NAME = PG.NAME";

    private static final String[] STR_ARR = new String[0];

    //
    // instance data
    //

    /** The username. */
    private String username;
	
    /** The password. */
    private String password;
	
    /** The JDBC URL. */
    private String url;
	
    /** The driver class name. */
    private String driver;
	
    /** The query. */
    private String query;

    /**
     * Instantiates a new SQL data provider.
     *
     * @param username the user name
     * @param password the password
     * @param url the JDBC URL
     * @param driver the driver class name
     */
    public SQLPageDataProvider( String username, String password, String url, String driver )
    {
        this.username = username;
        this.password = password;
        this.url = url;
        this.driver = driver;
        this.query = DEF_QUERY;
    }

    /**
     * Instantiates a new SQL data provider.
     *
     * @param username the user name
     * @param password the password
     * @param url the JDBC URL
     * @param driver the driver class name
     */
    public SQLPageDataProvider( String username, String password, String url, String driver, String query )
    {
        this( username, password, url, driver );

        this.query = query;
    }

    //
    // override AbstractPageDataProvider
    //
    
    public void readPageData()
    {
        try
        {
            Object[][] data = SQLUtil.getResults( username, password, url, driver, query, null );
            HashSet existingRecordTypes = new HashSet();
            HashMap existingRecordsByName = new HashMap();

            for( int i = 0; i < data.length; ++i )
            {
                String record_type_name = (String) data[i][0];
                boolean lockRecords = "Y".equals( (String) data[i][1] );

                if ( !existingRecordTypes.contains( record_type_name ))
                {
                    addRecordType( record_type_name, lockRecords );
                    existingRecordTypes.add( record_type_name );
                }

                String record_name = (String) data[i][2];
                boolean active = "Y".equals( (String) data[i][3] );

                DefaultPageData currentRecord = (DefaultPageData) existingRecordsByName.get( record_name );
                if( currentRecord == null )
                {
                    currentRecord = new DefaultPageData( record_type_name, record_name, active );
                }

                String currentName = (String) data[i][4];
                String currentValue = (String) data[i][5];

                if ( currentValue.startsWith( PageData.TREE_MARKER ) && currentValue.endsWith( PageData.TREE_MARKER ) )
                {
                    //
                    // This is a reference to another page data table
                    //
                    currentRecord.addPageData( currentName );
                    currentRecord.addValue( currentName + PageData.DEF, currentValue );
                    currentRecord.setContainsChildren( true );
                }
                else
                {
                    currentRecord.addValue( currentName, currentValue );
                }
            }

            Iterator records = existingRecordsByName.values().iterator();
            while( records.hasNext() )
            {
                DefaultPageData currentRecord = (DefaultPageData) records.next();
                    
                addRecord( currentRecord );
            }

        }
        catch (Exception e)
        {
            log.fatal( "Error reading Excel Element File", e );
        }
    }
}
