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
 *         &lt;element name="vnfc-name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vnfc-function-code" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vnfc-type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="prov-status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orchestration-status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="ipaddress-v4-oam-vip" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="in-maint" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="is-closed-loop-disabled" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="group-notation" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "vnfcName",
    "vnfcFunctionCode",
    "vnfcType",
    "provStatus",
    "orchestrationStatus",
    "ipaddressV4OamVip",
    "inMaint",
    "isClosedLoopDisabled",
    "groupNotation",
    "resourceVersion",
    "relationshipList"
})
@XmlRootElement(name = "vnfc")
public class Vnfc
    implements AAIDatum
{

    @XmlElement(name = "vnfc-name", required = true)
    protected String vnfcName;
    @XmlElement(name = "vnfc-function-code", required = true)
    protected String vnfcFunctionCode;
    @XmlElement(name = "vnfc-type", required = true)
    protected String vnfcType;
    @XmlElement(name = "prov-status")
    protected String provStatus;
    @XmlElement(name = "orchestration-status")
    protected String orchestrationStatus;
    @XmlElement(name = "ipaddress-v4-oam-vip")
    protected String ipaddressV4OamVip;
    @XmlElement(name = "in-maint")
    protected boolean inMaint;
    @XmlElement(name = "is-closed-loop-disabled")
    protected boolean isClosedLoopDisabled;
    @XmlElement(name = "group-notation")
    protected String groupNotation;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;

    /**
     * Gets the value of the vnfcName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnfcName() {
        return vnfcName;
    }

    /**
     * Sets the value of the vnfcName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnfcName(String value) {
        this.vnfcName = value;
    }

    /**
     * Gets the value of the vnfcFunctionCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnfcFunctionCode() {
        return vnfcFunctionCode;
    }

    /**
     * Sets the value of the vnfcFunctionCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnfcFunctionCode(String value) {
        this.vnfcFunctionCode = value;
    }

    /**
     * Gets the value of the vnfcType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVnfcType() {
        return vnfcType;
    }

    /**
     * Sets the value of the vnfcType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVnfcType(String value) {
        this.vnfcType = value;
    }

    /**
     * Gets the value of the provStatus property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvStatus() {
        return provStatus;
    }

    /**
     * Sets the value of the provStatus property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvStatus(String value) {
        this.provStatus = value;
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
     * Gets the value of the ipaddressV4OamVip property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIpaddressV4OamVip() {
        return ipaddressV4OamVip;
    }

    /**
     * Sets the value of the ipaddressV4OamVip property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIpaddressV4OamVip(String value) {
        this.ipaddressV4OamVip = value;
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
     * Gets the value of the isClosedLoopDisabled property.
     * 
     */
    public boolean isIsClosedLoopDisabled() {
        return isClosedLoopDisabled;
    }

    /**
     * Sets the value of the isClosedLoopDisabled property.
     * 
     */
    public void setIsClosedLoopDisabled(boolean value) {
        this.isClosedLoopDisabled = value;
    }

    /**
     * Gets the value of the groupNotation property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupNotation() {
        return groupNotation;
    }

    /**
     * Sets the value of the groupNotation property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupNotation(String value) {
        this.groupNotation = value;
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
