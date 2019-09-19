package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.Transient;


public interface ProjectionItem extends Persistable {
   
	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the id
	 */
	public Long getProjectionId();

	/**
	 * @param id the id to set
	 */
	public void setProjectionId(Long id);

	/**
	 * @return the kind
	 */
	public Kind getKind();

	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) ;

	/**
	 * @return the costName
	 */
	public String getCostName();

	/**
	 * @param costName the costName to set
	 */
	public void setCostName(String costName) ;

	/**
	 * @return the isManuallyAdded
	 */
	public StringBooleanEnum getIsManuallyAdded() ;

	/**
	 * @param isManuallyAdded the isManuallyAdded to set
	 */
	public void setIsManuallyAdded(StringBooleanEnum isManuallyAdded);
	
	/**
	 * @return the isSupportCost
	 */
	public StringBooleanEnum getIsSupportCost() ;

	/**
	 * @param isSupportCost the isSupportCost to set
	 */
	public void setIsSupportCost(StringBooleanEnum isSupportCost) ;
	
	
	/**
	 * @return the isSupportCost
	 */
	//public Boolean getSupportCost() ;

	/**
	 * @param isSupportCost the isSupportCost to set
	 */
	public void setSupportCost(Boolean supportCost) ;
	
	

	/**
	 * @return the quantity
	 */
	public Integer getQuantity();

	/**
	 * @param quantity the quantity to set
	 */
	public void setQuantity(Integer quantity);

	/**
	 * @return the averageCost
	 */
	public BigDecimal getAverageCost() ;

	/**
	 * @param averageCost the averageCost to set
	 */
	public void setAverageCost(BigDecimal averageCost);

	/**
	 * @return the numberOfPersonnel
	 */
	public Integer getNumberOfPersonnel();

	/**
	 * @param numberOfPersonnel the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Integer numberOfPersonnel);

	/**
	 * @return the projection
	 */
	public Projection getProjection();

	/**
	 * @param projection the projection to set
	 */
	public void setProjection(Projection projection);

	/**
	 * @return the projectionItemWorksheets
	 */
	public Collection<ProjectionItemWorksheet> getProjectionItemWorksheets();

	/**
	 * @param projectionItemWorksheets the projectionItemWorksheets to set
	 */
	public void setProjectionItemWorksheets(
			Collection<ProjectionItemWorksheet> projectionItemWorksheets) ;

	public Double getCostRate();
	public void setCostRate(Double costRate);

	public BigDecimal getTotalCost() ;
	public void setTotalCost(BigDecimal totalCost) ;
	
	public Long getItemId();
	public void setItemId(Long itemId);
	
	/**
	 * @return the groupItemCode
	 */
	public String getItemCodeGroup();

	/**
	 * @param groupItemCode the groupItemCode to set
	 */
	public void setItemCodeGroup(String itemCodeGroup) ;
	
	/**
	 * @return the isItemCodeActive
	 */
	public StringBooleanEnum getIsItemCodeActive() ;

	/**
	 * @param isItemCodeActive the isItemCodeActive to set
	 */
	public void setIsItemCodeActive(StringBooleanEnum isItemCodeActive) ;
	
	@Transient 
	public void addProjectionItemWorksheet(ProjectionItemWorksheet piwksht);
	@Transient 
	public void addAllProjectionItemWorksheet(Collection<ProjectionItemWorksheet> list);
		
}
