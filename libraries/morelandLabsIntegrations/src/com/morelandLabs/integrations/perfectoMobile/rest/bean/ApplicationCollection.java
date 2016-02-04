package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.morelandLabs.integrations.perfectoMobile.rest.services.Application;
import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

/**
 * The Class ApplicationCollection.
 */
@BeanDescriptor( beanName="response" )
public class ApplicationCollection extends AbstractBean
{
	@FieldDescriptor ( )
	private String executionId;
	@FieldDescriptor ( )
	private String reportKey;
	@FieldDescriptor ( )
	private String status;
	@FieldDescriptor ( )
	private String returnValue;
	
	private Map<String,String> applicationMap;

	/**
	 * Gets the execution id.
	 *
	 * @return the execution id
	 */
	public String getExecutionId()
	{
		return executionId;
	}

	/**
	 * Sets the execution id.
	 *
	 * @param executionId the new execution id
	 */
	public void setExecutionId( String executionId )
	{
		this.executionId = executionId;
	}

	/**
	 * Gets the report key.
	 *
	 * @return the report key
	 */
	public String getReportKey()
	{
		return reportKey;
	}

	/**
	 * Sets the report key.
	 *
	 * @param reportKey the new report key
	 */
	public void setReportKey( String reportKey )
	{
		this.reportKey = reportKey;
	}

	/**
	 * Gets the status.
	 *
	 * @return the status
	 */
	public String getStatus()
	{
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus( String status )
	{
		this.status = status;
	}

	/**
	 * Gets the return value.
	 *
	 * @return the return value
	 */
	public String getReturnValue()
	{
		return returnValue;
	}

	/**
	 * Sets the return value.
	 *
	 * @param returnValue the new return value
	 */
	public void setReturnValue( String returnValue )
	{
		this.returnValue = returnValue;
	}
	
	/**
	 * Gets the applications.
	 *
	 * @return the applications
	 */
	public Collection<String> getApplications()
	{
		if ( applicationMap == null )
			parseApplications();
		return applicationMap.values();
	}
	
	
	private void parseApplications()
	{
		applicationMap = new HashMap<String,String>( 20 );
		
		String workingValue = returnValue;
		workingValue = workingValue.replace( '[', ' ' ).replace( ']', ' ' ).trim();
		String[] allApps = workingValue.split( "," );
		
		for ( String app : allApps )
		{
			String trimmedApp = app.trim();
			applicationMap.put( trimmedApp.toLowerCase(), trimmedApp );
		}
	}
	
	/**
	 * Gets the application.
	 *
	 * @param applicationName the application name
	 * @return the application
	 */
	public String getApplication( String applicationName )
	{
		if ( applicationMap == null )
			parseApplications();
		
		return applicationMap.get( applicationName.toLowerCase() );
	}

	
	
	
	

}
