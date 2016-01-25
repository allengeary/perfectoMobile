<<<<<<< HEAD
package com.perfectomobile.integration.rqm.net;

import java.io.IOException;
import java.net.Socket;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RQMSocket
{
	private Log log = LogFactory.getLog( RQMSocket.class ); 

	private static final RQMSocket singleton = new RQMSocket();
	
	public static final RQMSocket instance()
	{
		return singleton;
	}
	
	private RQMSocket()
	{
		
	}
	
	private Socket serverSocket;
	
	public synchronized Socket getSocket()
	{
		if ( serverSocket == null )
		{
			try
			{
				serverSocket = getSocket( "rqm5.allengeary.com", 9443, true );
			}
			catch( Exception e )
			{
				log.fatal( "Couold not connect to RQM server", e );
			}
		}
		
		if ( serverSocket.isClosed() || serverSocket.isInputShutdown() || serverSocket.isOutputShutdown() || !serverSocket.isConnected() )
		{
			try
			{
				serverSocket = getSocket( "rqm5.allengeary.com", 9443, true );
			}
			catch( Exception e )
			{
				log.fatal( "Couold not connect to RQM server", e );
			}
		}
		
		return serverSocket;
	}
	
	private Socket getSocket( String hostName, int port, boolean useSsl ) throws IOException
	{
		if ( useSsl )
		{
			try
			{
				TrustManager[] trustAllCerts = new TrustManager[] { 
					    new X509TrustManager() {     
					        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
					            return new X509Certificate[0];
					        } 
					        public void checkClientTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					            } 
					        public void checkServerTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					        }
					    } 
					};
				
				SSLContext sc = SSLContext.getInstance( "TLS" ); 
			    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
				SSLSocketFactory factory = sc.getSocketFactory();
				SSLSocket sslSocket = (SSLSocket) factory.createSocket(hostName, port);
				sslSocket.setUseClientMode( true );
				return sslSocket;
			}
			catch( Exception e )
			{
				log.error( "Could not create SSL Connection", e );
			}
			
			return null;
		}
		else
		{
			return new Socket( hostName, port );
		}
	}
}
=======
package com.perfectomobile.integration.rqm.net;

import java.io.IOException;
import java.net.Socket;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class RQMSocket
{
	private Log log = LogFactory.getLog( RQMSocket.class ); 

	private static final RQMSocket singleton = new RQMSocket();
	
	public static final RQMSocket instance()
	{
		return singleton;
	}
	
	private RQMSocket()
	{
		
	}
	
	private Socket serverSocket;
	
	public synchronized Socket getSocket()
	{
		if ( serverSocket == null )
		{
			try
			{
				serverSocket = getSocket( "rqm5.allengeary.com", 9443, true );
			}
			catch( Exception e )
			{
				log.fatal( "Couold not connect to RQM server", e );
			}
		}
		
		if ( serverSocket.isClosed() || serverSocket.isInputShutdown() || serverSocket.isOutputShutdown() || !serverSocket.isConnected() )
		{
			try
			{
				serverSocket = getSocket( "rqm5.allengeary.com", 9443, true );
			}
			catch( Exception e )
			{
				log.fatal( "Couold not connect to RQM server", e );
			}
		}
		
		return serverSocket;
	}
	
	private Socket getSocket( String hostName, int port, boolean useSsl ) throws IOException
	{
		if ( useSsl )
		{
			try
			{
				TrustManager[] trustAllCerts = new TrustManager[] { 
					    new X509TrustManager() {     
					        public java.security.cert.X509Certificate[] getAcceptedIssuers() { 
					            return new X509Certificate[0];
					        } 
					        public void checkClientTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					            } 
					        public void checkServerTrusted( 
					            java.security.cert.X509Certificate[] certs, String authType) {
					        }
					    } 
					};
				
				SSLContext sc = SSLContext.getInstance( "TLS" ); 
			    sc.init(null, trustAllCerts, new java.security.SecureRandom()); 
				SSLSocketFactory factory = sc.getSocketFactory();
				SSLSocket sslSocket = (SSLSocket) factory.createSocket(hostName, port);
				sslSocket.setUseClientMode( true );
				return sslSocket;
			}
			catch( Exception e )
			{
				log.error( "Could not create SSL Connection", e );
			}
			
			return null;
		}
		else
		{
			return new Socket( hostName, port );
		}
	}
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
