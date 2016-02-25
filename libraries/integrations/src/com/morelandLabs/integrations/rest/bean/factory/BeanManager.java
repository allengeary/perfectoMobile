package com.morelandLabs.integrations.rest.bean.factory;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import javax.imageio.ImageIO;

import com.morelandLabs.integrations.perfectoMobile.rest.PerfectoMobile;
import com.morelandLabs.integrations.perfectoMobile.rest.bean.Execution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.ImageFormat;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.MatchMode;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Resolution;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Imaging.Screen;
import com.morelandLabs.integrations.perfectoMobile.rest.services.Repositories.RepositoryType;
import com.morelandLabs.integrations.rest.bean.Bean;

// TODO: Auto-generated Javadoc
/**
 * The Class BeanManager.
 */
public class BeanManager
{
	
	/** The singleton. */
	private static BeanManager singleton = new BeanManager();
	
	/**
	 * Instantiates a new bean manager.
	 */
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
	
	/** The bean factory. */
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
		
		PerfectoMobile.instance().setBaseUrl( "https://discover.perfectomobile.com" );
		PerfectoMobile.instance().setUserName( "alleng@perfectomobile.com" );
		PerfectoMobile.instance().setPassword( "Perfecto123" );
		
		System.out.println( PerfectoMobile.instance().devices().getDevice( "78C4EE8665F1C7E428BA4F7CCA3F7B1F0D9FDB1D" ) );
		
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
		
		Execution x = PerfectoMobile.instance().executions().startExecution();
		System.out.println( x.getExecutionId() );
		
		PerfectoMobile.instance().device().open( x.getExecutionId(), "78C4EE8665F1C7E428BA4F7CCA3F7B1F0D9FDB1D" );
		
		//PerfectoMobile.instance().imaging().imageExists("exe", "hset", "PRIVATE:logo.png", (short) 20, MatchMode.bounded );
		
		//Thread.sleep( 5000 );
		//PerfectoMobile.instance().imaging().screenShot( x.getExecutionId(), "93C8CC73", "PRIVATE:a.png", Screen.primary, ImageFormat.png, Resolution.high );
		
		//byte[] file = PerfectoMobile.instance().repositories().download( RepositoryType.MEDIA, "PRIVATE:a.png" );
		//System.out.println( file.length );
		
		//BufferedImage image = ImageIO.read( new ByteArrayInputStream( file ) );
		//System.out.println( image.getSubimage( 100, 100, 100, 100 ) );
		
		//FileOutputStream os = new FileOutputStream( "c:/tools/a.png" );
		//os.write( file );
		//os.close();
		
		System.out.println( PerfectoMobile.instance().device().call(  x.getExecutionId(), "78C4EE8665F1C7E428BA4F7CCA3F7B1F0D9FDB1D"  ) );
		
		Thread.sleep( 10000 );
		
		PerfectoMobile.instance().device().close(  x.getExecutionId(), "78C4EE8665F1C7E428BA4F7CCA3F7B1F0D9FDB1D"  );
		x = PerfectoMobile.instance().executions().endExecution( x.getExecutionId() );
		System.out.println( x.getStatus() ); 
		
		//System.out.println( PerfectoMobile.instance().devices().getDevice( "991C4AED" ) );
		
		//System.out.println( PerfectoMobile.instance().devices().getDevices( "Samsung" ) );
		
		//System.out.println( PerfectoMobile.instance().repositories().getRepositorys( RepositoryType.MEDIA ) );
		
		//Handset x = (Handset)BeanManager.instance().createBean( Handset.class, new FileInputStream( "c:/tools/handset.xml" ) );
		
		//System.out.println( x );
		
	}
}
