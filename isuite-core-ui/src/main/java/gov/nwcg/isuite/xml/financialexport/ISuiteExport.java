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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IncidentNumber" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IncidentStartDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExportDateTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DBIncidents" type="{http://isuite.nwcg.gov/FinancialExport}DBIncidentsType" maxOccurs="unbounded"/>
 *         &lt;element name="Accruals" type="{http://isuite.nwcg.gov/FinancialExport}AccrualsType" maxOccurs="unbounded"/>
 *         &lt;element name="ISuiteInvoice286" type="{http://isuite.nwcg.gov/FinancialExport}ISuiteInvoice286Type" maxOccurs="unbounded"/>
 *         &lt;element name="Invoice288" type="{http://isuite.nwcg.gov/FinancialExport}Invoice288Type" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "incidentNumber",
    "incidentStartDate",
    "exportDateTime",
    "dbIncidents",
    "accruals",
    "iSuiteInvoice286",
    "invoice288"
})
@XmlRootElement(name = "ISuiteExport")
public class ISuiteExport {

    @XmlElement(name = "IncidentNumber", required = true)
    protected String incidentNumber;
    @XmlElement(name = "IncidentStartDate", required = true)
    protected String incidentStartDate;
    @XmlElement(name = "ExportDateTime", required = true)
    protected String exportDateTime;
    @XmlElement(name = "DBIncidents", required = true)
    protected List<DBIncidentsType> dbIncidents;
    @XmlElement(name = "Accruals", required = true)
    protected List<AccrualsType> accruals;
    @XmlElement(name = "ISuiteInvoice286", required = true)
    protected List<ISuiteInvoice286Type> iSuiteInvoice286;
    @XmlElement(name = "Invoice288", required = true)
    protected List<Invoice288Type> invoice288;

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
     * Gets the value of the incidentStartDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIncidentStartDate() {
        return incidentStartDate;
    }

    /**
     * Sets the value of the incidentStartDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIncidentStartDate(String value) {
        this.incidentStartDate = value;
    }

    /**
     * Gets the value of the exportDateTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getExportDateTime() {
        return exportDateTime;
    }

    /**
     * Sets the value of the exportDateTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setExportDateTime(String value) {
        this.exportDateTime = value;
    }

    /**
     * Gets the value of the dbIncidents property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the dbIncidents property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDBIncidents().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DBIncidentsType }
     * 
     * 
     */
    public List<DBIncidentsType> getDBIncidents() {
        if (dbIncidents == null) {
            dbIncidents = new ArrayList<DBIncidentsType>();
        }
        return this.dbIncidents;
    }

    /**
     * Gets the value of the accruals property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the accruals property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAccruals().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AccrualsType }
     * 
     * 
     */
    public List<AccrualsType> getAccruals() {
        if (accruals == null) {
            accruals = new ArrayList<AccrualsType>();
        }
        return this.accruals;
    }

    /**
     * Gets the value of the iSuiteInvoice286 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the iSuiteInvoice286 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getISuiteInvoice286().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ISuiteInvoice286Type }
     * 
     * 
     */
    public List<ISuiteInvoice286Type> getISuiteInvoice286() {
        if (iSuiteInvoice286 == null) {
            iSuiteInvoice286 = new ArrayList<ISuiteInvoice286Type>();
        }
        return this.iSuiteInvoice286;
    }

    /**
     * Gets the value of the invoice288 property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the invoice288 property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInvoice288().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Invoice288Type }
     * 
     * 
     */
    public List<Invoice288Type> getInvoice288() {
        if (invoice288 == null) {
            invoice288 = new ArrayList<Invoice288Type>();
        }
        return this.invoice288;
    }

}
