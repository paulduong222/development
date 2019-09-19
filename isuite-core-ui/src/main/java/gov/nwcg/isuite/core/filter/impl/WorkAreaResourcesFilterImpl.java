package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.WorkAreaResourcesFilter;
import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

import java.util.ArrayList;
import java.util.Collection;

@SuppressWarnings("unchecked")
public class WorkAreaResourcesFilterImpl extends FilterImpl implements WorkAreaResourcesFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1866492645171549146L;

	private Long workAreaId=0L;

	private String resourceName;
    private String unitCode;
    private String incidentName;
    private String resourceCategory;
    private String assignment;
    private Collection<AssignmentStatusVo> assignmentStatusVos = new ArrayList<AssignmentStatusVo>();
	private Collection<RequestCategoryVo> requestCategoryVos = new ArrayList<RequestCategoryVo>();

	private Boolean deletable;
	private String deletableString;
    
	public WorkAreaResourcesFilterImpl(){
		
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
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Returns the unitCode.
	 *
	 * @return 
	 *		the unitCode to return
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * Sets the unitCode.
	 *
	 * @param unitCode 
	 *			the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}

	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * Returns the resourceCategory.
	 *
	 * @return 
	 *		the resourceCategory to return
	 */
	public String getResourceCategory() {
		return resourceCategory;
	}

	/**
	 * Sets the resourceCategory.
	 *
	 * @param resourceCategory 
	 *			the resourceCategory to set
	 */
	public void setResourceCategory(String resourceCategory) {
		this.resourceCategory = resourceCategory;
	}

	/**
	 * Returns the assignment.
	 *
	 * @return 
	 *		the assignment to return
	 */
	public String getAssignment() {
		return assignment;
	}

	/**
	 * Sets the assignment.
	 *
	 * @param assignment 
	 *			the assignment to set
	 */
	public void setAssignment(String assignment) {
		this.assignment = assignment;
	}

	/**
	 * Returns the requestCategoryVos.
	 *
	 * @return 
	 *		the requestCategoryVos to return
	 */
	public Collection<RequestCategoryVo> getRequestCategoryVos() {
		return requestCategoryVos;
	}

	/**
	 * Sets the requestCategoryVos.
	 *
	 * @param requestCategoryVos 
	 *			the requestCategoryVos to set
	 */
	public void setRequestCategoryVos(
			Collection<RequestCategoryVo> requestCategoryVos) {
		this.requestCategoryVos = requestCategoryVos;
	}

	/**
	 * Returns the assignmentStatusVos.
	 *
	 * @return 
	 *		the assignmentStatusVos to return
	 */
	public Collection<AssignmentStatusVo> getAssignmentStatusVos() {
		return assignmentStatusVos;
	}

	/**
	 * Sets the assignmentStatusVos.
	 *
	 * @param assignmentStatusVos 
	 *			the assignmentStatusVos to set
	 */
	public void setAssignmentStatusVos(
			Collection<AssignmentStatusVo> assignmentStatusVos) {
		this.assignmentStatusVos = assignmentStatusVos;
	}
	
	
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

	
	public Boolean getDeletable() {
		return deletable;
	}

	/**
	 * @return the deletableString
	 */
	public String getDeletableString() {
		return deletableString;
	}

	/**
	 * @param deletableString the deletableString to set
	 */
	public void setDeletableString(String deletableString) {
		this.deletableString = deletableString;
		this.setDeletable(super.determineDeletableValue(this.deletableString));
	}


}