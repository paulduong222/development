package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;

@Entity
@SequenceGenerator(name="SEQ_PROJECTION_ITEM", sequenceName="SEQ_PROJECTION_ITEM")
@Table(name="isw_projection_item")
public class ProjectionItemImpl extends PersistableImpl implements ProjectionItem {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_PROJECTION_ITEM")
	private Long id = 0L;

	@Column(name = "ITEM_ID", insertable = false, updatable = false, nullable = true)
	private Long itemId;
	
	@ManyToOne(targetEntity=KindImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ITEM_ID")
	private Kind kind;
	
	@Column(name = "COST_NAME", length = 200)
	private String costName;
	
    @Column(name="IS_MANUALLY_ADDED",nullable=false, length=10)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isManuallyAdded;
   
    @Column(name="IS_SUPPORT_COST",nullable=false, length=10)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isSupportCost;
	
	@Column(name = "QUANTITY")
	private Integer quantity;
	
	@Column(name = "AVERAGE_COST", precision = 22, scale = 0)
	private BigDecimal averageCost;
	
	@Column(name = "NUMBER_OF_PERSONNEL")
	private Integer numberOfPersonnel;
	
	@ManyToOne(targetEntity=ProjectionImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECTION_ID", nullable = false)
	private Projection projection;
	
	@Column(name="PROJECTION_ID", insertable = false, updatable = false, nullable = false, length=19)
	private Long projectionId;
	
	@OneToMany(targetEntity=ProjectionItemWorksheetImpl.class, fetch = FetchType.LAZY, mappedBy = "projectionItem")
	@Cascade({org.hibernate.annotations.CascadeType.ALL, org.hibernate.annotations.CascadeType.DELETE_ORPHAN})
	private Collection<ProjectionItemWorksheet> projectionItemWorksheets = null;

	@Transient
	private Double costRate;
	
	@Column(name="total_cost")
	private BigDecimal totalCost;
	
	@Column(name = "ITEM_CODE_GROUP", length = 10)
	private String itemCodeGroup;
	
	@Column(name = "IS_ITEM_CODE_ACTIVE", nullable=false, length=10)
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isItemCodeActive;
	
	@Transient
	private Boolean supportCost;
//	public Boolean getSupportCost() {
//		return this.supportCost;
//	}
	public void setSupportCost(Boolean supportCost) {
		    this.supportCost = supportCost;
		    if(supportCost)
		    	this.isSupportCost = StringBooleanEnum.Y;
		    else
		    	this.isSupportCost = StringBooleanEnum.N;
	}

	/**
	 * Default constructor.
	 *
	 */
	public ProjectionItemImpl() {
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

	public Long getItemId() {
		return itemId;
	}

	public void setItemId(Long itemId) {
		this.itemId = itemId;
	}

	/**
	 * @return the kind
	 */
	public Kind getKind() {
		return kind;
	}

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) {
		this.kind = kind;
	}

	/**
	 * @return the costName
	 */
	public String getCostName() {
		return costName;
	}

	/**
	 * @param costName the costName to set
	 */
	public void setCostName(String costName) {
		this.costName = costName;
	}

	/**
	 * @return the isManuallyAdded
	 */
	public StringBooleanEnum getIsManuallyAdded() {
		return isManuallyAdded;
	}

	/**
	 * @param isManuallyAdded the isManuallyAdded to set
	 */
	public void setIsManuallyAdded(StringBooleanEnum isManuallyAdded) {
		this.isManuallyAdded = isManuallyAdded;
	}

	/**
	 * @return the isSupportCost
	 */
	public StringBooleanEnum getIsSupportCost() {
		return this.isSupportCost;
	}

	/**
	 * @param isSupportCost the isSupportCost to set
	 */
	public void setIsSupportCost(StringBooleanEnum isSupportCost) {
			this.isSupportCost = isSupportCost;
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
	
	public Long getProjectionId() {
		return projectionId;
	}

	public void setProjectionId(Long projectionId) {
		this.projectionId = projectionId;
	}

	/**
	 * @return the projection
	 */
	public Projection getProjection() {
		return projection;
	}

	/**
	 * @param projection the projection to set
	 */
	public void setProjection(Projection projection) {
		this.projection = projection;
	}

	/**
	 * @return the projectionItemWorksheets
	 */
	public Collection<ProjectionItemWorksheet> getProjectionItemWorksheets() {
		if(projectionItemWorksheets == null)
			projectionItemWorksheets = new ArrayList<ProjectionItemWorksheet>();
		return projectionItemWorksheets;
	}

	/**
	 * @param projectionItemWorksheets the projectionItemWorksheets to set
	 */
	public void setProjectionItemWorksheets(
			Collection<ProjectionItemWorksheet> projectionItemWorksheets) {
		this.projectionItemWorksheets.clear();
		this.projectionItemWorksheets.addAll(projectionItemWorksheets);
	}
	
	public Double getCostRate() {
		return costRate;
	}

	public void setCostRate(Double costRate) {
		this.costRate = costRate;
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
	 * @param groupItemCode the groupItemCode to set
	 */
	public void setItemCodeGroup(String itemCodeGroup) {
		this.itemCodeGroup = itemCodeGroup;
	}
	
	public StringBooleanEnum getIsItemCodeActive() {
		return isItemCodeActive;
	}

	public void setIsItemCodeActive(StringBooleanEnum isItemCodeActive) {
		this.isItemCodeActive = isItemCodeActive;
	}

	@Transient 
	public void addProjectionItemWorksheet(ProjectionItemWorksheet piwksht) { 
		getProjectionItemWorksheets().add(piwksht); 
		piwksht.setProjectionItem(this); 
	}
	
	@Transient 
	public void addAllProjectionItemWorksheet(Collection<ProjectionItemWorksheet> list) { 
		for(ProjectionItemWorksheet piwksht:list)
			addProjectionItemWorksheet(piwksht); 
	}
	
	@Transient
	public void updateObject(ProjectionItem pi) {
		if( pi == null)
			return;
		
		this.quantity = pi.getQuantity();
		this.averageCost = pi.getAverageCost();
		this.numberOfPersonnel = pi.getNumberOfPersonnel();
		this.totalCost = pi.getTotalCost();
	}
	
	@Transient String status;
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
}
