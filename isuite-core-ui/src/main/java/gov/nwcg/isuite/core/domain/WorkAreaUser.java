package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.types.WorkAreaUserTypeEnum;

/**
 * Represents a User that has been associated to a Work Area.
 * 
 * @author bsteiner
 */
public interface WorkAreaUser extends Persistable {

   /**
    * Retrieve the Work Area this association represents.
    * 
    * @return {@link WorkArea} object.
    */
	public WorkArea getWorkArea();
   
   /**
    * Retrieve the id for the Work Area this association represents.
    * 
    * @return id of the work area.
    */
   public Long getWorkAreaId();
   
   /**
    * Retrieve the User that has been granted access to the associated work area.
    * @return the User.
    */
   public User getUser();
   
   /**
    * Retrieve the id of the User that has been granted access to the associated work area.
    * @return id of the user
    */
   public Long getUserId();
   
   /**
    * Set the User who has been granted access to the work area.
    * @param user
    */
   public void setUser(User user);
   
   /**
    * Set the id of the User who has been granted access to the work area.
    * @param userId
    */
   public void setUserId(Long userId);
   
   /**
    * Set the WorkArea.
    * @param user
    */
   public void setWorkArea(WorkArea workArea);
   
   /**
    * Set the id of the WorkArea.
    * @param userId
    */
   public void setWorkAreaId(Long workAreaId);

//	/**
//	 * Returns the workAreaUserRoles.
//	 *
//	 * @return 
//	 *		the workAreaUserRoles to return
//	 */
//	public Collection<SystemRole> getWorkAreaUserRoles();	
//	/**
//	 * Sets the workAreaUserRoles.
//	 *
//	 * @param workAreaUserRoles 
//	 *			the workAreaUserRoles to set
//	 */
//	public void setWorkAreaUserRoles(Collection<SystemRole> workAreaUserRoles) ;

    public WorkAreaUserTypeEnum getUserType();
    
    public void setUserType(WorkAreaUserTypeEnum type);
    
    public void setSharedByUser(User sharedByUser);
    
    public User getSharedByUser();
    
    public void setSharedByUserId(Long sharedByUserId);
    
    public Long getSharedByUserId(); 

}
