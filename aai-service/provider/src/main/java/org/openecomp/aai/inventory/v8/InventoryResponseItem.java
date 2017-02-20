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
 *         &lt;element name="model-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}extra-properties" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}inventory-response-items" minOccurs="0"/&gt;
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
    "modelName",
    "extraProperties",
    "inventoryResponseItems"
})
@XmlRootElement(name = "inventory-response-item")
public class InventoryResponseItem
    implements AAIDatum
{

    @XmlElement(name = "model-name")
    protected String modelName;
    @XmlElement(name = "extra-properties")
    protected ExtraProperties extraProperties;
    @XmlElement(name = "inventory-response-items")
    protected InventoryResponseItems inventoryResponseItems;

    /**
     * Gets the value of the modelName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getModelName() {
        return modelName;
    }

    /**
     * Sets the value of the modelName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setModelName(String value) {
        this.modelName = value;
    }

    /**
     * Gets the value of the extraProperties property.
     * 
     * @return
     *     possible object is
     *     {@link ExtraProperties }
     *     
     */
    public ExtraProperties getExtraProperties() {
        return extraProperties;
    }

    /**
     * Sets the value of the extraProperties property.
     * 
     * @param value
     *     allowed object is
     *     {@link ExtraProperties }
     *     
     */
    public void setExtraProperties(ExtraProperties value) {
        this.extraProperties = value;
    }

    /**
     * Gets the value of the inventoryResponseItems property.
     * 
     * @return
     *     possible object is
     *     {@link InventoryResponseItems }
     *     
     */
    public InventoryResponseItems getInventoryResponseItems() {
        return inventoryResponseItems;
    }

    /**
     * Sets the value of the inventoryResponseItems property.
     * 
     * @param value
     *     allowed object is
     *     {@link InventoryResponseItems }
     *     
     */
    public void setInventoryResponseItems(InventoryResponseItems value) {
        this.inventoryResponseItems = value;
    }

}
