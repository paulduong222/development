package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.core.domain.SystemRole;

import java.util.Collection;

public interface CustomReportView {

	public Long getId();

	public void setId(Long id);

	public String getViewName();

	public void setViewName(String viewName);

	public String getViewCode();

	public void setViewCode(String viewCode);

	public String getDisplayName();

	public void setDisplayName(String displayName);

	public Collection<SystemRole> getSystemRoles();

	public void setSystemRoles(Collection<SystemRole> systemRoles);
	
	public Collection<CustomReportViewField> getCustomReportViewFields();
	
	public void setCustomReportViewFields(Collection<CustomReportViewField> customReportViewFields);

}