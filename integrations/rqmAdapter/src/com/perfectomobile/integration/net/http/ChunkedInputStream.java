<<<<<<< HEAD
package com.perfectomobile.integration.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChunkedInputStream extends InputStream
{
	/** The inputstream that we're wrapping */
	private InputStream in;

	/** The chunk size */
	private int chunkSize;

	/** The current position within the current chunk */
	private int pos;

	/** True if we'are at the beginning of stream */
	private boolean bof = true;

	/** True if we've reached the end of stream */
	private boolean eof = false;

	/** True if this stream is closed */
	private boolean closed = false;

	public ChunkedInputStream( final InputStream in ) throws IOException
	{

		if (in == null)
		{
			throw new IllegalArgumentException( "InputStream parameter may not be null" );
		}

		this.in = in;
		this.pos = 0;
	}

	public int read() throws IOException
	{

		if (closed)
		{
			throw new IOException( "Attempted read from closed stream." );
		}
		if (eof)
		{
			return -1;
		}
		if (pos >= chunkSize)
		{
			nextChunk();
			if (eof)
			{
				return -1;
			}
		}
		pos++;
		return in.read();
	}

	public int read( byte[] b, int off, int len ) throws IOException
	{

		if (closed)
			throw new IOException( "Attempted read from closed stream." );

		if (eof)
			return -1;
		
		if (pos >= chunkSize)
		{
			nextChunk();
			if (eof)
			{
				return -1;
			}
		}
		len = Math.min( len, chunkSize - pos );
		int count = in.read( b, off, len );
		pos += count;
		return count;
	}

	public int read( byte[] b ) throws IOException
	{
		return read( b, 0, b.length );
	}


	private void readCRLF() throws IOException
	{
		int cr = in.read();
		int lf = in.read();
		if (( cr != '\r' ) || ( lf != '\n' ))
		{
			throw new IOException( "CRLF expected at end of chunk: " + cr + "/" + lf );
		}
	}


	private void nextChunk() throws IOException
	{
		if (!bof)
		{
			readCRLF();
		}
		chunkSize = getChunkSizeFromInputStream( in );
		bof = false;
		pos = 0;
		if (chunkSize == 0)
		{
			eof = true;
			parseTrailerHeaders();
		}
	}

	private static int getChunkSizeFromInputStream( final InputStream in ) throws IOException
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// States: 0=normal, 1=\r was scanned, 2=inside quoted string, -1=end
		int state = 0;
		while (state != -1)
		{
			int b = in.read();
			if (b == -1)
			{
				throw new IOException( "chunked stream ended unexpectedly" );
			}
			switch (state)
			{
			case 0:
				switch (b)
				{
				case '\r':
					state = 1;
					break;
				case '\"':
					state = 2;
					/* fall through */
				default:
					baos.write( b );
				}
				break;

			case 1:
				if (b == '\n')
				{
					state = -1;
				}
				else
				{
					// this was not CRLF
					throw new IOException( "Protocol violation: Unexpected" + " single newline character in chunk size" );
				}
				break;

			case 2:
				switch (b)
				{
				case '\\':
					b = in.read();
					baos.write( b );
					break;
				case '\"':
					state = 0;
					/* fall through */
				default:
					baos.write( b );
				}
				break;
			default:
				throw new RuntimeException( "assertion failed" );
			}
		}

		// parse data
		String dataString = new String( baos.toByteArray() );
		int separator = dataString.indexOf( ';' );
		dataString = ( separator > 0 ) ? dataString.substring( 0, separator ).trim() : dataString.trim();

		int result;
		try
		{
			result = Integer.parseInt( dataString.trim(), 16 );
		}
		catch (NumberFormatException e)
		{
			throw new IOException( "Bad chunk size: " + dataString );
		}
		return result;
	}


	private void parseTrailerHeaders() throws IOException
	{
		// Header[] footers = HttpParser.parseHeaders(in);
		//
		// for (int i = 0; i < footers.length; i++) {
		// method.addResponseFooter(footers[i]);
		// }
	}


	public void close() throws IOException
	{
		if (!closed)
		{
			try
			{
				if (!eof)
				{
					exhaustInputStream( this );
				}
			}
			finally
			{
				eof = true;
				closed = true;
			}
		}
	}

	static void exhaustInputStream( InputStream inStream ) throws IOException
	{
		// read and discard the remainder of the message
		byte buffer[] = new byte[1024];
		while (inStream.read( buffer ) >= 0)
		{
			;
		}
	}
}
=======
package com.perfectomobile.integration.net.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ChunkedInputStream extends InputStream
{
	/** The inputstream that we're wrapping */
	private InputStream in;

	/** The chunk size */
	private int chunkSize;

	/** The current position within the current chunk */
	private int pos;

	/** True if we'are at the beginning of stream */
	private boolean bof = true;

	/** True if we've reached the end of stream */
	private boolean eof = false;

	/** True if this stream is closed */
	private boolean closed = false;

	public ChunkedInputStream( final InputStream in ) throws IOException
	{

		if (in == null)
		{
			throw new IllegalArgumentException( "InputStream parameter may not be null" );
		}

		this.in = in;
		this.pos = 0;
	}

	public int read() throws IOException
	{

		if (closed)
		{
			throw new IOException( "Attempted read from closed stream." );
		}
		if (eof)
		{
			return -1;
		}
		if (pos >= chunkSize)
		{
			nextChunk();
			if (eof)
			{
				return -1;
			}
		}
		pos++;
		return in.read();
	}

	public int read( byte[] b, int off, int len ) throws IOException
	{

		if (closed)
			throw new IOException( "Attempted read from closed stream." );

		if (eof)
			return -1;
		
		if (pos >= chunkSize)
		{
			nextChunk();
			if (eof)
			{
				return -1;
			}
		}
		len = Math.min( len, chunkSize - pos );
		int count = in.read( b, off, len );
		pos += count;
		return count;
	}

	public int read( byte[] b ) throws IOException
	{
		return read( b, 0, b.length );
	}


	private void readCRLF() throws IOException
	{
		int cr = in.read();
		int lf = in.read();
		if (( cr != '\r' ) || ( lf != '\n' ))
		{
			throw new IOException( "CRLF expected at end of chunk: " + cr + "/" + lf );
		}
	}


	private void nextChunk() throws IOException
	{
		if (!bof)
		{
			readCRLF();
		}
		chunkSize = getChunkSizeFromInputStream( in );
		bof = false;
		pos = 0;
		if (chunkSize == 0)
		{
			eof = true;
			parseTrailerHeaders();
		}
	}

	private static int getChunkSizeFromInputStream( final InputStream in ) throws IOException
	{

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		// States: 0=normal, 1=\r was scanned, 2=inside quoted string, -1=end
		int state = 0;
		while (state != -1)
		{
			int b = in.read();
			if (b == -1)
			{
				throw new IOException( "chunked stream ended unexpectedly" );
			}
			switch (state)
			{
			case 0:
				switch (b)
				{
				case '\r':
					state = 1;
					break;
				case '\"':
					state = 2;
					/* fall through */
				default:
					baos.write( b );
				}
				break;

			case 1:
				if (b == '\n')
				{
					state = -1;
				}
				else
				{
					// this was not CRLF
					throw new IOException( "Protocol violation: Unexpected" + " single newline character in chunk size" );
				}
				break;

			case 2:
				switch (b)
				{
				case '\\':
					b = in.read();
					baos.write( b );
					break;
				case '\"':
					state = 0;
					/* fall through */
				default:
					baos.write( b );
				}
				break;
			default:
				throw new RuntimeException( "assertion failed" );
			}
		}

		// parse data
		String dataString = new String( baos.toByteArray() );
		int separator = dataString.indexOf( ';' );
		dataString = ( separator > 0 ) ? dataString.substring( 0, separator ).trim() : dataString.trim();

		int result;
		try
		{
			result = Integer.parseInt( dataString.trim(), 16 );
		}
		catch (NumberFormatException e)
		{
			throw new IOException( "Bad chunk size: " + dataString );
		}
		return result;
	}


	private void parseTrailerHeaders() throws IOException
	{
		// Header[] footers = HttpParser.parseHeaders(in);
		//
		// for (int i = 0; i < footers.length; i++) {
		// method.addResponseFooter(footers[i]);
		// }
	}


	public void close() throws IOException
	{
		if (!closed)
		{
			try
			{
				if (!eof)
				{
					exhaustInputStream( this );
				}
			}
			finally
			{
				eof = true;
				closed = true;
			}
		}
	}

	static void exhaustInputStream( InputStream inStream ) throws IOException
	{
		// read and discard the remainder of the message
		byte buffer[] = new byte[1024];
		while (inStream.read( buffer ) >= 0)
		{
			;
		}
	}
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
