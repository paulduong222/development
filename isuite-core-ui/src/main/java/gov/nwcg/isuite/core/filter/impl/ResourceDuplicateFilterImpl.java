package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.ResourceDuplicateFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;

public class ResourceDuplicateFilterImpl extends FilterImpl implements ResourceDuplicateFilter {
	
	private static final long serialVersionUID = -1182931524274725258L;
	
	private String resourceName;
	private String firstName;
	private String lastName;
    private Boolean person;
    private Boolean enabled;
    private Long organizationId=0L;
    private Long primaryDispatchCenterId=0L;
    public Long resourceId=0L;
    private Boolean workAreaOnly;
    
    public ResourceDuplicateFilterImpl(){
    	
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
	 * Returns the enabled.
	 *
	 * @return 
	 *		the enabled to return
	 */
	public Boolean getEnabled() {
		return enabled;
	}


	/**
	 * Sets the enabled.
	 *
	 * @param enabled 
	 *			the enabled to set
	 */
	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}


	/**
	 * Returns the organizationId.
	 *
	 * @return 
	 *		the organizationId to return
	 */
	public Long getOrganizationId() {
		return organizationId;
	}


	/**
	 * Sets the organizationId.
	 *
	 * @param organizationId 
	 *			the organizationId to set
	 */
	public void setOrganizationId(Long organizationId) {
		this.organizationId = organizationId;
	}
	
	
	/**
	 * Returns the PrimaryDispatchCenterId.
	 *
	 * @return 
	 *		the PrimaryDispatchCenterId to return
	 */
	public Long getPrimaryDispatchCenterId() {
		return primaryDispatchCenterId;
	}

	/**
	 * Sets the PrimaryDispatchCenterId.
	 *
	 * @param PrimaryDispatchCenterId 
	 *			the PrimaryDispatchCenterId to set
	 */
	public void setPrimaryDispatchCenterId(Long primaryDispatchCenterId) {
		this.primaryDispatchCenterId = primaryDispatchCenterId;
	}
	
	/**
	 * Returns the ResourceId
	 */
	public Long getResourceId() {
		return resourceId;
	}
	
	/**
	 * Sets the ResourceId
	 */
	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}
	
	

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(ResourceDuplicateFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		// TYPE_EQUALS
		criteria.add( null != filter.getEnabled() && filter.getEnabled() ? new FilterCriteria("this.enabled",filter.getEnabled(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getOrganizationId() && !(filter.getOrganizationId()==0) ? new FilterCriteria("this.organizationId",filter.getOrganizationId(),FilterCriteria.TYPE_EQUAL) : null);
		criteria.add( null != filter.getPrimaryDispatchCenterId() && !(filter.getPrimaryDispatchCenterId()==0) ? new FilterCriteria("this.primaryDispatchCenterId",filter.getPrimaryDispatchCenterId(),FilterCriteria.TYPE_EQUAL) : null);
		
		// TYPE_ILIKE
		criteria.add( null != filter.getLastName() && !filter.getLastName().isEmpty()  ? new FilterCriteria("this.lastName",filter.getLastName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getFirstName() && !filter.getFirstName().isEmpty()  ? new FilterCriteria("this.firstName",filter.getFirstName(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getResourceName() && !filter.getResourceName().isEmpty()  ? new FilterCriteria("this.resourceName",filter.getResourceName(),FilterCriteria.TYPE_ILIKE) : null);

		// TYPE_ISNULL

		// TYPE_ISNOTNULL
		
		return criteria;
	}

	public Boolean getWorkAreaOnly() {
		return workAreaOnly;
	}

	public void setWorkAreaOnly(Boolean workAreaOnly) {
		this.workAreaOnly = workAreaOnly;
	}	
}