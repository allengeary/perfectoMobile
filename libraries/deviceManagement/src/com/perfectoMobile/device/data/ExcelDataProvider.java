package com.perfectoMobile.device.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.morelandLabs.application.ApplicationDescriptor;
import com.morelandLabs.application.ApplicationRegistry;
import com.morelandLabs.spi.Device;
import com.perfectoMobile.device.DeviceManager;
import com.perfectoMobile.device.SimpleDevice;

/**
 * The Class CSVDataProvider.
 */
public class ExcelDataProvider implements DataProvider
{
	private Log log = LogFactory.getLog( ExcelDataProvider.class );
	private File fileName;
	private String tabName;
	private String resourceName;
	private DriverType driverType;

	/**
	 * Instantiates a new CSV data provider.
	 *
	 * @param fileName            the file name
	 * @param tabName the tab name
	 * @param driverType the driver type
	 */
	public ExcelDataProvider( File fileName, String tabName, DriverType driverType )
	{
		this.fileName = fileName;
		this.tabName = tabName;
		this.driverType = driverType;
	}

	/**
	 * Instantiates a new CSV data provider.
	 *
	 * @param resourceName            the resource name
	 * @param tabName the tab name
	 * @param driverType the driver type
	 */
	public ExcelDataProvider( String resourceName, String tabName, DriverType driverType )
	{
		this.tabName = tabName;
		this.resourceName = resourceName;
		this.driverType = driverType;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.perfectoMobile.device.data.DataProvider#readData()
	 */
	public void readData()
	{
		if (fileName == null)
		{
			if (log.isInfoEnabled())
				log.info( "Reading Device Data from Resource " + resourceName );

			readData( getClass().getClassLoader().getResourceAsStream( resourceName ) );
		}
		else
		{
			try
			{
				readData( new FileInputStream( fileName ) );
			}
			catch (Exception e)
			{
				log.fatal( "Could mot read from " + fileName, e );
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
					return String.valueOf( ( int ) cell.getNumericCellValue() );
				case XSSFCell.CELL_TYPE_STRING:
					return cell.getRichStringCellValue().toString();

			}
		}
		return null;
	}

	private void readData( InputStream inputStream )
	{
		BufferedReader fileReader = null;

		XSSFWorkbook workbook = null;

		try
		{
			workbook = new XSSFWorkbook( inputStream );
			XSSFSheet sheet = workbook.getSheet( tabName );

			for (int i = 1; i <= sheet.getLastRowNum(); i++)
			{
				XSSFRow currentRow = sheet.getRow( i );

				if (getCellValue( currentRow.getCell( 0 ) ) == null || getCellValue( currentRow.getCell( 0 ) ).isEmpty())
					break;

				String driverName = "";
				switch (driverType)
				{
					case APPIUM:
						if (getCellValue( currentRow.getCell( 3 ) ).toUpperCase().equals( "IOS" ))
							driverName = "IOS";
						else if (getCellValue( currentRow.getCell( 3 ) ).toUpperCase().equals( "ANDROID" ))
							driverName = "ANDROID";
						else
							throw new IllegalArgumentException( "Appium is not supported on the following OS " + getCellValue( currentRow.getCell( 3 ) ).toUpperCase() );
						break;

					case PERFECTO:
						driverName = "PERFECTO";
						break;

					case WEB:
						driverName = "WEB";
						break;
				}

				Device currentDevice = new SimpleDevice( getCellValue( currentRow.getCell( 0 ) ), getCellValue( currentRow.getCell( 1 ) ), getCellValue( currentRow.getCell( 2 ) ), getCellValue( currentRow.getCell( 3 ) ), getCellValue( currentRow.getCell( 4 ) ), getCellValue( currentRow.getCell( 5 ) ), getCellValue(
						currentRow.getCell( 6 ) ), Integer.parseInt( getCellValue( currentRow.getCell( 7 ) ) ), driverName, Boolean.parseBoolean( getCellValue( currentRow.getCell( 8 ) ) ), null );
				if (currentDevice.isActive())
				{
					if (log.isDebugEnabled())
						log.debug( "Extracted: " + currentDevice );

					DeviceManager.instance().registerDevice( currentDevice );
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
