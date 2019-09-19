package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapBranchPersonnel;
import gov.nwcg.isuite.core.domain.IapBranchPersonnelRes;
import gov.nwcg.isuite.core.domain.impl.IapBranchPersonnelImpl;
import gov.nwcg.isuite.core.domain.impl.IapBranchPersonnelResImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapBranchPersonnelResVo extends AbstractVo implements PersistableVo {
	private Long iapBranchPersonnelId;
	private String name;
	private Integer positionNum;
	private Boolean isTrainee=false;
	
	/**
	 * Constructor
	 */
	public IapBranchPersonnelResVo() {
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapBranchPersonnelResVo getInstance(IapBranchPersonnelRes entity, boolean cascadable) throws Exception {
		IapBranchPersonnelResVo vo = new IapBranchPersonnelResVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapBranchPersonnelResVo from null IapBranchPersonnelRes entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIsTrainee(entity.getIsTrainee().getValue());
			vo.setName(entity.getName());
			vo.setIapBranchPersonnelId(entity.getIapBranchPersonnelId());
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
	public static Collection<IapBranchPersonnelResVo> getInstances(Collection<IapBranchPersonnelRes> entities, boolean cascadable,String formType) throws Exception {
		Collection<IapBranchPersonnelResVo> vos = new ArrayList<IapBranchPersonnelResVo>();
		
		for(IapBranchPersonnelRes entity : entities) {
			vos.add(IapBranchPersonnelResVo.getInstance(entity, cascadable));
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
	public static IapBranchPersonnelRes toEntity(IapBranchPersonnelRes entity, IapBranchPersonnelResVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapBranchPersonnelResImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setName(StringUtility.toUpper(vo.getName()));
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsTrainee(StringBooleanEnum.toEnumValue(vo.getIsTrainee()));
			
			IapBranchPersonnel iapBranchPersonnel=(IapBranchPersonnel)AbstractVo.getPersistableObject(persistables, IapBranchPersonnelImpl.class);
			if(null != iapBranchPersonnel){
				entity.setIapBranchPersonnel(iapBranchPersonnel);
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
	public static Collection<IapBranchPersonnelRes> toEntityList(Collection<IapBranchPersonnelResVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapBranchPersonnelRes> entities = new ArrayList<IapBranchPersonnelRes>();
		
		for(IapBranchPersonnelResVo vo : vos) {
			entities.add(IapBranchPersonnelResVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}

	/**
	 * @return the iapBranchPersonnelId
	 */
	public Long getIapBranchPersonnelId() {
		return iapBranchPersonnelId;
	}

	/**
	 * @param iapBranchPersonnelId the iapBranchPersonnelId to set
	 */
	public void setIapBranchPersonnelId(Long iapBranchPersonnelId) {
		this.iapBranchPersonnelId = iapBranchPersonnelId;
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
