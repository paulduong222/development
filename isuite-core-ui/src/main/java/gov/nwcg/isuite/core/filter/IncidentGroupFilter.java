package gov.nwcg.isuite.core.filter;

import java.util.Date;

import gov.nwcg.isuite.framework.core.filter.Filter;

public interface IncidentGroupFilter extends Filter {
   
   /**
    * Sets the group name.
    * 
    * @param groupName the new group name
    */
   public void setGroupName(String groupName);
   
   /**
    * Gets the group name.
    * 
    * @return the group name
    */
   public String getGroupName();
   
   /**
    * Retrieve the created date
    * @return createdDate
    */
   public Date getCreatedDate();
   
   /**
    * Retrieve the created date string
    * @return createdDate
    */
   public String getCreatedDateString();
   
   
   /**
    * Set the created date
    * @param date
    */
   public void setCreatedDateString(String date);
   
   /**
    * @return the deletable
    */
   public Boolean getDeletable();
   
   /**
    * @param deletable the deletable to set
    */
   public void setDeletable(Boolean deletable);
 
   /**
    * 
    * @param isPrivilegedUser
    */
   public void setIsPrivilegedUser(Boolean isPrivilegedUser);
   
   /**
    * 
    * @return Boolean
    */
   public Boolean getIsPrivilegedUser();
   
   
}
