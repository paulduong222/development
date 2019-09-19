package gov.nwcg.isuite.core.domain.xmlv2;

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

	@XmlTransferField(name = "DataTransferInc", sqlname="", type="COMPLEX")
	private Collection<DataTransferInc> dataTransferIncs = new ArrayList<DataTransferInc>();
	
	@XmlTransferField(name = "DataTransferIncGroup", sqlname="", type="COMPLEX")
	private Collection<DataTransferIncGroup> dataTransferIncGroups = new ArrayList<DataTransferIncGroup>();
	
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

	public Collection<DataTransferInc> getDataTransferIncs() {
		return dataTransferIncs;
	}

	public void setDataTransferIncs(Collection<DataTransferInc> dataTransferIncs) {
		this.dataTransferIncs = dataTransferIncs;
	}

	public Collection<DataTransferIncGroup> getDataTransferIncGroups() {
		return dataTransferIncGroups;
	}

	public void setDataTransferIncGroups(
			Collection<DataTransferIncGroup> dataTransferIncGroups) {
		this.dataTransferIncGroups = dataTransferIncGroups;
	}
	
}
