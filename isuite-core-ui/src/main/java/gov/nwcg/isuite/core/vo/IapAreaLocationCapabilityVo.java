package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.IapAreaLocationCapability;
import gov.nwcg.isuite.core.domain.IapForm206;
import gov.nwcg.isuite.core.domain.impl.IapAreaLocationCapabilityImpl;
import gov.nwcg.isuite.core.domain.impl.IapForm206Impl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.IntegerUtility;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;

public class IapAreaLocationCapabilityVo extends AbstractVo implements PersistableVo {
	private Long iapForm206Id;
	private String divisionName;
	private String branchName;
	private String groupName;
	private String emsResponders;
	private String capability;
	private String ambAirEta;
	private String ambGroundEta;
	private String approvedHelispot;
	private String latitude;
	private String longitude;
	private String availEquipment;
	private String emergencyChannel;
	private Boolean isBlankLine=false;
	private Integer positionNum;
	
	/**
	 * Constructor
	 */
	public IapAreaLocationCapabilityVo() {
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static IapAreaLocationCapabilityVo getInstance(IapAreaLocationCapability entity, boolean cascadable) throws Exception {
		IapAreaLocationCapabilityVo vo = new IapAreaLocationCapabilityVo();
		
		if(null == entity)
			throw new Exception("Unable to create IapAreaLocationCapabilityVo from null IapAreaLocationCapability entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable) {
			vo.setIapForm206Id(entity.getIapForm206Id());
			if ( null != entity.getIapForm206()) {
				vo.setIapForm206Id(entity.getIapForm206().getId());
			}
			vo.setAmbAirEta(entity.getAmbAirEta());
			vo.setAmbGroundEta(entity.getAmbGroundEta());
			vo.setApprovedHelispot(entity.getApprovedHelispot());
			vo.setBranchName(entity.getBranchName());
			vo.setCapability(entity.getCapability());
			vo.setDivisionName(entity.getDivisionName());
			vo.setEmsResponders(entity.getEmsResponders());
			vo.setGroupName(entity.getGroupName());
			vo.setLatitude(entity.getLatitude());
			vo.setLongitude(entity.getLongitude());
			vo.setEmergencyChannel(entity.getEmergencyChannel());
			vo.setAvailEquipment(entity.getAvailableEquipment());
			
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
	public static Collection<IapAreaLocationCapabilityVo> getInstances(Collection<IapAreaLocationCapability> entities, boolean cascadable) throws Exception {
		Collection<IapAreaLocationCapabilityVo> vos = new ArrayList<IapAreaLocationCapabilityVo>();
		
		for(IapAreaLocationCapability entity : entities) {
			vos.add(IapAreaLocationCapabilityVo.getInstance(entity, cascadable));
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
	public static IapAreaLocationCapability toEntity(IapAreaLocationCapability entity, IapAreaLocationCapabilityVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new IapAreaLocationCapabilityImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable) {
			entity.setAmbAirEta(StringUtility.toUpper(vo.getAmbAirEta()));
			entity.setAmbGroundEta(StringUtility.toUpper(vo.getAmbGroundEta()));
			entity.setApprovedHelispot(StringUtility.toUpper(vo.getApprovedHelispot()));
			entity.setBranchName(StringUtility.toUpper(vo.getBranchName()));
			entity.setCapability(StringUtility.toUpper(vo.getCapability()));
			entity.setDivisionName(StringUtility.toUpper(vo.getDivisionName()));
			entity.setEmsResponders(StringUtility.toUpper(vo.getEmsResponders()));
			entity.setGroupName(StringUtility.toUpper(vo.getGroupName()));
			entity.setLatitude(StringUtility.toUpper(vo.getLatitude()));
			entity.setLongitude(StringUtility.toUpper(vo.getLongitude()));
			entity.setEmergencyChannel(StringUtility.toUpper(vo.getEmergencyChannel()));
			entity.setAvailableEquipment(StringUtility.toUpper(vo.getAvailEquipment()));
			
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
				entity.setBranchName("");
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
	public static Collection<IapAreaLocationCapability> toEntityList(Collection<IapAreaLocationCapabilityVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<IapAreaLocationCapability> entities = new ArrayList<IapAreaLocationCapability>();
		
		for(IapAreaLocationCapabilityVo vo : vos) {
			entities.add(IapAreaLocationCapabilityVo.toEntity(null, vo, cascadable, persistables));
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
	 * @return the divisionName
	 */
	public String getDivisionName() {
		return divisionName;
	}

	/**
	 * @param divisionName the divisionName to set
	 */
	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	/**
	 * @return the branchName
	 */
	public String getBranchName() {
		return branchName;
	}

	/**
	 * @param branchName the branchName to set
	 */
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	/**
	 * @return the groupName
	 */
	public String getGroupName() {
		return groupName;
	}

	/**
	 * @param groupName the groupName to set
	 */
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
	 * @return the availEquipment
	 */
	public String getAvailEquipment() {
		return availEquipment;
	}

	/**
	 * @param availEquipment the availEquipment to set
	 */
	public void setAvailEquipment(String availEquipment) {
		this.availEquipment = availEquipment;
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
