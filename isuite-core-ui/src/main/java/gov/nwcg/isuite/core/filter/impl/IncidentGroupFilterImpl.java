/**
 * 
 */
package gov.nwcg.isuite.core.filter.impl;

import java.util.Date;

import gov.nwcg.isuite.core.filter.IncidentGroupFilter;
import gov.nwcg.isuite.framework.core.filter.impl.FilterImpl;

/**
 * @author bsteiner
 * @author aroundy
 *
 */
@SuppressWarnings("serial")
public class IncidentGroupFilterImpl extends FilterImpl implements IncidentGroupFilter {

   private String groupName;
   private String createdDateString;
   private Boolean deletable;
   private String deletableString;
   private Boolean isPrivilegedUser;
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#getCreatedDate()
    */
   public Date getCreatedDate() {
//     if(createdDateString != null) {
//       return super.crypticDateConverter(createdDateString);
//     }
     return null;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#getCreatedDateString()
    */
   public String getCreatedDateString() {
      return this.createdDateString;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#setCreatedDate(java.util.Date)
    */
   public void setCreatedDateString(String date) {
      this.createdDateString = date;
   }
   
   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#getGroupName()
    */
   public String getGroupName() {
      return this.groupName;
   }

   /* (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#setGroupName(java.lang.String)
    */
   public void setGroupName(String groupName) {
      this.groupName = groupName;
   }
   
   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#getDeletable()
    */
   public Boolean getDeletable() {
      return deletable;
   }

   /*
    * (non-Javadoc)
    * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#setDeletable(java.lang.Boolean)
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

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#getIsPrivilegedUser()
   */
  public Boolean getIsPrivilegedUser() {
    return isPrivilegedUser;
  }

  /*
   * (non-Javadoc)
   * @see gov.nwcg.isuite.core.filter.IncidentGroupFilter#setIsPrivilegedUser(java.lang.Boolean)
   */
  public void setIsPrivilegedUser(Boolean isPrivilegedUser) {
    this.isPrivilegedUser = isPrivilegedUser;
  }
  

   
}
