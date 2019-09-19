package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswIapForm205", table="isw_iap_form_205")
public class IswIapForm205 {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_IAP_FORM_205", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "IapPlanId", sqlname="IAP_PLAN_ID", type="LONG")
	private Long iapPlanId;

	@XmlTransferField(name = "SpecialInstruction", sqlname="SPECIAL_INSTRUCTION", type="STRING", ischardata=true)
	private String specialInstruction;
	
	@XmlTransferField(name = "PreparedBy", sqlname="PREPARED_BY", type="STRING")
	private String preparedBy;

	@XmlTransferField(name = "PreparedByPosition", sqlname="PREPARED_BY_POS", type="STRING")
	private String preparedByPosition;
	
	@XmlTransferField(name = "PreparedDate", sqlname="PREPARED_DATE", type="DATE")
	private Date preparedDate;

	@XmlTransferField(name = "IsFormLocked", sqlname="IS_FORM_LOCKED", type="STRING")
	private String isFormLocked;
	
	@XmlTransferField(name = "IapFrequency", type="COMPLEX", target=IswIapFrequency.class
			, lookupname="IapForm205Id", sourcename="Id"
			, cascade=true)
	private Collection<IswIapFrequency> iapFrequencys = new ArrayList<IswIapFrequency>();

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
	 * @return the specialInstruction
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * @param specialInstruction the specialInstruction to set
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
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
	 * @return the iapFrequencys
	 */
	public Collection<IswIapFrequency> getIapFrequencys() {
		return iapFrequencys;
	}

	/**
	 * @param iapFrequencys the iapFrequencys to set
	 */
	public void setIapFrequencys(Collection<IswIapFrequency> iapFrequencys) {
		this.iapFrequencys = iapFrequencys;
	}

}
