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
 *         &lt;element name="update-node-type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}update-node-key" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element name="update-node-uri" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}action" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "updateNodeType",
    "updateNodeKey",
    "updateNodeUri",
    "action"
})
@XmlRootElement(name = "update")
public class Update
    implements AAIDatum
{

    @XmlElement(name = "update-node-type", required = true)
    protected String updateNodeType;
    @XmlElement(name = "update-node-key")
    protected List<UpdateNodeKey> updateNodeKey;
    @XmlElement(name = "update-node-uri")
    protected String updateNodeUri;
    protected List<Action> action;

    /**
     * Gets the value of the updateNodeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateNodeType() {
        return updateNodeType;
    }

    /**
     * Sets the value of the updateNodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateNodeType(String value) {
        this.updateNodeType = value;
    }

    /**
     * Gets the value of the updateNodeKey property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the updateNodeKey property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUpdateNodeKey().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link UpdateNodeKey }
     * 
     * 
     */
    public List<UpdateNodeKey> getUpdateNodeKey() {
        if (updateNodeKey == null) {
            updateNodeKey = new ArrayList<UpdateNodeKey>();
        }
        return this.updateNodeKey;
    }

    /**
     * Gets the value of the updateNodeUri property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUpdateNodeUri() {
        return updateNodeUri;
    }

    /**
     * Sets the value of the updateNodeUri property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUpdateNodeUri(String value) {
        this.updateNodeUri = value;
    }

    /**
     * Gets the value of the action property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the action property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAction().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Action }
     * 
     * 
     */
    public List<Action> getAction() {
        if (action == null) {
            action = new ArrayList<Action>();
        }
        return this.action;
    }

}
