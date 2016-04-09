package com.morelandLabs.spi;

import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class RunDetails implements RunListener
{
    /** The singleton. */
    private static RunDetails singleton = new RunDetails();
    private static DateFormat timeFormat = new SimpleDateFormat( "MM-dd_HH-mm-ss-SSS");
    
    private List<Object[]> detailsList = new ArrayList<Object[]>( 20 );
    
    /**
     * Instance.
     *
     * @return the RunDetails
     */
    public static RunDetails instance()
    {
        return singleton;
    }

    /**
     * Instantiates a RunDetails.
     */
    private RunDetails()
    {

    }
    
    private long startTime = System.currentTimeMillis();

    public long getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime()
    {
        startTime = System.currentTimeMillis();
    }
    
    public String getRootFolder()
    {
        return timeFormat.format( new Date( startTime ) );
    }

    @Override
    public boolean beforeRun( Device currentDevice, String runKey )
    {
        // TODO Auto-generated method stub
        return true;
    }

    @Override
    public void afterRun( Device currentDevice, String runKey, boolean successful )
    {
        detailsList.add( new Object[] { runKey, currentDevice, successful } );
    }
    
    public synchronized void writeHTMLIndex( File rootFolder )
    {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append( "<html><body><table cellspacing='0' border='1'>" );
        
        stringBuilder.append( "<tr><th>ID</th><th>Test Case</th><th>Device</th><th>Device ID</th><th>Status</th><th></th></tr>" );
        
        for ( int i=0; i<detailsList.size(); i++ )
        {
            String runKey = ((String) detailsList.get( i )[0] ).substring( 25 );
            Device device = (Device) detailsList.get( i )[1];
            String location = runKey + "/" + device.getKey() + "/";
            boolean success = (boolean)detailsList.get( i )[2];
            stringBuilder.append( "<tr><td>" ).append( i ).append( "</td><td><a href='" ).append( location + runKey + ".html'>" ).append( runKey ).append( "</a></td><td>" );
            stringBuilder.append(  device.getManufacturer() ).append( " " ).append(  device.getModel() ).append( "</td><td>" ).append( device.getKey() ).append( "</td><td>" );
            stringBuilder.append( success ).append( "</td><td align='center'>" );
            if ( !success )
            {
                stringBuilder.append( "<img height='150' src='").append( location ).append("failure-screenshot.png'/>" );
            }
            stringBuilder.append( "</td></tr>" );
        }
        
        stringBuilder.append( "</table></body></html>" );
        
        try
        {
            File useFile = getIndex( rootFolder );
            useFile.getParentFile().mkdirs();
            FileWriter fileWriter = new FileWriter( useFile );
            fileWriter.write( stringBuilder.toString() );
            fileWriter.close();
        }
        catch( Exception e)
        {
            e.printStackTrace( );
        }
        
    }
    
    public File getIndex( File rootFolder )
    {
        return new File( rootFolder, getRootFolder() + System.getProperty( "file.separator" ) + "index.html" );
    }
    
    
}
