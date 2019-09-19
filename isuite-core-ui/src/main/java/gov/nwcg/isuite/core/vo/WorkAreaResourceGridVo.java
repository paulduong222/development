package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

public class WorkAreaResourceGridVo extends AbstractVo {
	private String groupTag;
	private Long resourceId;
	private Long parentResourceId;
    private Long workAreaId;
    private Long workAreaResourceId;
    private Long incidentId;
    private Collection<WorkAreaResourceGridVo> children=new ArrayList<WorkAreaResourceGridVo>();
	
	private String resourceName;
	private String lastName;
	private String firstName;
	private String agencyCode;
	private String unitCode;
	private Long primaryDispatchCenterId;
	private String requestCategory;
	private String requestCategoryCode;
	private String incidentName;
	private String assignment;
	private AssignmentStatusTypeEnum assignmentStatus;
	private Long permanentResourceId;
	private String kindDescription;
	private String requestNumber;
	private Boolean enabled;
	private Collection<String> criticalData;
	private Boolean deletable;
	private Integer leaderType;
	
	
	public WorkAreaResourceGridVo() {		
	}
	
	public static WorkAreaResourceGridVo getInstance(Resource entity) throws Exception {
		WorkAreaResourceGridVo vo = new WorkAreaResourceGridVo();
		
		if(null != entity){
			vo.setResourceId(entity.getId());
			vo.setFirstName(entity.getFirstName());
			vo.setLastName(entity.getLastName());
			if( (null != entity.getResourceName()) && (!entity.getResourceName().isEmpty())){
				vo.setResourceName(entity.getResourceName());
			}else{
				vo.setResourceName(entity.getLastName() + ", " + entity.getFirstName());
			}
			if( (null != entity.getAgencyId()) && (entity.getAgencyId()>0L))
				vo.setAgencyCode(entity.getAgency().getAgencyCode());
			
			if( (null != entity.getOrganizationId()) && (entity.getOrganizationId()>0L))
				vo.setUnitCode(entity.getOrganization().getUnitCode());
			
			//TODO: Add additional criteria to determine if resource is deletable such as:
			// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
			if( null != entity.getIncidents() ) { 
				vo.setDeletable(false); 
			}
			else { 
				vo.setDeletable(true); 
			}
			
		}
		
		return vo;
	}
	
	public static Collection<WorkAreaResourceGridVo> getInstances(Collection<Resource> entities) throws Exception {
		Collection<WorkAreaResourceGridVo> vos = new ArrayList<WorkAreaResourceGridVo>();
		
		for(Resource entity : entities) {
			vos.add(getInstance(entity));
		}
		
		return vos;
	}
	
	/**
	 * @return the resourceName
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * @param resourceName
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}
	
	/**
	 * @param primaryDispatchCenterId the primaryDispatchCenterId to set
	 */
	public void setPrimaryDispatchCenterId(Long primaryDispatchCenterId) {
		this.primaryDispatchCenterId = primaryDispatchCenterId;
	}

	/**
	 * @return the primaryDispatchCenterId
	 */
	public Long getPrimaryDispatchCenterId() {
		return primaryDispatchCenterId;
	}	

	/**
	 * @return the requestCategory
	 */
	public String getRequestCategory() {
		return requestCategory;
	}

	/**
	 * @param requestCategory
	 */
	public void setRequestCategory(String requestCategory) {
		this.requestCategory = requestCategory;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the assignment
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * @param assignment
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	/**
	 * @return the assignmentStatus
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * @param assignmentStatus
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum assignmentStatus) {
		
		this.assignmentStatus = assignmentStatus;
	}

	/**
	 * @param assignmentStatus
	 */
	public void setAssignmentStatusString(String assignmentStatus) {
		if(null!=assignmentStatus)
			this.assignmentStatus = AssignmentStatusTypeEnum.valueOf(assignmentStatus);
	}

	public String getAssignmentStatusString() {
		return this.assignmentStatus.toString();
	}
	
	/**
	 * @return the permanentResourceId
	 */
	public Long getPermanentResourceId() {
		return permanentResourceId;
	}

	/**
	 * @param permanentResourceId
	 */
	public void setPermanentResourceId(Long permanentResourceId) {
		this.permanentResourceId = permanentResourceId;
	}

	/**
	 * @param requestCatgoryCode
	 */
	public void setRequestCategoryCode(String requestCategoryCode) {
		this.requestCategoryCode = requestCategoryCode;
	}

	/**
	 * @return requestCatgoryCode
	 */
	public String getRequestCategoryCode() {
		return requestCategoryCode;
	}
	
	/**
	 * Returns the groupTag.
	 *
	 * @return 
	 *		the groupTag to return
	 */
	public String getGroupTag() {
		return groupTag;
	}

	/**
	 * Sets the groupTag.
	 *
	 * @param groupTag 
	 *			the groupTag to set
	 */
	public void setGroupTag(String groupTag) {
		this.groupTag = groupTag;
	}

	/**
	 * Returns the resourceId.
	 *
	 * @return 
	 *		the resourceId to return
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * Sets the resourceId.
	 *
	 * @param resourceId 
	 *			the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Returns the parentResourceId.
	 *
	 * @return 
	 *		the parentResourceId to return
	 */
	public Long getParentResourceId() {
		return parentResourceId;
	}

	/**
	 * Sets the parentResourceId.
	 *
	 * @param parentResourceId 
	 *			the parentResourceId to set
	 */
	public void setParentResourceId(Long parentResourceId) {
		this.parentResourceId = parentResourceId;
	}

	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	/**
	 * Returns the workAreaResourceId.
	 *
	 * @return 
	 *		the workAreaResourceId to return
	 */
	public Long getWorkAreaResourceId() {
		return workAreaResourceId;
	}

	/**
	 * Sets the workAreaResourceId.
	 *
	 * @param workAreaResourceId 
	 *			the workAreaResourceId to set
	 */
	public void setWorkAreaResourceId(Long workAreaResourceId) {
		this.workAreaResourceId = workAreaResourceId;
	}

	/**
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the agencyCode.
	 *
	 * @return 
	 *		the agencyCode to return
	 */
	public String getAgencyCode() {
		return agencyCode;
	}

	/**
	 * Sets the agencyCode.
	 *
	 * @param agencyCode 
	 *			the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}
	
	/**
	 * Returns the children.
	 *
	 * @return 
	 *		the children to return
	 */
	public Collection<WorkAreaResourceGridVo> getChildren() {
		return children;
	}

	/**
	 * Sets the children.
	 *
	 * @param children 
	 *			the children to set
	 */
	public void setChildren(Collection<WorkAreaResourceGridVo> children) {
		this.children = children;
	}
	
	
	/**
	 * @return criticalData
	 */
	public Collection<String> getCriticalData() {
		return criticalData;
	}
	
	/**
	 * @param criticalData 
	 */
	public void setCriticalData(Collection<String>criticalData) {
		this.criticalData = criticalData;
	}
	
	
	public Boolean getDeletable() {		
		return deletable;
	}

	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}
	
	

	/**
	 * Creates and returns a hierarchal collection of WorkAreaResourceGridVo's.
	 * Hierarchy is based on the request number sequence.
	 * 
	 * @param vos
	 * 			the source collection of WorkAreaResourceGridVo's.
	 * @return
	 * 			hierarchal collection of WorkAreaResourceGridVo's 
	 * @throws Exception
	 */
	public static Collection<WorkAreaResourceGridVo> toHierarchyCollection(Collection<WorkAreaResourceGridVo> vos) throws Exception {
		Collection<WorkAreaResourceGridVo> rtnVos = new ArrayList<WorkAreaResourceGridVo>();
		Boolean addedToParent=false;
		
		for(WorkAreaResourceGridVo vo : vos){
			Long parentResourceId = vo.getParentResourceId();
			
			if((null!=parentResourceId) &&(parentResourceId>0L)){
				vo.setGroupTag(parentResourceId.toString());
				addedToParent=addToParent(vos,parentResourceId,vo);
			}
			if ((null==parentResourceId) || (parentResourceId==0L) || (!addedToParent)){
				vo.setGroupTag("");
				rtnVos.add(vo);
			}
		}

		return rtnVos;
	}
	
	/**
	 * Adds a vo to it's parent vo based on request number sequence.
	 * 
	 * @param vos
	 * 			source collection of vo's.
	 * @param parentResourceId
	 * 			the parent resource Id
	 * @param voToAdd
	 * 			the vo to add to it's parent (if found)
	 * @throws Exception
	 */
	private static Boolean addToParent(Collection<WorkAreaResourceGridVo> vos, Long parentResourceId, WorkAreaResourceGridVo voToAdd) throws Exception {

		Boolean addedToParent = false;
		
		for(WorkAreaResourceGridVo vo : vos){
			if(vo.getWorkAreaResourceId().equals(parentResourceId)){
				vo.getChildren().add(voToAdd);
				addedToParent = true;
				return addedToParent;
			}
			if(vo.children.size()>0){
				for(WorkAreaResourceGridVo cvo : vo.children){
					if(cvo.getWorkAreaResourceId().equals(parentResourceId)){
						cvo.getChildren().add(voToAdd);
						addedToParent = true;
						return addedToParent;
					}
				}
			}
		}	
		return addedToParent;
	}

	/**
	 * Returns the kindDescription.
	 *
	 * @return 
	 *		the kindDescription to return
	 */
	public String getKindDescription() {
		return kindDescription;
	}

	/**
	 * Sets the kindDescription.
	 *
	 * @param kindDescription 
	 *			the kindDescription to set
	 */
	public void setKindDescription(String kindDescription) {
		this.kindDescription = kindDescription;
	}

	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @param enabled the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the enabled
	 */
	public Boolean getEnabled() {
		return (enabled != null ? enabled : false);
	}

	public Integer getLeaderType() {
		return leaderType;
	}

	public void setLeaderType(Integer leaderType) {
		this.leaderType = leaderType;
	}

}
