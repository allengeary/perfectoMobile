package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.ImageExecution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

/**
 * The Interface Imaging.
 */
@ServiceDescriptor( serviceName="executions" )
public interface Imaging extends PerfectoService
{
	
	/**
	 * The Enum MatchMode.
	 */
	public enum MatchMode
	{
		
		/** The identical. */
		identical,
		
		/** The similar. */
		similar,
		
		/** The fine. */
		fine,
		
		/** The bounded. */
		bounded;
	}
	
	/**
	 * Text exists.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param content the content
	 * @param timeout the timeout
	 * @return the image execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="checkpoint", subCommandName = "text" )
	public ImageExecution textExists( @ResourceID String executionId, @Parameter String handsetId, @Parameter String content, @Parameter Short timeout  );
	
	
	/**
	 * Image exists.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param content the content
	 * @param timeout the timeout
	 * @param match the match
	 * @return the image execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="checkpoint", subCommandName = "image" )
	public ImageExecution imageExists( @ResourceID String executionId, @Parameter String handsetId, @Parameter String content, @Parameter Short timeout, @Parameter MatchMode match );
	
}
