package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlGroupCategory", table="iswl_group_category")
public class IswlGroupCategory {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_GROUP_CATEGORY", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Code", sqlname="CODE", type="STRING")
	private String code;

	@XmlTransferField(name = "Description", sqlname="DESCRIPTION", type="STRING")
	private String description;

	@XmlTransferField(name = "Standard", sqlname="IS_STANDARD", type="BOOLEAN")
	private Boolean standard;

	@XmlTransferField(name = "Active", sqlname = "IS_ACTIVE", type="STRING")
	private String active;

	public IswlGroupCategory() {
	}

	/**
	 * @return
	 */
	public Long getId() {
		return this.id;
	}

	/**
	 * @param id
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return
	 */
	public String getCode() {
		return this.code;
	}

	/**
	 * @return
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * @return
	 */
	public Boolean isStandard() {
		return this.standard;
	}

	/**
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @param description
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param isStandard
	 */
	public void setStandard(Boolean isStandard) {
		this.standard = isStandard;
	}

	/**
	 * @param active
	 *            the active to set
	 */
	public void setActive(String active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public String isActive() {
		return active;
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
	 * @return the standard
	 */
	public Boolean getStandard() {
		return standard;
	}

	/**
	 * @return the active
	 */
	public String getActive() {
		return active;
	}

}
