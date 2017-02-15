//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.14 at 01:05:25 PM EST 
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
 *         &lt;element name="cambria.partition" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}notification-event-header" minOccurs="0"/&gt;
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
    "cambriaPartition",
    "notificationEventHeader"
})
@XmlRootElement(name = "notification-event")
public class NotificationEvent
    implements AAIDatum
{

    @XmlElement(name = "cambria.partition")
    protected String cambriaPartition;
    @XmlElement(name = "notification-event-header")
    protected NotificationEventHeader notificationEventHeader;

    /**
     * Gets the value of the cambriaPartition property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCambriaPartition() {
        return cambriaPartition;
    }

    /**
     * Sets the value of the cambriaPartition property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCambriaPartition(String value) {
        this.cambriaPartition = value;
    }

    /**
     * Gets the value of the notificationEventHeader property.
     * 
     * @return
     *     possible object is
     *     {@link NotificationEventHeader }
     *     
     */
    public NotificationEventHeader getNotificationEventHeader() {
        return notificationEventHeader;
    }

    /**
     * Sets the value of the notificationEventHeader property.
     * 
     * @param value
     *     allowed object is
     *     {@link NotificationEventHeader }
     *     
     */
    public void setNotificationEventHeader(NotificationEventHeader value) {
        this.notificationEventHeader = value;
    }

}
