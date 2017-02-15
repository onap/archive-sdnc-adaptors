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
 *         &lt;element name="vf-module-id" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="vf-module-name" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="heat-stack-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="orchestration-status" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="is-base-vf-module" type="{http://www.w3.org/2001/XMLSchema}boolean"/&gt;
 *         &lt;element name="resource-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="persona-model-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="persona-model-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="widget-model-id" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="widget-model-version" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
 *         &lt;element name="contrail-service-instance-fqdn" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "vfModuleId",
    "vfModuleName",
    "heatStackId",
    "orchestrationStatus",
    "isBaseVfModule",
    "resourceVersion",
    "personaModelId",
    "personaModelVersion",
    "widgetModelId",
    "widgetModelVersion",
    "contrailServiceInstanceFqdn",
    "relationshipList"
})
@XmlRootElement(name = "vf-module")
public class VfModule
    implements AAIDatum
{

    @XmlElement(name = "vf-module-id", required = true)
    protected String vfModuleId;
    @XmlElement(name = "vf-module-name")
    protected String vfModuleName;
    @XmlElement(name = "heat-stack-id")
    protected String heatStackId;
    @XmlElement(name = "orchestration-status")
    protected String orchestrationStatus;
    @XmlElement(name = "is-base-vf-module")
    protected boolean isBaseVfModule;
    @XmlElement(name = "resource-version")
    protected String resourceVersion;
    @XmlElement(name = "persona-model-id")
    protected String personaModelId;
    @XmlElement(name = "persona-model-version")
    protected String personaModelVersion;
    @XmlElement(name = "widget-model-id")
    protected String widgetModelId;
    @XmlElement(name = "widget-model-version")
    protected String widgetModelVersion;
    @XmlElement(name = "contrail-service-instance-fqdn")
    protected String contrailServiceInstanceFqdn;
    @XmlElement(name = "relationship-list")
    protected RelationshipList relationshipList;

    /**
     * Gets the value of the vfModuleId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVfModuleId() {
        return vfModuleId;
    }

    /**
     * Sets the value of the vfModuleId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVfModuleId(String value) {
        this.vfModuleId = value;
    }

    /**
     * Gets the value of the vfModuleName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getVfModuleName() {
        return vfModuleName;
    }

    /**
     * Sets the value of the vfModuleName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setVfModuleName(String value) {
        this.vfModuleName = value;
    }

    /**
     * Gets the value of the heatStackId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHeatStackId() {
        return heatStackId;
    }

    /**
     * Sets the value of the heatStackId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHeatStackId(String value) {
        this.heatStackId = value;
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
     * Gets the value of the isBaseVfModule property.
     * 
     */
    public boolean isIsBaseVfModule() {
        return isBaseVfModule;
    }

    /**
     * Sets the value of the isBaseVfModule property.
     * 
     */
    public void setIsBaseVfModule(boolean value) {
        this.isBaseVfModule = value;
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
     * Gets the value of the personaModelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonaModelId() {
        return personaModelId;
    }

    /**
     * Sets the value of the personaModelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonaModelId(String value) {
        this.personaModelId = value;
    }

    /**
     * Gets the value of the personaModelVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPersonaModelVersion() {
        return personaModelVersion;
    }

    /**
     * Sets the value of the personaModelVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPersonaModelVersion(String value) {
        this.personaModelVersion = value;
    }

    /**
     * Gets the value of the widgetModelId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidgetModelId() {
        return widgetModelId;
    }

    /**
     * Sets the value of the widgetModelId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidgetModelId(String value) {
        this.widgetModelId = value;
    }

    /**
     * Gets the value of the widgetModelVersion property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWidgetModelVersion() {
        return widgetModelVersion;
    }

    /**
     * Sets the value of the widgetModelVersion property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWidgetModelVersion(String value) {
        this.widgetModelVersion = value;
    }

    /**
     * Gets the value of the contrailServiceInstanceFqdn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getContrailServiceInstanceFqdn() {
        return contrailServiceInstanceFqdn;
    }

    /**
     * Sets the value of the contrailServiceInstanceFqdn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setContrailServiceInstanceFqdn(String value) {
        this.contrailServiceInstanceFqdn = value;
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
