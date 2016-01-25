package com.perfectoMobile.deviceMaintenance;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;
import com.perfectoMobile.deviceMaintenance.action.DeviceAction;
import com.perfectoMobile.deviceMaintenance.listener.DeviceActionListener;
import com.perfectoMobile.deviceMaintenance.query.DeviceQuery;

/**
 * The Class MaintenanceManager.
 */
public class MaintenanceManager
{
	private static Log log = LogFactory.getLog( MaintenanceManager.class );
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main( String[] args )
	{
		try
		{
			if ( args.length != 6 )
			{
				System.err.println( "Usage: cloudHost cloudUser cloudPassword queryType actionName listenerName" );
				System.exit( -1 );
			}
			
			if ( log.isInfoEnabled() )
				log.info( "Creating DeviceQuery as " + DeviceQuery.class.getPackage().getName() + "." + args[3] + "DeviceQuery" );
			
			DeviceQuery deviceQuery = (DeviceQuery) Class.forName( DeviceQuery.class.getPackage().getName() + "." + args[3] + "DeviceQuery" ).newInstance();
			
			PerfectoMobile.instance().setBaseUrl( "https://" + args[ 0 ] );
			PerfectoMobile.instance().setUserName( args[ 1 ] );
			PerfectoMobile.instance().setPassword( args[ 2 ] );
			
			List<Handset> handSetList = deviceQuery.findDevices();
			
			if ( log.isInfoEnabled() )
				log.info( "Located " + handSetList.size() + " devices to work with" );
			
			ExecutorService threadPool = Executors.newCachedThreadPool();
			
			for( Handset hs : handSetList )
			{
				
				if ( log.isInfoEnabled() )
					log.info( "Creating DeviceAction as " + DeviceAction.class.getPackage().getName() + "." + args[4] + "DeviceAction for device " + hs.getDeviceId() );
				
				DeviceAction deviceAction = (DeviceAction) Class.forName( DeviceAction.class.getPackage().getName() + "." + args[4] + "DeviceAction" ).newInstance();
				deviceAction.setDeviceId( hs.getDeviceId() );
				
				if ( log.isInfoEnabled() )
					log.info( "Creating DeviceActionListener as " + DeviceActionListener.class.getPackage().getName() + "." + args[5] + "DeviceActionListener" );
				DeviceActionListener deviceListener = (DeviceActionListener) Class.forName( DeviceActionListener.class.getPackage().getName() + "." + args[5] + "DeviceActionListener" ).newInstance();
				deviceAction.setDeviceActionListener( deviceListener );
				
				threadPool.submit( deviceAction );
			}
			
			threadPool.shutdown();
			threadPool.awaitTermination( 10, TimeUnit.MINUTES );
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
	}
}
