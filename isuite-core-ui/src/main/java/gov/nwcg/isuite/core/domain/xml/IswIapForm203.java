package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIapForm203", table="isw_iap_form_203")
public class IswIapForm203 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_203", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IapPlanTransferableIdentity", alias="planti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="IapPlanId"
		, disjoined=true, disjoinedtable="isw_iap_plan", disjoinedfield="transferable_identity",disjoinedsource="iap_plan_id")
	private String iapPlanTransferableIdentity;

	@XmlTransferField(name = "IapPlanId", sqlname="IAP_PLAN_ID", type="LONG"
		,derived=true,derivedfield="IapPlanTransferableIdentity")
	private Long iapPlanId;

	@XmlTransferField(name = "PreparedBy", sqlname="PREPARED_BY", type="STRING")
	private String preparedBy;

	@XmlTransferField(name = "PreparedByPosition", sqlname="PREPARED_BY_POS", type="STRING")
	private String preparedByPosition;

	@XmlTransferField(name = "PreparedDate", sqlname="PREPARED_DATE", type="DATE")
	private Date preparedDate;
	
	@XmlTransferField(name = "IsFormLocked", sqlname="IS_FORM_LOCKED", type="STRING")
	private String isFormLocked;

	@XmlTransferField(name = "IsNoBranch", sqlname="IS_NOBRANCH", type="STRING")
	private String isNoBranch;
	
	@XmlTransferField(name = "IapPersonnel", type="COMPLEX", target=IswIapPersonnel.class
			, lookupname="IapForm203Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapPersonnel> iapPersonnels = new ArrayList<IswIapPersonnel>();

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
	 * @return the iapPlanTransferableIdentity
	 */
	public String getIapPlanTransferableIdentity() {
		return iapPlanTransferableIdentity;
	}

	/**
	 * @param iapPlanTransferableIdentity the iapPlanTransferableIdentity to set
	 */
	public void setIapPlanTransferableIdentity(String iapPlanTransferableIdentity) {
		this.iapPlanTransferableIdentity = iapPlanTransferableIdentity;
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	/**
	 * @return the isFormLocked
	 */
	public String getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(String isFormLocked) {
		this.isFormLocked = isFormLocked;
	}

	/**
	 * @return the isNoBranch
	 */
	public String getIsNoBranch() {
		return isNoBranch;
	}

	/**
	 * @param isNoBranch the isNoBranch to set
	 */
	public void setIsNoBranch(String isNoBranch) {
		this.isNoBranch = isNoBranch;
	}

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IswIapPersonnel> getIapPersonnels() {
		return iapPersonnels;
	}

	/**
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IswIapPersonnel> iapPersonnels) {
		this.iapPersonnels = iapPersonnels;
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

	
}
