package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.BranchSetting;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.impl.BranchSettingImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentGroupImpl;
import gov.nwcg.isuite.core.domain.impl.IncidentImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class BranchSettingVo extends AbstractVo implements PersistableVo {
	private Long incidentId;
	private Long incidentGroupId;
	private String branchName;
	private Integer positionNum;
	private Collection<BranchSettingPositionVo> branchSettingPositionVos = new ArrayList<BranchSettingPositionVo>();

	private Collection<BranchPositionVo> branchPositionVos = new ArrayList<BranchPositionVo>();
	
	public BranchSettingVo(){
		
	}

	public static BranchSettingVo getInstance(BranchSetting entity, Boolean cascadable) throws Exception {
		BranchSettingVo vo = new BranchSettingVo();
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if(null != entity.getIncident())
				vo.setIncidentId(entity.getIncident().getId());
			if(null != entity.getIncidentGroup())
				vo.setIncidentGroupId(entity.getIncidentGroup().getId());
			
			vo.setBranchName(entity.getBranchName());
			vo.setPositionNum(entity.getPositionNum());
			
			if(CollectionUtility.hasValue(entity.getBranchSettingPositions())){
				vo.setBranchSettingPositionVos(BranchSettingPositionVo.getInstances(entity.getBranchSettingPositions(), true));

				vo.setBranchPositionVos(BranchPositionVo.getInstances(vo.getBranchSettingPositionVos()));
			}
		}
		
		return vo;
	}
	
	public static Collection<BranchSettingVo> getInstances(Collection<BranchSetting> entities, Boolean cascadable) throws Exception {
		Collection<BranchSettingVo> vos = new ArrayList<BranchSettingVo>();
		
		for(BranchSetting entity : entities){
			vos.add(getInstance(entity,cascadable));
		}
		
		return vos;
	}
	
	/**
	 * @param entity
	 * @param vo
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static BranchSetting toEntity(BranchSetting entity, BranchSettingVo vo, Boolean cascadable,Persistable...persistables  ) throws Exception {
		if(null == entity) entity = new BranchSettingImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			entity.setBranchName(vo.getBranchName());
			entity.setPositionNum(vo.getPositionNum());
			
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
			
		}
		
		return entity;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<BranchSetting> toEntityList(Collection<BranchSettingVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<BranchSetting> entities = new ArrayList<BranchSetting>();

		for(BranchSettingVo vo : vos){
			entities.add(BranchSettingVo.toEntity(null, vo,cascadable, persistables));
		}

		return entities;
	}
	
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchSettingPositionVos
	 */
	public Collection<BranchSettingPositionVo> getBranchSettingPositionVos() {
		return branchSettingPositionVos;
	}

	/**
	 * @param branchSettingPositionVos the branchSettingPositionVos to set
	 */
	public void setBranchSettingPositionVos(
			Collection<BranchSettingPositionVo> branchSettingPositionVos) {
		this.branchSettingPositionVos = branchSettingPositionVos;
	}

	/**
	 * @return the branchPositionVos
	 */
	public Collection<BranchPositionVo> getBranchPositionVos() {
		return branchPositionVos;
	}

	/**
	 * @param branchPositionVos the branchPositionVos to set
	 */
	public void setBranchPositionVos(Collection<BranchPositionVo> branchPositionVos) {
		this.branchPositionVos = branchPositionVos;
	}

	/**
	 * @return the positionNum
	 */
	public Integer getPositionNum() {
		return positionNum;
	}

	/**
	 * @param positionNum the positionNum to set
	 */
	public void setPositionNum(Integer positionNum) {
		this.positionNum = positionNum;
	}

	
	
}
