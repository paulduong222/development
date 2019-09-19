package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;
import java.util.Collection;

public interface SysCostRateState extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the agency
	 */
	public Agency getAgency() ;

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(Agency agency) ;

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() ;

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId);
	
	/**
	 * @return the directRate
	 */
	public BigDecimal getDirectRate() ;

	/**
	 * @param directRate the directRate to set
	 */
	public void setDirectRate(BigDecimal directRate) ;

	/**
	 * @return the indirectRate
	 */
	public BigDecimal getIndirectRate();
	
	/**
	 * @param indirectRate the indirectRate to set
	 */
	public void setIndirectRate(BigDecimal indirectRate) ;

	/**
	 * @return the singleRate
	 */
	public BigDecimal getSingleRate();

	/**
	 * @param singleRate the singleRate to set
	 */
	public void setSingleRate(BigDecimal singleRate) ;

	/**
	 * @return the subordinateRate
	 */
	public BigDecimal getSubordinateRate() ;
	
	/**
	 * @param subordinateRate the subordinateRate to set
	 */
	public void setSubordinateRate(BigDecimal subordinateRate) ;
	
	/**
	 * @return the sysCostRate
	 */
	public SysCostRate getSysCostRate() ;
	/**
	 * @param sysCostRate the sysCostRate to set
	 */
	public void setSysCostRate(SysCostRate sysCostRate);

	/**
	 * @return the sysCostStateKinds
	 */
	public Collection<SysCostRateStateKind> getSysCostStateKinds() ;
	/**
	 * @param sysCostStateKinds the sysCostStateKinds to set
	 */
	public void setSysCostStateKinds(
			Collection<SysCostRateStateKind> sysCostStateKinds) ;


}
