//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.21 at 07:59:51 PM EST 
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
 *         &lt;element name="constrained-element-set-uuid" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="constraint-type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="check-type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}element-choice-sets" minOccurs="0"/&gt;
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
    "constrainedElementSetUuid",
    "constraintType",
    "checkType",
    "resourceVersion",
    "elementChoiceSets",
    "relationshipList"
})
@XmlRootElement(name = "constrained-element-set")
public class ConstrainedElementSet
    implements AAIDatum
{

    @XmlElement(name = "constrained-element-set-uuid", required = true)
    protected String constrainedElementSetUuid;
    @XmlElement(name = "constraint-type", required = true)
    protected String constraintType;
    @XmlElement(name = "check-type", required = true)
    protected String checkType;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "element-choice-sets")
    protected ElementChoiceSets elementChoiceSets;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;

    /**
     * Gets the value of the constrainedElementSetUuid property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstrainedElementSetUuid() {
        return constrainedElementSetUuid;
    }

    /**
     * Sets the value of the constrainedElementSetUuid property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstrainedElementSetUuid(String value) {
        this.constrainedElementSetUuid = value;
    }

    /**
     * Gets the value of the constraintType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConstraintType() {
        return constraintType;
    }

    /**
     * Sets the value of the constraintType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConstraintType(String value) {
        this.constraintType = value;
    }

    /**
     * Gets the value of the checkType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCheckType() {
        return checkType;
    }

    /**
     * Sets the value of the checkType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCheckType(String value) {
        this.checkType = value;
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
     * Gets the value of the elementChoiceSets property.
     * 
     * @return
     *     possible object is
     *     {@link ElementChoiceSets }
     *     
     */
    public ElementChoiceSets getElementChoiceSets() {
        return elementChoiceSets;
    }

    /**
     * Sets the value of the elementChoiceSets property.
     * 
     * @param value
     *     allowed object is
     *     {@link ElementChoiceSets }
     *     
     */
    public void setElementChoiceSets(ElementChoiceSets value) {
        this.elementChoiceSets = value;
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

}
