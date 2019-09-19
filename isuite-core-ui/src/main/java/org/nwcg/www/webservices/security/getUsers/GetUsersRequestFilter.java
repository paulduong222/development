/**
 * GetUsersRequestFilter.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.nwcg.www.webservices.security.getUsers;

public class GetUsersRequestFilter implements java.io.Serializable {
	private java.lang.String _value_;
	private static java.util.HashMap _table_ = new java.util.HashMap();

	// Constructor
	protected GetUsersRequestFilter(java.lang.String value) {
		_value_ = value;
		_table_.put(_value_,this);
	}

	public static final java.lang.String _value1 = "contains";
	public static final java.lang.String _value2 = "startswith";
	public static final java.lang.String _value3 = "endswith";
	public static final java.lang.String _value4 = "=";
	public static final java.lang.String _value5 = "!=";
	public static final java.lang.String _value6 = ">";
	public static final java.lang.String _value7 = ">=";
	public static final java.lang.String _value8 = "<";
	public static final java.lang.String _value9 = "<=";
	public static final java.lang.String _value10 = "";
	public static final GetUsersRequestFilter value1 = new GetUsersRequestFilter(_value1);
	public static final GetUsersRequestFilter value2 = new GetUsersRequestFilter(_value2);
	public static final GetUsersRequestFilter value3 = new GetUsersRequestFilter(_value3);
	public static final GetUsersRequestFilter value4 = new GetUsersRequestFilter(_value4);
	public static final GetUsersRequestFilter value5 = new GetUsersRequestFilter(_value5);
	public static final GetUsersRequestFilter value6 = new GetUsersRequestFilter(_value6);
	public static final GetUsersRequestFilter value7 = new GetUsersRequestFilter(_value7);
	public static final GetUsersRequestFilter value8 = new GetUsersRequestFilter(_value8);
	public static final GetUsersRequestFilter value9 = new GetUsersRequestFilter(_value9);
	public static final GetUsersRequestFilter value10 = new GetUsersRequestFilter(_value10);
	public java.lang.String getValue() { return _value_;}
	public static GetUsersRequestFilter fromValue(java.lang.String value)
	throws java.lang.IllegalArgumentException {
		GetUsersRequestFilter enumeration = (GetUsersRequestFilter)
		_table_.get(value);
		if (enumeration==null) throw new java.lang.IllegalArgumentException();
		return enumeration;
	}
	public static GetUsersRequestFilter fromString(java.lang.String value)
	throws java.lang.IllegalArgumentException {
		return fromValue(value);
	}
	public boolean equals(java.lang.Object obj) {return (obj == this);}
	public int hashCode() { return toString().hashCode();}
	public java.lang.String toString() { return _value_;}
	public java.lang.Object readResolve() throws java.io.ObjectStreamException { return fromValue(_value_);}
	public static org.apache.axis.encoding.Serializer getSerializer(
			java.lang.String mechType, 
			java.lang.Class _javaType,  
			javax.xml.namespace.QName _xmlType) {
		return 
		new org.apache.axis.encoding.ser.EnumSerializer(
				_javaType, _xmlType);
	}
	public static org.apache.axis.encoding.Deserializer getDeserializer(
			java.lang.String mechType, 
			java.lang.Class _javaType,  
			javax.xml.namespace.QName _xmlType) {
		return 
		new org.apache.axis.encoding.ser.EnumDeserializer(
				_javaType, _xmlType);
	}
	// Type metadata
	private static org.apache.axis.description.TypeDesc typeDesc =
		new org.apache.axis.description.TypeDesc(GetUsersRequestFilter.class);

	static {
		typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", ">>getUsersRequest>Filter"));
	}
	/**
	 * Return type metadata object
	 */
	public static org.apache.axis.description.TypeDesc getTypeDesc() {
		return typeDesc;
	}

}
