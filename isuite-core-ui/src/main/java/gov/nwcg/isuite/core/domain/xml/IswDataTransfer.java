package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

@XmlTransferTable(name = "IswDataTransfer", table="isw_data_transfer")
public class IswDataTransfer {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_DATA_TRANSFER", type="LONG")
	private Long id = 0L;

	@XmlTransferField(name = "FileName", sqlname="FILENAME", type="STRING")
	private String filename;

	@XmlTransferField(name = "Email", sqlname="EMAIL", type="STRING")
	private String email;

	@XmlTransferField(name = "Phone", sqlname="PHONE", type="STRING")
	private String phone;

	@XmlTransferField(name = "FilePassword", sqlname="FILE_PASSWORD", type="STRING")
	private String filePassword;

	@XmlTransferField(name = "SourceSystem", sqlname="SOURCE_SYSTEM", type="STRING")
	private String sourceSystem;

	@XmlTransferField(name = "StoredFilepath", sqlname="STORED_FILEPATH", type="STRING")
	private String storedFilepath;
	
	@XmlTransferField(name = "IncidentId", sqlname="INCIDENT_ID", type="LONG")
	private Long incidentId;

	@XmlTransferField(name = "IncidentGroupId", sqlname="INCIDENT_GROUP_ID", type="LONG")
	private Long incidentGroupId;

	/**
	 * Default constructor.
	 *
	 */
	public IswDataTransfer() {
		super();
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the filePassword
	 */
	public String getFilePassword() {
		return filePassword;
	}

	/**
	 * @param filePassword the filePassword to set
	 */
	public void setFilePassword(String filePassword) {
		this.filePassword = filePassword;
	}

	/**
	 * @return the sourceSystem
	 */
	public String getSourceSystem() {
		return sourceSystem;
	}

	/**
	 * @param sourceSystem the sourceSystem to set
	 */
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}

	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}

	/**
	 * @return the storedFilepath
	 */
	public String getStoredFilepath() {
		return storedFilepath;
	}

	/**
	 * @param storedFilepath the storedFilepath to set
	 */
	public void setStoredFilepath(String storedFilepath) {
		this.storedFilepath = storedFilepath;
	}

}
