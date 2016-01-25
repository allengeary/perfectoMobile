<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.model.tasks.TasksModel;

public class GetTasks extends AbstractAdapterAction
{
	public static final String NAME = "Get Tasks";
	public static final String ADAPTER_URL = "adapterUrl";


	public GetTasks()
	{

	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL adapterUrl = new URL( (String) actionParameters.get( ADAPTER_URL ) );
		
		//
		// Search for any instructions
		//
		HttpRequest adapterRequest = new HttpRequest( GET, adapterUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		HttpResponse httpResponse = doGet( adapterUrl.toString(), adapterRequest );
		
		TasksModel tModel = new TasksModel( new String( httpResponse.getresponseBody() ) );
		
		return tModel.getTasks();
	}

	public String getName()
	{
		return NAME;
	}

}
=======
package com.perfectomobile.integration.rqm.action;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.model.tasks.TasksModel;

public class GetTasks extends AbstractAdapterAction
{
	public static final String NAME = "Get Tasks";
	public static final String ADAPTER_URL = "adapterUrl";


	public GetTasks()
	{

	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL adapterUrl = new URL( (String) actionParameters.get( ADAPTER_URL ) );
		
		//
		// Search for any instructions
		//
		HttpRequest adapterRequest = new HttpRequest( GET, adapterUrl.getPath() );
		adapterRequest.getRequestHeaders().addHeader( sessionId );
		HttpResponse httpResponse = doGet( adapterUrl.toString(), adapterRequest );
		
		TasksModel tModel = new TasksModel( new String( httpResponse.getresponseBody() ) );
		
		return tModel.getTasks();
	}

	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
