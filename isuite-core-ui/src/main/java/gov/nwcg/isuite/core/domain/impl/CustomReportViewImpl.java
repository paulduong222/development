package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.CustomReportView;
import gov.nwcg.isuite.core.domain.CustomReportViewField;
import gov.nwcg.isuite.core.domain.SystemRole;

import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "iswl_custom_report_view")
public class CustomReportViewImpl implements CustomReportView  {
	
	@Id
	@Column(name = "ID", insertable = false, updatable = false)
	private Long id;

	@Column(name = "VIEW_NAME", insertable = false, updatable = false)
	private String viewName;

	@Column(name = "VIEW_CODE", insertable = false, updatable = false)
	private String viewCode;

	@Column(name = "DISPLAY_NAME", insertable = false, updatable = false)
	private String displayName;
	
	@ManyToMany(targetEntity=SystemRoleImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "isw_custom_report_view_role", 
			joinColumns = { 
				@JoinColumn(name = "view_id", nullable = false, updatable = false) }, 
			inverseJoinColumns = { 
				@JoinColumn(name = "role_id", nullable = false, updatable = false) })
	private Collection<SystemRole> systemRoles = new ArrayList<SystemRole>();
	
	@OneToMany(targetEntity=CustomReportViewFieldImpl.class,cascade=CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="customReportView")
	private Collection<CustomReportViewField> customReportViewFields = new ArrayList<CustomReportViewField>();
	
	public CustomReportViewImpl() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getViewName() {
		return viewName;
	}

	public void setViewName(String viewName) {
		this.viewName = viewName;
	}

	public String getViewCode() {
		return viewCode;
	}

	public void setViewCode(String viewCode) {
		this.viewCode = viewCode;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public Collection<SystemRole> getSystemRoles() {
		return systemRoles;
	}

	public void setSystemRoles(Collection<SystemRole> systemRoles) {
		this.systemRoles = systemRoles;
	}

	public Collection<CustomReportViewField> getCustomReportViewFields() {
		return customReportViewFields;
	}

	public void setCustomReportViewFields(
			Collection<CustomReportViewField> customReportViewFields) {
		this.customReportViewFields = customReportViewFields;
	}
	
}
