package com.morelandLabs.utility;

import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ExecutionDefinitionMap 
{
	private Map<String,ExecutionDefinition> definitionMap = new HashMap<String,ExecutionDefinition>( 10 );
	
	public ExecutionDefinitionMap ( File mapFile )
	{
		try
		{
			Properties props = new Properties();
			props.load( new FileInputStream( mapFile ) );
			
			for ( Object keyName : props.keySet() )
			{
				ExecutionDefinition ed = new ExecutionDefinition( new File( mapFile.getAbsoluteFile().getParentFile(), props.getProperty( keyName + "") ) );
				definitionMap.put( keyName + "", ed );

			}
			
		}
		catch( Exception e )
		{
			
		}
	}
	
	public Collection<ExecutionDefinition> getExecutions()	
	{
		return definitionMap.values();
	}
	
	public static void main(String[] args) 
	{
		File x = new File( "C:\\Users\\AJ\\git\\perfectoMobile\\Test-Driver\\test-output\\05-08_08-14-53-600\\executionMap.properties" );
		
		ExecutionDefinitionMap y = new ExecutionDefinitionMap( x );
		System.out.println( y.getExecutions() );
	}
	
}
