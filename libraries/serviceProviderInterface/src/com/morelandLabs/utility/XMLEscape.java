package com.morelandLabs.utility;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.htmlcleaner.DomSerializer;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.SimpleXmlSerializer;
import org.htmlcleaner.TagNode;
import org.w3c.dom.Document;

public class XMLEscape
{
    private static int[][] CHAR_LIST = new int[][] { { 0, 9 }, { 11, 13 }, {128, 255 }, {38, 39 } };
    
    public static String escapeXML( String xmlIn )
    {
        String xmlOut = xmlIn;
        for ( int[] currentArray : CHAR_LIST )
        {
           
            for ( int i=currentArray[ 0 ]; i<currentArray[ 1 ]; i++ )
            {
                xmlOut = xmlOut.replace( new String( new byte[] { (byte)i } ), "&#" + i + ";" );
            }
        }
        
        return xmlOut;
    }
    
    public static String toXML( String xmlIn )
    {
        
        //
        // Check if the document is already well formed
        //
        if ( validateDocument( xmlIn ) )
            return xmlIn;
        
        //
        // Try to simply escape some characters
        //
        String escapedString = escapeXML( xmlIn );
        if ( validateDocument( escapedString ) )
            return escapedString;
        
        //
        // We assume HTML at this point
        //

        try
        {
            HtmlCleaner cleaner = new HtmlCleaner();
            TagNode node = cleaner.clean( new ByteArrayInputStream( xmlIn.getBytes()) );
            
            ByteArrayOutputStream htmlDocument = new ByteArrayOutputStream();
            new SimpleXmlSerializer( cleaner.getProperties() ).writeToStream( node, htmlDocument );
            
            String htmlOutput = new String( htmlDocument.toByteArray() );
            if ( validateDocument( htmlOutput ) )
                return htmlOutput;
        }
        catch( Exception e )
        {
            
        }
        
        return null;

        
    }
    
    private static boolean validateDocument( String inputDocument )
    {
        try
        {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse( new ByteArrayInputStream( inputDocument.getBytes() ) );
            
            return true;
        }
        catch( Throwable t)
        {
            return false;
        }
    }
    
    
    
    public static void main( String[] args ) throws Exception
    {
        StringBuilder outputStream = new StringBuilder();
        BufferedReader r = new BufferedReader( new FileReader( "c:/projects/tools/failureDOM.xml") );
        
        HtmlCleaner cleaner = new HtmlCleaner();
        TagNode node = cleaner.clean( new FileReader( "c:/projects/tools/failureDOM.xml") );
        
        new DomSerializer( cleaner.getProperties(), true).createDOM(node).getDocumentElement();
        
        ByteArrayOutputStream htmlDocument = new ByteArrayOutputStream();
        new SimpleXmlSerializer( cleaner.getProperties() ).writeToStream( node, new FileOutputStream( "c:/projects/tools/fixedDOM2.xml" ) );
        

    }
}
