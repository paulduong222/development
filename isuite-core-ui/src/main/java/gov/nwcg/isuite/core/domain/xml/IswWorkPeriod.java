package gov.nwcg.isuite.core.domain.xml;

import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferField;
import gov.nwcg.isuite.framework.core.xmltransfer.XmlTransferTable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

@XmlTransferTable(name = "IswWorkPeriod", table="isw_work_period")
public class IswWorkPeriod {
	
	@XmlTransferField(name = "Id", sqlname="ID", primarykey=true, sequence="SEQ_WORK_PERIOD", type="LONG")
	private Long id = 0L;
   
	@XmlTransferField(name = "TransferableIdentity", sqlname="TRANSFERABLE_IDENTITY", type="STRING")
	private String transferableIdentity;
	
	@XmlTransferField(name = "IncidentResourceId", sqlname="INCIDENT_RESOURCE_ID", type="LONG",updateable=false)
	private Long incidentResourceId;
	
	@XmlTransferField(name = "DefIncidentAccountCodeTransferableIdentity", alias="diacti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="DefIncidentAccountCodeId"
						, disjoined=true, disjoinedtable="isw_incident_account_code", disjoinedfield="transferable_identity",disjoinedsource="DEF_INCIDENT_ACCOUNT_CODE_ID")
	private String defIncidentAccountCodeTransferableIdentity;
	
	@XmlTransferField(name = "DefIncidentAccountCodeId", sqlname="DEF_INCIDENT_ACCOUNT_CODE_ID", type="LONG"
						,derived=true, derivedfield="DefIncidentAccountCodeTransferableIdentity")
	private Long defIncidentAccountCodeId;
	
	@XmlTransferField(name = "CiArrivalJetPortTransferableIdentity", alias="ciarrjpti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="CiArrivalJetPortId"
						, disjoined=true, disjoinedtable="iswl_jet_port", disjoinedfield="transferable_identity",disjoinedsource="CI_ARRIVAL_JET_PORT_ID")
	private String ciArrivalJetPortTransferableIdentity;

	@XmlTransferField(name = "CiArrivalJetPortId", sqlname="CI_ARRIVAL_JET_PORT_ID", type="LONG"
						,derived=true, derivedfield="CiArrivalJetPortTransferableIdentity")
	private Long ciArrivalJetPortId;
	
	@XmlTransferField(name = "CiResMobTransferableIdentity", alias="ciresmobti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="CiMobilizationId"
						, disjoined=true, disjoinedtable="isw_resource_mobilization", disjoinedfield="transferable_identity",disjoinedsource="CI_MOBILIZATION_ID")
	private String ciResMobTransferableIdentity;
	
	@XmlTransferField(name = "CiMobilizationId", sqlname="CI_MOBILIZATION_ID", type="LONG"
						,derived=true, derivedfield="CiResMobTransferableIdentity")
	private Long ciMobilizationId;

	@XmlTransferField(name = "CiTravelMethod", sqlname = "CI_TRAVEL_METHOD", type="STRING",nullwhenempty=true)
	private String ciTravelMethod;
	
	@XmlTransferField(name = "CiRentalLocation", sqlname="CI_RENTAL_LOCATION", type="STRING")
	private String ciRentalLocation;
   
	@XmlTransferField(name = "CiCheckInDate", sqlname="CI_CHECK_IN_DATE", type="DATE")
	private Date ciCheckInDate;
   
	@XmlTransferField(name = "CiFirstWorkDate", sqlname="CI_FIRST_WORK_DATE", type="DATE")
	private Date ciFirstWorkDate;

	@XmlTransferField(name = "CiPrePlanningRemarks", sqlname="CI_PRE_PLANNING_REMARKS", type="STRING")
	private String ciPrePlanningRemarks;
   
	@XmlTransferField(name = "CiLengthAtAssignment", sqlname="CI_LENGTH_AT_ASSIGNMENT", type="LONG")
	private Long ciLengthAtAssignment;
	
	@XmlTransferField(name = "DmTentativeDemobCity", sqlname="DM_TENTATIVE_DEMOB_CITY", type="STRING")
	private String dmTentativeDemobCity;
	
	@XmlTransferField(name = "DmTentDemobStateTransferableIdentity", alias="dmtentdmstateti", type="STRING"
						, lookupname="TransferableIdentity", sourcename="DmTentativeDemobStateId"
						, disjoined=true, disjoinedtable="iswl_country_subdivision", disjoinedfield="transferable_identity",disjoinedsource="DM_TENTATIVE_DEMOB_STATE_ID")
	private String dmTentDemobStateTransferableIdentity;
	
	@XmlTransferField(name = "DmTentativeDemobStateId", sqlname="DM_TENTATIVE_DEMOB_STATE_ID", type="LONG"
						,derived=true, derivedfield="DmTentDemobStateTransferableIdentity")
	private Long dmTentativeDemobStateId;

    @XmlTransferField(name = "DmTentativeArrivalDate", sqlname="DM_TENTATIVE_ARRIVAL_DATE", type="DATE")
	private Date dmTentativeArrivalDate;

    @XmlTransferField(name = "DmReleaseDate", sqlname="DM_RELEASE_DATE", type="DATE")
	private Date dmReleaseDate;

    @XmlTransferField(name = "DmTentativeReleaseDate", sqlname="DM_TENTATIVE_RELEASE_DATE", type="DATE")
	private Date dmTentativeReleaseDate;
	
    @XmlTransferField(name = "DmReAssignable", sqlname="DM_IS_REASSIGNABLE", type="BOOLEAN")
	private Boolean dmReAssignable=true;

    @XmlTransferField(name = "DmRestOvernight", sqlname="DM_IS_REST_OVERNIGHT", type="BOOLEAN")
	private Boolean dmRestOvernight=false;

    @XmlTransferField(name = "DmReleaseDispatchNotified", sqlname="DM_IS_RELEASE_DISPATCH_NOTIF", type="BOOLEAN")
	private Boolean dmReleaseDispatchNotified=false;

    @XmlTransferField(name = "DmPlanningDispatchNotified", sqlname="DM_IS_PLANNING_DISPATCH_NOTIF", type="BOOLEAN")
	private Boolean dmPlanningDispatchNotified=false;

    @XmlTransferField(name = "DmCheckoutFormPrinted", sqlname="DM_IS_CHECKOUT_FORM_PRINTED", type="BOOLEAN")
	private Boolean dmCheckoutFormPrinted=false;
	
    @XmlTransferField(name = "DmReleaseRemarks", sqlname="DM_RELEASE_REMARKS", type="STRING")
	private String dmReleaseRemarks;

    @XmlTransferField(name = "DmPlanningRemarks", sqlname="DM_PLANNING_REMARKS", type="STRING")
	private String dmPlanningRemarks;
	
	@XmlTransferField(name = "DmAirTravel", type="COMPLEX", target=IswAirTravel.class
							, lookupname="Id", sourcename="DmAirTravelId")
	private IswAirTravel dmAirTravel;

	@XmlTransferField(name = "DmAirTravelId", sqlname="DM_AIR_TRAVEL_ID", type="LONG"
						,derived=true, derivedfield="DmAirTravel")
	private Long dmAirTravelId;

	@XmlTransferField(name = "DmTravelMethod", sqlname = "DM_TRAVEL_METHOD", type="STRING",nullwhenempty=true)
	private String dmTravelMethod;
   
	@XmlTransferField(name="CiTentativeArrivalDate", sqlname="CI_TENTATIVE_ARRIVAL_DATE", type = "DATE")
	private Date ciTentativeArrivalDate;
	
	@XmlTransferField(name = "GroupSupport", sqlname="IS_GROUND_SUPPORT", type = "BOOLEAN")
	private Boolean groundSupport;
	
	@XmlTransferField(name = "WorkPeriodAssignment", type = "COMPLEX", target=IswWorkPeriodAssignment.class
						,lookupname="WorkPeriodId", sourcename="Id"
						, cascade=true)
	private Collection<IswWorkPeriodAssignment> workPeriodAssignments = new ArrayList<IswWorkPeriodAssignment>();
	
	@XmlTransferField(name = "WpOvernightStayInfo", type = "COMPLEX", target=IswWorkPeriodOvernightStayInfo.class
						,lookupname="WorkPeriodId", sourcename="Id"
						, cascade=true)
	private Collection<IswWorkPeriodOvernightStayInfo> wpOvernightStayInfos = new ArrayList<IswWorkPeriodOvernightStayInfo>();
	
	public IswWorkPeriod(){
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
	 * @return the transferableIdentity
	 */
	public String getTransferableIdentity() {
		return transferableIdentity;
	}

	/**
	 * @param transferableIdentity the transferableIdentity to set
	 */
	public void setTransferableIdentity(String transferableIdentity) {
		this.transferableIdentity = transferableIdentity;
	}

	/**
	 * @return the incidentResourceId
	 */
	public Long getIncidentResourceId() {
		return incidentResourceId;
	}

	/**
	 * @param incidentResourceId the incidentResourceId to set
	 */
	public void setIncidentResourceId(Long incidentResourceId) {
		this.incidentResourceId = incidentResourceId;
	}

	/**
	 * @return the defIncidentAccountCodeTransferableIdentity
	 */
	public String getDefIncidentAccountCodeTransferableIdentity() {
		return defIncidentAccountCodeTransferableIdentity;
	}

	/**
	 * @param defIncidentAccountCodeTransferableIdentity the defIncidentAccountCodeTransferableIdentity to set
	 */
	public void setDefIncidentAccountCodeTransferableIdentity(
			String defIncidentAccountCodeTransferableIdentity) {
		this.defIncidentAccountCodeTransferableIdentity = defIncidentAccountCodeTransferableIdentity;
	}

	/**
	 * @return the defIncidentAccountCodeId
	 */
	public Long getDefIncidentAccountCodeId() {
		return defIncidentAccountCodeId;
	}

	/**
	 * @param defIncidentAccountCodeId the defIncidentAccountCodeId to set
	 */
	public void setDefIncidentAccountCodeId(Long defIncidentAccountCodeId) {
		this.defIncidentAccountCodeId = defIncidentAccountCodeId;
	}

	/**
	 * @return the ciArrivalJetPortTransferableIdentity
	 */
	public String getCiArrivalJetPortTransferableIdentity() {
		return ciArrivalJetPortTransferableIdentity;
	}

	/**
	 * @param ciArrivalJetPortTransferableIdentity the ciArrivalJetPortTransferableIdentity to set
	 */
	public void setCiArrivalJetPortTransferableIdentity(
			String ciArrivalJetPortTransferableIdentity) {
		this.ciArrivalJetPortTransferableIdentity = ciArrivalJetPortTransferableIdentity;
	}

	/**
	 * @return the ciArrivalJetPortId
	 */
	public Long getCiArrivalJetPortId() {
		return ciArrivalJetPortId;
	}

	/**
	 * @param ciArrivalJetPortId the ciArrivalJetPortId to set
	 */
	public void setCiArrivalJetPortId(Long ciArrivalJetPortId) {
		this.ciArrivalJetPortId = ciArrivalJetPortId;
	}

	/**
	 * @return the ciResMobTransferableIdentity
	 */
	public String getCiResMobTransferableIdentity() {
		return ciResMobTransferableIdentity;
	}

	/**
	 * @param ciResMobTransferableIdentity the ciResMobTransferableIdentity to set
	 */
	public void setCiResMobTransferableIdentity(String ciResMobTransferableIdentity) {
		this.ciResMobTransferableIdentity = ciResMobTransferableIdentity;
	}

	/**
	 * @return the ciMobilizationId
	 */
	public Long getCiMobilizationId() {
		return ciMobilizationId;
	}

	/**
	 * @param ciMobilizationId the ciMobilizationId to set
	 */
	public void setCiMobilizationId(Long ciMobilizationId) {
		this.ciMobilizationId = ciMobilizationId;
	}

	/**
	 * @return the ciTravelMethod
	 */
	public String getCiTravelMethod() {
		return ciTravelMethod;
	}

	/**
	 * @param ciTravelMethod the ciTravelMethod to set
	 */
	public void setCiTravelMethod(String ciTravelMethod) {
		this.ciTravelMethod = ciTravelMethod;
	}

	/**
	 * @return the ciRentalLocation
	 */
	public String getCiRentalLocation() {
		return ciRentalLocation;
	}

	/**
	 * @param ciRentalLocation the ciRentalLocation to set
	 */
	public void setCiRentalLocation(String ciRentalLocation) {
		this.ciRentalLocation = ciRentalLocation;
	}

	/**
	 * @return the ciCheckInDate
	 */
	public Date getCiCheckInDate() {
		return ciCheckInDate;
	}

	/**
	 * @param ciCheckInDate the ciCheckInDate to set
	 */
	public void setCiCheckInDate(Date ciCheckInDate) {
		this.ciCheckInDate = ciCheckInDate;
	}

	/**
	 * @return the ciFirstWorkDate
	 */
	public Date getCiFirstWorkDate() {
		return ciFirstWorkDate;
	}

	/**
	 * @param ciFirstWorkDate the ciFirstWorkDate to set
	 */
	public void setCiFirstWorkDate(Date ciFirstWorkDate) {
		this.ciFirstWorkDate = ciFirstWorkDate;
	}

	/**
	 * @return the ciPrePlanningRemarks
	 */
	public String getCiPrePlanningRemarks() {
		return ciPrePlanningRemarks;
	}

	/**
	 * @param ciPrePlanningRemarks the ciPrePlanningRemarks to set
	 */
	public void setCiPrePlanningRemarks(String ciPrePlanningRemarks) {
		this.ciPrePlanningRemarks = ciPrePlanningRemarks;
	}

	/**
	 * @return the ciLengthAtAssignment
	 */
	public Long getCiLengthAtAssignment() {
		return ciLengthAtAssignment;
	}

	/**
	 * @param ciLengthAtAssignment the ciLengthAtAssignment to set
	 */
	public void setCiLengthAtAssignment(Long ciLengthAtAssignment) {
		this.ciLengthAtAssignment = ciLengthAtAssignment;
	}

	/**
	 * @return the dmTentativeDemobCity
	 */
	public String getDmTentativeDemobCity() {
		return dmTentativeDemobCity;
	}

	/**
	 * @param dmTentativeDemobCity the dmTentativeDemobCity to set
	 */
	public void setDmTentativeDemobCity(String dmTentativeDemobCity) {
		this.dmTentativeDemobCity = dmTentativeDemobCity;
	}

	/**
	 * @return the dmTentDemobStateTransferableIdentity
	 */
	public String getDmTentDemobStateTransferableIdentity() {
		return dmTentDemobStateTransferableIdentity;
	}

	/**
	 * @param dmTentDemobStateTransferableIdentity the dmTentDemobStateTransferableIdentity to set
	 */
	public void setDmTentDemobStateTransferableIdentity(
			String dmTentDemobStateTransferableIdentity) {
		this.dmTentDemobStateTransferableIdentity = dmTentDemobStateTransferableIdentity;
	}

	/**
	 * @return the dmTentativeDemobStateId
	 */
	public Long getDmTentativeDemobStateId() {
		return dmTentativeDemobStateId;
	}

	/**
	 * @param dmTentativeDemobStateId the dmTentativeDemobStateId to set
	 */
	public void setDmTentativeDemobStateId(Long dmTentativeDemobStateId) {
		this.dmTentativeDemobStateId = dmTentativeDemobStateId;
	}

	/**
	 * @return the dmTentativeArrivalDate
	 */
	public Date getDmTentativeArrivalDate() {
		return dmTentativeArrivalDate;
	}

	/**
	 * @param dmTentativeArrivalDate the dmTentativeArrivalDate to set
	 */
	public void setDmTentativeArrivalDate(Date dmTentativeArrivalDate) {
		this.dmTentativeArrivalDate = dmTentativeArrivalDate;
	}

	/**
	 * @return the dmReleaseDate
	 */
	public Date getDmReleaseDate() {
		return dmReleaseDate;
	}

	/**
	 * @param dmReleaseDate the dmReleaseDate to set
	 */
	public void setDmReleaseDate(Date dmReleaseDate) {
		this.dmReleaseDate = dmReleaseDate;
	}

	/**
	 * @return the dmTentativeReleaseDate
	 */
	public Date getDmTentativeReleaseDate() {
		return dmTentativeReleaseDate;
	}

	/**
	 * @param dmTentativeReleaseDate the dmTentativeReleaseDate to set
	 */
	public void setDmTentativeReleaseDate(Date dmTentativeReleaseDate) {
		this.dmTentativeReleaseDate = dmTentativeReleaseDate;
	}

	/**
	 * @return the dmReAssignable
	 */
	public Boolean getDmReAssignable() {
		return dmReAssignable;
	}

	/**
	 * @param dmReAssignable the dmReAssignable to set
	 */
	public void setDmReAssignable(Boolean dmReAssignable) {
		this.dmReAssignable = dmReAssignable;
	}

	/**
	 * @return the dmRestOvernight
	 */
	public Boolean getDmRestOvernight() {
		return dmRestOvernight;
	}

	/**
	 * @param dmRestOvernight the dmRestOvernight to set
	 */
	public void setDmRestOvernight(Boolean dmRestOvernight) {
		this.dmRestOvernight = dmRestOvernight;
	}

	/**
	 * @return the dmReleaseDispatchNotified
	 */
	public Boolean getDmReleaseDispatchNotified() {
		return dmReleaseDispatchNotified;
	}

	/**
	 * @param dmReleaseDispatchNotified the dmReleaseDispatchNotified to set
	 */
	public void setDmReleaseDispatchNotified(Boolean dmReleaseDispatchNotified) {
		this.dmReleaseDispatchNotified = dmReleaseDispatchNotified;
	}

	/**
	 * @return the dmPlanningDispatchNotified
	 */
	public Boolean getDmPlanningDispatchNotified() {
		return dmPlanningDispatchNotified;
	}

	/**
	 * @param dmPlanningDispatchNotified the dmPlanningDispatchNotified to set
	 */
	public void setDmPlanningDispatchNotified(Boolean dmPlanningDispatchNotified) {
		this.dmPlanningDispatchNotified = dmPlanningDispatchNotified;
	}

	/**
	 * @return the dmCheckoutFormPrinted
	 */
	public Boolean getDmCheckoutFormPrinted() {
		return dmCheckoutFormPrinted;
	}

	/**
	 * @param dmCheckoutFormPrinted the dmCheckoutFormPrinted to set
	 */
	public void setDmCheckoutFormPrinted(Boolean dmCheckoutFormPrinted) {
		this.dmCheckoutFormPrinted = dmCheckoutFormPrinted;
	}

	/**
	 * @return the dmReleaseRemarks
	 */
	public String getDmReleaseRemarks() {
		return dmReleaseRemarks;
	}

	/**
	 * @param dmReleaseRemarks the dmReleaseRemarks to set
	 */
	public void setDmReleaseRemarks(String dmReleaseRemarks) {
		this.dmReleaseRemarks = dmReleaseRemarks;
	}

	/**
	 * @return the dmPlanningRemarks
	 */
	public String getDmPlanningRemarks() {
		return dmPlanningRemarks;
	}

	/**
	 * @param dmPlanningRemarks the dmPlanningRemarks to set
	 */
	public void setDmPlanningRemarks(String dmPlanningRemarks) {
		this.dmPlanningRemarks = dmPlanningRemarks;
	}

	/**
	 * @return the dmAirTravel
	 */
	public IswAirTravel getDmAirTravel() {
		return dmAirTravel;
	}

	/**
	 * @param dmAirTravel the dmAirTravel to set
	 */
	public void setDmAirTravel(IswAirTravel dmAirTravel) {
		this.dmAirTravel = dmAirTravel;
	}

	/**
	 * @return the dmAirTravelId
	 */
	public Long getDmAirTravelId() {
		return dmAirTravelId;
	}

	/**
	 * @param dmAirTravelId the dmAirTravelId to set
	 */
	public void setDmAirTravelId(Long dmAirTravelId) {
		this.dmAirTravelId = dmAirTravelId;
	}

	/**
	 * @return the dmTravelMethod
	 */
	public String getDmTravelMethod() {
		return dmTravelMethod;
	}

	/**
	 * @param dmTravelMethod the dmTravelMethod to set
	 */
	public void setDmTravelMethod(String dmTravelMethod) {
		this.dmTravelMethod = dmTravelMethod;
	}

	/**
	 * @return the ciTentativeArrivalDate
	 */
	public Date getCiTentativeArrivalDate() {
		return ciTentativeArrivalDate;
	}

	/**
	 * @param ciTentativeArrivalDate the ciTentativeArrivalDate to set
	 */
	public void setCiTentativeArrivalDate(Date ciTentativeArrivalDate) {
		this.ciTentativeArrivalDate = ciTentativeArrivalDate;
	}

	/**
	 * @return the groundSupport
	 */
	public Boolean getGroundSupport() {
		return groundSupport;
	}

	/**
	 * @param groundSupport the groundSupport to set
	 */
	public void setGroundSupport(Boolean groundSupport) {
		this.groundSupport = groundSupport;
	}

	/**
	 * @return the workPeriodAssignments
	 */
	public Collection<IswWorkPeriodAssignment> getWorkPeriodAssignments() {
		return workPeriodAssignments;
	}

	/**
	 * @param workPeriodAssignments the workPeriodAssignments to set
	 */
	public void setWorkPeriodAssignments(
			Collection<IswWorkPeriodAssignment> workPeriodAssignments) {
		this.workPeriodAssignments = workPeriodAssignments;
	}

	/**
	 * @return the wpOvernightStayInfos
	 */
	public Collection<IswWorkPeriodOvernightStayInfo> getWpOvernightStayInfos() {
		return wpOvernightStayInfos;
	}

	/**
	 * @param wpOvernightStayInfos the wpOvernightStayInfos to set
	 */
	public void setWpOvernightStayInfos(
			Collection<IswWorkPeriodOvernightStayInfo> wpOvernightStayInfos) {
		this.wpOvernightStayInfos = wpOvernightStayInfos;
	}




}
