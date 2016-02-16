package com.perfectoMobile.page.keyWord.step;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.HasCapabilities;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.services.WindTunnel.Status;
import com.morelandLabs.spi.PropertyProvider;
import com.morelandLabs.spi.driver.DeviceProvider;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.content.ContentManager;
import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.PageManager.StepStatus;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.keyWord.KeyWordParameter;
import com.perfectoMobile.page.keyWord.KeyWordStep;
import com.perfectoMobile.page.keyWord.KeyWordToken;
import com.perfectoMobile.page.keyWord.step.spi.KWSLoopBreak;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractKeyWordStep.
 */
public abstract class AbstractKeyWordStep implements KeyWordStep
{

	/** The name. */
	private String name;
	
	/** The page name. */
	private String pageName;
	
	/** The active. */
	private boolean active;
	
	/** The link id. */
	private String linkId;
	
	/** The timed. */
	private boolean timed;
	
	/** The s failure. */
	private StepFailure sFailure;
	
	/** The inverse. */
	private boolean inverse = false;
	
	/** The Constant SPLIT. */
	private static final String SPLIT = "-->";
	
	/** The fork. */
	private boolean fork;
	
	/** The os. */
	private String os;
	
	/** The context. */
	private String context;
	
	/** The validation. */
	private String validation;
	
	/** The threshold. */
	private int threshold;
	
	/** The description. */
	private String description;
	
	/** The validation type. */
	private ValidationType validationType;
	
	/** The poi. */
	private String poi;
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getPoi()
	 */
	public String getPoi() {
		return poi;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setPoi(java.lang.String)
	 */
	public void setPoi(String poi) {
		this.poi = poi;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getContext()
	 */
	public String getContext()
	{
		return context;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setContext(java.lang.String)
	 */
	public void setContext( String context )
	{
		this.context = context;
	}

	/** The parameter list. */
	private List<KeyWordParameter> parameterList = new ArrayList<KeyWordParameter>( 10 );
	
	/** The token list. */
	private List<KeyWordToken> tokenList = new ArrayList<KeyWordToken>( 10 );
	
	/** The log. */
	protected Log log = LogFactory.getLog( AbstractKeyWordStep.class );

	/**
	 * _execute step.
	 *
	 * @param pageObject the page object
	 * @param webDriver the web driver
	 * @param contextMap the context map
	 * @param dataMap the data map
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	protected abstract boolean _executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap ) throws Exception;

	
	/**
	 * Creates the point.
	 *
	 * @param pointValue the point value
	 * @return the point
	 */
	protected Point createPoint( String pointValue )
	{
		Point x = null;
		
		try
		{
			String[] coors = pointValue.split( "," );
			
			if ( coors.length == 2 )
			{
				x = new Point( Integer.parseInt( coors[ 0 ].trim() ), Integer.parseInt( coors[ 1 ].trim() ) );
				return x;
			}
		}
		catch( Exception e )
		{
			log.warn( "Could not parse coordinates " +  pointValue + " due to " + e.getMessage() );
		}
		
		return null;
		
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#toError()
	 */
	public String toError()
	{
		return getClass().getSimpleName() + " returned false";
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#isFork()
	 */
	public boolean isFork()
	{
		return fork;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getOs()
	 */
	@Override
	public String getOs()
	{
		return os;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setOs(java.lang.String)
	 */
	@Override
	public void setOs( String os )
	{
		this.os = os != null ? os.toUpperCase() : os;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getThreshold()
	 */
	public int getThreshold() {
		return threshold;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setThreshold(int)
	 */
	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getDescription()
	 */
	public String getDescription() {
		return description;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setDescription(java.lang.String)
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * Checks if is recordable.
	 *
	 * @return true, if is recordable
	 */
	public boolean isRecordable()
	{
		return true;
	}


	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setFork(boolean)
	 */
	public void setFork( boolean fork )
	{
		this.fork = fork;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#isInverse()
	 */
	public boolean isInverse()
	{
		return inverse;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setInverse(boolean)
	 */
	public void setInverse( boolean inverse )
	{
		this.inverse = inverse;
	}

	/** The step list. */
	private List<KeyWordStep> stepList = new ArrayList<KeyWordStep>( 10 );

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#isTimed()
	 */
	public boolean isTimed()
	{
		return timed;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setTimed(boolean)
	 */
	public void setTimed( boolean timed )
	{
		this.timed = timed;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getFailure()
	 */
	@Override
	public StepFailure getFailure()
	{
		return sFailure;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setFailure(com.perfectoMobile.page.keyWord.KeyWordStep.StepFailure)
	 */
	@Override
	public void setFailure( StepFailure sFailure )
	{
		this.sFailure = sFailure;

	}

	/**
	 * Gets the element.
	 *
	 * @param pageObject the page object
	 * @param contextMap the context map
	 * @param webDriver the web driver
	 * @param dataMap the data map
	 * @return the element
	 */
	protected Element getElement( Page pageObject, Map<String, Object> contextMap, Object webDriver, Map<String,PageData> dataMap )
	{
		return getElement( pageObject, contextMap, webDriver, dataMap, null );
	}
	
	/**
	 * Gets the element.
	 *
	 * @param pageObject the page object
	 * @param contextMap the context map
	 * @param webDriver the web driver
	 * @param dataMap the data map
	 * @param overrideName the override name
	 * @return the element
	 */
	protected Element getElement( Page pageObject, Map<String, Object> contextMap, Object webDriver, Map<String,PageData> dataMap, String overrideName )
	{
		String useName = name;
		if ( overrideName != null && !overrideName.isEmpty() )
			useName = overrideName;
		
		if ( useName.startsWith( Element.CONTEXT_ELEMENT ) )
		{
			if (Element.CONTEXT_ELEMENT.equals( useName ))
			{
				if (log.isDebugEnabled())
					log.debug( "Attempting to acquire CONTEXT element" );
	
				Element currentElement = ( Element ) contextMap.get( Element.CONTEXT_ELEMENT );
	
				if (log.isDebugEnabled())
					log.debug( "CONTEXT element found as " + currentElement );
	
				return currentElement;
			}
			else
			{
				String elementName = useName.split( SPLIT )[ 1 ];
				
				if (log.isDebugEnabled())
					log.debug( "Attempting to acquire CONTEXT element" );
	
				Element currentElement = ( Element ) contextMap.get( Element.CONTEXT_ELEMENT );
	
				if (log.isDebugEnabled())
					log.debug( "CONTEXT element found as " + currentElement );
				
				ElementDescriptor elementDescriptor = new ElementDescriptor( PageManager.instance().getSiteName(), getPageName(), elementName );
				Element myElement = PageManager.instance().getElementProvider().getElement( elementDescriptor );
				
				if ( myElement == null )
				{
					log.error( "**** COULD NOT LOCATE ELEMENT [" + elementDescriptor.toString() + "]  Make sure your Page Name and Element Name are spelled correctly and that they have been defined"  );
					return null;
				}
				
				myElement.setDriver( webDriver );
				myElement.setContext( currentElement );
				return myElement;
			}
		}
		else
		{
			if ( tokenList != null && !tokenList.isEmpty() )
			{
				if ( log.isInfoEnabled() )
					log.info( "Cloning Element " + useName + " on page " + pageName );
				Element clonedElement = pageObject.getElement( pageName, useName ).cloneElement();
				
				for ( KeyWordToken token : tokenList )
				{
					clonedElement.addToken( token.getName(), getTokenValue( token, contextMap, dataMap ) );
				}
				
				return clonedElement;
			}
			else
				return pageObject.getElement( pageName, useName );
		}
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	public boolean executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap ) throws Exception
	{
		PageManager.instance().setThrowable( null );
		long startTime = System.currentTimeMillis();

		try
		{
			//
			// OS Checks to allow for elements to be skipped if the OS did not match
			//
			if ( os != null )
			{
				String deviceOs = getDeviceOs( webDriver );
				if ( deviceOs == null )
				{
					if ( log.isInfoEnabled() )
						log.info( "A Required OS of [" + os + "] was specified however the OS of the device could not be determined" );
					return true;
				}
				if ( !os.equals( deviceOs.toUpperCase() ) )
				{
					if ( log.isInfoEnabled() )
						log.info( "A Required OS of [" + os + "] was specified however the OS of the device was [" + deviceOs.toUpperCase() + "]" );
					return true;
				}
			}
			
			if (log.isInfoEnabled())
				log.info( "*** Executing Step " + name + " of type " + getClass().getSimpleName() + ( linkId != null ? " linked to " + linkId : "" ) );

			Exception stepException = null;
			boolean returnValue = false;
			try
			{
				returnValue = _executeStep( pageObject, webDriver, contextMap, dataMap );
			}
			catch( KWSLoopBreak lb )
			{
				throw lb;
			}
			catch( Exception e )
			{
				stepException = e;
				returnValue = false;
			}

			if (inverse)
				returnValue = !returnValue;

			
			//
			// If there are sub steps and this was successful, then execute
			// those. If we are in fork mode, then skip that execution
			//
			if ( !fork && getStepList() != null && !getStepList().isEmpty() && returnValue)
			{
				boolean subReturnValue = false;
				for (KeyWordStep step : getStepList())
				{					
					try
					{
						subReturnValue = step.executeStep( pageObject, webDriver, contextMap, dataMap );
					}
					catch( KWSLoopBreak e )
					{
						throw e;
					}
					catch( Exception e )
					{
						stepException = e;
						subReturnValue = false;
					}
					
					if ( step.isInverse() )
						subReturnValue = !subReturnValue;
					
					if ( !subReturnValue )
					{
						returnValue = false;
						break;
					}
					
				}
			}

			if ( isRecordable() )
			{
				PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, returnValue ? StepStatus.SUCCESS : StepStatus.FAILURE, "", null, getThreshold(), getDescription() );
				if ( isTimed() )
					PageManager.instance().addExecutionTiming(getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName() + "." + getName() + "." + getClass().getSimpleName(), System.currentTimeMillis() - startTime, returnValue ? StepStatus.SUCCESS : StepStatus.FAILURE, description, threshold);
			}
			
			if ( PageManager.instance().isWindTunnelEnabled() && getPoi() != null && !getPoi().isEmpty() )
				PerfectoMobile.instance().windTunnel().addPointOfInterest( getExecutionId( webDriver ), getPoi() + "(" + getPageName() + "." + getName() + ")", returnValue ? Status.success :Status.failure);
			
			if (!returnValue)
			{
				switch (sFailure)
				{
					case ERROR:
						if ( PageManager.instance().getThrowable() == null )
						{
							if ( stepException == null )
								stepException = new IllegalArgumentException( toError() );
	
							PageManager.instance().setThrowable( stepException );						
							PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, StepStatus.FAILURE, stepException.getMessage(), stepException, getThreshold(), getDescription() );
							
							if ( isTimed() )
								PageManager.instance().addExecutionTiming(getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName() + "." + getName() + "." + getClass().getSimpleName(), System.currentTimeMillis() - startTime, StepStatus.FAILURE, description, threshold);
							
							if ( PageManager.instance().isWindTunnelEnabled() && getPoi() != null && !getPoi().isEmpty() )
								PerfectoMobile.instance().windTunnel().addPointOfInterest( getExecutionId( webDriver ), getPoi() + "(" + getPageName() + "." + getName() + ")", Status.failure);
						}
						log.error( "***** Step " + name + " on page " + pageName + " failed as " + PageManager.instance().getThrowable().getMessage() );
						return false;
						
					case LOG_IGNORE:
						log.warn( "Step " + name + " failed but was marked to log and ignore" );
						
					case IGNORE:
						if ( PageManager.instance().getThrowable() == null )
						{
							if ( stepException == null )
								stepException = new IllegalArgumentException( toError() );
	
							PageManager.instance().setThrowable( stepException );						
							PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, StepStatus.FAILURE_IGNORED, stepException.getMessage(), stepException, getThreshold(), getDescription() );
							
							if ( isTimed() )
								PageManager.instance().addExecutionTiming(getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName() + "." + getName() + "." + getClass().getSimpleName(), System.currentTimeMillis() - startTime, StepStatus.FAILURE, description, threshold);
							
							if ( PageManager.instance().isWindTunnelEnabled() && getPoi() != null && !getPoi().isEmpty() )
								PerfectoMobile.instance().windTunnel().addPointOfInterest( getExecutionId( webDriver ), getPoi() + "(" + getPageName() + "." + getName() + ")", Status.failure);
						}
						return true;

					
				}
			}

			return returnValue;

		}
		catch( KWSLoopBreak lb )
		{
			throw lb;
		}
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setName(java.lang.String)
	 */
	public void setName( String name )
	{
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setPageName(java.lang.String)
	 */
	public void setPageName( String pageName )
	{
		this.pageName = pageName;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setActive(boolean)
	 */
	public void setActive( boolean active )
	{
		this.active = active;
	}

	/**
	 * Gets the parameter list.
	 *
	 * @return the parameter list
	 */
	public List<KeyWordParameter> getParameterList()
	{
		return parameterList;
	}

	/**
	 * Gets the token list.
	 *
	 * @return the token list
	 */
	public List<KeyWordToken> getTokenList()
	{
		return tokenList;
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getStepList()
	 */
	public List<KeyWordStep> getStepList()
	{
		return stepList;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getLinkId()
	 */
	public String getLinkId()
	{
		return linkId;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setLinkId(java.lang.String)
	 */
	public void setLinkId( String linkId )
	{
		this.linkId = linkId;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#addParameter(com.perfectoMobile.page.keyWord.KeyWordParameter)
	 */
	public void addToken( KeyWordToken token )
	{
		if (log.isInfoEnabled())
			log.info( "Adding Token " + token.getValue() );
		tokenList.add( token );
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#addParameter(com.perfectoMobile.page.keyWord.KeyWordParameter)
	 */
	public void addParameter( KeyWordParameter param )
	{
		if (log.isInfoEnabled())
			log.info( "Adding Parameter " + param.getValue() );
		parameterList.add( param );
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#addStep(com.perfectoMobile.page.keyWord.KeyWordStep)
	 */
	public void addStep( KeyWordStep step )
	{
		if (log.isInfoEnabled())
			log.info( "Adding Sub Step " + step.getName() );
		stepList.add( step );
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#addAllSteps(com.perfectoMobile.page.keyWord.KeyWordStep[])
	 */
	public void addAllSteps( KeyWordStep[] step )
	{
		if (step != null)
		{
			for (KeyWordStep s : step)
				addStep( s );
		}
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getName()
	 */
	public String getName()
	{
		return name;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#isActive()
	 */
	public boolean isActive()
	{
		return active;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getPageName()
	 */
	public String getPageName()
	{
		return pageName;
	}

	/**
	 * Gets the parameter value.
	 *
	 * @param param the param
	 * @param contextMap the context map
	 * @param dataMap the data map
	 * @return the parameter value
	 */
	protected Object getParameterValue( KeyWordParameter param, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		switch (param.getType())
		{
			case CONTEXT:
				return contextMap.get( param.getValue() );

			case STATIC:
				return param.getValue();

			case PROPERTY:
				return System.getProperty( param.getValue(), "" );
				
			case CONTENT:
				return ContentManager.instance().getContentValue( param.getValue() );

			case DATA:
				String[] recordParts = param.getValue().split( "\\." );
				if (recordParts.length != 2)
					throw new IllegalArgumentException( "Parameters of type data need to be formatted as follows recordType.fieldName" );

				PageData pageData = dataMap.get( recordParts[0] );
				if ( pageData == null )
				{
					throw new IllegalArgumentException( "The Page Data record type [" + recordParts[0] + "] does not exist for this test - chexk your dataProvider or dataDriver attribute" );
				}
				
				
				Object returnValue = pageData.getData( recordParts[1] );
				
				if ( returnValue == null )
					throw new IllegalArgumentException( "The Page Data field [" + recordParts[1] + "] does not exist for the page data record type [" + recordParts[0] + "] - Reference one of the following fields - " + pageData );
				
				return returnValue;

			default:
				throw new IllegalArgumentException( "Unknown Parameter Type [" + param.getValue() + "]" );
		}
	}
	
	/**
	 * Gets the parameter value.
	 *
	 * @param token the token
	 * @param contextMap the context map
	 * @param dataMap the data map
	 * @return the parameter value
	 */
	protected String getTokenValue( KeyWordToken token, Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		switch (token.getType())
		{
			case CONTEXT:
				return contextMap.get( token.getValue() ) + "";

			case STATIC:
				return token.getValue();

			case PROPERTY:
				return System.getProperty( token.getValue(), "" );
				
			case CONTENT:
				return ContentManager.instance().getContentValue( token.getValue() + "" );

			case DATA:
				String[] recordParts = token.getValue().split( "\\." );
				if (recordParts.length != 2)
					throw new IllegalArgumentException( "Tokens of type data need to be formatted as follows recordType.fieldName" );

				return dataMap.get( recordParts[0] ).getData( recordParts[1] );

			default:
				throw new IllegalArgumentException( "Unknown Token Type [" + token.getValue() + "]" );
		}
	}

	/**
	 * Gets the parameters.
	 *
	 * @param contextMap the context map
	 * @param dataMap the data map
	 * @return the parameters
	 */
	protected Object[] getParameters( Map<String, Object> contextMap, Map<String, PageData> dataMap )
	{
		Object[] parameterArray = new Object[parameterList.size()];

		for (int i = 0; i < parameterList.size(); i++)
		{
			parameterArray[i] = getParameterValue( parameterList.get( i ), contextMap, dataMap );
		}

		return parameterArray;
	}

	/**
	 * Find method.
	 *
	 * @param rootClass the root class
	 * @param methodName the method name
	 * @param args the args
	 * @return the method
	 */
	protected Method findMethod( Class rootClass, String methodName, Object[] args )
	{
		Method[] methodArray = rootClass.getMethods();

		for (Method currentMethod : methodArray)
		{
			if (isCorrectMethod( currentMethod, methodName, args ))
			{
				if (log.isInfoEnabled())
					log.info( "Found [" + methodName + "] on " + rootClass.getName() );

				if (log.isDebugEnabled() && args != null)
				{
					StringBuilder pBuilder = new StringBuilder();

					pBuilder.append( args.length ).append( " paramters supplied as: \r\n" );

					for (Object arg : args)
					{
						pBuilder.append( "\t" );
						if (arg == null)
							pBuilder.append( "NULL" );
						else
							pBuilder.append( "[" + arg.toString() + "] of type " + arg.getClass().getName() );
						pBuilder.append( "\r\n" );

					}
					log.debug( pBuilder.toString() );
				}

				return currentMethod;
			}
		}

		if (log.isWarnEnabled())
		{
			StringBuilder pBuilder = new StringBuilder();
			pBuilder.append( "Could not locate " ).append( methodName ).append( " with " );
			pBuilder.append( args.length ).append( " paramters supplied as: \r\n" );

			for (Object arg : args)
			{
				pBuilder.append( "\t" );
				if (arg == null)
					pBuilder.append( "NULL" );
				else
					pBuilder.append( "[" + arg.toString() + "] of type " + arg.getClass().getName() );
				pBuilder.append( "\r\n" );

			}
			log.warn( pBuilder.toString() );
		}
		return null;

	}

	/**
	 * Checks if is correct method.
	 *
	 * @param compareMethod the compare method
	 * @param methodName the method name
	 * @param parameterArray the parameter array
	 * @return true, if is correct method
	 */
	protected boolean isCorrectMethod( Method compareMethod, String methodName, Object[] parameterArray )
	{
		if (!methodName.equals( compareMethod.getName() ))
			return false;

		if (( parameterArray == null || parameterArray.length == 0 ) && ( compareMethod.getParameterTypes() == null || compareMethod.getParameterTypes().length == 0 ))
			return true;

		if (parameterArray == null || compareMethod.getParameterTypes() == null)
			return false;

		Class[] parameterTypes = compareMethod.getParameterTypes();

		if (parameterTypes.length != parameterArray.length)
		{
			if (log.isDebugEnabled())
				log.debug( "Paramter Count Mismatch " + parameterTypes.length + " - " + parameterArray.length );
			return false;
		}

		for (int i = 0; i < parameterArray.length; i++)
		{
			if (log.isDebugEnabled())
				log.debug( parameterTypes[i] + " - " + parameterArray[i] );
			if (!isInstance( parameterTypes[i], parameterArray[i] ))
				return false;
		}

		return true;
	}

	/**
	 * Checks if is instance.
	 *
	 * @param classType the class type
	 * @param value the value
	 * @return true, if is instance
	 */
	protected boolean isInstance( Class classType, Object value )
	{
		try
		{
			if (classType.isPrimitive())
			{
				if (value == null)
				{
					if (log.isDebugEnabled())
						log.debug( "Primative value null" );
					return false;
				}
				else
				{
					Field typeField = value.getClass().getField( TYPE );
					return classType.isAssignableFrom( ( Class ) typeField.get( value ) );
				}
			}
			else
				return ( value == null || classType.isInstance( value ) );
		}
		catch (Exception e)
		{
			log.error( "Error getting instance", e );
			return false;
		}
	}
	
	/**
	 * Gets the execution id.
	 *
	 * @param webDriver the web driver
	 * @return the execution id
	 */
	public String getExecutionId( WebDriver webDriver )
	{
		return PageManager.instance().getExecutionId(webDriver);
	}
	
	/**
	 * Gets the execution id.
	 *
	 * @param webDriver the web driver
	 * @return the execution id
	 */
	public String getDeviceOs( WebDriver webDriver )
	{
		return PageManager.instance().getDeviceOs(webDriver);
	}
	
	/**
	 * Gets the device name.
	 *
	 * @param webDriver the web driver
	 * @return the device name
	 */
	public String getDeviceName( WebDriver webDriver )
	{
		return PageManager.instance().getDeviceName(webDriver);
	}
	
	/**
	 * Validate data.
	 *
	 * @param dataValue the data value
	 * @return true, if successful
	 */
	protected boolean validateData( String dataValue )
	{
		if ( validationType == null )
			return true;
		
		switch( validationType )
		{
			case EMPTY:
				return dataValue == null || dataValue.isEmpty();
				
			case REGEX:
				if ( dataValue == null )
				{
					log.warn( "REGEX validation specified with a blank value" );
					return true;
				}
				else
				{
					log.info( "Attempting to analyze [" + dataValue + "] using the Regular Expression [" + validation + "]" );
					if ( !dataValue.matches( validation ) )
					{
						log.error( "Validation failed for [" + dataValue + "] using the Regular Expression [" + validation + "]" );
						return false;
					}
					return true;
				}

			case NOT_EMPTY:
				return dataValue != null && !dataValue.isEmpty();
		}
		
		return true;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getValidation()
	 */
	public String getValidation()
	{
		return validation;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setValidation(java.lang.String)
	 */
	public void setValidation( String validation )
	{
		this.validation = validation;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#getValidationType()
	 */
	public ValidationType getValidationType()
	{
		return validationType;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#setValidationType(com.perfectoMobile.page.keyWord.KeyWordStep.ValidationType)
	 */
	public void setValidationType( ValidationType validationType )
	{
		this.validationType = validationType;
	}
}
