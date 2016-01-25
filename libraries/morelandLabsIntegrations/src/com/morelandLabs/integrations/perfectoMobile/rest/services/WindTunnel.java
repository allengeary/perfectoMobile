package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.Execution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

/**
 * The Interface WindTunnel.
 */
@ServiceDescriptor( serviceName="executions" )
public interface WindTunnel extends PerfectoService
{
	
	/**
	 * The Enum Network.
	 */
	public enum Network
	{
		
		/** The wifi. */
		wifi,
		
		/** The data. */
		data,
		
		/** The airplanemode. */
		airplanemode;
	}
	
	/**
	 * The Enum TimerPolicy.
	 */
	public enum TimerPolicy
	{
		
		/** The reset. */
		reset,
		
		/** The noreset. */
		noreset;
	}
	
	
	/**
	 * Adds the point of interest.
	 *
	 * @param executionId the execution id
	 * @param description the description
	 * @param status the status
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="status", subCommandName = "event" )
	public Execution addPointOfInterest( @ResourceID String executionId, @Parameter String description, @Parameter boolean status );
	
	/**
	 * Adds the timer report.
	 *
	 * @param executionId the execution id
	 * @param name the name
	 * @param result the result
	 * @param status the status
	 * @param description the description
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="status", subCommandName = "event" )
	public Execution addTimerReport( @ResourceID String executionId, @Parameter String name, @Parameter int result, @Parameter boolean status, @Parameter String description );
	
	/**
	 * Gets the network setting.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param property the property
	 * @return the network setting
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="status", subCommandName = "event" )
	public Execution getNetworkSetting( @ResourceID String executionId, @Parameter String handsetId, @Parameter Network property );
	
	/**
	 * Configure network.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param wifi the wifi
	 * @param data the data
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="status", subCommandName = "event" )
	public Execution configureNetwork( @ResourceID String executionId, @Parameter String handsetId, @Parameter boolean wifi, @Parameter boolean data );
	
	/**
	 * Start network virtualization.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param latency the latency
	 * @param packetLoss the packet loss
	 * @param packetCorruption the packet corruption
	 * @param packetReordering the packet reordering
	 * @param packetDuplication the packet duplication
	 * @param delayJitter the delay jitter
	 * @param correlation the correlation
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="status", subCommandName = "event" )
	public Execution startNetworkVirtualization( @ResourceID String executionId, @Parameter String handsetId, @Parameter int latency, @Parameter int packetLoss, @Parameter int packetCorruption, @Parameter int packetReordering, @Parameter int packetDuplication, @Parameter int delayJitter, @Parameter int correlation  );
	
	/**
	 * Stop network virtualization.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="status", subCommandName = "event" )
	public Execution stopNetworkVirtualization( @ResourceID String executionId, @Parameter String handsetId );
	
	/**
	 * Start timer.
	 *
	 * @param executionId the execution id
	 * @param timerId the timer id
	 * @param initPolicy the init policy
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="timer", subCommandName = "start" )
	public Execution startTimer( @ResourceID String executionId, @Parameter String timerId, @Parameter TimerPolicy initPolicy );
	
	/**
	 * Stop timer.
	 *
	 * @param executionId the execution id
	 * @param timerId the timer id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="timer", subCommandName = "stop" )
	public Execution stopTimer( @ResourceID String executionId, @Parameter String timerId );
	
	/**
	 * Start vitals.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param timerId the timer id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="monitor", subCommandName = "start" )
	public Execution startVitals( @ResourceID String executionId, @Parameter String handsetId, @Parameter String timerId );
}
