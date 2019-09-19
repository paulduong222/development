package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.domain.RossImportProcessDataError;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ROSS_XML_FILE", sequenceName="SEQ_ROSS_XML_FILE")
@Table(name = "isw_ross_xml_file")
public class RossXmlFileImpl extends PersistableImpl implements RossXmlFile {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ROSS_XML_FILE")
	private Long id;
	
	@Column(name = "FILE_NAME", nullable = false, length = 60)
	private String fileName;

	@Column(name = "INCIDENT_NAME", length=90)
	private String incidentName;

	@Column(name = "INCIDENT_NUMBER", length=90)
	private String incidentNumber;
	
	@Column(name = "INCIDENT_EVENT_TYPE", length=90)
	private String incidentEventType;

	@Column(name = "INCIDENT_START_DATE")
	private Date incidentStartDate;
	
	@Column(name = "IMPORT_STATUS", length = 20)
	private String importStatus;

	@Column(name = "ROSS_INC_ID", length = 20)
	private String rossIncId;

	@Column(name = "UNIT_CODE", length = 20)
	private String unitCode;
	
	@Column(name = "IMPORTED_DATE")
	private Date importedDate;
	
	@OneToMany(targetEntity=RossXmlFileDataImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rossXmlFile")
	private Collection<RossXmlFileData> rossXmlFileDatas = new ArrayList<RossXmlFileData>();
	
	@OneToOne(targetEntity=RossImportProcessImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rossXmlFile")
	private RossImportProcess rossImportProcess;
	
	@OneToMany(targetEntity=RossImportProcessDataErrorImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rossXmlFile")
	private Collection<RossImportProcessDataError> rossImportProcessDataErrors = new ArrayList<RossImportProcessDataError>();
	
	public RossXmlFileImpl(){
	
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
	 * @return the fileName
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * @param fileName the fileName to set
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return the importStatus
	 */
	public String getImportStatus() {
		return importStatus;
	}

	/**
	 * @param importStatus the importStatus to set
	 */
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}

	/**
	 * @return the rossXmlFileDatas
	 */
	public Collection<RossXmlFileData> getRossXmlFileDatas() {
		return rossXmlFileDatas;
	}

	/**
	 * @param rossXmlFileDatas the rossXmlFileDatas to set
	 */
	public void setRossXmlFileDatas(Collection<RossXmlFileData> rossXmlFileDatas) {
		this.rossXmlFileDatas = rossXmlFileDatas;
	}

	/**
	 * @return the incidentName
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * @param incidentName the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/**
	 * @return the incidentNumber
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/**
	 * @param incidentNumber the incidentNumber to set
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/**
	 * @return the incidentEventType
	 */
	public String getIncidentEventType() {
		return incidentEventType;
	}

	/**
	 * @param incidentEventType the incidentEventType to set
	 */
	public void setIncidentEventType(String incidentEventType) {
		this.incidentEventType = incidentEventType;
	}

	/**
	 * @return the rossImportProcess
	 */
	public RossImportProcess getRossImportProcess() {
		return rossImportProcess;
	}

	/**
	 * @param rossImportProcess the rossImportProcess to set
	 */
	public void setRossImportProcess(RossImportProcess rossImportProcess) {
		this.rossImportProcess = rossImportProcess;
	}
	
	/**
	 * @return the incidentStartDate
	 */
	public Date getIncidentStartDate() {
		return incidentStartDate;
	}

	/**
	 * @param incidentStartDate the incidentStartDate to set
	 */
	public void setIncidentStartDate(Date incidentStartDate) {
		this.incidentStartDate = incidentStartDate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if ( obj == null ) return false;
		if ( this == obj ) return true;
		if ( getClass() != obj.getClass() ) return false;
		RossXmlFileImpl o = (RossXmlFileImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id},
				new Object[]{o.id})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @return the rossIncId
	 */
	public String getRossIncId() {
		return rossIncId;
	}

	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(String rossIncId) {
		this.rossIncId = rossIncId;
	}

	/**
	 * @return the rossImportProcessDataErrors
	 */
	public Collection<RossImportProcessDataError> getRossImportProcessDataErrors() {
		return rossImportProcessDataErrors;
	}

	/**
	 * @param rossImportProcessDataErrors the rossImportProcessDataErrors to set
	 */
	public void setRossImportProcessDataErrors(
			Collection<RossImportProcessDataError> rossImportProcessDataErrors) {
		this.rossImportProcessDataErrors = rossImportProcessDataErrors;
	}

	/**
	 * @return the importedDate
	 */
	public Date getImportedDate() {
		return importedDate;
	}

	/**
	 * @param importedDate the importedDate to set
	 */
	public void setImportedDate(Date importedDate) {
		this.importedDate = importedDate;
	}

	/**
	 * @return the unitCode
	 */
	public String getUnitCode() {
		return unitCode;
	}

	/**
	 * @param unitCode the unitCode to set
	 */
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
	}



	
}
