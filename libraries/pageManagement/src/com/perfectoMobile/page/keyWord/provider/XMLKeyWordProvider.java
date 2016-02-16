package com.perfectoMobile.page.keyWord.provider;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
import com.perfectoMobile.page.keyWord.KeyWordTest;
import com.perfectoMobile.page.keyWord.KeyWordToken;
import com.perfectoMobile.page.keyWord.KeyWordToken.TokenType;
import com.perfectoMobile.page.keyWord.step.KeyWordStepFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class XMLKeyWordProvider.
 */
public class XMLKeyWordProvider implements KeyWordProvider
{
	
	/** The Constant NAME. */
	private static final String NAME = "name";
	
	/** The Constant PAGE. */
	private static final String PAGE = "page";
	
	/** The Constant CLASS. */
	private static final String CLASS = "class";
	
	/** The Constant TYPE. */
	private static final String TYPE = "type";
	
	/** The Constant VALUE. */
	private static final String VALUE = "value";
	
	/** The Constant ACTIVE. */
	private static final String ACTIVE = "active";
	
	/** The Constant LINK. */
	private static final String LINK = "linkId";
	
	/** The Constant TIMED. */
	private static final String TIMED = "timed";
	
	/** The Constant INVERT. */
	private static final String INVERT = "inverse";
	
	/** The Constant POI. */
	private static final String POI = "poi";
	
	/** The Constant THRESHOLD. */
	private static final String THRESHOLD = "threshold";
	
	/** The Constant OS. */
	private static final String OS = "os";
	
	/** The Constant FAILURE_MODE. */
	private static final String FAILURE_MODE = "failureMode";
	
	/** The Constant DATA_PROVIDER. */
	private static final String DATA_PROVIDER = "dataProvider";
	
	/** The Constant DATA_DRIVER. */
	private static final String DATA_DRIVER = "dataDriver";
	
	/** The Constant FILE_NAME. */
	private static final String FILE_NAME = "fileName";
	
	/** The Constant INCLUDE_TESTS. */
	private static final String INCLUDE_TESTS = "includeTests";
	
	/** The Constant INCLUDE_FUNCTIONS. */
	private static final String INCLUDE_FUNCTIONS = "includeFunctions";

	/** The log. */
	private Log log = LogFactory.getLog( KeyWordTest.class );
	
	/** The x path factory. */
	private XPathFactory xPathFactory;
	
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
		xPathFactory = XPathFactory.newInstance();
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
		xPathFactory = XPathFactory.newInstance();
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

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			dbFactory.setNamespaceAware( true );
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

			Document xmlDocument = dBuilder.parse( inputStream );

			parseModel( xmlDocument );
			

			NodeList nodeList = null;
			if (readTests)
			{
				nodeList = getNodes( xmlDocument, "//suite/test" );
				for (int i = 0; i < nodeList.getLength(); i++)
				{
					Node currentNode = nodeList.item( i );
					KeyWordTest currentTest = parseTest( currentNode, "test" );

					if (KeyWordDriver.instance().getTest( currentTest.getName() ) != null)
					{
						log.warn( "The test [" + currentTest.getName() + "] is already defined and will not be added again" );
						continue;
					}

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
				nodeList = getNodes( xmlDocument, "//suite/function" );
				for (int i = 0; i < nodeList.getLength(); i++)
					KeyWordDriver.instance().addFunction( parseTest( nodeList.item( i ), "function" ) );
			}
			
			parseImports( xmlDocument );

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
	private void parseImports( Document xmlDocument )
	{
		NodeList nodeList = getNodes( xmlDocument, "//suite/import" );
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node fileName = nodeList.item( i ).getAttributes().getNamedItem( FILE_NAME );
			boolean includeTests = true;
			boolean includeFunctions = true;

			Node includeTestsNode = nodeList.item( i ).getAttributes().getNamedItem( INCLUDE_TESTS );
			if (includeTestsNode != null)
				includeTests = Boolean.parseBoolean( includeTestsNode.getNodeValue() );
			Node includeFunctionsNode = nodeList.item( i ).getAttributes().getNamedItem( INCLUDE_FUNCTIONS );
			if (includeFunctionsNode != null)
				includeFunctions = Boolean.parseBoolean( includeFunctionsNode.getNodeValue() );

			if (fileName != null)
			{
				try
				{
					if (log.isInfoEnabled())
						log.info( "Attempting to import file [" + Paths.get(".").toAbsolutePath().normalize().toString() + fileName.getNodeValue() + "]" );

					readElements( new FileInputStream( fileName.getNodeValue() ), includeTests, includeFunctions );
				}
				catch (FileNotFoundException e)
				{
					log.fatal( "Could not read from " + fileName, e );
					throw new IllegalStateException( e );
				}
			}
		}
	}

	/**
	 * Parses the model.
	 *
	 * @param xmlDocument the xml document
	 */
	private void parseModel( Document xmlDocument )
	{
		NodeList nodeList = getNodes( xmlDocument, "//suite/model/page" );
		for (int i = 0; i < nodeList.getLength(); i++)
		{
			Node name = nodeList.item( i ).getAttributes().getNamedItem( NAME );
			Node className = nodeList.item( i ).getAttributes().getNamedItem( CLASS );

			if (name != null)
			{
				try
				{
					Class useClass = KeyWordPage.class;
					if (className != null && className.getNodeValue() != null)
						useClass = ( Class<Page> ) Class.forName( className.getNodeValue() );

					if (log.isInfoEnabled())
						log.info( "Creating page as " + useClass.getSimpleName() + " for " + name.getNodeValue() );

					KeyWordDriver.instance().addPage( name.getNodeValue(), useClass );
				}
				catch (Exception e)
				{
					log.error( "Error creating instance of [" + className.getNodeValue() + "]" );
				}
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
	private KeyWordTest parseTest( Node testNode, String typeName )
	{
		Node useNode = testNode;
		Node fileNode = testNode.getAttributes().getNamedItem( "import" );

		if (fileNode != null)
		{
			String fileName = fileNode.getNodeValue();
			if (log.isInfoEnabled())
				log.info( "Importing test case from " + fileName );

			if (!fileName.isEmpty())
			{
				InputStream inputStream = null;
				try
				{
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					dbFactory.setNamespaceAware( true );
					DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();

					if (asResource)
						inputStream = getClass().getClassLoader().getResourceAsStream( fileName );
					else
						inputStream = new FileInputStream( fileName );

					Document importDocument = dBuilder.parse( inputStream );

					parseModel( importDocument );

					useNode = getNode( importDocument, "//suite/test[@name='" + testNode.getAttributes().getNamedItem( NAME ).getNodeValue() + "']" );

				}
				catch (Exception e)
				{
					log.warn( "Error reading include file " + ( asResource ? " from the classpath" : "" ) + " as " + fileName, e );
				}
				finally
				{
					try
					{
						inputStream.close();
					}
					catch (Exception e)
					{
					}
				}
			}
		}

		String testName = useNode.getAttributes().getNamedItem( NAME ).getNodeValue();

		boolean active = true;
		boolean timed = false;

		String data = null;
		String dataDriver = null;
		String linkId = null;
		int threshold = 0;
		Node pData = useNode.getAttributes().getNamedItem( DATA_PROVIDER );
		if (pData != null)
			data = pData.getNodeValue();

		pData = useNode.getAttributes().getNamedItem( DATA_DRIVER );
		if (pData != null)
			dataDriver = pData.getNodeValue();

		pData = useNode.getAttributes().getNamedItem( LINK );
		if (pData != null)
			linkId = pData.getNodeValue();

		pData = useNode.getAttributes().getNamedItem( TIMED );
		if (pData != null)
			timed = Boolean.parseBoolean( pData.getNodeValue() );
		
		pData = useNode.getAttributes().getNamedItem( THRESHOLD );
		if (pData != null)
			threshold = Integer.parseInt( pData.getNodeValue() );

		if (useNode.getAttributes().getNamedItem( ACTIVE ) != null)
			active = Boolean.parseBoolean( useNode.getAttributes().getNamedItem( ACTIVE ).getNodeValue() );

		String osString = null;
		Node os = useNode.getAttributes().getNamedItem( OS );
		if (os != null)
			osString = os.getNodeValue();

		if (log.isDebugEnabled())
			log.debug( "Extracted Test [" + testName + "]" );

		KeyWordTest test = new KeyWordTest( testName, active, data, dataDriver, timed, linkId, osString, threshold, useNode.getTextContent() );
		
		KeyWordStep[] steps = parseSteps( useNode, testName, typeName );

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
	private KeyWordStep[] parseSteps( Node testNode, String testName, String typeName )
	{
		NodeList nodeList = testNode.getChildNodes();

		if (log.isDebugEnabled())
			log.debug( "Extracted " + nodeList.getLength() + " Steps" );

		List<KeyWordStep> stepList = new ArrayList<KeyWordStep>( 10 );

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			if ("step".equals( nodeList.item( i ).getNodeName() ))
			{
				Node name = nodeList.item( i ).getAttributes().getNamedItem( NAME );
				Node pageName = nodeList.item( i ).getAttributes().getNamedItem( PAGE );

				String usePage = null;
				if (pageName != null)
					usePage = pageName.getNodeValue();

				Node type = nodeList.item( i ).getAttributes().getNamedItem( TYPE );
				Node active = nodeList.item( i ).getAttributes().getNamedItem( ACTIVE );

				if (type == null || name == null)
					throw new IllegalArgumentException( "The step is not formatted correctly" );

				String linkIdString = null;
				Node linkId = nodeList.item( i ).getAttributes().getNamedItem( LINK );
				if (linkId != null)
					linkIdString = linkId.getNodeValue();

				String osString = null;
				Node os = nodeList.item( i ).getAttributes().getNamedItem( OS );
				if (os != null)
					osString = os.getNodeValue();
				
				String poiString = null;
				Node poi = nodeList.item( i ).getAttributes().getNamedItem( POI );
				if (poi != null)
					poiString = poi.getNodeValue();
				
				int tInt = 0;
				Node t = nodeList.item( i ).getAttributes().getNamedItem( THRESHOLD );
				if (t != null)
					tInt = Integer.parseInt( t.getNodeValue() );

				boolean timed = false;
				Node timedCall = nodeList.item( i ).getAttributes().getNamedItem( TIMED );
				if (timedCall != null)
					timed = Boolean.parseBoolean( timedCall.getNodeValue() );

				boolean inverse = false;
				Node inverseData = nodeList.item( i ).getAttributes().getNamedItem( INVERT );
				if (inverseData != null)
					inverse = Boolean.parseBoolean( inverseData.getNodeValue() );

				StepFailure sFailure = StepFailure.ERROR;
				Node sFailureNode = nodeList.item( i ).getAttributes().getNamedItem( FAILURE_MODE );
				if (sFailureNode != null && !sFailureNode.getNodeValue().isEmpty() )
					sFailure = StepFailure.valueOf( sFailureNode.getNodeValue() );

				KeyWordStep step = KeyWordStepFactory.instance().createStep( name.getNodeValue(), usePage, active == null ? true : Boolean.parseBoolean( active.getNodeValue() ), type.getNodeValue().toUpperCase(), linkIdString, timed, sFailure, inverse, osString, poiString, tInt, nodeList.item( i ).getTextContent() );

				KeyWordParameter[] params = parseParameters( nodeList.item( i ), testName, name.getNodeValue(), typeName );

				for (KeyWordParameter param : params)
				{
					step.addParameter( param );
				}

				KeyWordToken[] tokens = parseTokens( nodeList.item( i ), testName, name.getNodeValue(), typeName );
				for (KeyWordToken token : tokens)
				{
					step.addToken( token );
				}

				//
				// Recursively add conditional sub steps
				//
				step.addAllSteps( parseSteps( nodeList.item( i ), testName, typeName ) );
				stepList.add( step );
			}
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
	private KeyWordParameter[] parseParameters( Node testNode, String testName, String stepName, String typeName )
	{
		List<KeyWordParameter> paramList = new ArrayList<KeyWordParameter>( 10 );
		NodeList nodeList = testNode.getChildNodes();

		if (log.isDebugEnabled())
			log.debug( "Extracted " + nodeList.getLength() + " Parameters" );

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			if (nodeList.item( i ).getNodeName().equalsIgnoreCase( "parameter" ))
			{
				Node type = nodeList.item( i ).getAttributes().getNamedItem( TYPE );
				Node value = nodeList.item( i ).getAttributes().getNamedItem( VALUE );

				if (type == null || value == null)
					throw new IllegalArgumentException( "The parameter is not formatted correctly" );

				paramList.add( new KeyWordParameter( ParameterType.valueOf( type.getNodeValue().toUpperCase() ), value.getNodeValue() ) );
			}
		}

		return paramList.toArray( new KeyWordParameter[0] );
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
	private KeyWordToken[] parseTokens( Node testNode, String testName, String stepName, String typeName )
	{
		List<KeyWordToken> paramList = new ArrayList<KeyWordToken>( 10 );
		NodeList nodeList = testNode.getChildNodes();

		if (log.isDebugEnabled())
			log.debug( "Extracted " + nodeList.getLength() + " Tokens" );

		for (int i = 0; i < nodeList.getLength(); i++)
		{
			if (nodeList.item( i ).getNodeName().equalsIgnoreCase( "token" ))
			{
				Node type = nodeList.item( i ).getAttributes().getNamedItem( TYPE );
				Node value = nodeList.item( i ).getAttributes().getNamedItem( VALUE );
				Node name = nodeList.item( i ).getAttributes().getNamedItem( NAME );

				if (type == null || value == null || name == null)
					throw new IllegalArgumentException( "The token is not formatted correctly - you must specify the name, value and type" );

				paramList.add( new KeyWordToken( TokenType.valueOf( type.getNodeValue().toUpperCase() ), value.getNodeValue(), name.getNodeValue() ) );
			}
		}

		return paramList.toArray( new KeyWordToken[0] );
	}

	/**
	 * Gets the nodes.
	 *
	 * @param xmlDocument the xml document
	 * @param xPathExpression the x path expression
	 * @return the nodes
	 */
	private NodeList getNodes( Document xmlDocument, String xPathExpression )
	{
		try
		{
			if (log.isDebugEnabled())
				log.debug( "Attempting to return Nodes for [" + xPathExpression + "]" );

			XPath xPath = xPathFactory.newXPath();
			return ( NodeList ) xPath.evaluate( xPathExpression, xmlDocument, XPathConstants.NODESET );
		}
		catch (Exception e)
		{
			log.error( "Error parsing xPath Expression [" + xPathExpression + "]" );
			return null;
		}
	}

	/**
	 * Gets the node.
	 *
	 * @param xmlDocument the xml document
	 * @param xPathExpression the x path expression
	 * @return the node
	 */
	private Node getNode( Document xmlDocument, String xPathExpression )
	{
		try
		{
			if (log.isDebugEnabled())
				log.debug( "Attempting to return Node for [" + xPathExpression + "]" );

			XPath xPath = xPathFactory.newXPath();
			return ( Node ) xPath.evaluate( xPathExpression, xmlDocument, XPathConstants.NODE );
		}
		catch (Exception e)
		{
			log.error( "Error parsing xPath Expression [" + xPathExpression + "]" );
			return null;
		}
	}
}
