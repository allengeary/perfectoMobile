package com.perfectomobile.integration.action;

import java.util.Map;

import com.perfectomobile.integration.rqm.RQMAdapter;

public interface AdapterAction
{
	public Object performAction( Map<String,Object> actionParameters );
	public String getName();
	public void setRQMAdapter( RQMAdapter rqmAdapter );
}
