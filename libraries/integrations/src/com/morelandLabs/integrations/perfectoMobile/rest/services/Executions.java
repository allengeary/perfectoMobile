package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.Execution;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.ExecutionCollection;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.ExecutionResult;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

// TODO: Auto-generated Javadoc
/**
 * The Interface Executions.
 */
@ServiceDescriptor( serviceName="executions" )
public interface Executions extends PerfectoService
{
	
	/**
	 * The Enum FlowEndCode.
	 */
	public enum FlowEndCode
	{
		
		/** The Success. */
		Success,
		
		/** The Failed. */
		Failed,
		
		/** The User aborted. */
		UserAborted;
	}
	
	/**
	 * The Enum TimeType.
	 */
	public enum TimeType
	{
		
		/** The started. */
		started,
		
		/** The completed. */
		completed;
	}
	
	/**
	 * Execute.
	 *
	 * @param scriptKey the script key
	 * @param DUT the dut
	 * @return the execution
	 */
	@Operation( operationName="execute" )
	public Execution execute( String scriptKey, @Parameter String DUT );
	
	/**
	 * Status.
	 *
	 * @param executionId the execution id
	 * @return the execution result
	 */
	@Operation( operationName="status" )
	public ExecutionResult status( @ResourceID String executionId );
	
	/**
	 * End execution.
	 *
	 * @param executionId the execution id
	 * @return the execution
	 */
	@Operation( operationName="end" )
	public Execution endExecution( @ResourceID String executionId );
	
	/**
	 * Start execution.
	 *
	 * @return the execution
	 */
	@Operation( operationName="start" )
	public Execution startExecution();
	
	/**
	 * Gets the executions.
	 *
	 * @param completed the completed
	 * @param admin the admin
	 * @param flowEndCode the flow end code
	 * @param timeType the time type
	 * @param timeAnchor the time anchor
	 * @param timeOffset the time offset
	 * @return the executions
	 */
	@Operation( operationName="list" )
	public ExecutionCollection getExecutions( Boolean completed, Boolean admin, FlowEndCode flowEndCode, @NameOverride( name="time.type" ) TimeType timeType, @NameOverride( name="time.anchor") Long timeAnchor, @NameOverride( name="time.offset" ) Short timeOffset );
	
}
