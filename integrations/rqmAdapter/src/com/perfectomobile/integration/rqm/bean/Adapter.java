<<<<<<< HEAD
package com.perfectomobile.integration.rqm.bean;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class Adapter
{
	private int pollingInterval;
	private String adapterType;
	private String description;
	private String name;

	public Adapter()
	{
		// TODO Auto-generated constructor stub
	}

	public Adapter( int pollingInterval, String adapterType, String description, String name )
	{
		super();
		this.pollingInterval = pollingInterval;
		this.adapterType = adapterType;
		this.description = description;
		this.name = name;
	}

	public int getPollingInterval()
	{
		return pollingInterval;
	}

	public void setPollingInterval( int pollingInterval )
	{
		this.pollingInterval = pollingInterval;
	}

	public String getAdapterType()
	{
		return adapterType;
	}

	public void setAdapterType( String adapterType )
	{
		this.adapterType = adapterType;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String toString()
	{
		String hostName = null;
		String ipAddress = null;
		String macAddress = null;
		String fqdn = null;
		
		try
		{
			InetAddress ip = InetAddress.getLocalHost();
			ipAddress = ip.getHostAddress();
			
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) 
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));	
				
			macAddress = sb.toString();
			
			hostName = ip.getHostName();
			fqdn = ip.getCanonicalHostName();
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		
		StringBuilder xmlBuilder = new StringBuilder();
		
		xmlBuilder.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		xmlBuilder.append( "<rdf:RDF xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:j.0=\"http://jazz.net/ns/auto/rqm#\" xmlns:oslc=\"http://open-services.net/ns/core# \" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">" );
		xmlBuilder.append( "<rdf:Description rdf:nodeID=\"A0\">" );
		xmlBuilder.append( "<j.0:macAddress>" ).append( macAddress ).append( "</j.0:macAddress>" );
		xmlBuilder.append( "<j.0:pollingInterval>" ).append( pollingInterval ).append( "</j.0:pollingInterval>" );
		xmlBuilder.append( "<rdf:type rdf:resource=\"http://jazz.net/ns/auto/rqm#AutomationAdapter\"/>" );
		xmlBuilder.append( "<dcterms:type>" ).append( adapterType ).append( "</dcterms:type>" );
		xmlBuilder.append( "<dcterms:description rdf:parseType=\"Literal\">" ).append( description ).append( "</dcterms:description>" );
		xmlBuilder.append( "<j.0:capability>Execute</j.0:capability>" );
		xmlBuilder.append( "<j.0:hostname>" ).append( hostName ).append( "</j.0:hostname>" );
		xmlBuilder.append( "<j.0:fullyQualifiedDomainName>" ).append( fqdn ).append( "</j.0:fullyQualifiedDomainName>" );
		xmlBuilder.append( "<j.0:ipAddress>" ).append( ipAddress ).append( "</j.0:ipAddress>" );
		xmlBuilder.append( "<dcterms:title rdf:parseType=\"Literal\">" ).append( name ).append( "</dcterms:title>" );
		xmlBuilder.append( "</rdf:Description>" );
		xmlBuilder.append( "</rdf:RDF>" );
		
		return xmlBuilder.toString();
		
	}

}
=======
package com.perfectomobile.integration.rqm.bean;

import java.net.InetAddress;
import java.net.NetworkInterface;

public class Adapter
{
	private int pollingInterval;
	private String adapterType;
	private String description;
	private String name;

	public Adapter()
	{
		// TODO Auto-generated constructor stub
	}

	public Adapter( int pollingInterval, String adapterType, String description, String name )
	{
		super();
		this.pollingInterval = pollingInterval;
		this.adapterType = adapterType;
		this.description = description;
		this.name = name;
	}

	public int getPollingInterval()
	{
		return pollingInterval;
	}

	public void setPollingInterval( int pollingInterval )
	{
		this.pollingInterval = pollingInterval;
	}

	public String getAdapterType()
	{
		return adapterType;
	}

	public void setAdapterType( String adapterType )
	{
		this.adapterType = adapterType;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription( String description )
	{
		this.description = description;
	}

	public String getName()
	{
		return name;
	}

	public void setName( String name )
	{
		this.name = name;
	}

	public String toString()
	{
		String hostName = null;
		String ipAddress = null;
		String macAddress = null;
		String fqdn = null;
		
		try
		{
			InetAddress ip = InetAddress.getLocalHost();
			ipAddress = ip.getHostAddress();
			
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) 
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));	
				
			macAddress = sb.toString();
			
			hostName = ip.getHostName();
			fqdn = ip.getCanonicalHostName();
			
		}
		catch( Exception e )
		{
			e.printStackTrace();
		}
		
		
		StringBuilder xmlBuilder = new StringBuilder();
		
		xmlBuilder.append( "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>");
		xmlBuilder.append( "<rdf:RDF xmlns:dcterms=\"http://purl.org/dc/terms/\" xmlns:rdf=\"http://www.w3.org/1999/02/22-rdf-syntax-ns#\" xmlns:j.0=\"http://jazz.net/ns/auto/rqm#\" xmlns:oslc=\"http://open-services.net/ns/core# \" xmlns:rdfs=\"http://www.w3.org/2000/01/rdf-schema#\">" );
		xmlBuilder.append( "<rdf:Description rdf:nodeID=\"A0\">" );
		xmlBuilder.append( "<j.0:macAddress>" ).append( macAddress ).append( "</j.0:macAddress>" );
		xmlBuilder.append( "<j.0:pollingInterval>" ).append( pollingInterval ).append( "</j.0:pollingInterval>" );
		xmlBuilder.append( "<rdf:type rdf:resource=\"http://jazz.net/ns/auto/rqm#AutomationAdapter\"/>" );
		xmlBuilder.append( "<dcterms:type>" ).append( adapterType ).append( "</dcterms:type>" );
		xmlBuilder.append( "<dcterms:description rdf:parseType=\"Literal\">" ).append( description ).append( "</dcterms:description>" );
		xmlBuilder.append( "<j.0:capability>Execute</j.0:capability>" );
		xmlBuilder.append( "<j.0:hostname>" ).append( hostName ).append( "</j.0:hostname>" );
		xmlBuilder.append( "<j.0:fullyQualifiedDomainName>" ).append( fqdn ).append( "</j.0:fullyQualifiedDomainName>" );
		xmlBuilder.append( "<j.0:ipAddress>" ).append( ipAddress ).append( "</j.0:ipAddress>" );
		xmlBuilder.append( "<dcterms:title rdf:parseType=\"Literal\">" ).append( name ).append( "</dcterms:title>" );
		xmlBuilder.append( "</rdf:Description>" );
		xmlBuilder.append( "</rdf:RDF>" );
		
		return xmlBuilder.toString();
		
	}

}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
