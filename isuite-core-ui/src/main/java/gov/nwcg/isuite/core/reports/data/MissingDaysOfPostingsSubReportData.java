package gov.nwcg.isuite.core.reports.data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Report data object for MissingDaysOfPostingsSubReport.jrxml.
 */
public class MissingDaysOfPostingsSubReportData {
		
	private String requestNumber = "";
	private String resourceName = "";
	private Date postingDate = null;
	private String postingDateChar = "";
	private String checkInDateChar = "";
	private String firstPostingDateChar = "";
	private String agencyName = "";
	
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
	
	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public Date getPostingDate() {
		return postingDate;
	}

	public void setPostingDate(Date postingDate) {
		this.postingDate = postingDate;
	}	
	
	public String getPostingDateChar() {
		return postingDateChar;
	}

	public void setPostingDateChar(String postingDateChar) {
		this.postingDateChar = postingDateChar;
	}
	
	public String getCheckInDateChar() {
		return checkInDateChar;
	}

	public void setCheckInDateChar(String checkInDateChar) {
		this.checkInDateChar = checkInDateChar;
	}
	
	public String getFirstPostingDateChar() {
		return firstPostingDateChar;
	}

	public void setFirstPostingDateChar(String firstPostingDateChar) {
		this.firstPostingDateChar = firstPostingDateChar;
	}
	
	public String getAgencyName() {
		return agencyName;
	}

	public void setAgencyName(String agencyName) {
		this.agencyName = agencyName;
	}


}