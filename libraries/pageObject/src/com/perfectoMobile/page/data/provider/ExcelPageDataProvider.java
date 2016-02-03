package com.perfectoMobile.page.data.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.perfectoMobile.page.data.DefaultPageData;

/**
 * The Class XMLPageDataProvider.
 */
public class ExcelPageDataProvider extends AbstractPageDataProvider
{
	private File fileName;
	private String resourceName;
	private String tabNames;

	/**
	 * Instantiates a new XML page data provider.
	 *
	 * @param fileName the file name
	 * @param tabNames the tab names
	 */
	public ExcelPageDataProvider( File fileName, String tabNames )
	{
		this.tabNames = tabNames;
		this.fileName = fileName;
	}

	/**
	 * Instantiates a new XML page data provider.
	 *
	 * @param resourceName the resource name
	 * @param tabNames the tab names
	 */
	public ExcelPageDataProvider( String resourceName, String tabNames )
	{
		this.tabNames = tabNames;
		this.resourceName = resourceName;
	}

	/* (non-Javadoc)
	 * @see com.perfectoMobile.page.data.provider.AbstractPageDataProvider#readPageData()
	 */
	@Override
	public void readPageData()
	{
		if (fileName == null)
		{
			if (log.isInfoEnabled())
				log.info( "Reading from CLASSPATH as " + resourceName );
			readElements( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				if (log.isInfoEnabled())
					log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				readElements( new FileInputStream( fileName ) );
			}
			catch (FileNotFoundException e)
			{
				log.fatal( "Could not read from " + fileName, e );
			}
		}

	}

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
	
	
	private void readElements( InputStream inputStream )
	{
		
		XSSFWorkbook workbook = null;

		try
		{
			workbook = new XSSFWorkbook( inputStream );
			
			String[] tabs = tabNames.split( "," );
			
			for ( String tabName : tabs )
			{
				addRecordType( tabName, false );
				
				XSSFSheet sheet = workbook.getSheet( tabName );
	
				XSSFRow firstRow = sheet.getRow( 0 );
				
				for (int i = 1; i <= sheet.getLastRowNum(); i++)
				{
					XSSFRow currentRow = sheet.getRow( i );
	
					if ( getCellValue( currentRow.getCell( 0 ) ) == null || getCellValue( currentRow.getCell( 0 ) ).isEmpty() )
						break;
					
					DefaultPageData currentRecord = new DefaultPageData( tabName, tabName + "-" + i, true );
					for ( int x=0; x<firstRow.getLastCellNum(); x++ )
					{
						currentRecord.addValue( getCellValue( firstRow.getCell( x ) ), getCellValue( currentRow.getCell( x ) ) );
					}
					
					addRecord( currentRecord );
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
}
