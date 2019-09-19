/**
 * AuthenticationResponse.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.nwcg.www.webservices.security.authentication;

public class AuthenticationResponse  implements java.io.Serializable {
    private java.lang.String firstName;

    private java.lang.String middleName;

    private java.lang.String lastName;

    private java.lang.String LDAPIdentifier;

    private java.lang.String status;

    private boolean ROBAccepted;

    private java.lang.String ROBExpirationDays;

    private boolean isPrivileged;

    public AuthenticationResponse() {
    }

    public AuthenticationResponse(
           java.lang.String firstName,
           java.lang.String middleName,
           java.lang.String lastName,
           java.lang.String LDAPIdentifier,
           java.lang.String status,
           boolean ROBAccepted,
           java.lang.String ROBExpirationDays,
           boolean isPrivileged) {
           this.firstName = firstName;
           this.middleName = middleName;
           this.lastName = lastName;
           this.LDAPIdentifier = LDAPIdentifier;
           this.status = status;
           this.ROBAccepted = ROBAccepted;
           this.ROBExpirationDays = ROBExpirationDays;
           this.isPrivileged = isPrivileged;
    }


    /**
     * Gets the firstName value for this AuthenticationResponse.
     * 
     * @return firstName
     */
    public java.lang.String getFirstName() {
        return firstName;
    }


    /**
     * Sets the firstName value for this AuthenticationResponse.
     * 
     * @param firstName
     */
    public void setFirstName(java.lang.String firstName) {
        this.firstName = firstName;
    }


    /**
     * Gets the middleName value for this AuthenticationResponse.
     * 
     * @return middleName
     */
    public java.lang.String getMiddleName() {
        return middleName;
    }


    /**
     * Sets the middleName value for this AuthenticationResponse.
     * 
     * @param middleName
     */
    public void setMiddleName(java.lang.String middleName) {
        this.middleName = middleName;
    }


    /**
     * Gets the lastName value for this AuthenticationResponse.
     * 
     * @return lastName
     */
    public java.lang.String getLastName() {
        return lastName;
    }


    /**
     * Sets the lastName value for this AuthenticationResponse.
     * 
     * @param lastName
     */
    public void setLastName(java.lang.String lastName) {
        this.lastName = lastName;
    }


    /**
     * Gets the LDAPIdentifier value for this AuthenticationResponse.
     * 
     * @return LDAPIdentifier
     */
    public java.lang.String getLDAPIdentifier() {
        return LDAPIdentifier;
    }


    /**
     * Sets the LDAPIdentifier value for this AuthenticationResponse.
     * 
     * @param LDAPIdentifier
     */
    public void setLDAPIdentifier(java.lang.String LDAPIdentifier) {
        this.LDAPIdentifier = LDAPIdentifier;
    }


    /**
     * Gets the status value for this AuthenticationResponse.
     * 
     * @return status
     */
    public java.lang.String getStatus() {
        return status;
    }


    /**
     * Sets the status value for this AuthenticationResponse.
     * 
     * @param status
     */
    public void setStatus(java.lang.String status) {
        this.status = status;
    }


    /**
     * Gets the ROBAccepted value for this AuthenticationResponse.
     * 
     * @return ROBAccepted
     */
    public boolean isROBAccepted() {
        return ROBAccepted;
    }


    /**
     * Sets the ROBAccepted value for this AuthenticationResponse.
     * 
     * @param ROBAccepted
     */
    public void setROBAccepted(boolean ROBAccepted) {
        this.ROBAccepted = ROBAccepted;
    }


    /**
     * Gets the ROBExpirationDays value for this AuthenticationResponse.
     * 
     * @return ROBExpirationDays
     */
    public java.lang.String getROBExpirationDays() {
        return ROBExpirationDays;
    }


    /**
     * Sets the ROBExpirationDays value for this AuthenticationResponse.
     * 
     * @param ROBExpirationDays
     */
    public void setROBExpirationDays(java.lang.String ROBExpirationDays) {
        this.ROBExpirationDays = ROBExpirationDays;
    }


    /**
     * Gets the isPrivileged value for this AuthenticationResponse.
     * 
     * @return isPrivileged
     */
    public boolean isIsPrivileged() {
        return isPrivileged;
    }


    /**
     * Sets the isPrivileged value for this AuthenticationResponse.
     * 
     * @param isPrivileged
     */
    public void setIsPrivileged(boolean isPrivileged) {
        this.isPrivileged = isPrivileged;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof AuthenticationResponse)) return false;
        AuthenticationResponse other = (AuthenticationResponse) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.firstName==null && other.getFirstName()==null) || 
             (this.firstName!=null &&
              this.firstName.equals(other.getFirstName()))) &&
            ((this.middleName==null && other.getMiddleName()==null) || 
             (this.middleName!=null &&
              this.middleName.equals(other.getMiddleName()))) &&
            ((this.lastName==null && other.getLastName()==null) || 
             (this.lastName!=null &&
              this.lastName.equals(other.getLastName()))) &&
            ((this.LDAPIdentifier==null && other.getLDAPIdentifier()==null) || 
             (this.LDAPIdentifier!=null &&
              this.LDAPIdentifier.equals(other.getLDAPIdentifier()))) &&
            ((this.status==null && other.getStatus()==null) || 
             (this.status!=null &&
              this.status.equals(other.getStatus()))) &&
            this.ROBAccepted == other.isROBAccepted() &&
            ((this.ROBExpirationDays==null && other.getROBExpirationDays()==null) || 
             (this.ROBExpirationDays!=null &&
              this.ROBExpirationDays.equals(other.getROBExpirationDays()))) &&
            this.isPrivileged == other.isIsPrivileged();
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getFirstName() != null) {
            _hashCode += getFirstName().hashCode();
        }
        if (getMiddleName() != null) {
            _hashCode += getMiddleName().hashCode();
        }
        if (getLastName() != null) {
            _hashCode += getLastName().hashCode();
        }
        if (getLDAPIdentifier() != null) {
            _hashCode += getLDAPIdentifier().hashCode();
        }
        if (getStatus() != null) {
            _hashCode += getStatus().hashCode();
        }
        _hashCode += (isROBAccepted() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        if (getROBExpirationDays() != null) {
            _hashCode += getROBExpirationDays().hashCode();
        }
        _hashCode += (isIsPrivileged() ? Boolean.TRUE : Boolean.FALSE).hashCode();
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(AuthenticationResponse.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", ">authenticationResponse"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("firstName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "FirstName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("middleName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "MiddleName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("lastName");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "LastName"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("LDAPIdentifier");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "LDAPIdentifier"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("status");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "Status"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ROBAccepted");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "ROBAccepted"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("ROBExpirationDays");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "ROBExpirationDays"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("isPrivileged");
        elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/authentication", "IsPrivileged"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
        elemField.setNillable(false);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
