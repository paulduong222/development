package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;

/**
 * @author bsteiner
 *
 */
public class UserGroupPicklistVo extends AbstractVo {
   private String groupName;

   /**
    * @return the groupName
    */
   public String getGroupName() {
      return groupName;
   }

   /**
    * @param groupName the groupName to set
    */
   public void setGroupName(String groupName) {
      this.groupName = groupName;
   } 
}
