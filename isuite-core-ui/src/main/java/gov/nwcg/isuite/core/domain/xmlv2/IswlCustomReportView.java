package gov.nwcg.isuite.core.domain.xmlv2;

import java.util.ArrayList;
import java.util.Collection;

import gov.nwcg.isuite.core.domain.SystemRole;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswlCustomReportView", table = "iswl_custom_report_view")
public class IswlCustomReportView {

	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "ViewName", sqlname = "VIEW_NAME", type="STRING")
	private String viewName;

	@XmlTransferField(name = "ViewCode", sqlname = "VIEW_CODE", type="STRING")
	private String viewCode;

	@XmlTransferField(name = "DisplayName", sqlname = "DISPLAY_NAME", type="STRING")
	private String displayName;

//	@ManyToMany(targetEntity = SystemRoleImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//	@JoinTable(name = "isw_custom_report_view_role", joinColumns = { @JoinColumn(name = "view_id", nullable = false, updatable = false) }, inverseJoinColumns = { @JoinColumn(name = "role_id", nullable = false, updatable = false) })
	//private Collection<SystemRole> systemRoles = new ArrayList<SystemRole>();


	public IswlCustomReportView() {
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


}
