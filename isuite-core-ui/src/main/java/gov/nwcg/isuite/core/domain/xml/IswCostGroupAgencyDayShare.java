package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswCostGroupAgencyDayShare", table = "isw_cost_group_ag_ds")
public class IswCostGroupAgencyDayShare {

	@XmlTransferField(name = "Id", sqlname = "ID", primarykey = true, sequence = "SEQ_COST_GROUP_AG_DS", type = "LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "CostGroupId", sqlname = "COST_GROUP_ID", type = "LONG")
	private Long costGroupId;

	@XmlTransferField(name = "AgencyShareDate", sqlname = "AGENCY_SHARE_DATE", type = "DATE")
	private Date agencyShareDate;

	@XmlTransferField(name = "DeletedDate", sqlname = "DELETED_DATE", type = "DATE")
	private Date deletedDate;

	@XmlTransferField(name = "CostGroupAgencyDaySharePercentage", type = "COMPLEX", target=IswCostGroupAgencyDaySharePercentage.class
			,lookupname="CostGroupAgencyDayShareId", sourcename="Id"
			,cascade=true)
	private Collection<IswCostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages = new ArrayList<IswCostGroupAgencyDaySharePercentage>();
	
	public IswCostGroupAgencyDayShare() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the agencyShareDate
	 */
	public Date getAgencyShareDate() {
		return agencyShareDate;
	}

	/**
	 * @param agencyShareDate
	 *            the agencyShareDate to set
	 */
	public void setAgencyShareDate(Date agencyShareDate) {
		this.agencyShareDate = agencyShareDate;
	}

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate() {
		return deletedDate;
	}

	/**
	 * @param deletedDate
	 *            the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * @return the costGroupId
	 */
	public Long getCostGroupId() {
		return costGroupId;
	}

	/**
	 * @param costGroupId
	 *            the costGroupId to set
	 */
	public void setCostGroupId(Long costGroupId) {
		this.costGroupId = costGroupId;
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
	 * @return the costGroupAgencyDaySharePercentages
	 */
	public Collection<IswCostGroupAgencyDaySharePercentage> getCostGroupAgencyDaySharePercentages() {
		return costGroupAgencyDaySharePercentages;
	}

	/**
	 * @param costGroupAgencyDaySharePercentages the costGroupAgencyDaySharePercentages to set
	 */
	public void setCostGroupAgencyDaySharePercentages(
			Collection<IswCostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages) {
		this.costGroupAgencyDaySharePercentages = costGroupAgencyDaySharePercentages;
	}

}
