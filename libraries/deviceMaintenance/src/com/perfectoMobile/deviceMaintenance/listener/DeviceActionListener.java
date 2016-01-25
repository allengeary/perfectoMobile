package com.perfectoMobile.deviceMaintenance.listener;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;

/**
 * The listener interface for receiving deviceAction events.
 * The class that is interested in processing a deviceAction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addDeviceActionListener<code> method. When
 * the deviceAction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see DeviceActionEvent
 */
public interface DeviceActionListener
{
	
	/**
	 * Before run.
	 *
	 * @param deviceId the device id
	 * @return true, if successful
	 */
	public boolean beforeRun( String deviceId );
	
	/**
	 * After run.
	 *
	 * @param deviceId the device id
	 */
	public void afterRun( String deviceId );
}
