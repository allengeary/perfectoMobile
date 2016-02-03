package com.morelandLabs.integrations.perfectoMobile.rest.bean;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import com.morelandLabs.integrations.rest.bean.AbstractBean;
import com.morelandLabs.integrations.rest.bean.Bean.BeanDescriptor;
import com.morelandLabs.integrations.rest.bean.Bean.FieldCollection;
import com.morelandLabs.integrations.rest.bean.factory.XMLBeanFactory;

/**
 * The Class Action.
 */
@BeanDescriptor( beanName="action" )
public class Action extends AbstractBean
{
	

	

	

	@Override
	public String toString()
	{
		return "Action [name=" + name + ", startTime=" + startTime + ", endTime=" + endTime + ", success=" + success + ", failureDescription=" + failureDescription + ", timerList=" + timerList + "]";
	}

	public List<Timer> getTimerList()
	{
		return timerList;
	}

	public void setTimerList( List<Timer> timerList )
	{
		this.timerList = timerList;
	}

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
	 * Gets the run time.
	 *
	 * @return the run time
	 */
	public long getRunTime()
	{
		return (endTime - startTime);
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
	
	/**
	 * Gets the success.
	 *
	 * @return the success
	 */
	public Boolean getSuccess()
	{
		if ( success == null )
			success = false;
		return success;
	}

	/**
	 * Sets the success.
	 *
	 * @param success the new success
	 */
	public void setSuccess( Boolean success )
	{
		this.success = success;
	}

	/**
	 * Gets the failure description.
	 *
	 * @return the failure description
	 */
	public String getFailureDescription()
	{
		return failureDescription;
	}

	/**
	 * Sets the failure description.
	 *
	 * @param failureDescription the new failure description
	 */
	public void setFailureDescription( String failureDescription )
	{
		this.failureDescription = failureDescription;
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main( String[] args ) throws Exception
	{
		XMLBeanFactory x = new XMLBeanFactory();
		System.out.println( x.createBean( ExecutionReport.class, new FileInputStream( "C:/Users/Allen/Downloads/DiscoverNative_v1_16-01-13_16_57_13_3718.xml") ) );
	}

}
