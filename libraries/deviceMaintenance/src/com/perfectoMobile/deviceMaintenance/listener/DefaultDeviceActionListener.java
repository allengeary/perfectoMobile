package com.perfectoMobile.deviceMaintenance.listener;

/**
 * The listener interface for receiving defaultDeviceAction events.
 * The class that is interested in processing a defaultDeviceAction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDefaultDeviceActionListener<code> method. When
 * the defaultDeviceAction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DefaultDeviceActionEvent
 */
public class DefaultDeviceActionListener extends AbstractDeviceActionListener
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.listener.DeviceActionListener#afterRun(java.lang.String)
	 */
	@Override
	public void afterRun( String deviceId )
	{
		if ( log.isInfoEnabled() )
			log.info( "Run on " + deviceId + " complete" );
		
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.listener.DeviceActionListener#beforeRun(java.lang.String)
	 */
	@Override
	public boolean beforeRun( String deviceId )
	{
		if ( log.isInfoEnabled() )
			log.info( "Running on " + deviceId );
		return true;
	}
}
