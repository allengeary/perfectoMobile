package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import java.util.ArrayList;
import java.util.List;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

/**
 * The Class Action.
 */
@BeanDescriptor( beanName="script" )
public class Script extends AbstractBean
{
	@Override
	public String toString()
	{
		return "Script [name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + ", success=" + success + ", failureDescription=" + failureDescription + ", timerList=" + timerList + "]";
	}


	public String getName()
	{
		return name;
	}


	public void setName( String name )
	{
		this.name = name;
	}


	public Long getStartTime()
	{
		return startTime;
	}


	public void setStartTime( Long startTime )
	{
		this.startTime = startTime;
	}


	public Long getEndTime()
	{
		return endTime;
	}


	public void setEndTime( Long endTime )
	{
		this.endTime = endTime;
	}


	public Boolean getSuccess()
	{
		return success;
	}


	public void setSuccess( Boolean success )
	{
		this.success = success;
	}


	public String getFailureDescription()
	{
		return failureDescription;
	}


	public void setFailureDescription( String failureDescription )
	{
		this.failureDescription = failureDescription;
	}


	public List<Timer> getTimerList()
	{
		return timerList;
	}


	public void setTimerList( List<Timer> timerList )
	{
		this.timerList = timerList;
	}


	@FieldDescriptor ( fieldPath = "info/name")
	private String name;
	
	@FieldDescriptor ( fieldPath = "info/times/flowTimes/start/millis")
	private Long startTime;
	
	@FieldDescriptor ( fieldPath = "info/times/flowTimes/end/millis")
	private Long endTime;

	@FieldDescriptor ( fieldPath = "output/status/success")
	private Boolean success;
	
	@FieldDescriptor ( fieldPath = "output/status/description")
	private String failureDescription;

	
	@FieldCollection ( fieldElement = Timer.class, fieldPath = "output/timers")
	private List<Timer> timerList = new ArrayList<Timer>( 10 );

}
