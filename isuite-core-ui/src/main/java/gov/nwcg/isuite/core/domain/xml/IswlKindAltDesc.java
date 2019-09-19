package gov.nwcg.isuite.core.domain.xml;


import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlKindAltDesc", table="iswl_kind_alt_desc")
public class IswlKindAltDesc {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_KIND_ALT_DESC", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IswlKind", type="COMPLEX", target=IswlKind.class
			, lookupname="Id", sourcename="KindId")
	private IswlKind iswlKind;

	@XmlTransferField(name = "KindId", sqlname = "KIND_ID", type="LONG"
						,derived=true,derivedfield="IswlKind")
	private Long kindId;
	
	@XmlTransferField(name = "Description", sqlname = "DESCRIPTION", type="STRING")
	private String description;
	
	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @return the iswlKind
	 */
	public IswlKind getIswlKind() {
		return iswlKind;
	}

	/**
	 * @param iswlKind the iswlKind to set
	 */
	public void setIswlKind(IswlKind iswlKind) {
		this.iswlKind = iswlKind;
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
