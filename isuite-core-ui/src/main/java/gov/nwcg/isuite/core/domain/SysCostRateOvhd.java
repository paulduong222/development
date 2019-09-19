package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;
import java.util.Collection;

public interface SysCostRateOvhd extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;



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
	public BigDecimal getIndirectRate() ;



	/**
	 * @param indirectRate the indirectRate to set
	 */
	public void setIndirectRate(BigDecimal indirectRate) ;



	/**
	 * @return the singleRate
	 */
	public BigDecimal getSingleRate() ;



	/**
	 * @param singleRate the singleRate to set
	 */
	public void setSingleRate(BigDecimal singleRate) ;



	/**
	 * @return the subordinateRate
	 */
	public BigDecimal getSubordinateRate();



	/**
	 * @param subordinateRate the subordinateRate to set
	 */
	public void setSubordinateRate(BigDecimal subordinateRate);



	/**
	 * @return the sysCostRate
	 */
	public SysCostRate getSysCostRate();



	/**
	 * @param sysCostRate the sysCostRate to set
	 */
	public void setSysCostRate(SysCostRate sysCostRate);


	
}
