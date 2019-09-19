package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.AssignmentStatusTypeEnum;

import java.util.Collection;
import java.util.Date;

/**
 * ORM Mapping representative of an Assignment which a Resource performs 
 * at an Incident.
 * 
 * @author bsteiner
 */
public interface Assignment extends Persistable {
	
	/**
	 * Returns the request number.
	 * 
	 * @return
	 * 		the request number to return
	 */
	public String getRequestNumber();
	
	/**
	 * Sets the request number.
	 * 
	 * @param requestNumber
	 * 			the request number to set
	 */
	public void setRequestNumber(String requestNumber);
	
	/**
	 * Returns the assignment start date.
	 * 
	 * @return
	 * 		the start date to return
	 */
	public Date getStartDate();
	
	/**
	 * Sets the assignment start date
	 * 
	 * @param date
	 * 			the start date to set
	 */
	public void setStartDate(Date date);
	
	/**
	 * Returns the assignment end date.
	 * 
	 * @return
	 * 		the end date to return
	 */
	public Date getEndDate();
	
	/**
	 * Sets the assignment end date.
	 * 
	 * @param date
	 * 			the end date to set
	 */
	public void setEndDate(Date date);
	
	/**
	 * Returns the kind.
	 * 
	 * @return
	 * 		the kind to return
	 */
	public Kind getKind();
	
	/**
	 * Sets the kind.
	 * 
	 * @param kind
	 * 			the kind to set
	 */
	public void setKind(Kind kind);
	
	/**
	 * Returns the kind id.
	 * 
	 * @return
	 * 		the kind id return
	 */
	public Long getKindId();
	
	/**
	 * Sets the kind id.
	 * 
	 * @param id
	 * 			the kind id to set
	 */
	public void setKindId(Long id);

	/**
	 * Returns whether the assignment is for training.
	 * 
	 * @return
	 * 		whether the assignment is for training
	 */
	public Boolean isTraining();
	
	/**
	 * Sets whether the assignment is for training.
	 * 
	 * @param training
	 * 		the assignment training to set
	 */
	public void setTraining(Boolean training);

	/**
	 * Returns the assignment status.
	 * 
	 * @return
	 * 		the assignment status to return
	 */
	public AssignmentStatusTypeEnum getAssignmentStatus();
	
	/**
	 * Sets the assignment status.
	 * 
	 * @param type
	 * 			the assignment status to set
	 */
	public void setAssignmentStatus(AssignmentStatusTypeEnum type);

	/**
	 * Returns collection of work periods.
	 * 
	 * @return
	 * 		the collection of work periods to return
	 */
	public Collection<WorkPeriod> getWorkPeriods();
	
	/**
	 * Sets the collection of work periods.
	 * 
	 * @param list
	 * 		the collection of work periods to set
	 */
	public void setWorkPeriods(Collection<WorkPeriod> list);

	/**
	 * @return the assignmentTime
	 */
	public AssignmentTime getAssignmentTime();

	/**
	 * @param assignmentTime the assignmentTime to set
	 */
	public void setAssignmentTime(AssignmentTime assignmentTime);

	/**
	 * @return the timeAssignAdjusts
	 */
	public Collection<TimeAssignAdjust> getTimeAssignAdjusts();

	/**
	 * @param timeAssignAdjusts the timeAssignAdjusts to set
	 */
	public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts);

	public String getReassignIncidentName();

	public void setReassignIncidentName(String reassignIncidentName);

	public String getReassignIncidentNumber();

	public void setReassignIncidentNumber(String reassignIncidentNumber);
	
	
}
