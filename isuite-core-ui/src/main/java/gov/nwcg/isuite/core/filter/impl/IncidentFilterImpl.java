package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.IncidentFilter;
import gov.nwcg.isuite.core.vo.IncidentGridVo;
import gov.nwcg.isuite.core.vo.IncidentRestrictedStatusVo;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IncidentFilterImpl extends FilterImpl implements IncidentFilter {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String agency;
	private String countrySubdivision;
	private String eventType;
	private String homeUnit;
	private String incidentName;
	private String incidentNumber;
	private String startDateString;
	private Boolean deletable;
	private String deletableString;
	private String riuType;
	private String defaultAccountingCode;
	private Boolean restricted;
	private Collection<IncidentRestrictedStatusVo> incidentRestrictedStatuses = new ArrayList<IncidentRestrictedStatusVo>();
	private Long rossIncId;
	private String rossIncidentId;
	private Boolean rossIncidentsOnly;
	private String incidentTagNumber;
	private Integer incidentYear;
	
	public IncidentFilterImpl() {
	}

	public static Collection<IncidentGridVo> filterRestrStatus(Collection<IncidentGridVo> vos, IncidentFilter filter){
		Collection<IncidentGridVo> rtnVos = new ArrayList<IncidentGridVo>();
		
		if(StringUtility.hasValue(filter.getRiuType())){
			for(IncidentGridVo vo : vos){
				if(vo.getIncidentRestrictedStatusType().getDescription().equals(filter.getRiuType())){
					rtnVos.add(vo);
				}
			}
		}else
			return vos;
		
		return rtnVos;
	}
	
	public Long getId(){
		return id;
	}

	public void setId(Long id){
		this.id=id;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#getAgency()
	 */
	public String getAgency() {
		return agency;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#setAgency(java.lang.String)
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#getCountrySubdivision()
	 */
	public String getCountrySubdivision() {
		return countrySubdivision;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#setCountrySubdivision(java.lang.String)
	 */
	public void setCountrySubdivision(String countrySubdivision) {
		this.countrySubdivision = countrySubdivision;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#getEventType()
	 */
	public String getEventType() {
		return eventType;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#setEventType(java.lang.String)
	 */
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#getHomeUnit()
	 */
	public String getHomeUnit() {
		return homeUnit;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#setHomeUnit(java.lang.String)
	 */
	public void setHomeUnit(String homeUnit) {
		this.homeUnit = homeUnit;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#getIncidentName()
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#setIncidentName(java.lang.String)
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#getIncidentNumber()
	 */
	public String getIncidentNumber() {
		return incidentNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#setIncidentNumber(java.lang.String)
	 */
	public void setIncidentNumber(String incidentNumber) {
		this.incidentNumber = incidentNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.incident.IncidentFilter#getIncidentStartDate()
	 */
	public Date getIncidentStartDate() {
//		if(this.startDateString != null) {
//			return super.crypticDateConverter(startDateString);
//		}
		
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentFilter#setDeletable(java.lang.Boolean)
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentFilter#getDeletable()
	 */
	public Boolean getDeletable() {
		return deletable;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.access.impl.FilterImpl#reset()
	 */
	public final void reset() {
		this.incidentName = null;
		this.incidentNumber = null;
		this.startDateString = null;
		this.agency = null;
		this.eventType = null;
		this.countrySubdivision = null;
		this.homeUnit = null;
		this.riuType = null;
	}

	/**
	 * Returns the riuType.
	 *
	 * @return 
	 *		the riuType to return
	 */
	public String getRiuType() {
		return riuType;
	}

	/**
	 * Sets the riuType.
	 *
	 * @param riuType 
	 *			the riuType to set
	 */
	public void setRiuType(String riuType) {
		this.riuType = riuType;
	}

	/**
	 * Returns the defaultAccountingCode.
	 *
	 * @return 
	 *		the defaultAccountingCode to return
	 */
	public String getDefaultAccountingCode() {
		return defaultAccountingCode;
	}

	/**
	 * Sets the defaultAccountingCode.
	 *
	 * @param defaultAccountingCode 
	 *			the defaultAccountingCode to set
	 */
	public void setDefaultAccountingCode(String defaultAccountingCode) {
		this.defaultAccountingCode = defaultAccountingCode;
	}

	/**
	 * Returns the restricted.
	 *
	 * @return 
	 *		the restricted to return
	 */
	public Boolean getRestricted() {
		return restricted;
	}

	/**
	 * Sets the restricted.
	 *
	 * @param restricted 
	 *			the restricted to set
	 */
	public void setRestricted(Boolean restricted) {
		this.restricted = restricted;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentFilter#getIncidentRestrictedStatuses()
	 */
	public Collection<IncidentRestrictedStatusVo> getIncidentRestrictedStatuses() {
		return incidentRestrictedStatuses;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.IncidentFilter#setIncidentRestrictedStatuses(java.util.Collection)
	 */
	public void setIncidentRestrictedStatuses(
			Collection<IncidentRestrictedStatusVo> incidentRestrictedStatuses) {
		this.incidentRestrictedStatuses = incidentRestrictedStatuses;
	}

	/**
	 * @return the deletableString
	 */
	public String getDeletableString() {
		return deletableString;
	}

	/**
	 * @param deletableString the deletableString to set
	 */
	public void setDeletableString(String deletableString) {
		this.deletableString = deletableString;
		this.setDeletable(super.determineDeletableValue(this.deletableString));
	}

	/**
	 * @return the rossIncId
	 */
	public Long getRossIncId() {
		return rossIncId;
	}

	/**
	 * @param rossIncId the rossIncId to set
	 */
	public void setRossIncId(Long rossIncId) {
		this.rossIncId = rossIncId;
	}

	/**
	 * @return the rossIncidentsOnly
	 */
	public Boolean getRossIncidentsOnly() {
		return rossIncidentsOnly;
	}

	/**
	 * @param rossIncidentsOnly the rossIncidentsOnly to set
	 */
	public void setRossIncidentsOnly(Boolean rossIncidentsOnly) {
		this.rossIncidentsOnly = rossIncidentsOnly;
	}

	/**
	 * @return the rossIncidentId
	 */
	public String getRossIncidentId() {
		return rossIncidentId;
	}

	/**
	 * @param rossIncidentId the rossIncidentId to set
	 */
	public void setRossIncidentId(String rossIncidentId) {
		this.rossIncidentId = rossIncidentId;
	}

	@Override
	public String getStartDateString() {
		return this.startDateString;
	}

	@Override
	public void setStartDateString(String startDateString) {
		this.startDateString = startDateString;
	}

	public String getIncidentTagNumber() {
		return incidentTagNumber;
	}

	public void setIncidentTagNumber(String incidentTagNumber) {
		this.incidentTagNumber = incidentTagNumber;
	}

	/**
	 * @return the incidentYear
	 */
	public Integer getIncidentYear() {
		return incidentYear;
	}

	/**
	 * @param incidentYear the incidentYear to set
	 */
	public void setIncidentYear(Integer incidentYear) {
		this.incidentYear = incidentYear;
	}

}
