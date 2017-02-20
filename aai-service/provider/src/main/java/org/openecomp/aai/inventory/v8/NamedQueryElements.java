//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2017.02.20 at 02:31:54 PM EST 
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
 *         &lt;element ref="{http://org.openecomp.aai.inventory/v8}named-query-element" maxOccurs="unbounded" minOccurs="0"/&gt;
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
    "namedQueryElement"
})
@XmlRootElement(name = "named-query-elements")
public class NamedQueryElements
    implements AAIDatum
{

    @XmlElement(name = "named-query-element")
    protected List<NamedQueryElement> namedQueryElement;

    /**
     * Gets the value of the namedQueryElement property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the namedQueryElement property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNamedQueryElement().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link NamedQueryElement }
     * 
     * 
     */
    public List<NamedQueryElement> getNamedQueryElement() {
        if (namedQueryElement == null) {
            namedQueryElement = new ArrayList<NamedQueryElement>();
        }
        return this.namedQueryElement;
    }

}
