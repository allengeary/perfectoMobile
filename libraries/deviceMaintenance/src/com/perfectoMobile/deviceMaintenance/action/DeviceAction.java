package com.perfectoMobile.deviceMaintenance.action;

import com.perfectoMobile.deviceMaintenance.listener.DeviceActionListener;

/**
 * The Interface DeviceAction.
 */
public interface DeviceAction extends Runnable
{
	
	/**
	 * Sets the device id.
	 *
	 * @param deviceId the new device id
	 */
	public void setDeviceId( String deviceId );
	
	/**
	 * Gets the device id.
	 *
	 * @return the device id
	 */
	public String getDeviceId();
	
	/**
	 * Sets the device action listener.
	 *
	 * @param actionListener the new device action listener
	 */
	public void setDeviceActionListener( DeviceActionListener actionListener );
}
