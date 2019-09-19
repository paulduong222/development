package gov.nwcg.isuite.core.domain.xmlv3;

import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransferv2.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;

@XmlTransferTable(name = "DataTransfer", table = "datatransfer")
public class DataTransferXml {

	@XmlTransferField(name = "ExportDate", sqlname="", type="STRING")
	private String exportDate;
	
	@XmlTransferField(name = "Password", sqlname="", type="STRING")
	private String password;
	
	@XmlTransferField(name = "Source", sqlname="", type="STRING")
	private String source;

	@XmlTransferField(name = "SourceRevisionLevel", sqlname="", type="STRING")
	private String sourceRevisionLevel;

	@XmlTransferField(name = "IncidentTI", sqlname="", type="STRING")
	private String incidentTI;
	
	@XmlTransferField(name = "IncidentGroupTI", sqlname="", type="STRING")
	private String incidentGroupTI;

	@XmlTransferField(name = "IncidentGroupName", sqlname="", type="STRING")
	private String incidentGroupName;

	public String getExportDate() {
		return exportDate;
	}

	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSourceRevisionLevel() {
		return sourceRevisionLevel;
	}

	public void setSourceRevisionLevel(String sourceRevisionLevel) {
		this.sourceRevisionLevel = sourceRevisionLevel;
	}

	public String getIncidentTI() {
		return incidentTI;
	}

	public void setIncidentTI(String incidentTI) {
		this.incidentTI = incidentTI;
	}

	public String getIncidentGroupTI() {
		return incidentGroupTI;
	}

	public void setIncidentGroupTI(String incidentGroupTI) {
		this.incidentGroupTI = incidentGroupTI;
	}

	public String getIncidentGroupName() {
		return incidentGroupName;
	}

	public void setIncidentGroupName(String incidentGroupName) {
		this.incidentGroupName = incidentGroupName;
	}

	
}
