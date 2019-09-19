package gov.nwcg.isuite.framework.report.data;

import java.util.Calendar;
import java.util.Date;

public class TimeReportData {
	
	protected final String boxChecked = "x";
	protected final String printFinal = "FINAL";
	protected final String printInterim = "INTERIM";
	
	private String accountingCode;
	private String agreementNumber;
	private Boolean casualEmployee;
	private Long day;
	private Boolean employeeDischarged;
	private Boolean employeeQuit;
	private Boolean entitledToReturnTransportationNo;
	private Boolean entitledToReturnTransportationYes;
	private Boolean entitledToReturnTravelTimeNo;
	private Boolean entitledToReturnTravelTimeYes;
	private String faxNumber;
	private String fireFighterClassification;
	private Double grossAmount;
	private Double totalAmount;
	private String headerSubTitle;
	private String headerTitle;
	private String identificationNumber;
	private String incidentLocation;
	private String incidentName;
	private String incidentNumber;
	private String incidentState;
	private String incidentYear;
	private Boolean initialEmploymentNo;
	private Boolean initialEmploymentYes;
	private Long month;
	private String officialNumber;
	private Boolean otherEmployee;
	private String phoneNumber;
	private Double rate;
	private Boolean regularGovEmployee;
	private String requestNumber;
	private String resourceCity;
	private String resourceName;
	private String resourceFirstName;
	private String resourceLastName;
	private String resourceSSN;
	private String resourceState;
	private String resourceStreetAddress1;
	private String resourceStreetAddress2;
	private String resourceZipCode;
	private Date postStartDate;
	private Date postStopDate;
	private Double totalHours;
	private String unitCode;
	private Long year;
	private String pointOfHire;
	private String make;
	private String model;
	private String contractorName;
	private Date lastDateToIncludeOnInvoice;
	private Boolean finalInvoice;
	private Date reportPrintedDate;
	private Date reportModifiedDate;
	private Double netAmount;
	private Double deductions;
	private Double additions;
	private String employmentType;
	private Date firstDateToIncludeOnReport;
	private Date lastDateToIncludeOnReport;

	public TimeReportData() {

	}

	/**
	 * @return the accountingCode
	 */
	public String getAccountingCode() {
		return accountingCode;
	}

	/**
	 * @return the casualEmployee
	 */
	public String getCasualEmployee() {
		if(null != this.employmentType && this.employmentType.equals("AD")){
		//if(this.casualEmployee != null && this.casualEmployee == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the day
	 */
	public Long getDay() {
		if(this.day == null) {
			this.day = ((Integer)Calendar.getInstance().get(Calendar.DAY_OF_MONTH)).longValue();
		}
		return day;
	}

	/**
	 * @return the employeeDischarged
	 */
	public String getEmployeeDischarged() {
		if(this.employeeDischarged != null && this.employeeDischarged == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the employeeQuit
	 */
	public String getEmployeeQuit() {
		if(this.employeeQuit != null && this.employeeQuit == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the entitledToReturnTransportationNo
	 */
	public String getEntitledToReturnTransportationNo() {
		if(this.entitledToReturnTransportationNo != null && this.entitledToReturnTransportationNo == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the entitledToReturnTransportationYes
	 */
	public String getEntitledToReturnTransportationYes() {
		if(this.entitledToReturnTransportationYes != null && this.entitledToReturnTransportationYes == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the entitledToReturnTravelTimeNo
	 */
	public String getEntitledToReturnTravelTimeNo() {
		if(this.entitledToReturnTravelTimeNo != null && this.entitledToReturnTravelTimeNo == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the entitledToReturnTravelTimeYes
	 */
	public String getEntitledToReturnTravelTimeYes() {
		if(this.entitledToReturnTravelTimeYes != null && this.entitledToReturnTravelTimeYes == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}

	/**
	 * @return the fireFighterClassification
	 */
	public String getFireFighterClassification() {
		return fireFighterClassification;
	}

	/**
	 * @return the grossAmount
	 */
	public Double getGrossAmount() {
		return grossAmount;
	}

	/**
	 * @return the headerSubTitle
	 */
	public String getHeaderSubTitle() {
		return headerSubTitle;
	}

	/**
	 * @return the headerTitle
	 */
	public String getHeaderTitle() {
		return headerTitle;
	}

	/**
	 * @return the identificationNumber
	 */
	public String getIdentificationNumber() {
		return identificationNumber;
	}

	/**
	 * @return the incidentLocation
	 */
	public String getIncidentLocation() {
		return incidentLocation;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * @return the incidentState
	 */
	public String getIncidentState() {
		return incidentState;
	}

	/**
	 * @return the initialEmploymentNo
	 */
	public String getInitialEmploymentNo() {
		if(this.initialEmploymentNo != null && this.initialEmploymentNo == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the initialEmploymentYes
	 */
	public String getInitialEmploymentYes() {
		if(this.initialEmploymentYes != null && this.initialEmploymentYes == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the month
	 */
	public Long getMonth() {
		if(this.month == null) {
			this.month = ((Integer)Calendar.getInstance().get(Calendar.MONTH)).longValue() + 1;
		}
		return month;
	}

	/**
	 * @return the officialNumber
	 */
	public String getOfficialNumber() {
		return officialNumber;
	}

	/**
	 * @return the otherEmployee
	 */
	public String getOtherEmployee() {
		if(this.otherEmployee != null && this.otherEmployee == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}

	/**
	 * @return the rate
	 */
	public Double getRate() {
		return rate;
	}

	/**
	 * @return the regularGovEmployee
	 */
	public String getRegularGovEmployee() {
		if(null != this.employmentType && this.employmentType.equals("FED")){
		//if(this.regularGovEmployee != null && this.regularGovEmployee == true) {
			return this.boxChecked;
		} else {
			return "";
		}
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @return the resourceCity
	 */
	public String getResourceCity() {
		return resourceCity;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @return the resourceSSN
	 */
	public String getResourceSSN() {
		return resourceSSN;
	}

	/**
	 * @return the resourceState
	 */
	public String getResourceState() {
		return resourceState;
	}

	/**
	 * @return the resourceStreetAddress1
	 */
	public String getResourceStreetAddress1() {
		return resourceStreetAddress1;
	}

	/**
	 * @return the resourceStreetAddress2
	 */
	public String getResourceStreetAddress2() {
		return resourceStreetAddress2;
	}

	/**
	 * @param resourceStreetAddress2 the resourceStreetAddress2 to set
	 */
	public void setResourceStreetAddress2(String resourceStreetAddress2) {
		this.resourceStreetAddress2 = resourceStreetAddress2;
	}

	/**
	 * @return the resourceZipCode
	 */
	public String getResourceZipCode() {
		return resourceZipCode;
	}

	/**
	 * @return the postStartDate
	 */
	public Date getPostStartDate() {
		return postStartDate;
	}

	/**
	 * @return the postStopDate
	 */
	public Date getPostStopDate() {
		return postStopDate;
	}

	/**
	 * @return the totalHours
	 */
	public Double getTotalHours() {
		return totalHours;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @return the year
	 */
	public Long getYear() {
		if(this.year == null) {
			this.year = ((Integer)Calendar.getInstance().get(Calendar.YEAR)).longValue();
		}
		return year;
	}

	/**
	 * @param accountingCode the accountingCode to set
	 */
	public void setAccountingCode(String accountingCode) {
		this.accountingCode = accountingCode;
	}

	/**
	 * @param casualEmployee the casualEmployee to set
	 */
	public void setCasualEmployee(Boolean casualEmployee) {
		this.casualEmployee = casualEmployee;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(Long day) {
		this.day = day;
	}

	/**
	 * @param employeeDischarged the employeeDischarged to set
	 */
	public void setEmployeeDischarged(Boolean employeeDischarged) {
		this.employeeDischarged = employeeDischarged;
	}

	/**
	 * @param employeeQuit the employeeQuit to set
	 */
	public void setEmployeeQuit(Boolean employeeQuit) {
		this.employeeQuit = employeeQuit;
	}

	/**
	 * @param entitledToReturnTransportationNo the entitledToReturnTransportationNo to set
	 */
	public void setEntitledToReturnTransportationNo(Boolean entitledToReturnTransportationNo) {
		this.entitledToReturnTransportationNo = entitledToReturnTransportationNo;
	}

	/**
	 * @param entitledToReturnTransportationYes the entitledToReturnTransportationYes to set
	 */
	public void setEntitledToReturnTransportationYes(Boolean entitledToReturnTransportationYes) {
		this.entitledToReturnTransportationYes = entitledToReturnTransportationYes;
	}

	/**
	 * @param entitledToReturnTravelTimeNo the entitledToReturnTravelTimeNo to set
	 */
	public void setEntitledToReturnTravelTimeNo(Boolean entitledToReturnTravelTimeNo) {
		this.entitledToReturnTravelTimeNo = entitledToReturnTravelTimeNo;
	}

	/**
	 * @param entitledToReturnTravelTimeYes the entitledToReturnTravelTimeYes to set
	 */
	public void setEntitledToReturnTravelTimeYes(Boolean entitledToReturnTravelTimeYes) {
		this.entitledToReturnTravelTimeYes = entitledToReturnTravelTimeYes;
	}

	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(String faxNumber) {
		this.faxNumber = faxNumber;
	}

	/**
	 * @param fireFighterClassification the fireFighterClassification to set
	 */
	public void setFireFighterClassification(String fireFighterClassification) {
		this.fireFighterClassification = fireFighterClassification;
	}

	/**
	 * @param grossAmount the grossAmount to set
	 */
	public void setGrossAmount(Double grossAmount) {
		this.grossAmount = grossAmount;
	}

	/**
	 * @param headerSubTitle the headerSubTitle to set
	 */
	public void setHeaderSubTitle(String headerSubTitle) {
		this.headerSubTitle = headerSubTitle;
	}

	/**
	 * @param headerTitle the headerTitle to set
	 */
	public void setHeaderTitle(String headerTitle) {
		this.headerTitle = headerTitle;
	}

	/**
	 * @param identificationNumber the identificationNumber to set
	 */
	public void setIdentificationNumber(String identificationNumber) {
		this.identificationNumber = identificationNumber;
	}

	/**
	 * @param incidentLocation the incidentLocation to set
	 */
	public void setIncidentLocation(String incidentLocation) {
		this.incidentLocation = incidentLocation;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * @param incidentState the incidentState to set
	 */
	public void setIncidentState(String incidentState) {
		this.incidentState = incidentState;
	}

	/**
	 * @param initialEmploymentNo the initialEmploymentNo to set
	 */
	public void setInitialEmploymentNo(Boolean initialEmploymentNo) {
		this.initialEmploymentNo = initialEmploymentNo;
	}

	/**
	 * @param initialEmploymentYes the initialEmploymentYes to set
	 */
	public void setInitialEmploymentYes(Boolean initialEmploymentYes) {
		this.initialEmploymentYes = initialEmploymentYes;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(Long month) {
		this.month = month;
	}

	/**
	 * @param officialNumber the officialNumber to set
	 */
	public void setOfficialNumber(String officialNumber) {
		this.officialNumber = officialNumber;
	}

	/**
	 * @param otherEmployee the otherEmployee to set
	 */
	public void setOtherEmployee(Boolean otherEmployee) {
		this.otherEmployee = otherEmployee;
	}

	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	/**
	 * @param rate the rate to set
	 */
	public void setRate(Double rate) {
		this.rate = rate;
	}

	/**
	 * @param regularGovEmployee the regularGovEmployee to set
	 */
	public void setRegularGovEmployee(Boolean regularGovEmployee) {
		this.regularGovEmployee = regularGovEmployee;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @param resourceCity the resourceCity to set
	 */
	public void setResourceCity(String resourceCity) {
		this.resourceCity = resourceCity;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @param resourceSSN the resourceSSN to set
	 */
	public void setResourceSSN(String resourceSSN) {
		this.resourceSSN = resourceSSN;
	}

	/**
	 * @param resourceState the resourceState to set
	 */
	public void setResourceState(String resourceState) {
		this.resourceState = resourceState;
	}

	/**
	 * @param resourceStreetAddress1 the resourceStreetAddress1 to set
	 */
	public void setResourceStreetAddress1(String resourceStreetAddress1) {
		this.resourceStreetAddress1 = resourceStreetAddress1;
	}

	/**
	 * @param resourceZipCode the resourceZipCode to set
	 */
	public void setResourceZipCode(String resourceZipCode) {
		this.resourceZipCode = resourceZipCode;
	}

	/**
	 * @param postStartDate the postStartDate to set
	 */
	public void setPostStartDate(Date postStartDate) {
		this.postStartDate = postStartDate;
	}

	/**
	 * @param postStopDate the postStopDate to set
	 */
	public void setPostStopDate(Date postStopDate) {
		this.postStopDate = postStopDate;
	}

	/**
	 * @param totalHours the totalHours to set
	 */
	public void setTotalHours(Double totalHours) {
		this.totalHours = totalHours;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * @return the pointOfHire
	 */
	public String getPointOfHire() {
		return pointOfHire;
	}

	/**
	 * @param pointOfHire the pointOfHire to set
	 */
	public void setPointOfHire(String pointOfHire) {
		this.pointOfHire = pointOfHire;
	}

	/**
	 * @return the make
	 */
	public String getMake() {
		return make;
	}

	/**
	 * @param make the make to set
	 */
	public void setMake(String make) {
		this.make = make;
	}

	/**
	 * @return the model
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @param model the model to set
	 */
	public void setModel(String model) {
		this.model = model;
	}

	/**
	 * @return the contractorName
	 */
	public String getContractorName() {
		return contractorName;
	}

	/**
	 * @param contractorName the contractorName to set
	 */
	public void setContractorName(String contractorName) {
		this.contractorName = contractorName;
	}

	/**
	 * @return the lastDateToIncludeOnInvoice
	 */
	public Date getLastDateToIncludeOnInvoice() {
		return lastDateToIncludeOnInvoice;
	}

	/**
	 * @param lastDateToIncludeOnInvoice the lastDateToIncludeOnInvoice to set
	 */
	public void setLastDateToIncludeOnInvoice(Date lastDateToIncludeOnInvoice) {
		this.lastDateToIncludeOnInvoice = lastDateToIncludeOnInvoice;
	}

	/**
	 * @return the finalInvoice
	 */
	public String getFinalInvoice() {
		if(this.finalInvoice != null && this.finalInvoice == true) {
			return this.printFinal;
		} else {
			return this.printInterim;
		}
	}

	/**
	 * @param finalInvoice the finalInvoice to set
	 */
	public void setFinalInvoice(Boolean finalInvoice) {
		this.finalInvoice = finalInvoice;
	}

	/**
	 * @return the reportPrintedDate
	 */
	public Date getReportPrintedDate() {
		return reportPrintedDate;
	}

	/**
	 * @param reportPrintedDate the reportPrintedDate to set
	 */
	public void setReportPrintedDate(Date reportPrintedDate) {
		this.reportPrintedDate = reportPrintedDate;
	}

	/**
	 * @return the reportModifiedDate
	 */
	public Date getReportModifiedDate() {
		return reportModifiedDate;
	}

	/**
	 * @param reportModifiedDate the reportModifiedDate to set
	 */
	public void setReportModifiedDate(Date reportModifiedDate) {
		this.reportModifiedDate = reportModifiedDate;
	}

	/**
	 * @return the totalAmount
	 */
	public Double getTotalAmount() {
		return totalAmount;
	}

	/**
	 * @param totalAmount the totalAmount to set
	 */
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}

	/**
	 * @return the netAmount
	 */
	public Double getNetAmount() {
		return netAmount;
	}

	/**
	 * @param netAmount the netAmount to set
	 */
	public void setNetAmount(Double netAmount) {
		this.netAmount = netAmount;
	}

	/**
	 * @return the deductions
	 */
	public Double getDeductions() {
		return deductions;
	}

	/**
	 * @param deductions the deductions to set
	 */
	public void setDeductions(Double deductions) {
		this.deductions = deductions;
	}

	/**
	 * @return the additions
	 */
	public Double getAdditions() {
		return additions;
	}

	/**
	 * @param additions the additions to set
	 */
	public void setAdditions(Double additions) {
		this.additions = additions;
	}

	/**
	 * @return the employmentType
	 */
	public String getEmploymentType() {
		return employmentType;
	}

	/**
	 * @param employmentType the employmentType to set
	 */
	public void setEmploymentType(String employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * @return the resourceFirstName
	 */
	public String getResourceFirstName() {
		return resourceFirstName;
	}

	/**
	 * @param resourceFirstName the resourceFirstName to set
	 */
	public void setResourceFirstName(String resourceFirstName) {
		this.resourceFirstName = resourceFirstName;
	}

	/**
	 * @return the resourceLastName
	 */
	public String getResourceLastName() {
		return resourceLastName;
	}

	/**
	 * @param resourceLastName the resourceLastName to set
	 */
	public void setResourceLastName(String resourceLastName) {
		this.resourceLastName = resourceLastName;
	}

	/**
	 * @return the firstDateToIncludeOnReport
	 */
	public Date getFirstDateToIncludeOnReport() {
		return firstDateToIncludeOnReport;
	}

	/**
	 * @param firstDateToIncludeOnReport the firstDateToIncludeOnReport to set
	 */
	public void setFirstDateToIncludeOnReport(Date firstDateToIncludeOnReport) {
		this.firstDateToIncludeOnReport = firstDateToIncludeOnReport;
	}

	/**
	 * @return the lastDateToIncludeOnReport
	 */
	public Date getLastDateToIncludeOnReport() {
		return lastDateToIncludeOnReport;
	}

	/**
	 * @param lastDateToIncludeOnReport the lastDateToIncludeOnReport to set
	 */
	public void setLastDateToIncludeOnReport(Date lastDateToIncludeOnReport) {
		this.lastDateToIncludeOnReport = lastDateToIncludeOnReport;
	}

	/**
	 * @return the agreementNumber
	 */
	public String getAgreementNumber() {
		return agreementNumber;
	}

	/**
	 * @param agreementNumber the agreementNumber to set
	 */
	public void setAgreementNumber(String agreementNumber) {
		this.agreementNumber = agreementNumber;
	}

	/**
	 * @return the incidentYear
	 */
	public String getIncidentYear() {
		return incidentYear;
	}

	/**
	 * @param incidentYear the incidentYear to set
	 */
	public void setIncidentYear(String incidentYear) {
		this.incidentYear = incidentYear;
	}

}
