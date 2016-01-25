<<<<<<< HEAD
package com.perfectomobile.integration.rqm.action.task;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.action.AdapterAction;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.rqm.bean.Progress;

public abstract class AbstractTask extends AbstractAdapterAction
{
	public static final String TASK_MODEL = "TASK_MODEL";
	public static final String PROJECT = "PROJECT_AREA";
	public static final String ADAPTER_ID = "adapterId";
	public static final String RESULT_URL = "resultUrl";
	public static final String EXECUTION_ID = "executionId";
	protected Log log = LogFactory.getLog( AdapterAction.class ); 

	protected void setStatus( HttpRequest httpRequest, URL url, int progressLevel, String statusMessage, String urn ) throws IOException
	{
		Progress progress = new Progress();
		progress.addProperty( "", "", "Status", statusMessage );
		progress.addProperty( "", "", "Time", new Date().toString() );
		progress.addProperty( "", "", "Streaming Execution", urn );
		
		progress.setProgressLevel( progressLevel );
		
		httpRequest.setRequestBody( progress.toString().getBytes() );
		
		doPut( url.toString(), httpRequest );
		
	}

}
=======
package com.perfectomobile.integration.rqm.action.task;

import java.io.IOException;
import java.net.URL;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectomobile.integration.action.AbstractAdapterAction;
import com.perfectomobile.integration.action.AdapterAction;
import com.perfectomobile.integration.net.http.HttpRequest;
import com.perfectomobile.integration.rqm.bean.Progress;

public abstract class AbstractTask extends AbstractAdapterAction
{
	public static final String TASK_MODEL = "TASK_MODEL";
	public static final String PROJECT = "PROJECT_AREA";
	public static final String ADAPTER_ID = "adapterId";
	public static final String RESULT_URL = "resultUrl";
	public static final String EXECUTION_ID = "executionId";
	protected Log log = LogFactory.getLog( AdapterAction.class ); 

	protected void setStatus( HttpRequest httpRequest, URL url, int progressLevel, String statusMessage, String urn ) throws IOException
	{
		Progress progress = new Progress();
		progress.addProperty( "", "", "Status", statusMessage );
		progress.addProperty( "", "", "Time", new Date().toString() );
		progress.addProperty( "", "", "Streaming Execution", urn );
		
		progress.setProgressLevel( progressLevel );
		
		httpRequest.setRequestBody( progress.toString().getBytes() );
		
		doPut( url.toString(), httpRequest );
		
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
