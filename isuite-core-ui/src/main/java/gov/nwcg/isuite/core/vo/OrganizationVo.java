package gov.nwcg.isuite.core.vo;


import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import gov.nwcg.isuite.core.domain.Organization;
import gov.nwcg.isuite.core.domain.impl.OrganizationImpl;
import gov.nwcg.isuite.core.filter.OrganizationFilter;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.filter.ObjectFilter;
import gov.nwcg.isuite.framework.types.OrganizationTypeEnum;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.LongUtility;
import gov.nwcg.isuite.framework.util.VoValidator;

/**
 * Implementation of an organization.
 * <p>
 * Organizations are read-only, that is there is no public way to create
 * Organizations.
 * </p>
 */
@JsonAutoDetect(fieldVisibility = Visibility.PUBLIC_ONLY)
public class OrganizationVo extends AbstractVo {
	private OrganizationTypeEnum organizationType;
	private String name;
	private String unitCode;
	private Boolean dispatchCenter;
	private Boolean supplyCache;
	private OrganizationVo managingOrganization;
	private Long managingOrganizationId;
	private Collection<OrganizationVo> managedOrganizations;
	private Long agencyId;
	private AgencyVo agencyVo;
	private String countrySubAbbreviation;
	private Collection<OrganizationVo> dispatchCenters = new ArrayList<OrganizationVo>();
	private Boolean standard;
	private Boolean local;
	private Boolean active;
	private IncidentVo incidentVo;
	private IncidentGroupVo incidentGroupVo;
	private Collection<IncidentOrgVo> incidentOrgVos = new ArrayList<IncidentOrgVo>();
	
	//default constructor
	public OrganizationVo(){}

	public OrganizationVo(Organization org) {
		super(org);
		if (org != null) {
			setId(org.getId());
			setName(org.getName());
			setUnitCode(org.getUnitCode());
			setOrganizationType(org.getOrganizationType());
			setDispatchCenter(org.isDispatchCenter());
			setSupplyCache(org.isSupplyCache());
		}
	}

	/**
	 * @param filter
	 * @param sourceVos
	 * @return
	 * @throws Exception
	 */
	public static Collection<OrganizationVo> getVosByFilter(OrganizationFilter filter, Collection<OrganizationVo> sourceVos) throws Exception {
		Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();
		ObjectFilter oFilter = new ObjectFilter(OrganizationVo.class);

		Collection<FilterCriteria> criteria = getVoFilterCriteria(filter);

		if(!VoValidator.hasOneFilter(criteria)){
			return sourceVos;
		}

		for(OrganizationVo vo : sourceVos){
			OrganizationVo orgVo = (OrganizationVo)oFilter.filterVo(vo, criteria);

			if(null != orgVo)
				vos.add(orgVo);
		}

		return vos;
	}

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getVoFilterCriteria(OrganizationFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		criteria.add( null != filter.getOrganizationName() && !filter.getOrganizationName().isEmpty() ? new FilterCriteria("name",filter.getOrganizationName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getUnitCode() && !filter.getUnitCode().isEmpty() ? new FilterCriteria("unitCode",filter.getUnitCode(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getOrganizationType() ? new FilterCriteria("organizationType",filter.getOrganizationType(),FilterCriteria.TYPE_ILIKE) : null);

		return criteria;
	}

	/**
	 * @param entity
	 * @param cascadable
	 * @param persistableObjects
	 * @return
	 * @throws Exception
	 */
	public static OrganizationVo getInstance(Organization entity,boolean cascadable,Persistable... persistableObjects) throws Exception {
		OrganizationVo vo = new OrganizationVo();

		if(null == entity)
			throw new Exception("Unable to get OrganizationVo instance from null Organization entity.");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setUnitCode(entity.getUnitCode());
			vo.setOrganizationType(entity.getOrganizationType());
			vo.setName(entity.getName());
			vo.setDispatchCenter(entity.isDispatchCenter());
			vo.setSupplyCache(entity.isSupplyCache());
			vo.setCountrySubAbbreviation(entity.getCountrySubAbbreviation());
			vo.setStandard(entity.isStandard());
			vo.setLocal(entity.isLocal());
			
			if(null != entity.getDispatchCenters()){
				for(Organization org : entity.getDispatchCenters()){
					OrganizationVo ovo = new OrganizationVo();
					ovo.setId(org.getId());
					ovo.setUnitCode(org.getUnitCode());
					ovo.setName(org.getName());
					vo.getDispatchCenters().add(ovo);
				}
			}
			
			if(entity.getAgencyId() != null)
			{
				vo.setAgencyId(entity.getAgencyId());
				if(entity.getAgency() != null)
				{
					vo.setAgencyVo(AgencyVo.getInstance(entity.getAgency(),true));
				}
			}
			
	         vo.setActive(StringBooleanEnum.toBooleanValue(entity.isActive()));
	         
	         if(null != entity.getIncident()) {
//	        	vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
	        	 IncidentVo incidentVo = new IncidentVo();
	        	 incidentVo.setId(entity.getIncident().getId());
	        	 incidentVo.setIncidentName(entity.getIncident().getIncidentName());
	        	 vo.setIncidentVo(incidentVo);
	         }
	         
	         if(null != entity.getIncidentGroup()) {
//	        	 vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), true));
	        	 IncidentGroupVo incidentGroupVo = new IncidentGroupVo();
	        	 incidentGroupVo.setId(entity.getIncidentGroup().getId());
	        	 incidentGroupVo.setGroupName(entity.getIncidentGroup().getGroupName());
	        	 vo.setIncidentGroupVo(incidentGroupVo);
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
	public static Collection<OrganizationVo> getInstances(Collection<Organization> entities, boolean cascadable) throws Exception {
		Collection<OrganizationVo> vos = new ArrayList<OrganizationVo>();

		for(Organization entity : entities){
			vos.add(getInstance(entity,true));
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
	public static Organization toEntity(Organization entity, OrganizationVo vo,boolean cascadable,Persistable... persistables) throws Exception {
		if(null == entity)
			entity = new OrganizationImpl();

		entity.setId(vo.getId());
		if(cascadable){
			entity.setUnitCode(vo.getUnitCode().toUpperCase());
			entity.setName(vo.getName().toUpperCase());
			entity.setDispatchCenter(vo.getDispatchCenter());
			entity.setSupplyCache(vo.getSupplyCache());
			entity.setStandard(vo.getStandard());
			entity.setLocal(vo.getLocal());
			
			if(null != vo.getAgencyVo()) {
				entity.setAgency(AgencyVo.toEntity(null, vo.getAgencyVo(), false));
			}
			
			entity.setActive(StringBooleanEnum.toEnumValue(vo.isActive()));
	         
			if(null != vo.getIncidentVo() && LongUtility.hasValue(vo.getIncidentVo().getId())) {
				entity.setIncident(IncidentVo.toEntity(null, vo.getIncidentVo(), false));
			}
         
			if(null != vo.getIncidentGroupVo() && LongUtility.hasValue(vo.getIncidentGroupVo().getId())) {
				entity.setIncidentGroup(IncidentGroupVo.toEntity(null, vo.getIncidentGroupVo(), false));
			}
			
			if(null != vo.getIncidentOrgVos()) {
				entity.setIncidentOrgs(IncidentOrgVo.toEntityList(vo.getIncidentOrgVos(), true));
			}
			
//			WorkArea waEntity = (WorkArea)getPersistableObject(persistables,WorkAreaImpl.class);
//			if(null != waEntity)
//				entity.getWorkAreas().add(waEntity);
		}

		return entity;
	}

	/**
	 * Returns a collection of Organization entities from a collection of Organization vos.
	 * 
	 * @param vos
	 * 			the source collection of Organization vos
	 * @param cascadable
	 * 			flag indicating whether the entity instances should created as a cascadable entities
	 * @return
	 * 			collection of Organization entities
	 * @throws Exception
	 */
	public static Collection<Organization> toEntityList(Collection<OrganizationVo> vos,boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<Organization> entities = new ArrayList<Organization>();

		for(OrganizationVo vo : vos){
			entities.add(OrganizationVo.toEntity(null,vo, cascadable, persistables));
		}

		return entities;
	}
	
	/**
	 * @param unitCode
	 * @param vos
	 * @return
	 */
	public static OrganizationVo getByUnitCode(String unitCode, Collection<OrganizationVo> vos){
		for(OrganizationVo orgVo : vos){
			if(orgVo.getUnitCode().equals(unitCode))
				return orgVo;
		}
		
		return null;
	}

	public static OrganizationVo getOrgByUnitCode(String unitCode, Collection<OrganizationVo> vos){
		for(OrganizationVo orgVo : vos){
			if(BooleanUtility.isFalse(orgVo.getDispatchCenter())){
				if(orgVo.getUnitCode().equals(unitCode))
					return orgVo;
			}
		}
		
		return null;
	}
	
	public static OrganizationVo getDispatchCenterByUnitCode(String unitCode, Collection<OrganizationVo> vos){
		for(OrganizationVo orgVo : vos){
			if(orgVo.getUnitCode().equals(unitCode) && BooleanUtility.isTrue(orgVo.getDispatchCenter()))
				return orgVo;
		}
		
		return null;
	}
	
	public static OrganizationVo getById(Long id, Collection<OrganizationVo> vos){
		for(OrganizationVo orgVo : vos){
			if(orgVo.getId().compareTo(id)==0)
				return orgVo;
		}
		
		return null;
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.access.Organization#getName()
	 */
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#setName(java.lang.String)
	 */
	public void setName(String name) {
		/*
      if (name == null || name.length() == 0) {
         throw new IllegalArgumentException("name can not be null or empty");
      }
		 */
		this.name = name;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#getUnitCode()
	 */
	public String getUnitCode() {
		return this.unitCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#setUnitCode(java.lang.String)
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#getManagedOrganizations()
	 */
	@JsonIgnore
	public Collection<OrganizationVo> getManagedOrganizations() {
		return managedOrganizations;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#setManagedOrganizations(java.util.Collection)
	 */
	@JsonIgnore
	public void setManagedOrganizations(Collection<OrganizationVo> managedOrganizations) {
		this.managedOrganizations = managedOrganizations;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#getManagingOrganization()
	 */
	@JsonIgnore
	public OrganizationVo getManagingOrganization() {
		return managingOrganization;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#setManagingOrganization(gov.nwcg.isuite.domain.organization.Organization)
	 */
	@JsonIgnore
	public void setManagingOrganization(OrganizationVo managingOrganization) {
		this.managingOrganization = managingOrganization;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#getManagingOrganizationId()
	 */
	@JsonIgnore
	public Long getManagingOrganizationId() {
		return managingOrganizationId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#setManagingOrganizationId(java.lang.Long)
	 */
	@JsonIgnore
	public void setManagingOrganizationId(Long managingOrganizationId) {
		this.managingOrganizationId = managingOrganizationId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#getOrganizationType()
	 */
	public OrganizationTypeEnum getOrganizationType() {
		return this.organizationType;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.organization.Organization#setOrganizationType(gov.nwcg.isuite.domain.organization.impl.OrganizationType)
	 */
	public void setOrganizationType(OrganizationTypeEnum type) {
		this.organizationType = type;
	}

	/**
	 * @return the dispatchCenter
	 */
	public Boolean getDispatchCenter() {
		return dispatchCenter;
	}

	/**
	 * @param dispatchCenter the dispatchCenter to set
	 */
	public void setDispatchCenter(Boolean dispatchCenter) {
		this.dispatchCenter = dispatchCenter;
	}
	
	/**
	 * @return supplyCache
	 */
	public Boolean getSupplyCache() {
		return supplyCache;
	}
	
	/**
	 * 
	 * @param supplyCache
	 */
	public void setSupplyCache(Boolean supplyCache) {
		this.supplyCache = supplyCache;
	}

	/**
	 * @return the agencyId
	 */
	public Long getAgencyId() {
		return agencyId;
	}

	/**
	 * @param agencyId the agencyId to set
	 */
	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	/**
	 * @return the agency
	 */
	public AgencyVo getAgencyVo() {
		return agencyVo;
	}

	/**
	 * @param agency the agency to set
	 */
	public void setAgencyVo(AgencyVo agencyVo) {
		this.agencyVo = agencyVo;
	}

	/**
	 * @param countrySubAbbreviation the countrySubAbbreviation to set
	 */
	public void setCountrySubAbbreviation(String countrySubAbbreviation) {
		this.countrySubAbbreviation = countrySubAbbreviation;
	}

	/**
	 * @return the countrySubAbbreviation
	 */
	public String getCountrySubAbbreviation() {
		return countrySubAbbreviation;
	}

	/**
	 * Returns the dispatchCenters.
	 *
	 * @return 
	 *		the dispatchCenters to return
	 */
	public Collection<OrganizationVo> getDispatchCenters() {
		return dispatchCenters;
	}

	/**
	 * Sets the dispatchCenters.
	 *
	 * @param dispatchCenters 
	 *			the dispatchCenters to set
	 */
	public void setDispatchCenters(Collection<OrganizationVo> dispatchCenters) {
		this.dispatchCenters = dispatchCenters;
	}

	/**
	 * @param standard the standard to set
	 */
	@JsonProperty("standard")
	public void setStandard(Boolean standard) {
		this.standard = standard;
	}

	/**
	 * @return the standard
	 */
	@JsonProperty("standard")
	public Boolean getStandard() {
		return standard;
	}
	
	/**
	 * @return the standard
	 */
	@JsonIgnore
	public Boolean isStandard() {
		return standard;
	}

	/**
	 * @param local the local to set
	 */
	@JsonProperty("local")
	public void setLocal(Boolean local) {
		this.local = local;
	}

	/**
	 * @return the local
	 */
	@JsonProperty("local")
	public Boolean getLocal() {
		return local;
	}
	
	/**
	 * @return the local
	 */
	@JsonIgnore
	public Boolean isLocal() {
		return local;
	}
	
	/**
	 * @return the active
	 */
	@JsonProperty("active")
	public Boolean getActive() {
		return active;
	}

	/**
	 * @return the active
	 */
	@JsonIgnore
	public Boolean isActive() {
		return active;
	}

	/**
	 * @param active the active to set
	 */
	@JsonProperty("active")
	public void setActive(Boolean active) {
		this.active = active;
	}

	/**
	 * @param incidentVo the incidentVo to set
	 */
//	@JsonIgnore
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}
	
	/**
	 * @return the incidentVo
	 */
//	@JsonIgnore
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}
	
	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
//	@JsonIgnore
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}
	
	/**
	 * @return the incidentGroupVo
	 */
//	@JsonIgnore
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

	/**
	 * @param incidentOrgVos the incidentOrgVos to set
	 */
	@JsonIgnore
	public void setIncidentOrgVos(Collection<IncidentOrgVo> incidentOrgVos) {
		this.incidentOrgVos = incidentOrgVos;
	}

	/**
	 * @return the incidentOrgVos
	 */
	@JsonIgnore
	public Collection<IncidentOrgVo> getIncidentOrgVos() {
		return incidentOrgVos;
	}
}
