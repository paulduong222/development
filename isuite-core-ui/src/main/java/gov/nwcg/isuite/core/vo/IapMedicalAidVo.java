package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapMedicalAid;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.core.domain.impl.IapMedicalAidImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

public class IapMedicalAidVo extends AbstractVo implements PersistableVo {
	private Long iapForm206Id;
	private String type;
	private String name;
	private String location;
	private String phone;
	private Boolean paramedics;
	private String serviceLevel;
	private AddressVo addressVo = new AddressVo();
	private String airType;
	private String capability;
	private String emsFrequency;
	private Boolean lifeSupport;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	
	/**
	 * Constructor
	 */
	public IapMedicalAidVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapMedicalAidVo getInstance(IapMedicalAid entity, boolean cascadable) throws Exception {
		IapMedicalAidVo vo = new IapMedicalAidVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapMedicalAidVo from null IapMedicalAid entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			if ( LongUtility.hasValue(entity.getIapForm206Id()))
				vo.setIapForm206Id(entity.getIapForm206Id());
			
			if ( null != entity.getIapForm206()) {
				vo.setIapForm206Id(entity.getIapForm206().getId());
			}

			vo.setType(entity.getType());
			vo.setName(entity.getName());
			vo.setLocation(entity.getLocation());
			vo.setPhone(entity.getPhone());
			vo.setParamedics(entity.getParamedics().getValue());
			vo.setServiceLevel(entity.getServiceLevel());
			vo.setLifeSupport(entity.getLifeSupport().getValue());
			vo.setAirType(entity.getAirType());
			vo.setCapability(entity.getCapability());
			vo.setEmsFrequency(entity.getEmsFrequency());
			if(null != entity.getAddress()){
				vo.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
			}
			
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
	public static Collection<IapMedicalAidVo> getInstances(Collection<IapMedicalAid> entities, boolean cascadable) throws Exception {
		Collection<IapMedicalAidVo> vos = new ArrayList<IapMedicalAidVo>();
		
		for(IapMedicalAid entity : entities) {
			vos.add(IapMedicalAidVo.getInstance(entity, cascadable));
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
	public static IapMedicalAid toEntity(IapMedicalAid entity, IapMedicalAidVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapMedicalAidImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setType(vo.getType());
			entity.setName(vo.getName());
			entity.setLocation(vo.getLocation());
			entity.setPhone(vo.getPhone());
			entity.setParamedics(StringBooleanEnum.toEnumValue(vo.getParamedics()));
			entity.setServiceLevel(vo.getServiceLevel());
			entity.setAirType(vo.getAirType());
			entity.setCapability(vo.getCapability());
			entity.setLifeSupport(StringBooleanEnum.toEnumValue(vo.getLifeSupport()));
			entity.setEmsFrequency(vo.getEmsFrequency());
			if(null != vo.getAddressVo()){
				entity.setAddress(AddressVo.toEntity(vo.getAddressVo(), true));
			}
			
			IapForm206 form206=(IapForm206Impl)AbstractVo.getPersistableObject(persistables, IapForm206Impl.class);
			if(null != form206){
				entity.setIapForm206(form206);
//				IapForm206 form206Entity = new IapForm206Impl();
//				form206Entity.setId(form206.getId());
//				entity.setIapForm206(form206Entity);
			}
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			if ( BooleanUtility.isTrue(vo.getIsBlankLine()))
				entity.setName("");
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
	public static Collection<IapMedicalAid> toEntityList(Collection<IapMedicalAidVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapMedicalAid> entities = new ArrayList<IapMedicalAid>();
		
		for(IapMedicalAidVo vo : vos) {
			entities.add(IapMedicalAidVo.toEntity(null, vo, cascadable, persistables));
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
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param paramedics the paramedics to set
	 */
	public void setParamedics(Boolean paramedics) {
		this.paramedics = paramedics;
	}

	/**
	 * @return the paramedics
	 */
	public Boolean getParamedics() {
		return paramedics;
	}

	/**
	 * @param serviceLevel the serviceLevel to set
	 */
	public void setServiceLevel(String serviceLevel) {
		this.serviceLevel = serviceLevel;
	}

	/**
	 * @return the serviceLevel
	 */
	public String getServiceLevel() {
		return serviceLevel;
	}

	/**
	 * @return the iapForm206Id
	 */
	public Long getIapForm206Id() {
		return iapForm206Id;
	}

	/**
	 * @param iapForm206Id the iapForm206Id to set
	 */
	public void setIapForm206Id(Long iapForm206Id) {
		this.iapForm206Id = iapForm206Id;
	}

	/**
	 * @return the addressVo
	 */
	public AddressVo getAddressVo() {
		return addressVo;
	}

	/**
	 * @param addressVo the addressVo to set
	 */
	public void setAddressVo(AddressVo addressVo) {
		this.addressVo = addressVo;
	}

	/**
	 * @return the airType
	 */
	public String getAirType() {
		return airType;
	}

	/**
	 * @param airType the airType to set
	 */
	public void setAirType(String airType) {
		this.airType = airType;
	}

	/**
	 * @return the capability
	 */
	public String getCapability() {
		return capability;
	}

	/**
	 * @param capability the capability to set
	 */
	public void setCapability(String capability) {
		this.capability = capability;
	}

	/**
	 * @return the emsFrequency
	 */
	public String getEmsFrequency() {
		return emsFrequency;
	}

	/**
	 * @param emsFrequency the emsFrequency to set
	 */
	public void setEmsFrequency(String emsFrequency) {
		this.emsFrequency = emsFrequency;
	}

	/**
	 * @return the lifeSupport
	 */
	public Boolean getLifeSupport() {
		return lifeSupport;
	}

	/**
	 * @param lifeSupport the lifeSupport to set
	 */
	public void setLifeSupport(Boolean lifeSupport) {
		this.lifeSupport = lifeSupport;
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
