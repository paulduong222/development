package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "IswProjectionItem", table = "isw_projection_item")
public class IswProjectionItem {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_PROJECTION_ITEM", type="LONG")
	private Long id = 0L;
	
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "Kind", type="COMPLEX", target=IswlKind.class
				, lookupname="Id", sourcename="ItemId")
	private IswlKind kind;
	
	@XmlTransferField(name = "ItemId", sqlname="ITEM_ID", type="LONG"
						,derived=true, derivedfield="Kind")
	private Long itemId;

	@XmlTransferField(name = "CostName", sqlname = "COST_NAME", type="STRING")
	private String costName;

	@XmlTransferField(name = "IsManuallyAdded", sqlname = "IS_MANUALLY_ADDED", type="STRING")
	private String isManuallyAdded;

	@XmlTransferField(name = "IsSupportCost", sqlname = "IS_SUPPORT_COST", type="STRING")
	private String isSupportCost;

	@XmlTransferField(name = "Quantity", sqlname = "QUANTITY", type="INTEGER")
	private Integer quantity;

	@XmlTransferField(name = "AverageCost", sqlname = "AVERAGE_COST", type="BIGDECIMAL")
	private BigDecimal averageCost;

	@XmlTransferField(name = "NumberOfPersonnel", sqlname = "NUMBER_OF_PERSONNEL", type="INTEGER")
	private Integer numberOfPersonnel;

	@XmlTransferField(name = "ProjectionId", sqlname="PROJECTION_ID", type="LONG")
	private Long projectionId;

	@XmlTransferField(name = "TotalCost", sqlname = "total_cost", type="BIGDECIMAL")
	private BigDecimal totalCost;

	@XmlTransferField(name = "ItemCodeGroup", sqlname = "ITEM_CODE_GROUP", type="STRING")
	private String itemCodeGroup;

	@XmlTransferField(name = "IsItemCodeActive", sqlname = "IS_ITEM_CODE_ACTIVE", type="STRING")
	private String isItemCodeActive;

	@XmlTransferField(name = "ProjectionItemWorksheet", type="COMPLEX", target=IswProjectionItemWorksheet.class
			,lookupname="ProjectionItemId", sourcename="Id"
			,cascade=true)
	private Collection<IswProjectionItemWorksheet> projectionItemWorksheets=new ArrayList<IswProjectionItemWorksheet>();
	

	/**
	 * Default constructor.
	 * 
	 */
	public IswProjectionItem() {
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the kind
	 */
	public IswlKind getKind() {
		return kind;
	}

	/**
	 * @param kind
	 *            the kind to set
	 */
	public void setKind(IswlKind kind) {
		this.kind = kind;
	}

	/**
	 * @return the costName
	 */
	public String getCostName() {
		return costName;
	}

	/**
	 * @param costName
	 *            the costName to set
	 */
	public void setCostName(String costName) {
		this.costName = costName;
	}

	/**
	 * @return the isManuallyAdded
	 */
	public String getIsManuallyAdded() {
		return isManuallyAdded;
	}

	/**
	 * @param isManuallyAdded
	 *            the isManuallyAdded to set
	 */
	public void setIsManuallyAdded(String isManuallyAdded) {
		this.isManuallyAdded = isManuallyAdded;
	}

	/**
	 * @return the isSupportCost
	 */
	public String getIsSupportCost() {
		return this.isSupportCost;
	}

	/**
	 * @param isSupportCost
	 *            the isSupportCost to set
	 */
	public void setIsSupportCost(String isSupportCost) {
		this.isSupportCost = isSupportCost;
	}

	/**
	 * @return the quantity
	 */
	public Integer getQuantity() {
		return quantity;
	}

	/**
	 * @param quantity
	 *            the quantity to set
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
	 * @param averageCost
	 *            the averageCost to set
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
	 * @param numberOfPersonnel
	 *            the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Integer numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	public Long getProjectionId() {
		return projectionId;
	}

	public void setProjectionId(Long projectionId) {
		this.projectionId = projectionId;
	}

	public BigDecimal getTotalCost() {
		return totalCost;
	}

	public void setTotalCost(BigDecimal totalCost) {
		this.totalCost = totalCost;
	}

	/**
	 * @return the groupItemCode
	 */
	public String getItemCodeGroup() {
		return itemCodeGroup;
	}

	/**
	 * @param groupItemCode
	 *            the groupItemCode to set
	 */
	public void setItemCodeGroup(String itemCodeGroup) {
		this.itemCodeGroup = itemCodeGroup;
	}

	public String getIsItemCodeActive() {
		return isItemCodeActive;
	}

	public void setIsItemCodeActive(String isItemCodeActive) {
		this.isItemCodeActive = isItemCodeActive;
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
	 * @return the projectionItemWorksheets
	 */
	public Collection<IswProjectionItemWorksheet> getProjectionItemWorksheets() {
		return projectionItemWorksheets;
	}

	/**
	 * @param projectionItemWorksheets the projectionItemWorksheets to set
	 */
	public void setProjectionItemWorksheets(
			Collection<IswProjectionItemWorksheet> projectionItemWorksheets) {
		this.projectionItemWorksheets = projectionItemWorksheets;
	}


}
