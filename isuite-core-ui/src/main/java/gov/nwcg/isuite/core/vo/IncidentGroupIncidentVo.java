package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.PersistableVo;

/**
 * A Persistable VO representative of an Incident within an Incident Group.
 * 
 * @author bsteiner
 */
public interface IncidentGroupIncidentVo extends PersistableVo {
	
	/**
	 * 
	 * @return
	 */
	public String getIncidentName();
	
	/**
	 * 
	 * @param name
	 */
	public void setIncidentName(String name);
	
	/**
	 * 
	 * @return
	 */
	public String getEventType();
	
	/**
	 * 
	 * @param eventType
	 */
	public void setEventType(String eventType);
	
	/**
	 * 
	 * @return
	 */
	public String getIncidentNumber();
	
	/**
	 * 
	 * @param incidentNumber
	 */
	public void setIncidentNumber(String incidentNumber);
}
