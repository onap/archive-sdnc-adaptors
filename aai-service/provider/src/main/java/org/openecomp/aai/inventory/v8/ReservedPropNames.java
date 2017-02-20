//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.20 at 02:31:54 PM EST 
//


package org.openecomp.aai.inventory.v8;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
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
 *         &lt;element name="last-mod-source-of-truth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aai-node-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aai-created-ts" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/&gt;
 *         &lt;element name="aai-unique-key" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="aai-last-mod-ts" type="{http://www.w3.org/2001/XMLSchema}unsignedInt" minOccurs="0"/&gt;
 *         &lt;element name="source-of-truth" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "lastModSourceOfTruth",
    "aaiNodeType",
    "aaiCreatedTs",
    "aaiUniqueKey",
    "aaiLastModTs",
    "sourceOfTruth"
})
@XmlRootElement(name = "reserved-prop-names")
public class ReservedPropNames
    implements AAIDatum
{

    @XmlElement(name = "last-mod-source-of-truth")
    protected String lastModSourceOfTruth;
    @XmlElement(name = "aai-node-type")
    protected String aaiNodeType;
    @XmlElement(name = "aai-created-ts")
    @XmlSchemaType(name = "unsignedInt")
    protected Long aaiCreatedTs;
    @XmlElement(name = "aai-unique-key")
    protected String aaiUniqueKey;
    @XmlElement(name = "aai-last-mod-ts")
    @XmlSchemaType(name = "unsignedInt")
    protected Long aaiLastModTs;
    @XmlElement(name = "source-of-truth")
    protected String sourceOfTruth;

    /**
     * Gets the value of the lastModSourceOfTruth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastModSourceOfTruth() {
        return lastModSourceOfTruth;
    }

    /**
     * Sets the value of the lastModSourceOfTruth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastModSourceOfTruth(String value) {
        this.lastModSourceOfTruth = value;
    }

    /**
     * Gets the value of the aaiNodeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAaiNodeType() {
        return aaiNodeType;
    }

    /**
     * Sets the value of the aaiNodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAaiNodeType(String value) {
        this.aaiNodeType = value;
    }

    /**
     * Gets the value of the aaiCreatedTs property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAaiCreatedTs() {
        return aaiCreatedTs;
    }

    /**
     * Sets the value of the aaiCreatedTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAaiCreatedTs(Long value) {
        this.aaiCreatedTs = value;
    }

    /**
     * Gets the value of the aaiUniqueKey property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAaiUniqueKey() {
        return aaiUniqueKey;
    }

    /**
     * Sets the value of the aaiUniqueKey property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAaiUniqueKey(String value) {
        this.aaiUniqueKey = value;
    }

    /**
     * Gets the value of the aaiLastModTs property.
     * 
     * @return
     *     possible object is
     *     {@link Long }
     *     
     */
    public Long getAaiLastModTs() {
        return aaiLastModTs;
    }

    /**
     * Sets the value of the aaiLastModTs property.
     * 
     * @param value
     *     allowed object is
     *     {@link Long }
     *     
     */
    public void setAaiLastModTs(Long value) {
        this.aaiLastModTs = value;
    }

    /**
     * Gets the value of the sourceOfTruth property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceOfTruth() {
        return sourceOfTruth;
    }

    /**
     * Sets the value of the sourceOfTruth property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceOfTruth(String value) {
        this.sourceOfTruth = value;
    }

}
