package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;


/**
 * Account Code relational mapping to the database.
 * @author mpoll
 *
 */
public interface IncidentAccountCode extends Persistable{
   
   /**
    * Return a single {@link AccountCode} object from this {@link IncidentAccountCode}
    * @return - A single {@link AccountCode} object
    */
   public AccountCode getAccountCode();
   
   /**
    * Sets the {@link AccountCode} for this {@link IncidentAccountCode}
    * @param accountCode
    */
   public void setAccountCode(AccountCode accountCode);
   
   /**
    * Return a single {@link AccountCode} ID for this {@link IncidentAccountCode}
    * @return - A single {@link AccountCode} ID 
    */
   public Long getAccountCodeId();
   
   /**
    * Set the {@link AccountCode} ID for this {@link IncidentAccountCode}
    * @param accountCodeId
    */
   public void setAccountCodeId(Long accountCodeId);
   
   /**
    * Return a single {@link Incident} object from this {@link IncidentAccountCode}
    * @return - A single {@link Incident} object
    */
   public Incident getIncident();
   
   /**
    * Set the {@link Incident} for this {@link IncidentAccountCode}
    * @param incident
    */
   public void setIncident(Incident incident);
   
   /**
    * Get the {@link Incident} ID for this {@link IncidentAccountCode}
    * @return - the {@link Incident} ID for this {@link IncidentAccountCode}
    */
   public Long getIncidentId();
   
   /**
    * Set the {@link Incident} ID for this {@link IncidentAccountCode}
    * @param incidentID
    */
   public void setIncidentId(Long incidentId);
   
   /**
    * Get the default value
    * @return - The default value
    */
   public Boolean getDefaultFlag();
   
   /**
    * Set the default value
    * @param defaultFlag
    */
   public void setDefaultFlag(Boolean defaultFlag);
   
   /**
    * Get the override account code
    * @return - A single {@link AccountCode} object
    */
   public AccountCode getOverrideAccountCode();
   
   /**
    * Set the override account code
    * @param overrideAccountCode
    */
   public void setOverrideAccountCode(AccountCode overrideAccountCode);
   
   /**
    * Get the override account code ID
    * @return - The override account code ID
    */
   public Long getOverrideAccountCodeId();
   
   /**
    * Set the override account code ID
    * @param overrideAccountCodeId
    */
   public void setOverrideAccountCodeId(Long overrideAccountCodeId);

	/**
	 * @return the timeAssignAdjust
	 */
	public Collection<TimeAssignAdjust> getTimeAssignAdjusts();
	
	/**
	 * @param timeAssignAdjust the timeAssignAdjust to set
	 */
	public void setTimeAssignAdjusts(Collection<TimeAssignAdjust> timeAssignAdjusts);
   
	/**
	 * @return the assignmentTimePosts
	 */
	public Collection<AssignmentTimePost> getAssignmentTimePosts();

	/**
	 * @param assignmentTimePosts the assignmentTimePosts to set
	 */
	public void setAssignmentTimePosts(Collection<AssignmentTimePost> assignmentTimePosts);

	/**
	 * @return the resourceOthers
	 */
	public Collection<ResourceOther> getResourceOthers() ;

	/**
	 * @param resourceOthers the resourceOthers to set
	 */
	public void setResourceOthers(
			Collection<ResourceOther> resourceOthers) ;

	public void setAccrualAccountCode(String code);
	
	public String getAccrualAccountCode();
}
