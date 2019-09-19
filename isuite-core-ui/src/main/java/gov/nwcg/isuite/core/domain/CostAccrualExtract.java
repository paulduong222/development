package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;
import java.util.Date;

public interface CostAccrualExtract extends Persistable{

	/**
	 * @return the id
	 */
	public Long getId() ;

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) ;

	/**
	 * @return the extractDate
	 */
	public Date getExtractDate() ;

	/**
	 * @param extractDate the extractDate to set
	 */
	public void setExtractDate(Date extractDate) ;

	/**
	 * @return the finalized
	 */
	public StringBooleanEnum isFinalized() ;

	/**
	 * @param finalized the finalized to set
	 */
	public void setFinalized(StringBooleanEnum finalized) ;

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() ;

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy);

	/**
	 * @return the preparedPhone
	 */
	public String getPreparedPhone() ;

	/**
	 * @param preparedPhone the preparedPhone to set
	 */
	public void setPreparedPhone(String preparedPhone);

	/**
	 * @return the incident
	 */
	public Incident getIncident();

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) ;

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup();

	/**
	 * @param incident the incident to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) ;

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
	public Collection<CostAccrualExtractRsc> getCostAccrualExtractRscs(); 

	public void setCostAccrualExtractRscs(Collection<CostAccrualExtractRsc> costAccrualExtractRscs);

	public Date getFinalizedDate();

	public void setFinalizedDate(Date val);

	/**
	 * @param sequenceNumber the sequenceNumber to set
	 */
	public void setSequenceNumber(Short sequenceNumber);

	public Short getSequenceNumber();
	
	/**
	 * @return the finalized
	 */
	public StringBooleanEnum getFinalized();
	
	public StringBooleanEnum getIsExported();
	
	public void setIsExported(StringBooleanEnum isExported);
	
	/**
	 * @return the isFromSingleIncident
	 */
	public StringBooleanEnum getIsFromSingleIncident();

	/**
	 * @param isFromSingleIncident the isFromSingleIncident to set
	 */
	public void setIsFromSingleIncident(StringBooleanEnum isFromSingleIncident);
	
}
