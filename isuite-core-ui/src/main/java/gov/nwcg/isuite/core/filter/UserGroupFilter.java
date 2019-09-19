package gov.nwcg.isuite.core.filter;

import gov.nwcg.isuite.framework.core.filter.Filter;

import java.util.Date;

/**
 * @author bsteiner
 *
 */
public interface UserGroupFilter extends Filter {
   
   /**
    * Retrieve the group name
    * @return groupName
    */
   public String getGroupName();
   
   /**
    * Set the group name
    * @param groupName
    */
   public void setGroupName(String groupName);
   
   /**
    * Retrieve the created date
    * @return createdDate
    */
   public Date getCreatedDate();
   
   /**
    * Set the created date
    * @param date
    */
   public void setCreatedDate(Date date);
   
   /**
    * Returns the deletable.
    *
    * @return 
    *		the deletable to return
    */
   public Boolean getDeletable();

   /**
    * Sets the deletable.
    *
    * @param deletable 
    *			the deletable to set
    */
   public void setDeletable(Boolean deletable);
   
   /**
    * 
    * @return
    */
   public String getCrypticDateFilterCode();
   
   /**
    * 
    * @param crypticDateFilterCode
    */
   public void setCrypticDateFilterCode(String crypticDateFilterCode);
}
