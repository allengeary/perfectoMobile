package com.perfectomobile.integration.rqm.action.cloud;

import java.net.URL;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.rqm.model.cloud.ExecutionModel;

public class ExecuteScript extends AbstractAdapterAction
{
	public static final String NAME = "Execute Script";
	public static final String SCRIPT_NAME = "scriptName";
	public static final String DEVICES = "deviceList";

	private String baseUrl;
	private String userName;
	private String password;

	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	public ExecuteScript( String baseUrl, String userName, String password )
	{
		this.baseUrl = baseUrl;
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		
		String scriptName = (String) actionParameters.get(  SCRIPT_NAME );
		String[] deviceList = ( (String) actionParameters.get( DEVICES ) ).split( "," );
		
		String restResponse = new String( getUrl( new URL( baseUrl + "/services/executions?operation=execute&user=" + userName + "&password=" + password + "&scriptKey=" + scriptName + "&param.DUT=" + deviceList[ 0 ] + "&responseFormat=xml" ) ) );
		
		ExecutionModel model = new ExecutionModel( restResponse );
		
		return model;
	}

	public String getName()
	{
		return NAME;
	}

}