package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.IapRemoteCampLocations;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.core.domain.impl.IapRemoteCampLocationsImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapRemoteCampLocationsVo extends AbstractVo implements PersistableVo {
	private Long iapForm206Id;
	private String name;
	private String location;
	private String pointOfContact;
	private String emsResponders;
	private String capability;
	private String ambAirEta;
	private String ambGroundEta;
	private String approvedHelispot;
	private String latitude;
	private String longitude;
	private String emergencyChannel;
	private String availableEquipment;
	private Boolean isBlankLine=false;
	private Integer positionNum;

	
	/**
	 * Constructor
	 */
	public IapRemoteCampLocationsVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapRemoteCampLocationsVo getInstance(IapRemoteCampLocations entity, boolean cascadable) throws Exception {
		IapRemoteCampLocationsVo vo = new IapRemoteCampLocationsVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapRemoteCampLocationsVo from null IapRemoteCampLocations entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapForm206Id(entity.getIapForm206Id());
			if ( null != entity.getIapForm206()) {
				vo.setIapForm206Id(entity.getIapForm206().getId());
			}
			vo.setAmbAirEta(entity.getAmbAirEta());
			vo.setAmbGroundEta(entity.getAmbGroundEta());
			vo.setApprovedHelispot(entity.getApprovedHelispot());
			vo.setAvailableEquipment(entity.getAvailableEquipment());
			vo.setCapability(entity.getCapability());
			vo.setEmergencyChannel(entity.getEmergencyChannel());
			vo.setEmsResponders(entity.getEmsResponders());
			vo.setLatitude(entity.getLatitude());
			vo.setLongitude(entity.getLongitude());
			vo.setLocation(entity.getLocation());
			vo.setName(entity.getName());
			vo.setPointOfContact(entity.getPointOfContact());
			
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
	public static Collection<IapRemoteCampLocationsVo> getInstances(Collection<IapRemoteCampLocations> entities, boolean cascadable) throws Exception {
		Collection<IapRemoteCampLocationsVo> vos = new ArrayList<IapRemoteCampLocationsVo>();
		
		for(IapRemoteCampLocations entity : entities) {
			vos.add(IapRemoteCampLocationsVo.getInstance(entity, cascadable));
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
	public static IapRemoteCampLocations toEntity(IapRemoteCampLocations entity, IapRemoteCampLocationsVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapRemoteCampLocationsImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setAmbAirEta(StringUtility.toUpper(vo.getAmbAirEta()));
			entity.setAmbGroundEta(StringUtility.toUpper(vo.getAmbGroundEta()));
			entity.setApprovedHelispot(StringUtility.toUpper(vo.getApprovedHelispot()));
			entity.setAvailableEquipment(StringUtility.toUpper(vo.getAvailableEquipment()));
			entity.setCapability(StringUtility.toUpper(vo.getCapability()));
			entity.setEmergencyChannel(StringUtility.toUpper(vo.getEmergencyChannel()));
			entity.setEmsResponders(StringUtility.toUpper(vo.getEmsResponders()));
			entity.setLatitude(StringUtility.toUpper(vo.getLatitude()));
			entity.setLongitude(StringUtility.toUpper(vo.getLongitude()));
			entity.setLocation(StringUtility.toUpper(vo.getLocation()));
			entity.setName(StringUtility.toUpper(vo.getName()));
			entity.setPointOfContact(StringUtility.toUpper(vo.getPointOfContact()));
			
			if(IntegerUtility.hasValue(vo.getPositionNum()))
				entity.setPositionNum(vo.getPositionNum());
			else
				entity.setPositionNum(new Integer(0));

			entity.setIsBlankLine(StringBooleanEnum.toEnumValue(vo.getIsBlankLine()));
			if ( BooleanUtility.isTrue(vo.getIsBlankLine()))
				entity.setName("");

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
	public static Collection<IapRemoteCampLocations> toEntityList(Collection<IapRemoteCampLocationsVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapRemoteCampLocations> entities = new ArrayList<IapRemoteCampLocations>();
		
		for(IapRemoteCampLocationsVo vo : vos) {
			entities.add(IapRemoteCampLocationsVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
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
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @return the pointOfContact
	 */
	public String getPointOfContact() {
		return pointOfContact;
	}

	/**
	 * @param pointOfContact the pointOfContact to set
	 */
	public void setPointOfContact(String pointOfContact) {
		this.pointOfContact = pointOfContact;
	}

	/**
	 * @return the emsResponders
	 */
	public String getEmsResponders() {
		return emsResponders;
	}

	/**
	 * @param emsResponders the emsResponders to set
	 */
	public void setEmsResponders(String emsResponders) {
		this.emsResponders = emsResponders;
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
	 * @return the ambAirEta
	 */
	public String getAmbAirEta() {
		return ambAirEta;
	}

	/**
	 * @param ambAirEta the ambAirEta to set
	 */
	public void setAmbAirEta(String ambAirEta) {
		this.ambAirEta = ambAirEta;
	}

	/**
	 * @return the ambGroundEta
	 */
	public String getAmbGroundEta() {
		return ambGroundEta;
	}

	/**
	 * @param ambGroundEta the ambGroundEta to set
	 */
	public void setAmbGroundEta(String ambGroundEta) {
		this.ambGroundEta = ambGroundEta;
	}

	/**
	 * @return the approvedHelispot
	 */
	public String getApprovedHelispot() {
		return approvedHelispot;
	}

	/**
	 * @param approvedHelispot the approvedHelispot to set
	 */
	public void setApprovedHelispot(String approvedHelispot) {
		this.approvedHelispot = approvedHelispot;
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
	 * @return the emergencyChannel
	 */
	public String getEmergencyChannel() {
		return emergencyChannel;
	}

	/**
	 * @param emergencyChannel the emergencyChannel to set
	 */
	public void setEmergencyChannel(String emergencyChannel) {
		this.emergencyChannel = emergencyChannel;
	}

	/**
	 * @return the availableEquipment
	 */
	public String getAvailableEquipment() {
		return availableEquipment;
	}

	/**
	 * @param availableEquipment the availableEquipment to set
	 */
	public void setAvailableEquipment(String availableEquipment) {
		this.availableEquipment = availableEquipment;
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
