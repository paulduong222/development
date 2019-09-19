package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Transient;

public interface ProjectionItemWorksheet extends Persistable {
   
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);

	/**
	 * @return the projectionDate
	 */
	public Date getProjectionDate();

	/**
	 * @param projectionDate the projectionDate to set
	 */
	public void setProjectionDate(Date projectionDate);

	/**
	 * @return the quantity
	 */
	public Integer getQuantity();

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity) ;

	/**
	 * @return the averageCost
	 */
	public BigDecimal getAverageCost() ;

	/**
	 * @param averageCost the averageCost to set
	 */
	public void setAverageCost(BigDecimal averageCost) ;

	/**
	 * @return the numberOfPersonnel
	 */
	public Integer getNumberOfPersonnel() ;

	/**
	 * @param numberOfPersonnel the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Integer numberOfPersonnel);

	/**
	 * @return the projectionItem
	 */
	public ProjectionItem getProjectionItem();

	/**
	 * @param projectionItem the projectionItem to set
	 */
	public void setProjectionItem(ProjectionItem projectionItem);
	@Transient
	public void updateObject(ProjectionItem pi,Date date);
	

}
