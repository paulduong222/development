package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.UserOrganizationFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.core.persistence.hibernate.FilterCriteria;

import java.util.ArrayList;
import java.util.Collection;

public class UserOrganizationFilterImpl extends FilterImpl implements UserOrganizationFilter {
   //TODO:  This class is no longer needed because the UserOrgs will be filtered on the client.  Use OrganizationFilterImpl instead. -dbudge
   private static final long serialVersionUID = 8174407791791142230L;

   private Long dispatchCenterId;
   private String dispatchCenterCode;
   private String dispatchCenterName;
   private Long userId;
   private Boolean dispatchCenterOnly;
   
   public UserOrganizationFilterImpl() {
      super();
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IUserOrganizationFilter#getDispatchCenterCode()
    */
   public String getDispatchCenterCode() {
      return dispatchCenterCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IUserOrganizationFilter#setDispatchCenterCode(java.lang.String)
    */
   public void setDispatchCenterCode(String dispatchCenterCode) {
      this.dispatchCenterCode = dispatchCenterCode;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IUserOrganizationFilter#getDispatchCenterName()
    */
   public String getDispatchCenterName() {
      return dispatchCenterName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IUserOrganizationFilter#setDispatchCenterName(java.lang.String)
    */
   public void setDispatchCenterName(String dispatchCenterName) {
      this.dispatchCenterName = dispatchCenterName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IUserOrganizationFilter#getUserId()
    */
   public Long getUserId() {
      return userId;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IUserOrganizationFilter#setUserId(java.lang.Long)
    */
   public void setUserId(Long userId) {
      this.userId = userId;
   }

   /**
    * @return the dispatchCenterId
    */
   public Long getDispatchCenterId() {
      return dispatchCenterId;
   }
   
   /**
    * @param dispatchCenterId the dispatchCenterId to set
    */
   public void setDispatchCenterId(Long dispatchCenterId) {
      this.dispatchCenterId = dispatchCenterId;
   }

	/**
	 * Returns the dispatchCenterOnly.
	 *
	 * @return 
	 *		the dispatchCenterOnly to return
	 */
	public Boolean getDispatchCenterOnly() {
		return dispatchCenterOnly;
	}

	/**
	 * Sets the dispatchCenterOnly.
	 *
	 * @param dispatchCenterOnly 
	 *			the dispatchCenterOnly to set
	 */
	public void setDispatchCenterOnly(Boolean dispatchCenterOnly) {
		this.dispatchCenterOnly = dispatchCenterOnly;
	}

	/**
	 * @param filter
	 * @return
	 * @throws Exception
	 */
	public static Collection<FilterCriteria> getFilterCriteria(UserOrganizationFilter filter) throws Exception {
		Collection<FilterCriteria> criteria = new ArrayList<FilterCriteria>();

		// TYPE_EQUALS
		criteria.add( null != filter.getUserId() && filter.getUserId() > 0L ? new FilterCriteria("this.id",filter.getUserId(),FilterCriteria.TYPE_EQUAL) : null);
		if(filter.getDispatchCenterOnly())
			criteria.add( new FilterCriteria("org.dispatchCenter",Boolean.TRUE,FilterCriteria.TYPE_EQUAL));
		
		// TYPE_NOT_EQUAL
		
		// TYPE_IN_STRING
		
		// TYPE_ILIKE
		criteria.add( null != filter.getDispatchCenterCode() && !filter.getDispatchCenterCode().isEmpty() ? new FilterCriteria("org.unitCode",filter.getDispatchCenterCode(),FilterCriteria.TYPE_ILIKE) : null);
		criteria.add( null != filter.getDispatchCenterName() && !filter.getDispatchCenterName().isEmpty() ? new FilterCriteria("org.name",filter.getDispatchCenterName(),FilterCriteria.TYPE_ILIKE) : null);
		
		// TYPE_ISNULL
		
		return criteria;
	}

}
