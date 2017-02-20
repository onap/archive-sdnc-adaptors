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
 *         &lt;element name="subnet-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="subnet-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="neutron-subnet-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="gateway-address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="network-start-address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="cidr-mask" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ip-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orchestration-status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dhcp-enabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="dhcp-start" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="dhcp-end" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "subnetId",
    "subnetName",
    "neutronSubnetId",
    "gatewayAddress",
    "networkStartAddress",
    "cidrMask",
    "ipVersion",
    "orchestrationStatus",
    "dhcpEnabled",
    "dhcpStart",
    "dhcpEnd",
    "resourceVersion",
    "relationshipList"
})
@XmlRootElement(name = "subnet")
public class Subnet
    implements AAIDatum
{

    @XmlElement(name = "subnet-id", required = true)
    protected String subnetId;
    @XmlElement(name = "subnet-name")
    protected String subnetName;
    @XmlElement(name = "neutron-subnet-id")
    protected String neutronSubnetId;
    @XmlElement(name = "gateway-address")
    protected String gatewayAddress;
    @XmlElement(name = "network-start-address")
    protected String networkStartAddress;
    @XmlElement(name = "cidr-mask")
    protected String cidrMask;
    @XmlElement(name = "ip-version")
    protected String ipVersion;
    @XmlElement(name = "orchestration-status")
    protected String orchestrationStatus;
    @XmlElement(name = "dhcp-enabled")
    protected boolean dhcpEnabled;
    @XmlElement(name = "dhcp-start")
    protected String dhcpStart;
    @XmlElement(name = "dhcp-end")
    protected String dhcpEnd;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;

    /**
     * Gets the value of the subnetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubnetId() {
        return subnetId;
    }

    /**
     * Sets the value of the subnetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubnetId(String value) {
        this.subnetId = value;
    }

    /**
     * Gets the value of the subnetName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSubnetName() {
        return subnetName;
    }

    /**
     * Sets the value of the subnetName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSubnetName(String value) {
        this.subnetName = value;
    }

    /**
     * Gets the value of the neutronSubnetId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNeutronSubnetId() {
        return neutronSubnetId;
    }

    /**
     * Sets the value of the neutronSubnetId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNeutronSubnetId(String value) {
        this.neutronSubnetId = value;
    }

    /**
     * Gets the value of the gatewayAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGatewayAddress() {
        return gatewayAddress;
    }

    /**
     * Sets the value of the gatewayAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGatewayAddress(String value) {
        this.gatewayAddress = value;
    }

    /**
     * Gets the value of the networkStartAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNetworkStartAddress() {
        return networkStartAddress;
    }

    /**
     * Sets the value of the networkStartAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNetworkStartAddress(String value) {
        this.networkStartAddress = value;
    }

    /**
     * Gets the value of the cidrMask property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCidrMask() {
        return cidrMask;
    }

    /**
     * Sets the value of the cidrMask property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCidrMask(String value) {
        this.cidrMask = value;
    }

    /**
     * Gets the value of the ipVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpVersion() {
        return ipVersion;
    }

    /**
     * Sets the value of the ipVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpVersion(String value) {
        this.ipVersion = value;
    }

    /**
     * Gets the value of the orchestrationStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrchestrationStatus() {
        return orchestrationStatus;
    }

    /**
     * Sets the value of the orchestrationStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrchestrationStatus(String value) {
        this.orchestrationStatus = value;
    }

    /**
     * Gets the value of the dhcpEnabled property.
     * 
     */
    public boolean isDhcpEnabled() {
        return dhcpEnabled;
    }

    /**
     * Sets the value of the dhcpEnabled property.
     * 
     */
    public void setDhcpEnabled(boolean value) {
        this.dhcpEnabled = value;
    }

    /**
     * Gets the value of the dhcpStart property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDhcpStart() {
        return dhcpStart;
    }

    /**
     * Sets the value of the dhcpStart property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDhcpStart(String value) {
        this.dhcpStart = value;
    }

    /**
     * Gets the value of the dhcpEnd property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDhcpEnd() {
        return dhcpEnd;
    }

    /**
     * Sets the value of the dhcpEnd property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDhcpEnd(String value) {
        this.dhcpEnd = value;
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
