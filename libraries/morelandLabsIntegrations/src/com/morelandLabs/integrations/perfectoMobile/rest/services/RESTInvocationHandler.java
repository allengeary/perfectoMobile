package com.morelandLabs.integrations.perfectoMobile.rest.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.NameOverride;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.Operation;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.Parameter;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ParameterMap;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.PerfectoCommand;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ResourceID;
import com.morelandLabs.integrations.perfectoMobile.rest.services.PerfectoService.ServiceDescriptor;
import com.morelandLabs.integrations.rest.bean.Bean;
import com.morelandLabs.integrations.rest.bean.factory.BeanManager;

/**
 * The Class RESTInvocationHandler.
 */
public class RESTInvocationHandler implements InvocationHandler
{
	private static final String SLASH = "/";
	private static final String TYPE = "TYPE";
	private static final String PARAM = "param.";
	
	private Log log = LogFactory.getLog( RESTInvocationHandler.class );
	
	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	public Object invoke( Object proxy, Method method, Object[] args ) throws Throwable
	{
		StringBuilder urlBuilder = new StringBuilder();
		
		urlBuilder.append( PerfectoMobile.instance().getBaseUrl() );
		
		if ( !PerfectoMobile.instance().getBaseUrl().endsWith( SLASH ) )
			urlBuilder.append( SLASH );
		
		urlBuilder.append( "services/" );
		
		ServiceDescriptor serviceDescriptor = proxy.getClass().getInterfaces()[0].getAnnotation( ServiceDescriptor.class );
		if ( serviceDescriptor == null )
			throw new IllegalArgumentException( "Service Descriptor was NOT found" );
		
		urlBuilder.append( serviceDescriptor.serviceName() );
		
		String parameterId = "";
		Map parameterMap = null;
		Map<String,String> derivedMap = new HashMap<String,String>( 10 );
		
		Annotation[][] parameterAnnotations = method.getParameterAnnotations();
		
		for ( int i=0; i<method.getParameterTypes().length; i++  )
		{
			if ( getAnnotation( parameterAnnotations[ i ], ResourceID.class ) != null )
				parameterId = parameterId + SLASH + args[ i ] + "";
			else if ( getAnnotation( parameterAnnotations[ i ], ParameterMap.class ) != null )
				parameterMap = (Map) parameterMap;
			else if ( getAnnotation( parameterAnnotations[ i ], Parameter.class ) != null )
			{
				if ( args[ i ] != null )
				{
					Parameter paramAnnotation = (Parameter) getAnnotation( parameterAnnotations[ i ], Parameter.class );
					if ( !paramAnnotation.name().isEmpty() )
						derivedMap.put( PARAM + method.getParameterTypes()[ i ].getName(), args[ i ] + "" );
					else
						derivedMap.put( PARAM + paramAnnotation.name(), args[ i ] + "" );
				}
			}
			else
			{
				if ( args[ i ] != null )
				{
					String parameterName = method.getParameterTypes()[ i ].getName();
					NameOverride namedParameter = (NameOverride) getAnnotation( parameterAnnotations[ i ], NameOverride.class );
					if ( namedParameter != null && !namedParameter.name().isEmpty() ) 
						parameterName = namedParameter.name();

					derivedMap.put( parameterName, args[ i ] + "" );
				}
			}
		}
		
		if ( !parameterId.isEmpty() )
			urlBuilder.append( parameterId );
		
		Method actualMethod = findMethod( proxy.getClass().getInterfaces()[0], method.getName(), args );
		
		Operation op = actualMethod.getAnnotation( Operation.class );
		if ( op == null )
			throw new IllegalArgumentException( "Operation was NOT found" );
		
		urlBuilder.append( "?operation=" ).append( op.operationName() );
		urlBuilder.append( "&user=" ).append( PerfectoMobile.instance().getUserName() );
		urlBuilder.append( "&password=" ).append( PerfectoMobile.instance().getPassword() );
		
		PerfectoCommand command = actualMethod.getAnnotation( PerfectoCommand.class );
		if ( command != null )
		{
			urlBuilder.append( "&command=" ).append( command.commandName() );
			if ( command.subCommandName() != null && !command.subCommandName().isEmpty())
				urlBuilder.append( "&subcommand=" ).append( command.subCommandName() );
		}
		
		urlBuilder.append( "&responseFormat=" ).append( PerfectoMobile.instance().getResponseMethod() );
		
		for ( String name : derivedMap.keySet() )
			urlBuilder.append( "&" ).append( name ).append( "=" ).append( URLEncoder.encode( derivedMap.get( name ), "UTF-8" ) );
		
		if ( parameterMap != null )
		{
			for ( Object name : parameterMap.keySet() )
				urlBuilder.append( "&" ).append( name ).append( "=" ).append( URLEncoder.encode( parameterMap.get( name ) + "", "UTF-8" ) );
		}
		
		URL currentUrl = new URL( urlBuilder.toString() );
		
		if ( log.isInfoEnabled() )
			log.info( "Submitting REST call as " + urlBuilder.toString() );
		
		if ( method.getReturnType().isAssignableFrom( byte[].class ) )
		{
			//
			// Byte Array is a special case that is not wrapped in XML
			//
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[ 512 ];
			int bytesRead= 0;
			InputStream inputStream = currentUrl.openStream();
			
			while ( ( bytesRead = inputStream.read( buffer ) ) > 0 )
			{
				outputStream.write( buffer, 0, bytesRead );
			}
			
			return outputStream.toByteArray();
		}
		
		try
		{
			Bean newBean = BeanManager.instance().createBean( method.getReturnType(), currentUrl.openStream() );
			return newBean;
		}
		catch( IOException e )
		{
			log.error( "Could not connect to the cloud instance - Verify your user name, password and Cloud URL", e );
			return null;
		}
		
		
	}
	
	private Annotation getAnnotation( Annotation[] annotationArray, Class annotationType )
	{
		for ( Annotation ant : annotationArray )
		{
			if ( annotationType.isAssignableFrom( ant.getClass() ) )
				return ant;
		}
		
		return null;
	}
	
	/**
	 * Gets the url.
	 *
	 * @param currentUrl the current url
	 * @return the url
	 */
	public byte[] getUrl( URL currentUrl )
	{
		if ( log.isInfoEnabled() )
			log.info( "Executing " + currentUrl.toString() );
		InputStream inputStream = null;
		try
		{
			ByteArrayOutputStream resultBuilder = new ByteArrayOutputStream();
			inputStream = currentUrl.openStream();
			byte[] buffer = new byte[ 1024 ];
			int bytesRead = 0;
			
			while ( ( bytesRead = inputStream.read( buffer ) ) > 0 )
				resultBuilder.write( buffer, 0, bytesRead );
			
			return resultBuilder.toByteArray();
		}
		catch( Exception e )
		{
			log.error( "Error performing GET request", e );
			return null;
		}
		finally
		{
			try { inputStream.close(); } catch( Exception e ) {}
		}
		
	}
	
	private Method findMethod( Class rootClass, String methodName, Object[] args )
    {
    	Method[] methodArray = rootClass.getMethods();
    	
    	for ( Method currentMethod : methodArray )
		{
			if ( isCorrectMethod(currentMethod, methodName, args) )
			{
				if ( log.isInfoEnabled() )
					log.info( "Found [" +methodName + "] on " + rootClass.getName() );
				
				if ( log.isDebugEnabled() && args != null )
				{
					StringBuilder pBuilder = new StringBuilder();
					
					pBuilder.append( args.length ).append(" paramters supplied as: \r\n" );
					
					for( Object arg : args )
					{
						pBuilder.append( "\t" );
						if ( arg == null )
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
    	
    	if ( log.isWarnEnabled() )
		{
			StringBuilder pBuilder = new StringBuilder();
			pBuilder.append( "Could not locate ").append( methodName ).append( " with ");
			pBuilder.append( args.length ).append(" paramters supplied as: \r\n" );
			
			for( Object arg : args )
			{
				pBuilder.append( "\t" );
				if ( arg == null )
					pBuilder.append( "NULL" );
				else
					pBuilder.append( "[" + arg.toString() + "] of type " + arg.getClass().getName() );
				pBuilder.append( "\r\n" );
				
			}
			log.warn( pBuilder.toString() );
		}
		return null;
    	
    	
    }
	
	private boolean isCorrectMethod( Method compareMethod, String methodName, Object[] parameterArray )
    {
        if ( !methodName.equals( compareMethod.getName() ) )
            return false;

        if ( (parameterArray == null || parameterArray.length == 0) && (compareMethod.getParameterTypes() == null || compareMethod.getParameterTypes().length == 0) )
            return true;

        if ( parameterArray == null || compareMethod.getParameterTypes() == null )
            return false;

        Class[] parameterTypes = compareMethod.getParameterTypes();

        if ( parameterTypes.length != parameterArray.length )
        {
            if ( log.isDebugEnabled() ) log.debug( "Paramter Count Mismatch " + parameterTypes.length + " - " + parameterArray.length );
            return false;
        }

        for(int i = 0; i < parameterArray.length; i++)
        {
            if ( log.isDebugEnabled() ) log.debug( parameterTypes[i] + " - " + parameterArray[i] );
            if ( !isInstance( parameterTypes[i], parameterArray[i] ) )
                return false;
        }

        return true;
    }
    
    private boolean isInstance( Class classType, Object value )
    {
        try
        {
            if ( classType.isPrimitive() )
            {
                if ( value == null )
                {
                    if ( log.isDebugEnabled() )
                    	log.debug( "Primative value null" );
                    return false;
                }
                else
                {
                    Field typeField = value.getClass().getField( TYPE );
                    return classType.isAssignableFrom( (Class) typeField.get( value ) );
                }
            }
            else
                return (value == null || classType.isInstance( value ));
        }
        catch( Exception e )
        {
        	log.error( "Error getting instance", e );
            return false;
        }
    }
	

}
