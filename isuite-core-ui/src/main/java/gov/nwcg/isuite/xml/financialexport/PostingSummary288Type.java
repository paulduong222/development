//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.20 at 12:53:35 PM MDT 
//


package gov.nwcg.isuite.xml.financialexport;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for PostingSummary288Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="PostingSummary288Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IncidentNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AccountingCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClassCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ClassRate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="KindCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PostingYear" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PostingDetail288" type="{http://isuite.nwcg.gov/FinancialExport}PostingDetail288Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PostingSummary288Type", propOrder = {
    "incidentNumber",
    "accountingCode",
    "classCode",
    "classRate",
    "kindCode",
    "postingYear",
    "postingDetail288"
})
public class PostingSummary288Type {

    @XmlElement(name = "IncidentNumber", required = true)
    protected String incidentNumber;
    @XmlElement(name = "AccountingCode", required = true)
    protected String accountingCode;
    @XmlElement(name = "ClassCode", required = true)
    protected String classCode;
    @XmlElement(name = "ClassRate", required = true)
    protected String classRate;
    @XmlElement(name = "KindCode", required = true)
    protected String kindCode;
    @XmlElement(name = "PostingYear", required = true)
    protected String postingYear;
    @XmlElement(name = "PostingDetail288", required = true)
    protected List<PostingDetail288Type> postingDetail288;

    /**
     * Gets the value of the incidentNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncidentNumber() {
        return incidentNumber;
    }

    /**
     * Sets the value of the incidentNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncidentNumber(String value) {
        this.incidentNumber = value;
    }

    /**
     * Gets the value of the accountingCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccountingCode() {
        return accountingCode;
    }

    /**
     * Sets the value of the accountingCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccountingCode(String value) {
        this.accountingCode = value;
    }

    /**
     * Gets the value of the classCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassCode() {
        return classCode;
    }

    /**
     * Sets the value of the classCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassCode(String value) {
        this.classCode = value;
    }

    /**
     * Gets the value of the classRate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClassRate() {
        return classRate;
    }

    /**
     * Sets the value of the classRate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClassRate(String value) {
        this.classRate = value;
    }

    /**
     * Gets the value of the kindCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKindCode() {
        return kindCode;
    }

    /**
     * Sets the value of the kindCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKindCode(String value) {
        this.kindCode = value;
    }

    /**
     * Gets the value of the postingYear property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPostingYear() {
        return postingYear;
    }

    /**
     * Sets the value of the postingYear property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPostingYear(String value) {
        this.postingYear = value;
    }

    /**
     * Gets the value of the postingDetail288 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postingDetail288 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostingDetail288().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostingDetail288Type }
     * 
     * 
     */
    public List<PostingDetail288Type> getPostingDetail288() {
        if (postingDetail288 == null) {
            postingDetail288 = new ArrayList<PostingDetail288Type>();
        }
        return this.postingDetail288;
    }

}
