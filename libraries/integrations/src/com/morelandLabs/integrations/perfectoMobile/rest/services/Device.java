package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.common.Location;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Execution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

/**
 * The Interface Device.
 */
@ServiceDescriptor( serviceName="executions" )
public interface Device extends PerfectoService
{
	
	/**
	 * The Enum ScreenOrientation.
	 */
	public enum ScreenOrientation
	{
		
		/** The portrait. */
		portrait,
		
		/** The landscape. */
		landscape;
	}
	
	/**
	 * Rotate.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param state the state
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="device", subCommandName = "rotate" )
	public Execution rotate( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId, @Parameter( name="state" ) ScreenOrientation state );
	
	/**
	 * Reboot.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="device", subCommandName = "reboot" )
	public Execution reboot( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId );
	
	/**
	 * Recover.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="device", subCommandName = "recover" )
	public Execution recover( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId );
	
	/**
	 * Open.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="device", subCommandName = "open" )
	public Execution open( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId );
	
	/**
	 * Close.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="device", subCommandName = "close" )
	public Execution close( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId );
	
	/**
	 * Sets the location.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param coordinates the coordinates
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="location", subCommandName = "set" )
	public Execution setLocation( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId, @Parameter( name="coordinates" ) Location coordinates );
}
