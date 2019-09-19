package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RossImportProcessDataError;
import gov.nwcg.isuite.core.domain.RossResError;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.core.domain.RossXmlFileData;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ROSS_IMP_PROC_DATA_ERROR", sequenceName="SEQ_ROSS_IMP_PROC_DATA_ERROR")
@Table(name = "isw_ross_imp_proc_data_error")
public class RossImportProcessDataErrorImpl extends PersistableImpl implements RossImportProcessDataError {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ROSS_IMP_PROC_DATA_ERROR")
	private Long id;
	
	@ManyToOne(targetEntity=RossXmlFileDataImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ROSS_XML_FILE_DATA_ID", insertable=true, updatable=true, nullable = false)
	private RossXmlFileData rossXmlFileData;
	
	@Column(name = "ROSS_XML_FILE_DATA_ID",insertable=false,updatable=false,nullable=false)
	private Long rossXmlFileDataId;

	@ManyToOne(targetEntity=RossXmlFileImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ROSS_XML_FILE_ID", insertable=true, updatable=true, nullable = false)
	private RossXmlFile rossXmlFile;
	
	@Column(name = "ROSS_XML_FILE_ID",insertable=false,updatable=false,nullable=false)
	private Long rossXmlFileId;
	
	@Column(name = "EXCLUDE_FROM_IMPORT")
	private Short excludeFromImport;
	
	@Column(name = "ERROR_DESC", length=255)
	private String errorDescription;
	
	@Column(name = "NEW_VALUE", length=90)
	private String newValue;
	
	@Column(name = "NEW_VALUE_TYPE", length=60)
	private String newValueType;
	
	@OneToOne(targetEntity=RossResErrorImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ROSS_RES_ERROR_ID", insertable=true, updatable=true, nullable = false)
	private RossResError rossResError;
	
	@Column(name = "ROSS_RES_ERROR_ID", insertable=false, updatable=false, nullable = false)
	private Long rossResErrorId;
	
	public RossImportProcessDataErrorImpl(){
		
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
	 * @return the rossXmlFileData
	 */
	public RossXmlFileData getRossXmlFileData() {
		return rossXmlFileData;
	}

	/**
	 * @param rossXmlFileData the rossXmlFileData to set
	 */
	public void setRossXmlFileData(RossXmlFileData rossXmlFileData) {
		this.rossXmlFileData = rossXmlFileData;
	}

	/**
	 * @return the rossXmlFileDataId
	 */
	public Long getRossXmlFileDataId() {
		return rossXmlFileDataId;
	}

	/**
	 * @param rossXmlFileDataId the rossXmlFileDataId to set
	 */
	public void setRossXmlFileDataId(Long rossXmlFileDataId) {
		this.rossXmlFileDataId = rossXmlFileDataId;
	}

	/**
	 * @return the excludeFromImport
	 */
	public Short getExcludeFromImport() {
		return excludeFromImport;
	}

	/**
	 * @param excludeFromImport the excludeFromImport to set
	 */
	public void setExcludeFromImport(Short excludeFromImport) {
		this.excludeFromImport = excludeFromImport;
	}

	/**
	 * @return the errorDescription
	 */
	public String getErrorDescription() {
		return errorDescription;
	}

	/**
	 * @param errorDescription the errorDescription to set
	 */
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}

	/**
	 * @return the newValue
	 */
	public String getNewValue() {
		return newValue;
	}

	/**
	 * @param newValue the newValue to set
	 */
	public void setNewValue(String newValue) {
		this.newValue = newValue;
	}

	/**
	 * @return the newValueType
	 */
	public String getNewValueType() {
		return newValueType;
	}

	/**
	 * @param newValueType the newValueType to set
	 */
	public void setNewValueType(String newValueType) {
		this.newValueType = newValueType;
	}

	/**
	 * @return the rossResError
	 */
	public RossResError getRossResError() {
		return rossResError;
	}

	/**
	 * @param rossResError the rossResError to set
	 */
	public void setRossResError(RossResError rossResError) {
		this.rossResError = rossResError;
	}

	/**
	 * @return the rossResErrorId
	 */
	public Long getRossResErrorId() {
		return rossResErrorId;
	}

	/**
	 * @param rossResErrorId the rossResErrorId to set
	 */
	public void setRossResErrorId(Long rossResErrorId) {
		this.rossResErrorId = rossResErrorId;
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
		RossImportProcessDataErrorImpl o = (RossImportProcessDataErrorImpl)obj;
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
	 * @return the rossXmlFile
	 */
	public RossXmlFile getRossXmlFile() {
		return rossXmlFile;
	}

	/**
	 * @param rossXmlFile the rossXmlFile to set
	 */
	public void setRossXmlFile(RossXmlFile rossXmlFile) {
		this.rossXmlFile = rossXmlFile;
	}

	/**
	 * @return the rossXmlFileId
	 */
	public Long getRossXmlFileId() {
		return rossXmlFileId;
	}

	/**
	 * @param rossXmlFileId the rossXmlFileId to set
	 */
	public void setRossXmlFileId(Long rossXmlFileId) {
		this.rossXmlFileId = rossXmlFileId;
	}

	
}
