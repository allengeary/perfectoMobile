//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2016.04.18 at 08:47:08 PM EDT 
//


package com.perfectoMobile.page.keyWord.provider.xsd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for Import complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Import">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;attribute name="fileName" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="includeTests" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *       &lt;attribute name="includeFunctions" type="{http://www.w3.org/2001/XMLSchema}boolean" default="true" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Import")
public class Import {

    @XmlAttribute(name = "fileName", required = true)
    protected String fileName;
    @XmlAttribute(name = "includeTests")
    protected Boolean includeTests;
    @XmlAttribute(name = "includeFunctions")
    protected Boolean includeFunctions;

    /**
     * Gets the value of the fileName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * Sets the value of the fileName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFileName(String value) {
        this.fileName = value;
    }

    /**
     * Gets the value of the includeTests property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeTests() {
        if (includeTests == null) {
            return true;
        } else {
            return includeTests;
        }
    }

    /**
     * Sets the value of the includeTests property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeTests(Boolean value) {
        this.includeTests = value;
    }

    /**
     * Gets the value of the includeFunctions property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public boolean isIncludeFunctions() {
        if (includeFunctions == null) {
            return true;
        } else {
            return includeFunctions;
        }
    }

    /**
     * Sets the value of the includeFunctions property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setIncludeFunctions(Boolean value) {
        this.includeFunctions = value;
    }

}
