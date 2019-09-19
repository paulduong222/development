package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;

public interface SysCostRateKind extends Persistable {

	/**
	 * @return the id
	 */
	public Long getId() ;



	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;


	/**
	 * @return the kind
	 */
	public Kind getKind() ;



	/**
	 * @param kind the kind to set
	 */
	public void setKind(Kind kind) ;



	/**
	 * @return the kindId
	 */
	public Long getKindId();



	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId);



	/**
	 * @return the rateType
	 */
	public String getRateType();



	/**
	 * @param rateType the rateType to set
	 */
	public void setRateType(String rateType);


	/**
	 * @return the rateAmount
	 */
	public BigDecimal getRateAmount() ;


	/**
	 * @param rateAmount the rateAmount to set
	 */
	public void setRateAmount(BigDecimal rateAmount);



	/**
	 * @return the sysCostRate
	 */
	public SysCostRate getSysCostRate();



	/**
	 * @param sysCostRate the sysCostRate to set
	 */
	public void setSysCostRate(SysCostRate sysCostRate) ;

	
}
