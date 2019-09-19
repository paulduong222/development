//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.3-hudson-jaxb-ri-2.2.3-3- 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2014.02.13 at 11:25:25 AM EST 
//


package gov.nwcg.isuite.xml.masterfrequency;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Used to express a master frequency record.
 * 
 * <p>Java class for MasterFrequencyType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="MasterFrequencyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Show" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ZoneGroup" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Channel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Function" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Assignment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Remarks" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ChannelNameRadioTalkgroup" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RXFreq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RXTone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TXFreq" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TXTone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Mode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "MasterFrequencyType", propOrder = {
    "show",
    "zoneGroup",
    "channel",
    "function",
    "assignment",
    "remarks",
    "channelNameRadioTalkgroup",
    "rxFreq",
    "rxTone",
    "txFreq",
    "txTone",
    "mode"
})
public class MasterFrequencyType {

    @XmlElement(name = "Show", required = true)
    protected String show;
    @XmlElement(name = "ZoneGroup", required = true)
    protected String zoneGroup;
    @XmlElement(name = "Channel", required = true)
    protected String channel;
    @XmlElement(name = "Function", required = true)
    protected String function;
    @XmlElement(name = "Assignment", required = true)
    protected String assignment;
    @XmlElement(name = "Remarks", required = true)
    protected String remarks;
    @XmlElement(name = "ChannelNameRadioTalkgroup", required = true)
    protected String channelNameRadioTalkgroup;
    @XmlElement(name = "RXFreq", required = true)
    protected String rxFreq;
    @XmlElement(name = "RXTone", required = true)
    protected String rxTone;
    @XmlElement(name = "TXFreq", required = true)
    protected String txFreq;
    @XmlElement(name = "TXTone", required = true)
    protected String txTone;
    @XmlElement(name = "Mode", required = true)
    protected String mode;

    /**
     * Gets the value of the show property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShow() {
        return show;
    }

    /**
     * Sets the value of the show property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShow(String value) {
        this.show = value;
    }

    /**
     * Gets the value of the zoneGroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZoneGroup() {
        return zoneGroup;
    }

    /**
     * Sets the value of the zoneGroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZoneGroup(String value) {
        this.zoneGroup = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the function property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFunction() {
        return function;
    }

    /**
     * Sets the value of the function property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFunction(String value) {
        this.function = value;
    }

    /**
     * Gets the value of the assignment property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAssignment() {
        return assignment;
    }

    /**
     * Sets the value of the assignment property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAssignment(String value) {
        this.assignment = value;
    }

    /**
     * Gets the value of the remarks property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * Sets the value of the remarks property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemarks(String value) {
        this.remarks = value;
    }

    /**
     * Gets the value of the channelNameRadioTalkgroup property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannelNameRadioTalkgroup() {
        return channelNameRadioTalkgroup;
    }

    /**
     * Sets the value of the channelNameRadioTalkgroup property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannelNameRadioTalkgroup(String value) {
        this.channelNameRadioTalkgroup = value;
    }

    /**
     * Gets the value of the rxFreq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRXFreq() {
        return rxFreq;
    }

    /**
     * Sets the value of the rxFreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRXFreq(String value) {
        this.rxFreq = value;
    }

    /**
     * Gets the value of the rxTone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRXTone() {
        return rxTone;
    }

    /**
     * Sets the value of the rxTone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRXTone(String value) {
        this.rxTone = value;
    }

    /**
     * Gets the value of the txFreq property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTXFreq() {
        return txFreq;
    }

    /**
     * Sets the value of the txFreq property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTXFreq(String value) {
        this.txFreq = value;
    }

    /**
     * Gets the value of the txTone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTXTone() {
        return txTone;
    }

    /**
     * Sets the value of the txTone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTXTone(String value) {
        this.txTone = value;
    }

    /**
     * Gets the value of the mode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMode() {
        return mode;
    }

    /**
     * Sets the value of the mode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMode(String value) {
        this.mode = value;
    }

}
