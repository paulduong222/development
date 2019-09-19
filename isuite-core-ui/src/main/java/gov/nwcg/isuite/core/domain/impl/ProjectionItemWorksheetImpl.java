package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.ProjectionItemWorksheet;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@SequenceGenerator(name="SEQ_PROJECTION_ITEM_WKSHT", sequenceName="SEQ_PROJECTION_ITEM_WKSHT")
@Table(name="ISW_PROJECTION_ITEM_WKSHT")
public class ProjectionItemWorksheetImpl extends PersistableImpl implements ProjectionItemWorksheet {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_PROJECTION_ITEM_WKSHT")
	private Long id = 0L;

	@Column(name = "PROJECTION_DATE", nullable = false)
	private Date projectionDate;
	
	@Column(name = "QUANTITY", precision = 22, scale = 0)
	private Integer quantity;
	
	@Column(name = "AVERAGE_COST", precision = 22, scale = 0)
	private BigDecimal averageCost;
	
	@Column(name = "NUMBER_OF_PERSONNEL")
	private Integer numberOfPersonnel;

	@ManyToOne(targetEntity=ProjectionItemImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "PROJECTION_ITEM_ID", nullable = false)
	private ProjectionItem projectionItem;

	/**
	 * Default constructor.
	 *
	 */
	public ProjectionItemWorksheetImpl() {
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
	 * @return the projectionItem
	 */
	public ProjectionItem getProjectionItem() {
		return projectionItem;
	}

	/**
	 * @param projectionItem the projectionItem to set
	 */
	public void setProjectionItem(ProjectionItem projectionItem) {
		this.projectionItem = projectionItem;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.ProjectionItemWorksheet#updateObject(gov.nwcg.isuite.core.domain.ProjectionItem)
	 * the updating is for refresh projection worksheet action. for projection item work sheet the updating is only changed
	 * average cost. for supporting cost work sheet only change the number of personal. 
	 */
	@Transient
	public void updateObject(ProjectionItem pi, Date date) {
		if( pi == null)
			return;
		
		this.projectionDate = date;
		
		if(pi.getIsItemCodeActive().equals(StringBooleanEnum.N)) //deleted projection items
			this.averageCost = BigDecimal.ZERO;
		else if(!pi.getIsSupportCost().getValue()) {//projection work sheet updating
			this.averageCost = pi.getAverageCost();
		}
//		else if(pi.getIsSupportCost().getValue()) //supporting work sheet updating
//			this.numberOfPersonnel = this.numberOfPersonnel + personnel;
	}
}
