package com.perfectoMobile.gesture.device.action.spi.perfecto;

import java.util.List;

import org.openqa.selenium.WebDriver;

import com.morelandLabs.application.ApplicationDescriptor;
import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Handset;
import com.perfectoMobile.gesture.device.action.AbstractDefaultAction;
import com.perfectoMobile.gesture.device.action.DeviceAction;

// TODO: Auto-generated Javadoc
/**
 * The Class ResetApplicationsAction.
 */
public class ResetApplicationsAction extends AbstractDefaultAction implements DeviceAction
{
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.gesture.device.action.AbstractDefaultAction#_executeAction(org.openqa.selenium.WebDriver, java.util.List)
	 */
	@Override
	public boolean _executeAction( WebDriver webDriver, List<Object> parameterList )
	{
		String executionId = getExecutionId( webDriver );
		String deviceName = getDeviceName( webDriver );
		
		String applicationName = (String) parameterList.get( 0 );

		PerfectoMobile.instance().application().uninstallAll( executionId, deviceName );
		
		return true;
	}

}
