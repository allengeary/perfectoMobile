package com.perfectoMobile.deviceMaintenance.query;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;

/**
 * The Class AbstractDeviceQuery.
 */
public abstract class AbstractDeviceQuery implements DeviceQuery
{
	
	/** The log. */
	protected Log log = LogFactory.getLog( DeviceQuery.class );
	
	/**
	 * _find devices.
	 *
	 * @return the list
	 */
	protected abstract List<Handset> _findDevices();
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.query.DeviceQuery#findDevices()
	 */
	public List<Handset> findDevices()
	{
		return _findDevices();
	}
}
