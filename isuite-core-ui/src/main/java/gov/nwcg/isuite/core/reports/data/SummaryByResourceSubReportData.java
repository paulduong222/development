package gov.nwcg.isuite.core.reports.data;

import java.math.BigInteger;
import java.util.Date;

/**
 * Report data object for SummaryByResourceSubReport.jrxml.
 */
public class SummaryByResourceSubReportData extends CostReportSubReportData{

	private Long resourceid;
	private String resourceName;
	private String requestnum;
	private String itemCode;
	private String reqResnum;

	public SummaryByResourceSubReportData() {}

	public Long getResourceid() {
		return resourceid;
	}

	public void setResourceid(Long resourceid) {
		this.resourceid = resourceid;
	}

	public String getRequestnum() {
		return requestnum;
	}

	public void setRequestnum(String requestnum) {
		this.requestnum = requestnum;
	}

	public String getReqResnum() {
		reqResnum = requestnum==null? "":requestnum + "   " + resourceName;
		return reqResnum;
	}
	
	public void setReqResnum() {
		reqResnum = requestnum==null? "":requestnum + "   " + resourceName;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getItemCode() {
		return itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}	
}
