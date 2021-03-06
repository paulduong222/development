//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.1-b02-fcs 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2010.07.29 at 03:00:43 PM MDT 
//


package gov.nwcg.isuite.xml;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Used to express a Work Period.
 * 
 * <p>Java class for WorkPeriodType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="WorkPeriodType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CheckInDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="FirstWorkDate" type="{http://www.w3.org/2001/XMLSchema}date"/>
 *         &lt;element name="Assignment" type="{http://isuite.nwcg.gov/lib}AssignmentType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "WorkPeriodType", propOrder = {
    "checkInDate",
    "firstWorkDate",
    "assignment"
})
public class WorkPeriodType {

    @XmlElement(name = "CheckInDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar checkInDate;
    @XmlElement(name = "FirstWorkDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar firstWorkDate;
    @XmlElement(name = "Assignment", required = true)
    protected List<AssignmentType> assignment;

    /**
     * Gets the value of the checkInDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCheckInDate() {
        return checkInDate;
    }

    /**
     * Sets the value of the checkInDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCheckInDate(XMLGregorianCalendar value) {
        this.checkInDate = value;
    }

    /**
     * Gets the value of the firstWorkDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFirstWorkDate() {
        return firstWorkDate;
    }

    /**
     * Sets the value of the firstWorkDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFirstWorkDate(XMLGregorianCalendar value) {
        this.firstWorkDate = value;
    }

    /**
     * Gets the value of the assignment property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the assignment property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getAssignment().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link AssignmentType }
     * 
     * 
     */
    public List<AssignmentType> getAssignment() {
        if (assignment == null) {
            assignment = new ArrayList<AssignmentType>();
        }
        return this.assignment;
    }

}
