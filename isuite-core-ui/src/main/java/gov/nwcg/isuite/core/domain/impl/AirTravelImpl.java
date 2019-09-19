package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.WorkPeriod;
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

/**
 * 
 * 
 * @author bsteiner
 */
@Entity
@SequenceGenerator(name="SEQ_AIR_TRAVEL", sequenceName="SEQ_AIR_TRAVEL")
@Table(name="isw_air_travel")
public class AirTravelImpl extends PersistableImpl implements AirTravel {

   @Id
   @GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_AIR_TRAVEL")
   private Long id = 0L;
   
   @Column(name="JET_PORT_ID",length=19, insertable = false, updatable = false)
   private Long jetPortId;
   
   @ManyToOne(targetEntity=JetPortImpl.class, fetch=FetchType.LAZY)
   @JoinColumn(name="JET_PORT_ID", insertable=true, updatable=true, unique=false, nullable=true)
   private JetPort jetPort;
   
   @Column(name="AIRLINE",length=50)
   private String airline;
   
   @Column(name="IS_DISPATCH_NOTIFIED",nullable=false)
   private Boolean dispatchNotified;
   
   @Column(name="HOURS_TO_AIRPORT")
   private Integer hoursToAirport;
   
   @Column(name="MINUTES_TO_AIRPORT")
   private Integer minutesToAirport;
   
   @Column(name="LEAVE_TIME")
   private Integer leaveTime;
   
   @Column(name="FLIGHT_NUMBER", length=20)
   private String flightNumber;
   
   @Column(name="FLIGHT_TIME")
   private Integer flightTime;
   
   @Column(name="REMARKS", length=1000)
   private String remarks;
   
   @Column(name="IS_ITINERARY_RECEIVED",nullable=false)
   private Boolean itineraryReceived;
   
   @OneToOne(targetEntity=WorkPeriodImpl.class, fetch = FetchType.LAZY, mappedBy = "dmAirTravel")
   private WorkPeriod workPeriod;
	
	public AirTravelImpl() {
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
    * @see gov.nwcg.isuite.core.domain.AirTravel#getJetPortId()
    */
   public Long getJetPortId() {
	   return jetPortId;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.domain.AirTravel#setJetPortId(java.lang.Long)
    */
   public void setJetPortId(Long jetPortId) {
	   this.jetPortId = jetPortId;
   }

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getJetPort()
	 */
	public JetPort getJetPort() {
		return jetPort;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setJetPort(gov.nwcg.isuite.core.domain.JetPort)
	 */
	public void setJetPort(JetPort jetPort) {
		this.jetPort = jetPort;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getAirline()
	 */
	public String getAirline() {
		return airline;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setAirline(java.lang.String)
	 */
	public void setAirline(String airline) {
		this.airline = airline;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#isDispatchNotified()
	 */
	public Boolean isDispatchNotified() {
		return dispatchNotified;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setDispatchNotified(java.lang.Boolean)
	 */
	public void setDispatchNotified(Boolean dispatchNotified) {
		this.dispatchNotified = dispatchNotified;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getHoursToAirport()
	 */
	public Integer getHoursToAirport() {
		return hoursToAirport;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setHoursToAirport(java.lang.Integer)
	 */
	public void setHoursToAirport(Integer hoursToAirport) {
		this.hoursToAirport = hoursToAirport;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getMinutesToAirport()
	 */
	public Integer getMinutesToAirport() {
		return minutesToAirport;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setMinutesToAirport(java.lang.Integer)
	 */
	public void setMinutesToAirport(Integer minutesToAirport) {
		this.minutesToAirport = minutesToAirport;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getLeaveTime()
	 */
	public Integer getLeaveTime() {
		return leaveTime;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setLeaveTime(java.lang.Integer)
	 */
	public void setLeaveTime(Integer leaveTime) {
		this.leaveTime = leaveTime;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getFlightNumber()
	 */
	public String getFlightNumber() {
		return flightNumber;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setFlightNumber(java.lang.String)
	 */
	public void setFlightNumber(String flightNumber) {
		this.flightNumber = flightNumber;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getFlightTime()
	 */
	public Integer getFlightTime() {
		return flightTime;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setFlightTime(java.lang.Integer)
	 */
	public void setFlightTime(Integer flightTime) {
		this.flightTime = flightTime;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#getRemarks()
	 */
	public String getRemarks() {
		return remarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setRemarks(java.lang.String)
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#isItineraryReceived()
	 */
	public Boolean isItineraryReceived() {
		return itineraryReceived;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.core.domain.AirTravel#setItineraryReceived(java.lang.Boolean)
	 */
	public void setItineraryReceived(Boolean itineraryReceived) {
		this.itineraryReceived = itineraryReceived;
	}

	/**
	 * Returns the workPeriod.
	 *
	 * @return 
	 *		the workPeriod to return
	 */
	public WorkPeriod getWorkPeriod() {
		return workPeriod;
	}

	/**
	 * Sets the workPeriod.
	 *
	 * @param workPeriod 
	 *			the workPeriod to set
	 */
	public void setWorkPeriod(WorkPeriod workPeriod) {
		this.workPeriod = workPeriod;
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
      AirTravelImpl o = (AirTravelImpl)obj;
      return new EqualsBuilder()
      	.append(new Object[]{id,jetPortId,dispatchNotified,hoursToAirport,minutesToAirport
      						,leaveTime,airline,flightNumber,flightTime
      						,remarks,itineraryReceived},
				new Object[]{o.id,o.jetPortId,o.dispatchNotified,o.hoursToAirport,o.minutesToAirport
							,o.leaveTime,o.airline,o.flightNumber,o.flightTime
							,o.remarks,o.itineraryReceived})
      	.appendSuper(super.equals(o))
      	.isEquals();
   }   
   
   /* (non-Javadoc)
    * @see java.lang.Object#hashCode()
    */
   public int hashCode() {
	  return new HashCodeBuilder(31,33)
	  	.append(super.hashCode())
	  	.append(new Object[]{id,jetPortId,dispatchNotified,hoursToAirport,minutesToAirport
			,leaveTime,airline,flightNumber,flightTime
			,remarks,itineraryReceived})
	  .toHashCode();
   }

   /* (non-Javadoc)
    * @see java.lang.Object#toString()
    */
   public String toString() {
	   return new ToStringBuilder(this)
	       .append("id", id)
	       .append("jetPortId", jetPortId)
	       .append("dispatchNotified", dispatchNotified)
	       .append("hoursToAirport", hoursToAirport)
	       .append("minutesToAirport",minutesToAirport)
	       .append("leaveTime",leaveTime)
	       .append("airline",airline)
	       .append("flightNumber",flightNumber)
	       .append("flightTime",flightTime)
	       .append("remarks",remarks)
	       .append("itineraryReceived",itineraryReceived)
	       .appendSuper(super.toString())
	       .toString();
   }



}
