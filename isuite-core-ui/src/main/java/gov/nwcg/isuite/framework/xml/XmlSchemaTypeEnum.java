package gov.nwcg.isuite.framework.xml;

public enum XmlSchemaTypeEnum {
	USER_TRANSFER("UserTransfer.xsd", "gov.nwcg.isuite.xml")
	,FINANCIAL_EXPORT("FinancialExport.xsd", "gov.nwcg.isuite.xml.financialexport")
	,INCIDENT_TRANSFER("IncidentTransfer.xsd","gov.nwcg.isuite.xml")
	,ROSS_XML_DATA("RossXmlData.xsd","gov.nwcg.isuite.xml.ross")
	,ROSS_XML_DATA2("RossXmlData2.xsd","gov.nwcg.isuite.xml.ross2")
	,ROSS_RESOURCE_XML_DATA("RossResourceXmlData.xsd","gov.nwcg.isuite.xml.rossresource")
	,CUSTOM_REPORT("CustomReport.xsd", "gov.nwcg.isuite.xml.customreport")
	,DATA_TRANSFER("DataTransfer.xsd", "gov.nwcg.isuite.xml.datatransfer")
	,MASTER_FREQUENCY("MasterFrequency.xsd", "gov.nwcg.isuite.xml.masterfrequency");
	
	private String fileName="";
	private String contextPath="";
	
	XmlSchemaTypeEnum(String fileName, String ctxPath){
		this.fileName=fileName;
		this.contextPath=ctxPath;
	}
	
	/**
	 * @return
	 */
	public String getFileName(){
		return this.fileName;
	}
	
	/**
	 * @return
	 */
	public String getContextPath(){
		return this.contextPath;
	}
}
