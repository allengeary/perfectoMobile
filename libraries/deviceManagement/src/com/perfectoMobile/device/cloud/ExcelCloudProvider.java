package com.perfectoMobile.device.cloud;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * The Class ExcelApplicationProvider.
 */
public class ExcelCloudProvider extends AbstractCloudProvider
{
	private String tabName;
	private File fileName;
	private String resourceName;

	/**
	 * Instantiates a new Excel application provider.
	 *
	 * @param fileName            the file name
	 * @param tabName the tab name
	 */
	public ExcelCloudProvider( File fileName, String tabName )
	{
		this.fileName = fileName;
		this.tabName = tabName;
		readData();
	}

	/**
	 * Instantiates a new Excel application provider.
	 *
	 * @param resourceName            the resource name
	 * @param tabName the tab name
	 */
	public ExcelCloudProvider( String resourceName, String tabName )
	{
		this.resourceName = resourceName;
		this.tabName = tabName;
		readData();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.perfectoMobile.device.application.ApplicationProvider#readData()
	 */
	public void readData()
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
			XSSFSheet sheet = workbook.getSheet( tabName );

			for (int i = 1; i <= sheet.getLastRowNum(); i++)
			{
				XSSFRow currentRow = sheet.getRow( i );

				if ( getCellValue( currentRow.getCell( 0 ) ) == null || getCellValue( currentRow.getCell( 0 ) ).isEmpty() )
					break;
				
				CloudRegistry.instance().addCloudDescriptor( new CloudDescriptor( getCellValue( currentRow.getCell( 0 ) ), getCellValue( currentRow.getCell( 1 ) ), getCellValue( currentRow.getCell( 2 ) ), getCellValue( currentRow.getCell( 3 ) ), getCellValue( currentRow.getCell( 4  ) ), getCellValue( currentRow.getCell( 5  ) ), getCellValue( currentRow.getCell( 7 ) ), getCellValue( currentRow.getCell( 6 ) ) ) );
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
