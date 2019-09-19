package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface ResourceDuplicateFilter extends Filter {
	
	public String getResourceName();
	
	public void setResourceName(String val);
	
	public String getFirstName();
	
	public void setFirstName(String val);
	
	public String getLastName();
	
	public void setLastName(String val);

	public Boolean getEnabled();
	
	public void setEnabled(Boolean val);

	public void setOrganizationId(Long id);
	
	public Long getOrganizationId();
	
	public void setPrimaryDispatchCenterId(Long id);
	
	public Long getPrimaryDispatchCenterId();
	
	public void setResourceId(Long id);
	
	public Long getResourceId();
	
	public void setWorkAreaOnly(Boolean val);
	
	public Boolean getWorkAreaOnly();
}
