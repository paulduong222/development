package gov.nwcg.isuite.core.domain;

import java.util.Collection;

import gov.nwcg.isuite.framework.core.domain.Persistable;

public interface IncidentShift extends Persistable {
	/**
	 * @return the id
	 */
	public Long getId();
	
	/**
	 * @param id the id to set
	 */
	public void setId(Long id);
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId();
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId);
	
	/**
	 * @return the incident
	 */
	public Incident getIncident();
	
	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident);
	
	/**
	 * @return shiftName
	 */
	public String getShiftName();
	
	/**
	 * @param shiftName the shiftName to set
	 */
	public void setShiftName(String shiftName);
	
	/**
	 * @return the startTime
	 */
	public String getStartTime();
	
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime);
	
	/**
	 * @return the endTime
	 */
	public String getEndTime();
	
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime);
	
	/**
	 * @return the fontId
	 */
	public Long getFontId();
	
	/**
	 * @param fontId the fontId to set
	 */
	public void setFontId(Long fontId);
	
	/**
	 * @return the font
	 */
	public Font getFont();
	
	/**
	 * @param font the font to set
	 */
	public void setFont(Font font);
	
	/**
	 * @param costGroups the costGroups to set
	 */
	public void setCostGroups(Collection<CostGroup> costGroups);

	/**
	 * @return the costGroups
	 */
	public Collection<CostGroup> getCostGroups();
	
	/**
	 * @return the costData
	 */
	public Collection<CostData> getCostDatas() ;

	/**
	 * @param costData the costData to set
	 */
	public void setCostDatas(Collection<CostData> costDatas) ;
		
}