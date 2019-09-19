package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface SysCostRate extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId();

	/**
	 * @param id the id to set
	 */
	public void setId(Long id); 



	/**
	 * @return the costRateCategory
	 */
	public String getCostRateCategory();



	/**
	 * @param costRateCategory the costRateCategory to set
	 */
	public void setCostRateCategory(String costRateCategory) ;



	/**
	 * @return the sysCostRateStates
	 */
	public Collection<SysCostRateState> getSysCostRateStates();



	/**
	 * @param sysCostRateStates the sysCostRateStates to set
	 */
	public void setSysCostRateStates(Collection<SysCostRateState> sysCostRateStates) ;



	/**
	 * @return the sysCostRateKinds
	 */
	public Collection<SysCostRateKind> getSysCostRateKinds();



	/**
	 * @param sysCostRateKinds the sysCostRateKinds to set
	 */
	public void setSysCostRateKinds(Collection<SysCostRateKind> sysCostRateKinds);



	/**
	 * @return the sysCostRateOvhds
	 */
	public Collection<SysCostRateOvhd> getSysCostRateOvhds();



	/**
	 * @param sysCostRateOvhds the sysCostRateOvhds to set
	 */
	public void setSysCostRateOvhds(Collection<SysCostRateOvhd> sysCostRateOvhds);

	
}
