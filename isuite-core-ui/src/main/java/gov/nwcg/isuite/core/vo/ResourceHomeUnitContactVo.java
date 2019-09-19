package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.ResourceHomeUnitContact;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceHomeUnitContactImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class ResourceHomeUnitContactVo extends AbstractVo implements PersistableVo {
	private IncidentResourceVo incidentResourceVo;
//	private String lastName;
//	private String firstName;
	private String contactName;
	private OrganizationVo unitVo;
	private AddressVo addressVo;
	private String phone;
	private String email;
	
	public ResourceHomeUnitContactVo(){
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static ResourceHomeUnitContactVo getInstance(ResourceHomeUnitContact entity, boolean cascadable) throws Exception {
		ResourceHomeUnitContactVo vo = new ResourceHomeUnitContactVo();
		
		if(null != entity) {
			vo.setId(entity.getId());
			
			if(cascadable){
				if(null != entity.getIncidentResource()){
					vo.setIncidentResourceVo(IncidentResourceVo.getInstance(entity.getIncidentResource(), true));
				}
//				vo.setLastName(entity.getLastName());
//				vo.setFirstName(entity.getFirstName());
				vo.setContactName(entity.getContactName());
				if(null != entity.getUnit()) {
					vo.setUnitVo(OrganizationVo.getInstance(entity.getUnit(), true));
				}
				if(null != entity.getAddress()){
					vo.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
				}
				vo.setPhone(entity.getPhone());
				vo.setEmail(entity.getEmail());
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
	public static Collection<ResourceHomeUnitContactVo> getInstances(Collection<ResourceHomeUnitContact> entities, boolean cascadable) throws Exception {
		Collection<ResourceHomeUnitContactVo> vos = new ArrayList<ResourceHomeUnitContactVo>();
		
		for(ResourceHomeUnitContact entity : entities){
			vos.add(ResourceHomeUnitContactVo.getInstance(entity, cascadable));
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
	public static ResourceHomeUnitContact toEntity(ResourceHomeUnitContact entity, ResourceHomeUnitContactVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new ResourceHomeUnitContactImpl();
		
		if(vo.getId()==0){
			entity.setId(null);
		}else{
			entity.setId(vo.getId());
		}
		
		if(cascadable){
			IncidentResource incidentResourceEntity=(IncidentResource)getPersistableObject(persistables,IncidentResourceImpl.class);
			if(null != incidentResourceEntity)
				entity.setIncidentResource(incidentResourceEntity);
			
//			entity.setLastName(vo.getLastName());
//			entity.setFirstName(vo.getFirstName());
			entity.setContactName(vo.getContactName());
			
			if(null != vo.getUnitVo())
				entity.setUnit(OrganizationVo.toEntity(null, vo.getUnitVo(), false));
			
			if(null != vo.getAddressVo())
				entity.setAddress(AddressVo.toEntity(null, vo.getAddressVo(), true));
			
			entity.setPhone(vo.getPhone());
			entity.setEmail(vo.getEmail());
			
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
	public static Collection<ResourceHomeUnitContact> toEntityList(Collection<ResourceHomeUnitContactVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<ResourceHomeUnitContact> entities = new ArrayList<ResourceHomeUnitContact>();
		
		for(ResourceHomeUnitContactVo vo : vos){
			entities.add(ResourceHomeUnitContactVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	

	/**
	 * @param incidentResourceVo the incidentResourceVo to set
	 */
	public void setIncidentResourceVo(IncidentResourceVo incidentResourceVo) {
		this.incidentResourceVo = incidentResourceVo;
	}

	/**
	 * @return the incidentResourceVo
	 */
	public IncidentResourceVo getIncidentResourceVo() {
		return incidentResourceVo;
	}

	/**
	 * @param unitVo the unitVo to set
	 */
	public void setUnitVo(OrganizationVo unitVo) {
		this.unitVo = unitVo;
	}

	/**
	 * @return the unitVo
	 */
	public OrganizationVo getUnitVo() {
		return unitVo;
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
	 * @param contactName the contactName to set
	 */
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	/**
	 * @return the contactName
	 */
	public String getContactName() {
		return contactName;
	}
	
	

}
