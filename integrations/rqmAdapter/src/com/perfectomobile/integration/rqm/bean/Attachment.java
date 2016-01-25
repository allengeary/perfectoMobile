<<<<<<< HEAD
package com.perfectomobile.integration.rqm.bean;

import java.io.File;

public class Attachment
{
	private String contentType;
	private File fileName;
	private String id;



	public String getContentType()
	{
		return contentType;
	}

	public void setContentType( String contentType )
	{
		this.contentType = contentType;
	}

	public File getFileName()
	{
		return fileName;
	}

	public void setFileName( String fileName )
	{
		this.fileName = new File( fileName );
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public Attachment( String contentType, String fileName )
	{
		this.contentType = contentType;
		setFileName( fileName );
	}

	@Override
	public String toString()
	{
		return "Attachment [contentType=" + contentType + ", fileName=" + fileName + ", id=" + id + "]";
	}



}
=======
package com.perfectomobile.integration.rqm.bean;

import java.io.File;

public class Attachment
{
	private String contentType;
	private File fileName;
	private String id;



	public String getContentType()
	{
		return contentType;
	}

	public void setContentType( String contentType )
	{
		this.contentType = contentType;
	}

	public File getFileName()
	{
		return fileName;
	}

	public void setFileName( String fileName )
	{
		this.fileName = new File( fileName );
	}

	public String getId()
	{
		return id;
	}

	public void setId( String id )
	{
		this.id = id;
	}

	public Attachment( String contentType, String fileName )
	{
		this.contentType = contentType;
		setFileName( fileName );
	}

	@Override
	public String toString()
	{
		return "Attachment [contentType=" + contentType + ", fileName=" + fileName + ", id=" + id + "]";
	}



}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
