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
 * <p>Java class for AccountingCodeSummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AccountingCodeSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AccountingCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SubTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SubPrevAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="SubChangeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RCLineSummary" type="{http://isuite.nwcg.gov/FinancialExport}RCLineSummaryType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AccountingCodeSummaryType", propOrder = {
    "accountingCode",
    "subTotalAmount",
    "subPrevAmount",
    "subChangeAmount",
    "rcLineSummary"
})
public class AccountingCodeSummaryType {

    @XmlElement(name = "AccountingCode", required = true)
    protected String accountingCode;
    @XmlElement(name = "SubTotalAmount", required = true)
    protected BigDecimal subTotalAmount;
    @XmlElement(name = "SubPrevAmount", required = true)
    protected BigDecimal subPrevAmount;
    @XmlElement(name = "SubChangeAmount", required = true)
    protected BigDecimal subChangeAmount;
    @XmlElement(name = "RCLineSummary", required = true)
    protected List<RCLineSummaryType> rcLineSummary;

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
     * Gets the value of the subTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSubTotalAmount() {
        return subTotalAmount;
    }

    /**
     * Sets the value of the subTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSubTotalAmount(BigDecimal value) {
        this.subTotalAmount = value;
    }

    /**
     * Gets the value of the subPrevAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSubPrevAmount() {
        return subPrevAmount;
    }

    /**
     * Sets the value of the subPrevAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSubPrevAmount(BigDecimal value) {
        this.subPrevAmount = value;
    }

    /**
     * Gets the value of the subChangeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getSubChangeAmount() {
        return subChangeAmount;
    }

    /**
     * Sets the value of the subChangeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setSubChangeAmount(BigDecimal value) {
        this.subChangeAmount = value;
    }

    /**
     * Gets the value of the rcLineSummary property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the rcLineSummary property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getRCLineSummary().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link RCLineSummaryType }
     * 
     * 
     */
    public List<RCLineSummaryType> getRCLineSummary() {
        if (rcLineSummary == null) {
            rcLineSummary = new ArrayList<RCLineSummaryType>();
        }
        return this.rcLineSummary;
    }

}
