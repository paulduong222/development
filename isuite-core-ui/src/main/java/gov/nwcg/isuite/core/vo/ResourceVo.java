package gov.nwcg.isuite.core.vo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Agency;
import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.core.domain.ResourceKind;
import gov.nwcg.isuite.core.domain.impl.AgencyImpl;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.domain.impl.ResourceImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;
import gov.nwcg.isuite.framework.types.EmploymentTypeEnum;
import gov.nwcg.isuite.framework.types.ResourceClassificationEnum;
import gov.nwcg.isuite.framework.types.ResourceStatusTypeEnum;
import gov.nwcg.isuite.framework.util.StringUtility;
import gov.nwcg.isuite.framework.util.Validator;

/**
 * @author bsteiner
 */
public class ResourceVo extends AbstractVo implements PersistableVo {
	private String resourceName;
	private String firstName;
	private String lastName;
	private ResourceVo parentResourceVo;
	private ResourceVo permanentResourceVo;
	private Long parentResourceId;
	private Long permanentResourceId;
	private Collection<ResourceVo> childResourceVos = null;
	private Collection<IncidentResourceVo> incidentResourceVos = null;
	private OrganizationVo organizationVo;
	private Long organizationId;
	private OrganizationVo primaryDispatchCenterVo;
	private Long primaryDispatchCenterId;
	private Collection<ResourceMobilizationVo> resourceMobilizationVos = null;
	private Collection<IncidentVo> incidentVos;
	private ResourceClassificationEnum resourceClassification;
	private ResourceStatusTypeEnum resourceStatus;
	private AgencyVo agencyVo;
	private Long agencyId;
	private Boolean person;
	private Boolean contracted;
	private Boolean leader;
	private String nameOnPictureId;
	private String contactName;
	private String phone;
	private String email;
	private String other1;
	private String other2;
	private String other3;
	private Boolean enabled;
	private Boolean permanent;
	private Collection<ResourceKindVo> resourceKindVos = null;
	private Date deletedDate;
	private Long numberOfPersonnel;
	private ResourceClassificationVo resourceClassificationVo;
	private Integer leaderType;
	private Boolean component;
	private Collection<ResourceKindVo> otherQuals;
	private ResourceKindVo primaryQual;
	private EmploymentTypeEnum employmentType;
	private ContractorVo contractorVo;
	private ContractorAgreementVo contractorAgreementVo;
	private String rossGroupAssignment;
	
	private Long rossResId;
	// the kindVo property is only for convenience access to the primary item
	// code
	// for the resource. all item codes are normally stored in the
	// resourceKindVos
	private KindVo kindVo;

	public ResourceVo() {
		super();
	}

	/**
	 * Returns a ResourceVo instance from a Resource entity.
	 * 
	 * @param entity
	 *            the source Resource entity
	 * @param cascadable
	 *            flag indicating whether the vo instance should created as a
	 *            cascadable vo
	 * @param persistables
	 *            Optional array of referenced persistable entities
	 * @return
	 * @throws Exception
	 */
	public static ResourceVo getInstance(Resource entity, boolean cascadable,
			Persistable... persistables) throws Exception {
		ResourceVo vo = new ResourceVo();

		if (null == entity)
			throw new Exception(
					"Unable to create ResourceVo from null Resource entity.");

		vo.setId(entity.getId());

		/*
		 * Only populate fields outside of the entity Id if needed
		 */
		if (cascadable) {
			vo.setRossResId(entity.getRossResId());
			vo.setResourceName(StringUtility.toUpper(entity.getResourceName()));
			vo.setFirstName(StringUtility.toUpper(entity.getFirstName()));
			vo.setLastName(StringUtility.toUpper(entity.getLastName()));
			vo.setResourceClassification(entity.getResourceClassification());
			if (null != entity.getResourceClassification()) {
				Collection<ResourceClassificationVo> rcList = ResourceClassificationEnum
						.getResourceClassificationVoList();

				for (ResourceClassificationVo rcvo : rcList) {
					if (rcvo.getCode().equals(
							entity.getResourceClassification().name())) {
						vo.setResourceClassificationVo(rcvo);
					}
				}
			}
			vo.setResourceStatus(entity.getResourceStatus());
			vo.setPerson(entity.isPerson());
			vo.setContracted(entity.isContracted());
			vo.setPermanent(entity.isPermanent());
			vo.setPermanentResourceId(entity.getPermanentResourceId());
			vo.setLeader(entity.isLeader());
			vo.setNameOnPictureId(StringUtility.toUpper(entity.getNameOnPictureId()));
			vo.setContactName(StringUtility.toUpper(entity.getContactName()));
			vo.setPhone(entity.getPhone());
			vo.setEmail(entity.getEmail());
			vo.setOther1(entity.getOther1());
			vo.setOther2(entity.getOther2());
			vo.setOther3(entity.getOther3());
			vo.setEnabled(entity.isEnabled());
			vo.setDeletedDate(entity.getDeletedDate());
			vo.setNumberOfPersonnel(entity.getNumberOfPersonnel());
			vo.setLeaderType(entity.getLeaderType());
			vo.setComponent(entity.getComponent());
			vo.setEmploymentType(entity.getEmploymentType());

			vo.setOrganizationId(entity.getOrganizationId());

			if (null != entity.getOrganizationId()) {
				vo.setOrganizationVo(OrganizationVo.getInstance(entity
						.getOrganization(), true));
			}

			if (null != entity.getPrimaryDispatchCenter()) {
				vo.setPrimaryDispatchCenterVo(OrganizationVo.getInstance(entity
						.getPrimaryDispatchCenter(), true));
			}

			vo.setAgencyId(entity.getAgencyId());
			if (null != entity.getAgencyId()) {
				vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(), true));
			}
			
			if (null != entity.getContractor()) {
				vo.setContractorVo(ContractorVo.getInstance(entity.getContractor(), true));
			}
			
			if (null != entity.getContractorAgreement()) {
				vo.setContractorAgreementVo(ContractorAgreementVo.getInstance(entity.getContractorAgreement(), true));
			}

			/*
			 * Get all resources in the persistables array.
			 */
			List<Resource> persistableResources = (List<Resource>) getPersistableObjectList(
					persistables, ResourceImpl.class);

			/*
			 * Always try to set the parentResourceId.
			 * 
			 * Only set the parentResourceVo if there is a parent resource in
			 * the persistables array.
			 * 
			 * Since the Resource entity has multiple inner relationships:
			 * (resource<-->children,resource<-->permanent) get all of the
			 * resources from the persistables array and find the parent
			 * resource instance
			 */
			vo.setParentResourceId(entity.getParentResourceId());
			for (Resource r : persistableResources) {
				if (r.getId().equals(vo.getParentResourceId())) {
					/*
					 * Resource <--> ParentResource (has bidirectional
					 * relationship) The relationship from child to parent
					 * should not cascade the parent (cascadable = false).
					 */
					vo.setParentResourceVo(ResourceVo.getInstance(r, false));
				}
			}

			/*
			 * Since the Resource entity has multiple inner relationships:
			 * (resource<-->children,resource<-->permanent) get all of the
			 * resources from the persistables array and find the child resource
			 * instances. Loop through the persistableResources and determine if
			 * the resource (r)'s parentResourceId is this instance's entity id.
			 * if so, the resource (r) is a child resource
			 */
			/*
			 * List<Resource> children = new ArrayList<Resource>(); for(Resource
			 * r : persistableResources){ if(null != r.getParentResourceId()) {
			 * if(r.getParentResourceId().equals(vo.getId())){ / Resource (r) is
			 * a child to this instance
			 */
			/*
			 * children.add(r); } } }
			 */

			/*
			 * Resource <--> ChildResource (has bidirectional relationship) The
			 * relationship from parent to child should cascade the child
			 * (cascadable = true).
			 */
			if (null != entity.getChildResources()
					&& entity.getChildResources().size() > 0)
				vo.setChildResourceVos(ResourceVo.getInstances(entity
						.getChildResources(), true));

			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
// The resourceMobilization object is obtained, utilized, and cascaded through the WorkPeriodVo.  
// It if is obtained and set here as well, 
// it generates multiple, identical objects in the session and creates a hibernate error on save.
				
			/*
			 * Resource <--> ResourceMobilization (has bidirectional
			 * relationship) The relationship from resource to resource
			 * mobilization should cascade (cascadable = true).
			 */
//			if (null != entity.getResourceMobilizations())
//				vo.setResourceMobilizationVos(ResourceMobilizationVo
//						.getInstances(entity.getResourceMobilizations(), true));
///////////////////////////////////////////////////////////////////////////////////////////////////////////			
			
			

			/*
			 * Resource <--> ResourceKind (has bidirectional relationship) The
			 * relationship from resource to resourcekind should cascade
			 * (cascadable = true).
			 */
			if (null != entity.getResourceKinds()) {
				vo.setResourceKindVos(ResourceKindVo.getInstances(entity
						.getResourceKinds(), true));

				for (ResourceKindVo rkvo : vo.getResourceKindVos()) {
					if (rkvo.getPrimary()) {
						vo.setKindVo(rkvo.getKindVo());
						vo.setPrimaryQual(rkvo);
					} else {
						vo.getOtherQuals().add(rkvo);
					}
				}
			} 
			
			// todo: incidentResourceVos;
		}

		return vo;
	}

	/**
	 * Returns a collection of ResourceVo's.
	 * 
	 * @param entities
	 *            the source collection of resource entities
	 * @param cascadable
	 *            flag indicating whether the vo instances should created as
	 *            cascadable vos
	 * @param persistables
	 *            Optional array of referenced persistable entities
	 * @return collection of resourceVo's
	 * @throws Exception
	 */
	public static List<ResourceVo> getInstances(Collection<Resource> entities,
			boolean cascadable, Persistable... persistables) throws Exception {
		List<ResourceVo> vos = new ArrayList<ResourceVo>();

		for (Resource r : entities) {
			vos.add(ResourceVo.getInstance(r, cascadable, persistables));
		}

		return vos;
	}

	/**
	 * Creates and returns a Resource entity from a resource vo.
	 * 
	 * @param sourceVo
	 *            the source resourcevo
	 * @param cascadable
	 *            flag indicating whether the entity instance should created as
	 *            a cascadable entity
	 * @param persistables
	 *            Optional array of referenced persistable entities
	 * @return resource entity
	 * @throws Exception
	 */
	public static Resource toEntity(Resource entity, ResourceVo sourceVo,
			boolean cascadable, Persistable... persistables) throws Exception {
		if (null == entity)
			entity = new ResourceImpl();

		entity.setId(sourceVo.getId());

		if (cascadable) {
			entity.setRossResId(sourceVo.getRossResId());
			entity.setContracted(sourceVo.getContracted());
			entity.setEnabled(sourceVo.getEnabled());
			entity.setPerson(sourceVo.getPerson());
			entity.setLeader(sourceVo.getLeader());
			entity.setPermanent(sourceVo.getPermanent());
			entity.setResourceName(StringUtility.toUpper(sourceVo.getResourceName()));
			entity.setContactName(sourceVo.getContactName());
			entity.setEmail(sourceVo.getEmail());
			entity.setPhone(StringUtility.removeNonNumeric(sourceVo.getPhone()));
			entity.setOther1(sourceVo.getOther1());
			entity.setOther2(sourceVo.getOther2());
			entity.setOther3(sourceVo.getOther3());
			entity.setResourceStatus(sourceVo.getResourceStatus());
			if (null == sourceVo.getResourceClassification()
					|| sourceVo.getResourceClassification().name().isEmpty()) {
				entity.setResourceClassification(null);
			} else {
				entity.setResourceClassification(sourceVo
						.getResourceClassification());
			}
			entity.setFirstName(StringUtility.toUpper(sourceVo.getFirstName()));
			entity.setLastName(StringUtility.toUpper(sourceVo.getLastName()));
			entity.setNameOnPictureId(sourceVo.getNameOnPictureId());
			entity.setDeletedDate(sourceVo.getDeletedDate());
			entity.setNumberOfPersonnel(sourceVo.getNumberOfPersonnel());
			entity.setLeaderType(sourceVo.getLeaderType());
			entity.setComponent(sourceVo.getComponent());
			entity.setEmploymentType(sourceVo.getEmploymentType());

			// entity.setAgencyId(sourceVo.getAgencyId());
			// entity.setOrganizationId(sourceVo.getOrganizationId());

			// if(null != sourceVo.getAgencyId()){
			/*
			 * Resource --> Agency (one way directional relationship) Only need
			 * to setAgency with just the entity id (set cascadable false).
			 */
			if (null != sourceVo.getAgencyVo())
				if (sourceVo.getAgencyVo().getId() > 0) {
					entity.setAgency(AgencyVo.toEntity(null, sourceVo.getAgencyVo(),
							false));
				} else {
					entity.setAgency(null);
				}
			else {
				if (null != sourceVo.getAgencyId()
						&& sourceVo.getAgencyId() > 0) {
					Agency agency = new AgencyImpl();
					agency.setId(sourceVo.getAgencyId());
					entity.setAgency(agency);
				}
			}
			// }

			// if(null != sourceVo.getOrganizationId()){
			/*
			 * Resource --> Organization (one way directional relationship) Only
			 * need an Organization entity with just the entity id (set
			 * cascadable false).
			 * 
			 * //entity.setOrganization(OrganizationVo.toEntity(sourceVo.
			 * getOrganizationVo(),false));
			 */
			if (null != sourceVo.getOrganizationVo()) {
				if ((null != sourceVo.getOrganizationVo().getId())
						&& (sourceVo.getOrganizationVo().getId() > 0)) {
					entity.setOrganization(OrganizationVo.toEntity(null,
							sourceVo.getOrganizationVo(), false));
				}
			} else {
				if ((null != sourceVo.getOrganizationId())
						&& (sourceVo.getOrganizationId() > 0)) {
					Organization org = new OrganizationImpl();
					org.setId(sourceVo.getOrganizationId());
					entity.setOrganization(org);
				}
			}
			// }

			if (null != sourceVo.getPrimaryDispatchCenterVo()) {

				if ((null != sourceVo.getPrimaryDispatchCenterVo().getId())
						&& sourceVo.getPrimaryDispatchCenterVo().getId() > 0) {
					entity.setPrimaryDispatchCenter(OrganizationVo.toEntity(
									null,
									sourceVo.getPrimaryDispatchCenterVo(),
									false));
				}
			} else {

				if ((null != sourceVo.getPrimaryDispatchCenterId())
						&& (sourceVo.getPrimaryDispatchCenterId() > 0)) {
					Organization pdc = new OrganizationImpl();
					pdc.setId(sourceVo.getPrimaryDispatchCenterId());
					entity.setPrimaryDispatchCenter(pdc);
				}
			}
			
			if (null != sourceVo.getContractorVo()) {
				if ((null != sourceVo.getContractorVo().getId()) && (sourceVo.getContractorVo().getId() > 0)) {
					entity.setContractor(ContractorVo.toEntity(null,sourceVo.getContractorVo(), false));
				}
			}
			
			if (null != sourceVo.getContractorAgreementVo()) {
				if ((null != sourceVo.getContractorAgreementVo().getId()) && (sourceVo.getContractorAgreementVo().getId() > 0)) {
					entity.setContractorAgreement(ContractorAgreementVo.toEntity(null, sourceVo.getContractorAgreementVo(), false));
				}
			}

			/*
			 * Resource <--> ParentResource (bidirectional relationship) The
			 * relationship from child to parent should not cascade the child
			 * (cascadable = false).
			 */
			if ((null != sourceVo.getParentResourceId())) {
				/*
				 * if parentResourceVo is available, use parentResourceVo
				 */
				if ((null != sourceVo.getParentResourceVo())
						&& (null != sourceVo.getParentResourceVo().getId())) {
					entity.setParentResource(ResourceVo.toEntity(null, sourceVo
							.getParentResourceVo(), false));
				} else {
					if ((null != sourceVo.getParentResourceId())
							&& (sourceVo.getParentResourceId() > 0)) {
						Resource parentResource = new ResourceImpl();
						parentResource.setId(sourceVo.getParentResourceId());
						entity.setParentResource(parentResource);
					}
				}
			} else {
				/*
				 * Check the persistables array for a resource object.
				 */
				Resource parentResource = (ResourceImpl) getPersistableObject(
						persistables, ResourceImpl.class);
				if (null != parentResource)
					entity.setParentResource(parentResource);
			}

			/*
			 * Resource <--> ChildResource (bidirectional relationship) The
			 * relationship from parent to child should cascade the child
			 * (cascadable = true).
			 */
			if (null != sourceVo.getChildResourceVos()) {
				entity.getChildResources().addAll(
						ResourceVo.toEntityList(sourceVo.getChildResourceVos(),
								true, entity));
			}

			
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
// The resourceMobilization object is obtained, utilized, and cascaded through the WorkPeriodVo.  
// It if is obtained and set here as well, 
// it generates multiple, identical objects in the session and creates a hibernate error on save.
			
			/*
			 * Resource <--> ResourceMobilization (bidirectional relationship)
			 * The relationship from resource to resourcemobilization should
			 * cascade the resourcemobilizations (cascadable = true).
			 * 
			 * Pass in this entity instance as part of the referenced
			 * persistableObjects.
			 */
//			if (null != sourceVo.getResourceMobilizationVos()) {
//				entity.setResourceMobilizations(ResourceMobilizationVo
//						.toEntityList(sourceVo.getResourceMobilizationVos(),
//								true, entity));
//			}
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
			
			
			
			/*
			 * Resource <--> ResourceKind (bidirectional relationship) The
			 * relationship from resource to resourcekind should cascade the
			 * resourcekinds (cascadable = true).
			 * 
			 * Pass in this entity instance as part of the referenced
			 * persistableObjects.
			 */
			entity.getResourceKinds().clear();
			if (null != sourceVo.getResourceKindVos()) {
				Collection<ResourceKind> rks = ResourceKindVo.toEntityList(
						sourceVo.getResourceKindVos(), true, entity);
				for (ResourceKind rk : rks) {
					entity.addResourceKind(rk);
				}
				// entity.setResourceKinds(ResourceKindVo.toEntityList(sourceVo.
				// getResourceKindVos(), true, entity));
			}

			/*
			 * Check the persistables array for a workArea object.
			 */
//			WorkArea waEntity = (WorkAreaImpl) getPersistableObject(
//					persistables, WorkAreaImpl.class);
//			if (null != waEntity)
//				entity.getWorkAreas().add(waEntity);

			if (entity.isPerson()) {
				entity.setResourceName("");
			}

			/*
			 * Validate the entity
			 */
			validateEntity(entity);
		} else {
			// verify not null fields have some value (should not get persisted)
			entity.setContracted(false);
			entity.setPerson(false);
			entity.setPermanent(false);
			entity.setEnabled(false);
			entity.setLeader(false);
		}

		return entity;
	}
	
	public static Resource toResourceInventoryEntity(Resource entity, ResourceVo sourceVo,
			Persistable... persistables) throws Exception {
		if (null == entity)
			entity = new ResourceImpl();

		entity.setId(sourceVo.getId());
		entity.setResourceName(StringUtility.toUpper(sourceVo.getResourceName()));
		entity.setFirstName(StringUtility.toUpper(sourceVo.getFirstName()));
		entity.setLastName(StringUtility.toUpper(sourceVo.getLastName()));
		entity.setPhone(StringUtility.removeNonNumeric(sourceVo.getPhone()));

		if (null != sourceVo.getPrimaryQual().getKindVo()) {
			Boolean found=false;
			
			for(ResourceKind rke :  entity.getResourceKinds()) {
				if(rke.getKind().getCode().equals(sourceVo.getPrimaryQual().getKindVo().getCode())){
					found=true;
					break;
				}
			}
			
			if(!found) {
				sourceVo.getPrimaryQual().setId(null);
				sourceVo.getPrimaryQual().setPrimary(Boolean.TRUE);
				
				entity.getResourceKinds().clear();
				
				ResourceKind rk = ResourceKindVo.toEntity(sourceVo.getPrimaryQual(), true, entity);
				
				entity.addResourceKind(rk);
			}
		}
		
		return entity;
	}

	/**
	 * Creates and returns a collection of Resource entities from a collecion of
	 * resourcevos.
	 * 
	 * @param sourceVos
	 *            the source collection of resourcevos
	 * @param cascadable
	 *            flag indicating whether the entity instances should created as
	 *            a cascadable entities
	 * @param persistables
	 *            Optional array of referenced persistable entities
	 * @return colleciton of resource entities
	 * @throws Exception
	 */
	public static Collection<Resource> toEntityList(
			Collection<ResourceVo> sourceVos, boolean cascadable,
			Persistable... persistables) throws Exception {
		List<Resource> entityList = new ArrayList<Resource>();

		for (ResourceVo sourceVo : sourceVos) {
			entityList.add(ResourceVo.toEntity(null, sourceVo, cascadable,
					persistables));
		}

		return entityList;
	}

	/**
	 * Perform some validation on the Resource entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * @throws ValidationException
	 */
	private static void validateEntity(Resource entity)
			throws ValidationException {
		Validator.validateStringField("firstName", entity.getFirstName(), 35,
				false);
		Validator.validateStringField("lastName", entity.getLastName(), 35,
				false);
		Validator.validateStringField("contactName", entity.getContactName(),
				55, false);
		Validator.validateStringField("email", entity.getEmail(), 50, false);
		Validator.validateStringField("nameOnPictureId", entity
				.getNameOnPictureId(), 70, false);
		Validator.validateStringField("other1", entity.getOther1(), 15, false);
		Validator.validateStringField("other2", entity.getOther2(), 15, false);
		Validator.validateStringField("other3", entity.getOther3(), 15, false);
		// Validator.validateStringField("phone", entity.getPhone(), 12,false);
		Validator.validateStringField("phone", entity.getPhone(), 10, false);// 10
																				// digit
																				// phone
																				// number
																				// .
																				// TODO
																				// :
																				// If
																				// we
																				// go
																				// international
																				// ,
																				// this
																				// will
																				// need
																				// to
																				// change
																				// .
																				// -
																				// dbudge
		Validator.validateStringField("resourceClassification", (entity
				.getResourceClassification() != null ? entity
				.getResourceClassification().name() : null), 3, false);
		Validator.validateStringField("resourceStatus", (entity
				.getResourceStatus() != null ? entity.getResourceStatus()
				.name() : null), 10, false);
		Validator.validateStringField("resourceName", entity.getResourceName(),
				55, false);
		Validator.validateBooleanField("contracted", entity.isContracted(),
				false);
		Validator.validateBooleanField("person", entity.isPerson(), true);
		Validator.validateBooleanField("leader", entity.isLeader(), true);
		Validator.validateBooleanField("permanent", entity.isPermanent(), true);
		Validator.validateBooleanField("enabled", entity.isEnabled(), true);
		Validator.validateEntityField("agency", entity.getAgency(), false);
		// Validator.validateLongField("agencyId", entity.getAgencyId(), false);
		// Validator.validateLongField("organizationId",
		// entity.getOrganizationId(), false);
	}

	public static boolean isKindChanged(Collection<ResourceKind> resourceKinds,
			KindVo kindVo) {
		boolean rtn = false;

		if (null != resourceKinds) {
			for (ResourceKind entity : resourceKinds) {
				if (entity.getKindId().compareTo(kindVo.getId()) != 0)
					rtn = true;
			}
		}

		return rtn;
	}

	public String getResourceName() {
		return resourceName;
	}

	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public ResourceVo getParentResourceVo() {
		return parentResourceVo;
	}

	public void setParentResourceVo(ResourceVo parentResourceVo) {
		this.parentResourceVo = parentResourceVo;
	}

	public ResourceVo getPermanentResourceVo() {
		return permanentResourceVo;
	}

	public void setPermanentResourceVo(ResourceVo permanentResourceVo) {
		this.permanentResourceVo = permanentResourceVo;
	}

	public Long getParentResourceId() {
		return parentResourceId;
	}

	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	public Long getPermanentResourceId() {
		return permanentResourceId;
	}

	public void setPermanentResourceId(Long permanentResourceId) {
		this.permanentResourceId = permanentResourceId;
	}

	public Collection<ResourceVo> getChildResourceVos() {
		if (null == childResourceVos)
			childResourceVos = new ArrayList<ResourceVo>();
		return childResourceVos;
	}

	public void setChildResourceVos(Collection<ResourceVo> childResourceVos) {
		this.childResourceVos = childResourceVos;
	}

	public Collection<IncidentResourceVo> getIncidentResourceVos() {
		if (null == incidentResourceVos)
			incidentResourceVos = new ArrayList<IncidentResourceVo>();

		return incidentResourceVos;
	}

	public void setIncidentResourceVos(
			Collection<IncidentResourceVo> incidentResourceVos) {
		this.incidentResourceVos = incidentResourceVos;
	}

	public OrganizationVo getOrganizationVo() {
		return organizationVo;
	}

	public void setOrganizationVo(OrganizationVo organizationVo) {
		this.organizationVo = organizationVo;
	}

	public Long getOrganizationId() {
		return organizationId;
	}

	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}

	public OrganizationVo getPrimaryDispatchCenterVo() {
		return this.primaryDispatchCenterVo;
	}

	public void setPrimaryDispatchCenterVo(OrganizationVo organizationVo) {
		this.primaryDispatchCenterVo = organizationVo;
	}

	public Long getPrimaryDispatchCenterId() {
		return primaryDispatchCenterId;
	}

	public void setPrimaryDispatchCenterId(Long organizationId) {
		this.primaryDispatchCenterId = organizationId;
	}

	public Collection<ResourceMobilizationVo> getResourceMobilizationVos() {
		if (null == resourceMobilizationVos)
			resourceMobilizationVos = new ArrayList<ResourceMobilizationVo>();

		return resourceMobilizationVos;
	}

	public void setResourceMobilizationVos(
			Collection<ResourceMobilizationVo> resourceMobilizationVos) {
		this.resourceMobilizationVos = resourceMobilizationVos;
	}

	public Collection<IncidentVo> getIncidentVos() {
		return incidentVos;
	}

	public void setIncidentVos(Collection<IncidentVo> incidentVos) {
		this.incidentVos = incidentVos;
	}

	public ResourceClassificationEnum getResourceClassification() {
		return resourceClassification;
	}

	public void setResourceClassification(
			ResourceClassificationEnum resourceClassification) {
		this.resourceClassification = resourceClassification;
	}

	public ResourceStatusTypeEnum getResourceStatus() {
		return resourceStatus;
	}

	public void setResourceStatus(ResourceStatusTypeEnum resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	public Long getAgencyId() {
		return agencyId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	@JsonIgnore
	public Boolean isPerson() {
		return person;
	}

	@JsonProperty("person")
	public Boolean getPerson() {
		return isPerson();
	}

	@JsonProperty("person")
	public void setPerson(Boolean person) {
		this.person = person;
	}

	@JsonIgnore
	public Boolean isContracted() {
		return contracted;
	}

	@JsonProperty("contracted")
	public Boolean getContracted() {
		return isContracted();
	}

	@JsonProperty("contracted")
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}

	@JsonIgnore
	public Boolean isLeader() {
		return leader;
	}

	@JsonProperty("leader")
	public Boolean getLeader() {
		return isLeader();
	}

	@JsonProperty("leader")
	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	public String getNameOnPictureId() {
		return nameOnPictureId;
	}

	public void setNameOnPictureId(String nameOnPictureId) {
		this.nameOnPictureId = nameOnPictureId;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOther1() {
		return other1;
	}

	public void setOther1(String other1) {
		this.other1 = other1;
	}

	public String getOther2() {
		return other2;
	}

	public void setOther2(String other2) {
		this.other2 = other2;
	}

	public String getOther3() {
		return other3;
	}

	public void setOther3(String other3) {
		this.other3 = other3;
	}

	@JsonIgnore
	public Boolean isEnabled() {
		return enabled;
	}

	@JsonProperty("enabled")
	public Boolean getEnabled() {
		return isEnabled();
	}

	@JsonProperty("enabled")
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	@JsonIgnore
	public Boolean isPermanent() {
		return permanent;
	}

	@JsonProperty("permanent")
	public Boolean getPermanent() {
		return isPermanent();
	}

	@JsonProperty("permanent")
	public void setPermanent(Boolean permanent) {
		this.permanent = permanent;
	}

	public Collection<ResourceKindVo> getResourceKindVos() {
		if (null == resourceKindVos)
			resourceKindVos = new ArrayList<ResourceKindVo>();

		return resourceKindVos;
	}

	public void setResourceKindVos(Collection<ResourceKindVo> resourceKindVos) {
		this.resourceKindVos = resourceKindVos;
	}

	public Date getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(Date deletedDate) {
		this.deletedDate = deletedDate;
	}

	/**
	 * Returns the kindVo.
	 * 
	 * @return the kindVo to return
	 */
	public KindVo getKindVo() {
		return kindVo;
	}

	/**
	 * Sets the kindVo.
	 * 
	 * @param kindVo
	 *            the kindVo to set
	 */
	public void setKindVo(KindVo kindVo) {
		this.kindVo = kindVo;
	}

	/**
	 * Returns the numberOfPersonnel.
	 * 
	 * @return the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	/**
	 * Sets the numberOfPersonnel.
	 * 
	 * @param numberOfPersonnel
	 *            the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * Returns the resourceClassficationVo.
	 * 
	 * @return the resourceClassficationVo to return
	 */
	public ResourceClassificationVo getResourceClassificationVo() {
		return resourceClassificationVo;
	}

	/**
	 * Sets the resourceClassficationVo.
	 * 
	 * @param resourceClassficationVo
	 *            the resourceClassficationVo to set
	 */
	public void setResourceClassificationVo(
			ResourceClassificationVo resourceClassificationVo) {
		this.resourceClassificationVo = resourceClassificationVo;
	}

	/**
	 * Returns the leaderType.
	 * 
	 * @return the leaderType to return
	 */
	public Integer getLeaderType() {
		return leaderType;
	}

	/**
	 * Sets the leaderType.
	 * 
	 * @param leaderType
	 *            the leaderType to set
	 */
	public void setLeaderType(Integer leaderType) {
		this.leaderType = leaderType;
	}

	public Boolean getComponent() {
		return component;
	}

	public void setComponent(Boolean component) {
		this.component = component;
	}

	/**
	 * @param otherQuals the otherQuals to set
	 */
	public void setOtherQuals(Collection<ResourceKindVo> otherQuals) {
		this.otherQuals = otherQuals;
	}

	/**
	 * @return the otherQuals
	 */
	public Collection<ResourceKindVo> getOtherQuals() {
		if (null == otherQuals)
			otherQuals = new ArrayList<ResourceKindVo>();
		
		return otherQuals;
	}

	/**
	 * @param primaryQual the primaryQual to set
	 */
	public void setPrimaryQual(ResourceKindVo primaryQual) {
		this.primaryQual = primaryQual;
	}

	/**
	 * @return the primaryQual
	 */
	public ResourceKindVo getPrimaryQual() {
		return primaryQual;
	}

	/**
	 * @return the rossResId
	 */
	public Long getRossResId() {
		return rossResId;
	}

	/**
	 * @param rossResId the rossResId to set
	 */
	public void setRossResId(Long rossResId) {
		this.rossResId = rossResId;
	}

	/**
	 * @param employmentType the employmentType to set
	 */
	@JsonIgnore
	public void setEmploymentType(EmploymentTypeEnum employmentType) {
		this.employmentType = employmentType;
	}

	/**
	 * @return the employmentType
	 */
	@JsonIgnore
	public EmploymentTypeEnum getEmploymentType() {
		return employmentType;
	}

	/**
	 * @param contractorVo the contractorVo to set
	 */
	public void setContractorVo(ContractorVo contractorVo) {
		this.contractorVo = contractorVo;
	}

	/**
	 * @return the contractorVo
	 */
	public ContractorVo getContractorVo() {
		return contractorVo;
	}

	/**
	 * @param contractorAgreementVo the contractorAgreementVo to set
	 */
	public void setContractorAgreementVo(ContractorAgreementVo contractorAgreementVo) {
		this.contractorAgreementVo = contractorAgreementVo;
	}

	/**
	 * @return the contractorAgreementVo
	 */
	public ContractorAgreementVo getContractorAgreementVo() {
		return contractorAgreementVo;
	}

	public String getRossGroupAssignment() {
		return rossGroupAssignment;
	}

	public void setRossGroupAssignment(String rossGroupAssignment) {
		this.rossGroupAssignment = rossGroupAssignment;
	}
}
