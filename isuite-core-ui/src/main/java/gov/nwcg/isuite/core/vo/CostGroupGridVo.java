package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.CostGroup;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class CostGroupGridVo extends AbstractVo {
	private String incidentName;
	private String costGroupName;
	private Date startDate;
	private String description; 
	private String shift;
	
	public CostGroupGridVo() {
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<CostGroupGridVo> getInstances(Collection<CostGroup> entities, Boolean cascadable) throws Exception {
		Collection<CostGroupGridVo> vos = new ArrayList<CostGroupGridVo>();
		
		for(CostGroup entity : entities) {
			vos.add(CostGroupGridVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static CostGroupGridVo getInstance(CostGroup entity, Boolean cascadable) throws Exception {
		CostGroupGridVo vo = new CostGroupGridVo();
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setCostGroupName(entity.getCostGroupName());
			vo.setStartDate(entity.getStartDate());
			vo.setDescription(entity.getDescription());
			
			if (null != entity.getIncidentShift()) {
				vo.setShift(entity.getIncidentShift().getShiftName());
			}
			
			if (null != entity.getIncident()) {
				vo.setIncidentName(entity.getIncident().getIncidentName());
			}
		}
		
		return vo;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param costGroupName the costGroupName to set
	 */
	public void setCostGroupName(String costGroupName) {
		this.costGroupName = costGroupName;
	}

	/**
	 * @return the costGroupName
	 */
	public String getCostGroupName() {
		return costGroupName;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param shift the shift to set
	 */
	public void setShift(String shift) {
		this.shift = shift;
	}

	/**
	 * @return the shift
	 */
	public String getShift() {
		return shift;
	}

}
