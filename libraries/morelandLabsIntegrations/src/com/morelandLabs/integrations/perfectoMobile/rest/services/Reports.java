package com.morelandLabs.integrations.perfectoMobile.rest.services;

import com.morelandLabs.integrations.perfectoMobile.rest.bean.ExecutionReport;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;

/**
 * The Interface Reports.
 */
@ServiceDescriptor( serviceName="reports" )
public interface Reports extends PerfectoService
{
	
	/**
	 * The Enum ReportFormat.
	 */
	public enum ReportFormat
	{
		
		/** The xml. */
		xml,
		
		/** The csv. */
		csv,
		
		/** The pdf. */
		pdf,
		
		/** The html. */
		html;
	}
	
	/**
	 * Download.
	 *
	 * @param reportKey the report key
	 * @param format the format
	 * @return the execution report
	 */
	@Operation( operationName="download" )
	public ExecutionReport download( @ResourceID String reportKey, ReportFormat format );
	
}
