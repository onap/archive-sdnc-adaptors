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
 *         &lt;element name="interface-name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="interface-role" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="v6-wan-link-ip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="selflink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="interface-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="macaddr" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="network-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="management-option" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}vlans" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}relationship-list" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}l3-interface-ipv4-address-list" maxOccurs="unbounded" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}l3-interface-ipv6-address-list" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "interfaceRole",
    "v6WanLinkIp",
    "selflink",
    "interfaceId",
    "macaddr",
    "networkName",
    "managementOption",
    "resourceVersion",
    "vlans",
    "relationshipList",
    "l3InterfaceIpv4AddressList",
    "l3InterfaceIpv6AddressList"
})
@XmlRootElement(name = "l-interface")
public class LInterface
    implements AAIDatum
{

    @XmlElement(name = "interface-name", required = true)
    protected String interfaceName;
    @XmlElement(name = "interface-role")
    protected String interfaceRole;
    @XmlElement(name = "v6-wan-link-ip")
    protected String v6WanLinkIp;
    protected String selflink;
    @XmlElement(name = "interface-id")
    protected String interfaceId;
    protected String macaddr;
    @XmlElement(name = "network-name")
    protected String networkName;
    @XmlElement(name = "management-option")
    protected String managementOption;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    protected Vlans vlans;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;
    @XmlElement(name = "l3-interface-ipv4-address-list")
    protected List<L3InterfaceIpv4AddressList> l3InterfaceIpv4AddressList;
    @XmlElement(name = "l3-interface-ipv6-address-list")
    protected List<L3InterfaceIpv6AddressList> l3InterfaceIpv6AddressList;

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
     * Gets the value of the interfaceRole property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceRole() {
        return interfaceRole;
    }

    /**
     * Sets the value of the interfaceRole property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceRole(String value) {
        this.interfaceRole = value;
    }

    /**
     * Gets the value of the v6WanLinkIp property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getV6WanLinkIp() {
        return v6WanLinkIp;
    }

    /**
     * Sets the value of the v6WanLinkIp property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setV6WanLinkIp(String value) {
        this.v6WanLinkIp = value;
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

    /**
     * Gets the value of the interfaceId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInterfaceId() {
        return interfaceId;
    }

    /**
     * Sets the value of the interfaceId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInterfaceId(String value) {
        this.interfaceId = value;
    }

    /**
     * Gets the value of the macaddr property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMacaddr() {
        return macaddr;
    }

    /**
     * Sets the value of the macaddr property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMacaddr(String value) {
        this.macaddr = value;
    }

    /**
     * Gets the value of the networkName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetworkName() {
        return networkName;
    }

    /**
     * Sets the value of the networkName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetworkName(String value) {
        this.networkName = value;
    }

    /**
     * Gets the value of the managementOption property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getManagementOption() {
        return managementOption;
    }

    /**
     * Sets the value of the managementOption property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setManagementOption(String value) {
        this.managementOption = value;
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
     * Gets the value of the vlans property.
     * 
     * @return
     *     possible object is
     *     {@link Vlans }
     *     
     */
    public Vlans getVlans() {
        return vlans;
    }

    /**
     * Sets the value of the vlans property.
     * 
     * @param value
     *     allowed object is
     *     {@link Vlans }
     *     
     */
    public void setVlans(Vlans value) {
        this.vlans = value;
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
     * Gets the value of the l3InterfaceIpv4AddressList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the l3InterfaceIpv4AddressList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getL3InterfaceIpv4AddressList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link L3InterfaceIpv4AddressList }
     * 
     * 
     */
    public List<L3InterfaceIpv4AddressList> getL3InterfaceIpv4AddressList() {
        if (l3InterfaceIpv4AddressList == null) {
            l3InterfaceIpv4AddressList = new ArrayList<L3InterfaceIpv4AddressList>();
        }
        return this.l3InterfaceIpv4AddressList;
    }

    /**
     * Gets the value of the l3InterfaceIpv6AddressList property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the l3InterfaceIpv6AddressList property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getL3InterfaceIpv6AddressList().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link L3InterfaceIpv6AddressList }
     * 
     * 
     */
    public List<L3InterfaceIpv6AddressList> getL3InterfaceIpv6AddressList() {
        if (l3InterfaceIpv6AddressList == null) {
            l3InterfaceIpv6AddressList = new ArrayList<L3InterfaceIpv6AddressList>();
        }
        return this.l3InterfaceIpv6AddressList;
    }

}
