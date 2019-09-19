package gov.nwcg.isuite.core.reports.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

public class EmergencyFirefighterCommissaryReportData {

	private PersonDetail personDetail;

	private String identificationNumber;
	private String officialNumber;
	private String reportType;
	private String reportTypeHeader;
	private Date startDate;
	private Date endDate;

	private String incidentLocation;
	private String incidentName;
	private String incidentNumber;
	private String incidentState;
	private String unitCode;

	private String requestNumber;

	private String regularGovEmployee;
	private String casualEmployee;
	private String otherEmployee;
	
	private String hiringUnitName;
	private String hiringPhone;
	private String hiringFax;
	private String pointOfHire;
	
	private Collection<EmergencyFirefighterCommissarySubReportData> subReportData;

	public EmergencyFirefighterCommissaryReportData() {
	}

	/**
	 * @return the dataSourceSubReport
	 */
	public JRBeanCollectionDataSource getDataSourceSubReport() {
		return new JRBeanCollectionDataSource(this.getSubReportData());
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		Double total = 0.0;
		for (EmergencyFirefighterCommissarySubReportData data : getSubReportData()) {
			total += data.getAmount();
		}
		return total;
	}

	public PersonDetail getPersonDetail() {
		return personDetail;
	}

	public void setPersonDetail(PersonDetail personDetail) {
		this.personDetail = personDetail;
	}

	public String getIdentificationNumber() {
		return identificationNumber;
	}

	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	public String getOfficialNumber() {
		return officialNumber;
	}

	public void setOfficialNumber(String officialNumber) {
		this.officialNumber = officialNumber;
	}

	public String getReportType() {
		return reportType;
	}

	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

	public String getReportTypeHeader() {
		return reportTypeHeader;
	}

	public void setReportTypeHeader(String reportTypeHeader) {
		this.reportTypeHeader = reportTypeHeader;
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

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode
	 *            the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the subReportData
	 */
	public Collection<EmergencyFirefighterCommissarySubReportData> getSubReportData() {
		if (subReportData == null) {
			subReportData = new ArrayList<EmergencyFirefighterCommissarySubReportData>();
		}
		return subReportData;
	}

	/**
	 * @param subReportData
	 *            the subReportData to set
	 */
	public void setSubReportData(Collection<EmergencyFirefighterCommissarySubReportData> subReportData) {
		this.subReportData = subReportData;
	}

	public String getIncidentLocation() {
		return incidentLocation;
	}

	public void setIncidentLocation(String incidentLocation) {
		this.incidentLocation = incidentLocation;
	}

	public String getIncidentName() {
		return incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentNumber() {
		return incidentNumber;
	}

	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	public String getIncidentState() {
		return incidentState;
	}

	public void setIncidentState(String incidentState) {
		this.incidentState = incidentState;
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

	public String getRegularGovEmployee() {
		return regularGovEmployee;
	}

	public void setRegularGovEmployee(String regularGovEmployee) {
		this.regularGovEmployee = regularGovEmployee;
	}

	public String getCasualEmployee() {
		return casualEmployee;
	}

	public void setCasualEmployee(String casualEmployee) {
		this.casualEmployee = casualEmployee;
	}

	public String getOtherEmployee() {
		return otherEmployee;
	}

	public void setOtherEmployee(String otherEmployee) {
		this.otherEmployee = otherEmployee;
	}

	public String getHiringUnitName() {
		return hiringUnitName;
	}

	public void setHiringUnitName(String hiringUnitName) {
		this.hiringUnitName = hiringUnitName;
	}

	public String getHiringPhone() {
		return hiringPhone;
	}

	public void setHiringPhone(String hiringPhone) {
		this.hiringPhone = hiringPhone;
	}

	public String getHiringFax() {
		return hiringFax;
	}

	public void setHiringFax(String hiringFax) {
		this.hiringFax = hiringFax;
	}

	public String getPointOfHire() {
		return pointOfHire;
	}

	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

}
