package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;


public interface IncidentResourceGetFilter extends Filter {

	public Long getId();
	
	public void setId(Long id);
	
	public Long getAssignmentId();
	
	public void setAssignmentId(Long id);
	
}
