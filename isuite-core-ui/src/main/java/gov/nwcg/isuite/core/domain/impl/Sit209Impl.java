package gov.nwcg.isuite.core.domain.impl;

import java.util.Collection;

import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.Kind;
import gov.nwcg.isuite.core.domain.Sit209;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.BatchSize;

@Entity
@SequenceGenerator(name="SEQ_SIT_209", sequenceName="SEQ_SIT_209")
@Table(name = "iswl_sit_209")
@NamedQuery(name=ReferenceDataQuery.IS_SIT_209_CODE_UNIQUE,query=ReferenceDataQuery.IS_SIT_209_CODE_UNIQUE_QUERY)
public class Sit209Impl extends PersistableImpl implements Sit209 {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_SIT_209")
   private Long id = 0L;
   
   @Column(name = "CODE", length = 10, nullable=false)
   private String code;
   
   @Column(name = "DESCRIPTION", length = 75, nullable=false)
   private String description;
   
   @Column(name = "IS_STANDARD",nullable=false)
   private Boolean standard;
   
   @ManyToOne(targetEntity=IncidentImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_ID")
   private Incident incident;
   
   @Column(name = "INCIDENT_ID", insertable = false, updatable = false, unique=false)
   private Long incidentId;
   
   @ManyToOne(targetEntity=IncidentGroupImpl.class, fetch = FetchType.LAZY)
   @JoinColumn(name = "INCIDENT_GROUP_ID")
   private IncidentGroup incidentGroup;
   
   @Column(name = "INCIDENT_GROUP_ID", insertable = false, updatable = false, unique=false)
   private Long incidentGroupId;
   
   @Column(name = "IS_ACTIVE",nullable=false)
   @Enumerated(EnumType.STRING)
   private StringBooleanEnum active;
   
   @OneToMany(targetEntity=KindImpl.class, cascade=CascadeType.ALL, mappedBy="sit209")
   @BatchSize(size=200)
   private Collection<Kind> kinds;
   
   public Sit209Impl() {
      super();
   }

   /* 
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#getId()
    */
   public Long getId() {
      return this.id;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.Persistable#setId(java.lang.Long)
    */
   public void setId(Long id) {
      this.id = id;
   } 

   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#setCode(java.lang.String)
    */
   public void setCode(String code) {
      this.code = code;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#getCode()
    */
   public String getCode() {
      return this.code;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#getDescription()
    */
   public String getDescription() {
      return this.description;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#isStandard()
    */
   public Boolean isStandard() {
      return this.standard;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#setStandard(java.lang.Boolean)
    */
   public void setStandard(Boolean isStandard) {
      this.standard = isStandard;
   }    
   
   /**
	 * @param incident the incident to set
	 */
	public void setIncident(Incident incident) {
		this.incident = incident;
	}
	
	/**
	 * @return the incident
	 */
	public Incident getIncident() {
		return incident;
	}
	
	/**
	 * @param incidentId the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	
	/**
	 * @param incidentGroup the incidentGroup to set
	 */
	public void setIncidentGroup(IncidentGroup incidentGroup) {
		this.incidentGroup = incidentGroup;
	}
	
	/**
	 * @return the incidentGroup
	 */
	public IncidentGroup getIncidentGroup() {
		return incidentGroup;
	}
	
	/**
	 * @param incidentGroupId the incidentGroupId to set
	 */
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
	/**
	 * @return the incidentGroupId
	 */
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	
	/**
	 * @param active the active to set
	 */
	public void setActive(StringBooleanEnum active) {
		this.active = active;
	}
	
	/**
	 * @return the active
	 */
	public StringBooleanEnum isActive() {
		return active;
	}

	/*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#setKinds(java.util.Collection)
    */
   public void setKinds(Collection<Kind> kinds) {
	   this.kinds = kinds;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.Sit209#getKinds()
    */
   public Collection<Kind> getKinds() {
	   return kinds;
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
      Sit209Impl o = (Sit209Impl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,code,description,standard},
      			new Object[]{o.id,o.code,o.description,o.standard})
  	    .appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,code,description,standard})
	  	.toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("code", code)
	       .append("description", description)
	       .append("standard",standard)
	       .appendSuper(super.toString())
	       .toString();
   }   
}
