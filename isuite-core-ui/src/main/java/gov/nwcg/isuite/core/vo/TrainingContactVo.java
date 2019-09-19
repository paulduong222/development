package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.TrainingContact;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.TrainingContactImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

public class TrainingContactVo extends AbstractVo {
	
	private String phone;
	private String email;
	private AddressVo addressVo;
//	private IncidentResourceVo incidentResourceVo;
	
	private Long incidentResourceId;
	private String requestNumber;
	private String resourceName;
	private String itemCode;
	private String itemDescription;
	private String status;
	private Boolean active;
	private String unitId;
	private String unitDescription;
	
	
	public TrainingContactVo() {
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static TrainingContactVo getInstance(TrainingContact entity, boolean cascadable) throws Exception {
		TrainingContactVo vo = new TrainingContactVo();
		
		if(null == entity)
			throw new Exception("Unable to create TrainingContactVo from null Complexity entity.");
		
		vo.setId(entity.getId());
		
		if(cascadable){
			vo.setPhone(entity.getPhone());
			vo.setEmail(entity.getEmail());
			vo.setActive(StringBooleanEnum.toBooleanValue(entity.getActive()));
			
			if(null != entity.getAddress()) 
				vo.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
			
//			if(null != entity.getIncidentResource())
//				vo.setIncidentResourceVo(IncidentResourceVo.getInstance(entity.getIncidentResource(), true));
			
			if(null != entity.getIncidentResource()){
				IncidentResourceVo irVo = IncidentResourceVo.getInstance(entity.getIncidentResource(), true);
				
				vo.setIncidentResourceId(irVo.getId());
				vo.setRequestNumber(irVo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber());
				vo.setResourceName(irVo.getResourceVo().getLastName() + ", " + irVo.getResourceVo().getFirstName()); 
				vo.setItemCode(irVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getCode());
				vo.setItemDescription(irVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo().getDescription());
				vo.setStatus(irVo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
				vo.setUnitId(irVo.getResourceVo().getOrganizationVo().getUnitCode());
				vo.setUnitDescription(irVo.getResourceVo().getOrganizationVo().getName());
			}
		}
		
		return vo;
	}
	
	/**
	 * @param entities
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static Collection<TrainingContactVo> getInstances(Collection<TrainingContact> entities, boolean cascadable) throws Exception {
		Collection<TrainingContactVo> vos = new ArrayList<TrainingContactVo>();
		
		for(TrainingContact entity : entities){
			vos.add(TrainingContactVo.getInstance(entity, cascadable));
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
	public static TrainingContact toEntity(TrainingContact entity, TrainingContactVo vo, boolean cascadable,Persistable...persistables) throws Exception {
		if(null == entity) {
			entity = new TrainingContactImpl();
		}
		
		entity.setId(vo.getId());
		
		if(cascadable){
			IncidentResource incidentResourceEntity=(IncidentResource)getPersistableObject(persistables,IncidentResourceImpl.class);
			if(null != incidentResourceEntity)
				entity.setIncidentResource(incidentResourceEntity);
			
			entity.setPhone(vo.getPhone());
			entity.setEmail(vo.getEmail());
			entity.setActive(StringBooleanEnum.toEnumValue(vo.getActive()));
			
			if(null != vo.getAddressVo())
				entity.setAddress(AddressVo.toEntity(vo.getAddressVo(), true));
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
	public static Collection<TrainingContact> toEntityList(Collection<TrainingContactVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<TrainingContact> entities = new ArrayList<TrainingContact>();
		
		for(TrainingContactVo vo : vos){
			entities.add(TrainingContactVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
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
	 * @param addressVo the addressVo to set
	 */
	public void setAddressVo(AddressVo addressVo) {
		this.addressVo = addressVo;
	}

	/**
	 * @return the addressVo
	 */
	public AddressVo getAddressVo() {
		return addressVo;
	}

	/**
	 * @param requestNumber the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * @return the requestNumber
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * @param resourceName the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param itemCode the itemCode to set
	 */
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	/**
	 * @return the itemCode
	 */
	public String getItemCode() {
		return itemCode;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param active the active to set
	 */
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @return the active
	 */
	public Boolean getActive() {
		return active;
	}

	/**
	 * @param unitId the unitId to set
	 */
	public void setUnitId(String unitId) {
		this.unitId = unitId;
	}

	/**
	 * @return the unitId
	 */
	public String getUnitId() {
		return unitId;
	}

	/**
	 * @param unitDescription the unitDescription to set
	 */
	public void setUnitDescription(String unitDescription) {
		this.unitDescription = unitDescription;
	}

	/**
	 * @return the unitDescription
	 */
	public String getUnitDescription() {
		return unitDescription;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param itemDescription the itemDescription to set
	 */
	public void setItemDescription(String itemDescription) {
		this.itemDescription = itemDescription;
	}

	/**
	 * @return the itemDescription
	 */
	public String getItemDescription() {
		return itemDescription;
	}

	/**
	 * @param incidentResourceVo the incidentResourceVo to set
	 */
//	public void setIncidentResourceVo(IncidentResourceVo incidentResourceVo) {
//		this.incidentResourceVo = incidentResourceVo;
//	}

	/**
	 * @return the incidentResourceVo
	 */
//	public IncidentResourceVo getIncidentResourceVo() {
//		return incidentResourceVo;
//	}

}
