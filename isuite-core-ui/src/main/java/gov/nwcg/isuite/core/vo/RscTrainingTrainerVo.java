package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceTraining;
import gov.nwcg.isuite.core.domain.RscTrainingTrainer;
import gov.nwcg.isuite.core.domain.impl.IncidentResourceImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceTrainingImpl;
import gov.nwcg.isuite.core.domain.impl.RscTrainingTrainerImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;

public class RscTrainingTrainerVo extends AbstractVo implements PersistableVo {
	
	private ResourceTrainingVo resourceTrainingVo;
	private AddressVo addressVo;
	private KindVo kindVo;
//	private String lastName;
//	private String firstName;
	private String requestNumber;
	private OrganizationVo unitVo;
	private String email;
	private String phone;
	private String resourceName;
	private String status;
	private IncidentResourceVo incidentResourceVo;
	
	
	public RscTrainingTrainerVo(){
		super();
	}
	
	/**
	 * @param entity
	 * @param cascadable
	 * @return
	 * @throws Exception
	 */
	public static RscTrainingTrainerVo getInstance(RscTrainingTrainer entity, boolean cascadable) throws Exception {
		RscTrainingTrainerVo vo = new RscTrainingTrainerVo();
		
		if(null != entity) {
			vo.setId(entity.getId());
			
			if(cascadable){
				if(null != entity.getResourceTraining()){
//					vo.setResourceTrainingVo(ResourceTrainingVo.getInstance(entity.getResourceTraining(), true));
				}
				
				if(null != entity.getIncidentResource()){
					vo.setIncidentResourceVo(IncidentResourceVo.getInstance(entity.getIncidentResource(), true));
					
					vo.setResourceName(vo.getIncidentResourceVo().getResourceVo().getLastName() + ", " +  vo.getIncidentResourceVo().getResourceVo().getFirstName());
					
					vo.setRequestNumber(vo.getIncidentResourceVo().getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber());
					
					vo.setKindVo(vo.getIncidentResourceVo().getWorkPeriodVo().getCurrentAssignmentVo().getKindVo());
					
					vo.setStatus(vo.getIncidentResourceVo().getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
					
					vo.setUnitVo(vo.getIncidentResourceVo().getResourceVo().getOrganizationVo());
					
					
				}else {
					if(null != entity.getKind())
						vo.setKindVo(KindVo.getInstance(entity.getKind(), true));

					if(null != entity.getUnit()) {
						vo.setUnitVo(OrganizationVo.getInstance(entity.getUnit(), true));
					}
					
					vo.setResourceName(entity.getResourceName());
				}
				
				if(null != entity.getAddress()){
					vo.setAddressVo(AddressVo.getInstance(entity.getAddress(), true));
				}
				
				vo.setEmail(entity.getEmail());
				vo.setPhone(entity.getPhone());
				
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
	public static Collection<RscTrainingTrainerVo> getInstances(Collection<RscTrainingTrainer> entities, boolean cascadable) throws Exception {
		Collection<RscTrainingTrainerVo> vos = new ArrayList<RscTrainingTrainerVo>();
		
		for(RscTrainingTrainer entity : entities){
			vos.add(RscTrainingTrainerVo.getInstance(entity, cascadable));
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
	public static RscTrainingTrainer toEntity(RscTrainingTrainer entity, RscTrainingTrainerVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity) entity = new RscTrainingTrainerImpl();
		
		entity.setId(vo.getId());
		
		if(cascadable){
			if(null != vo.getResourceTrainingVo()){
//				entity.setResourceTraining(ResourceTrainingVo.toEntity(null, vo.getResourceTrainingVo(), false));
				ResourceTraining rtEntity = new ResourceTrainingImpl();
				rtEntity.setId(vo.getResourceTrainingVo().getId());
				entity.setResourceTraining(rtEntity);
			}
			
			IncidentResource incidentResourceEntity=(IncidentResource)getPersistableObject(persistables,IncidentResourceImpl.class);
			if(null != incidentResourceEntity)
				entity.setIncidentResource(incidentResourceEntity);
			
			if(null != vo.getAddressVo()){
				entity.setAddress(AddressVo.toEntity(null, vo.getAddressVo(), true));
			}
			
			if(null != vo.getUnitVo())
				entity.setUnit(OrganizationVo.toEntity(null, vo.getUnitVo(), false));
			
			if(null != vo.getKindVo())
				entity.setKind(KindVo.toEntity(null, vo.getKindVo(), false));
			
			entity.setReqestNumber(vo.getRequestNumber());
//			entity.setHomeUnit(vo.getHomeUnit());
			entity.setEmail(vo.getEmail());
			entity.setPhone(vo.getPhone());
			entity.setResourceName(vo.getResourceName());
		}
		
		return entity;
	}
	
	public static Collection<RscTrainingTrainerVo> getInstancesIncRes(Collection<IncidentResource> entities) throws Exception {
		
		Collection<RscTrainingTrainerVo> vos = new ArrayList<RscTrainingTrainerVo>();
		
		for(IncidentResource entity : entities){
			vos.add(RscTrainingTrainerVo.getInstanceIncRes(entity));
		}
		
		return vos;
	}
	
	public static RscTrainingTrainerVo getInstanceIncRes(IncidentResource entity) throws Exception {
		RscTrainingTrainerVo vo = new RscTrainingTrainerVo();
		
		if(null != entity) {
			
			vo.setIncidentResourceVo(IncidentResourceVo.getInstance(entity, false));
			
//			IncidentResourceVo irVo = IncidentResourceVo.getInstance(entity, true);
//			vo.setRequestNumber(irVo.getWorkPeriodVo().getCurrentAssignmentVo().getRequestNumber());
//			vo.setResourceName(irVo.getResourceVo().getLastName() + ", " + irVo.getResourceVo().getFirstName());
//			vo.setKindVo(irVo.getWorkPeriodVo().getCurrentAssignmentVo().getKindVo());
//			vo.setUnitVo(irVo.getResourceVo().getOrganizationVo());
//			vo.setStatus(irVo.getWorkPeriodVo().getCurrentAssignmentVo().getAssignmentStatusVo().getCode());
			
			//Get current assignment
			Assignment assignment = null;
			for (Assignment assign : entity.getWorkPeriod().getAssignments()){
				if(null == assign.getEndDate()) {
					assignment = assign;
				}
			}
			
			vo.setRequestNumber(assignment.getRequestNumber());
			if(null != assignment.getKind()) {
				vo.setKindVo(KindVo.getInstance(assignment.getKind(), true));
			}
			
			if(null != assignment.getAssignmentStatus()) {
				vo.setStatus(assignment.getAssignmentStatus().name());
			}
			
			Resource resource = entity.getResource();
			vo.setResourceName(resource.getLastName() + ", " + resource.getFirstName());
			if(null != resource.getOrganization()){
				vo.setUnitVo(OrganizationVo.getInstance(resource.getOrganization(), true));
			}
			
//			if(null != entity.getRscTrainingTrainer()) {
//				vo.setId(entity.getRscTrainingTrainer().getId());
//				vo.setPhone(entity.getRscTrainingTrainer().getPhone());
//				vo.setEmail(entity.getRscTrainingTrainer().getEmail());
//				
//				if(null != entity.getRscTrainingTrainer().getAddress()) {
//					vo.setAddressVo(AddressVo.getInstance(entity.getRscTrainingTrainer().getAddress(), true));
//				}
//			}
		}

		return vo;
	}
	
	/**
	 * @param vos
	 * @param cascadable
	 * @param persistables
	 * @return
	 * @throws Exception
	 */
	public static Collection<RscTrainingTrainer> toEntityList(Collection<RscTrainingTrainerVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<RscTrainingTrainer> entities = new ArrayList<RscTrainingTrainer>();
		
		for(RscTrainingTrainerVo vo : vos){
			entities.add(RscTrainingTrainerVo.toEntity(null, vo, cascadable, persistables));
		}
		
		return entities;
	}
	

	/**
	 * @param resourceTrainingVo the resourceTrainingVo to set
	 */
	public void setResourceTrainingVo(ResourceTrainingVo resourceTrainingVo) {
		this.resourceTrainingVo = resourceTrainingVo;
	}

	/**
	 * @return the resourceTrainingVo
	 */
	public ResourceTrainingVo getResourceTrainingVo() {
		return resourceTrainingVo;
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
	 * @param lastName the lastName to set
	 */
//	public void setLastName(String lastName) {
//		this.lastName = lastName;
//	}

	/**
	 * @return the lastName
	 */
//	public String getLastName() {
//		return lastName;
//	}

	/**
	 * @param firstName the firstName to set
	 */
//	public void setFirstName(String firstName) {
//		this.firstName = firstName;
//	}

	/**
	 * @return the firstName
	 */
//	public String getFirstName() {
//		return firstName;
//	}

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
	 * @param kindVo the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * @return the kindVo
	 */
	public KindVo getKindVo() {
		return kindVo;
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

}
