package com.perfectomobile.integration.rqm.action;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.model.tasks.InstructionsModel;

public class GetInstructions extends AbstractAdapterAction
{
	public static final String NAME = "Get Instructions";
	public static final String ADAPTER_URL = "adapterUrl";


	public GetInstructions()
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
		
		InstructionsModel iModel = new InstructionsModel( new String( httpResponse.getresponseBody() ) );
		
		return iModel.hasInstructions();
	}

	public String getName()
	{
		return NAME;
	}

}
