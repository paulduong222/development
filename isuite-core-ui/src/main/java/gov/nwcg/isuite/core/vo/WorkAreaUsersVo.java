package gov.nwcg.isuite.core.vo;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Retrieves information for the Manage Shared Users tied to a Work Area.
 * 
 * @author bsteiner
 */
public class WorkAreaUsersVo extends WorkAreaPicklistVo {
   
   private static final long serialVersionUID = -183806545061921042L;
   private Collection<UserGridVo> waUsers;
   private List<String> allRoles;
   
   /**
    * @return the allRoles
    */
   public List<String> getAllRoles() {
      return allRoles;
   }
   
   /**
    * @param allRoles the allRoles to set
    */
   public void setAllRoles(Collection<String> allRoles) {
      if (allRoles != null) {
         this.allRoles = new ArrayList<String>(allRoles);
      }
   }
   
   /**
    * @return the waUsers
    */
   public Collection<UserGridVo> getWaUsers() {
      return waUsers;
   }
   
   /**
    * @param waUsers the waUsers to set
    */
   public void setWaUsers(Collection<UserGridVo> waUsers) {
      this.waUsers = waUsers;
   }

   /**
     * Constructs a <code>String</code> with all attributes in xml format.
     *
     * @return a <code>String</code> representation of this object.
     */
    @Override
    public String toString() {
        final String nl = System.getProperty("line.separator");
        
        StringBuffer retValue = new StringBuffer();
        String tab = "\t";
        
        retValue.append("<WorkAreaUsersVo>").append(nl)
    	    .append(tab).append(super.toString()).append(nl)
           .append(tab).append("<waUsers>").append(this.waUsers).append("</waUsers>").append(nl)   
           .append(tab).append("<allRoles>").append(this.allRoles).append("</allRoles>").append(nl)   
           .append("</WorkAreaUsersVo>");
            
        return retValue.toString();
    }
  
}
