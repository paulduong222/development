package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswResourceKind", table = "isw_resource_kind")
public class IswResourceKind {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_RESOURCE_KIND", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "ResourceId", sqlname="RESOURCE_ID", type="LONG", updateable=false)
	private Long resourceId;

	@XmlTransferField(name = "KindTransferableIdentity", alias="kindti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="KindId"
		, disjoined=true, disjoinedtable="iswl_kind", disjoinedfield="transferable_identity",disjoinedsource="KIND_ID")
	private String kindTransferableIdentity;

	@XmlTransferField(name = "KindId", sqlname="KIND_ID", type="LONG"
				,derived=true, derivedfield="KindTransferableIdentity")
	private Long kindId;

	@XmlTransferField(name = "Training", sqlname = "IS_TRAINING", type="BOOLEAN")
	private Boolean training = false;

	@XmlTransferField(name = "Primary", sqlname = "IS_PRIMARY", type="BOOLEAN")
	private Boolean primary = false;

	public IswResourceKind() {

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
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the kindTransferableIdentity
	 */
	public String getKindTransferableIdentity() {
		return kindTransferableIdentity;
	}

	/**
	 * @param kindTransferableIdentity the kindTransferableIdentity to set
	 */
	public void setKindTransferableIdentity(String kindTransferableIdentity) {
		this.kindTransferableIdentity = kindTransferableIdentity;
	}

	/**
	 * @return the kindId
	 */
	public Long getKindId() {
		return kindId;
	}

	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) {
		this.kindId = kindId;
	}

	/**
	 * @return the training
	 */
	public Boolean getTraining() {
		return training;
	}

	/**
	 * @param training the training to set
	 */
	public void setTraining(Boolean training) {
		this.training = training;
	}

	/**
	 * @return the primary
	 */
	public Boolean getPrimary() {
		return primary;
	}

	/**
	 * @param primary the primary to set
	 */
	public void setPrimary(Boolean primary) {
		this.primary = primary;
	}

	
}
