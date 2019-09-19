/**
 * 
 */
package gov.nwcg.isuite.framework.security;

import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

/**
 * Authenticates a user.
 * @author doug
 *
 */
public interface Authenticator {
   

   /**
    * Performs authentication based on the name and password.
    * @param name name of user, can not be null
    * @param password clear text password of user, can not be null
    * @return UserVo representing the user.  Will not be null
    * @throws ServiceException
    */
   public UserVo authenticate(String name, String password) throws ServiceException;
}
