package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.Incident;
import gov.nwcg.isuite.core.domain.IncidentGroup;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.persistence.hibernate.query.ReferenceDataQuery;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Collection;

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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author mpoll
 *
 */

@Entity
@SequenceGenerator(name="SEQ_JETPORT", sequenceName="SEQ_JETPORT")
@Table(name="iswl_jet_port")
@NamedQuery(name=ReferenceDataQuery.IS_JET_PORT_CODE_UNIQUE,query=ReferenceDataQuery.IS_JET_PORT_CODE_UNIQUE_QUERY)
public class JetPortImpl extends PersistableImpl implements JetPort {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_JETPORT")
   private Long id = 0L;
   
   @Column(name="CODE", length=4)
   private String code;
   
   @Column(name="DESCRIPTION", length=100)
   private String description;

   @Column(name="IS_STANDARD")
   private Boolean standard;

	@ManyToOne(targetEntity=CountrySubdivisionImpl.class)
	@JoinColumn(name = "COUNTRY_SUBDIVISION_ID", insertable = true, updatable = true, unique = false, nullable = true)
	@OnDelete(action=OnDeleteAction.NO_ACTION)
	@BatchSize(size=100)
	private CountrySubdivision countrySubdivision;

	@Column(name="COUNTRY_SUBDIVISION_ID", insertable = false, updatable = false, nullable = true)
	private Long countrySubdivisionId;
	
	@OneToMany(targetEntity=WorkPeriodImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="ciArrivalJetPort")
	private Collection<WorkPeriod> workPeriods;
	
	@OneToMany(targetEntity=AirTravelImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="jetPort")
	private Collection<AirTravel> airTravels;
	
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
	
	@Column(name = "OBSOLETE")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum obsolete;

   
   /**
    * Default Constructor
    */
   public JetPortImpl() {
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

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.JetPort#getCode()
    */
   public String getCode() {
      return code;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.JetPort#setCode(java.lang.String)
    */
   public void setCode(String code) {
      if (code == null) {
         throw new IllegalArgumentException("code can not be null");
      }
      this.code = code;
   }


   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.JetPort#getDescription()
    */
   public String getDescription() {
      return description;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.JetPort#setDescription(java.lang.String)
    */
   public void setDescription(String description) {
      this.description = description;
   }

  
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.JetPort#isStandard()
    */
   public Boolean isStandard() {
      return standard;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.JetPort#setStandard(java.lang.Boolean)
    */
   public void setStandard(Boolean standard) {
      this.standard = standard;
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
       JetPortImpl o = (JetPortImpl)obj;
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

	public CountrySubdivision getCountrySubdivision() {
		return countrySubdivision;
	}

	public void setCountrySubdivision(CountrySubdivision countrySubdivision) {
		this.countrySubdivision = countrySubdivision;
	}

	public Long getCountrySubdivisionId() {
		return countrySubdivisionId;
	}

	public void setCountrySubdivisionId(Long countrySubdivisionId) {
		this.countrySubdivisionId = countrySubdivisionId;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.JetPort#setWorkPeriods(java.util.Collection)
	 */
	public void setWorkPeriods(Collection<WorkPeriod> workPeriods) {
		this.workPeriods = workPeriods;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.JetPort#getWorkPeriods()
	 */
	public Collection<WorkPeriod> getWorkPeriods() {
		return workPeriods;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.JetPort#setAirTravels(java.util.Collection)
	 */
	public void setAirTravels(Collection<AirTravel> airTravels) {
		this.airTravels = airTravels;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.JetPort#getAirTravels()
	 */
	public Collection<AirTravel> getAirTravels() {
		return airTravels;
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

	/**
	 * @param obsolete
	 */
	public void setObsolete(StringBooleanEnum obsolete) {
		this.obsolete = obsolete;
	}

	/**
	 * @return obsolete
	 */
	public StringBooleanEnum getObsolete() {
		return obsolete;
	}
    
}
