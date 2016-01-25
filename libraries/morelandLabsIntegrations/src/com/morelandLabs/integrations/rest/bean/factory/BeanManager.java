package com.morelandLabs.integrations.rest.bean.factory;

import java.io.InputStream;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Executions.TimeType;
import com.morelandLabs.integrations.rest.bean.Bean;

/**
 * The Class BeanManager.
 */
public class BeanManager
{
	private static BeanManager singleton = new BeanManager();
	
	private BeanManager()
	{
		
	}
	
	/**
	 * Instance.
	 *
	 * @return the bean manager
	 */
	public static BeanManager instance()
	{
		return singleton;
	}
	
	private BeanFactory beanFactory = new XMLBeanFactory();

	/**
	 * Gets the bean factory.
	 *
	 * @return the bean factory
	 */
	public BeanFactory getBeanFactory()
	{
		return beanFactory;
	}

	/**
	 * Sets the bean factory.
	 *
	 * @param beanFactory the new bean factory
	 */
	public void setBeanFactory( BeanFactory beanFactory )
	{
		this.beanFactory = beanFactory;
	}
	
	/**
	 * Creates the bean.
	 *
	 * @param returnType the return type
	 * @param inputData the input data
	 * @return the bean
	 * @throws Exception the exception
	 */
	public Bean createBean( Class returnType, String inputData ) throws Exception
	{
		return beanFactory.createBean( returnType, inputData );
	}
	
	/**
	 * Creates the bean.
	 *
	 * @param returnType the return type
	 * @param inputStream the input stream
	 * @return the bean
	 * @throws Exception the exception
	 */
	public Bean createBean( Class returnType, InputStream inputStream ) throws Exception
	{
		return beanFactory.createBean( returnType, inputStream );
	}
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 * @throws Exception the exception
	 */
	public static void main( String[] args ) throws Exception
	{
		BeanManager.instance().setBeanFactory( new XMLBeanFactory() );
		
		PerfectoMobile.instance().setBaseUrl( "https://allstate.perfectomobile.com" );
		PerfectoMobile.instance().setUserName( "alleng@perfectomobile.com" );
		PerfectoMobile.instance().setPassword( "Perfecto123" );
		
		System.out.println( PerfectoMobile.instance().devices().getDevice( "854D1700A3D9E45E378200C63383707E7E05F140" ) );
		
		//PerfectoMobile.instance().executions().getExecutions( null, true, null, TimeType.started, null, (short) ( 60 * 60 * 1 ) );
		//System.out.println( PerfectoMobile.instance().devices().getDevices() );
		
//		Execution x = PerfectoMobile.instance().executions().execute( "GROUP:cameraTest.xml", "632E9899" );
//
//		while( !PerfectoMobile.instance().executions().status( x.getExecutionId()).getCompleted() )
//		{
//			try
//			{
//				Thread.sleep( 500 );
//			}
//			catch( Exception e )
//			{
//				
//			}
//		}
//		
//		System.out.println( PerfectoMobile.instance().executions().status( x.getExecutionId() ) );
		
		/*Execution x = PerfectoMobile.instance().executions().startExecution();
		System.out.println( x.getExecutionId() );
		
		PerfectoMobile.instance().device().open( x.getExecutionId(), "FA53XYJ18894" );
		ApplicationCollection a = PerfectoMobile.instance().application().find( x.getExecutionId(), "FA53XYJ18894" );
		
		System.out.println( a.getApplications() );
		//PerfectoMobile.instance().application().uninstallAll( x.getExecutionId(), "FA53XYJ18894" );
		
		PerfectoMobile.instance().application().open( x.getExecutionId(), "FA53XYJ18894", "Chrome", "com.android.chrome" );
		
		a = PerfectoMobile.instance().application().find( x.getExecutionId(), "FA53XYJ18894" );
		
		System.out.println( a.getApplications() );
		
		PerfectoMobile.instance().device().close(  x.getExecutionId(), "FA53XYJ18894"  );
		x = PerfectoMobile.instance().executions().endExecution( x.getExecutionId() );
		System.out.println( x.getStatus() ); */
		
		//System.out.println( PerfectoMobile.instance().devices().getDevice( "991C4AED" ) );
		
		//System.out.println( PerfectoMobile.instance().devices().getDevices( "Samsung" ) );
		
		//System.out.println( PerfectoMobile.instance().repositories().getRepositorys( RepositoryType.MEDIA ) );
		
		//Handset x = (Handset)BeanManager.instance().createBean( Handset.class, new FileInputStream( "c:/tools/handset.xml" ) );
		
		//System.out.println( x );
		
	}
}
