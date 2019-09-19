package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentBranch;
import gov.nwcg.isuite.core.domain.impl.IncidentBranchImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class IncidentBranchVo extends AbstractVo implements PersistableVo {
	private IncidentVo incidentVo;
	private String branchName;
	private Short displayOrder;
	private Collection<IapBranchPosItemCodeVo> iapBranchPosItemCodeVos = new ArrayList<IapBranchPosItemCodeVo>();
	
	/**
	 * Constructor
	 */
	public IncidentBranchVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IncidentBranchVo getInstance(IncidentBranch entity, boolean cascadable) throws Exception {
		IncidentBranchVo vo = new IncidentBranchVo();
		
		if(null == entity)
			throw new Exception("Unable to create IncidentBranchVo from null IncidentBranch entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if(null != entity.getIncident()) {
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), true));
			}
			
			vo.setBranchName(entity.getBranchName());
			vo.setDisplayOrder(entity.getDisplayOrder());
			
			if(null != entity.getIapBranchPosItemCodes()) {
				vo.setIapBranchPosItemCodeVos(IapBranchPosItemCodeVo.getInstances(entity.getIapBranchPosItemCodes(), true));
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
	public static Collection<IncidentBranchVo> getInstances(Collection<IncidentBranch> entities, boolean cascadable) throws Exception {
		Collection<IncidentBranchVo> vos = new ArrayList<IncidentBranchVo>();
		
		for(IncidentBranch entity : entities) {
			vos.add(IncidentBranchVo.getInstance(entity, cascadable));
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
	public static IncidentBranch toEntity(IncidentBranch entity, IncidentBranchVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IncidentBranchImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			if(null != vo.getIncidentVo()) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
			
			entity.setBranchName(vo.getBranchName());
			entity.setDisplayOrder(vo.getDisplayOrder());
			
			if(null != vo.getIapBranchPosItemCodeVos()) {
				entity.setIapBranchPosItemCodes(IapBranchPosItemCodeVo.toEntityList(vo.getIapBranchPosItemCodeVos(), true));
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
	public static Collection<IncidentBranch> toEntityList(Collection<IncidentBranchVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IncidentBranch> entities = new ArrayList<IncidentBranch>();
		
		for(IncidentBranchVo vo : vos) {
			entities.add(IncidentBranchVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
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
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param displayOrder the displayOrder to set
	 */
	public void setDisplayOrder(Short displayOrder) {
		this.displayOrder = displayOrder;
	}

	/**
	 * @return the displayOrder
	 */
	public Short getDisplayOrder() {
		return displayOrder;
	}

	/**
	 * @param iapBranchPosItemCodeVos the iapBranchPosItemCodeVos to set
	 */
	public void setIapBranchPosItemCodeVos(Collection<IapBranchPosItemCodeVo> iapBranchPosItemCodeVos) {
		this.iapBranchPosItemCodeVos = iapBranchPosItemCodeVos;
	}

	/**
	 * @return the iapBranchPosItemCodeVos
	 */
	public Collection<IapBranchPosItemCodeVo> getIapBranchPosItemCodeVos() {
		return iapBranchPosItemCodeVos;
	}

}
