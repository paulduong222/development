package gov.nwcg.isuite.core.domain;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface ResourceOther extends Persistable{

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
	 * @return the incidentAccountCode
	 */
	public IncidentAccountCode getIncidentAccountCode() ;

	/**
	 * @param incidentAccountCode the incidentAccountCode to set
	 */
	public void setIncidentAccountCode(IncidentAccountCode incidentAccountCode) ;

	/**
	 * @return the agency
	 */
	public Agency getAgency() ;

	/**
	 * @param agency the agency to set
	 */
	public void setAgency(Agency agency) ;

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() ;

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) ;

	/**
	 * @return the actualReleaseDate
	 */
	public Date getActualReleaseDate() ;

	/**
	 * @param actualReleaseDate the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) ;

	/**
	 * @return the costGroups
	 */
	public Collection<CostGroup> getCostGroups() ;

	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<CostGroup> costGroups) ;

	/**
	 * @return the incidentResourceOthers
	 */
	public Collection<IncidentResourceOther> getIncidentResourceOthers();

	/**
	 * @param incidentResourceOthers the incidentResourceOthers to set
	 */
	public void setIncidentResourceOthers(
			Collection<IncidentResourceOther> incidentResourceOthers);
	
	/**
	 * @return the costDescription
	 */
	public String getCostDescription();
	
	/**
	 * @param costDescription the costDescription to set
	 */
	public void setCostDescription(String costDescription);

	/**
	 * @return the incidentAccountCodeId
	 */
	public Long getIncidentAccountCodeId() ;


	/**
	 * @param incidentAccountCodeId the incidentAccountCodeId to set
	 */
	public void setIncidentAccountCodeId(Long incidentAccountCodeId);

	/**
	 * @return the kindId
	 */
	public Long getKindId() ;


	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId) ;
	
}
