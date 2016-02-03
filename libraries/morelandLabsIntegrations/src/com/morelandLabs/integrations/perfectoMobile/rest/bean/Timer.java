package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

/**
 * The Class Action.
 */
@BeanDescriptor( beanName="timer" )
public class Timer extends AbstractBean
{

	@Override
	public String toString()
	{
		return "Timer [name=" + name + ", elapsed=" + elapsed + ", device=" + device + ", system=" + system + ", ux=" + ux + "]";
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public Double getElapsed()
	{
		return elapsed;
	}

	public void setElapsed( Double elapsed )
	{
		this.elapsed = elapsed;
	}

	public Double getDevice()
	{
		return device;
	}

	public void setDevice( Double device )
	{
		this.device = device;
	}

	public Boolean getSystem()
	{
		return system;
	}

	public void setSystem( Boolean system )
	{
		this.system = system;
	}

	public String getUx()
	{
		return ux;
	}

	public void setUx( String ux )
	{
		this.ux = ux;
	}

	@FieldDescriptor ( fieldPath = "@id")
	private String name;
	
	@FieldDescriptor ( fieldPath = "time[@label='elapsed']")
	private Double elapsed;
	
	@FieldDescriptor ( fieldPath = "time[@label='device']")
	private Double device;

	@FieldDescriptor ( fieldPath = "time[@label='system']")
	private Boolean system;
	
	@FieldDescriptor ( fieldPath = "time[@label='ux']")
	private String ux;

	

}
