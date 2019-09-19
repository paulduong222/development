package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapAttachment;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.core.domain.impl.IapAttachmentImpl;
import gov.nwcg.isuite.core.domain.impl.IapPlanImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapAttachmentVo extends AbstractVo implements PersistableVo {
	private Long iapPlanId;
	private String attachmentName;
	private Boolean attached;
	private String filename;
	
	/**
	 * Constructor
	 */
	public IapAttachmentVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapAttachmentVo getInstance(IapAttachment entity, boolean cascadable) throws Exception {
		IapAttachmentVo vo = new IapAttachmentVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapMedicalAidVo from null IapMedicalAid entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if(null != entity.getIapPlan())
				vo.setIapPlanId(entity.getIapPlan().getId());
			vo.setAttachmentName(entity.getAttachmentName());
			vo.setAttached(StringBooleanEnum.toBooleanValue(entity.getAttached()));
			vo.setFilename(entity.getFilename());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapAttachmentVo> getInstances(Collection<IapAttachment> entities, boolean cascadable) throws Exception {
		Collection<IapAttachmentVo> vos = new ArrayList<IapAttachmentVo>();
		
		for(IapAttachment entity : entities) {
			vos.add(IapAttachmentVo.getInstance(entity, cascadable));
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
	public static IapAttachment toEntity(IapAttachment entity, IapAttachmentVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapAttachmentImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			if(LongUtility.hasValue(vo.getIapPlanId())){
				IapPlan iapPlan = new IapPlanImpl();
				iapPlan.setId(vo.getIapPlanId());
				entity.setIapPlan(iapPlan);
			}
			entity.setAttachmentName(vo.getAttachmentName());
			entity.setAttached(StringBooleanEnum.toEnumValue(vo.getAttached()));
			entity.setFilename(vo.getFilename());
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
	public static Collection<IapAttachment> toEntityList(Collection<IapAttachmentVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapAttachment> entities = new ArrayList<IapAttachment>();
		
		for(IapAttachmentVo vo : vos) {
			entities.add(IapAttachmentVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param attachmentName the attachmentName to set
	 */
	public void setAttachmentName(String attachmentName) {
		this.attachmentName = attachmentName;
	}

	/**
	 * @return the attachmentName
	 */
	public String getAttachmentName() {
		return attachmentName;
	}

	/**
	 * @param attached the attached to set
	 */
	public void setAttached(Boolean attached) {
		this.attached = attached;
	}

	/**
	 * @return the attached
	 */
	public Boolean getAttached() {
		return attached;
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
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
}
