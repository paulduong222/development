package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.core.domain.AirTravel;
import gov.nwcg.isuite.core.domain.JetPort;
import gov.nwcg.isuite.core.domain.ResourceMobilization;
import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.QuestionTypeEnum;
import gov.nwcg.isuite.framework.types.TravelMethodTypeEnum;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

public class IncidentResourceCheckInDemobVo extends AbstractVo {
	private Long resourceId; // needed as reference in dao
	private Long workPeriodId; // needed as reference in dao
	private Long incidentId; // need as reference in dao
	private ResourceMobilizationVo ciResourceMobilizationVo;
	private JetPortVo ciJetPortVo;
	private String ciRentalLocation;
	private Date ciFirstWorkDate;
	private Long ciLengthAtAssignment;
	private String ciPrePlanningRemarks;

	private String dmTentativeDemobCity;
	private Long dmTentativeDemobState;
	private TravelMethodTypeEnum dmTravelMethod;
	private Boolean dmReAssignable;
	private Boolean dmRestOvernight;
	private Boolean dmReleaseDispatchNotified;
	private Boolean dmPlanningDispatchNotified;
	private Boolean dmCheckoutFormPrinted;
	private String dmReleaseRemarks;
	private String dmPlanningRemarks;
	private Date dmTentativeReleaseDate;
	
	private AirTravelVo dmAirTravelVo;
	
	private Collection<ResourceKindVo> resourceKindVos = new ArrayList<ResourceKindVo>();
	
	private Collection<IncidentQuestionVo> airTravelQuestions = new ArrayList<IncidentQuestionVo>();
	private Collection<IncidentQuestionVo> prePlanningQuestions = new ArrayList<IncidentQuestionVo>();
	
	
	public IncidentResourceCheckInDemobVo() {

	}

	/**
	 * Returns the dmTentativeDemobCity.
	 * 
	 * @return the dmTentativeDemobCity to return
	 */
	public String getDmTentativeDemobCity() {
		return dmTentativeDemobCity;
	}

	/**
	 * Sets the dmTentativeDemobCity.
	 * 
	 * @param dmTentativeDemobCity
	 *            the dmTentativeDemobCity to set
	 */
	public void setDmTentativeDemobCity(String dmTentativeDemobCity) {
		this.dmTentativeDemobCity = dmTentativeDemobCity;
	}

	/**
	 * Returns the dmTentativeDemobState.
	 * 
	 * @return the dmTentativeDemobState to return
	 */
	public Long getDmTentativeDemobState() {
		return dmTentativeDemobState;
	}

	/**
	 * Sets the dmTentativeDemobState.
	 * 
	 * @param dmTentativeDemobState
	 *            the dmTentativeDemobState to set
	 */
	public void setDmTentativeDemobState(Long dmTentativeDemobState) {
		this.dmTentativeDemobState = dmTentativeDemobState;
	}

	/**
	 * Returns the dmTravelMethod.
	 * 
	 * @return the dmTravelMethod to return
	 */
	public TravelMethodTypeEnum getDmTravelMethod() {
		return dmTravelMethod;
	}

	/**
	 * Sets the dmTravelMethod.
	 * 
	 * @param dmTravelMethod
	 *            the dmTravelMethod to set
	 */
	public void setDmTravelMethod(TravelMethodTypeEnum dmTravelMethod) {
		this.dmTravelMethod = dmTravelMethod;
	}

	/**
	 * Returns the ciResourceMobilizationVo.
	 * 
	 * @return the ciResourceMobilizationVo to return
	 */
	public ResourceMobilizationVo getCiResourceMobilizationVo() {
		return ciResourceMobilizationVo;
	}

	/**
	 * Sets the ciResourceMobilizationVo.
	 * 
	 * @param ciResourceMobilizationVo
	 *            the ciResourceMobilizationVo to set
	 */
	public void setCiResourceMobilizationVo(
			ResourceMobilizationVo ciResourceMobilizationVo) {
		this.ciResourceMobilizationVo = ciResourceMobilizationVo;
	}

	/**
	 * Sets the ciResourceMobilization.
	 * (Convenience method for transformation)
	 * 
	 * @param entity
	 *            the ciResourceMobilization entity to set
	 */
	public void setCiResourceMobilization(ResourceMobilization entity) throws Exception {
		if(null != entity){
			this.setCiResourceMobilizationVo(ResourceMobilizationVo.getInstance(entity,false));
			this.getCiResourceMobilizationVo().setStartDate(entity.getStartDate());
		}
	}
	
	/**
	 * Returns the ciJetPortVo.
	 * 
	 * @return the ciJetPortVo to return
	 */
	public JetPortVo getCiJetPortVo() {
		return ciJetPortVo;
	}

	/**
	 * Sets the ciJetPortVo.
	 * 
	 * @param ciJetPortVo
	 *            the ciJetPortVo to set
	 */
	public void setCiJetPortVo(JetPortVo ciJetPortVo) {
		this.ciJetPortVo = ciJetPortVo;
	}

	/**
	 * Sets the ciArrivalJetPort.
	 * (Convenience method for transformation)
	 * 
	 * @param entity
	 *            the ciJetPort entity to set
	 */
	public void setCiArrivalJetPort(JetPort entity) throws Exception {
		if(null!=entity){
			this.setCiJetPortVo(JetPortVo.getInstance(entity,true));
		}
	}

	/**
	 * Returns the ciRentalLocation.
	 * 
	 * @return the ciRentalLocation to return
	 */
	public String getCiRentalLocation() {
		return ciRentalLocation;
	}

	/**
	 * Sets the ciRentalLocation.
	 * 
	 * @param ciRentalLocation
	 *            the ciRentalLocation to set
	 */
	public void setCiRentalLocation(String ciRentalLocation) {
		this.ciRentalLocation = ciRentalLocation;
	}

	/**
	 * Returns the ciFirstWorkDate.
	 * 
	 * @return the ciFirstWorkDate to return
	 */
	public Date getCiFirstWorkDate() {
		return ciFirstWorkDate;
	}

	/**
	 * Sets the ciFirstWorkDate.
	 * 
	 * @param ciFirstWorkDate
	 *            the ciFirstWorkDate to set
	 */
	public void setCiFirstWorkDate(Date ciFirstWorkDate) {
		this.ciFirstWorkDate = ciFirstWorkDate;
	}

	/**
	 * Returns the ciLengthAtAssignment.
	 * 
	 * @return the ciLengthAtAssignment to return
	 */
	public Long getCiLengthAtAssignment() {
		return ciLengthAtAssignment;
	}

	/**
	 * Sets the ciLengthAtAssignment.
	 * 
	 * @param ciLengthAtAssignment
	 *            the ciLengthAtAssignment to set
	 */
	public void setCiLengthAtAssignment(Long ciLengthAtAssignment) {
		this.ciLengthAtAssignment = ciLengthAtAssignment;
	}

	/**
	 * Returns the ciPrePlanningRemarks.
	 * 
	 * @return the ciPrePlanningRemarks to return
	 */
	public String getCiPrePlanningRemarks() {
		return ciPrePlanningRemarks;
	}

	/**
	 * Sets the ciPrePlanningRemarks.
	 * 
	 * @param ciPrePlanningRemarks
	 *            the ciPrePlanningRemarks to set
	 */
	public void setCiPrePlanningRemarks(String ciPrePlanningRemarks) {
		this.ciPrePlanningRemarks = ciPrePlanningRemarks;
	}

	/**
	 * Returns the resourceKindVos.
	 * 
	 * @return the resourceKindVos to return
	 */
	public Collection<ResourceKindVo> getResourceKindVos() {
		return resourceKindVos;
	}

	/**
	 * Sets the resourceKindVos.
	 * 
	 * @param resourceKindVos
	 *            the resourceKindVos to set
	 */
	public void setResourceKindVos(Collection<ResourceKindVo> resourceKindVos) {
		this.resourceKindVos = resourceKindVos;
	}

	/**
	 * Returns the dmReAssignable.
	 *
	 * @return 
	 *		the dmReAssignable to return
	 */
	public Boolean getDmReAssignable() {
		return dmReAssignable;
	}

	/**
	 * Sets the dmReAssignable.
	 *
	 * @param dmReAssignable 
	 *			the dmReAssignable to set
	 */
	public void setDmReAssignable(Boolean dmReAssignable) {
		this.dmReAssignable = dmReAssignable;
	}

	/**
	 * Returns the dmRestOvernight.
	 *
	 * @return 
	 *		the dmRestOvernight to return
	 */
	public Boolean getDmRestOvernight() {
		return dmRestOvernight;
	}

	/**
	 * Sets the dmRestOvernight.
	 *
	 * @param dmRestOvernight 
	 *			the dmRestOvernight to set
	 */
	public void setDmRestOvernight(Boolean dmRestOvernight) {
		this.dmRestOvernight = dmRestOvernight;
	}

	/**
	 * Returns the dmReleaseDispatchNotified.
	 *
	 * @return 
	 *		the dmReleaseDispatchNotified to return
	 */
	public Boolean getDmReleaseDispatchNotified() {
		return dmReleaseDispatchNotified;
	}

	/**
	 * Sets the dmReleaseDispatchNotified.
	 *
	 * @param dmReleaseDispatchNotified 
	 *			the dmReleaseDispatchNotified to set
	 */
	public void setDmReleaseDispatchNotified(Boolean dmReleaseDispatchNotified) {
		this.dmReleaseDispatchNotified = dmReleaseDispatchNotified;
	}

	/**
	 * Returns the dmPlanningDispatchNotified.
	 *
	 * @return 
	 *		the dmPlanningDispatchNotified to return
	 */
	public Boolean getDmPlanningDispatchNotified() {
		return dmPlanningDispatchNotified;
	}

	/**
	 * Sets the dmPlanningDispatchNotified.
	 *
	 * @param dmPlanningDispatchNotified 
	 *			the dmPlanningDispatchNotified to set
	 */
	public void setDmPlanningDispatchNotified(Boolean dmPlanningDispatchNotified) {
		this.dmPlanningDispatchNotified = dmPlanningDispatchNotified;
	}

	/**
	 * Returns the dmCheckoutFormPrinted.
	 *
	 * @return 
	 *		the dmCheckoutFormPrinted to return
	 */
	public Boolean getDmCheckoutFormPrinted() {
		return dmCheckoutFormPrinted;
	}

	/**
	 * Sets the dmCheckoutFormPrinted.
	 *
	 * @param dmCheckoutFormPrinted 
	 *			the dmCheckoutFormPrinted to set
	 */
	public void setDmCheckoutFormPrinted(Boolean dmCheckoutFormPrinted) {
		this.dmCheckoutFormPrinted = dmCheckoutFormPrinted;
	}

	/**
	 * Returns the dmReleaseRemarks.
	 *
	 * @return 
	 *		the dmReleaseRemarks to return
	 */
	public String getDmReleaseRemarks() {
		return dmReleaseRemarks;
	}

	/**
	 * Sets the dmReleaseRemarks.
	 *
	 * @param dmReleaseRemarks 
	 *			the dmReleaseRemarks to set
	 */
	public void setDmReleaseRemarks(String dmReleaseRemarks) {
		this.dmReleaseRemarks = dmReleaseRemarks;
	}

	/**
	 * Returns the dmPlanningRemarks.
	 *
	 * @return 
	 *		the dmPlanningRemarks to return
	 */
	public String getDmPlanningRemarks() {
		return dmPlanningRemarks;
	}

	/**
	 * Sets the dmPlanningRemarks.
	 *
	 * @param dmPlanningRemarks 
	 *			the dmPlanningRemarks to set
	 */
	public void setDmPlanningRemarks(String dmPlanningRemarks) {
		this.dmPlanningRemarks = dmPlanningRemarks;
	}

	/**
	 * Returns the resourceId.
	 *
	 * @return 
	 *		the resourceId to return
	 */
	public Long getResourceId() {
		return resourceId;
	}

	/**
	 * Sets the resourceId.
	 *
	 * @param resourceId 
	 *			the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * Returns the dmAirTravelVo.
	 *
	 * @return 
	 *		the dmAirTravelVo to return
	 */
	public AirTravelVo getDmAirTravelVo() {
		return dmAirTravelVo;
	}

	/**
	 * Sets the dmAirTravelVo.
	 *
	 * @param dmAirTravelVo 
	 *			the dmAirTravelVo to set
	 */
	public void setDmAirTravelVo(AirTravelVo dmAirTravelVo) {
		this.dmAirTravelVo = dmAirTravelVo;
	}

	/**
	 * Sets the dmAirTravel.
	 * (Convenience method for transformation)
	 * 
	 * @param entity
	 *            the dmAirTravel entity to set
	 */
	public void setDmAirTravel(AirTravel entity) throws Exception {
		if(null!=entity){
			this.setDmAirTravelVo(AirTravelVo.getInstance(entity,true));
		}
	}

	/**
	 * Convenience method to sort through collection of
	 * incidentQuestionvos and add to the appropriate collection.
	 * 
	 * @param vos
	 */
	public void setIncidentQuestions(Collection<IncidentQuestionVo> vos){
		if(null!=vos){
			for(IncidentQuestionVo vo : vos){
				if(vo.getQuestionVo().getQuestionType().equals(QuestionTypeEnum.AIRTRAVEL)){
					this.getAirTravelQuestions().add(vo);
				}
				if(vo.getQuestionVo().getQuestionType().equals(QuestionTypeEnum.PREPLANNING)){
					this.getPrePlanningQuestions().add(vo);
				}
			}
		}
	}
	
	/**
	 * Convenience method to sort through collection of
	 * workperiodquestionvalues and add to the appropriate collection.
	 * 
	 * @param vos
	 */
	public void setWorkPeriodQuestionValues(Collection<WorkPeriodQuestionValueVo> vos){
		if(null!=vos){
			for(WorkPeriodQuestionValueVo vo : vos){
				/*
				 * Add the question value record to the correct
				 * incidentQuestionVo
				 */
				if(null != vo.getIncidentQuestionVo().getId()){
					for(IncidentQuestionVo iqvo : this.getAirTravelQuestions()){
						if(vo.getIncidentQuestionVo().getId().equals(iqvo.getId())){
							iqvo.setWorkPeriodQuestionValueVo(vo);
						}
					}
					for(IncidentQuestionVo iqvo : this.getPrePlanningQuestions()){
						if(vo.getIncidentQuestionVo().getId().equals(iqvo.getId())){
							iqvo.setWorkPeriodQuestionValueVo(vo);
						}
					}
				}
			}
		}
	}
	
	/**
	 * Returns the workPeriodId.
	 *
	 * @return 
	 *		the workPeriodId to return
	 */
	public Long getWorkPeriodId() {
		return workPeriodId;
	}

	/**
	 * Sets the workPeriodId.
	 *
	 * @param workPeriodId 
	 *			the workPeriodId to set
	 */
	public void setWorkPeriodId(Long workPeriodId) {
		this.workPeriodId = workPeriodId;
	}

	/**
	 * Returns the airTravelQuestions.
	 *
	 * @return 
	 *		the airTravelQuestions to return
	 */
	public Collection<IncidentQuestionVo> getAirTravelQuestions() {
		return airTravelQuestions;
	}

	/**
	 * Sets the airTravelQuestions.
	 *
	 * @param airTravelQuestions 
	 *			the airTravelQuestions to set
	 */
	public void setAirTravelQuestions(
			Collection<IncidentQuestionVo> airTravelQuestions) {
		this.airTravelQuestions = airTravelQuestions;
	}

	/**
	 * Returns the prePlanningQuestions.
	 *
	 * @return 
	 *		the prePlanningQuestions to return
	 */
	public Collection<IncidentQuestionVo> getPrePlanningQuestions() {
		return prePlanningQuestions;
	}

	/**
	 * Sets the prePlanningQuestions.
	 *
	 * @param prePlanningQuestions 
	 *			the prePlanningQuestions to set
	 */
	public void setPrePlanningQuestions(
			Collection<IncidentQuestionVo> prePlanningQuestions) {
		this.prePlanningQuestions = prePlanningQuestions;
	}

	/**
	 * Returns the incidentId.
	 *
	 * @return 
	 *		the incidentId to return
	 */
	public Long getIncidentId() {
		return incidentId;
	}

	/**
	 * Sets the incidentId.
	 *
	 * @param incidentId 
	 *			the incidentId to set
	 */
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}

	/**
	 * Returns the dmTentativeReleaseDate.
	 *
	 * @return 
	 *		the dmTentativeReleaseDate to return
	 */
	public Date getDmTentativeReleaseDate() {
		return dmTentativeReleaseDate;
	}

	/**
	 * Sets the dmTentativeReleaseDate.
	 *
	 * @param dmTentativeReleaseDate 
	 *			the dmTentativeReleaseDate to set
	 */
	public void setDmTentativeReleaseDate(Date dmTentativeReleaseDate) {
		this.dmTentativeReleaseDate = dmTentativeReleaseDate;
	}
}
