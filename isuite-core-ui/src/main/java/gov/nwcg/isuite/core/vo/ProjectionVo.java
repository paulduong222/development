package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Projection;
import gov.nwcg.isuite.core.domain.ProjectionItem;
import gov.nwcg.isuite.core.domain.impl.ProjectionImpl;
import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.LongUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class ProjectionVo extends AbstractVo implements PersistableVo {
	private String projectionName;
	private Date startDate;
	private Date lastModifiedDate;
	private Short numberOfDays;
	private Collection<ProjectionItemVo> projectionItemsVos;
	private Long incidentId;
	private Long incidentGroupId;
	private IncidentVo incidentVo;
	private IncidentGroupVo incidentGroupVo;
	private Date createdDate;
    private String incidentName;
    private String incidentGroupName;
    
	public ProjectionVo() {
	}

	/**
	 * Returns a ProjectionVo instance from an Projection entity.
	 * 
	 * @param entity
	 * 			the source entity
	 * @param cascadable
	 * 			flag indicating whether the instance should created as a cascadable vo
	 * @return
	 * 		instance of ProjectionVo
	 * @throws Exception
	 */
	public static ProjectionVo getInstance(Projection entity,boolean cascadable) throws Exception {
		ProjectionVo vo = new ProjectionVo();

		if(null == entity)
			throw new Exception("Unable to create ProjectionVo from null Projection entity.");

		try{
		vo.setId(entity.getId());
		vo.setProjectionName(entity.getProjectionName());
		vo.setStartDate(entity.getStartDate());
		vo.setCreatedDate(entity.getCreatedDate());
		vo.setLastModifiedDate(entity.getLastModifiedDate());
		vo.setNumberOfDays(entity.getNumberOfDays());
		vo.setIncidentId(entity.getIncidentId());
		vo.setIncidentGroupId(entity.getIncidentGroupId());
		
		if(cascadable){
			if(CollectionUtility.hasValue(entity.getProjectionItems())){
				vo.setProjectionItemsVos(ProjectionItemVo.getInstances(entity.getProjectionItems(),true));
			}
			
			if(null != entity.getIncident()){
				vo.setIncidentVo(IncidentVo.getInstance(entity.getIncident(), false));
				vo.setIncidentName(entity.getIncident().getIncidentName());
			}
			
			if(null != entity.getIncidentGroup()){
				vo.setIncidentGroupVo(IncidentGroupVo.getInstance(entity.getIncidentGroup(), false));
				vo.setIncidentName("All Incidents");
				vo.setIncidentGroupName(entity.getIncidentGroup().getGroupName());
			}
		}
		} catch (Exception e){
			throw e;
		}
		
		return vo;
	}

	public static Collection<ProjectionVo> getInstances(Collection<Projection> entities, boolean cascadable) throws Exception {
		Collection<ProjectionVo> vos = new ArrayList<ProjectionVo>();
		try {
		for(Projection entity : entities){
			vos.add(ProjectionVo.getInstance(entity, cascadable));
		}
		} catch (Exception e) {
			throw e;
		}
		return vos;
	}

	/**
	 * Returns a Projection entity from a vo.
	 * 
	 * @param vo
	 * 			the source vo
	 * @param cascadable
	 * 			flag indicating whether the entity instance should created as a cascadable entity
	 * @return
	 * 			instance of Projection entity
	 * @throws Exception
	 */
	public static Projection toEntity(Projection entity, ProjectionVo vo, boolean cascadable,Persistable...persistables ) throws Exception {
		if(null == entity)
			entity=new ProjectionImpl();
		
		if(LongUtility.hasValue(vo.getId()))
			entity.setId(vo.getId());
		
		if(cascadable){
			entity.setProjectionName(vo.getProjectionName());
			entity.setStartDate(vo.getStartDate());
			entity.setCreatedDate(vo.getCreatedDate());
			entity.setLastModifiedDate(vo.getLastModifiedDate());
			entity.setNumberOfDays(vo.getNumberOfDays());

			if(CollectionUtility.hasValue(vo.getProjectionItemsVos())){

				Collection<ProjectionItem> piEntities=ProjectionItemVo.toEntities(vo.getProjectionItemsVos(),true,entity);
				entity.getProjectionItems().addAll(piEntities);
				entity.setProjectionItems(ProjectionItemVo.toEntities(vo.getProjectionItemsVos(),true));
			}
			
			if(null != vo.getIncidentVo()){
				entity.setIncident(IncidentVo.toEntity(null,vo.getIncidentVo(), false));
				
			}
			
			if(null != vo.getIncidentGroupVo()){
				entity.setIncidentGroup(IncidentGroupVo.toEntity(null,vo.getIncidentGroupVo(), false));
			}
			
		}

		return entity;
	}

	/**
	 * @return the projectionName
	 */
	public String getProjectionName() {
		return projectionName;
	}

	/**
	 * @param projectionName the projectionName to set
	 */
	public void setProjectionName(String projectionName) {
		this.projectionName = projectionName;
	}

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the numberOfDays
	 */
	public Short getNumberOfDays() {
		return numberOfDays;
	}

	/**
	 * @param numberOfDays the numberOfDays to set
	 */
	public void setNumberOfDays(Short numberOfDays) {
		this.numberOfDays = numberOfDays;
	}

	/**
	 * @return the projectionItemsVos
	 */
	public Collection<ProjectionItemVo> getProjectionItemsVos() {
		return projectionItemsVos;
	}

	/**
	 * @param projectionItemsVos the projectionItemsVos to set
	 */
	public void setProjectionItemsVos(
			Collection<ProjectionItemVo> projectionItemsVos) {
		this.projectionItemsVos = projectionItemsVos;
	}

	/**
	 * @return the incidentVo
	 */
	public IncidentVo getIncidentVo() {
		return incidentVo;
	}

	/**
	 * @param incidentVo the incidentVo to set
	 */
	public void setIncidentVo(IncidentVo incidentVo) {
		this.incidentVo = incidentVo;
	}

	/**
	 * @return the incidentGroupVo
	 */
	public IncidentGroupVo getIncidentGroupVo() {
		return incidentGroupVo;
	}

	/**
	 * @param incidentGroupVo the incidentGroupVo to set
	 */
	public void setIncidentGroupVo(IncidentGroupVo incidentGroupVo) {
		this.incidentGroupVo = incidentGroupVo;
	}

	public Long getIncidentId() {
		return incidentId;
	}

	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	
	public Date getLastModifiedDate() {
		return lastModifiedDate;
	}

	public void setLastModifiedDate(Date lastModifiedDate) {
		this.lastModifiedDate = lastModifiedDate;
	}

	public String getIncidentName() {
		return this.incidentName;
	}

	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	public String getIncidentGroupName() {
		return incidentGroupName;
	}

	public void setIncidentGroupName(String incidentGroupName) {
		this.incidentGroupName = incidentGroupName;
	}
}
