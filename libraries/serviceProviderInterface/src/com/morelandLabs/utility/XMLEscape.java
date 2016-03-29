package com.morelandLabs.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

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
    
    public static void main( String[] args ) throws Exception
    {
        StringBuilder outputStream = new StringBuilder();
        BufferedReader r = new BufferedReader( new FileReader( "c:/projects/tools/failureDOM.xml") );
        
        String currentLine = null;
        while ( ( currentLine = r.readLine() ) != null )
        {
            System.out.println( currentLine );
            System.out.println( escapeXML( currentLine ) );
            outputStream.append( escapeXML( currentLine ) );
        }
        
        FileWriter x = new FileWriter( "c:/projects/tools/fixedDOM.xml" );
        x.write( outputStream.toString() );
        x.close();
    }
}
