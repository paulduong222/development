package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;

public class WorkAreaIncidentVo extends AbstractVo {
	private String name;
	private String description;
	
	public WorkAreaIncidentVo(){
		super();
	}

	public WorkAreaIncidentVo(Incident incident){
		if(null!=incident){
			setId(incident.getId());
			setName(incident.getIncidentName());
			setDescription(incident.getIncidentDescription());
		}
	}
	
	public static WorkAreaIncidentVo getInstance(Incident entity, boolean cascadable) throws Exception { 
		WorkAreaIncidentVo vo = new WorkAreaIncidentVo();
		
		if(null == entity)
			throw new Exception("Unable to create WorkAreaIncidentVo from null Incident entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setName(entity.getIncidentName());
			vo.setDescription(entity.getIncidentDescription());
		}
		
		return vo;
	}
	
	public static Collection<WorkAreaIncidentVo> getInstances(Collection<Incident> entities, boolean cascadable) throws Exception {
		Collection<WorkAreaIncidentVo> vos = new ArrayList<WorkAreaIncidentVo>();
		
		for(Incident entity : entities){
			vos.add(WorkAreaIncidentVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * Returns the incident name.
	 * 
	 * @return 
	 * 		the incidentName to return
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the incident name.
	 * 
	 * @param incidentName 
	 * 			the incidentName to set
	 */
	public void setName(String incidentName) {
		this.name = incidentName;
	}

	/**
	 * Returns the incident description.
	 * 
	 * @return the incidentDesc
	 * 					the incidentDesc to return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Sets the incident description.
	 * 
	 * @param incidentDesc 
	 * 			the incidentDesc to set
	 */
	public void setDescription(String incidentDesc) {
		this.description = incidentDesc;
	}

	
	
}
