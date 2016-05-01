package com.perfectomobile.integration.rqm;

import java.io.FileInputStream;
import java.net.HttpCookie;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectomobile.integration.action.AdapterAction;
import com.perfectomobile.integration.net.http.Header;
import com.perfectomobile.integration.rqm.action.GetInstructions;
import com.perfectomobile.integration.rqm.action.GetTasks;
import com.perfectomobile.integration.rqm.action.HandleTask;
import com.perfectomobile.integration.rqm.action.cloud.ExecuteScript;
import com.perfectomobile.integration.rqm.action.cloud.HandsetList;
import com.perfectomobile.integration.rqm.action.cloud.RepositoryList;
import com.perfectomobile.integration.rqm.action.initialization.AdapterInformation;
import com.perfectomobile.integration.rqm.action.initialization.CheckRegistration;
import com.perfectomobile.integration.rqm.action.initialization.LoadCatalog;
import com.perfectomobile.integration.rqm.action.initialization.LoadResultCreationFactory;
import com.perfectomobile.integration.rqm.action.initialization.LoadServiceProvider;
import com.perfectomobile.integration.rqm.action.initialization.Login;
import com.perfectomobile.integration.rqm.action.initialization.RegisterAdapter;
import com.perfectomobile.integration.rqm.action.initialization.ValidateProjectArea;
import com.perfectomobile.integration.rqm.action.task.ExecuteTask;
import com.perfectomobile.integration.rqm.action.task.ImportTask;
import com.perfectomobile.integration.rqm.bean.Project;
import com.perfectomobile.integration.rqm.model.AdapterModel;
import com.perfectomobile.integration.rqm.model.RootServicesModel;
import com.perfectomobile.integration.ssl.SSLUtils;

public class RQMAdapter extends Thread
{
	private Log log = LogFactory.getLog( RQMAdapter.class ); 
	private static Map<String,AdapterAction> actionMap = new HashMap<String,AdapterAction>( 20 );
	private RootServicesModel rootModel = null;
	private String adapterInstructionsUrl = null;
	private String adapterTasksUrl = null;
	private String adapterId = null;
	private String resultUrl = null;
	private Header sessionId = null;
	private Project rqmProject;
	
	private static final String rqmConfig = "rqmConfig";
	//
	// RQM Initialization Parameters
	//
	private int pollInterval = 10;
	private String rqmUrl;
	private String rqmUserName;
	private String rqmPassword;
	private String projectArea;
	private String adapterName;
	private String adapterType;
	private String attachmentFolder;
	
	//
	// Perfecto Mobile Initialization Parameters
	//
	private String cloudUserName;
	private String cloudPassword;
	private String cloudUrl;
		
	
	public RQMAdapter() throws Exception
	{
		String rqmFile = System.getProperty( rqmConfig );
		
		if ( rqmFile == null )
		{
			System.err.println( "You must provide an Adapter Configuration file using the System Property [rqmConfig]" );
			System.exit( 0 );
		}
		
		System.out.println( "Reading Adapter Configuration from [" + rqmFile + "]" );
		
		Properties p = new Properties();
		p.load( new FileInputStream( rqmFile ) );
		
		try { pollInterval = Integer.parseInt( p.getProperty( "pollInterval" ) ); } catch( Exception e ){ pollInterval = 5; }
		rqmUrl = p.getProperty( "rqmUrl" );
		rqmUserName = p.getProperty( "rqmUserName" );
		rqmPassword = p.getProperty( "rqmPassword" );
		projectArea = p.getProperty( "projectArea" );
		adapterName = p.getProperty( "adapterName" );
		adapterType = p.getProperty( "adapterType" );
		
		attachmentFolder = p.getProperty( "attachmentFolder" );
		cloudUserName = p.getProperty( "cloudUserName" );
		cloudPassword = p.getProperty( "cloudPassword" );
		cloudUrl = p.getProperty( "cloudUrl" );
		
		rootModel = new RootServicesModel( new URL( rqmUrl + "rootservices" ) );
		registerDefaultActions();
		
	}
	
	public void run()
	{
		while( true )
		{
			try
			{
				if ( log.isInfoEnabled() )
					log.info( "Initializing Adapter" );
				initializeAdapter();
				
				if ( log.isInfoEnabled() )
					log.info( "Adapter Initialized - Initiating Heartbeat" );
				
				while ( true )
				{
					if ( log.isDebugEnabled() )
						log.debug( "Iteration Execution " );
					
					Map<String,Object> parameterMap = getSessionMap();
					parameterMap.put(GetInstructions.ADAPTER_URL, adapterInstructionsUrl );
					if ( (Boolean) executeAction( GetInstructions.NAME,  parameterMap ) )
					{
						if ( log.isInfoEnabled() )
							log.info( "There are pending requests" );
						
						parameterMap = getSessionMap();
						parameterMap.put(GetTasks.ADAPTER_URL, adapterTasksUrl );
						String[] taskList = (String[]) executeAction( GetTasks.NAME,  parameterMap );
						
						for( int i=0; i<taskList.length; i++ )
						{
							parameterMap = getSessionMap();
							parameterMap.put(HandleTask.TASK_URL, taskList[ i ] );
							parameterMap.put(HandleTask.PROJECT, rqmProject );
							parameterMap.put( HandleTask.ADAPTER_ID, adapterId );
							parameterMap.put( HandleTask.RESULT_URL, resultUrl );
							executeAction( HandleTask.NAME,  parameterMap );
						}
					}
					
					
					Thread.sleep( pollInterval * 1000 );
					
				}
				
				
			}
			catch( Exception e )
			{
				log.error( "Error in Adapter Run", e );
			}
			
			try { Thread.sleep( 10000 ); } catch( Exception e ) {}
		}
	}
	
	public void initializeAdapter() throws Exception
	{
		if ( log.isInfoEnabled() )
			log.info( "Initializing Adapter" );
		
		//
		// Login in to RQM
		//
		HttpCookie sessionCookie = (HttpCookie) executeAction( Login.NAME );
		sessionId = new Header( Header.COOKIE, sessionCookie.toString() );
		
		//
		// Find the specified project area
		//
		rqmProject = (Project)executeAction( ValidateProjectArea.NAME, getSessionMap() );
		
		//
		// Check if our adapter is registered
		//
		Map<String,Object> parameterMap = getSessionMap();
		parameterMap.put(CheckRegistration.PROJECT_AREA, rqmProject );
		String adapterUrl = (String) executeAction( CheckRegistration.NAME,  parameterMap );
		
		if ( adapterUrl == null )
		{
			if ( log.isInfoEnabled() )
				log.info( "Adapter is not registered - Attempting to Register" );
			
			//
			// Load the service catalog
			//
			parameterMap = getSessionMap();
			parameterMap.put(LoadCatalog.PROJECT_AREA, rqmProject );
			String spUrl = (String) executeAction( LoadCatalog.NAME, parameterMap );
			
			//
			// The spUrl contains the project ID.  Extract that for later
			//
			int servicesIndex = spUrl.indexOf( "/services.xml" );
			int projectMarker = spUrl.lastIndexOf( "/", servicesIndex - 1 );
			rqmProject.setId( spUrl.substring( projectMarker + 1 ).split( "/" )[ 0 ] );
			
			//
			// Find the automation adapter registration URL
			//
			parameterMap = getSessionMap();
			parameterMap.put(LoadServiceProvider.SP_URL, spUrl );
			String automationAdapter = (String) executeAction( LoadServiceProvider.NAME, parameterMap );
			
			//
			// Find the result creation URL
			//
			parameterMap = getSessionMap();
			parameterMap.put(LoadServiceProvider.SP_URL, spUrl );
			resultUrl = (String) executeAction( LoadResultCreationFactory.NAME, parameterMap );
			
			//
			// Register the Adapter
			//
			parameterMap = getSessionMap();
			parameterMap.put(RegisterAdapter.AUTO_ADAPTER, automationAdapter );
			executeAction( RegisterAdapter.NAME, parameterMap );
			
			
			//
			// Check if our adapter is registered
			//
			parameterMap = getSessionMap();
			parameterMap.put(CheckRegistration.PROJECT_AREA, rqmProject );
			adapterUrl = (String) executeAction( CheckRegistration.NAME,  parameterMap );
			
			if ( adapterUrl == null )
				throw new IllegalStateException( "The adapter registration was successful however the adapter could not be found" );
		}
		else
		{
			//
			// Load the service catalog
			//
			parameterMap = getSessionMap();
			parameterMap.put(LoadCatalog.PROJECT_AREA, rqmProject );
			String spUrl = (String) executeAction( LoadCatalog.NAME, parameterMap );
			
			//
			// The spUrl contains the project ID.  Extract that for later
			//
			int servicesIndex = spUrl.indexOf( "/services.xml" );
			int projectMarker = spUrl.lastIndexOf( "/", servicesIndex - 1 );
			rqmProject.setId( spUrl.substring( projectMarker + 1 ).split( "/" )[ 0 ] );
			
			//
			// Find the result creation URL
			//
			parameterMap = getSessionMap();
			parameterMap.put(LoadServiceProvider.SP_URL, spUrl );
			resultUrl = (String) executeAction( LoadResultCreationFactory.NAME, parameterMap );
		}
		
		parameterMap = getSessionMap();
		parameterMap.put(AdapterInformation.ADAPTER_URL, adapterUrl );
		AdapterModel adapterModel = (AdapterModel) executeAction( AdapterInformation.NAME,  parameterMap );
		
		adapterInstructionsUrl = adapterModel.getInstructions();
		adapterTasksUrl = adapterModel.getTasks();
		adapterId = adapterModel.getId();
		
	}
	
	
	
	private Map<String,Object> getSessionMap()
	{
		HashMap<String,Object> sessionMap = new HashMap<String,Object>( 5 );
		sessionMap.put( Header.COOKIE, sessionId );
		return sessionMap;
	}
	
	private Object executeAction( String actionName )
	{
		return executeAction( actionName, null );
	}
	
	private Object executeAction( String actionName, Map<String,Object> actionParameters )
	{
		if ( log.isDebugEnabled() )
			log.debug( "Executing Action " + actionName + " using [" + actionParameters +"]" );
		AdapterAction adapterAction = actionMap.get( actionName );
		if ( adapterAction != null )
			return adapterAction.performAction( actionParameters );
		else
			throw new IllegalArgumentException( "Unknown Adapter Action [" + actionName + "]");
	}
	
	public AdapterAction getAction( String actionName )
	{
		return actionMap.get( actionName );
	}
	
	public void registerAdapterAction( AdapterAction adapterAction )
	{
		actionMap.put(  adapterAction.getName(), adapterAction );
		adapterAction.setRQMAdapter( this );
	}
	
	private void registerDefaultActions() throws Exception
	{
		//
		// Initialization Actions
		//
		registerAdapterAction( new Login( rqmUrl, rqmUserName, rqmPassword ) );
		registerAdapterAction( new ValidateProjectArea( rqmUrl, projectArea ) );
		registerAdapterAction( new CheckRegistration( rqmUrl, adapterName ) );
		registerAdapterAction( new LoadCatalog( rootModel.getAutoCatalog() ) );
		registerAdapterAction( new LoadServiceProvider() );
		registerAdapterAction( new LoadResultCreationFactory() );
		registerAdapterAction( new RegisterAdapter( adapterName, "Perfecto Mobile Automation Adapter", pollInterval, adapterType ) );
		registerAdapterAction( new AdapterInformation( adapterName ) );
		
		//
		// Running Actions
		//
		registerAdapterAction( new GetInstructions() );
		registerAdapterAction( new GetTasks() );
		registerAdapterAction( new HandleTask() );
		registerAdapterAction( new ImportTask() );
		registerAdapterAction( new ExecuteTask( cloudUrl, cloudUserName, cloudPassword, attachmentFolder, rqmUrl ) );
		
		//
		// Cloud based actions
		//
		registerAdapterAction( new RepositoryList( cloudUrl, cloudUserName, cloudPassword ) );
		registerAdapterAction( new HandsetList( cloudUrl, cloudUserName, cloudPassword ) );
		registerAdapterAction( new ExecuteScript( cloudUrl, cloudUserName, cloudPassword ) );
	}
	
	
	public static void main( String[] args ) throws Exception
	{
		SSLUtils.disableSsl();
		
		RQMAdapter rqmAdapter = new RQMAdapter();
		//rqmAdapter.execute();
		rqmAdapter.start();
		rqmAdapter.join();
	}
	
}
