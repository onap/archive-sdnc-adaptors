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
 *         &lt;element name="hostname" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="ptnii-equip-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="number-of-cpus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="disk-in-gigabytes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="ram-in-megabytes" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="equip-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="equip-vendor" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="equip-model" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="fqdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pserver-selflink" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ipv4-oam-address" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="serial-number" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pserver-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="internet-topology" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in-maint" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="pserver-name2" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="purpose" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}relationship-list" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}p-interfaces" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}lag-interfaces" minOccurs="0"/&gt;
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
    "hostname",
    "ptniiEquipName",
    "numberOfCpus",
    "diskInGigabytes",
    "ramInMegabytes",
    "equipType",
    "equipVendor",
    "equipModel",
    "fqdn",
    "pserverSelflink",
    "ipv4OamAddress",
    "serialNumber",
    "pserverId",
    "internetTopology",
    "inMaint",
    "resourceVersion",
    "pserverName2",
    "purpose",
    "relationshipList",
    "pInterfaces",
    "lagInterfaces"
})
@XmlRootElement(name = "pserver")
public class Pserver
    implements AAIDatum
{

    @XmlElement(required = true)
    protected String hostname;
    @XmlElement(name = "ptnii-equip-name")
    protected String ptniiEquipName;
    @XmlElement(name = "number-of-cpus")
    protected Integer numberOfCpus;
    @XmlElement(name = "disk-in-gigabytes")
    protected Integer diskInGigabytes;
    @XmlElement(name = "ram-in-megabytes")
    protected Integer ramInMegabytes;
    @XmlElement(name = "equip-type")
    protected String equipType;
    @XmlElement(name = "equip-vendor")
    protected String equipVendor;
    @XmlElement(name = "equip-model")
    protected String equipModel;
    protected String fqdn;
    @XmlElement(name = "pserver-selflink")
    protected String pserverSelflink;
    @XmlElement(name = "ipv4-oam-address")
    protected String ipv4OamAddress;
    @XmlElement(name = "serial-number")
    protected String serialNumber;
    @XmlElement(name = "pserver-id")
    protected String pserverId;
    @XmlElement(name = "internet-topology")
    protected String internetTopology;
    @XmlElement(name = "in-maint")
    protected boolean inMaint;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "pserver-name2")
    protected String pserverName2;
    protected String purpose;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;
    @XmlElement(name = "p-interfaces")
    protected PInterfaces pInterfaces;
    @XmlElement(name = "lag-interfaces")
    protected LagInterfaces lagInterfaces;

    /**
     * Gets the value of the hostname property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHostname() {
        return hostname;
    }

    /**
     * Sets the value of the hostname property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHostname(String value) {
        this.hostname = value;
    }

    /**
     * Gets the value of the ptniiEquipName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPtniiEquipName() {
        return ptniiEquipName;
    }

    /**
     * Sets the value of the ptniiEquipName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPtniiEquipName(String value) {
        this.ptniiEquipName = value;
    }

    /**
     * Gets the value of the numberOfCpus property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getNumberOfCpus() {
        return numberOfCpus;
    }

    /**
     * Sets the value of the numberOfCpus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setNumberOfCpus(Integer value) {
        this.numberOfCpus = value;
    }

    /**
     * Gets the value of the diskInGigabytes property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getDiskInGigabytes() {
        return diskInGigabytes;
    }

    /**
     * Sets the value of the diskInGigabytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setDiskInGigabytes(Integer value) {
        this.diskInGigabytes = value;
    }

    /**
     * Gets the value of the ramInMegabytes property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getRamInMegabytes() {
        return ramInMegabytes;
    }

    /**
     * Sets the value of the ramInMegabytes property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setRamInMegabytes(Integer value) {
        this.ramInMegabytes = value;
    }

    /**
     * Gets the value of the equipType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquipType() {
        return equipType;
    }

    /**
     * Sets the value of the equipType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquipType(String value) {
        this.equipType = value;
    }

    /**
     * Gets the value of the equipVendor property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquipVendor() {
        return equipVendor;
    }

    /**
     * Sets the value of the equipVendor property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquipVendor(String value) {
        this.equipVendor = value;
    }

    /**
     * Gets the value of the equipModel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEquipModel() {
        return equipModel;
    }

    /**
     * Sets the value of the equipModel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEquipModel(String value) {
        this.equipModel = value;
    }

    /**
     * Gets the value of the fqdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFqdn() {
        return fqdn;
    }

    /**
     * Sets the value of the fqdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFqdn(String value) {
        this.fqdn = value;
    }

    /**
     * Gets the value of the pserverSelflink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPserverSelflink() {
        return pserverSelflink;
    }

    /**
     * Sets the value of the pserverSelflink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPserverSelflink(String value) {
        this.pserverSelflink = value;
    }

    /**
     * Gets the value of the ipv4OamAddress property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpv4OamAddress() {
        return ipv4OamAddress;
    }

    /**
     * Sets the value of the ipv4OamAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpv4OamAddress(String value) {
        this.ipv4OamAddress = value;
    }

    /**
     * Gets the value of the serialNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSerialNumber() {
        return serialNumber;
    }

    /**
     * Sets the value of the serialNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSerialNumber(String value) {
        this.serialNumber = value;
    }

    /**
     * Gets the value of the pserverId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPserverId() {
        return pserverId;
    }

    /**
     * Sets the value of the pserverId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPserverId(String value) {
        this.pserverId = value;
    }

    /**
     * Gets the value of the internetTopology property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInternetTopology() {
        return internetTopology;
    }

    /**
     * Sets the value of the internetTopology property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInternetTopology(String value) {
        this.internetTopology = value;
    }

    /**
     * Gets the value of the inMaint property.
     * 
     */
    public boolean isInMaint() {
        return inMaint;
    }

    /**
     * Sets the value of the inMaint property.
     * 
     */
    public void setInMaint(boolean value) {
        this.inMaint = value;
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
     * Gets the value of the pserverName2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPserverName2() {
        return pserverName2;
    }

    /**
     * Sets the value of the pserverName2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPserverName2(String value) {
        this.pserverName2 = value;
    }

    /**
     * Gets the value of the purpose property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPurpose() {
        return purpose;
    }

    /**
     * Sets the value of the purpose property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPurpose(String value) {
        this.purpose = value;
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
     * Gets the value of the pInterfaces property.
     * 
     * @return
     *     possible object is
     *     {@link PInterfaces }
     *     
     */
    public PInterfaces getPInterfaces() {
        return pInterfaces;
    }

    /**
     * Sets the value of the pInterfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link PInterfaces }
     *     
     */
    public void setPInterfaces(PInterfaces value) {
        this.pInterfaces = value;
    }

    /**
     * Gets the value of the lagInterfaces property.
     * 
     * @return
     *     possible object is
     *     {@link LagInterfaces }
     *     
     */
    public LagInterfaces getLagInterfaces() {
        return lagInterfaces;
    }

    /**
     * Sets the value of the lagInterfaces property.
     * 
     * @param value
     *     allowed object is
     *     {@link LagInterfaces }
     *     
     */
    public void setLagInterfaces(LagInterfaces value) {
        this.lagInterfaces = value;
    }

}
