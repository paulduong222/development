package gov.nwcg.isuite.core.domain.impl;

import java.util.Collection;
import java.util.Date;

import gov.nwcg.isuite.core.domain.IapAircraft;
import gov.nwcg.isuite.core.domain.IapAircraftFrequency;
import gov.nwcg.isuite.core.domain.IapAircraftTask;
import gov.nwcg.isuite.core.domain.IapForm220;
import gov.nwcg.isuite.core.domain.IapPersonnel;
import gov.nwcg.isuite.core.domain.IapPlan;
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
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_IAP_FORM_220", sequenceName="SEQ_IAP_FORM_220")
@Table(name = "isw_iap_form_220")
public class IapForm220Impl extends PersistableImpl implements IapForm220 {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_FORM_220")
	private Long id = 0L;

	@ManyToOne(targetEntity=IapPlanImpl.class, fetch = FetchType.LAZY)
	@JoinColumn(name = "IAP_PLAN_ID", nullable = false)
	private IapPlan iapPlan;

	@Column(name = "IAP_PLAN_ID", insertable = false, updatable = false, unique=false, nullable = false)
	private Long iapPlanId;

	@Column(name = "PREPARED_BY", length = 50)
	private String preparedBy;

	@Column(name = "PREPARED_BY_POS", length = 50)
	private String preparedByPosition;

	@Column(name = "PREPARED_DATE")
	private Date preparedDate;
	
	@Column(name = "IS_FORM_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isFormLocked;

	@Column(name = "SUNSET",length=10)
	private String sunset;

	@Column(name = "SUNRISE",length=10)
	private String sunrise;
	
	@Column(name = "MEDIVAC", length = 200)
	private String medivac;
	
	@Column(name = "NEW_INCIDENT", length = 200)
	private String newIncident;

	@Column(name = "READY_ALERT_AIRCRAFT", length = 200)
	private String readyAlertAircraft;

	@Column(name = "TFR_NBR", length = 200)
	private String tfrNbr;
	
	@Column(name = "ALTITUDE", length = 200)
	private String altitude;
	
	@Column(name = "CENTRAL_POINT", length = 200)
	private String centralPoint;

	@Column(name = "REMARKS", length = 4000)
	private String remarks;

	@OneToMany(targetEntity=IapAircraftImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm220")
    @OrderBy("positionNum")
	private Collection<IapAircraft> iapAircrafts;
	
	@OneToMany(targetEntity=IapAircraftFrequencyImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm220")
	@OrderBy("id")
	private Collection<IapAircraftFrequency> iapAircraftFrequencies;

	@OneToMany(targetEntity=IapAircraftTaskImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm220")
    @OrderBy("positionNum")
	private Collection<IapAircraftTask> iapAircraftTasks;
	
	@OneToMany(targetEntity=IapPersonnelImpl.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "iapForm220")
	@OrderBy("id")
	private Collection<IapPersonnel> iapPersonnels;
	
	public IapForm220Impl() {
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

	/**
	 * @return the iapPlan
	 */
	public IapPlan getIapPlan() {
		return iapPlan;
	}

	/**
	 * @param iapPlan the iapPlan to set
	 */
	public void setIapPlan(IapPlan iapPlan) {
		this.iapPlan = iapPlan;
	}

	/**
	 * @return the iapPlanId
	 */
	public Long getIapPlanId() {
		return iapPlanId;
	}

	/**
	 * @param iapPlanId the iapPlanId to set
	 */
	public void setIapPlanId(Long iapPlanId) {
		this.iapPlanId = iapPlanId;
	}

	/**
	 * @return the preparedBy
	 */
	public String getPreparedBy() {
		return preparedBy;
	}

	/**
	 * @param preparedBy the preparedBy to set
	 */
	public void setPreparedBy(String preparedBy) {
		this.preparedBy = preparedBy;
	}

	/**
	 * @return the preparedByPosition
	 */
	public String getPreparedByPosition() {
		return preparedByPosition;
	}

	/**
	 * @param preparedByPosition the preparedByPosition to set
	 */
	public void setPreparedByPosition(String preparedByPosition) {
		this.preparedByPosition = preparedByPosition;
	}

	/**
	 * @return the preparedDate
	 */
	public Date getPreparedDate() {
		return preparedDate;
	}

	/**
	 * @param preparedDate the preparedDate to set
	 */
	public void setPreparedDate(Date preparedDate) {
		this.preparedDate = preparedDate;
	}

	/**
	 * @return the isFormLocked
	 */
	public StringBooleanEnum getIsFormLocked() {
		return isFormLocked;
	}

	/**
	 * @param isFormLocked the isFormLocked to set
	 */
	public void setIsFormLocked(StringBooleanEnum isFormLocked) {
		this.isFormLocked = isFormLocked;
	}

	/**
	 * @return the sunset
	 */
	public String getSunset() {
		return sunset;
	}

	/**
	 * @param sunset the sunset to set
	 */
	public void setSunset(String sunset) {
		this.sunset = sunset;
	}

	/**
	 * @return the sunrise
	 */
	public String getSunrise() {
		return sunrise;
	}

	/**
	 * @param sunrise the sunrise to set
	 */
	public void setSunrise(String sunrise) {
		this.sunrise = sunrise;
	}

	/**
	 * @return the medivac
	 */
	public String getMedivac() {
		return medivac;
	}

	/**
	 * @param medivac the medivac to set
	 */
	public void setMedivac(String medivac) {
		this.medivac = medivac;
	}

	/**
	 * @return the readyAlertAircraft
	 */
	public String getReadyAlertAircraft() {
		return readyAlertAircraft;
	}

	/**
	 * @param readyAlertAircraft the readyAlertAircraft to set
	 */
	public void setReadyAlertAircraft(String readyAlertAircraft) {
		this.readyAlertAircraft = readyAlertAircraft;
	}

	/**
	 * @return the tfrNbr
	 */
	public String getTfrNbr() {
		return tfrNbr;
	}

	/**
	 * @param tfrNbr the tfrNbr to set
	 */
	public void setTfrNbr(String tfrNbr) {
		this.tfrNbr = tfrNbr;
	}

	/**
	 * @return the altitude
	 */
	public String getAltitude() {
		return altitude;
	}

	/**
	 * @param altitude the altitude to set
	 */
	public void setAltitude(String altitude) {
		this.altitude = altitude;
	}

	/**
	 * @return the centralPoint
	 */
	public String getCentralPoint() {
		return centralPoint;
	}

	/**
	 * @param centralPoint the centralPoint to set
	 */
	public void setCentralPoint(String centralPoint) {
		this.centralPoint = centralPoint;
	}

	/**
	 * @return the remarks
	 */
	public String getRemarks() {
		return remarks;
	}

	/**
	 * @param remarks the remarks to set
	 */
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the iapAircrafts
	 */
	public Collection<IapAircraft> getIapAircrafts() {
		return iapAircrafts;
	}

	/**
	 * @param iapAircrafts the iapAircrafts to set
	 */
	public void setIapAircrafts(Collection<IapAircraft> iapAircrafts) {
		this.iapAircrafts = iapAircrafts;
	}

	/**
	 * @return the iapAircraftFrequencies
	 */
	public Collection<IapAircraftFrequency> getIapAircraftFrequencies() {
		return iapAircraftFrequencies;
	}

	/**
	 * @param iapAircraftFrequencies the iapAircraftFrequencies to set
	 */
	public void setIapAircraftFrequencies(
			Collection<IapAircraftFrequency> iapAircraftFrequencies) {
		this.iapAircraftFrequencies = iapAircraftFrequencies;
	}

	/**
	 * @return the iapAircraftTasks
	 */
	public Collection<IapAircraftTask> getIapAircraftTasks() {
		return iapAircraftTasks;
	}

	/**
	 * @param iapAircraftTasks the iapAircraftTasks to set
	 */
	public void setIapAircraftTasks(Collection<IapAircraftTask> iapAircraftTasks) {
		this.iapAircraftTasks = iapAircraftTasks;
	}

	/**
	 * @return the newIncident
	 */
	public String getNewIncident() {
		return newIncident;
	}

	/**
	 * @param newIncident the newIncident to set
	 */
	public void setNewIncident(String newIncident) {
		this.newIncident = newIncident;
	}

	/**
	 * @return the iapPersonnels
	 */
	public Collection<IapPersonnel> getIapPersonnels() {
		return iapPersonnels;
	}

	/**
	 * @param iapPersonnels the iapPersonnels to set
	 */
	public void setIapPersonnels(Collection<IapPersonnel> iapPersonnels) {
		this.iapPersonnels = iapPersonnels;
	} 

}
