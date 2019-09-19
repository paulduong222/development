package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentPrefsOtherFields extends Persistable{

	public void setIncident(Incident entity);
	
	public Incident getIncident();
	
	public void setIncidentId(Long id);
	
	public Long getIncidentId();
	
	public String getOther1Label();

	public void setOther1Label(String other1Label) ;

	public String getOther2Label() ;

	public void setOther2Label(String other2Label);

	public String getOther3Label();

	public void setOther3Label(String other3Label) ;

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() ;

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) ;

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() ;

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
}
