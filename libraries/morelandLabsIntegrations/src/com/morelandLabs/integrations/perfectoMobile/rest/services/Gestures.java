package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.common.PercentagePoint;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Execution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

/**
 * The Interface Gestures.
 */
@ServiceDescriptor( serviceName="executions" )
public interface Gestures extends PerfectoService
{
	
	
	/**
	 * Swipe.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param start the start
	 * @param end the end
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="touch", subCommandName = "swipe" )
	public Execution swipe( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId, @Parameter( name="start" ) PercentagePoint start, @Parameter( name="end" ) PercentagePoint end  );
	
	/**
	 * Zoom.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param start the start
	 * @param end the end
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="touch", subCommandName = "gesture" )
	public Execution zoom( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId, @Parameter( name="start" ) PercentagePoint start, @Parameter( name="end" ) PercentagePoint end  );

	/**
	 * Pinch.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param start the start
	 * @param end the end
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="touch", subCommandName = "gesture" )
	public Execution pinch( @ResourceID String executionId, @Parameter( name="handsetId" ) String handsetId, @Parameter( name="start" ) PercentagePoint start, @Parameter( name="end" ) PercentagePoint end  );
	
}
