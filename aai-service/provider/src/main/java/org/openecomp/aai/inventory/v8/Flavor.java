/*-
 * ============LICENSE_START=======================================================
 * openECOMP : SDN-C
 * ================================================================================
 * Copyright (C) 2017 AT&T Intellectual Property. All rights
 * 						reserved.
 * ================================================================================
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ============LICENSE_END=========================================================
 */

//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.03.29 at 11:26:46 AM GMT+00:00 
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
 *         &lt;element name="flavor-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="flavor-name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="flavor-vcpus" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="flavor-ram" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="flavor-disk" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="flavor-ephemeral" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/&gt;
 *         &lt;element name="flavor-swap" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="flavor-is-public" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
 *         &lt;element name="flavor-selflink" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="flavor-disabled" type="{http://www.w3.org/2001/XMLSchema}boolean" minOccurs="0"/&gt;
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
    "flavorId",
    "flavorName",
    "flavorVcpus",
    "flavorRam",
    "flavorDisk",
    "flavorEphemeral",
    "flavorSwap",
    "flavorIsPublic",
    "flavorSelflink",
    "flavorDisabled",
    "resourceVersion",
    "relationshipList"
})
@XmlRootElement(name = "flavor")
public class Flavor
    implements AAIDatum
{

    @XmlElement(name = "flavor-id", required = true)
    protected String flavorId;
    @XmlElement(name = "flavor-name", required = true)
    protected String flavorName;
    @XmlElement(name = "flavor-vcpus")
    protected Integer flavorVcpus;
    @XmlElement(name = "flavor-ram")
    protected Integer flavorRam;
    @XmlElement(name = "flavor-disk")
    protected Integer flavorDisk;
    @XmlElement(name = "flavor-ephemeral")
    protected Integer flavorEphemeral;
    @XmlElement(name = "flavor-swap")
    protected String flavorSwap;
    @XmlElement(name = "flavor-is-public")
    protected Boolean flavorIsPublic;
    @XmlElement(name = "flavor-selflink", required = true)
    protected String flavorSelflink;
    @XmlElement(name = "flavor-disabled")
    protected Boolean flavorDisabled;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;

    /**
     * Gets the value of the flavorId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavorId() {
        return flavorId;
    }

    /**
     * Sets the value of the flavorId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavorId(String value) {
        this.flavorId = value;
    }

    /**
     * Gets the value of the flavorName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavorName() {
        return flavorName;
    }

    /**
     * Sets the value of the flavorName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavorName(String value) {
        this.flavorName = value;
    }

    /**
     * Gets the value of the flavorVcpus property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFlavorVcpus() {
        return flavorVcpus;
    }

    /**
     * Sets the value of the flavorVcpus property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFlavorVcpus(Integer value) {
        this.flavorVcpus = value;
    }

    /**
     * Gets the value of the flavorRam property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFlavorRam() {
        return flavorRam;
    }

    /**
     * Sets the value of the flavorRam property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFlavorRam(Integer value) {
        this.flavorRam = value;
    }

    /**
     * Gets the value of the flavorDisk property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFlavorDisk() {
        return flavorDisk;
    }

    /**
     * Sets the value of the flavorDisk property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFlavorDisk(Integer value) {
        this.flavorDisk = value;
    }

    /**
     * Gets the value of the flavorEphemeral property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getFlavorEphemeral() {
        return flavorEphemeral;
    }

    /**
     * Sets the value of the flavorEphemeral property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setFlavorEphemeral(Integer value) {
        this.flavorEphemeral = value;
    }

    /**
     * Gets the value of the flavorSwap property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavorSwap() {
        return flavorSwap;
    }

    /**
     * Sets the value of the flavorSwap property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavorSwap(String value) {
        this.flavorSwap = value;
    }

    /**
     * Gets the value of the flavorIsPublic property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlavorIsPublic() {
        return flavorIsPublic;
    }

    /**
     * Sets the value of the flavorIsPublic property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlavorIsPublic(Boolean value) {
        this.flavorIsPublic = value;
    }

    /**
     * Gets the value of the flavorSelflink property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlavorSelflink() {
        return flavorSelflink;
    }

    /**
     * Sets the value of the flavorSelflink property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlavorSelflink(String value) {
        this.flavorSelflink = value;
    }

    /**
     * Gets the value of the flavorDisabled property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isFlavorDisabled() {
        return flavorDisabled;
    }

    /**
     * Sets the value of the flavorDisabled property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setFlavorDisabled(Boolean value) {
        this.flavorDisabled = value;
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
