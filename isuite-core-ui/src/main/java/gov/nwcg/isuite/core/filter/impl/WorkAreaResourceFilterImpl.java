package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.WorkAreaResourceFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


/**
 * @author bsteiner
 *
 */
public class WorkAreaResourceFilterImpl extends FilterImpl implements WorkAreaResourceFilter {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8641904450842036884L;

	private Long workAreaId=0L;
	
	private boolean excludeFilled=false;
	private boolean includeDemob=false;
	
	private boolean assignedToIncident=false;
	private boolean permanent=false;
	private boolean overhead=false;
	private boolean aircraft=false;
	private boolean personnel=false;
	private boolean crews=false;
	private boolean equipment=false;

	private String activeStatus;
	private boolean enabled=true;
	private Boolean deletable;	
	private String deletableString;
	
	private Boolean person;
	private Boolean contracted;
	private Boolean leader;
	private String other1;
	private String other2;
	private String other3;
	private String resourceName;
	private String lastName;
	private String firstName;
	private String resourceStatus;
	private Long numberOfPersonnel;
    private String leaderName;
	private String resourceAgencyName;
	private String resourceOrganizationName;
    private Long workAreaResourceId;

    private String kindDescription;
    private String agencyCode;
    //private String unitCode;
    private String resourceUnitCode;
    private String incidentName;
    private String assignmentStatus;
    
    private String crypticDateFilterCode;
    private Date mobilizationStartDate;
    
    private String requestNumber;

    private Collection<Integer> excludedResourceIds = new ArrayList<Integer>();
    
	public WorkAreaResourceFilterImpl(){
		
	}

	/**
	 * Returns the workAreaId.
	 *
	 * @return 
	 *		the workAreaId to return
	 */
	public Long getWorkAreaId() {
		return workAreaId;
	}

	/**
	 * Sets the workAreaId.
	 *
	 * @param workAreaId 
	 *			the workAreaId to set
	 */
	public void setWorkAreaId(Long workAreaId) {
		this.workAreaId = workAreaId;
	}

	/**
	 * Returns the excludeFilled.
	 *
	 * @return 
	 *		the excludeFilled to return
	 */
	public boolean isExcludeFilled() {
		return excludeFilled;
	}

	/**
	 * Sets the excludeFilled.
	 *
	 * @param excludeFilled 
	 *			the excludeFilled to set
	 */
	public void setExcludeFilled(boolean excludeFilled) {
		this.excludeFilled = excludeFilled;
	}

	/**
	 * Returns the includeDemob.
	 *
	 * @return 
	 *		the includeDemob to return
	 */
	public boolean isIncludeDemob() {
		return includeDemob;
	}

	/**
	 * Sets the includeDemob.
	 *
	 * @param includeDemob 
	 *			the includeDemob to set
	 */
	public void setIncludeDemob(boolean includeDemob) {
		this.includeDemob = includeDemob;
	}

	/**
	 * Returns the assignedToIncident.
	 *
	 * @return 
	 *		the assignedToIncident to return
	 */
	public boolean isAssignedToIncident() {
		return assignedToIncident;
	}

	/**
	 * Sets the assignedToIncident.
	 *
	 * @param assignedToIncident 
	 *			the assignedToIncident to set
	 */
	public void setAssignedToIncident(boolean assignedToIncident) {
		this.assignedToIncident = assignedToIncident;
	}

	/**
	 * Returns the permanent.
	 *
	 * @return 
	 *		the permanent to return
	 */
	public boolean isPermanent() {
		return permanent;
	}

	/**
	 * Sets the permanent.
	 *
	 * @param permanent 
	 *			the permanent to set
	 */
	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
	public boolean isOverhead() {
		return overhead;
	}

	/**
	 * Returns the overhead.
	 *
	 * @return 
	 *		the overhead to return
	 */
	public boolean getOverhead() {
		return isOverhead();
	}
	
	/**
	 * Sets the overhead.
	 *
	 * @param overhead 
	 *			the overhead to set
	 */
	public void setOverhead(boolean overhead) {
		this.overhead = overhead;
	}

	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
	public boolean isAircraft() {
		return aircraft;
	}

	/**
	 * Returns the aircraft.
	 *
	 * @return 
	 *		the aircraft to return
	 */
	public boolean getAircraft() {
		return isAircraft();
	}
	
	/**
	 * Sets the aircraft.
	 *
	 * @param aircraft 
	 *			the aircraft to set
	 */
	public void setAircraft(boolean aircraft) {
		this.aircraft = aircraft;
	}

	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
	public boolean isPersonnel() {
		return personnel;
	}

	/**
	 * Returns the personnel.
	 *
	 * @return 
	 *		the personnel to return
	 */
	public boolean getPersonnel() {
		return isPersonnel();
	}
	
	/**
	 * Sets the personnel.
	 *
	 * @param personnel 
	 *			the personnel to set
	 */
	public void setPersonnel(boolean personnel) {
		this.personnel = personnel;
	}

	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
	public boolean isCrews() {
		return crews;
	}

	/**
	 * Returns the crews.
	 *
	 * @return 
	 *		the crews to return
	 */
	public boolean getCrews() {
		return isCrews();
	}
	
	/**
	 * Sets the crews.
	 *
	 * @param crews 
	 *			the crews to set
	 */
	public void setCrews(boolean crews) {
		this.crews = crews;
	}

	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
	public boolean isEquipment() {
		return equipment;
	}

	/**
	 * Returns the equipment.
	 *
	 * @return 
	 *		the equipment to return
	 */
	public boolean getEquipment() {
		return isEquipment();
	}
	
	/**
	 * Sets the equipment.
	 *
	 * @param equipment 
	 *			the equipment to set
	 */
	public void setEquipment(boolean equipment) {
		this.equipment = equipment;
	}

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public boolean getEnabled() {
		return isEnabled();
	}
	
	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * Returns the deletable.
	 *
	 * @return 
	 *		the deletable to return
	 */
	public Boolean getDeletable() {
		return deletable;
	}

	/**
	 * Sets the deletable.
	 *
	 * @param deletable 
	 *			the deletable to set
	 */
	public void setDeletable(Boolean deletable) {
		this.deletable = deletable;
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
	 * Returns the person.
	 *
	 * @return 
	 *		the person to return
	 */
	public Boolean getPerson() {
		return person;
	}

	/**
	 * Sets the person.
	 *
	 * @param person 
	 *			the person to set
	 */
	public void setPerson(Boolean person) {
		this.person = person;
	}

	/**
	 * Returns the contracted.
	 *
	 * @return 
	 *		the contracted to return
	 */
	public Boolean getContracted() {
		return contracted;
	}

	/**
	 * Sets the contracted.
	 *
	 * @param contracted 
	 *			the contracted to set
	 */
	public void setContracted(Boolean contracted) {
		this.contracted = contracted;
	}

	/**
	 * Returns the leader.
	 *
	 * @return 
	 *		the leader to return
	 */
	public Boolean getLeader() {
		return leader;
	}

	/**
	 * Sets the leader.
	 *
	 * @param leader 
	 *			the leader to set
	 */
	public void setLeader(Boolean leader) {
		this.leader = leader;
	}

	/**
	 * Returns the other1.
	 *
	 * @return 
	 *		the other1 to return
	 */
	public String getOther1() {
		return other1;
	}

	/**
	 * Sets the other1.
	 *
	 * @param other1 
	 *			the other1 to set
	 */
	public void setOther1(String other1) {
		this.other1 = other1;
	}

	/**
	 * Returns the other2.
	 *
	 * @return 
	 *		the other2 to return
	 */
	public String getOther2() {
		return other2;
	}

	/**
	 * Sets the other2.
	 *
	 * @param other2 
	 *			the other2 to set
	 */
	public void setOther2(String other2) {
		this.other2 = other2;
	}

	/**
	 * Returns the other3.
	 *
	 * @return 
	 *		the other3 to return
	 */
	public String getOther3() {
		return other3;
	}

	/**
	 * Sets the other3.
	 *
	 * @param other3 
	 *			the other3 to set
	 */
	public void setOther3(String other3) {
		this.other3 = other3;
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
	 * Returns the lastName.
	 *
	 * @return 
	 *		the lastName to return
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * Sets the lastName.
	 *
	 * @param lastName 
	 *			the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	/**
	 * Returns the firstName.
	 *
	 * @return 
	 *		the firstName to return
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * Sets the firstName.
	 *
	 * @param firstName 
	 *			the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * Returns the resourceStatus.
	 *
	 * @return 
	 *		the resourceStatus to return
	 */
	public String getResourceStatus() {
		return resourceStatus;
	}

	/**
	 * Sets the resourceStatus.
	 *
	 * @param resourceStatus 
	 *			the resourceStatus to set
	 */
	public void setResourceStatus(String resourceStatus) {
		this.resourceStatus = resourceStatus;
	}

	/**
	 * Returns the numberOfPersonnel.
	 *
	 * @return 
	 *		the numberOfPersonnel to return
	 */
	public Long getNumberOfPersonnel() {
		return numberOfPersonnel;
	}

	/**
	 * Sets the numberOfPersonnel.
	 *
	 * @param numberOfPersonnel 
	 *			the numberOfPersonnel to set
	 */
	public void setNumberOfPersonnel(Long numberOfPersonnel) {
		this.numberOfPersonnel = numberOfPersonnel;
	}

	/**
	 * Returns the leaderName.
	 *
	 * @return 
	 *		the leaderName to return
	 */
	public String getLeaderName() {
		return leaderName;
	}

	/**
	 * Sets the leaderName.
	 *
	 * @param leaderName 
	 *			the leaderName to set
	 */
	public void setLeaderName(String leaderName) {
		this.leaderName = leaderName;
	}

	/**
	 * Returns the resourceAgencyName.
	 *
	 * @return 
	 *		the resourceAgencyName to return
	 */
	public String getResourceAgencyName() {
		return resourceAgencyName;
	}

	/**
	 * Sets the resourceAgencyName.
	 *
	 * @param resourceAgencyName 
	 *			the resourceAgencyName to set
	 */
	public void setResourceAgencyName(String resourceAgencyName) {
		this.resourceAgencyName = resourceAgencyName;
	}

	/**
	 * Returns the resourceOrganizationName.
	 *
	 * @return 
	 *		the resourceOrganizationName to return
	 */
	public String getResourceOrganizationName() {
		return resourceOrganizationName;
	}

	/**
	 * Sets the resourceOrganizationName.
	 *
	 * @param resourceOrganizationName 
	 *			the resourceOrganizationName to set
	 */
	public void setResourceOrganizationName(String resourceOrganizationName) {
		this.resourceOrganizationName = resourceOrganizationName;
	}

	/**
	 * Returns the workAreaResourceId.
	 *
	 * @return 
	 *		the workAreaResourceId to return
	 */
	public Long getWorkAreaResourceId() {
		return workAreaResourceId;
	}

	/**
	 * Sets the workAreaResourceId.
	 *
	 * @param workAreaResourceId 
	 *			the workAreaResourceId to set
	 */
	public void setWorkAreaResourceId(Long workAreaResourceId) {
		this.workAreaResourceId = workAreaResourceId;
	}

//	/**
//	 * Returns the unitCode.
//	 *
//	 * @return 
//	 *		the unitCode to return
//	 */
//	public String getUnitCode() {
//		return unitCode;
//	}
//
//	/**
//	 * Sets the unitCode.
//	 *
//	 * @param unitCode 
//	 *			the unitCode to set
//	 */
//	public void setUnitCode(String unitCode) {
//		this.unitCode = unitCode;
//	}
	
	
	/**
	 * Returns the resourceUnitCode.
	 *
	 * @return 
	 *		the resourceUnitCode to return
	 */
	public String getResourceUnitCode() {
		return resourceUnitCode;
	}

	/**
	 * Sets the resourceUnitCode.
	 *
	 * @param resourceUnitCode 
	 *			the resourceUnitCode to set
	 */
	public void setResourceUnitCode(String resourceUnitCode) {
		this.resourceUnitCode = resourceUnitCode;
	}

	/**
	 * Returns the agencyCode.
	 *
	 * @return 
	 *		the agencyCode to return
	 */
	public String getAgencyCode() {
		return agencyCode;
	}

	/**
	 * Sets the agencyCode.
	 *
	 * @param agencyCode 
	 *			the agencyCode to set
	 */
	public void setAgencyCode(String agencyCode) {
		this.agencyCode = agencyCode;
	}

	/**
	 * Returns the incidentName.
	 *
	 * @return 
	 *		the incidentName to return
	 */
	public String getIncidentName() {
		return incidentName;
	}

	/**
	 * Sets the incidentName.
	 *
	 * @param incidentName 
	 *			the incidentName to set
	 */
	public void setIncidentName(String incidentName) {
		this.incidentName = incidentName;
	}
	
	/**
	 * Returns the assignmentStatus.
	 *
	 * @return 
	 *		the assignmentStatus to return
	 */
	public String getAssignmentStatus() {
		return assignmentStatus;
	}

	/**
	 * Sets the assignmentStatus.
	 *
	 * @param assignmentStatus 
	 *			the assignmentStatus to set
	 */
	public void setAssignmentStatus(String assignmentStatus) {
		this.assignmentStatus = assignmentStatus;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#getKindDescription()
	 */
	public String getKindDescription() {
		return kindDescription;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#setKindDescription(java.lang.String)
	 */
	public void setKindDescription(String kindDescription) {
		this.kindDescription = kindDescription;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#getActiveStatus()
	 */
	public String getActiveStatus() {
		return activeStatus;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#setActiveStatus(java.lang.String)
	 */
	public void setActiveStatus(String activeStatus) {
		this.activeStatus = activeStatus;
	}
	
	
	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(WorkAreaResourceFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		// TYPE_EQUALS
		criteria.add( null != filter.getWorkAreaId() && filter.getWorkAreaId() > 0L ? new FilterCriteria("this.workAreaId",filter.getWorkAreaId(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getWorkAreaResourceId() && filter.getWorkAreaResourceId() > 0L ? new FilterCriteria("this.resourceId",filter.getWorkAreaResourceId(),FilterCriteria.TYPE_EQUAL) : null);
		
		if (filter.getActiveStatus() != null) {
			if (!filter.getActiveStatus().equalsIgnoreCase("BOTH")) {
				if (filter.getActiveStatus().equalsIgnoreCase("ENABLED")) {
					criteria.add( new FilterCriteria("this.enabled",Boolean.TRUE,FilterCriteria.TYPE_EQUAL));
				} else {
					criteria.add( new FilterCriteria("this.enabled",Boolean.FALSE,FilterCriteria.TYPE_EQUAL));
				}
			}
		}
		
		
		
		
		// TYPE_NOT_EQUAL


		// TYPE_IN_STRING
		
		// TYPE_ILIKE
		criteria.add( null != filter.getResourceName() && !filter.getResourceName().isEmpty() ? new FilterCriteria("this.resourceName",filter.getResourceName(),"this.lastName", filter.getResourceName(), FilterCriteria.TYPE_OR) : null);
		criteria.add( null != filter.getLastName() && !filter.getLastName().isEmpty() ? new FilterCriteria("this.lastName",filter.getLastName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getFirstName() && !filter.getOther3().isEmpty() ? new FilterCriteria("this.firstName",filter.getFirstName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getResourceStatus() && !filter.getResourceStatus().isEmpty() ? new FilterCriteria("this.resourceStatus",filter.getResourceStatus(),FilterCriteria.TYPE_ILIKE) : null);
		
		criteria.add( null != filter.getKindDescription() && !filter.getKindDescription().isEmpty() ? new FilterCriteria("this.kindDescription",filter.getKindDescription(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getAgencyCode() && !filter.getAgencyCode().isEmpty() ? new FilterCriteria("this.resourceAgencyCode",filter.getAgencyCode(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getResourceUnitCode() && !filter.getResourceUnitCode().isEmpty() ? new FilterCriteria("this.resourceUnitCode",filter.getResourceUnitCode().toUpperCase(),FilterCriteria.TYPE_ILIKE) : null);
//		criteria.add( null != filter.getUnitCode() && !filter.getUnitCode().isEmpty() ? new FilterCriteria("this.resourceUnitCode",filter.getUnitCode(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getIncidentName() && !filter.getIncidentName().isEmpty() ? new FilterCriteria("this.incidentName",filter.getIncidentName(),FilterCriteria.TYPE_ILIKE) : null);

		// TYPE_ISNULL
		//filter out deleted resources (filtered out at the view level)		
		//criteria.add( new FilterCriteria("this.deletedDate", null, FilterCriteria.TYPE_ISNULL));
		
		// filter by deletable true/false
		//TODO: Add additional criteria to determine if resource is deletable such as:
		// time postings, issued supplies, financial exports, invoices, and injury/illness recordings
		if (null != filter.getDeletable()) {
			if(filter.getDeletable()) {
				criteria.add( new FilterCriteria("this.incidentName",null,FilterCriteria.TYPE_ISNULL));
			}
			else {
				criteria.add( new FilterCriteria("this.incidentName",null,FilterCriteria.TYPE_ISNOTNULL));
			}			
		}
		

		
		return criteria;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#getCrypticDateFilterCode()
	 */
	@Override
	public String getCrypticDateFilterCode() {
		return this.crypticDateFilterCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#setCrypticDateFilterCode(java.lang.String)
	 */
	@Override
	public void setCrypticDateFilterCode(String crypticDateFilterCode) {
		this.crypticDateFilterCode = crypticDateFilterCode;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#getMobilizationStartDate()
	 */
	public Date getMobilizationStartDate() {
		return mobilizationStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#setMobilizationStartDate(java.util.Date)
	 */
	public void setMobilizationStartDate(Date mobilizationStartDate) {
		this.mobilizationStartDate = mobilizationStartDate;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#getRequestNumber()
	 */
	public String getRequestNumber() {
		return requestNumber;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.WorkAreaResourceFilter#setRequestNumber(java.lang.String)
	 */
	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}

	public Collection<Integer> getExcludedResourceIds() {
		return excludedResourceIds;
	}

	public void setExcludedResourceIds(Collection<Integer> excludedResourceIds) {
		this.excludedResourceIds = excludedResourceIds;
	}
}