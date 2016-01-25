package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.ItemCollection;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

/**
 * The Interface Repositories.
 */
@ServiceDescriptor( serviceName="repositories" )
public interface Repositories extends PerfectoService
{
	
	/**
	 * The Enum RepositoryType.
	 */
	public enum RepositoryType
	{
		
		/** The media. */
		MEDIA,
		
		/** The datatables. */
		DATATABLES,
		
		/** The scripts. */
		SCRIPTS
	}
	
	
	/**
	 * Gets the repositorys.
	 *
	 * @param rType the r type
	 * @return the repositorys
	 */
	@Operation( operationName="list" )
	public ItemCollection getRepositorys( @ResourceID RepositoryType rType );

}
