/**
 * GetUsersRequest.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package org.nwcg.www.webservices.security.getUsers;

public class GetUsersRequest  implements java.io.Serializable {
	private java.lang.String applicationInstance;

	private boolean ascendingOrder;

	private long maxCount;

	private long skipCount;

	private java.lang.String filterValue;

	private org.nwcg.www.webservices.security.getUsers.GetUsersRequestFilter filter;

	public GetUsersRequest() {
	}

	public GetUsersRequest(
			java.lang.String applicationInstance,
			boolean ascendingOrder,
			long maxCount,
			long skipCount,
			java.lang.String filterValue,
			org.nwcg.www.webservices.security.getUsers.GetUsersRequestFilter filter) {
		this.applicationInstance = applicationInstance;
		this.ascendingOrder = ascendingOrder;
		this.maxCount = maxCount;
		this.skipCount = skipCount;
		this.filterValue = filterValue;
		this.filter = filter;
	}


	/**
	 * Gets the applicationInstance value for this GetUsersRequest.
	 * 
	 * @return applicationInstance
	 */
	 public java.lang.String getApplicationInstance() {
		 return applicationInstance;
	 }


	 /**
	  * Sets the applicationInstance value for this GetUsersRequest.
	  * 
	  * @param applicationInstance
	  */
	 public void setApplicationInstance(java.lang.String applicationInstance) {
		 this.applicationInstance = applicationInstance;
	 }


	 /**
	  * Gets the ascendingOrder value for this GetUsersRequest.
	  * 
	  * @return ascendingOrder
	  */
	 public boolean isAscendingOrder() {
		 return ascendingOrder;
	 }


	 /**
	  * Sets the ascendingOrder value for this GetUsersRequest.
	  * 
	  * @param ascendingOrder
	  */
	 public void setAscendingOrder(boolean ascendingOrder) {
		 this.ascendingOrder = ascendingOrder;
	 }


	 /**
	  * Gets the maxCount value for this GetUsersRequest.
	  * 
	  * @return maxCount
	  */
	 public long getMaxCount() {
		 return maxCount;
	 }


	 /**
	  * Sets the maxCount value for this GetUsersRequest.
	  * 
	  * @param maxCount
	  */
	 public void setMaxCount(long maxCount) {
		 this.maxCount = maxCount;
	 }


	 /**
	  * Gets the skipCount value for this GetUsersRequest.
	  * 
	  * @return skipCount
	  */
	 public long getSkipCount() {
		 return skipCount;
	 }


	 /**
	  * Sets the skipCount value for this GetUsersRequest.
	  * 
	  * @param skipCount
	  */
	 public void setSkipCount(long skipCount) {
		 this.skipCount = skipCount;
	 }


	 /**
	  * Gets the filterValue value for this GetUsersRequest.
	  * 
	  * @return filterValue
	  */
	 public java.lang.String getFilterValue() {
		 return filterValue;
	 }


	 /**
	  * Sets the filterValue value for this GetUsersRequest.
	  * 
	  * @param filterValue
	  */
	 public void setFilterValue(java.lang.String filterValue) {
		 this.filterValue = filterValue;
	 }


	 /**
	  * Gets the filter value for this GetUsersRequest.
	  * 
	  * @return filter
	  */
	 public org.nwcg.www.webservices.security.getUsers.GetUsersRequestFilter getFilter() {
		 return filter;
	 }


	 /**
	  * Sets the filter value for this GetUsersRequest.
	  * 
	  * @param filter
	  */
	 public void setFilter(org.nwcg.www.webservices.security.getUsers.GetUsersRequestFilter filter) {
		 this.filter = filter;
	 }

	 private java.lang.Object __equalsCalc = null;
	 public synchronized boolean equals(java.lang.Object obj) {
		 if (!(obj instanceof GetUsersRequest)) return false;
		 GetUsersRequest other = (GetUsersRequest) obj;
		 if (obj == null) return false;
		 if (this == obj) return true;
		 if (__equalsCalc != null) {
			 return (__equalsCalc == obj);
		 }
		 __equalsCalc = obj;
		 boolean _equals;
		 _equals = true && 
		 ((this.applicationInstance==null && other.getApplicationInstance()==null) || 
				 (this.applicationInstance!=null &&
						 this.applicationInstance.equals(other.getApplicationInstance()))) &&
						 this.ascendingOrder == other.isAscendingOrder() &&
						 this.maxCount == other.getMaxCount() &&
						 this.skipCount == other.getSkipCount() &&
						 ((this.filterValue==null && other.getFilterValue()==null) || 
								 (this.filterValue!=null &&
										 this.filterValue.equals(other.getFilterValue()))) &&
										 ((this.filter==null && other.getFilter()==null) || 
												 (this.filter!=null &&
														 this.filter.equals(other.getFilter())));
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
		 if (getApplicationInstance() != null) {
			 _hashCode += getApplicationInstance().hashCode();
		 }
		 _hashCode += (isAscendingOrder() ? Boolean.TRUE : Boolean.FALSE).hashCode();
		 _hashCode += new Long(getMaxCount()).hashCode();
		 _hashCode += new Long(getSkipCount()).hashCode();
		 if (getFilterValue() != null) {
			 _hashCode += getFilterValue().hashCode();
		 }
		 if (getFilter() != null) {
			 _hashCode += getFilter().hashCode();
		 }
		 __hashCodeCalc = false;
		 return _hashCode;
	 }

	 // Type metadata
	 private static org.apache.axis.description.TypeDesc typeDesc =
		 new org.apache.axis.description.TypeDesc(GetUsersRequest.class, true);

	 static {
		 typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", ">getUsersRequest"));
		 org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
		 elemField.setFieldName("applicationInstance");
		 elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "ApplicationInstance"));
		 elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		 elemField.setNillable(false);
		 typeDesc.addFieldDesc(elemField);
		 elemField = new org.apache.axis.description.ElementDesc();
		 elemField.setFieldName("ascendingOrder");
		 elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "AscendingOrder"));
		 elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "boolean"));
		 elemField.setNillable(false);
		 typeDesc.addFieldDesc(elemField);
		 elemField = new org.apache.axis.description.ElementDesc();
		 elemField.setFieldName("maxCount");
		 elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "MaxCount"));
		 elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
		 elemField.setNillable(false);
		 typeDesc.addFieldDesc(elemField);
		 elemField = new org.apache.axis.description.ElementDesc();
		 elemField.setFieldName("skipCount");
		 elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "SkipCount"));
		 elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "long"));
		 elemField.setNillable(false);
		 typeDesc.addFieldDesc(elemField);
		 elemField = new org.apache.axis.description.ElementDesc();
		 elemField.setFieldName("filterValue");
		 elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "FilterValue"));
		 elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
		 elemField.setNillable(false);
		 typeDesc.addFieldDesc(elemField);
		 elemField = new org.apache.axis.description.ElementDesc();
		 elemField.setFieldName("filter");
		 elemField.setXmlName(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", "Filter"));
		 elemField.setXmlType(new javax.xml.namespace.QName("http://www.nwcg.org/webservices/security/getUsers", ">>getUsersRequest>Filter"));
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
