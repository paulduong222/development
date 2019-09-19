package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.math.BigDecimal;
import java.util.Collection;

public interface IncidentCostRateOvhd extends Persistable {

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
	 * @return the incidentCostRate
	 */
	public IncidentCostRate getIncidentCostRate();



	/**
	 * @param incidentCostRate the incidentCostRate to set
	 */
	public void setIncidentCostRate(IncidentCostRate incidentCostRate);

	/**
	 * @return the incidentCostRateId
	 */
	public Long getIncidentCostRateId();


	/**
	 * @param incidentCostRateId the incidentCostRateId to set
	 */
	public void setIncidentCostRateId(Long incidentCostRateId);

	
}
