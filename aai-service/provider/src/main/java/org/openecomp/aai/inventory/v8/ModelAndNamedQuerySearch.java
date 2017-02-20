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
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}query-parameters" minOccurs="0"/&gt;
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}instance-filters" minOccurs="0"/&gt;
 *         &lt;element name="top-node-type" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/&gt;
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
    "queryParameters",
    "instanceFilters",
    "topNodeType"
})
@XmlRootElement(name = "model-and-named-query-search")
public class ModelAndNamedQuerySearch
    implements AAIDatum
{

    @XmlElement(name = "query-parameters")
    protected QueryParameters queryParameters;
    @XmlElement(name = "instance-filters")
    protected InstanceFilters instanceFilters;
    @XmlElement(name = "top-node-type")
    protected String topNodeType;

    /**
     * Gets the value of the queryParameters property.
     * 
     * @return
     *     possible object is
     *     {@link QueryParameters }
     *     
     */
    public QueryParameters getQueryParameters() {
        return queryParameters;
    }

    /**
     * Sets the value of the queryParameters property.
     * 
     * @param value
     *     allowed object is
     *     {@link QueryParameters }
     *     
     */
    public void setQueryParameters(QueryParameters value) {
        this.queryParameters = value;
    }

    /**
     * Gets the value of the instanceFilters property.
     * 
     * @return
     *     possible object is
     *     {@link InstanceFilters }
     *     
     */
    public InstanceFilters getInstanceFilters() {
        return instanceFilters;
    }

    /**
     * Sets the value of the instanceFilters property.
     * 
     * @param value
     *     allowed object is
     *     {@link InstanceFilters }
     *     
     */
    public void setInstanceFilters(InstanceFilters value) {
        this.instanceFilters = value;
    }

    /**
     * Gets the value of the topNodeType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTopNodeType() {
        return topNodeType;
    }

    /**
     * Sets the value of the topNodeType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTopNodeType(String value) {
        this.topNodeType = value;
    }

}
