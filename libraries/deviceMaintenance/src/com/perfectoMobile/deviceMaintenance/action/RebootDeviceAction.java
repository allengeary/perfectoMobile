package com.perfectoMobile.deviceMaintenance.action;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;

/**
 * The Class RebootDeviceAction.
 */
public class RebootDeviceAction extends AbstractDeviceAction
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.deviceMaintenance.action.AbstractDeviceAction#_executeAction()
	 */
	@Override
	protected boolean _executeAction()
	{
		String executionId = PerfectoMobile.instance().executions().startExecution().getExecutionId();
		PerfectoMobile.instance().device().open( executionId, deviceId );
		PerfectoMobile.instance().device().reboot( executionId, deviceId );
		PerfectoMobile.instance().executions().endExecution( executionId );
		return true;
	}
}
