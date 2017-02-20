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
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}services" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}models" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}named-queries" minOccurs="0"/&gt;
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
    "services",
    "models",
    "namedQueries"
})
@XmlRootElement(name = "service-design-and-creation")
public class ServiceDesignAndCreation
    implements AAIDatum
{

    protected Services services;
    protected Models models;
    @XmlElement(name = "named-queries")
    protected NamedQueries namedQueries;

    /**
     * Gets the value of the services property.
     * 
     * @return
     *     possible object is
     *     {@link Services }
     *     
     */
    public Services getServices() {
        return services;
    }

    /**
     * Sets the value of the services property.
     * 
     * @param value
     *     allowed object is
     *     {@link Services }
     *     
     */
    public void setServices(Services value) {
        this.services = value;
    }

    /**
     * Gets the value of the models property.
     * 
     * @return
     *     possible object is
     *     {@link Models }
     *     
     */
    public Models getModels() {
        return models;
    }

    /**
     * Sets the value of the models property.
     * 
     * @param value
     *     allowed object is
     *     {@link Models }
     *     
     */
    public void setModels(Models value) {
        this.models = value;
    }

    /**
     * Gets the value of the namedQueries property.
     * 
     * @return
     *     possible object is
     *     {@link NamedQueries }
     *     
     */
    public NamedQueries getNamedQueries() {
        return namedQueries;
    }

    /**
     * Sets the value of the namedQueries property.
     * 
     * @param value
     *     allowed object is
     *     {@link NamedQueries }
     *     
     */
    public void setNamedQueries(NamedQueries value) {
        this.namedQueries = value;
    }

}
