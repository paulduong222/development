//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2013.07.25 at 04:52:06 PM EDT 
//


package gov.nwcg.isuite.xml.customreport;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for CRFilterType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CRFilterType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Connective" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CriteriaType" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="SourceFieldName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SourceEvaluatorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SourceValue1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SourceValue2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="SourceValue3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Operand" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TargetType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TargetEvaluatorType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TargetValue1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TargetValue2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TargetValue3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ChildFilter" type="{http://isuite.nwcg.gov/CustomReportExport}CRFilterType" maxOccurs="20"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CRFilterType", propOrder = {
    "connective",
    "criteriaType",
    "sourceFieldName",
    "sourceEvaluatorType",
    "sourceValue1",
    "sourceValue2",
    "sourceValue3",
    "operand",
    "targetType",
    "targetEvaluatorType",
    "targetValue1",
    "targetValue2",
    "targetValue3",
    "childFilter"
})
public class CRFilterType {

    @XmlElement(name = "Connective", required = true)
    protected String connective;
    @XmlElement(name = "CriteriaType")
    protected boolean criteriaType;
    @XmlElement(name = "SourceFieldName", required = true)
    protected String sourceFieldName;
    @XmlElement(name = "SourceEvaluatorType", required = true)
    protected String sourceEvaluatorType;
    @XmlElement(name = "SourceValue1", required = true)
    protected String sourceValue1;
    @XmlElement(name = "SourceValue2", required = true)
    protected String sourceValue2;
    @XmlElement(name = "SourceValue3", required = true)
    protected String sourceValue3;
    @XmlElement(name = "Operand", required = true)
    protected String operand;
    @XmlElement(name = "TargetType", required = true)
    protected String targetType;
    @XmlElement(name = "TargetEvaluatorType", required = true)
    protected String targetEvaluatorType;
    @XmlElement(name = "TargetValue1", required = true)
    protected String targetValue1;
    @XmlElement(name = "TargetValue2", required = true)
    protected String targetValue2;
    @XmlElement(name = "TargetValue3", required = true)
    protected String targetValue3;
    @XmlElement(name = "ChildFilter", required = true)
    protected List<CRFilterType> childFilter;

    /**
     * Gets the value of the connective property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConnective() {
        return connective;
    }

    /**
     * Sets the value of the connective property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConnective(String value) {
        this.connective = value;
    }

    /**
     * Gets the value of the criteriaType property.
     * 
     */
    public boolean isCriteriaType() {
        return criteriaType;
    }

    /**
     * Sets the value of the criteriaType property.
     * 
     */
    public void setCriteriaType(boolean value) {
        this.criteriaType = value;
    }

    /**
     * Gets the value of the sourceFieldName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceFieldName() {
        return sourceFieldName;
    }

    /**
     * Sets the value of the sourceFieldName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceFieldName(String value) {
        this.sourceFieldName = value;
    }

    /**
     * Gets the value of the sourceEvaluatorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceEvaluatorType() {
        return sourceEvaluatorType;
    }

    /**
     * Sets the value of the sourceEvaluatorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceEvaluatorType(String value) {
        this.sourceEvaluatorType = value;
    }

    /**
     * Gets the value of the sourceValue1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceValue1() {
        return sourceValue1;
    }

    /**
     * Sets the value of the sourceValue1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceValue1(String value) {
        this.sourceValue1 = value;
    }

    /**
     * Gets the value of the sourceValue2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceValue2() {
        return sourceValue2;
    }

    /**
     * Sets the value of the sourceValue2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceValue2(String value) {
        this.sourceValue2 = value;
    }

    /**
     * Gets the value of the sourceValue3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSourceValue3() {
        return sourceValue3;
    }

    /**
     * Sets the value of the sourceValue3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSourceValue3(String value) {
        this.sourceValue3 = value;
    }

    /**
     * Gets the value of the operand property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperand() {
        return operand;
    }

    /**
     * Sets the value of the operand property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperand(String value) {
        this.operand = value;
    }

    /**
     * Gets the value of the targetType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetType() {
        return targetType;
    }

    /**
     * Sets the value of the targetType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetType(String value) {
        this.targetType = value;
    }

    /**
     * Gets the value of the targetEvaluatorType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetEvaluatorType() {
        return targetEvaluatorType;
    }

    /**
     * Sets the value of the targetEvaluatorType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetEvaluatorType(String value) {
        this.targetEvaluatorType = value;
    }

    /**
     * Gets the value of the targetValue1 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetValue1() {
        return targetValue1;
    }

    /**
     * Sets the value of the targetValue1 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetValue1(String value) {
        this.targetValue1 = value;
    }

    /**
     * Gets the value of the targetValue2 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetValue2() {
        return targetValue2;
    }

    /**
     * Sets the value of the targetValue2 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetValue2(String value) {
        this.targetValue2 = value;
    }

    /**
     * Gets the value of the targetValue3 property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTargetValue3() {
        return targetValue3;
    }

    /**
     * Sets the value of the targetValue3 property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTargetValue3(String value) {
        this.targetValue3 = value;
    }

    /**
     * Gets the value of the childFilter property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the childFilter property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getChildFilter().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link CRFilterType }
     * 
     * 
     */
    public List<CRFilterType> getChildFilter() {
        if (childFilter == null) {
            childFilter = new ArrayList<CRFilterType>();
        }
        return this.childFilter;
    }

}
