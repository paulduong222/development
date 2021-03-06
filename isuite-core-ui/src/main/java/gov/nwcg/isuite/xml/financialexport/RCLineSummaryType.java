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
 * <p>Java class for RCLineSummaryType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RCLineSummaryType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RCLineNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RCLineDescription" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RCLineTotalAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RCLinePrevAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="RCLineChangeAmount" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="AccrualCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AccrualDetail" type="{http://isuite.nwcg.gov/FinancialExport}AccrualDetailType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RCLineSummaryType", propOrder = {
    "rcLineNumber",
    "rcLineDescription",
    "rcLineTotalAmount",
    "rcLinePrevAmount",
    "rcLineChangeAmount",
    "accrualCode",
    "accrualDetail"
})
public class RCLineSummaryType {

    @XmlElement(name = "RCLineNumber", required = true)
    protected String rcLineNumber;
    @XmlElement(name = "RCLineDescription", required = true)
    protected String rcLineDescription;
    @XmlElement(name = "RCLineTotalAmount", required = true)
    protected BigDecimal rcLineTotalAmount;
    @XmlElement(name = "RCLinePrevAmount", required = true)
    protected BigDecimal rcLinePrevAmount;
    @XmlElement(name = "RCLineChangeAmount", required = true)
    protected BigDecimal rcLineChangeAmount;
    @XmlElement(name = "AccrualCode", required = true)
    protected String accrualCode;
    @XmlElement(name = "AccrualDetail", required = true)
    protected List<AccrualDetailType> accrualDetail;

    /**
     * Gets the value of the rcLineNumber property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRCLineNumber() {
        return rcLineNumber;
    }

    /**
     * Sets the value of the rcLineNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRCLineNumber(String value) {
        this.rcLineNumber = value;
    }

    /**
     * Gets the value of the rcLineDescription property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRCLineDescription() {
        return rcLineDescription;
    }

    /**
     * Sets the value of the rcLineDescription property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRCLineDescription(String value) {
        this.rcLineDescription = value;
    }

    /**
     * Gets the value of the rcLineTotalAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRCLineTotalAmount() {
        return rcLineTotalAmount;
    }

    /**
     * Sets the value of the rcLineTotalAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRCLineTotalAmount(BigDecimal value) {
        this.rcLineTotalAmount = value;
    }

    /**
     * Gets the value of the rcLinePrevAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRCLinePrevAmount() {
        return rcLinePrevAmount;
    }

    /**
     * Sets the value of the rcLinePrevAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRCLinePrevAmount(BigDecimal value) {
        this.rcLinePrevAmount = value;
    }

    /**
     * Gets the value of the rcLineChangeAmount property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getRCLineChangeAmount() {
        return rcLineChangeAmount;
    }

    /**
     * Sets the value of the rcLineChangeAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setRCLineChangeAmount(BigDecimal value) {
        this.rcLineChangeAmount = value;
    }

    /**
     * Gets the value of the accrualCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccrualCode() {
        return accrualCode;
    }

    /**
     * Sets the value of the accrualCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccrualCode(String value) {
        this.accrualCode = value;
    }

    /**
     * Gets the value of the accrualDetail property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accrualDetail property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccrualDetail().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccrualDetailType }
     * 
     * 
     */
    public List<AccrualDetailType> getAccrualDetail() {
        if (accrualDetail == null) {
            accrualDetail = new ArrayList<AccrualDetailType>();
        }
        return this.accrualDetail;
    }

}
