package gov.nwcg.isuite.core.reports.filter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import gov.nwcg.isuite.core.vo.DateTransferVo;
import gov.nwcg.isuite.framework.filter.TimeReportFilter;

public class MissingDaysOfPostingsReportFilter extends TimeReportFilter{
	private Long incidentId = 0L;
	private Long incidentGroupId = 0L;	
	
	private boolean isIncidentGroup = false;
	
	private String startDateChar = "";
	private String endDateChar = "";
	private String endDateDbChar = ""; //for Query
	
	private Date startDate;
	private Date endDate;
	
	private String personnelOrVendor = "Personnel";
			
	private String agencyName = "";
	
	private String employmentCode = "ALL";
	
	private String noneOrAgency = "None";

	private String reqNumOrResNameOrAgencyOrCode = "Request Number";
	
	private String reqNumOrResName = "Request Number";
		
	private boolean excludeDemobReassigned = false;
	
	private DateTransferVo startDateVo=new DateTransferVo();
	private DateTransferVo endDateVo=new DateTransferVo();

	public MissingDaysOfPostingsReportFilter() {
	}
	
	public DateTransferVo getStartDateVo() {
		return startDateVo;
	}

	public void setStartDateVo(DateTransferVo startDateVo) {
		this.startDateVo = startDateVo;
	}

	public DateTransferVo getEndDateVo() {
		return endDateVo;
	}

	public void setEndDateVo(DateTransferVo endDateVo) {
		this.endDateVo = endDateVo;
	}
	
	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	public boolean getIsIncidentGroup() {
		return isIncidentGroup;
	}

	public void setIncidentGroup(boolean isIncidentGroup) {
		this.isIncidentGroup = isIncidentGroup;
	}
	
	public String getPersonnelOrVendor() {
		return personnelOrVendor;
	}

	public void setPersonnelOrVendor(String personnelOrVendor) {
		this.personnelOrVendor = personnelOrVendor;
	}
	
	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}

	public String getEmploymentCode() {
		return employmentCode;
	}

	public void setEmploymentCode(String employmentCode) {
		this.employmentCode = employmentCode;
	}
	
	public String getNoneOrAgency() {
		return noneOrAgency;
	}

	public void setNoneOrAgency(String noneOrAgency) {
		this.noneOrAgency = noneOrAgency;
	}

	public String getReqNumOrResNameOrAgencyOrCode() {
		return reqNumOrResNameOrAgencyOrCode;
	}

	public void setReqNumOrResNameOrAgencyOrCode(
			String reqNumOrResNameOrAgencyOrCode) {
		this.reqNumOrResNameOrAgencyOrCode = reqNumOrResNameOrAgencyOrCode;
	}

	public String getReqNumOrResName() {
		return reqNumOrResName;
	}

	public void setReqNumOrResName(String reqNumOrResName) {
		this.reqNumOrResName = reqNumOrResName;
	}

	public boolean isExcludeDemobReassigned() {
		return excludeDemobReassigned;
	}

	public void setExcludeDemobReassigned(boolean excludeDemobReassigned) {
		this.excludeDemobReassigned = excludeDemobReassigned;
	}

	public String getStartDateChar() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.getStartDate());
	}

	public void setStartDateChar(String startDateChar) {
		this.startDateChar = startDateChar;
	}

	public String getEndDateChar() {
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(this.getEndDate());
	}

	public void setEndDateChar(String endDateChar) {
		this.endDateChar = endDateChar;
	}
	
	public String getEndDateDbChar() {
		Date nextDate = new Date();
		nextDate.setTime(this.getEndDate().getTime() + 1 * 24 * 60 * 60 * 1000);
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		return df.format(nextDate);
	}

	public void setEndDateDbChar(String endDateDbChar) {
		this.endDateDbChar = endDateDbChar;
	}
	
	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}