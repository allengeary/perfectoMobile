package com.perfectoMobile.page.element.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.perfectoMobile.page.BY;
import com.perfectoMobile.page.ElementDescriptor;
import com.perfectoMobile.page.element.Element;
import com.perfectoMobile.page.element.ElementFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class CSVElementProvider.
 */
public class ExcelElementProvider extends AbstractElementProvider
{
	
	/** The element map. */
	private Map<String,Element> elementMap = new HashMap<String,Element>(20);
	
	/** The file name. */
	private File[] fileName;
	
	/** The resource name. */
	private String[] resourceName;
	
	/** The tab names. */
	private String tabNames;
	
	
	/**
	 * Instantiates a new CSV element provider.
	 *
	 * @param fileName the file name
	 * @param tabNames the tab names
	 */
	public ExcelElementProvider( File fileName, String tabNames )
	{
		this.fileName = new File[] { fileName };
		this.tabNames = tabNames;
		readElements();
	}
	
	
	/**
     * Instantiates a new CSV element provider.
     *
     * @param fileName the file name
     * @param tabNames the tab names
     */
    public ExcelElementProvider( File[] fileName, String tabNames )
    {
        this.fileName = fileName;
        this.tabNames = tabNames;
        readElements();
    }
    
    /**
     * Instantiates a new CSV element provider.
     *
     * @param resourceName the resource name
     * @param tabNames the tab names
     */
    public ExcelElementProvider( String resourceName, String tabNames )
    {
        this.resourceName = resourceName.split( "," );
        this.tabNames = tabNames;
        readElements();
    }
	
	/**
	 * Read elements.
	 */
	private void readElements()
	{
		if ( fileName == null )
		{
			
			for ( String resource : resourceName )
			{
			    if ( log.isInfoEnabled() )
	                log.info( "Reading from CLASSPATH as " + resource );
			    readElements( getClass().getClassLoader().getResourceAsStream( resource ) );
			}
		}
		else
		{
			try
			{
				
				for ( File currentFile : fileName )
				{
				    if ( log.isInfoEnabled() )
	                    log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				    readElements( new FileInputStream( currentFile ) );
				}
			}
			catch( FileNotFoundException e )
			{
				log.fatal( "Could not read from " + fileName, e );
			}
		}
	}
	
	/**
	 * Gets the cell value.
	 *
	 * @param cell the cell
	 * @return the cell value
	 */
	private String getCellValue( XSSFCell cell )
	{
		if (cell != null)
		{
			switch (cell.getCellType())
			{
				case XSSFCell.CELL_TYPE_BLANK:
					return null;
				case XSSFCell.CELL_TYPE_BOOLEAN:
					return String.valueOf( cell.getBooleanCellValue() );
				case XSSFCell.CELL_TYPE_NUMERIC:
					return String.valueOf( cell.getNumericCellValue() );
				case XSSFCell.CELL_TYPE_STRING:
					return cell.getRichStringCellValue().toString();
			}
		}
		return null;
	}
	
	/**
	 * Read elements.
	 *
	 * @param inputStream the input stream
	 */
	private void readElements( InputStream inputStream )
	{
		
		XSSFWorkbook workbook = null;

		try
		{
			workbook = new XSSFWorkbook( inputStream );
			
			String[] tabs = tabNames.split( "," );
			
			for ( String tabName : tabs )
			{
				XSSFSheet sheet = workbook.getSheet( tabName );
				if ( sheet == null )
				    continue;
	
				for (int i = 1; i <= sheet.getLastRowNum(); i++)
				{
					XSSFRow currentRow = sheet.getRow( i );
	
					if ( getCellValue( currentRow.getCell( 0 ) ) == null || getCellValue( currentRow.getCell( 0 ) ).isEmpty() )
						break;
					
					ElementDescriptor elementDescriptor = new ElementDescriptor( tabName, getCellValue( currentRow.getCell( 0 ) ),  getCellValue( currentRow.getCell( 1 ) ) );
					
					String contextName = null;
					if ( getCellValue( currentRow.getCell( 4 ) ) != null && !getCellValue( currentRow.getCell( 4 ) ).isEmpty() )
					{
						contextName = getCellValue( currentRow.getCell( 4 ) );
					}
					
					Element currentElement = ElementFactory.instance().createElement( BY.valueOf( getCellValue( currentRow.getCell( 2 ) ) ), getCellValue( currentRow.getCell( 3 ) ).replace( "$$", ","), getCellValue( currentRow.getCell( 1 ) ), getCellValue( currentRow.getCell( 0 ) ), contextName );
					
					if ( log.isDebugEnabled() )
						log.debug( "Adding Excel Element using [" + elementDescriptor.toString() + "] as [" + currentElement );
					elementMap.put(elementDescriptor.toString(), currentElement );
					
				}
			}

			
		}
		catch (Exception e)
		{
			log.fatal( "Error reading Excel Element File", e );
		}
		finally
		{
			try
			{
				workbook.close();
			}
			catch (Exception e)
			{
			}
		}
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.element.provider.AbstractElementProvider#_getElement(com.perfectoMobile.page.ElementDescriptor)
	 */
	@Override
	protected Element _getElement( ElementDescriptor elementDescriptor )
	{
		return elementMap.get(  elementDescriptor.toString() );
	}
}
