package gov.nwcg.isuite.core.reports.filter;

import java.util.ArrayList;
import java.util.Collection;

/*
 * Report filter for AllIncidentResourcesReport (AllIncidentResourcesReport.jrxml)
 */
public class AllIncidentResourcesReportFilter {
	private Long incidentId=null;
	private Long incidentGroupId=null;
	private Collection<String> resourceTypes=null;
	
	private Boolean allResourceCategories=false;
    private Boolean aircraft=false;
	private Boolean crews=false;
	private Boolean overhead=false;
	private Boolean equipment=false;
	
	private Boolean allGroups=false;
	private Boolean operations=false;
	private Boolean command=false;
	private Boolean logistics=false;
	private Boolean plans=false;
	private Boolean finance=false;
	private Boolean external=false;
	
	private Boolean allResourceStatuses = false;
	private Boolean checkin=false;
	private Boolean demobed=false;
	private Boolean reassigned=false;
	private Boolean pendingDemob=false;
	private Boolean filled=false;
	
	private Boolean strikeTeamTaskForce=false;
	private Boolean subTotalsFirstSort=false;
	private Boolean groupSubsection=false;

	private Collection<String> allResourcesSort = new ArrayList<String>();
	
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
	 * Returns the resourceTypes.
	 *
	 * @return 
	 *		the resourceTypes to return
	 */
	public Collection<String> getResourceTypes() {
		if(null==resourceTypes)
			resourceTypes = new ArrayList<String>();
		
		return resourceTypes;
	}
	
	/**
	 * Sets the resourceTypes.
	 *
	 * @param resourceTypes 
	 *			the resourceTypes to set
	 */
	public void setResourceTypes(Collection<String> resourceTypes) {
		this.resourceTypes = resourceTypes;
	}

	public static String getTypes(AllIncidentResourcesReportFilter filter){
		String types= "";
		
		if(filter.allResourceCategories){
			types="'A','C','E','O'";
		}else{
			if(filter.aircraft)types=types + (types.length() < 1 ? "'A'" : ",'A'") ;
			if(filter.crews)types=types + (types.length() < 1 ? "'C'" : ",'C'") ;
			if(filter.equipment)types=types + (types.length() < 1 ? "'E'" : ",'E'") ;
			if(filter.overhead)types=types + (types.length() < 1 ? "'O'" : ",'O'") ;
		}
		return types;
	}
	
	public static String getStatuses(AllIncidentResourcesReportFilter filter) {
		String statuses = "";
		
		if(filter.allResourceStatuses){
			statuses = "'F','D','P','R','C'";
		}else{
			if(filter.checkin)statuses=(statuses.length() < 1 ? "'C'" : ",'C'") ;
			if(filter.demobed)statuses=(statuses.length() < 1 ? "'D'" : statuses+",'D'") ;
			if(filter.reassigned)statuses=(statuses.length() < 1 ? "'R'" : statuses+",'R'") ;
			if(filter.pendingDemob)statuses=(statuses.length() < 1 ? "'P'" : statuses+",'P'") ;
			if(filter.filled)statuses=(statuses.length() < 1 ? "'F'" : statuses+",'F'") ;
		}
		return statuses;
	}
	
	public static String getSections(AllIncidentResourcesReportFilter filter){
		String sections = "";
		
		if(filter.allGroups){
			sections="'C','E','F','L','O','P'";
		}else {
			if(filter.command)sections=(sections.length() < 1 ? "'C'" : sections + ",'C'") ;
			if(filter.external)sections=(sections.length() < 1 ? "'E'" : sections + ",'E'") ;
			if(filter.finance)sections=(sections.length() < 1 ? "'F'" : sections + ",'F'") ;
			if(filter.logistics)sections=(sections.length() < 1 ? "'L'" : sections + ",'L'") ;
			if(filter.operations)sections=(sections.length() < 1 ? "'O'" : sections + ",'O'") ;
			if(filter.plans)sections=(sections.length() < 1 ? "'P'" : sections + ",'P'") ;
		}
		return sections;
		
	}
	
	public Boolean getAllResourceCategories() {
		return allResourceCategories;
	}
	public void setAllResourceCategories(Boolean allResourceCategories) {
		this.allResourceCategories = allResourceCategories;
	}
	public Boolean getAircraft() {
		return aircraft;
	}
	public void setAircraft(Boolean aircraft) {
		this.aircraft = aircraft;
	}
	public Boolean getCrews() {
		return crews;
	}
	public void setCrews(Boolean crews) {
		this.crews = crews;
	}
	public Boolean getOverhead() {
		return overhead;
	}
	public void setOverhead(Boolean overhead) {
		this.overhead = overhead;
	}
	public Boolean getEquipment() {
		return equipment;
	}
	public void setEquipment(Boolean equipment) {
		this.equipment = equipment;
	}
	public Boolean getAllGroups() {
		return allGroups;
	}
	public void setAllGroups(Boolean allGroups) {
		this.allGroups = allGroups;
	}
	public Boolean getOperations() {
		return operations;
	}
	public void setOperations(Boolean operations) {
		this.operations = operations;
	}
	public Boolean getCommand() {
		return command;
	}
	public void setCommand(Boolean command) {
		this.command = command;
	}
	public Boolean getLogistics() {
		return logistics;
	}
	public void setLogistics(Boolean logistics) {
		this.logistics = logistics;
	}
	public Boolean getPlans() {
		return plans;
	}
	public void setPlans(Boolean plans) {
		this.plans = plans;
	}
	public Boolean getFinance() {
		return finance;
	}
	public void setFinance(Boolean finance) {
		this.finance = finance;
	}
	public Boolean getExternal() {
		return external;
	}
	public void setExternal(Boolean external) {
		this.external = external;
	}
	public Boolean getAllResourceStatuses() {
		return allResourceStatuses;
	}
	public void setAllResourceStatuses(Boolean allResourceStatuses) {
		this.allResourceStatuses = allResourceStatuses;
	}
	public Boolean getCheckin() {
		return checkin;
	}
	public void setCheckin(Boolean checkin) {
		this.checkin = checkin;
	}
	public Boolean getDemobed() {
		return demobed;
	}
	public void setDemobed(Boolean demobed) {
		this.demobed = demobed;
	}
	public Boolean getReassigned() {
		return reassigned;
	}
	public void setReassigned(Boolean reassigned) {
		this.reassigned = reassigned;
	}
	public Boolean getPendingDemob() {
		return pendingDemob;
	}
	public void setPendingDemob(Boolean pendingDemob) {
		this.pendingDemob = pendingDemob;
	}
	public Boolean getFilled() {
		return filled;
	}
	public void setFilled(Boolean filled) {
		this.filled = filled;
	}
	public Boolean getStrikeTeamTaskForce() {
		if(null==strikeTeamTaskForce)
			return false;
		else
			return strikeTeamTaskForce;
	}
	public void setStrikeTeamTaskForce(Boolean strikeTeamTaskForce) {
		this.strikeTeamTaskForce = strikeTeamTaskForce;
	}
	public Boolean getSubTotalsFirstSort() {
		return subTotalsFirstSort;
	}
	public void setSubTotalsFirstSort(Boolean subTotalsFirstSort) {
		this.subTotalsFirstSort = subTotalsFirstSort;
	}
	public Boolean getGroupSubsection() {
		return groupSubsection;
	}
	public void setGroupSubsection(Boolean groupSubsection) {
		this.groupSubsection = groupSubsection;
	}
	public Collection<String> getAllResourcesSort() {
		return allResourcesSort;
	}
	public void setAllResourcesSort(Collection<String> allResourcesSort) {
		this.allResourcesSort = allResourcesSort;
	}

	public String getSortField(String field) {
		String fieldName = "";
		if(field.equals("requestNumber")){
			fieldName = "requestNumberSortValue";
		}else if(field.equals("resourceName")){
			fieldName = "resourceName";
		}else if(field.equals("itemCode")){
			fieldName = "kind";
		}else if(field.equals("unitId")){
			fieldName = "unit";
		}else if(field.equals("agencyCode")){
			fieldName = "agency";
		}else if(field.equals("assignmentStatus")){
			fieldName = "status";
		}else if(field.equals("checkInDate")){
			fieldName = "checkInDate";
		}else if(field.equals("mobDate")){
			fieldName = "mobDate";
		}else if(field.equals("firstWorkDay")){
			fieldName = "firstWorkDate";
		}else if(field.equals("lengthAtAssignment")){
			fieldName = "lngOfAssignment";
		}else if(field.equals("demobDate")){
			fieldName = "demobDate";
		}else if(field.equals("mobilizationTravelMethod")){
			fieldName = "returnTravelMethod";
		}else if(field.equals("cellPhone")){
			fieldName = "contactPhone";
		}
		return fieldName;
	}
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
}
