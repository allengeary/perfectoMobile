package com.perfectoMobile.page.keyWord.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Unmarshaller;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import com.perfectoMobile.page.Page;
import com.perfectoMobile.page.data.PageData;
import com.perfectoMobile.page.data.PageDataManager;
import com.perfectoMobile.page.keyWord.KeyWordDriver;
import com.perfectoMobile.page.keyWord.KeyWordPage;
import com.perfectoMobile.page.keyWord.KeyWordParameter;
import com.perfectoMobile.page.keyWord.KeyWordParameter.ParameterType;
import com.perfectoMobile.page.keyWord.KeyWordStep;
import com.perfectoMobile.page.keyWord.KeyWordStep.StepFailure;
import com.perfectoMobile.page.keyWord.KeyWordStep.ValidationType;
import com.perfectoMobile.page.keyWord.KeyWordTest;
import com.perfectoMobile.page.keyWord.KeyWordToken;
import com.perfectoMobile.page.keyWord.KeyWordToken.TokenType;
import com.perfectoMobile.page.keyWord.provider.xsd.Import;
import com.perfectoMobile.page.keyWord.provider.xsd.Model;
import com.perfectoMobile.page.keyWord.provider.xsd.ObjectFactory;
import com.perfectoMobile.page.keyWord.provider.xsd.Parameter;
import com.perfectoMobile.page.keyWord.provider.xsd.RegistryRoot;
import com.perfectoMobile.page.keyWord.provider.xsd.Step;
import com.perfectoMobile.page.keyWord.provider.xsd.Test;
import com.perfectoMobile.page.keyWord.provider.xsd.Token;
import com.perfectoMobile.page.keyWord.step.KeyWordStepFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLKeyWordProvider.
 */
public class XMLKeyWordProvider implements KeyWordProvider
{
	

	/** The log. */
	private Log log = LogFactory.getLog( KeyWordTest.class );
	
	/** The file name. */
	private File fileName;
	
	/** The resource name. */
	private String resourceName;
	
	/** The as resource. */
	private boolean asResource = false;

	/**
	 * Instantiates a new XML key word provider.
	 *
	 * @param fileName
	 *            the file name
	 */
	public XMLKeyWordProvider( File fileName )
	{
		this.fileName = fileName;
	}

	/**
	 * Instantiates a new XML key word provider.
	 *
	 * @param resourceName
	 *            the resource name
	 */
	public XMLKeyWordProvider( String resourceName )
	{
		this.resourceName = resourceName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.perfectoMobile.page.keyWord.provider.KeyWordProvider#readData()
	 */
	public void readData()
	{
		if (fileName == null)
		{
			if (log.isInfoEnabled())
				log.info( "Reading from CLASSPATH as XMLElementProvider.elementFile" );
			readElements( getClass().getClassLoader().getResourceAsStream( resourceName ), true, true );
			asResource = true;
		}
		else
		{
			try
			{
				if (log.isInfoEnabled())
					log.info( "Reading from FILE SYSTEM as [" + fileName + "]" );
				readElements( new FileInputStream( fileName ), true, true );
			}
			catch (Exception e)
			{
				log.fatal( "Could not read from " + fileName, e );
				System.exit( -1 );
			}
		}
	}

	/**
	 * Read elements.
	 *
	 * @param inputStream the input stream
	 * @param readTests the read tests
	 * @param readFunctions the read functions
	 */
	private void readElements( InputStream inputStream, boolean readTests, boolean readFunctions )
	{

		try
		{

		    JAXBContext jc = JAXBContext.newInstance( ObjectFactory.class );
            Unmarshaller u = jc.createUnmarshaller();
            JAXBElement<?> rootElement = (JAXBElement<?>)u.unmarshal( inputStream );
            
            RegistryRoot rRoot = (RegistryRoot)rootElement.getValue();

			parseModel( rRoot.getModel() );
			parseImports(  rRoot.getImport() );
			
			if ( readTests )
			{
			    for( Test test : rRoot.getTest() )
			    {
			        if (KeyWordDriver.instance().getTest( test.getName() ) != null)
                    {
                        log.warn( "The test [" + test.getName() + "] is already defined and will not be added again" );
                        continue;
                    }
			        
			        KeyWordTest currentTest = parseTest( test, "test" );
			        
			        if (currentTest.getDataDriver() != null && !currentTest.getDataDriver().isEmpty())
                    {
                        PageData[] pageData = PageDataManager.instance().getRecords( currentTest.getDataDriver() );
                        if (pageData == null)
                        {
                            log.warn( "Specified Data Driver [" + currentTest.getDataDriver() + "] could not be located. Make sure it exists and it was populated prior to initializing your keyword factory" );
                            KeyWordDriver.instance().addTest( currentTest );
                        }
                        else
                        {
                            String testName = currentTest.getName();

                            for (PageData record : pageData)
                            {
                                KeyWordDriver.instance().addTest( currentTest.copyTest( testName + "!" + record.getName() ) );
                            }
                        }
                    }
                    else
                        KeyWordDriver.instance().addTest( currentTest );
			        
			    }
			}

			if (readFunctions)
			{
			    for( Test test : rRoot.getFunction() )
                {
                    if (KeyWordDriver.instance().getTest( test.getName() ) != null)
                    {
                        log.warn( "The function [" + test.getName() + "] is already defined and will not be added again" );
                        continue;
                    }
                    
                    KeyWordDriver.instance().addFunction( parseTest( test, "function" ) );
                }
			}

		}
		catch( IllegalStateException e )
		{
			throw e;
		}
		catch (Exception e)
		{
			log.fatal( "Error reading XML Element File", e );
		}
	}

	/**
	 * Parses the imports.
	 *
	 * @param xmlDocument the xml document
	 */
	private void parseImports( List<Import> importList )
	{
	    for ( Import imp : importList )
	    {
	        try
	        {
	            if (log.isInfoEnabled())
                    log.info( "Attempting to import file [" + Paths.get(".").toAbsolutePath().normalize().toString() + imp.getFileName() + "]" );
	            
	            readElements( new FileInputStream( imp.getFileName() ), imp.isIncludeTests(), imp.isIncludeFunctions() );
	        }
	        catch( Exception e )
	        {
	            log.fatal( "Could not read from " + imp.getFileName(), e );
                throw new IllegalStateException( e );
	        }
	    }
	}

	/**
	 * Parses the model.
	 *
	 * @param xmlDocument the xml document
	 */
	private void parseModel( Model model )
	{
	    for ( com.perfectoMobile.page.keyWord.provider.xsd.Page page : model.getPage() )
	    {
	        try
	        {
    	        Class useClass = KeyWordPage.class;
    	        if ( page.getClazz() != null && !page.getClazz().isEmpty() )
    	            useClass = ( Class<Page> ) Class.forName( page.getClazz() );
    	        
    	        if (log.isDebugEnabled())
                    log.debug( "Creating page as " + useClass.getSimpleName() + " for " + page.getName() );
    
                KeyWordDriver.instance().addPage( page.getName(), useClass );
	        }
	        catch( Exception e )
	        {
	            log.error( "Error creating instance of [" + page.getClazz() + "]" );
	        }
	    }
	}

	/**
	 * Parses the test.
	 *
	 * @param testNode the test node
	 * @param typeName the type name
	 * @return the key word test
	 */
	private KeyWordTest parseTest( Test xTest, String typeName )
	{
		KeyWordTest test = new KeyWordTest( xTest.getName(), xTest.isActive(), xTest.getDataProvider(), xTest.getDataDriver(), xTest.isTimed(), xTest.getLinkId(), xTest.getOs(), xTest.getThreshold().intValue(), "", xTest.getTagNames() );
		
		KeyWordStep[] steps = parseSteps( xTest.getStep(), xTest.getName(), typeName );

		for (KeyWordStep step : steps)
			test.addStep( step );

		return test;
	}

	/**
	 * Parses the steps.
	 *
	 * @param testNode the test node
	 * @param testName the test name
	 * @param typeName the type name
	 * @return the key word step[]
	 */
	private KeyWordStep[] parseSteps( List<Step> xSteps, String testName, String typeName )
	{

		if (log.isDebugEnabled())
			log.debug( "Extracted " + xSteps.size() + " Steps" );

		List<KeyWordStep> stepList = new ArrayList<KeyWordStep>( 10 );

		for ( Step xStep : xSteps )
		{
		    KeyWordStep step = KeyWordStepFactory.instance().createStep( xStep.getName(), xStep.getPage(), xStep.isActive(), xStep.getType(),
                                                                                 xStep.getLinkId(), xStep.isTimed(), StepFailure.valueOf( xStep.getFailureMode() ), xStep.isInverse(),
                                                                                 xStep.getOs(), xStep.getPoi(), xStep.getThreshold().intValue(), "", xStep.getWait().intValue(),
                                                                                 xStep.getContext(), xStep.getValidation(), xStep.getDevice(),
                                                                                 (xStep.getValidationType() != null && !xStep.getValidationType().isEmpty() ) ? ValidationType.valueOf( xStep.getValidationType() ) : null );
		    
		    parseParameters( xStep.getParameter(), testName, xStep.getName(), typeName, step );
		    parseTokens( xStep.getToken(), testName, xStep.getName(), typeName, step );
		    
		    step.addAllSteps( parseSteps( xStep.getStep(), testName, typeName ) );
		    stepList.add( step );
		}

		return stepList.toArray( new KeyWordStep[0] );
	}

	/**
	 * Parses the parameters.
	 *
	 * @param testNode the test node
	 * @param testName the test name
	 * @param stepName the step name
	 * @param typeName the type name
	 * @return the key word parameter[]
	 */
	private void parseParameters( List<Parameter> pList, String testName, String stepName, String typeName, KeyWordStep parentStep )
	{
	    if (log.isDebugEnabled())
            log.debug( "Extracted " + pList.size() + " Parameters" );
	    

	    for ( Parameter p : pList )
	    {
	        parentStep.addParameter( new KeyWordParameter( ParameterType.valueOf( p.getType() ), p.getValue() ) );
	    }
	}

	/**
	 * Parses the tokens.
	 *
	 * @param testNode the test node
	 * @param testName the test name
	 * @param stepName the step name
	 * @param typeName the type name
	 * @return the key word token[]
	 */
	private void parseTokens( List<Token> tList, String testName, String stepName, String typeName, KeyWordStep parentStep )
	{
	    if (log.isDebugEnabled())
            log.debug( "Extracted " + tList + " Tokens" );

		for ( Token t : tList )
		{
		    parentStep.addToken( new KeyWordToken( TokenType.valueOf(t.getType() ), t.getValue(), t.getName() ) );
		}

	}
}
