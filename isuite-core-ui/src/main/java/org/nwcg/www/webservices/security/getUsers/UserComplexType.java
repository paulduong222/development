/**
 * UserComplexType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.nwcg.www.webservices.security.getUsers;

public class UserComplexType  implements java.io.Serializable {
	private java.lang.String userName;

	private java.lang.String firstName;

	private java.lang.String lastName;

	public UserComplexType() {
	}

	public UserComplexType(
			java.lang.String userName,
			java.lang.String firstName,
			java.lang.String lastName) {
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	/**
	 * Gets the userName value for this UserComplexType.
	 * 
	 * @return userName
	 */
	public java.lang.String getUserName() {
		return userName;
	}


	/**
	 * Sets the userName value for this UserComplexType.
	 * 
	 * @param userName
	 */
	public void setUserName(java.lang.String userName) {
		this.userName = userName;
	}


	/**
	 * Gets the firstName value for this UserComplexType.
	 * 
	 * @return firstName
	 */
	public java.lang.String getFirstName() {
		return firstName;
	}


	/**
	 * Sets the firstName value for this UserComplexType.
	 * 
	 * @param firstName
	 */
	public void setFirstName(java.lang.String firstName) {
		this.firstName = firstName;
	}


	/**
	 * Gets the lastName value for this UserComplexType.
	 * 
	 * @return lastName
	 */
	public java.lang.String getLastName() {
		return lastName;
	}


	/**
	 * Sets the lastName value for this UserComplexType.
	 * 
	 * @param lastName
	 */
	public void setLastName(java.lang.String lastName) {
		this.lastName = lastName;
	}

	private java.lang.Object __equalsCalc = null;
	public synchronized boolean equals(java.lang.Object obj) {
		if (!(obj instanceof UserComplexType)) return false;
		UserComplexType other = (UserComplexType) obj;
		if (obj == null) return false;
		if (this == obj) return true;
		if (__equalsCalc != null) {
			return (__equalsCalc == obj);
		}
		__equalsCalc = obj;
		boolean _equals;
		_equals = true && 
		((this.userName==null && other.getUserName()==null) || 
				(this.userName!=null &&
						this.userName.equals(other.getUserName()))) &&
						((this.firstName==null && other.getFirstName()==null) || 
								(this.firstName!=null &&
										this.firstName.equals(other.getFirstName()))) &&
										((this.lastName==null && other.getLastName()==null) || 
												(this.lastName!=null &&
														this.lastName.equals(other.getLastName())));
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
		if (getUserName() != null) {
			_hashCode += getUserName().hashCode();
		}
		if (getFirstName() != null) {
			_hashCode += getFirstName().hashCode();
		}
		if (getLastName() != null) {
			_hashCode += getLastName().hashCode();
		}
		__hashCodeCalc = false;
		return _hashCode;
	}

	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
		new org.apache.axis.description.TypeDesc(UserComplexType.class, true);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "UserComplexType"));
		org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("userName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "UserName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("firstName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "FirstName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		elemField.setNillable(false);
		typeDesc.addFieldDesc(elemField);
		elemField = new org.apache.axis.description.ElementDesc();
		elemField.setFieldName("lastName");
		elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "LastName"));
		elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
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
