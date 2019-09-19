package gov.nwcg.isuite.core.reports.filter;

import java.util.ArrayList;
import java.util.Collection;


public class DemobPlanningReportFilter {
	private Long incidentId;
	private Long incidentGroupId;
	private Boolean includeSTTFComponents = false;

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
	
	private Boolean subTotalsFirstSort=false;
	private Boolean groupSubsection=false;

	private Collection<String> sorts = new ArrayList<String>();
		
	/**
	 * @return the incidentId
	 */
	public Long getIncidentId() {
		return incidentId;
	}
	/**
	 * @param incidentId the incidentId to set
	 */
	
	public void setIncidentId(Long incidentId) {
		this.incidentId = incidentId;
	}
	
	/**
	 * @return the includeSTTFComponents
	 */
	public Boolean getIncludeSTTFComponents() {
		return includeSTTFComponents;
	}
	
	/**
	 * @param includeSTTFComponents the includeSTTFComponents to set
	 */
	public void setIncludeSTTFComponents(Boolean includeSTTFComponents) {
		this.includeSTTFComponents = includeSTTFComponents;
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
	public Collection<String> getSorts() {
		return sorts;
	}
	public void setSorts(Collection<String> sorts) {
		this.sorts = sorts;
	}

	public static String getTypes(DemobPlanningReportFilter filter){
		String types= "";
		
		if(filter.allResourceCategories){
			types="'A','C','E','O'";
		}else{
			if(filter.aircraft)types=(types.length() < 1 ? "'A'" : types+",'A'") ;
			if(filter.crews)types=(types.length() < 1 ? "'C'" : types+",'C'") ;
			if(filter.equipment)types=(types.length() < 1 ? "'E'" : types+",'E'") ;
			if(filter.overhead)types=(types.length() < 1 ? "'O'" : types+",'O'") ;
		}
		return types;
	}
	
	public static String getStatuses(DemobPlanningReportFilter filter) {
		String statuses = "";
		
		if(filter.allResourceStatuses){
			statuses = "'F','D','P','R','C'";
		}else{
			if(filter.checkin)statuses=(statuses.length() < 1 ? "'C'" : statuses+",'C'") ;
			if(filter.demobed)statuses=(statuses.length() < 1 ? "'D'" : statuses+",'D'") ;
			if(filter.reassigned)statuses=(statuses.length() < 1 ? "'R'" : statuses+",'R'") ;
			if(filter.pendingDemob)statuses=(statuses.length() < 1 ? "'P'" : statuses+",'P'") ;
			if(filter.filled)statuses=(statuses.length() < 1 ? "'F'" : statuses+",'F'") ;
		}
		return statuses;
	}
	
	public static String getSections(DemobPlanningReportFilter filter){
		String sections = "";
		
		if(filter.allGroups){
			sections="'C','E','F','L','O','P'";
		}else {
			if(filter.command)sections=(sections.length() < 1 ? "'C'" : sections+",'C'") ;
			if(filter.external)sections=(sections.length() < 1 ? "'E'" : sections+",'E'") ;
			if(filter.finance)sections=(sections.length() < 1 ? "'F'" : sections+",'F'") ;
			if(filter.logistics)sections=(sections.length() < 1 ? "'L'" : sections+",'L'") ;
			if(filter.operations)sections=(sections.length() < 1 ? "'O'" : sections+",'O'") ;
			if(filter.plans)sections=(sections.length() < 1 ? "'P'" : sections+",'P'") ;
		}
		return sections;
		
	}
	public Long getIncidentGroupId() {
		return incidentGroupId;
	}
	public void setIncidentGroupId(Long incidentGroupId) {
		this.incidentGroupId = incidentGroupId;
	}
	
}
