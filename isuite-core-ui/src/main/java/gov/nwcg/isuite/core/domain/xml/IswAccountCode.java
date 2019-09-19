package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswAccountCode", table = "isw_account_code")
public class IswAccountCode {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_ACCOUNT_CODE", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;

	@XmlTransferField(name = "AccountCode", sqlname = "ACCOUNT_CODE", type="STRING")
	private String accountCode;

	@XmlTransferField(name = "AgencyTransferableIdentity", alias="agti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="AgencyId"
						, disjoined=true, disjoinedtable="iswl_agency", disjoinedfield="transferable_identity",disjoinedsource="AGENCY_ID")
	private String agencyTransferableIdentity;

	@XmlTransferField(name = "AgencyId", sqlname="AGENCY_ID", type="LONG"
						,derived=true, derivedfield="AgencyTransferableIdentity")
	private Long agencyId;

	@XmlTransferField(name = "RegionCodeTransferableIdentity", alias="rgcti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="RegionCodeUnitId"
						, disjoined=true, disjoinedtable="iswl_region_code", disjoinedfield="transferable_identity",disjoinedsource="REGION_UNIT_ID")
	private String regionCodeTransferableIdentity;

	@XmlTransferField(name = "RegionCodeUnitId", sqlname="REGION_UNIT_ID", type="LONG"
						,derived=true, derivedfield="RegionCodeTransferableIdentity")
	private Long regionCodeUnitId;

	/**
	 * Default constructor.
	 * 
	 */
	public IswAccountCode() {
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
	 * @return the accountCode
	 */
	public String getAccountCode() {
		return accountCode;
	}

	/**
	 * @param accountCode the accountCode to set
	 */
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}

	/**
	 * @return the agencyTransferableIdentity
	 */
	public String getAgencyTransferableIdentity() {
		return agencyTransferableIdentity;
	}

	/**
	 * @param agencyTransferableIdentity the agencyTransferableIdentity to set
	 */
	public void setAgencyTransferableIdentity(String agencyTransferableIdentity) {
		this.agencyTransferableIdentity = agencyTransferableIdentity;
	}

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the regionCodeTransferableIdentity
	 */
	public String getRegionCodeTransferableIdentity() {
		return regionCodeTransferableIdentity;
	}

	/**
	 * @param regionCodeTransferableIdentity the regionCodeTransferableIdentity to set
	 */
	public void setRegionCodeTransferableIdentity(
			String regionCodeTransferableIdentity) {
		this.regionCodeTransferableIdentity = regionCodeTransferableIdentity;
	}

	/**
	 * @return the regionCodeUnitId
	 */
	public Long getRegionCodeUnitId() {
		return regionCodeUnitId;
	}

	/**
	 * @param regionCodeUnitId the regionCodeUnitId to set
	 */
	public void setRegionCodeUnitId(Long regionCodeUnitId) {
		this.regionCodeUnitId = regionCodeUnitId;
	}


}
