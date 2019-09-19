package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentShift;
import gov.nwcg.isuite.core.domain.impl.IncidentShiftImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;

public class IncidentShiftVo extends AbstractVo {
	private String shiftName;
	private IncidentVo incidentVo;
	private String startTime;
	private String endTime;
	private FontVo fontVo;
	
	public IncidentShiftVo() {
		
	}
	
	/**
	 * Returns a IncidentShiftVo from a IncidentShift entity.
	 * 
	 * @param entity
	 * 			the source IncidentShift entity
	 * @param cascadable
	 * 			flag indicating whether the vo instance should created as a cascadable vo
	 * @return
	 * 			instance of IncidentShiftVo
	 * @throws Exception
	 */
	public static IncidentShiftVo getInstance(IncidentShift entity, boolean  cascadable) throws Exception{
		IncidentShiftVo vo = new IncidentShiftVo();
		
		if(null == entity)
			throw new Exception("Unable to create IncidentShiftVo from null IncidentShift entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setShiftName(entity.getShiftName());
			
			if (null != entity.getIncident()) {
				IncidentVo ivo = new IncidentVo();
				ivo.setId(entity.getIncident().getId());
				ivo.setIncidentName(entity.getIncident().getIncidentName());
				vo.setIncidentVo(ivo);
			}
	
			vo.setStartTime(entity.getStartTime());
			vo.setEndTime(entity.getEndTime());
			
			if(null != entity.getFont()) {
				vo.setFontVo(FontVo.getInstance(entity.getFont(), true));
			}
		}

		return vo;
	}
	
	/**
	 * Returns a collection of IncidentShiftVos from a collection of IncidentShift entities.
	 * 
	 * @param entities
	 * 			the source collection of IncidentShift entities
	 * @param cascadable
	 * 			flag indicating whether the vo instances should created as a cascadable vos
	 * @return
	 * 			collection of IncidentShift vos
	 * @throws Exception
	 */
	public static Collection<IncidentShiftVo> getInstances(Collection<IncidentShift> entities, boolean cascadable) throws Exception {
		Collection<IncidentShiftVo> vos = new ArrayList<IncidentShiftVo>();
		
		for(IncidentShift entity : entities) {
			vos.add(IncidentShiftVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	/**
	 * Returns an IncidentShift entity from an IncidentShift vo
	 * 
	 * @param vo
	 * 			the source IncidentShift vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity			
	 * @param persistables
	 * @return
	 * 			instance of IncidentGroup entity
	 * @throws Exception
	 */
	public static IncidentShift toEntity(IncidentShift entity, IncidentShiftVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		
		if(null == entity) entity = new IncidentShiftImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setShiftName(vo.getShiftName());
			
			if(null != vo.getIncidentVo()) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
			
			entity.setStartTime(vo.getStartTime());
			entity.setEndTime(vo.getEndTime());
			
			if(null != vo.getFontVo()) {
				entity.setFont(FontVo.toEntity(null, vo.getFontVo(), false));
			}
		}
		
		return entity;
	}
	
	/**
	 * Returns a collection of IncidentShift entities from a collection of IncidentShift vos
	 * 
	 * @param vos
	 * 			the source collection of IncidentShift vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @param persistables
	 * @return
	 * 			collection of IncidentShift entities
	 * @throws Exception
	 */
	public static Collection<IncidentShift> toEntityList(Collection<IncidentShiftVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentShift> entities = new ArrayList<IncidentShift>();
		
		for(IncidentShiftVo vo : vos) {
			entities.add(IncidentShiftVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param shiftName the shiftName to set
	 */
	public void setShiftName(String shiftName) {
		this.shiftName = shiftName;
	}

	/**
	 * @return the shiftName
	 */
	public String getShiftName() {
		return shiftName;
	}

	/**
	 * @param incidentVo the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @param fontVo the fontVo to set
	 */
	public void setFontVo(FontVo fontVo) {
		this.fontVo = fontVo;
	}

	/**
	 * @return the fontVo
	 */
	public FontVo getFontVo() {
		return fontVo;
	}

}
