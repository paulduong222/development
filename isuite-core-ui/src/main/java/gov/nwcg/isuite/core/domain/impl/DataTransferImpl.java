package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.DataTransfer;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_DATA_TRANSFER", sequenceName="SEQ_DATA_TRANSFER")
@Table(name="isw_data_transfer")
public class DataTransferImpl extends PersistableImpl implements DataTransfer {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_DATA_TRANSFER")
	private Long id = 0L;

	@Column(name="FILENAME", length=200, nullable=false)
	private String filename;

	@Column(name="EMAIL", length=200, nullable=true)
	private String email;

	@Column(name="PHONE", length=50, nullable=true)
	private String phone;

	@Column(name="FILE_PASSWORD", length=200, nullable=true)
	private String filePassword;

	@Column(name="SOURCE_SYSTEM", length=50, nullable=true)
	private String sourceSystem;

	@Column(name="STORED_FILEPATH", length=200, nullable=true)
	private String storedFilepath;
	
	@Column(name="INCIDENT_ID", insertable = true, updatable = true, nullable = true, length=19)
	private Long incidentId;

	@Column(name="INCIDENT_GROUP_ID", insertable = true, updatable = true, nullable = true, length=19)
	private Long incidentGroupId;

	/**
	 * Default constructor.
	 *
	 */
	public DataTransferImpl() {
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
