package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

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
	
	@XmlTransferField(name = "IncidentGroup", sqlname="", type="COMPLEX", target=IswIncidentGroup.class)
	private IswIncidentGroup incidentGroup=null;
	
	//@XmlTransferField(name = "Incident", sqlname="", type="COMPLEX", target=IswIncident.class)
	//private IswIncident incident=null;

	@XmlTransferField(name = "Incident", sqlname="", type="COMPLEX", target=IswIncident.class)
	private Collection<IswIncident> incidents=new ArrayList<IswIncident>();
	
	/**
	 * @return the exportDate
	 */
	public String getExportDate() {
		return exportDate;
	}

	/**
	 * @param exportDate the exportDate to set
	 */
	public void setExportDate(String exportDate) {
		this.exportDate = exportDate;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	/**
	 * @return the sourceRevisionLevel
	 */
	public String getSourceRevisionLevel() {
		return sourceRevisionLevel;
	}

	/**
	 * @param sourceRevisionLevel the sourceRevisionLevel to set
	 */
	public void setSourceRevisionLevel(String sourceRevisionLevel) {
		this.sourceRevisionLevel = sourceRevisionLevel;
	}

	/**
	 * @return the incidentGroup
	 */
	public IswIncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IswIncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}

	/**
	 * @return the incidents
	 */
	public Collection<IswIncident> getIncidents() {
		return incidents;
	}

	/**
	 * @param incidents the incidents to set
	 */
	public void setIncidents(Collection<IswIncident> incidents) {
		this.incidents = incidents;
	}


}
