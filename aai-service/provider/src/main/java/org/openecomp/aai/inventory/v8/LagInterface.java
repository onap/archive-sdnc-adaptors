//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.19 at 04:48:44 PM EST 
//


package org.openecomp.aai.inventory.v8;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.openecomp.sdnc.sli.aai.data.AAIDatum;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="interface-name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="speed-value" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="speed-units" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}relationship-list" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}l-interfaces" minOccurs="0"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "interfaceName",
    "resourceVersion",
    "speedValue",
    "speedUnits",
    "relationshipList",
    "lInterfaces"
})
@XmlRootElement(name = "lag-interface")
public class LagInterface
    implements AAIDatum
{

    @XmlElement(name = "interface-name", required = true)
    protected String interfaceName;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "speed-value")
    protected String speedValue;
    @XmlElement(name = "speed-units")
    protected String speedUnits;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;
    @XmlElement(name = "l-interfaces")
    protected LInterfaces lInterfaces;

    /**
     * Gets the value of the interfaceName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceName() {
        return interfaceName;
    }

    /**
     * Sets the value of the interfaceName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceName(String value) {
        this.interfaceName = value;
    }

    /**
     * Gets the value of the resourceVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResourceVersion() {
        return resourceVersion;
    }

    /**
     * Sets the value of the resourceVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResourceVersion(String value) {
        this.resourceVersion = value;
    }

    /**
     * Gets the value of the speedValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeedValue() {
        return speedValue;
    }

    /**
     * Sets the value of the speedValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeedValue(String value) {
        this.speedValue = value;
    }

    /**
     * Gets the value of the speedUnits property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSpeedUnits() {
        return speedUnits;
    }

    /**
     * Sets the value of the speedUnits property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSpeedUnits(String value) {
        this.speedUnits = value;
    }

    /**
     * Gets the value of the relationshipList property.
     * 
     * @return
     *     possible object is
     *     {@link RelationshipList }
     *     
     */
    public RelationshipList getRelationshipList() {
        return relationshipList;
    }

    /**
     * Sets the value of the relationshipList property.
     * 
     * @param value
     *     allowed object is
     *     {@link RelationshipList }
     *     
     */
    public void setRelationshipList(RelationshipList value) {
        this.relationshipList = value;
    }

    /**
     * Gets the value of the lInterfaces property.
     * 
     * @return
     *     possible object is
     *     {@link LInterfaces }
     *     
     */
    public LInterfaces getLInterfaces() {
        return lInterfaces;
    }

    /**
     * Sets the value of the lInterfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link LInterfaces }
     *     
     */
    public void setLInterfaces(LInterfaces value) {
        this.lInterfaces = value;
    }

}
