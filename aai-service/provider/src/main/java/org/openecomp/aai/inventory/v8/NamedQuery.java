//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.19 at 04:48:44 PM EST 
//


package org.openecomp.aai.inventory.v8;

import java.util.ArrayList;
import java.util.List;
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
 *         &lt;element name="named-query-uuid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="named-query-name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="named-query-version" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="required-input-params" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="required-input-param" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *         &lt;element name="description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}named-query-elements" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}relationship-list" minOccurs="0"/&gt;
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
    "namedQueryUuid",
    "namedQueryName",
    "namedQueryVersion",
    "requiredInputParams",
    "description",
    "resourceVersion",
    "namedQueryElements",
    "relationshipList"
})
@XmlRootElement(name = "named-query")
public class NamedQuery
    implements AAIDatum
{

    @XmlElement(name = "named-query-uuid", required = true)
    protected String namedQueryUuid;
    @XmlElement(name = "named-query-name", required = true)
    protected String namedQueryName;
    @XmlElement(name = "named-query-version", required = true)
    protected String namedQueryVersion;
    @XmlElement(name = "required-input-params")
    protected NamedQuery.RequiredInputParams requiredInputParams;
    protected String description;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "named-query-elements")
    protected NamedQueryElements namedQueryElements;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;

    /**
     * Gets the value of the namedQueryUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamedQueryUuid() {
        return namedQueryUuid;
    }

    /**
     * Sets the value of the namedQueryUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamedQueryUuid(String value) {
        this.namedQueryUuid = value;
    }

    /**
     * Gets the value of the namedQueryName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamedQueryName() {
        return namedQueryName;
    }

    /**
     * Sets the value of the namedQueryName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamedQueryName(String value) {
        this.namedQueryName = value;
    }

    /**
     * Gets the value of the namedQueryVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNamedQueryVersion() {
        return namedQueryVersion;
    }

    /**
     * Sets the value of the namedQueryVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNamedQueryVersion(String value) {
        this.namedQueryVersion = value;
    }

    /**
     * Gets the value of the requiredInputParams property.
     * 
     * @return
     *     possible object is
     *     {@link NamedQuery.RequiredInputParams }
     *     
     */
    public NamedQuery.RequiredInputParams getRequiredInputParams() {
        return requiredInputParams;
    }

    /**
     * Sets the value of the requiredInputParams property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedQuery.RequiredInputParams }
     *     
     */
    public void setRequiredInputParams(NamedQuery.RequiredInputParams value) {
        this.requiredInputParams = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
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
     * Gets the value of the namedQueryElements property.
     * 
     * @return
     *     possible object is
     *     {@link NamedQueryElements }
     *     
     */
    public NamedQueryElements getNamedQueryElements() {
        return namedQueryElements;
    }

    /**
     * Sets the value of the namedQueryElements property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedQueryElements }
     *     
     */
    public void setNamedQueryElements(NamedQueryElements value) {
        this.namedQueryElements = value;
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
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="required-input-param" type="{http://www.w3.org/2001/XMLSchema}string" maxOccurs="unbounded" minOccurs="0"/&gt;
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
        "requiredInputParam"
    })
    public static class RequiredInputParams
        implements AAIDatum
    {

        @XmlElement(name = "required-input-param")
        protected List<String> requiredInputParam;

        /**
         * Gets the value of the requiredInputParam property.
         * 
         * <p>
         * This accessor method returns a reference to the live list,
         * not a snapshot. Therefore any modification you make to the
         * returned list will be present inside the JAXB object.
         * This is why there is not a <CODE>set</CODE> method for the requiredInputParam property.
         * 
         * <p>
         * For example, to add a new item, do as follows:
         * <pre>
         *    getRequiredInputParam().add(newItem);
         * </pre>
         * 
         * 
         * <p>
         * Objects of the following type(s) are allowed in the list
         * {@link String }
         * 
         * 
         */
        public List<String> getRequiredInputParam() {
            if (requiredInputParam == null) {
                requiredInputParam = new ArrayList<String>();
            }
            return this.requiredInputParam;
        }

    }

}
