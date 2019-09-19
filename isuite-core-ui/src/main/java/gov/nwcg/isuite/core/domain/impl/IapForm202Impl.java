package gov.nwcg.isuite.core.domain.impl;

import gov.nwcg.isuite.core.domain.IapForm202;
import gov.nwcg.isuite.core.domain.IapPlan;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.types.StringBooleanEnum;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@SequenceGenerator(name="SEQ_IAP_FORM_202", sequenceName="SEQ_IAP_FORM_202")
@Table(name = "isw_iap_form_202")
public class IapForm202Impl extends PersistableImpl implements IapForm202 {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO, generator="SEQ_IAP_FORM_202")
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

	@Column(name = "APPROVED_BY", length = 50)
	private String approvedBy;
	
	@Column(name = "APPROVED_DATE")
	private Date approvedDate;

	@Lob
	@Column(name = "OBJECTIVES")
	private String objectives;
	
	@Lob
	@Column(name = "COMMAND_EMPHASIS")
	private String commandEmphasis;

	@Lob
	@Column(name = "GEN_SIT_AWARENESS")
	private String generalSituationalAwareness;

	@Column(name = "SITE_SAFETY_PLAN_RQRD")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum siteSafetyPlanRqrd;
	
	@Column(name = "SITE_SAFETY_PLAN_LOC", length = 50)
	private String siteSafetyPlanLoc;

	@Column(name = "IS_FORM_202_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm202Attached;
	
	@Column(name = "IS_FORM_203_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm203Attached;
	
	@Column(name = "IS_FORM_204_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm204Attached;
	
	@Column(name = "IS_FORM_205_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm205Attached;
	
	@Column(name = "IS_FORM_205A_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm205aAttached;
	
	@Column(name = "IS_FORM_206_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm206Attached;
	
	@Column(name = "IS_FORM_207_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm207Attached;
	
	@Column(name = "IS_FORM_208_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm208Attached;
	
	@Column(name = "IS_FORM_220_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isForm220Attached;

	@Column(name = "IS_INCIDENT_MAP_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isIncidentMapAttached;
	
	@Column(name = "IS_WEATHER_FORECAST_ATTACHED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isWeatherForecastAttached;

	@Column(name = "IS_OTHER_FORM_ATTACHED_1")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isOtherFormAttached1;
	
	@Column(name = "IS_OTHER_FORM_ATTACHED_2")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isOtherFormAttached2;

	@Column(name = "IS_OTHER_FORM_ATTACHED_3")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isOtherFormAttached3;

	@Column(name = "IS_OTHER_FORM_ATTACHED_4")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isOtherFormAttached4;

	@Column(name = "OTHER_FORM_NAME_1", length = 50)
	private String otherFormName1;
	
	@Column(name = "OTHER_FORM_NAME_2", length = 50)
	private String otherFormName2;

	@Column(name = "OTHER_FORM_NAME_3", length = 50)
	private String otherFormName3;
	
	@Column(name = "OTHER_FORM_NAME_4", length = 50)
	private String otherFormName4;
	
	@Column(name = "IS_FORM_LOCKED")
	@Enumerated(EnumType.STRING)
	private StringBooleanEnum isFormLocked;

	public IapForm202Impl() {
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
	 * @return the approvedBy
	 */
	public String getApprovedBy() {
		return approvedBy;
	}

	/**
	 * @param approvedBy the approvedBy to set
	 */
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}

	/**
	 * @return the approvedDate
	 */
	public Date getApprovedDate() {
		return approvedDate;
	}

	/**
	 * @param approvedDate the approvedDate to set
	 */
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}

	/**
	 * @return the objectives
	 */
	public String getObjectives() {
		return objectives;
	}

	/**
	 * @param objectives the objectives to set
	 */
	public void setObjectives(String objectives) {
		this.objectives = objectives;
	}

	/**
	 * @return the commandEmphasis
	 */
	public String getCommandEmphasis() {
		return commandEmphasis;
	}

	/**
	 * @param commandEmphasis the commandEmphasis to set
	 */
	public void setCommandEmphasis(String commandEmphasis) {
		this.commandEmphasis = commandEmphasis;
	}

	/**
	 * @return the generalSituationalAwareness
	 */
	public String getGeneralSituationalAwareness() {
		return generalSituationalAwareness;
	}

	/**
	 * @param generalSituationalAwareness the generalSituationalAwareness to set
	 */
	public void setGeneralSituationalAwareness(String generalSituationalAwareness) {
		this.generalSituationalAwareness = generalSituationalAwareness;
	}

	/**
	 * @return the siteSafetyPlanRqrd
	 */
	public StringBooleanEnum getSiteSafetyPlanRqrd() {
		return siteSafetyPlanRqrd;
	}

	/**
	 * @param siteSafetyPlanRqrd the siteSafetyPlanRqrd to set
	 */
	public void setSiteSafetyPlanRqrd(StringBooleanEnum siteSafetyPlanRqrd) {
		this.siteSafetyPlanRqrd = siteSafetyPlanRqrd;
	}

	/**
	 * @return the siteSafetyPlanLoc
	 */
	public String getSiteSafetyPlanLoc() {
		return siteSafetyPlanLoc;
	}

	/**
	 * @param siteSafetyPlanLoc the siteSafetyPlanLoc to set
	 */
	public void setSiteSafetyPlanLoc(String siteSafetyPlanLoc) {
		this.siteSafetyPlanLoc = siteSafetyPlanLoc;
	}

	/**
	 * @return the isForm202Attached
	 */
	public StringBooleanEnum getIsForm202Attached() {
		return isForm202Attached;
	}

	/**
	 * @param isForm202Attached the isForm202Attached to set
	 */
	public void setIsForm202Attached(StringBooleanEnum isForm202Attached) {
		this.isForm202Attached = isForm202Attached;
	}

	/**
	 * @return the isForm203Attached
	 */
	public StringBooleanEnum getIsForm203Attached() {
		return isForm203Attached;
	}

	/**
	 * @param isForm203Attached the isForm203Attached to set
	 */
	public void setIsForm203Attached(StringBooleanEnum isForm203Attached) {
		this.isForm203Attached = isForm203Attached;
	}

	/**
	 * @return the isForm204Attached
	 */
	public StringBooleanEnum getIsForm204Attached() {
		return isForm204Attached;
	}

	/**
	 * @param isForm204Attached the isForm204Attached to set
	 */
	public void setIsForm204Attached(StringBooleanEnum isForm204Attached) {
		this.isForm204Attached = isForm204Attached;
	}

	/**
	 * @return the isForm205Attached
	 */
	public StringBooleanEnum getIsForm205Attached() {
		return isForm205Attached;
	}

	/**
	 * @param isForm205Attached the isForm205Attached to set
	 */
	public void setIsForm205Attached(StringBooleanEnum isForm205Attached) {
		this.isForm205Attached = isForm205Attached;
	}

	/**
	 * @return the isForm205aAttached
	 */
	public StringBooleanEnum getIsForm205aAttached() {
		return isForm205aAttached;
	}

	/**
	 * @param isForm205aAttached the isForm205aAttached to set
	 */
	public void setIsForm205aAttached(StringBooleanEnum isForm205aAttached) {
		this.isForm205aAttached = isForm205aAttached;
	}

	/**
	 * @return the isForm206Attached
	 */
	public StringBooleanEnum getIsForm206Attached() {
		return isForm206Attached;
	}

	/**
	 * @param isForm206Attached the isForm206Attached to set
	 */
	public void setIsForm206Attached(StringBooleanEnum isForm206Attached) {
		this.isForm206Attached = isForm206Attached;
	}

	/**
	 * @return the isForm207Attached
	 */
	public StringBooleanEnum getIsForm207Attached() {
		return isForm207Attached;
	}

	/**
	 * @param isForm207Attached the isForm207Attached to set
	 */
	public void setIsForm207Attached(StringBooleanEnum isForm207Attached) {
		this.isForm207Attached = isForm207Attached;
	}

	/**
	 * @return the isForm208Attached
	 */
	public StringBooleanEnum getIsForm208Attached() {
		return isForm208Attached;
	}

	/**
	 * @param isForm208Attached the isForm208Attached to set
	 */
	public void setIsForm208Attached(StringBooleanEnum isForm208Attached) {
		this.isForm208Attached = isForm208Attached;
	}

	/**
	 * @return the isIncidentMapAttached
	 */
	public StringBooleanEnum getIsIncidentMapAttached() {
		return isIncidentMapAttached;
	}

	/**
	 * @param isIncidentMapAttached the isIncidentMapAttached to set
	 */
	public void setIsIncidentMapAttached(StringBooleanEnum isIncidentMapAttached) {
		this.isIncidentMapAttached = isIncidentMapAttached;
	}

	/**
	 * @return the isWeatherForecastAttached
	 */
	public StringBooleanEnum getIsWeatherForecastAttached() {
		return isWeatherForecastAttached;
	}

	/**
	 * @param isWeatherForecastAttached the isWeatherForecastAttached to set
	 */
	public void setIsWeatherForecastAttached(
			StringBooleanEnum isWeatherForecastAttached) {
		this.isWeatherForecastAttached = isWeatherForecastAttached;
	}

	/**
	 * @return the isOtherFormAttached1
	 */
	public StringBooleanEnum getIsOtherFormAttached1() {
		return isOtherFormAttached1;
	}

	/**
	 * @param isOtherFormAttached1 the isOtherFormAttached1 to set
	 */
	public void setIsOtherFormAttached1(StringBooleanEnum isOtherFormAttached1) {
		this.isOtherFormAttached1 = isOtherFormAttached1;
	}

	/**
	 * @return the isOtherFormAttached2
	 */
	public StringBooleanEnum getIsOtherFormAttached2() {
		return isOtherFormAttached2;
	}

	/**
	 * @param isOtherFormAttached2 the isOtherFormAttached2 to set
	 */
	public void setIsOtherFormAttached2(StringBooleanEnum isOtherFormAttached2) {
		this.isOtherFormAttached2 = isOtherFormAttached2;
	}

	/**
	 * @return the isOtherFormAttached3
	 */
	public StringBooleanEnum getIsOtherFormAttached3() {
		return isOtherFormAttached3;
	}

	/**
	 * @param isOtherFormAttached3 the isOtherFormAttached3 to set
	 */
	public void setIsOtherFormAttached3(StringBooleanEnum isOtherFormAttached3) {
		this.isOtherFormAttached3 = isOtherFormAttached3;
	}

	/**
	 * @return the isOtherFormAttached4
	 */
	public StringBooleanEnum getIsOtherFormAttached4() {
		return isOtherFormAttached4;
	}

	/**
	 * @param isOtherFormAttached4 the isOtherFormAttached4 to set
	 */
	public void setIsOtherFormAttached4(StringBooleanEnum isOtherFormAttached4) {
		this.isOtherFormAttached4 = isOtherFormAttached4;
	}

	/**
	 * @return the otherFormName1
	 */
	public String getOtherFormName1() {
		return otherFormName1;
	}

	/**
	 * @param otherFormName1 the otherFormName1 to set
	 */
	public void setOtherFormName1(String otherFormName1) {
		this.otherFormName1 = otherFormName1;
	}

	/**
	 * @return the otherFormName2
	 */
	public String getOtherFormName2() {
		return otherFormName2;
	}

	/**
	 * @param otherFormName2 the otherFormName2 to set
	 */
	public void setOtherFormName2(String otherFormName2) {
		this.otherFormName2 = otherFormName2;
	}

	/**
	 * @return the otherFormName3
	 */
	public String getOtherFormName3() {
		return otherFormName3;
	}

	/**
	 * @param otherFormName3 the otherFormName3 to set
	 */
	public void setOtherFormName3(String otherFormName3) {
		this.otherFormName3 = otherFormName3;
	}

	/**
	 * @return the otherFormName4
	 */
	public String getOtherFormName4() {
		return otherFormName4;
	}

	/**
	 * @param otherFormName4 the otherFormName4 to set
	 */
	public void setOtherFormName4(String otherFormName4) {
		this.otherFormName4 = otherFormName4;
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
	 * @return the isForm220Attached
	 */
	public StringBooleanEnum getIsForm220Attached() {
		return isForm220Attached;
	}

	/**
	 * @param isForm220Attached the isForm220Attached to set
	 */
	public void setIsForm220Attached(StringBooleanEnum isForm220Attached) {
		this.isForm220Attached = isForm220Attached;
	} 

}
