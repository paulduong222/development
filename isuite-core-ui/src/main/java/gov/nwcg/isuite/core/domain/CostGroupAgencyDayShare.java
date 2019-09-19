package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface CostGroupAgencyDayShare extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the costGroup
	 */
	public CostGroup getCostGroup() ;

	/**
	 * @param costGroup the costGroup to set
	 */
	public void setCostGroup(CostGroup costGroup) ;

	/**
	 * @return the agencyShareDate
	 */
	public Date getAgencyShareDate() ;

	/**
	 * @param agencyShareDate the agencyShareDate to set
	 */
	public void setAgencyShareDate(Date agencyShareDate) ;

	/**
	 * @return the costGroupAgencyDaySharePercentages
	 */
	public Collection<CostGroupAgencyDaySharePercentage> getCostGroupAgencyDaySharePercentages() ;

	/**
	 * @param costGroupAgencyDaySharePercentages the costGroupAgencyDaySharePercentages to set
	 */
	public void setCostGroupAgencyDaySharePercentages(Collection<CostGroupAgencyDaySharePercentage> costGroupAgencyDaySharePercentages) ;	

	/**
	 * @return the deletedDate
	 */
	public Date getDeletedDate();

	/**
	 * @param deletedDate the deletedDate to set
	 */
	public void setDeletedDate(Date deletedDate);

	/**
	 * @return the costGroupId
	 */
	public Long getCostGroupId() ;


	/**
	 * @param costGroupId the costGroupId to set
	 */
	public void setCostGroupId(Long costGroupId);	
}
