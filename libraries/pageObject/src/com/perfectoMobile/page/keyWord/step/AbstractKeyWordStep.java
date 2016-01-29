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
import org.openqa.selenium.WebDriver;

import com.morelandLabs.spi.PropertyProvider;
import com.morelandLabs.spi.driver.DeviceProvider;
import com.morelandLabs.spi.driver.NativeDriverProvider;
import com.perfectoMobile.content.ContentManager;
import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.keyWord.KeyWordParameter;
import com.perfectoMobile.page.keyWord.KeyWordStep;
import com.perfectoMobile.page.keyWord.KeyWordToken;
import com.perfectoMobile.page.keyWord.step.spi.KWSLoopBreak;

/**
 * The Class AbstractKeyWordStep.
 */
public abstract class AbstractKeyWordStep implements KeyWordStep
{
	private static final String EXECUTION_ID = "EXECUTION_ID";
	private static final String DEVICE_NAME = "DEVICE_NAME";

	private String name;
	private String pageName;
	private boolean active;
	private String linkId;
	private boolean timed;
	private StepFailure sFailure;
	private boolean inverse = false;
	private static final String SPLIT = "-->";
	private boolean fork;
	private String os;
	private String context;
	private String validation;
	private ValidationType validationType;
	
	public String getContext()
	{
		return context;
	}

	public void setContext( String context )
	{
		this.context = context;
	}

	private List<KeyWordParameter> parameterList = new ArrayList<KeyWordParameter>( 10 );
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
	
	@Override
	public String getOs()
	{
		return os;
	}
	
	@Override
	public void setOs( String os )
	{
		this.os = os != null ? os.toUpperCase() : os;
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
		if ( name.startsWith( Element.CONTEXT_ELEMENT ) )
		{
			if (Element.CONTEXT_ELEMENT.equals( name ))
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
				String elementName = name.split( SPLIT )[ 1 ];
				
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
					log.info( "Cloning Element " + name + " on page " + pageName );
				Element clonedElement = pageObject.getElement( pageName, name ).cloneElement();
				
				for ( KeyWordToken token : tokenList )
				{
					clonedElement.addToken( token.getName(), getTokenValue( token, contextMap, dataMap ) );
				}
				
				return clonedElement;
			}
			else
				return pageObject.getElement( pageName, name );
		}
	}
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.keyWord.KeyWordStep#executeStep(com.perfectoMobile.page.Page, org.openqa.selenium.WebDriver, java.util.Map, java.util.Map)
	 */
	public boolean executeStep( Page pageObject, WebDriver webDriver, Map<String, Object> contextMap, Map<String, PageData> dataMap ) throws Exception
	{
		long startTime = System.currentTimeMillis();
		boolean passedIgnore = false;
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

			boolean returnValue = _executeStep( pageObject, webDriver, contextMap, dataMap );

			if (inverse)
				returnValue = !returnValue;

			
			//
			// If there are sub steps and this was successful, then execute
			// those. If we are in fork mode, then skip that execution
			//
			if ( !fork && getStepList() != null && !getStepList().isEmpty() && returnValue)
			{
				for (KeyWordStep step : getStepList())
				{
					passedIgnore = true;
					step.executeStep( pageObject, webDriver, contextMap, dataMap );
				}
			}

			if ( isRecordable() )
				PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, returnValue, "", null );
			
			if (!returnValue)
			{
				switch (sFailure)
				{
					case ERROR:
						throw new IllegalStateException( toError() );

					case IGNORE:
						return true;

					case LOG_IGNORE:
						log.warn( "Step " + name + " failed but was marked to log and ignore" );
						return true;
				}
			}

			return returnValue;

		}
		catch( KWSLoopBreak lb )
		{
			throw lb;
		}
		catch (Exception e)
		{
			switch (sFailure)
			{
				case ERROR:
					PageManager.instance().setThrowable( e );
					PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, false, e.getMessage(), e );
					log.error( "***** Step " + name + " on page " + pageName + " failed due to " + e.getMessage() );
					throw e;

				case IGNORE:
					if ( passedIgnore )
					{
						PageManager.instance().setThrowable( e );
						PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, false, e.getMessage(), e );
						log.error( "***** Step " + name + " on page " + pageName + " failed due to " + e.getMessage() );
						throw e;
					}
					else
					{
						PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, false, e.getMessage(), null );
						return true;
					}

				case LOG_IGNORE:
					PageManager.instance().addExecutionLog( getExecutionId( webDriver ), getDeviceName( webDriver ), getPageName(), getName(), getClass().getSimpleName(), startTime, System.currentTimeMillis() - startTime, false, e.getMessage(), null );
					log.warn( "Step " + name + " failed due to " + e.getMessage() + " but was marked to log and ignore" );
					return true;
			}

			return false;
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
		String executionId = null;
		
		if ( webDriver instanceof PropertyProvider )
		{
			executionId = ( (PropertyProvider) webDriver ).getProperty( EXECUTION_ID );
		}
		
		if ( executionId == null )
		{
			if ( webDriver instanceof HasCapabilities )
			{
				Capabilities caps = ( (HasCapabilities) webDriver ).getCapabilities();
				executionId = caps.getCapability( "executionId" ).toString();
			}
		}
		
		if ( executionId == null )
		{
			if ( webDriver instanceof NativeDriverProvider )
			{
				WebDriver nativeDriver = ( (NativeDriverProvider) webDriver ).getNativeDriver();
				if ( nativeDriver instanceof HasCapabilities )
				{
					Capabilities caps = ( (HasCapabilities) webDriver ).getCapabilities();
					executionId = caps.getCapability( "executionId" ).toString();
				}
			}
		}
		
		if ( executionId == null )
			log.warn( "No Execution ID could be located" );
		
		return executionId;
	}
	
	/**
	 * Gets the execution id.
	 *
	 * @param webDriver the web driver
	 * @return the execution id
	 */
	public String getDeviceOs( WebDriver webDriver )
	{
		String os = null;
		
		if ( webDriver instanceof DeviceProvider )
		{
			os = ( (DeviceProvider) webDriver ).getDevice().getOs().toUpperCase();
		}
		
		if ( os == null )
		{
			if ( webDriver instanceof HasCapabilities )
			{
				Capabilities caps = ( (HasCapabilities) webDriver ).getCapabilities();
				os = caps.getCapability( "os" ).toString();
			}
		}
		
		if ( os == null )
		{
			if ( webDriver instanceof NativeDriverProvider )
			{
				WebDriver nativeDriver = ( (NativeDriverProvider) webDriver ).getNativeDriver();
				if ( nativeDriver instanceof HasCapabilities )
				{
					Capabilities caps = ( (HasCapabilities) webDriver ).getCapabilities();
					os = caps.getCapability( "os" ).toString();
				}
			}
		}
		
		if ( os == null )
			log.warn( "No OS could be located" );
		
		return os;
	}
	
	/**
	 * Gets the device name.
	 *
	 * @param webDriver the web driver
	 * @return the device name
	 */
	public String getDeviceName( WebDriver webDriver )
	{
		String executionId = null;
		
		if ( webDriver instanceof PropertyProvider )
		{
			executionId = ( (PropertyProvider) webDriver ).getProperty( DEVICE_NAME );
		}
		
		if ( executionId == null )
		{
			if ( webDriver instanceof HasCapabilities )
			{
				Capabilities caps = ( (HasCapabilities) webDriver ).getCapabilities();
				executionId = caps.getCapability( "deviceName" ).toString();
			}
		}
		
		if ( executionId == null )
		{
			if ( webDriver instanceof NativeDriverProvider )
			{
				WebDriver nativeDriver = ( (NativeDriverProvider) webDriver ).getNativeDriver();
				if ( nativeDriver instanceof HasCapabilities )
				{
					Capabilities caps = ( (HasCapabilities) webDriver ).getCapabilities();
					executionId = caps.getCapability( "deviceName" ).toString();
				}
			}
		}
		
		if ( executionId == null )
			log.warn( "No Execution ID could be located" );
		
		return executionId;
	}
	
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
					return dataValue.matches( validation );
				}

			case NOT_EMPTY:
				return dataValue != null && !dataValue.isEmpty();
		}
		
		return true;
	}

	public String getValidation()
	{
		return validation;
	}

	public void setValidation( String validation )
	{
		this.validation = validation;
	}

	public ValidationType getValidationType()
	{
		return validationType;
	}

	public void setValidationType( ValidationType validationType )
	{
		this.validationType = validationType;
	}
}
