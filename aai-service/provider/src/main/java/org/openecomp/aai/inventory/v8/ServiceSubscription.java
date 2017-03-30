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
 *         &lt;element name="service-type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="temp-ub-sub-account-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}service-instances" minOccurs="0"/&gt;
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
    "serviceType",
    "tempUbSubAccountId",
    "resourceVersion",
    "serviceInstances",
    "relationshipList"
})
@XmlRootElement(name = "service-subscription")
public class ServiceSubscription
    implements AAIDatum
{

    @XmlElement(name = "service-type", required = true)
    protected String serviceType;
    @XmlElement(name = "temp-ub-sub-account-id")
    protected String tempUbSubAccountId;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "service-instances")
    protected ServiceInstances serviceInstances;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;

    /**
     * Gets the value of the serviceType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceType() {
        return serviceType;
    }

    /**
     * Sets the value of the serviceType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceType(String value) {
        this.serviceType = value;
    }

    /**
     * Gets the value of the tempUbSubAccountId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTempUbSubAccountId() {
        return tempUbSubAccountId;
    }

    /**
     * Sets the value of the tempUbSubAccountId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTempUbSubAccountId(String value) {
        this.tempUbSubAccountId = value;
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
     * Gets the value of the serviceInstances property.
     * 
     * @return
     *     possible object is
     *     {@link ServiceInstances }
     *     
     */
    public ServiceInstances getServiceInstances() {
        return serviceInstances;
    }

    /**
     * Sets the value of the serviceInstances property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceInstances }
     *     
     */
    public void setServiceInstances(ServiceInstances value) {
        this.serviceInstances = value;
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
