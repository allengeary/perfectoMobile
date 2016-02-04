<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.cloud;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.rqm.model.cloud.HandsetModel;

public class HandsetList extends AbstractAdapterAction
{
	public static final String NAME = "Handset List";
	public static final String ADAPTER_URL = "adapterUrl";

	private String baseUrl;
	private String userName;
	private String password;

	public HandsetList( String baseUrl, String userName, String password )
	{
		this.baseUrl = baseUrl;
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		
		String restResponse = new String( getUrl( new URL( baseUrl + "/services/handsets?operation=list&user=" + userName + "&password=" + password + "&inUse=false&status=connected&availableTo=" + userName + "&responseFormat=xml" ) ) );
		
		HandsetModel hModel = new HandsetModel( restResponse );
		return hModel.getEntries();
	}

	public String getName()
	{
		return NAME;
	}

}
=======
package com.perfectomobile.integration.rqm.action.cloud;

import java.net.URL;
import java.util.Map;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.rqm.model.cloud.HandsetModel;

public class HandsetList extends AbstractAdapterAction
{
	public static final String NAME = "Handset List";
	public static final String ADAPTER_URL = "adapterUrl";

	private String baseUrl;
	private String userName;
	private String password;

	public HandsetList( String baseUrl, String userName, String password )
	{
		this.baseUrl = baseUrl;
		this.userName = userName;
		this.password = password;
	}

	@Override
	protected Object _performAction( Map<String, Object> actionParameters ) throws Exception
	{
		
		String restResponse = new String( getUrl( new URL( baseUrl + "/services/handsets?operation=list&user=" + userName + "&password=" + password + "&inUse=false&status=connected&availableTo=" + userName + "&responseFormat=xml" ) ) );
		
		HandsetModel hModel = new HandsetModel( restResponse );
		return hModel.getEntries();
	}

	public String getName()
	{
		return NAME;
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
