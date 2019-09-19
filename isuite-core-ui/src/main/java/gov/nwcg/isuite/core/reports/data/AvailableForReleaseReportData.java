package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.BaseReportData;
import gov.nwcg.isuite.framework.util.DateUtil;

import java.util.Calendar;
import java.util.Date;

public class AvailableForReleaseReportData extends BaseReportData {
	
	private String resourceCategoryCode = "";
	private String referenceNumber = "1";
	private String requestNumber = "";
	private String unitId = "";
	private String kindCode = "";
	private String demobCity = "";
	private String demobState = "";
	private String returnTravelMethod = "";
	private String returnJetport = "";
	private Date tentativeReleaseDate = null;
	private Boolean reassignable = false;
	private Date mobilizationStartDate = null;
	private Date firstWorkDate = null;
	private Long lengthAtAssignment = null;
	private String releaseRemarks = "";
	private String otherQuals = "";
	private Long resourceId;
	private String destination;
	private String daysLeft="0";
	
	public AvailableForReleaseReportData() {
	}

	/**
	 * @return the resourceCategoryCode
	 */
	public String getResourceCategoryCode() {
		return resourceCategoryCode;
	}

	/**
	 * @param resourceCategoryCode the resourceCategoryCode to set
	 */
	public void setResourceCategoryCode(String resourceCategoryCode) {
		this.resourceCategoryCode = resourceCategoryCode;
	}

	/**
	 * @param referenceNumber the referenceNumber to set
	 */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/**
	 * @return the referenceNumber
	 */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * @param kindCode the kindCode to set
	 */
	public void setKindCode(String kindCode) {
		this.kindCode = kindCode;
	}

	/**
	 * @return the kindCode
	 */
	public String getKindCode() {
		return kindCode;
	}

	/**
	 * @return the demobCity
	 */
	public String getDemobCity() {
		return demobCity;
	}

	/**
	 * @param demobCity the demobCity to set
	 */
	public void setDemobCity(String demobCity) {
		this.demobCity = demobCity;
	}

	/**
	 * @return the demobState
	 */
	public String getDemobState() {
		return demobState;
	}

	/**
	 * @param demobState the demobState to set
	 */
	public void setDemobState(String demobState) {
		this.demobState = demobState;
	}

	/**
	 * @return the returnTravelMethod
	 */
	public String getReturnTravelMethod() {
		return returnTravelMethod;
	}

	/**
	 * @param returnTravelMethod the returnTravelMethod to set
	 */
	public void setReturnTravelMethod(String returnTravelMethod) {
		this.returnTravelMethod = returnTravelMethod;
	}

	/**
	 * @return the returnJetport
	 */
	public String getReturnJetport() {
		return returnJetport;
	}

	/**
	 * @param returnJetport the returnJetport to set
	 */
	public void setReturnJetport(String returnJetport) {
		this.returnJetport = returnJetport;
	}

	/**
	 * @return the tentativeReleaseDate
	 */
	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
	}

	/**
	 * @param tentativeReleaseDate the tentativeReleaseDate to set
	 */
	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}

	/**
	 * @return the reassignable
	 */
	public Boolean getReassignable() {
		return reassignable;
	}

	/**
	 * @param reassignable the reassignable to set
	 */
	public void setReassignable(Boolean reassignable) {
		this.reassignable = reassignable;
	}

	/**
	 * @return the mobilizationStartDate
	 */
	public Date getMobilizationStartDate() {
		return mobilizationStartDate;
	}

	/**
	 * @param mobilizationStartDate the mobilizationStartDate to set
	 */
	public void setMobilizationStartDate(Date mobilizationStartDate) {
		this.mobilizationStartDate = mobilizationStartDate;
	}

	/**
	 * @return the lengthAtAssignment
	 */
	public Long getLengthAtAssignment() {
		return lengthAtAssignment;
	}

	/**
	 * @param lengthAtAssignment the lengthAtAssignment to set
	 */
	public void setLengthAtAssignment(Long lengthAtAssignment) {
		this.lengthAtAssignment = lengthAtAssignment;
	}

	/**
	 * @return the  releaseRemarks
	 */
	public String getReleaseRemarks() {
		return  releaseRemarks;
	}

	/**
	 * @param  releaseRemarks the  releaseRemarks to set
	 */
	public void setReleaseRemarks(String  releaseRemarks) {
		this.releaseRemarks = releaseRemarks;
	}

	/**
	 * @return the otherQuals
	 */
	public String getOtherQuals() {
		return otherQuals;
	}

	/**
	 * @param otherQuals the otherQuals to set
	 */
	public void setOtherQuals(String otherQuals) {
		this.otherQuals = otherQuals;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	public String getDestination() {
		String rtn="";
		if( (null != demobCity && !demobCity.isEmpty()))
		{
			rtn=demobCity;
		}
		
		if( (null != demobState && !demobState.isEmpty()))
		{
			if(!rtn.isEmpty()){
				rtn=rtn+", "+demobState;
			}else{
				rtn=", "+demobState;
			}
		}
		
		return rtn;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getDaysLeft() {
		if(this.reassignable ){
			if(null != this.firstWorkDate
					&&
				(null != this.lengthAtAssignment && this.lengthAtAssignment.intValue() > 0 ) ){
				
				try{
					Date dt = DateUtil.addDaysToDate(this.firstWorkDate, this.lengthAtAssignment.intValue());
					Date dt2 = Calendar.getInstance().getTime();
	
					if(dt2.before(dt)){
						long val = DateUtil.diffDays(dt2, dt);
						
						if(val > 0)
							return String.valueOf(val);
						else
							return "0";
					}else
						return "0";
				}catch(Exception e){
					return "0";
				}
			}
		}
		
		return daysLeft;
	}

	public void setDaysLeft(String daysLeft) {
		this.daysLeft = daysLeft;
	}

	/**
	 * @param firstWorkDate the firstWorkDate to set
	 */
	public void setFirstWorkDate(Date firstWorkDate) {
		this.firstWorkDate = firstWorkDate;
	}

	/**
	 * @return the firstWorkDate
	 */
	public Date getFirstWorkDate() {
		return firstWorkDate;
	}

}
