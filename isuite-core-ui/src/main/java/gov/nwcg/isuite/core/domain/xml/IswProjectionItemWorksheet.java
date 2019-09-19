package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;
import java.util.Date;

@XmlTransferTable(name = "IswProjectionItemWorksheet", table = "isw_projection_item_wksht")
public class IswProjectionItemWorksheet {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_PROJECTION_ITEM_WKSHT", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "ProjectionDate", sqlname = "PROJECTION_DATE", type="DATE")
	private Date projectionDate;
	
	@XmlTransferField(name = "Quantity", sqlname = "QUANTITY", type="INTEGER")
	private Integer quantity;
	
	@XmlTransferField(name = "AverageCost", sqlname = "AVERAGE_COST", type="BIGDECIMAL")
	private BigDecimal averageCost;
	
	@XmlTransferField(name = "NumberOfPersonnel", sqlname = "NUMBER_OF_PERSONNEL", type="INTEGER")
	private Integer numberOfPersonnel;

	@XmlTransferField(name = "ProjectionItemTransferableIdentity", alias="projitemti", type="STRING"
		, lookupname="TransferableIdentity", sourcename="ProjectionItemId"
		, disjoined=true, disjoinedtable="isw_projection_item", disjoinedfield="transferable_identity",disjoinedsource="PROJECTION_ITEM_ID")
     private String projectionItemTransferableIdentity;

	@XmlTransferField(name = "ProjectionItemId", sqlname="PROJECTION_ITEM_ID", type="LONG"
		,derived=true, derivedfield="ProjectionItemTransferableIdentity")
	private Long projectionItemId;

	/**
	 * Default constructor.
	 *
	 */
	public IswProjectionItemWorksheet() {
		super();
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
	 * @return the projectionDate
	 */
	public Date getProjectionDate() {
		return projectionDate;
	}

	/**
	 * @param projectionDate the projectionDate to set
	 */
	public void setProjectionDate(Date projectionDate) {
		this.projectionDate = projectionDate;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	/**
	 * @return the averageCost
	 */
	public BigDecimal getAverageCost() {
		return averageCost;
	}

	/**
	 * @param averageCost the averageCost to set
	 */
	public void setAverageCost(BigDecimal averageCost) {
		this.averageCost = averageCost;
	}

	/**
	 * @return the numberOfPersonnel
	 */
	public Integer getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	/**
	 * @param numberOfPersonnel the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Integer numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * @param projectionItemId the projectionItemId to set
	 */
	public void setProjectionItemId(Long projectionItemId) {
		this.projectionItemId = projectionItemId;
	}

	/**
	 * @return the projectionItemId
	 */
	public Long getProjectionItemId() {
		return projectionItemId;
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
	 * @return the projectionItemTransferableIdentity
	 */
	public String getProjectionItemTransferableIdentity() {
		return projectionItemTransferableIdentity;
	}

	/**
	 * @param projectionItemTransferableIdentity the projectionItemTransferableIdentity to set
	 */
	public void setProjectionItemTransferableIdentity(
			String projectionItemTransferableIdentity) {
		this.projectionItemTransferableIdentity = projectionItemTransferableIdentity;
	}

}
