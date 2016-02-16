package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Class GestureExecution.
 */
@BeanDescriptor( beanName="response" )
public class GestureExecution extends AbstractBean
{
	
	/** The execution id. */
	@FieldDescriptor ( )
	private String executionId;
	
	/** The report key. */
	@FieldDescriptor ( )
	private String reportKey;

	/** The reason. */
	@FieldDescriptor ( )
	private String reason;

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
	 * Gets the reason.
	 *
	 * @return the reason
	 */
	public String getReason()
	{
		return reason;
	}

	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus( String status )
	{
		this.reason = status;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "Execution [executionId=" + executionId + ", reportKey=" + reportKey + ", reason=" + reason + "]";
	}
	
	
	

}
