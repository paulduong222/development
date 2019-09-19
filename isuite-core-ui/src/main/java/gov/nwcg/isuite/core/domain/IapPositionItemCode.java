package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.IapSectionEnum;

public interface IapPositionItemCode extends Persistable {
	
	/**
	 * @return the id
	 */
	public Long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the incident
	 */
	public Incident getIncident();
	
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @return the position
	 */
	public String getPosition();

	/**
	 * @param position
	 */
	public void setPosition(String position);
	
	/**
	 * @return the kind
	 */
	public Kind getKind();

	/**
	 * @param kind
	 */
	public void setKind(Kind kind);
	
	/**
	 * @param kindId the kindId to set
	 */
	public void setKindId(Long kindId);

	/**
	 * @return the kindId
	 */
	public Long getKindId();

	/**
	 * @return the agency
	 */
	public Agency getAgency();

	/**
	 * @param agency
	 */
	public void setAgency(Agency agency);
	
	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId);

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId();
	
	/**
	 * @return the form
	 */
	public String getForm();

	/**
	 * @param form
	 */
	public void setForm(String form);

	/**
	 * @return the section
	 */
	public IapSectionEnum getSection();

	/**
	 * @param section
	 */
	public void setSection(IapSectionEnum section);

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup();

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup);

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId();

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId);
	
}
