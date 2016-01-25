package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.ApplicationCollection;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Execution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

/**
 * The Interface Application.
 */
@ServiceDescriptor( serviceName="executions" )
public interface Application extends PerfectoService
{
	
	
	/**
	 * Install.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param file the file
	 * @param instrument the instrument
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="application", subCommandName = "install" )
	public Execution install( @ResourceID String executionId, @Parameter String handsetId, @Parameter String file, @Parameter String instrument  );
	
	/**
	 * Uninstall.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param name the name
	 * @param identifier the identifier
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="application", subCommandName = "uninstall" )
	public Execution uninstall( @ResourceID String executionId, @Parameter String handsetId, @Parameter String name, @Parameter String identifier  );

	/**
	 * Open.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param name the name
	 * @param identifier the identifier
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="application", subCommandName = "open" )
	public Execution open( @ResourceID String executionId, @Parameter String handsetId, @Parameter String name, @Parameter String identifier );
	
	/**
	 * Close.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param name the name
	 * @param identifier the identifier
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="application", subCommandName = "close" )
	public Execution close( @ResourceID String executionId, @Parameter String handsetId, @Parameter String name, @Parameter String identifier );
	
	/**
	 * Uninstall all.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @return the execution
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="application", subCommandName = "reset" )
	public Execution uninstallAll( @ResourceID String executionId, @Parameter String handsetId  );
	
	/**
	 * Find.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @return the application collection
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="application", subCommandName = "find" )
	public ApplicationCollection find( @ResourceID String executionId, @Parameter String handsetId  );
	
	/**
	 * Clean.
	 *
	 * @param executionId the execution id
	 * @param handsetId the handset id
	 * @param name the name
	 * @param identifier the identifier
	 * @return the application collection
	 */
	@Operation( operationName="command" )
	@PerfectoCommand( commandName="application", subCommandName = "clean" )
	public ApplicationCollection clean( @ResourceID String executionId, @Parameter String handsetId, @Parameter String name, @Parameter String identifier  );
	
}
