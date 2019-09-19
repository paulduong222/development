package gov.nwcg.isuite.core.reports.data;

import gov.nwcg.isuite.core.domain.Assignment;
import gov.nwcg.isuite.core.domain.IncidentResource;
import gov.nwcg.isuite.core.domain.Resource;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.DateUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.util.Collection;
import java.util.Date;



/**
 * Report data object for StrikeTeamTaskForceReport.jrxml.
 */
public class StrikeTeamTaskForceReportData {
	private Long resourceId;
	private String group;
	private String resourceName;
	private String requestNumber;
	private String kind;
	private String itemName;
	private String agency;
	private String unit;
	private String returnTravelMethod;
	private String status;
	private Long numPersonnel;
	private Date lastWorkDay;
	private Date tentativeReleaseDate;
	private Date actualReleaseDate;
	private String stepValue;
	
	public StrikeTeamTaskForceReportData(){
	}

	public static Collection<StrikeTeamTaskForceReportData> buildTree(Collection<StrikeTeamTaskForceReportData> list,Resource entity,int groupId, int step){
		list.add(StrikeTeamTaskForceReportData.getInstance(entity, groupId,step));
		if(null != entity.getChildResources() && entity.getChildResources().size() > 0){
			
			try{
				for(Resource child : entity.getChildResources()){
					StrikeTeamTaskForceReportData.buildTree(list,child,groupId,(step+1));
				}
			}catch(Exception e){}
		}
		
		return list;
	}
	
	public static StrikeTeamTaskForceReportData getInstance(Resource entity,int groupId, int step) {
		StrikeTeamTaskForceReportData data = new StrikeTeamTaskForceReportData();

		data.setGroup(String.valueOf(groupId));
		data.setStepValue(String.valueOf(step));
		data.setResourceName((BooleanUtility.isFalse(entity.isPerson()) ?  entity.getResourceName() : entity.getLastName() + ", " + entity.getFirstName()));
		
		if(null != entity.getAgency())
			data.setAgency(entity.getAgency().getAgencyCode());
		
		if(null != entity.getOrganization())
			data.setUnit(entity.getOrganization().getUnitCode());
		
//		data.setNumPersonnel(String.valueOf(entity.getNumberOfPersonnel()));
//
//		if(!StringUtility.hasValue(data.getNumPersonnel()))
//			data.setNumPersonnel("0");
		
		if(null != entity.getIncidentResources() && entity.getIncidentResources().size() > 0){
			IncidentResource irEntity = entity.getIncidentResources().iterator().next();
		
			if(null != irEntity.getWorkPeriod()){
				data.setTentativeReleaseDate(irEntity.getWorkPeriod().getDMTentativeReleaseDate());
				data.setActualReleaseDate(irEntity.getWorkPeriod().getDMReleaseDate());
				data.setReturnTravelMethod((null != irEntity.getWorkPeriod().getDMTravelMethod() ? irEntity.getWorkPeriod().getDMTravelMethod().name() : ""));
				
				if(null != irEntity.getWorkPeriod().getCIFirstWorkDate() 
						&&
				   (null != irEntity.getWorkPeriod().getCILengthAtAssignment() && irEntity.getWorkPeriod().getCILengthAtAssignment().intValue() > 0)){
					Date dt = null;
					try{
						dt=DateUtil.addDaysToDate(irEntity.getWorkPeriod().getCIFirstWorkDate(), irEntity.getWorkPeriod().getCILengthAtAssignment().intValue());
					}catch(Exception e){
						// smother
					}
					 
					data.setLastWorkDay(dt);
				}
				
				if(null != irEntity.getWorkPeriod().getAssignments() && irEntity.getWorkPeriod().getAssignments().size() > 0){
					// find first with null end date
					for(Assignment a : irEntity.getWorkPeriod().getAssignments()){
						if(a.getEndDate() == null){
							data.setStatus(a.getAssignmentStatus().name());
							data.setRequestNumber(StringUtility.leftPad(a.getRequestNumber(),step));
							if(null != a.getKind()){
								data.setKind(a.getKind().getCode());
							}
						}
					}
				}
			}
		}
		return data;
	}
	
	public static StrikeTeamTaskForceReportData getInstance(StrikeTeamTaskForceReportData data,int groupId, int step) {
		data.setGroup(String.valueOf(groupId));
		data.setStepValue(String.valueOf(step));
		data.setRequestNumber(StringUtility.leftPad(data.getRequestNumber(), step));
		
		return data;
	}
	
	/**
	 * Returns the resourceName.
	 *
	 * @return 
	 *		the resourceName to return
	 */
	public String getResourceName() {
		return resourceName;
	}

	/**
	 * Sets the resourceName.
	 *
	 * @param resourceName 
	 *			the resourceName to set
	 */
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}

	/**
	 * Returns the requestNumber.
	 *
	 * @return 
	 *		the requestNumber to return
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/**
	 * Sets the requestNumber.
	 *
	 * @param requestNumber 
	 *			the requestNumber to set
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	/**
	 * Returns the kind.
	 *
	 * @return 
	 *		the kind to return
	 */
	public String getKind() {
		return kind;
	}

	/**
	 * Sets the kind.
	 *
	 * @param kind 
	 *			the kind to set
	 */
	public void setKind(String kind) {
		this.kind = kind;
	}

	/**
	 * Returns the agency.
	 *
	 * @return 
	 *		the agency to return
	 */
	public String getAgency() {
		return agency;
	}

	/**
	 * Sets the agency.
	 *
	 * @param agency 
	 *			the agency to set
	 */
	public void setAgency(String agency) {
		this.agency = agency;
	}

	/**
	 * Returns the unit.
	 *
	 * @return 
	 *		the unit to return
	 */
	public String getUnit() {
		return unit;
	}

	/**
	 * Sets the unit.
	 *
	 * @param unit 
	 *			the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}

	/**
	 * Returns the returnTravelMethod.
	 *
	 * @return 
	 *		the returnTravelMethod to return
	 */
	public String getReturnTravelMethod() {
		if(null == returnTravelMethod)
			return "";
		else 
			return returnTravelMethod;
	}

	/**
	 * Sets the returnTravelMethod.
	 *
	 * @param returnTravelMethod 
	 *			the returnTravelMethod to set
	 */
	public void setReturnTravelMethod(String returnTravelMethod) {
		this.returnTravelMethod = returnTravelMethod;
	}

	/**
	 * Returns the status.
	 *
	 * @return 
	 *		the status to return
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * Sets the status.
	 *
	 * @param status 
	 *			the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * Returns the numPersonnel.
	 *
	 * @return 
	 *		the numPersonnel to return
	 */
	public Long getNumPersonnel() {
		if(null==numPersonnel)
			return 0L;
		else
			return numPersonnel;
	}

	/**
	 * Sets the numPersonnel.
	 *
	 * @param numPersonnel 
	 *			the numPersonnel to set
	 */
	public void setNumPersonnel(Long numPersonnel) {
		this.numPersonnel = numPersonnel;
	}

	/**
	 * Returns the lastWorkDay.
	 *
	 * @return 
	 *		the lastWorkDay to return
	 */
	public Date getLastWorkDay() {
		return lastWorkDay;
	}

	/**
	 * Sets the lastWorkDay.
	 *
	 * @param lastWorkDay 
	 *			the lastWorkDay to set
	 */
	public void setLastWorkDay(Date lastWorkDay) {
		this.lastWorkDay = lastWorkDay;
	}

	/**
	 * Returns the tentativeReleaseDate.
	 *
	 * @return 
	 *		the tentativeReleaseDate to return
	 */
	public Date getTentativeReleaseDate() {
		return tentativeReleaseDate;
	}

	/**
	 * Sets the tentativeReleaseDate.
	 *
	 * @param tentativeReleaseDate 
	 *			the tentativeReleaseDate to set
	 */
	public void setTentativeReleaseDate(Date tentativeReleaseDate) {
		this.tentativeReleaseDate = tentativeReleaseDate;
	}

	/**
	 * Returns the actualReleaseDate.
	 *
	 * @return 
	 *		the actualReleaseDate to return
	 */
	public Date getActualReleaseDate() {
		return actualReleaseDate;
	}

	/**
	 * Sets the actualReleaseDate.
	 *
	 * @param actualReleaseDate 
	 *			the actualReleaseDate to set
	 */
	public void setActualReleaseDate(Date actualReleaseDate) {
		this.actualReleaseDate = actualReleaseDate;
	}

	public String getGroup() {
		return group;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public String getStepValue() {
		return stepValue;
	}

	public void setStepValue(String stepValue) {
		this.stepValue = stepValue;
	}

	/**
	 * @param itemName the itemName to set
	 */
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	/**
	 * @return the itemName
	 */
	public String getItemName() {
		return itemName;
	}

	/**
	 * @param resourceId the resourceId to set
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	/**
	 * @return the resourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}

	
}
