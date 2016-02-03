<<<<<<< HEAD
package com.perfectomobile.integration.model;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface XMLModel
{
	public NodeList getNodes( String xPathExpression ) throws XPathExpressionException;
	
	public Node getNode( String xPathExpression ) throws XPathExpressionException;

	public String getAttribute( String xPathExpression, String attributeName ) throws XPathExpressionException;
}
=======
package com.perfectomobile.integration.model;

import javax.xml.xpath.XPathExpressionException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public interface XMLModel
{
	public NodeList getNodes( String xPathExpression ) throws XPathExpressionException;
	
	public Node getNode( String xPathExpression ) throws XPathExpressionException;

	public String getAttribute( String xPathExpression, String attributeName ) throws XPathExpressionException;
}
>>>>>>> branch 'master' of https://github.com/allengeary/perfectoMobile.git
