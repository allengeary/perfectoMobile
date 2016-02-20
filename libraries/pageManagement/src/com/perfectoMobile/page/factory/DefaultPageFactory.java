/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.perfectoMobile.page.factory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.PageManager;




// TODO: Auto-generated Javadoc
/**
 * A factory for creating DefaultPage objects.
 *
 * @author ageary
 */
public class DefaultPageFactory extends LocalPageFactory implements InvocationHandler
{

    /** The Constant TYPE. */
    private static final String TYPE = "TYPE";

    /* (non-Javadoc)
     * @see com.perfectoMobile.page.factory.LocalPageFactory#_createPage(java.lang.Class, java.lang.Object)
     */
    @Override
    protected Page _createPage(Class<Page> serviceInterface, Object webDriver ) 
    {
    	
    	this.webDriver = webDriver;
    	if ( serviceInterface == null )
    		throw new IllegalArgumentException( "You are attempting to create a page for non existent Page name - make sure you page name is defined properly and matches case" );
    	
    	if ( log.isInfoEnabled() )
    		log.info( "Attempting to create PROXY interface by " + serviceInterface );
    	
    	return (Page) Proxy.newProxyInstance( this.getClass().getClassLoader(), new Class[] { serviceInterface, Page.class }, this );
    }
    
    /**
     * Find method.
     *
     * @param rootClass the root class
     * @param methodName the method name
     * @param args the args
     * @return the method
     */
    private Method findMethod( Class rootClass, String methodName, Object[] args )
    {
    	Method[] methodArray = rootClass.getMethods();
    	
    	for ( Method currentMethod : methodArray )
		{
			if ( isCorrectMethod(currentMethod, methodName, args) )
			{
				if ( log.isDebugEnabled() )
					log.debug( "Found [" +methodName + "] on " + rootClass.getName() );
				
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
    
	/* (non-Javadoc)
	 * @see java.lang.reflect.InvocationHandler#invoke(java.lang.Object, java.lang.reflect.Method, java.lang.Object[])
	 */
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable 
	{
		
		Page currentService = PageManager.instance().getPageCache().get( proxy.getClass() );
		
		if ( currentService == null )
		{
		    currentService = super._createPage( (Class<Page>) proxy.getClass().getInterfaces()[0], webDriver );
		    PageManager.instance().getPageCache().put(  proxy.getClass(), currentService );
		}
		 
		Method methodImplemenation = findMethod( currentService.getClass(), method.getName(), args );
		
		if ( methodImplemenation != null )
		{
			String methodKeyName = PageManager.instance().getSiteName() + "." + proxy.getClass().getInterfaces()[0].getSimpleName() + "." + method.getName();
			
			PageManager.instance().beforeExecution( methodKeyName );
				
			long startTime = System.currentTimeMillis();
			Object returnValue = methodImplemenation.invoke( currentService, args );
			long runLength = System.currentTimeMillis() - startTime;
			

			PageManager.instance().afterExecution( methodKeyName, runLength );
			
			return returnValue;
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
    
    /**
     * Checks if is instance.
     *
     * @param classType the class type
     * @param value the value
     * @return true, if is instance
     */
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
