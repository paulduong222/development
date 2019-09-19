package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm205;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapForm205Impl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IapForm205Vo extends AbstractVo {
	private Long iapPlanId;
	private String specialInstruction;
	private String preparedBy;
	private String preparedByPosition;
	private String preparedTime;
	private DateTransferVo preparedDateVo = new DateTransferVo();
	private Boolean isFormLocked=false;
	private Collection<IapFrequencyVo> iapFrequencieVos = new ArrayList<IapFrequencyVo>();

	
	public IapForm205Vo(){
		
	}

	public static IapForm205Vo getInstance(IapForm205 entity, Boolean cascadable) throws Exception {
		IapForm205Vo vo = new IapForm205Vo();
		
		if(null==entity)
			throw new Exception("Unable to create IapForm205Vo from null IapForm205 entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){

			vo.setSpecialInstruction(entity.getSpecialInstruction());
			vo.setPreparedBy(entity.getPreparedBy());
			vo.setPreparedByPosition(entity.getPreparedByPosition());
			vo.setIsFormLocked(entity.getIsFormLocked().getValue());
			vo.setIapPlanId(entity.getIapPlanId());

			if(DateUtil.hasValue(entity.getPreparedDate())){
				DateTransferVo.populateDate(vo.getPreparedDateVo(), entity.getPreparedDate());
				vo.setPreparedTime(vo.getPreparedDateVo().getTimeString());
			}
			
			if(CollectionUtility.hasValue(entity.getIapFrequencies())){
				vo.setIapFrequencieVos(IapFrequencyVo.getInstances(entity.getIapFrequencies(), true));
			}
		}
		
		return vo;
	}
	
	public static IapForm205 toEntity(IapForm205 entity, IapForm205Vo vo, Boolean cascadable,Persistable...persistables  ) throws Exception {
		if(null == entity) entity = new IapForm205Impl();
		
		entity.setId(vo.getId());
		
		IapPlan iapPlan =(IapPlan)AbstractVo.getPersistableObject(persistables, IapPlanImpl.class);
		if(null != iapPlan)
			entity.setIapPlan(iapPlan);
		else if(LongUtility.hasValue(vo.getIapPlanId())){
			iapPlan = new IapPlanImpl();
			iapPlan.setId(vo.getIapPlanId());
			entity.setIapPlan(iapPlan);
		}
		
		if(cascadable){
			entity.setIsFormLocked(StringBooleanEnum.toEnumValue(vo.getIsFormLocked()));
			entity.setSpecialInstruction(vo.getSpecialInstruction());
			
			entity.setPreparedBy(vo.getPreparedBy());
			entity.setPreparedByPosition(vo.getPreparedByPosition());
			if(DateTransferVo.hasDateString(vo.getPreparedDateVo())){
				Date dt=DateTransferVo.getTransferDate(vo.getPreparedDateVo());
				entity.setPreparedDate(dt);
			}else {
				entity.setPreparedDate(null);
			}
			
			if(CollectionUtility.hasValue(vo.getIapFrequencieVos())){
				entity.setIapFrequencies(IapFrequencyVo.toEntityList(vo.getIapFrequencieVos(), Boolean.TRUE, entity));
			}
		}
		
		return entity;
	}
	
	public static Collection<IapForm205> toEntityList(Collection<IapForm205Vo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapForm205> entities = new ArrayList<IapForm205>();

		for(IapForm205Vo vo : vos){
			entities.add(IapForm205Vo.toEntity(null, vo,cascadable, persistables));
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
	 * @return the specialInstruction
	 */
	public String getSpecialInstruction() {
		return specialInstruction;
	}

	/**
	 * @param specialInstruction the specialInstruction to set
	 */
	public void setSpecialInstruction(String specialInstruction) {
		this.specialInstruction = specialInstruction;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedDateVo
	 */
	public DateTransferVo getPreparedDateVo() {
		return preparedDateVo;
	}

	/**
	 * @param preparedDateVo the preparedDateVo to set
	 */
	public void setPreparedDateVo(DateTransferVo preparedDateVo) {
		this.preparedDateVo = preparedDateVo;
	}

	/**
	 * @return the iapFrequencieVos
	 */
	public Collection<IapFrequencyVo> getIapFrequencieVos() {
		return iapFrequencieVos;
	}

	/**
	 * @param iapFrequencieVos the iapFrequencieVos to set
	 */
	public void setIapFrequencieVos(Collection<IapFrequencyVo> iapFrequencieVos) {
		this.iapFrequencieVos = iapFrequencieVos;
	}

	/**
	 * @return the preparedTime
	 */
	public String getPreparedTime() {
		return preparedTime;
	}

	/**
	 * @param preparedTime the preparedTime to set
	 */
	public void setPreparedTime(String preparedTime) {
		this.preparedTime = preparedTime;
	}

	/**
	 * @return the isFormLocked
	 */
	public Boolean getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(Boolean isFormLocked) {
		this.isFormLocked = isFormLocked;
	}
	
}
