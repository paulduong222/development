package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.framework.report.data.TimeInvoiceData;
import gov.nwcg.isuite.framework.report.data.TimeInvoiceDetail;
import gov.nwcg.isuite.framework.util.CollectionUtility;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * OF288 Time Invoice report details
 * 
 * @author aroundy
 * 
 */
public class OF288TimeInvoice extends TimeInvoiceData {

	private String resourceName;
	private String requestNumber;

	private Boolean initialEmployment = false;
	private String employmentType;
	private String transferredFrom = "";
	private Boolean entitledToReturnTravelTime = false;
	private Boolean entitledToReturnTransportation = false;

	private Boolean employeeDischarged = false;
	private Boolean employeeQuit = false;

	private Boolean commissaryReport = false;
	private String commissarySummary = "See Attachment for Details \n Total: $";

	private Boolean hasPendingTotals = false;

	private PersonDetail personDetail;
	private Collection<OF288TimeDetail> of288TimeDetails;
	private EmergencyFirefighterCommissaryReportData commissaryDetails;

	private String hiringUnitName;
	private String hiringFax;
	private String hiringPhone;
	
	public OF288TimeInvoice() {}

	public void setReportType(String type) {}

	/**
	 * Return a String if a commissary report is also being generated.
	 * 
	 * TODO (may 27, 2011): verify if this should be if a comm report is being generated
	 * or if it is if comm report data exists, and so one should be generated.
	 * 
	 * @return
	 *    String commissarySummary
	 */
	public String getCommissarySummary() {
		if(commissaryReport) {
			return commissarySummary 
			+ String.format("%1$,( .2f", getDeductions());
		}
		return null;
	}

	/**
	 * Returns the dataSourceOf288TimeDetails based on the Of288TimeDetails collection.
	 * 
	 * @return 
	 *    the dataSourceOf288TimeDetails to return
	 */
	public JRBeanCollectionDataSource getDataSourceOf288TimeDetails() {
		return new JRBeanCollectionDataSource(getOf288TimeDetails());
	}

	/*
	 * getters and setters
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public void setEmploymentType(String employementType) {
		this.employmentType = employementType;
	}

	public String getEmploymentType() {
		return employmentType;
	}

	public String getCasualEmployee() {
		return getBoxChecked(getEmploymentType().equals("AD"));
	}

	public void setCasualEmployee(Boolean casualEmployee) {}

	public String getRegularGovEmployee() {
		return getBoxChecked(getEmploymentType().equals("FED"));
	}

	public void setRegularGovEmployee(Boolean regularGovEmployee) {}

	public String getOtherEmployee() {
		return getBoxChecked(getEmploymentType().equals("OTHER"));
	}

	public void setOtherEmployee(Boolean otherEmployee) {}

	/**
	 * @return the transferredFrom
	 */
	public String getTransferredFrom() {
		return transferredFrom;
	}

	public void setPostStartDate(Date dt){
		this.postStartDate=dt;
	}
	
	public Date getPostStartDate() {
		if(postStartDate==null && CollectionUtility.hasValue(of288TimeDetails)) {
			Calendar psd = Calendar.getInstance();
			Calendar time = Calendar.getInstance();
			for(OF288TimeDetail td : of288TimeDetails) {
				if(null != td.getFireFighterClassification() && !td.getFireFighterClassification().equalsIgnoreCase("BLANK")){
					time.set((int)td.getPostStartYear(), (int)td.getPostStartMonth()-1, (int)td.getPostStartDay());
					if (time.before(psd)) {
						psd.setTime(time.getTime());
					}
				}
			}
			this.postStartDate = psd.getTime();
		}
		return postStartDate;
	}

	public void setPostStopDate(Date dt){
		this.postStopDate=dt;
	}
	
	public Date getPostStopDate() {
		if(postStopDate==null && CollectionUtility.hasValue(of288TimeDetails)) {
			Calendar psd = Calendar.getInstance();
			psd.setTime(getPostStartDate());
			Calendar time = Calendar.getInstance();
			for(OF288TimeDetail td : of288TimeDetails) {
				if(null != td.getFireFighterClassification() && !td.getFireFighterClassification().equalsIgnoreCase("BLANK")){
					time.set((int)td.getYear(), (int)td.getPostStartMonth()-1, (int)td.getPostStartDay());
					if (time.after(psd)) {
						psd.setTime(time.getTime());
					}
				}
			}
			this.postStopDate = psd.getTime();
		}
		return postStopDate;
	}

	public void setInitialEmployment(Boolean initialEmployment) {
		this.initialEmployment = initialEmployment;
	}

	public String getInitialEmploymentNo() {
		return getBoxChecked(!initialEmployment);
	}


	public void setInitialEmploymentNo(Boolean initialEmployment) {}

	public String getInitialEmploymentYes() {
		return getBoxChecked(initialEmployment);
	}

	public void setInitialEmploymentYes(Boolean initialEmployment) {}

	public String getEmployeeDischarged() {
		return getBoxChecked(employeeDischarged);
	}

	public void setEmployeeDischarged(Boolean employeeDischarged) {
		this.employeeDischarged = employeeDischarged;
	}

	public String getEmployeeQuit() {
		return getBoxChecked(employeeQuit);
	}

	public void setEmployeeQuit(Boolean employeeQuit) {
		this.employeeQuit = employeeQuit;
	}

	public void setEntitledToReturnTransportation(Boolean entitledToReturnTransportation) {
		this.entitledToReturnTransportation = entitledToReturnTransportation;
	}

	public String getEntitledToReturnTransportationNo() {
		return getBoxChecked(false); //This is not filled in by eISuite so submit false to return blank string
		//return getBoxChecked(!entitledToReturnTransportation);
	}

	public void setEntitledToReturnTransportationNo(Boolean entitledToReturnTransportationNo) { }

	public String getEntitledToReturnTransportationYes() {
		return getBoxChecked(entitledToReturnTransportation);
	}

	public void setEntitledToReturnTransportationYes(Boolean entitledToReturnTransportationYes) { }

	public void setEntitledToReturnTravelTime(Boolean entitledToReturnTravelTime) {
		this.entitledToReturnTravelTime = entitledToReturnTravelTime;
	}

	public String getEntitledToReturnTravelTimeNo() {
		return getBoxChecked(!entitledToReturnTravelTime);
	}

	public void setEntitledToReturnTravelTimeNo(Boolean entitledToReturnTravelTimeNo) {}

	public String getEntitledToReturnTravelTimeYes() {
		return getBoxChecked(entitledToReturnTravelTime);
	}

	public void setEntitledToReturnTravelTimeYes(Boolean entitledToReturnTravelTimeYes) {}

	public Double getDeductions() {
		if(commissaryDetails!=null) 
			return commissaryDetails.getTotalAmount();
		return 0.0;
	}

	public void setCommissaryReport(Boolean commReport) {
		this.commissaryReport = commReport;
	}

	/**
	 * @param transferredFrom
	 *          the transferredFrom to set
	 */
	public void setTransferredFrom(String transferredFrom) {
		this.transferredFrom = transferredFrom;
	}

	public void setPersonDetail(PersonDetail personDetail) {
		this.personDetail = personDetail;
	}

	public PersonDetail getPersonDetail() {
		return personDetail;
	}

	public Collection<? extends TimeInvoiceDetail> getTimeInvoiceDetails() {
		return getOf288TimeDetails();
	}

	public Collection<OF288TimeDetail> getOf288TimeDetails() {
		if (of288TimeDetails == null) {
			of288TimeDetails = new ArrayList<OF288TimeDetail>();
		}
		return of288TimeDetails;
	}

	public void setOf288TimeDetails(Collection<OF288TimeDetail> of288TimeDetails) {
		this.of288TimeDetails = of288TimeDetails;
	}


	public EmergencyFirefighterCommissaryReportData getCommissaryDetails() {
		return commissaryDetails;
	}


	public void setCommissaryDetails(
			EmergencyFirefighterCommissaryReportData commissaryDetails) {
		this.commissaryDetails = commissaryDetails;
	}

	public Boolean getHasPendingTotals() {
		return hasPendingTotals;
	}

	public void setHasPendingTotals(Boolean hasPendingTotals) {
		this.hasPendingTotals = hasPendingTotals;
	}

	public String getHiringUnitName() {
		return hiringUnitName;
	}

	public void setHiringUnitName(String hiringUnitName) {
		this.hiringUnitName = hiringUnitName;
	}

	public String getHiringFax() {
		return hiringFax;
	}

	public void setHiringFax(String hiringFax) {
		this.hiringFax = hiringFax;
	}

	public String getHiringPhone() {
		return hiringPhone;
	}

	public void setHiringPhone(String hiringPhone) {
		this.hiringPhone = hiringPhone;
	}


}
