package gov.nwcg.isuite.core.domain;





public interface WorkPeriodAssignment {

	/**
	 * Returns the work period.
	 * 
	 * @return
	 * 		the work period to return
	 */
	public WorkPeriod getWorkPeriod();
	
	/**
	 * Sets the work period.
	 * 
	 * @param wp
	 * 			the work period to set
	 */
	public void setWorkPeriod(WorkPeriod wp);
	
	/**
	 * Returns the WorkPeriod id.
	 * 
	 * @return
	 * 		the work period id to return
	 */
	public Long getWorkPeriodId();
	
	/**
	 * Sets the WorkPeriod id.
	 * 
	 * @param id
	 * 		the WorkPeriod id to set
	 */
	public void setWorkPeriodId(Long id);
	
	/**
	 * Returns the assignment id.
	 * 
	 * @return
	 * 		the assignment id to return
	 */
	public Long getAssignmentId();
	
	/**
	 * Sets the assignment id.
	 * 
	 * @param id
	 * 		the assignment id to set
	 */
	public void setAssignmentId(Long id);


	/**
	 * Returns the assignment.
	 * 
	 * @return
	 * 		the assignment to return
	 */
	public Assignment getAssignment();
	
	/**
	 * Sets the Assignment.
	 * 
	 * @param assignment
	 * 			the assignment to set
	 */
	public void setAssignment(Assignment assignment);
	
}
