package gov.nwcg.isuite.core.reports.data;

/**
 * Data object that contains agency, fax, and resource information retrieved from the database.
 * Information from a collection of these objects will transformed to create
 * a collection of PersonnelTimeRepCTDVo objects, which will be the data source of the 
 * checkbox tree component on the Flex UI.
 */
public class PersonnelTimeReportAgencyFaxResourceData {

	private Long agencyId;
	private String agencyCode;
	private String resourceName;
	private Long resourceId;
	private String faxNumber;
	
	public PersonnelTimeReportAgencyFaxResourceData() {}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public String getAgencyCode() {
		return agencyCode;
	}

	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public String getFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}
}
