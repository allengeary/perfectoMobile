
package com.perfectomobile.integration.action;

import java.util.Map;

public class ActionRunner implements Runnable
{
	private Map<String,Object> actionParameters;
	private AdapterAction adapterAction;
	
	public ActionRunner( Map<String,Object> actionParameters, AdapterAction adapterAction )
	{
		this.actionParameters = actionParameters;
		this.adapterAction = adapterAction;
	}
	
	public void run()
	{
		adapterAction.performAction( actionParameters );
	}
}