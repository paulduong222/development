package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.domain.RossImportProcessMatchInc;
import gov.nwcg.isuite.core.domain.RossXmlFile;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_ROSS_IMP_PROC", sequenceName="SEQ_ROSS_IMP_PROC")
@Table(name = "isw_ross_imp_proc")
public class RossImportProcessImpl extends PersistableImpl implements RossImportProcess {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ROSS_IMP_PROC")
	private Long id;

	@OneToOne(targetEntity=RossXmlFileImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ROSS_XML_FILE_ID", insertable=true, updatable=true, nullable = false)
	private RossXmlFile rossXmlFile;

	@Column(name = "ROSS_XML_FILE_ID", insertable=false, updatable=false)
	private Long rossXmlFileId;

	@Column(name = "COMPLETED_STAGE", length=40)
	private String completedStage;

	@OneToOne(targetEntity=RossImportProcessMatchIncImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "rossImportProcess")
	private RossImportProcessMatchInc rossImportProcessMatchIncident;
	
	public RossImportProcessImpl(){

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

	/**
	 * @return the completedStage
	 */
	public String getCompletedStage() {
		return completedStage;
	}

	/**
	 * @param completedStage the completedStage to set
	 */
	public void setCompletedStage(String completedStage) {
		this.completedStage = completedStage;
	}

	/**
	 * @return the rossImportProcessMatchIncident
	 */
	public RossImportProcessMatchInc getRossImportProcessMatchIncident() {
		return rossImportProcessMatchIncident;
	}

	/**
	 * @param rossImportProcessMatchIncident the rossImportProcessMatchIncident to set
	 */
	public void setRossImportProcessMatchIncident(
			RossImportProcessMatchInc rossImportProcessMatchIncident) {
		this.rossImportProcessMatchIncident = rossImportProcessMatchIncident;
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
		RossImportProcessImpl o = (RossImportProcessImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,rossXmlFileId,completedStage},
				new Object[]{o.id,o.rossXmlFileId,o.completedStage})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,rossXmlFileId,completedStage})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("rossXmlFileId", rossXmlFileId)
		.append("completedStage", completedStage)
		.appendSuper(super.toString())
		.toString();
	}


}
