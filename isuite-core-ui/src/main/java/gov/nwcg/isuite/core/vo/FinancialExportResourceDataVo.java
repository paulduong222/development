package gov.nwcg.isuite.core.vo;

public class FinancialExportResourceDataVo {
	
	private String requestNumber;
	private String resourceName;
	private String employmentType;
	
	public FinancialExportResourceDataVo(){
		super();
	}
	
	public static String getResourceInfo(FinancialExportResourceDataVo vo){
		return vo.getRequestNumber() + " " + vo.getResourceName() + " " + vo.getEmploymentType();
	}
	
	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	
	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}
	
	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}
	
	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}
	
	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}

}
