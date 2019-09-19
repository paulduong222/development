package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.domain.IncidentAccountCode;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.core.domain.WorkPeriod;
import gov.nwcg.isuite.core.domain.WorkPeriodOvernightStayInfo;
import gov.nwcg.isuite.core.domain.WorkPeriodQuestionValue;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

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
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

@Entity
@SequenceGenerator(name="SEQ_WORK_PERIOD", sequenceName="SEQ_WORK_PERIOD")
@Table(name="isw_work_period")
public class WorkPeriodImpl extends PersistableImpl implements WorkPeriod {
   
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_WORK_PERIOD")
	private Long id = 0L;
   
	@OneToMany(targetEntity=AssignmentImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "isw_work_period_assignment", 
			   joinColumns = 
			   		{@JoinColumn(name = "work_period_id", unique = true, updatable = false) },
			   inverseJoinColumns = 
			   		{ @JoinColumn(name = "assignment_id", nullable = false, updatable = false) }
	)
	private Collection<Assignment> assignments;
	
	@OneToOne(targetEntity=IncidentResourceImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="INCIDENT_RESOURCE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private IncidentResource incidentResource;

	@Column(name="INCIDENT_RESOURCE_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long incidentResourceId;

	@OneToOne(targetEntity=IncidentAccountCodeImpl.class, fetch=FetchType.LAZY)
	@JoinColumn(name="DEF_INCIDENT_ACCOUNT_CODE_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private IncidentAccountCode defIncidentAccountCode;

	@Column(name="DEF_INCIDENT_ACCOUNT_CODE_ID", insertable=false, updatable=false, nullable=true, unique=false)
	private Long defIncidentAccountCodeId;
	
    @ManyToOne(targetEntity=JetPortImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name="CI_ARRIVAL_JET_PORT_ID", insertable=true, updatable=true, unique=false, nullable=true)
    private JetPort ciArrivalJetPort;

    @Column(name="CI_ARRIVAL_JET_PORT_ID", insertable=false, updatable=false, nullable=true, unique=false)
    private Long ciArrivalJetPortId;

    @OneToOne(targetEntity=ResourceMobilizationImpl.class,cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="CI_MOBILIZATION_ID", insertable=true, updatable=true, unique=false, nullable=true)
    private ResourceMobilization ciResourceMobilization;
    
	@Column(name="CI_MOBILIZATION_ID",length=19, insertable = false, updatable = false)
	private Long ciMobilizationId;

	@Column(name = "CI_TRAVEL_METHOD")
    @Enumerated(EnumType.STRING)
	private TravelMethodTypeEnum ciTravelMethod;
	
	@Column(name="CI_RENTAL_LOCATION", length=50)
	private String ciRentalLocation;
   
	@Column(name="CI_CHECK_IN_DATE", nullable=true)
	private Date ciCheckInDate;
   
	@Column(name="CI_FIRST_WORK_DATE", nullable=true)
	private Date ciFirstWorkDate;

	@Column(name="CI_PRE_PLANNING_REMARKS", length=1000)
	private String ciPrePlanningRemarks;
   
	@Column(name="CI_LENGTH_AT_ASSIGNMENT")
	private Long ciLengthAtAssignment;
	
	@Column(name="DM_TENTATIVE_DEMOB_CITY", length=50)
	private String dmTentativeDemobCity;

    @OneToOne(targetEntity=CountrySubdivisionImpl.class, fetch=FetchType.LAZY)
    @JoinColumn(name="DM_TENTATIVE_DEMOB_STATE_ID", insertable=true, updatable=true, unique=false, nullable=true)
    private CountrySubdivision dmTentativeDemobState;

    @Column(name="DM_TENTATIVE_DEMOB_STATE_ID", insertable=false, updatable=false, nullable=true, unique=false)
    private Long dmTentativeDemobStateId;
   
	@Column(name="DM_TENTATIVE_ARRIVAL_DATE", nullable=true)
	private Date dmTentativeArrivalDate;

    @Column(name="DM_RELEASE_DATE", nullable=true)
	private Date dmReleaseDate;

	@Column(name="DM_TENTATIVE_RELEASE_DATE", nullable=true)
	private Date dmTentativeReleaseDate;
	
	@Column(name="DM_IS_REASSIGNABLE", nullable=true)
	private Boolean dmReAssignable=true;

	@Column(name="DM_IS_REST_OVERNIGHT", nullable=true)
	private Boolean dmRestOvernight=false;

	@Column(name="DM_IS_RELEASE_DISPATCH_NOTIF", nullable=true)
	private Boolean dmReleaseDispatchNotified=false;

	@Column(name="DM_IS_PLANNING_DISPATCH_NOTIF", nullable=true)
	private Boolean dmPlanningDispatchNotified=false;

	@Column(name="DM_IS_CHECKOUT_FORM_PRINTED", nullable=true)
	private Boolean dmCheckoutFormPrinted=false;
	
	@Column(name="DM_RELEASE_REMARKS", length=1000)
	private String dmReleaseRemarks;

	@Column(name="DM_PLANNING_REMARKS", length=1000)
	private String dmPlanningRemarks;

	@OneToOne(targetEntity=AirTravelImpl.class, cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="DM_AIR_TRAVEL_ID", insertable=true, updatable=true, unique=false, nullable=true)
	private AirTravel dmAirTravel;

	@Column(name="DM_AIR_TRAVEL_ID",length=19, insertable = false, updatable = false)
	private Long dmAirTravelId;
	
	@Column(name = "DM_TRAVEL_METHOD")
    @Enumerated(EnumType.STRING)
	private TravelMethodTypeEnum dmTravelMethod;
   
	@OneToMany(targetEntity=WorkPeriodQuestionValueImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "workPeriod")
	private Collection<WorkPeriodQuestionValue> workPeriodQuestionValues;
	
	@Column(name="CI_TENTATIVE_ARRIVAL_DATE")
	private Date ciTentativeArrivalDate;
	
	@OneToMany(targetEntity=WorkPeriodOvernightStayInfoImpl.class,cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "workPeriod")
    @org.hibernate.annotations.Cascade(value=org.hibernate.annotations.CascadeType.DELETE_ORPHAN)
	private Collection<WorkPeriodOvernightStayInfo> wpOvernightStayInfos = new ArrayList<WorkPeriodOvernightStayInfo>();

	@Column(name="IS_GROUND_SUPPORT", nullable=true)
	private Boolean groundSupport=false;
	
	
	public WorkPeriodImpl(){
		
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
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getAssignments()
	 */
	public Collection<Assignment> getAssignments() {
		return assignments;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setAssignments(java.util.Collection)
	 */
	public void setAssignments(Collection<Assignment> assignments) {
		this.assignments = assignments;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getIncidentResource()
	 */
	public IncidentResource getIncidentResource() {
		return incidentResource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setIncidentResource(gov.nwcg.isuite.core.domain.IncidentResource)
	 */
	public void setIncidentResource(IncidentResource incidentResource) {
		this.incidentResource = incidentResource;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getIncidentResourceId()
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setIncidentResourceId(java.lang.Long)
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCIArrivalJetPort()
	 */
	public JetPort getCIArrivalJetPort() {
		return ciArrivalJetPort;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCIArrivalJetPort(gov.nwcg.isuite.core.domain.JetPort)
	 */
	public void setCIArrivalJetPort(JetPort ciArrivalJetPort) {
		this.ciArrivalJetPort = ciArrivalJetPort;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCIArrivalJetPortId()
	 */
	public Long getCIArrivalJetPortId() {
		return ciArrivalJetPortId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCIArrivalJetPortId(java.lang.Long)
	 */
	public void setCIArrivalJetPortId(Long ciArrivalJetPortId) {
		this.ciArrivalJetPortId = ciArrivalJetPortId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCIResourceMobilization()
	 */
	public ResourceMobilization getCIResourceMobilization(){
		return this.ciResourceMobilization;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCIResourceMobilization(gov.nwcg.isuite.core.domain.ResourceMobilization)
	 */
	public void setCIResourceMobilization(ResourceMobilization rm){
		this.ciResourceMobilization=rm;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCIMobilizationId()
	 */
	public Long getCIMobilizationId(){
		return ciMobilizationId;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCIMobilizationId(java.lang.Long)
	 */
	public void setCIMobilizationId(Long id){
		this.ciMobilizationId=id;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCIRentalLocation()
	 */
	public String getCIRentalLocation() {
		return ciRentalLocation;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCIRentalLocation(java.lang.String)
	 */
	public void setCIRentalLocation(String ciRentalLocation) {
		this.ciRentalLocation = ciRentalLocation;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCICheckInDate()
	 */
	public Date getCICheckInDate() {
		return ciCheckInDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCICheckInDate(java.util.Date)
	 */
	public void setCICheckInDate(Date ciCheckInDate) {
		this.ciCheckInDate = ciCheckInDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCIFirstWorkDate()
	 */
	public Date getCIFirstWorkDate() {
		return ciFirstWorkDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCIFirstWorkDate(java.util.Date)
	 */
	public void setCIFirstWorkDate(Date ciFirstWorkDate) {
		this.ciFirstWorkDate = ciFirstWorkDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCIPrePlanningRemarks()
	 */
	public String getCIPrePlanningRemarks() {
		return ciPrePlanningRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCIPrePlanningRemarks(java.lang.String)
	 */
	public void setCIPrePlanningRemarks(String ciPrePlanningRemarks) {
		this.ciPrePlanningRemarks = ciPrePlanningRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getCILengthAtAssignment()
	 */
	public Long getCILengthAtAssignment() {
		return ciLengthAtAssignment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setCILengthAtAssignment(java.lang.Long)
	 */
	public void setCILengthAtAssignment(Long ciLengthAtAssignment) {
		this.ciLengthAtAssignment = ciLengthAtAssignment;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMTentativeDemobCity()
	 */
	public String getDMTentativeDemobCity() {
		return dmTentativeDemobCity;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMTentativeDemobCity(java.lang.String)
	 */
	public void setDMTentativeDemobCity(String dmTentativeDemobCity) {
		this.dmTentativeDemobCity = dmTentativeDemobCity;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMTentativeDemobState()
	 */
	public CountrySubdivision getDMTentativeDemobState() {
		return dmTentativeDemobState;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMTentativeDemobState(gov.nwcg.isuite.core.domain.CountrySubdivision)
	 */
	public void setDMTentativeDemobState(
			CountrySubdivision dmTentativeDemobState) {
		this.dmTentativeDemobState = dmTentativeDemobState;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMTentativeDemobStateId()
	 */
	public Long getDMTentativeDemobStateId() {
		return dmTentativeDemobStateId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMTentativeDemobStateId(java.lang.Long)
	 */
	public void setDMTentativeDemobStateId(Long dmTentativeDemobStateId) {
		this.dmTentativeDemobStateId = dmTentativeDemobStateId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMReleaseDate()
	 */
	public Date getDMReleaseDate() {
		return dmReleaseDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMReleaseDate(java.util.Date)
	 */
	public void setDMReleaseDate(Date dmReleaseDate) {
		this.dmReleaseDate = dmReleaseDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMTentativeReleaseDate()
	 */
	public Date getDMTentativeReleaseDate() {
		return dmTentativeReleaseDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMTentativeReleaseDate(java.util.Date)
	 */
	public void setDMTentativeReleaseDate(Date dmTentativeReleaseDate) {
		this.dmTentativeReleaseDate = dmTentativeReleaseDate;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#isDMReAssignable()
	 */
	public Boolean isDMReAssignable() {
		return dmReAssignable;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMReAssignable(java.lang.Boolean)
	 */
	public void setDMReAssignable(Boolean dmReAssignable) {
		this.dmReAssignable = dmReAssignable;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#isDMRestOvernight()
	 */
	public Boolean isDMRestOvernight() {
		return dmRestOvernight;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMRestOvernight(java.lang.Boolean)
	 */
	public void setDMRestOvernight(Boolean dmRestOvernight) {
		this.dmRestOvernight = dmRestOvernight;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#isDMReleaseDispatchNotified()
	 */
	public Boolean isDMReleaseDispatchNotified() {
		return dmReleaseDispatchNotified;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMReleaseDispatchNotified(java.lang.Boolean)
	 */
	public void setDMReleaseDispatchNotified(Boolean dmReleaseDispatchNotified) {
		this.dmReleaseDispatchNotified = dmReleaseDispatchNotified;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#isDMPlanningDispatchNotified()
	 */
	public Boolean isDMPlanningDispatchNotified() {
		return dmPlanningDispatchNotified;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMPlanningDispatchNotified(java.lang.Boolean)
	 */
	public void setDMPlanningDispatchNotified(Boolean dmPlanningDispatchNotified) {
		this.dmPlanningDispatchNotified = dmPlanningDispatchNotified;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#isDMCheckoutFormPrinted()
	 */
	public Boolean isDMCheckoutFormPrinted() {
		return dmCheckoutFormPrinted;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMCheckoutFormPrinted(java.lang.Boolean)
	 */
	public void setDMCheckoutFormPrinted(Boolean dmCheckoutFormPrinted) {
		this.dmCheckoutFormPrinted = dmCheckoutFormPrinted;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMReleaseRemarks()
	 */
	public String getDMReleaseRemarks() {
		return dmReleaseRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMReleaseRemarks(java.lang.String)
	 */
	public void setDMReleaseRemarks(String dmReleaseRemarks) {
		this.dmReleaseRemarks = dmReleaseRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMPlanningRemarks()
	 */
	public String getDMPlanningRemarks() {
		return dmPlanningRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMPlanningRemarks(java.lang.String)
	 */
	public void setDMPlanningRemarks(String dmPlanningRemarks) {
		this.dmPlanningRemarks = dmPlanningRemarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMAirTravelId()
	 */
	public Long getDMAirTravelId() {
		return dmAirTravelId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMAirTravelId(java.lang.Long)
	 */
	public void setDMAirTravelId(Long dmAirTravelId) {
		this.dmAirTravelId = dmAirTravelId;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMAirTravel()
	 */
	public AirTravel getDMAirTravel() {
		return dmAirTravel;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMAirTravel(gov.nwcg.isuite.core.domain.AirTravel)
	 */
	public void setDMAirTravel(AirTravel dmAirTravel) {
		this.dmAirTravel = dmAirTravel;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getDMTravelMethod()
	 */
	public TravelMethodTypeEnum getDMTravelMethod() {
		return dmTravelMethod;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setDMTravelMethod(gov.nwcg.isuite.framework.types.TravelMethodTypeEnum)
	 */
	public void setDMTravelMethod(TravelMethodTypeEnum dmTravelMethod) {
		this.dmTravelMethod = dmTravelMethod;
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#getWorkPeriodQuestionValues()
	 */
	public Collection<WorkPeriodQuestionValue> getWorkPeriodQuestionValues() {
		return workPeriodQuestionValues;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.WorkPeriod#setWorkPeriodQuestionValues(java.util.Collection)
	 */
	public void setWorkPeriodQuestionValues(Collection<WorkPeriodQuestionValue> workPeriodQuestionValues) {
		this.workPeriodQuestionValues = workPeriodQuestionValues;
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
      WorkPeriodImpl o = (WorkPeriodImpl)obj;
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
	 * Returns the tentative arrival date
	 * 
	 * @return
	 * 		tentative arrival date
	 */
	public Date getCITentativeArrivalDate() {
		return ciTentativeArrivalDate;
	}
	
	/**
	 * Sets the tentative arrival date
	 * 
	 * @param date
	 * 		tentative arrival date to set
	 */
	public void setCITentativeArrivalDate(Date date) {
		ciTentativeArrivalDate = date;
	}

	/**
	 * Returns the wpOvernightStayInfos.
	 *
	 * @return 
	 *		the wpOvernightStayInfos to return
	 */
	public Collection<WorkPeriodOvernightStayInfo> getWpOvernightStayInfos() {
		return wpOvernightStayInfos;
	}

	/*
	 * keep private for cascade_delete_orphan, use addWpOvernightStayInfo
	 */
	private void setWpOvernightStayInfos(Collection<WorkPeriodOvernightStayInfo> wpOvernightStayInfos) {
		this.wpOvernightStayInfos = wpOvernightStayInfos;
	}

	public void addWpWpOvernightStayInfo(WorkPeriodOvernightStayInfo info){
		this.getWpOvernightStayInfos().add(info);
	}
	
	/**
	 * Returns the dmTentativeArrivalDate.
	 *
	 * @return 
	 *		the dmTentativeArrivalDate to return
	 */
	public Date getDmTentativeArrivalDate() {
		return dmTentativeArrivalDate;
	}

	/**
	 * Sets the dmTentativeArrivalDate.
	 *
	 * @param dmTentativeArrivalDate 
	 *			the dmTentativeArrivalDate to set
	 */
	public void setDmTentativeArrivalDate(Date dmTentativeArrivalDate) {
		this.dmTentativeArrivalDate = dmTentativeArrivalDate;
	}

	/**
	 * Returns the ciTravelMethod.
	 *
	 * @return 
	 *		the ciTravelMethod to return
	 */
	public TravelMethodTypeEnum getCiTravelMethod() {
		return ciTravelMethod;
	}

	/**
	 * Sets the ciTravelMethod.
	 *
	 * @param ciTravelMethod 
	 *			the ciTravelMethod to set
	 */
	public void setCiTravelMethod(TravelMethodTypeEnum ciTravelMethod) {
		this.ciTravelMethod = ciTravelMethod;
	}

	public IncidentAccountCode getDefIncidentAccountCode() {
		return defIncidentAccountCode;
	}

	public void setDefIncidentAccountCode(IncidentAccountCode defIncidentAccountCode) {
		this.defIncidentAccountCode = defIncidentAccountCode;
	}

	public Long getDefIncidentAccountCodeId() {
		return defIncidentAccountCodeId;
	}

	public void setDefIncidentAccountCodeId(Long defIncidentAccountCodeId) {
		this.defIncidentAccountCodeId = defIncidentAccountCodeId;
	}

	public Boolean getGroundSupport() {
		return groundSupport;
	}

	public void setGroundSupport(Boolean groundSupport) {
		this.groundSupport = groundSupport;
	}
}