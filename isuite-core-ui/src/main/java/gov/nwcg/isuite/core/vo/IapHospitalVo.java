package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapHospital;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.core.domain.impl.IapHospitalImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapHospitalVo extends AbstractVo implements PersistableVo {
	private Long iapForm206Id;
	private String name;
	private AddressVo addressVo;
	private String latitude;
	private String longitude;
	private String vhf;
	private String airTravelTime;
	private String groundTravelTime;
	private String phone;
	private String levelOfCare;
	private Boolean trauma;
	private Boolean helipad;
	private Boolean burnCenter;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	
	
	public IapHospitalVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapHospitalVo getInstance(IapHospital entity, boolean cascadable) throws Exception {
		IapHospitalVo vo = new IapHospitalVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapHospitalVo from null IapHospital entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapForm206Id(entity.getIapForm206Id());
			
			if ( null != entity.getIapForm206()) {
				vo.setIapForm206Id(entity.getIapForm206().getId());
			}
			vo.setName(entity.getName());
			if(null != entity.getAddress())
				vo.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
			vo.setLatitude(entity.getLatitude());
			vo.setLongitude(entity.getLongitude());
			vo.setAirTravelTime(entity.getAirTravelTime());
			vo.setGroundTravelTime(entity.getGroundTravelTime());
			vo.setVhf(entity.getVhf());
			vo.setPhone(entity.getPhone());
			vo.setLevelOfCare(entity.getLevelOfCare());
			vo.setTrauma(entity.getTrauma().getValue());
			vo.setHelipad(entity.getHelipad().getValue());
			vo.setBurnCenter(entity.getBurnCenter().getValue());
			
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
	public static Collection<IapHospitalVo> getInstances(Collection<IapHospital> entities, boolean cascadable) throws Exception {
		Collection<IapHospitalVo> vos = new ArrayList<IapHospitalVo>();
		
		for(IapHospital entity : entities) {
			vos.add(IapHospitalVo.getInstance(entity, cascadable));
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
	public static IapHospital toEntity(IapHospital entity, IapHospitalVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapHospitalImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setName(vo.getName());
			entity.setAddress(AddressVo.toEntity(vo.getAddressVo(), true));
			entity.setLatitude(vo.getLatitude());
			entity.setLongitude(vo.getLongitude());
			entity.setAirTravelTime(vo.getAirTravelTime());
			entity.setGroundTravelTime(vo.getGroundTravelTime());
			entity.setPhone(vo.getPhone());
			entity.setVhf(vo.getVhf());
			entity.setLevelOfCare(vo.getLevelOfCare());
			entity.setTrauma(StringBooleanEnum.toEnumValue(vo.getTrauma()));
			entity.setHelipad(StringBooleanEnum.toEnumValue(vo.getHelipad()));
			entity.setBurnCenter(StringBooleanEnum.toEnumValue(vo.getBurnCenter()));
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			if ( BooleanUtility.isTrue(vo.getIsBlankLine()))
				entity.setName("");

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
	public static Collection<IapHospital> toEntityList(Collection<IapHospitalVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapHospital> entities = new ArrayList<IapHospital>();
		
		for(IapHospitalVo vo : vos) {
			entities.add(IapHospitalVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
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
	 * @param airTravelTime the airTravelTime to set
	 */
	public void setAirTravelTime(String airTravelTime) {
		this.airTravelTime = airTravelTime;
	}

	/**
	 * @return the airTravelTime
	 */
	public String getAirTravelTime() {
		return airTravelTime;
	}

	/**
	 * @param groundTravelTime the groundTravelTime to set
	 */
	public void setGroundTravelTime(String groundTravelTime) {
		this.groundTravelTime = groundTravelTime;
	}

	/**
	 * @return the groundTravelTime
	 */
	public String getGroundTravelTime() {
		return groundTravelTime;
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
	 * @param trauma the trauma to set
	 */
	public void setTrauma(Boolean trauma) {
		this.trauma = trauma;
	}

	/**
	 * @return the trauma
	 */
	public Boolean getTrauma() {
		return trauma;
	}

	/**
	 * @param helipad the helipad to set
	 */
	public void setHelipad(Boolean helipad) {
		this.helipad = helipad;
	}

	/**
	 * @return the helipad
	 */
	public Boolean getHelipad() {
		return helipad;
	}

	/**
	 * @param burnCenter the burnCenter to set
	 */
	public void setBurnCenter(Boolean burnCenter) {
		this.burnCenter = burnCenter;
	}

	/**
	 * @return the burnCenter
	 */
	public Boolean getBurnCenter() {
		return burnCenter;
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
	 * @return the latitude
	 */
	public String getLatitude() {
		return latitude;
	}

	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	/**
	 * @return the longitude
	 */
	public String getLongitude() {
		return longitude;
	}

	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	/**
	 * @return the vhf
	 */
	public String getVhf() {
		return vhf;
	}

	/**
	 * @param vhf the vhf to set
	 */
	public void setVhf(String vhf) {
		this.vhf = vhf;
	}

	/**
	 * @return the levelOfCare
	 */
	public String getLevelOfCare() {
		return levelOfCare;
	}

	/**
	 * @param levelOfCare the levelOfCare to set
	 */
	public void setLevelOfCare(String levelOfCare) {
		this.levelOfCare = levelOfCare;
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
