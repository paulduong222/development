package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.Date;

@XmlTransferTable(name = "IswWorkPeriodOvernightStayInfo", table="isw_wp_overnight_stay_info")
public class IswWorkPeriodOvernightStayInfo {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_WP_OVERNIGHT_STAY_INFO", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "WorkPeriodId", sqlname="WORK_PERIOD_ID", type="LONG", updateable=false)
	private Long workPeriodId;
	
	@XmlTransferField(name = "CsTransferableIdentity", alias="csti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="StateId"
						, disjoined=true, disjoinedtable="iswl_country_subdivision", disjoinedfield="transferable_identity",disjoinedsource="STATE_ID")
	private String csTransferableIdentity;
	
	@XmlTransferField(name = "StateId", sqlname="STATE_ID", type="LONG"
						,derived=true, derivedfield="CsTransferableIdentity")
	private Long stateId;
	
    @XmlTransferField(name = "City", sqlname = "CITY", type="STRING")
	private String city;
	
    @XmlTransferField(name = "EstimatedArrivalDate", sqlname="ESTIMATED_ARRIVAL_DATE", type="DATE")
	private Date estimatedArrivalDate;
	
    @XmlTransferField(name = "LengthOfStay", sqlname = "LENGTH_OF_STAY", type="LONG")
	private Long lengthOfStay;
	
    @XmlTransferField(name = "Remarks", sqlname = "REMARKS", type="STRING")
	private String remarks;

	public IswWorkPeriodOvernightStayInfo() {
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the workPeriodId
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	/**
	 * @param workPeriodId the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	/**
	 * @return the csTransferableIdentity
	 */
	public String getCsTransferableIdentity() {
		return csTransferableIdentity;
	}

	/**
	 * @param csTransferableIdentity the csTransferableIdentity to set
	 */
	public void setCsTransferableIdentity(String csTransferableIdentity) {
		this.csTransferableIdentity = csTransferableIdentity;
	}

	/**
	 * @return the stateId
	 */
	public Long getStateId() {
		return stateId;
	}

	/**
	 * @param stateId the stateId to set
	 */
	public void setStateId(Long stateId) {
		this.stateId = stateId;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the estimatedArrivalDate
	 */
	public Date getEstimatedArrivalDate() {
		return estimatedArrivalDate;
	}

	/**
	 * @param estimatedArrivalDate the estimatedArrivalDate to set
	 */
	public void setEstimatedArrivalDate(Date estimatedArrivalDate) {
		this.estimatedArrivalDate = estimatedArrivalDate;
	}

	/**
	 * @return the lengthOfStay
	 */
	public Long getLengthOfStay() {
		return lengthOfStay;
	}

	/**
	 * @param lengthOfStay the lengthOfStay to set
	 */
	public void setLengthOfStay(Long lengthOfStay) {
		this.lengthOfStay = lengthOfStay;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}


}
