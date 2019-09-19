//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.03.20 at 12:53:35 PM MDT 
//


package gov.nwcg.isuite.xml.financialexport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for AcctCodeSummary286Type complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AcctCodeSummary286Type">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccountingCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AccountingCodeTotals" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="PostingSummary286" type="{http://isuite.nwcg.gov/FinancialExport}PostingSummary286Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AcctCodeSummary286Type", propOrder = {
    "accountingCode",
    "accountingCodeTotals",
    "postingSummary286"
})
public class AcctCodeSummary286Type {

    @XmlElement(name = "AccountingCode", required = true)
    protected String accountingCode;
    @XmlElement(name = "AccountingCodeTotals", required = true)
    protected BigDecimal accountingCodeTotals;
    @XmlElement(name = "PostingSummary286", required = true)
    protected List<PostingSummary286Type> postingSummary286;

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
     * Gets the value of the accountingCodeTotals property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getAccountingCodeTotals() {
        return accountingCodeTotals;
    }

    /**
     * Sets the value of the accountingCodeTotals property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setAccountingCodeTotals(BigDecimal value) {
        this.accountingCodeTotals = value;
    }

    /**
     * Gets the value of the postingSummary286 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the postingSummary286 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPostingSummary286().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link PostingSummary286Type }
     * 
     * 
     */
    public List<PostingSummary286Type> getPostingSummary286() {
        if (postingSummary286 == null) {
            postingSummary286 = new ArrayList<PostingSummary286Type>();
        }
        return this.postingSummary286;
    }

}
