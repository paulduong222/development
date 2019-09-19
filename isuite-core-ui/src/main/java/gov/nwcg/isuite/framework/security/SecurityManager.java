package gov.nwcg.isuite.framework.security;

import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.other.UserSession;
import gov.nwcg.isuite.framework.types.SecurityGroupEnum;

/**
 * This interface is the gateway to security matters.
 * <p>
 * It provides a way of checking to see whether or not sections of a page are
 * viewable by the current user (based on their roles/authorities).
 * </p>
 * <p>
 * It also provides access to manage (set/get) roles/authorities of the current
 * user.
 * </p>
 * 
 * @author ncollette
 * @uml.annotations
 * 
 * refine_abstraction="platform:/resource/isuite-architecture/isuite.emx#_spkucHXGEdyPROtMTgAZmA"
 * refine_abstraction="platform:/resource/isuite-architecture/isuite.emx#_DbqboHXGEdyPROtMTgAZmA"
 * 
 */
public interface SecurityManager {

   public boolean isViewable(SecurityGroupEnum key);

   public boolean isEditable(SecurityGroupEnum key);

   /**
    * Login the user represented by the name and password.
    * 
    * @param name
    * @param password
    * @return the Logged in User
    * @throws ServiceException
    */
   public UserVo login(String name, String password) throws ServiceException;
   
   /**
    * Indicate if the current user has accepted the security agreement
    * 
    * @param accept
    *           true if agreement is accepted, false otherwise.
    */
   public void acceptSecurityAgreement(boolean accept) throws ServiceException;
   
   //public GrantedAuthority[] getGrantedAuthorities();
   
   /**
    * This method returns the UserSession class that will contain all the 
    * information about the user who is currently logged into the system.
    */
   public UserSession getDetails();
}
