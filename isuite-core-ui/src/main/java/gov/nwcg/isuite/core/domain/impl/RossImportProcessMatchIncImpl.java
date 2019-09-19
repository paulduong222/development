package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.RossImportProcess;
import gov.nwcg.isuite.core.domain.RossImportProcessMatchInc;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;

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
@SequenceGenerator(name="SEQ_ROSS_IMP_PROC_MATCH_INC", sequenceName="SEQ_ROSS_IMP_PROC_MATCH_INC")
@Table(name = "isw_ross_imp_proc_match_inc")
public class RossImportProcessMatchIncImpl extends PersistableImpl implements RossImportProcessMatchInc {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_ROSS_IMP_PROC_MATCH_INC")
	private Long id;
	
	@OneToOne(targetEntity=RossImportProcessImpl.class,fetch = FetchType.LAZY)
	@JoinColumn(name = "ROSS_IMP_PROC_ID", insertable=true, updatable=true, nullable = false)
	private RossImportProcess rossImportProcess;
	
	@Column(name = "ROSS_IMP_PROC_ID", insertable=false, updatable=false)
	private Long rossImportProcessId;
	
	@Column(name = "MATCHING_INCIDENT_ID")
	private Long matchingIncidentId;
	
	
	public RossImportProcessMatchIncImpl(){
		
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
	 * @return the rossImportProcessId
	 */
	public Long getRossImportProcessId() {
		return rossImportProcessId;
	}

	/**
	 * @param rossImportProcessId the rossImportProcessId to set
	 */
	public void setRossImportProcessId(Long rossImportProcessId) {
		this.rossImportProcessId = rossImportProcessId;
	}

	/**
	 * @return the matchingIncidentId
	 */
	public Long getMatchingIncidentId() {
		return matchingIncidentId;
	}

	/**
	 * @param matchingIncidentId the matchingIncidentId to set
	 */
	public void setMatchingIncidentId(Long matchingIncidentId) {
		this.matchingIncidentId = matchingIncidentId;
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
		RossImportProcessMatchIncImpl o = (RossImportProcessMatchIncImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,rossImportProcessId,matchingIncidentId},
				new Object[]{o.id,o.rossImportProcessId,o.matchingIncidentId})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,rossImportProcessId,matchingIncidentId})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("rossImportProcessId", rossImportProcessId)
		.append("matchingIncidentId", matchingIncidentId)
		.appendSuper(super.toString())
		.toString();
	}
	
}
