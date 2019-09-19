package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.IncidentResourceGetFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;


/**
 * @author bsteiner
 *
 */
public class IncidentResourceGetFilterImpl extends FilterImpl implements IncidentResourceGetFilter {
	
	private static final long serialVersionUID = -3098056085012683579L;

	private Long id=0L;
	private Long assignmentId=0L;
	
    
	public IncidentResourceGetFilterImpl(){
		
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Long getAssignmentId() {
		return assignmentId;
	}


	public void setAssignmentId(Long assignmentId) {
		this.assignmentId = assignmentId;
	}

}