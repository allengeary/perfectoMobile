package com.perfectoMobile.deviceMaintenance.action;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectoMobile.deviceMaintenance.listener.DeviceActionListener;


/**
 * The Class AbstractDeviceAction.
 */
public abstract class AbstractDeviceAction implements DeviceAction
{
	
	/** The device id. */
	protected String deviceId;
	
	/** The log. */
	protected Log log = LogFactory.getLog( DeviceAction.class );
	
	/**
	 * _execute action.
	 *
	 * @return true, if successful
	 */
	protected abstract boolean _executeAction();
	
	/** The action listener. */
	protected DeviceActionListener actionListener;
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.action.DeviceAction#setDeviceId(java.lang.String)
	 */
	public void setDeviceId( String deviceId )
	{
		this.deviceId = deviceId;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.action.DeviceAction#getDeviceId()
	 */
	public String getDeviceId()
	{
		return deviceId;
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run()
	{
		if ( actionListener != null )
		{
			if ( !actionListener.beforeRun( deviceId ) )
				return;
		}
		
		_executeAction();
		
		if ( actionListener != null )
			actionListener.afterRun( deviceId );
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.action.DeviceAction#setDeviceActionListener(com.perfectoMobile.deviceMaintenance.listener.DeviceActionListener)
	 */
	@Override
	public void setDeviceActionListener( DeviceActionListener actionListener )
	{
		this.actionListener = actionListener;
	}
	
}
