package com.perfectoMobile.page;

import com.perfectoMobile.page.PageManager.StepStatus;

// TODO: Auto-generated Javadoc
/**
 * The Class ExecutionRecord.
 */
public class ExecutionRecord
{
	
	/** The group. */
	private String group;
	
	/** The name. */
	private String name;
	
	/** The type. */
	private String type;
	
	/** The time stamp. */
	private long timeStamp;
	
	/** The run time. */
	private long runTime;
	
	/** The status. */
	private StepStatus status;
	
	/** The t. */
	private Throwable t;
	
	/**
	 * Instantiates a new execution record.
	 *
	 * @param group the group
	 * @param name the name
	 * @param type the type
	 * @param timeStamp the time stamp
	 * @param runTime the run time
	 * @param status the status
	 * @param detail the detail
	 * @param t the t
	 */
	public ExecutionRecord( String group, String name, String type, long timeStamp, long runTime, StepStatus status, String detail, Throwable t )
	{
		super();
		this.group = group;
		this.name = name;
		this.type = type;
		this.timeStamp = timeStamp;
		this.runTime = runTime;
		this.status = status;
		this.detail = detail;
		this.t = t;
	}
	
	/**
	 * Gets the t.
	 *
	 * @return the t
	 */
	public Throwable getT()
	{
		return t;
	}

	/**
	 * Sets the t.
	 *
	 * @param t the new t
	 */
	public void setT( Throwable t )
	{
		this.t = t;
	}

	/**
	 * Checks if is status.
	 *
	 * @return true, if is status
	 */
	public StepStatus getStatus()
	{
		return status;
	}
	
	/**
	 * Sets the status.
	 *
	 * @param status the new status
	 */
	public void setStatus( StepStatus status )
	{
		this.status = status;
	}

	/** The detail. */
	private String detail;
	
	/**
	 * Gets the group.
	 *
	 * @return the group
	 */
	public String getGroup()
	{
		return group;
	}
	
	/**
	 * Sets the group.
	 *
	 * @param group the new group
	 */
	public void setGroup( String group )
	{
		this.group = group;
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
	 * Gets the type.
	 *
	 * @return the type
	 */
	public String getType()
	{
		return type;
	}
	
	/**
	 * Sets the type.
	 *
	 * @param type the new type
	 */
	public void setType( String type )
	{
		this.type = type;
	}
	
	/**
	 * Gets the time stamp.
	 *
	 * @return the time stamp
	 */
	public long getTimeStamp()
	{
		return timeStamp;
	}
	
	/**
	 * Sets the time stamp.
	 *
	 * @param timeStamp the new time stamp
	 */
	public void setTimeStamp( long timeStamp )
	{
		this.timeStamp = timeStamp;
	}
	
	/**
	 * Gets the run time.
	 *
	 * @return the run time
	 */
	public long getRunTime()
	{
		return runTime;
	}
	
	/**
	 * Sets the run time.
	 *
	 * @param runTime the new run time
	 */
	public void setRunTime( long runTime )
	{
		this.runTime = runTime;
	}
	
	/**
	 * Gets the detail.
	 *
	 * @return the detail
	 */
	public String getDetail()
	{
		return detail;
	}
	
	/**
	 * Sets the detail.
	 *
	 * @param detail the new detail
	 */
	public void setDetail( String detail )
	{
		this.detail = detail;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString()
	{
		return group + "," + name + "," + type + "," + timeStamp + "," + runTime + "," + status + "," + detail;
	}
	
	/**
	 * To html.
	 *
	 * @return the string
	 */
	public String toHTML()
	{
		StringBuffer stringBuffer = new StringBuffer();
		
		switch( status )
		{
			case FAILURE:
				stringBuffer.append( "<tr bgcolor='#ff3333'>" );
				break;
			case FAILURE_IGNORED:
				stringBuffer.append( "<tr bgcolor='#F5DEB3'>" );
				break;
				
			case SUCCESS:
				stringBuffer.append( "<tr bgcolor='#66FF99'>" );
				break;
		}
		
			
		stringBuffer.append( "<td>" ).append( group ).append( "</td>" );
		stringBuffer.append( "<td>" ).append( name ).append( "</td>" );
		stringBuffer.append( "<td>" ).append( type ).append( "</td>" );
		stringBuffer.append( "<td>" ).append( timeStamp ).append( "</td>" );
		stringBuffer.append( "<td>" ).append( runTime ).append( "</td>" );
		stringBuffer.append( "<td>" ).append( status ).append( "</td>" );
		stringBuffer.append( "</tr>" );
		if ( !status.equals( StepStatus.SUCCESS ) && detail != null && !detail.isEmpty() )
		{
			String backgroundColor = null;
			switch( status )
			{
				case FAILURE:
					backgroundColor = "#ff3333";
					break;
				case FAILURE_IGNORED:
					backgroundColor = "#F5DEB3";
					break;
					
				case SUCCESS:
					backgroundColor = "#66FF99";
					break;
			}
			
			
			stringBuffer.append( "<tr><td></td><td colSpan='5' color='" + backgroundColor + "'><font size='1'>" ).append( detail ).append( "</font></td></tr>");
			if ( t != null )
			{
				stringBuffer.append( "<tr><td></td><td colSpan='5' color='" + backgroundColor + "'><b><font size='1'>" );
				
				stringBuffer.append( t.getMessage() ).append( "<br/>");
				t.fillInStackTrace();
				for( StackTraceElement s : t.getStackTrace() )
					stringBuffer.append( "&nbsp;&nbsp;&nbsp;&nbsp;").append( s.toString() ).append( "<br/>");
				stringBuffer.append( "</font></b></td></tr>");
				
			}
		}
		
		
		return stringBuffer.toString();
	}
	
}

