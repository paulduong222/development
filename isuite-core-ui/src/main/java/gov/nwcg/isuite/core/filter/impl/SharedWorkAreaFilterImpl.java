package gov.nwcg.isuite.core.filter.impl;

import gov.nwcg.isuite.core.filter.SharedWorkAreaFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;
import gov.nwcg.isuite.framework.util.IntegerUtility;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Filter for Manage Shared Work Area Page
 * @author dbudge
 */
public class SharedWorkAreaFilterImpl extends FilterImpl implements SharedWorkAreaFilter {

   private static final long serialVersionUID = 2209423597989510810L;

   private String userName;
   private String firstName;
   private String lastName;
   private String primaryDispatchCenter;
   private String primaryOrganization;
   private Boolean sharedUsers = false;
   private Collection<Integer> roleVoIds;
   
   public SharedWorkAreaFilterImpl() {}
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#getFirstName()
    */
   public String getFirstName() {
      return firstName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#getLastName()
    */
   public String getLastName() {
     return lastName;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#getPrimaryDispatchCenter()
    */
   public String getPrimaryDispatchCenter() {
      return primaryDispatchCenter;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#getPrimaryOrganization()
    */
   public String getPrimaryOrganization() {
      return primaryOrganization;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#getUserName()
    */
   public String getUserName() {
      return userName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#setFirstName(java.lang.String)
    */
   public void setFirstName(String firstName) {
      this.firstName = firstName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#setLastName(java.lang.String)
    */
   public void setLastName(String lastName) {
      this.lastName = lastName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#setPrimaryDispatchCenter(java.lang.String)
    */
   public void setPrimaryDispatchCenter(String primaryDispatchCenter) {
     this.primaryDispatchCenter = primaryDispatchCenter;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#setPrimaryOrganization(java.lang.String)
    */
   public void setPrimaryOrganization(String primaryOrganization) {
      this.primaryOrganization = primaryOrganization;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.domain.access.SharedWorkAreaFilter#setUserName(java.lang.String)
    */
   public void setUserName(String userName) {
     this.userName = userName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.SharedWorkAreaFilter#getRoleVoIds()
    */
   public Collection<Integer> getRoleVoIds() {
      return roleVoIds;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.SharedWorkAreaFilter#setRoleVoIds(java.util.Collection)
    */
   public void setRoleVoIds(Collection<Integer> roleVoIds) {
      this.roleVoIds = roleVoIds;
   }

   public Collection<Long> getRoleVoIdsFromIntegers() throws Exception{
	   Collection<Long> ids = new ArrayList<Long>();
	   
	   if(null != this.roleVoIds && roleVoIds.size() > 0)
		   return IntegerUtility.convertToLongs(roleVoIds);
	   else
		   return ids;
	   /*
		// Use safe string conversion to prevent errors on Linux OS
	   Iterator iter = this.roleVoIds.iterator();
		
		while(iter.hasNext()){
			String s = String.valueOf(iter.next());
			Long l = TypeConverter.convertToLong(s);
			ids.add(l);
		}
		
	   return ids;
	   */
   }

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.SharedWorkAreaFilter#setSharedUsers(java.lang.Boolean)
	 */
	public void setSharedUsers(Boolean sharedUsers) {
		this.sharedUsers = sharedUsers;
	}
	
	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.core.filter.SharedWorkAreaFilter#getSharedUsers()
	 */
	public Boolean getSharedUsers() {
		return sharedUsers;
	}
   
}
