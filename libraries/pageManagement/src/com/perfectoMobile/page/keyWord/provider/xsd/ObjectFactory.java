//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.18 at 08:47:08 PM EDT 
//


package com.perfectoMobile.page.keyWord.provider.xsd;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.perfectoMobile.page.keyWord.provider.xsd package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Suite_QNAME = new QName("http://www.morelandlabs.com/testRegistry", "suite");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.perfectoMobile.page.keyWord.provider.xsd
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link RegistryRoot }
     * 
     */
    public RegistryRoot createRegistryRoot() {
        return new RegistryRoot();
    }

    /**
     * Create an instance of {@link Import }
     * 
     */
    public Import createImport() {
        return new Import();
    }

    /**
     * Create an instance of {@link Test }
     * 
     */
    public Test createTest() {
        return new Test();
    }

    /**
     * Create an instance of {@link Model }
     * 
     */
    public Model createModel() {
        return new Model();
    }

    /**
     * Create an instance of {@link Parameter }
     * 
     */
    public Parameter createParameter() {
        return new Parameter();
    }

    /**
     * Create an instance of {@link Page }
     * 
     */
    public Page createPage() {
        return new Page();
    }

    /**
     * Create an instance of {@link Token }
     * 
     */
    public Token createToken() {
        return new Token();
    }

    /**
     * Create an instance of {@link Step }
     * 
     */
    public Step createStep() {
        return new Step();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RegistryRoot }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://www.morelandlabs.com/testRegistry", name = "suite")
    public JAXBElement<RegistryRoot> createSuite(RegistryRoot value) {
        return new JAXBElement<RegistryRoot>(_Suite_QNAME, RegistryRoot.class, null, value);
    }

}
