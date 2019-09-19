package gov.nwcg.isuite.core.filter;

import java.util.Collection;

import gov.nwcg.isuite.core.vo.AssignmentStatusVo;
import gov.nwcg.isuite.core.vo.RequestCategoryVo;
import gov.nwcg.isuite.framework.core.filter.Filter;

public interface WorkAreaResourcesFilter extends Filter{

	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId();

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId);

	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName();

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName) ;

	/**
	 * Returns the unitCode.
	 *
	 * @return 
	 *		the unitCode to return
	 */
	public String getUnitCode();

	/**
	 * Sets the unitCode.
	 *
	 * @param unitCode 
	 *			the unitCode to set
	 */
	public void setUnitCode(String unitCode) ;

	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName();

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName);

	/**
	 * Returns the resourceCategory.
	 *
	 * @return 
	 *		the resourceCategory to return
	 */
	public String getResourceCategory();

	/**
	 * Sets the resourceCategory.
	 *
	 * @param resourceCategory 
	 *			the resourceCategory to set
	 */
	public void setResourceCategory(String resourceCategory);

	/**
	 * Returns the assignment.
	 *
	 * @return 
	 *		the assignment to return
	 */
	public String getAssignment();

	/**
	 * Sets the assignment.
	 *
	 * @param assignment 
	 *			the assignment to set
	 */
	public void setAssignment(String assignment);

	/**
	 * Returns the requestCategoryVos.
	 *
	 * @return 
	 *		the requestCategoryVos to return
	 */
	public Collection<RequestCategoryVo> getRequestCategoryVos();

	/**
	 * Sets the requestCategoryVos.
	 *
	 * @param requestCategoryVos 
	 *			the requestCategoryVos to set
	 */
	public void setRequestCategoryVos(Collection<RequestCategoryVo> requestCategoryVos);

	/**
	 * Returns the assignmentStatusVos.
	 *
	 * @return 
	 *		the assignmentStatusVos to return
	 */
	public Collection<AssignmentStatusVo> getAssignmentStatusVos();

	/**
	 * Sets the assignmentStatusVos.
	 *
	 * @param assignmentStatusVos 
	 *			the assignmentStatusVos to set
	 */
	public void setAssignmentStatusVos(Collection<AssignmentStatusVo> assignmentStatusVos);

}
