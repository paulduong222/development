package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.WorkArea;
import gov.nwcg.isuite.core.domain.impl.WorkAreaImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ValidationException;

import java.util.ArrayList;
import java.util.Collection;

public class WorkAreaVo extends AbstractVo implements PersistableVo {
	private String name;
	private String description;
	private Boolean sharedOut;
	private UserVo userVo;
	private OrganizationVo standardOrganizationVo;
	private Collection<IncidentVo> workAreaIncidentVos;
	private Collection<ResourceVo> workAreaResourceVos;
	private Collection<WorkAreaUserVo> workAreaUserVos;
	private Collection<OrganizationVo> filteredOrganizations = new ArrayList<OrganizationVo>();

	/* Used for copying an existing work area. */
	private Long workAreaIdToBeCopied;

	public WorkAreaVo() {
		super();
	}

	public static WorkAreaVo getInstance(WorkArea entity,boolean cascadable,Persistable... persistables) throws Exception {
		WorkAreaVo vo = new WorkAreaVo();

		if(null == entity)
			throw new Exception("Unable to create WorkAreaVo from null WorkArea entity");

		vo.setId(entity.getId());

		if(cascadable){
			vo.setName(entity.getName());
			vo.setDescription(entity.getDescription());
			vo.setSharedOut(entity.isSharedOut());
			if(null != entity.getWorkAreaIncidents())
				vo.setWorkAreaIncidentVos(IncidentVo.getInstances(entity.getWorkAreaIncidents(),true));
			vo.setCreatedBy(entity.getCreatedBy());
			vo.setCreatedDate(entity.getCreatedDate());

			if(null != entity.getUserId()){
				UserVo uvo = new UserVo();
				uvo.setId(entity.getUserId());
				uvo.setLoginName(entity.getUser().getLoginName());
				vo.setUserVo(uvo);
			}
			
			if(null != entity.getStandardOrganizationId()){
				vo.setStandardOrganizationVo(OrganizationVo.getInstance(entity.getStandardOrganization(), true));
			}

			if( (null != entity.getFilteredOrganizations()) && (entity.getFilteredOrganizations().size()>0) ){
				vo.setFilteredOrganizations(OrganizationVo.getInstances(entity.getFilteredOrganizations(), true));
			}

			if( (null != entity.getWorkAreaUsers()) && (entity.getWorkAreaUsers().size()>0) ){
				vo.setWorkAreaUserVos(WorkAreaUserVo.getInstances(entity.getWorkAreaUsers(), true));
			}
		}

		return vo;
	}

	public static Collection<WorkAreaVo> getInstances(Collection<WorkArea> entities, boolean cascadable) throws Exception {
		Collection<WorkAreaVo> vos = new ArrayList<WorkAreaVo>();

		for(WorkArea entity : entities){
			vos.add(WorkAreaVo.getInstance(entity,cascadable));
		}

		return vos;
	}

	/**
	 * Returns a WorkArea entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of WorkArea entity
	 * @throws Exception
	 */
	public static WorkArea toEntity(WorkArea entity, WorkAreaVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new WorkAreaImpl();

		entity.setId(vo.getId());

		if(cascadable){
			entity.setName(vo.getName());
			entity.setDescription(vo.getDescription());
			entity.setSharedOut((null != vo.getSharedOut() ? vo.getSharedOut() : false));
			if(null != vo.getUserVo())
				entity.setUser(UserVo.toEntity(null, vo.getUserVo(), false));

			if( (null != vo.getFilteredOrganizations()) && (vo.getFilteredOrganizations().size()>0) )
				entity.setFilteredOrganizations(OrganizationVo.toEntityList(vo.getFilteredOrganizations(), false, entity));

			if( (null != vo.getWorkAreaUserVos()) && (vo.getWorkAreaUserVos().size()>0) )
				entity.setWorkAreaUsers(WorkAreaUserVo.toEntityList(vo.getWorkAreaUserVos(), true,entity));

			if( (null != vo.getWorkAreaIncidentVos()) && (vo.getWorkAreaIncidentVos().size()>0) )
				entity.setWorkAreaIncidents(IncidentVo.toEntityList(vo.getWorkAreaIncidentVos(), false, entity));

			if( (null != vo.getWorkAreaResourceVos()) && (vo.getWorkAreaResourceVos().size()>0) )
				entity.setWorkAreaResources(ResourceVo.toEntityList(vo.getWorkAreaResourceVos(), false, entity));

			/*
			 * Validate the entity
			 */
			validateEntity(entity);

		}

		return entity;
	}
	
	public static Collection<WorkArea> toEntityList(Collection<WorkAreaVo> vos, boolean cascadable,Persistable...persistables ) throws Exception {
		Collection<WorkArea> entities = new ArrayList<WorkArea>();
		
		for(WorkAreaVo vo : vos){
			entities.add(WorkAreaVo.toEntity(null, vo, cascadable,persistables));
		}
		
		return entities;
	}

	/**
	 * Perform some validation on the WorkArea entity field values against the
	 * entity field definitions.
	 * 
	 * @param entity
	 * 			the source workArea entity
	 * @throws ValidationException
	 */
	private static void validateEntity(WorkArea entity) throws ValidationException {
	}

	public static Boolean hasOrganization(Long orgId, Collection<OrganizationVo> vos) throws Exception {
		if(null != vos){
			for(OrganizationVo vo : vos){
				if(vo.getId().compareTo(orgId)==0){
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return the workAreaIdToBeCopied
	 */
	public Long getWorkAreaIdToBeCopied() {
		return workAreaIdToBeCopied;
	}

	/**
	 * @param workAreaIdToBeCopied the workAreaIdToBeCopied to set
	 */
	public void setWorkAreaIdToBeCopied(Long workAreaIdToBeCopied) {
		this.workAreaIdToBeCopied = workAreaIdToBeCopied;
	}



	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		if(null != description){
			this.description = description.toUpperCase();	
		}
	}

	/**
	 * @return the filteredOrganizations
	 */
	public Collection<OrganizationVo> getFilteredOrganizations() {
		return filteredOrganizations;
	}

	/**
	 * @param filteredOrganizations the filteredOrganizations to set
	 */
	public void setFilteredOrganizations(Collection<OrganizationVo> filteredOrganizations) {
		this.filteredOrganizations = filteredOrganizations;
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
		if(null != name){
			this.name = name.toUpperCase();	
		}
	}

	/**
	 * @return the sharedOut
	 */
	public Boolean getSharedOut() {
		return sharedOut;
	}

	/**
	 * @param sharedOut the sharedOut to set
	 */
	public void setSharedOut(Boolean sharedOut) {
		this.sharedOut = sharedOut;
	}

	/**
	 * @return the standardOrganizationVo
	 */
	public OrganizationVo getStandardOrganizationVo() {
		return standardOrganizationVo;
	}

	/**
	 * @param standardOrganizationVo the standardOrganizationVo to set
	 */
	public void setStandardOrganizationVo(OrganizationVo standardOrganizationVo) {
		this.standardOrganizationVo = standardOrganizationVo;
	}

	/**
	 * @return the userVo
	 */
	public UserVo getUserVo() {
		return userVo;
	}

	/**
	 * @param userVo the userVo to set
	 */
	public void setUserVo(UserVo userVo) {
		this.userVo = userVo;
	}

	/**
	 * @return the workAreaIncidentVos
	 */
	public Collection<IncidentVo> getWorkAreaIncidentVos() {
		return workAreaIncidentVos;
	}

	/**
	 * @param workAreaIncidentVos the workAreaIncidentVos to set
	 */
	public void setWorkAreaIncidentVos(Collection<IncidentVo> workAreaIncidentVos) {
		this.workAreaIncidentVos = workAreaIncidentVos;
	}

	/**
	 * @return the workAreaResourceVos
	 */
	public Collection<ResourceVo> getWorkAreaResourceVos() {
		return workAreaResourceVos;
	}

	/**
	 * @param workAreaResourceVos the workAreaResourceVos to set
	 */
	public void setWorkAreaResourceVos(Collection<ResourceVo> workAreaResourceVos) {
		this.workAreaResourceVos = workAreaResourceVos;
	}

	/**
	 * @return the workAreaUserVos
	 */
	public Collection<WorkAreaUserVo> getWorkAreaUserVos() {
		if(null == workAreaUserVos)
			workAreaUserVos = new ArrayList<WorkAreaUserVo>();

		return workAreaUserVos;
	}

	/**
	 * @param workAreaUserVos the workAreaUserVos to set
	 */
	public void setWorkAreaUserVos(Collection<WorkAreaUserVo> workAreaUserVos) {
		this.workAreaUserVos = workAreaUserVos;
	}

	/**
	 * Constructs a <code>String</code> with all attributes in xml format.
	 *
	 * @return a <code>String</code> representation of this object.
	 */
	@Override
	public String toString() {
		final String nl = System.getProperty("line.separator");

		StringBuffer retValue = new StringBuffer();
		String tab = "\t";

		retValue.append("<WorkAreaVo>").append(nl)
		.append(tab).append(super.toString()).append(nl)
		.append(tab).append("<workAreaIdToBeCopied>").append(this.workAreaIdToBeCopied).append("</workAreaIdToBeCopied>").append(nl)   
		.append("</WorkAreaVo>");

		return retValue.toString();
	}

}
