package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.domain.RossImportProcessMatchInc;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessImpl;
import gov.nwcg.isuite.core.domain.impl.RossImportProcessMatchIncImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class RossImportProcessMatchIncVo extends AbstractVo{
	private RossImportProcessVo rossImportProcessVo;
	private Long matchingIncidentId;

	/*
	 * Non-Entity type properties
	 * 		Incident Information from the ross import file
	 * 		Collection of possible eisuite incidents that match the ross incident
	 *		Projected best guess matching eisuite incident vo
	 * 		Actual eisuite incident vo that the client has selected as the matching record  to use
	 */
	private IncidentVo rossIncidentVo;
	private Collection<IncidentVo> eisuiteIncidents = new ArrayList<IncidentVo>();
	private IncidentVo projectedMatchIncidentVo;
	private IncidentVo actualMatchIncidentVo;
	
	public RossImportProcessMatchIncVo(){
		
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossImportProcessMatchIncVo getInstance(RossImportProcessMatchInc entity, Boolean cascadable) throws Exception {
		RossImportProcessMatchIncVo vo = new RossImportProcessMatchIncVo();

		if(null == entity)
			throw new Exception("Unable to create RossImportProcessMatchIncVo from null RossImportProcessMatchInc entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setMatchingIncidentId(entity.getMatchingIncidentId());
			
			if(null != entity.getRossImportProcessId()){
				vo.setRossImportProcessVo(RossImportProcessVo.getInstance(entity.getRossImportProcess(), false));
			}
			
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossImportProcessMatchIncVo> getInstances(Collection<RossImportProcessMatchInc> entities, Boolean cascadable) throws Exception {
		Collection<RossImportProcessMatchIncVo> vos = new ArrayList<RossImportProcessMatchIncVo>();
		
		for(RossImportProcessMatchInc entity : entities){
			vos.add(RossImportProcessMatchIncVo.getInstance(entity,cascadable));
		}
		
		return vos;
	}

	/**
	 * @param vo
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RossImportProcessMatchInc toEntity(RossImportProcessMatchInc entity,RossImportProcessMatchIncVo vo, Boolean cascadable, Persistable... persistables) throws Exception {
		if(null == entity)
			entity=new RossImportProcessMatchIncImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setMatchingIncidentId(vo.getMatchingIncidentId());
			
			RossImportProcess ripEntity = (RossImportProcess)getPersistableObject(persistables,RossImportProcessImpl.class);
			if(null != ripEntity)
				entity.setRossImportProcess(ripEntity);
			
			/*
			 * Validate the entity
			 */
			 validateEntity(entity);
		}

		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<RossImportProcessMatchInc> toEntities(Collection<RossImportProcessMatchIncVo> vos, Boolean cascadable,Persistable... persistables) throws Exception {
		Collection<RossImportProcessMatchInc> entities = new ArrayList<RossImportProcessMatchInc>();
		
		for(RossImportProcessMatchIncVo vo : vos){
			entities.add(RossImportProcessMatchIncVo.toEntity(null,vo,cascadable,persistables));
		}
		
		return entities;
	}

	/**
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(RossImportProcessMatchInc entity) throws ValidationException {
		
	}

	/**
	 * @return the rossImportProcessVo
	 */
	public RossImportProcessVo getRossImportProcessVo() {
		return rossImportProcessVo;
	}

	/**
	 * @param rossImportProcessVo the rossImportProcessVo to set
	 */
	public void setRossImportProcessVo(RossImportProcessVo rossImportProcessVo) {
		this.rossImportProcessVo = rossImportProcessVo;
	}

	/**
	 * @return the matchingIncidentId
	 */
	public Long getMatchingIncidentId() {
		return matchingIncidentId;
	}

	/**
	 * @param matchingIncidentId the matchingIncidentId to set
	 */
	public void setMatchingIncidentId(Long matchingIncidentId) {
		this.matchingIncidentId = matchingIncidentId;
	}

	/**
	 * @return the rossIncidentVo
	 */
	public IncidentVo getRossIncidentVo() {
		return rossIncidentVo;
	}

	/**
	 * @param rossIncidentVo the rossIncidentVo to set
	 */
	public void setRossIncidentVo(IncidentVo rossIncidentVo) {
		this.rossIncidentVo = rossIncidentVo;
	}

	/**
	 * @return the eisuiteIncidents
	 */
	public Collection<IncidentVo> getEisuiteIncidents() {
		return eisuiteIncidents;
	}

	/**
	 * @param eisuiteIncidents the eisuiteIncidents to set
	 */
	public void setEisuiteIncidents(Collection<IncidentVo> eisuiteIncidents) {
		this.eisuiteIncidents = eisuiteIncidents;
	}

	/**
	 * @return the projectedMatchIncidentVo
	 */
	public IncidentVo getProjectedMatchIncidentVo() {
		return projectedMatchIncidentVo;
	}

	/**
	 * @param projectedMatchIncidentVo the projectedMatchIncidentVo to set
	 */
	public void setProjectedMatchIncidentVo(IncidentVo projectedMatchIncidentVo) {
		this.projectedMatchIncidentVo = projectedMatchIncidentVo;
	}

	/**
	 * @return the actualMatchIncidentVo
	 */
	public IncidentVo getActualMatchIncidentVo() {
		return actualMatchIncidentVo;
	}

	/**
	 * @param actualMatchIncidentVo the actualMatchIncidentVo to set
	 */
	public void setActualMatchIncidentVo(IncidentVo actualMatchIncidentVo) {
		this.actualMatchIncidentVo = actualMatchIncidentVo;
	}
	
}
