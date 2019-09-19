package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface UserOrganizationFilter extends Filter {

   /**
    * @return the dispatchCenterCode
    */
   public String getDispatchCenterCode();

   /**
    * @param dispatchCenterCode the dispatchCenterCode to set
    */
   public void setDispatchCenterCode(String dispatchCenterCode);

   /**
    * @return the dispatchCenterName
    */
   public String getDispatchCenterName();

   /**
    * @param dispatchCenterName the dispatchCenterName to set
    */
   public void setDispatchCenterName(String dispatchCenterName);
   
   /**
    * @return the userId
    */
   public Long getUserId();

   /**
    * @param userId the userId to set
    */
   public void setUserId(Long userId);
   
	/**
	 * Returns the dispatchCenterOnly.
	 *
	 * @return 
	 *		the dispatchCenterOnly to return
	 */
	public Boolean getDispatchCenterOnly() ;
	/**
	 * Sets the dispatchCenterOnly.
	 *
	 * @param dispatchCenterOnly 
	 *			the dispatchCenterOnly to set
	 */
	public void setDispatchCenterOnly(Boolean dispatchCenterOnly);
}
