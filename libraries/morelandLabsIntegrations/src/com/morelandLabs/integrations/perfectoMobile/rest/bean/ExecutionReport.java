package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import java.util.ArrayList;
import java.util.List;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;

/**
 * The Class ExecutionReport.
 */
@BeanDescriptor ( beanName = "execution")
public class ExecutionReport extends AbstractBean
{
	@FieldDescriptor ( fieldPath = "info/name")
	private String name;
	@FieldDescriptor ( fieldPath = "info/times/flowTimes/start/millis")
	private Long startTime;
	@FieldDescriptor ( fieldPath = "info/times/flowTimes/end/millis")
	private Long endTime;

	@FieldCollection ( fieldElement = Action.class, fieldPath = "flow/")
	private List<Action> actionList = new ArrayList<Action>( 10 );
	
	@FieldCollection ( fieldElement = Script.class, fieldPath = "flow/")
	private List<Script> scriptList = new ArrayList<Script>( 10 );

	/**
	 * Gets the name.
	 *
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Sets the name.
	 *
	 * @param name the new name
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/**
	 * Gets the run time.
	 *
	 * @return the run time
	 */
	public long getRunTime()
	{
		return ( endTime - startTime );
	}

	/**
	 * Gets the start time.
	 *
	 * @return the start time
	 */
	public Long getStartTime()
	{
		return startTime;
	}

	/**
	 * Sets the start time.
	 *
	 * @param startTime the new start time
	 */
	public void setStartTime( Long startTime )
	{
		this.startTime = startTime;
	}

	/**
	 * Gets the end time.
	 *
	 * @return the end time
	 */
	public Long getEndTime()
	{
		return endTime;
	}

	/**
	 * Sets the end time.
	 *
	 * @param endTime the new end time
	 */
	public void setEndTime( Long endTime )
	{
		this.endTime = endTime;
	}

	/**
	 * Gets the action list.
	 *
	 * @return the action list
	 */
	public List<Action> getActionList()
	{
		return actionList;
	}

	/**
	 * Sets the action list.
	 *
	 * @param actionList the new action list
	 */
	public void setActionList( List<Action> actionList )
	{
		this.actionList = actionList;
	}

	public List<Script> getScriptList()
	{
		return scriptList;
	}

	public void setScriptList( List<Script> scriptList )
	{
		this.scriptList = scriptList;
	}

	@Override
	public String toString()
	{
		return "ExecutionReport [name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + ", actionList=" + actionList + ", scriptList=" + scriptList + "]";
	}

	

}
