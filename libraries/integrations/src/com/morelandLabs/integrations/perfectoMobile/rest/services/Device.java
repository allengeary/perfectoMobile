package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.common.Location;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.ApplicationCollection;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Execution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.Operation;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.Parameter;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.PerfectoCommand;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ResourceID;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

// TODO: Auto-generated Javadoc
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
	

    /**
     * Call.
     *
     * @param executionId the execution id
     * @param handsetId the handset id
     * @return the execution
     */
    @Operation( operationName="command" )
    @PerfectoCommand( commandName="gateway", subCommandName = "call" )
    public Execution call( @ResourceID String executionId, @Parameter( name="to.handset" ) String handsetId );
    
    /**
     * Call.
     *
     * @param executionId the execution id
     * @param handsetId the handset id
     * @return the execution
     */
    @Operation( operationName="command" )
    @PerfectoCommand( commandName="gateway", subCommandName = "sms" )
    public Execution sendText( @ResourceID String executionId, @NameOverride( name="body" )String body, @Parameter( name="to.handset" ) String handsetId );
    
    
    @Operation( operationName="command" )
    @PerfectoCommand( commandName="browser", subCommandName = "execute" )
    public Execution clean( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId  );
	
    
    
    @Operation( operationName="command" )
    @PerfectoCommand( commandName="logs", subCommandName = "start" )
    public Execution startDebug( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId );
    
    @Operation( operationName="command" )
    @PerfectoCommand( commandName="logs", subCommandName = "stop" )
    public Execution stopDebug( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId );

}
