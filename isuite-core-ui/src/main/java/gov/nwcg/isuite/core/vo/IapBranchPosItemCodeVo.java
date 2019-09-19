package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapBranchPosItemCode;
import gov.nwcg.isuite.core.domain.impl.IapBranchPosItemCodeImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class IapBranchPosItemCodeVo extends AbstractVo implements PersistableVo {
	private KindVo kindVo;
	private IncidentBranchVo incidentBranchVo;
	private String position;
	private String form;
	
	/**
	 * Constructor
	 */
	public IapBranchPosItemCodeVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapBranchPosItemCodeVo getInstance(IapBranchPosItemCode entity, boolean cascadable) throws Exception {
		IapBranchPosItemCodeVo vo = new IapBranchPosItemCodeVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapBranchPosItemCodeVo from null IapBranchPosItemCode entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if(null != entity.getKind()) {
				vo.setKindVo(KindVo.getInstance(entity.getKind(), true));
			}
			
			if(null != entity.getIncidentBranch()) {
				vo.setIncidentBranchVo(IncidentBranchVo.getInstance(entity.getIncidentBranch(), true));
			}
			
			vo.setPosition(entity.getPosition());
			vo.setForm(entity.getForm());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapBranchPosItemCodeVo> getInstances(Collection<IapBranchPosItemCode> entities, boolean cascadable) throws Exception {
		Collection<IapBranchPosItemCodeVo> vos = new ArrayList<IapBranchPosItemCodeVo>();
		
		for(IapBranchPosItemCode entity : entities) {
			vos.add(IapBranchPosItemCodeVo.getInstance(entity, cascadable));
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
	public static IapBranchPosItemCode toEntity(IapBranchPosItemCode entity, IapBranchPosItemCodeVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapBranchPosItemCodeImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			if(null != vo.getKindVo()) {
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false));
			}
			
			if(null != vo.getIncidentBranchVo()) {
				entity.setIncidentBranch(IncidentBranchVo.toEntity(null, vo.getIncidentBranchVo(), false));
			}
			
			entity.setPosition(vo.getPosition());
			entity.setForm(vo.getForm());
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
	public static Collection<IapBranchPosItemCode> toEntityList(Collection<IapBranchPosItemCodeVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapBranchPosItemCode> entities = new ArrayList<IapBranchPosItemCode>();
		
		for(IapBranchPosItemCodeVo vo : vos) {
			entities.add(IapBranchPosItemCodeVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * @param incidentBranchVo the incidentBranchVo to set
	 */
	public void setIncidentBranchVo(IncidentBranchVo incidentBranchVo) {
		this.incidentBranchVo = incidentBranchVo;
	}

	/**
	 * @return the incidentBranchVo
	 */
	public IncidentBranchVo getIncidentBranchVo() {
		return incidentBranchVo;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(String position) {
		this.position = position;
	}

	/**
	 * @return the position
	 */
	public String getPosition() {
		return position;
	}

	/**
	 * @param form the form to set
	 */
	public void setForm(String form) {
		this.form = form;
	}

	/**
	 * @return the form
	 */
	public String getForm() {
		return form;
	}
}
