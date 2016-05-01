package com.perfectomobile.integration.rqm.action.initialization;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.net.http.HttpResponse;
import com.perfectomobile.integration.rqm.bean.Adapter;

public class RegisterAdapter extends AbstractAdapterAction
{
	public static final String NAME = "Register Adapter";
	public static final String AUTO_ADAPTER = "AUTO_ADAPTER";
	
	//
	// Static Headers
	//
	private static final Header OSLC_HEADER = new Header( "OSLC-Core-Version", "2.0" );
	private static final Header CONTENT_TYPE_HEADER = new Header( "Content-Type", "application/rdf+xml;charset=UTF-8" );

	private String adapterName;
	private String adapterDescription;
	private String adapterType;
	private int pollingInterval;

	public RegisterAdapter( String adapterName, String adapterDescription, int pollingInterval, String adapterType )
	{
		this.adapterName = adapterName;
		this.adapterDescription = adapterDescription;
		this.pollingInterval = pollingInterval;
		this.adapterType = adapterType;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		Header sessionId = (Header) actionParameters.get( Header.COOKIE );
		if ( sessionId == null )
			throw new SecurityException( "Session Header is not specified" );
		
		URL autoUrl = new URL( (String) actionParameters.get( AUTO_ADAPTER ) );
		
		//
		// Get the list of all active projects
		//
		HttpRequest autoRequest = new HttpRequest( POST, autoUrl.getPath() );
		autoRequest.getRequestHeaders().addHeader( sessionId );
		autoRequest.getRequestHeaders().addHeader( OSLC_HEADER );
		autoRequest.getRequestHeaders().addHeader( CONTENT_TYPE_HEADER );
		
		
		Adapter myAdapter = new Adapter( pollingInterval, adapterType, adapterDescription, adapterName );
		autoRequest.setRequestBody( myAdapter.toString().getBytes() );
		HttpResponse httpResponse = doPost( autoUrl.toString(), autoRequest );
		
		if ( httpResponse.getresponseDefinition().getCode() != 201 )
			throw new IllegalStateException( "Could not register adapter " + adapterName );
		else
			return true;
	}

	public String getName()
	{
		return NAME;
	}

}
