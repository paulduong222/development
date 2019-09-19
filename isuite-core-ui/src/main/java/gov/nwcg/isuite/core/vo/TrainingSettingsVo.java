package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.TrainingSettings;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.core.domain.impl.TrainingSettingsImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.LongUtility;

public class TrainingSettingsVo extends AbstractVo implements PersistableVo {
	private ComplexityVo complexityVo;
	private Long numberOfAcres;
	private Long incidentId;
	private Long incidentGroupId;
	
	private IncidentVo incidentVo;
//	private IncidentGroupVo incidentGroupVo;
	private Collection<FuelTypeVo> fuelTypeVos = new ArrayList<FuelTypeVo>();
	
	/**
	 * Default constructor
	 */
	public TrainingSettingsVo(){
		super();
	}
	
	public static TrainingSettingsVo getInstance(TrainingSettings entity, boolean cascadable) throws Exception {
		TrainingSettingsVo vo = new TrainingSettingsVo();
		
		if(null == entity)
			throw new Exception("Unable to create TrainingSettingsVo from null TrainingSettings entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			if(null != entity.getComplexity()) {
				vo.setComplexityVo(ComplexityVo.getInstance(entity.getComplexity(),	true));
			}
			
			if(null != entity.getIncident()) {
				vo.setIncidentId(entity.getIncident().getId());
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
			}
			
			if(null != entity.getIncidentGroup())
				vo.setIncidentGroupId(entity.getIncidentGroup().getId());
			
			vo.setNumberOfAcres(entity.getNumberOfAcres());
			
			if(null != entity.getFuelTypes())
				vo.setFuelTypeVos(FuelTypeVo.getInstances(entity.getFuelTypes(), true));
		}
		
		return vo;
	}
	
	public static Collection<TrainingSettingsVo> getInstances(Collection<TrainingSettings> entities,boolean cascadable) throws Exception {
		Collection<TrainingSettingsVo> vos = new ArrayList<TrainingSettingsVo>();
		
		for(TrainingSettings entity : entities){
			vos.add(TrainingSettingsVo.getInstance(entity, cascadable));
		}
		
		return vos;
	}
	
	public static TrainingSettings toEntity(TrainingSettings entity, TrainingSettingsVo vo, boolean cascadable,Persistable...persistables)throws Exception {
		if (null == entity){
			entity = new TrainingSettingsImpl();
		}
		
		entity.setId(vo.getId());
		
		if(cascadable){
			if(null != vo.getComplexityVo()){
				entity.setComplexity(ComplexityVo.toEntity(null, vo.getComplexityVo(), false));
			}
			
			entity.setNumberOfAcres(vo.getNumberOfAcres());
			
			if(LongUtility.hasValue(vo.getIncidentId())){
				Incident inc = new IncidentImpl();
				inc.setId(vo.getIncidentId());
				entity.setIncident(inc);
			}
			
			if(LongUtility.hasValue(vo.getIncidentGroupId())){
				IncidentGroup incGroup = new IncidentGroupImpl();
				incGroup.setId(vo.getIncidentGroupId());
				entity.setIncidentGroup(incGroup);
			}
			
			if(null != vo.getFuelTypeVos())
				entity.setFuelTypes(FuelTypeVo.toEntityList(vo.getFuelTypeVos(), true));
			
		}
		return entity;
	}
	
	public static TrainingSettings toCreateEntity(TrainingSettings entity, TrainingSettingsVo vo, Long id, Long incId, Long igId, boolean cascadable,Persistable...persistables)throws Exception {
		if (null == entity){
			entity = new TrainingSettingsImpl();
		}
		
		entity.setId(id);
		
		if(cascadable){
			if(null != vo.getComplexityVo()){
				entity.setComplexity(ComplexityVo.toEntity(null, vo.getComplexityVo(), false));
			}else{
				entity.setComplexity(null);
			}
			
			entity.setNumberOfAcres(vo.getNumberOfAcres());
			
			if(LongUtility.hasValue(incId)){
				Incident inc = new IncidentImpl();
				inc.setId(incId);
				entity.setIncident(inc);
			}
			
			if(LongUtility.hasValue(igId)){
				IncidentGroup incGroup = new IncidentGroupImpl();
				incGroup.setId(igId);
				entity.setIncidentGroup(incGroup);
			}
			
			if(null != vo.getFuelTypeVos())
				entity.setFuelTypes(FuelTypeVo.toEntityList(vo.getFuelTypeVos(), true));
			
		}
		return entity;
	}
	

	/**
	 * @param complexityVo the complexityVo to set
	 */
	public void setComplexityVo(ComplexityVo complexityVo) {
		this.complexityVo = complexityVo;
	}

	/**
	 * @return the complexityVo
	 */
	public ComplexityVo getComplexityVo() {
		return complexityVo;
	}

	/**
	 * @param numberOfAcres the numberOfAcres to set
	 */
	public void setNumberOfAcres(Long numberOfAcres) {
		this.numberOfAcres = numberOfAcres;
	}

	/**
	 * @return the numberOfAcres
	 */
	public Long getNumberOfAcres() {
		return numberOfAcres;
	}

	/**
	 * @param fuelTypeVos the fuelTypeVos to set
	 */
	public void setFuelTypeVos(Collection<FuelTypeVo> fuelTypeVos) {
		this.fuelTypeVos = fuelTypeVos;
	}

	/**
	 * @return the fuelTypeVos
	 */
	public Collection<FuelTypeVo> getFuelTypeVos() {
		return fuelTypeVos;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
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
	

}
