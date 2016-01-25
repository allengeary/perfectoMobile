package com.perfectoMobile.deviceMaintenance.listener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * The listener interface for receiving abstractDeviceAction events.
 * The class that is interested in processing a abstractDeviceAction
 * event implements this interface, and the object created
 * with that class is registered with a component using the
 * component's <code>addAbstractDeviceActionListener<code> method. When
 * the abstractDeviceAction event occurs, that object's appropriate
 * method is invoked.
 *
 * @see AbstractDeviceActionEvent
 */
public abstract class AbstractDeviceActionListener implements DeviceActionListener
{
	
	/** The log. */
	protected Log log = LogFactory.getLog( DeviceActionListener.class );
}
