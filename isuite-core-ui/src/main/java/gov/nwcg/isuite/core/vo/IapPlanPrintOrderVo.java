package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapPlanPrintOrderImpl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo; 
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapPlanPrintOrderVo extends AbstractVo implements PersistableVo {
	private Long iapPlanId;
	private String formType;
	private Long formId;
	private Integer position;
	
	/**
	 * Constructor
	 */
	public IapPlanPrintOrderVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapPlanPrintOrderVo getInstance(IapPlanPrintOrder entity, boolean cascadable) throws Exception {
		IapPlanPrintOrderVo vo = new IapPlanPrintOrderVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapPlanPrintOrderVo from null IapPlanPrintOrder entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if(null != entity.getIapPlanId())
				vo.setIapPlanId(entity.getIapPlan().getId());
			vo.setFormId(entity.getFormId());
			vo.setFormType(entity.getFormType());
			vo.setPosition(entity.getPosition());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapPlanPrintOrderVo> getInstances(Collection<IapPlanPrintOrder> entities, boolean cascadable) throws Exception {
		Collection<IapPlanPrintOrderVo> vos = new ArrayList<IapPlanPrintOrderVo>();
		
		for(IapPlanPrintOrder entity : entities) {
			vos.add(IapPlanPrintOrderVo.getInstance(entity, cascadable));
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
	public static IapPlanPrintOrder toEntity(IapPlanPrintOrder entity, IapPlanPrintOrderVo vo, boolean cascadable) throws Exception {
		if(null == entity) entity = new IapPlanPrintOrderImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			if(LongUtility.hasValue(vo.getIapPlanId())){
				IapPlan iapPlan = new IapPlanImpl();
				iapPlan.setId(vo.getIapPlanId());
				entity.setIapPlan(iapPlan);
			}
			entity.setFormType(vo.getFormType());
			entity.setFormId(vo.getFormId());
			entity.setPosition(vo.getPosition());
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
	public static Collection<IapPlanPrintOrder> toEntityList(Collection<IapPlanPrintOrderVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapPlanPrintOrder> entities = new ArrayList<IapPlanPrintOrder>();
		
		for(IapPlanPrintOrderVo vo : vos) {
			entities.add(IapPlanPrintOrderVo.toEntity(null, vo, cascadable));
		}
		
		return entities;
	}
 

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the formType
	 */
	public String getFormType() {
		return formType;
	}

	/**
	 * @param formType the formType to set
	 */
	public void setFormType(String formType) {
		this.formType = formType;
	}
	
	/**
	 * @return the formId
	 */
	public Long getFormId() {
		return formId;
	}

	/**
	 * @param formId the formId to set
	 */
	public void setFormId(Long formId) {
		this.formId = formId;
	}

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
}
