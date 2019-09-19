package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.impl.IapAircraftTaskImpl;
import gov.nwcg.isuite.core.domain.impl.IapForm220Impl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapAircraftTaskVo extends AbstractVo implements PersistableVo {
	private Long iapForm220Id;
	private String type;
	private String name;
	private String startTime;
	private String flyFrom;
	private String flyTo;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	
	/**
	 * Constructor
	 */
	public IapAircraftTaskVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapAircraftTaskVo getInstance(IapAircraftTask entity, boolean cascadable) throws Exception {
		IapAircraftTaskVo vo = new IapAircraftTaskVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapAircraftTaskVo from null IapAircraftTask entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapForm220Id(entity.getIapForm220Id());
			if ( null != entity.getIapForm220()) {
				vo.setIapForm220Id(entity.getIapForm220().getId());
			}
			vo.setType(entity.getType());
			vo.setName(entity.getName());
			vo.setStartTime(entity.getStartTime());
			vo.setFlyFrom(entity.getFlyFrom());
			vo.setFlyTo(entity.getFlyTo());
			if(IntegerUtility.hasValue(entity.getPositionNum()))
				vo.setPositionNum(entity.getPositionNum());
			else
				vo.setPositionNum(new Integer(0));

			vo.setIsBlankLine(entity.getIsBlankLine().getValue());
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<IapAircraftTaskVo> getInstances(Collection<IapAircraftTask> entities, boolean cascadable) throws Exception {
		Collection<IapAircraftTaskVo> vos = new ArrayList<IapAircraftTaskVo>();
	
		for(IapAircraftTask entity : entities) {
			vos.add(IapAircraftTaskVo.getInstance(entity, cascadable));
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
	public static IapAircraftTask toEntity(IapAircraftTask entity, IapAircraftTaskVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapAircraftTaskImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setType(StringUtility.toUpper(vo.getType()));
			entity.setName(StringUtility.toUpper(vo.getName()));
			entity.setStartTime(StringUtility.toUpper(vo.getStartTime()));
			entity.setFlyFrom(StringUtility.toUpper(vo.getFlyFrom()));
			entity.setFlyTo(StringUtility.toUpper(vo.getFlyTo()));
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			
			IapForm220 iapForm220 = (IapForm220)AbstractVo.getPersistableObject(persistables, IapForm220Impl.class);
			if(null != iapForm220){
				entity.setIapForm220(iapForm220);
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
	public static Collection<IapAircraftTask> toEntityList(Collection<IapAircraftTaskVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapAircraftTask> entities = new ArrayList<IapAircraftTask>();
		
		for(IapAircraftTaskVo vo : vos) {
			entities.add(IapAircraftTaskVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	
	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @param flyFrom the flyFrom to set
	 */
	public void setFlyFrom(String flyFrom) {
		this.flyFrom = flyFrom;
	}

	/**
	 * @return the flyFrom
	 */
	public String getFlyFrom() {
		return flyFrom;
	}

	/**
	 * @param flyTo the flyTo to set
	 */
	public void setFlyTo(String flyTo) {
		this.flyTo = flyTo;
	}

	/**
	 * @return the flyTo
	 */
	public String getFlyTo() {
		return flyTo;
	}

	/**
	 * @return the iapForm220Id
	 */
	public Long getIapForm220Id() {
		return iapForm220Id;
	}

	/**
	 * @param iapForm220Id the iapForm220Id to set
	 */
	public void setIapForm220Id(Long iapForm220Id) {
		this.iapForm220Id = iapForm220Id;
	}

	/**
	 * @return the isBlankLine
	 */
	public Boolean getIsBlankLine() {
		return isBlankLine;
	}

	/**
	 * @param isBlankLine the isBlankLine to set
	 */
	public void setIsBlankLine(Boolean isBlankLine) {
		this.isBlankLine = isBlankLine;
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
