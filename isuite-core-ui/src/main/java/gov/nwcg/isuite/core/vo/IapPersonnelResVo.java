package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.IapPersonnelRes;
import gov.nwcg.isuite.core.domain.impl.IapPersonnelImpl;
import gov.nwcg.isuite.core.domain.impl.IapPersonnelResImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapPersonnelResVo extends AbstractVo implements PersistableVo {
	private Long iapPersonnelId;
	private String name;
	private Integer positionNum;
	private Boolean isTrainee=false;
	
	/**
	 * Constructor
	 */
	public IapPersonnelResVo() {
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapPersonnelResVo getInstance(IapPersonnelRes entity, boolean cascadable) throws Exception {
		IapPersonnelResVo vo = new IapPersonnelResVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapPersonnelResVo from null IapPersonnelRes entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIsTrainee(entity.getIsTrainee().getValue());
			vo.setName(entity.getName());
			vo.setIapPersonnelId(entity.getIapPersonnelId());
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));

		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapPersonnelResVo> getInstances(Collection<IapPersonnelRes> entities, boolean cascadable,String formType) throws Exception {
		Collection<IapPersonnelResVo> vos = new ArrayList<IapPersonnelResVo>();
		
		for(IapPersonnelRes entity : entities) {
			vos.add(IapPersonnelResVo.getInstance(entity, cascadable));
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
	public static IapPersonnelRes toEntity(IapPersonnelRes entity, IapPersonnelResVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapPersonnelResImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setName(StringUtility.toUpper(vo.getName()));
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsTrainee(StringBooleanEnum.toEnumValue(vo.getIsTrainee()));
			
			IapPersonnel iapPersonnel=(IapPersonnel)AbstractVo.getPersistableObject(persistables, IapPersonnelImpl.class);
			if(null != iapPersonnel){
				entity.setIapPersonnel(iapPersonnel);
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
	public static Collection<IapPersonnelRes> toEntityList(Collection<IapPersonnelResVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapPersonnelRes> entities = new ArrayList<IapPersonnelRes>();
		
		for(IapPersonnelResVo vo : vos) {
			entities.add(IapPersonnelResVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @return the iapPersonnelId
	 */
	public Long getIapPersonnelId() {
		return iapPersonnelId;
	}

	/**
	 * @param iapPersonnelId the iapPersonnelId to set
	 */
	public void setIapPersonnelId(Long iapPersonnelId) {
		this.iapPersonnelId = iapPersonnelId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
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

	/**
	 * @return the isTrainee
	 */
	public Boolean getIsTrainee() {
		return isTrainee;
	}

	/**
	 * @param isTrainee the isTrainee to set
	 */
	public void setIsTrainee(Boolean isTrainee) {
		this.isTrainee = isTrainee;
	}

}
