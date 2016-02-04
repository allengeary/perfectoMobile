package com.perfectoMobile.deviceMaintenance.query;

import java.util.List;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;

/**
 * The Interface DeviceQuery.
 */
public interface DeviceQuery
{
	
	/**
	 * Find devices.
	 *
	 * @return the list
	 */
	public List<Handset> findDevices();
}
