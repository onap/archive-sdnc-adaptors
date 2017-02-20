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
 *         &lt;element name="event-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="node-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="event-trigger" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}key-data" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="selflink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "eventId",
    "nodeType",
    "eventTrigger",
    "keyData",
    "selflink"
})
@XmlRootElement(name = "notify")
public class Notify
    implements AAIDatum
{

    @XmlElement(name = "event-id", required = true)
    protected String eventId;
    @XmlElement(name = "node-type")
    protected String nodeType;
    @XmlElement(name = "event-trigger")
    protected String eventTrigger;
    @XmlElement(name = "key-data")
    protected List<KeyData> keyData;
    protected String selflink;

    /**
     * Gets the value of the eventId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventId() {
        return eventId;
    }

    /**
     * Sets the value of the eventId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventId(String value) {
        this.eventId = value;
    }

    /**
     * Gets the value of the nodeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNodeType() {
        return nodeType;
    }

    /**
     * Sets the value of the nodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNodeType(String value) {
        this.nodeType = value;
    }

    /**
     * Gets the value of the eventTrigger property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEventTrigger() {
        return eventTrigger;
    }

    /**
     * Sets the value of the eventTrigger property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEventTrigger(String value) {
        this.eventTrigger = value;
    }

    /**
     * Gets the value of the keyData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the keyData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKeyData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link KeyData }
     * 
     * 
     */
    public List<KeyData> getKeyData() {
        if (keyData == null) {
            keyData = new ArrayList<KeyData>();
        }
        return this.keyData;
    }

    /**
     * Gets the value of the selflink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSelflink() {
        return selflink;
    }

    /**
     * Sets the value of the selflink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSelflink(String value) {
        this.selflink = value;
    }

}
