
package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.IncidentPrefsOtherFields;
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
@SequenceGenerator(name="SEQ_INCIDENTPREFS_OTHERFIELDS", sequenceName="SEQ_INCIDENTPREFS_OTHERFIELDS")
@Table(name="isw_incident_prefs_otherfields")
public class IncidentPrefsOtherFieldsImpl extends PersistableImpl implements IncidentPrefsOtherFields {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_INCIDENTPREFS_OTHERFIELDS")
	private Long id = 0L;

	@OneToOne(targetEntity=IncidentImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private Incident incident;

	@Column(name="INCIDENT_ID", insertable = false, updatable = false, nullable = true)
	private Long incidentId;

	@OneToOne(targetEntity=IncidentGroupImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name = "INCIDENT_GROUP_ID", insertable = true, updatable = true, unique = false, nullable = true)
	private IncidentGroup incidentGroup;

	@Column(name="INCIDENT_GROUP_ID", insertable = false, updatable = false, nullable = true)
	private Long incidentGroupId;

	@Column(name="OTHER_1_LABEL", length=60)
	private String other1Label;

	@Column(name="OTHER_2_LABEL", length=60)
	private String other2Label;

	@Column(name="OTHER_3_LABEL", length=60)
	private String other3Label;

	/**
	 * Default Constructor
	 */
	public IncidentPrefsOtherFieldsImpl() {
		super();
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#getId()
	 */
	@Override
	public Long getId() {
		return this.id;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl#setId(java.lang.Long)
	 */
	@Override
	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}

	/**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
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
	 * @return the other1Label
	 */
	public String getOther1Label() {
		return other1Label;
	}

	/**
	 * @param other1Label the other1Label to set
	 */
	public void setOther1Label(String other1Label) {
		this.other1Label = other1Label;
	}

	/**
	 * @return the other2Label
	 */
	public String getOther2Label() {
		return other2Label;
	}

	/**
	 * @param other2Label the other2Label to set
	 */
	public void setOther2Label(String other2Label) {
		this.other2Label = other2Label;
	}

	/**
	 * @return the other3Label
	 */
	public String getOther3Label() {
		return other3Label;
	}

	/**
	 * @param other3Label the other3Label to set
	 */
	public void setOther3Label(String other3Label) {
		this.other3Label = other3Label;
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
		IncidentPrefsOtherFieldsImpl o = (IncidentPrefsOtherFieldsImpl)obj;
		return new EqualsBuilder()
		.append(new Object[]{id,incidentId,other1Label,other2Label,other3Label},
				new Object[]{o.id,o.incidentId,o.other1Label,o.other2Label,o.other3Label})
				.appendSuper(super.equals(o))
				.isEquals();
	}   

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(31,33)
		.append(super.hashCode())
		.append(new Object[]{id,incidentId,other1Label,other2Label,other3Label})
		.toHashCode();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
		.append("id", id)
		.append("incidentId", incidentId)
		.append("other1Label", other1Label)
		.append("other2Label", other2Label)
		.append("other3Label", other3Label)
		.appendSuper(super.toString())
		.toString();
	}

	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}

	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
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
	
}
