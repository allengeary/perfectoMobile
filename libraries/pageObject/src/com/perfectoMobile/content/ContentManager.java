package com.perfectoMobile.content;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.perfectoMobile.content.provider.ContentProvider;
import com.perfectoMobile.page.keyWord.step.AbstractKeyWordStep;

/**
 * The singleton containing all loaded page data records.
 */
public class ContentManager
{
	private static final String OB = "{";
	private static final String CB = "}";
	private static final String PS = "#";
	
	private static Pattern CONTENT_PATTERN = Pattern.compile( "(\\S*)\\{?(#)?(\\w*)?\\}?" );
	private static ContentManager singleton = new ContentManager();
	
	private Map<String,ContentData> contentMap = new HashMap<String,ContentData>( 100 );
	private Map<String,Integer> matrixPositions = new HashMap<String,Integer> ();
	private Map<Integer,String> matrixData = new HashMap<Integer,String> ();
	private Log log = LogFactory.getLog( ContentManager.class );
	
    /**
     * Instance.
     *
     * @return the page data manager
     */
    public static ContentManager instance()
    {
        return singleton;
    }

    private ContentManager() {}
    
    private ContentProvider contentProvider;
    
    /**
     * Sets the page data provider.
     *
     * @param contentProvider the new content provider
     */
    public void setContentProvider( ContentProvider contentProvider )
    {
    	this.contentProvider = contentProvider;
    	contentProvider.readContent();
    }
    
    /**
     * Gets the content.
     *
     * @param contentKey the content key
     * @return the content
     */
    public ContentData getContent( String contentName )
    {
    	return contentMap.get( contentName );
    }
    
    /**
     * Gets the content value.  This will parse the key to extract the Content Data element
     * and then using the key extension it will get the actual value.  The key is formatted 
     * using the key name along with wavy brackets indicating the position or language if specified
     *
     * @param contentKey the content key
     * @return the content value
     */
    public String getContentValue( String contentKey )
    {
    	
    	if ( log.isInfoEnabled() )
    		log.info( "Extracting Content for [" + contentKey + "]" );
    	String contentName = null;
    	String keyName = null;
    	int indexNumber = -1;
    	
    	if ( contentKey.indexOf( OB ) > 0 )
    	{
    		contentName = contentKey.substring( 0, contentKey.indexOf( OB ) );
    		
    		if ( contentKey.indexOf( PS ) > 0 )
    			indexNumber = Integer.parseInt( contentKey.substring( contentKey.indexOf( PS ) + 1, contentKey.length() - 1 ) );
    		else
    			keyName = contentKey.substring( contentKey.indexOf( OB ) + 1,contentKey.length() - 1 );
    	}
    	else
    		contentName = contentKey;
    	
    	
    	ContentData contentData = contentMap.get( contentName );
    	
    	if ( contentData != null )
    	{
    		if ( keyName != null )
    			return contentData.getValue( keyName );
    		
    		if ( indexNumber >= 0 )
    			return contentData.getValue( indexNumber );
    		
    		return contentData.getValue();
    	}
    	else
    		log.error( "No content could be found for [" + contentName + "]" );
    	
    	return null;
    }
    
    
    /**
     * Sets the matrix data.
     *
     * @param matrixEntries the new matrix data
     */
    public void setMatrixData( String[] matrixEntries )
    {
    	for ( int i=0; i<matrixEntries.length; i++ )
    	{
    		matrixPositions.put( matrixEntries[ i ], i );
    		matrixData.put( i, matrixEntries[ i ] );
    	}
    }
    
    /**
     * Gets the matrix value.
     *
     * @param matrixPosition the matrix position
     * @return the matrix value
     */
    public String getMatrixValue( int matrixPosition )
    {
    	return matrixData.get( matrixPosition );
    }
    
    /**
     * Gets the matrix position.
     *
     * @param matrixValue the matrix value
     * @return the matrix position
     */
    public Integer getMatrixPosition (String matrixValue )
    {
    	return matrixPositions.get( matrixValue );
    }
    
    /**
     * Adds the content data.
     *
     * @param contentData the content data
     */
    public void addContentData( ContentData contentData )
    {
    	if ( contentData != null )
    		contentMap.put( contentData.getName(), contentData );
    }
}
