/**
 * 
 */
package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.core.domain.User;
import gov.nwcg.isuite.core.service.IsuiteAuthorityService;
import gov.nwcg.isuite.core.service.UserService;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.exceptions.NoSuchItemException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.security.Authorizer;

import java.util.Set;

import org.apache.log4j.Logger;
import org.springframework.security.GrantedAuthority;

/**
 * Abstract implementation of an Authorizer.
 * <p>
 * This class follows the <i>Template Method</i> pattern.
 * </p>
 * 
 * @author doug
 * 
 */
public abstract class AbstractAuthorizer implements Authorizer {
   private final UserService userService;
   private static final Logger LOG = Logger.getLogger(AbstractAuthorizer.class);
   private final IsuiteAuthorityService isuiteAuthorityService;
   
   public AbstractAuthorizer(UserService userService, IsuiteAuthorityService isuiteAuthorityService) {
      if ( userService == null ) {
         throw new IllegalArgumentException("userService can not be null");
      }
      if ( isuiteAuthorityService == null ) {
         throw new IllegalArgumentException("isuiteAuthorityService can not be null");
      }
      this.userService = userService;
      this.isuiteAuthorityService = isuiteAuthorityService;
   }

//   /*
//    * (non-Javadoc)
//    * 
//    * @see gov.nwcg.isuite.service.access.Authorizer#authorize(gov.nwcg.isuite.domain.access.UserVo)
//    */
//   public Set<GrantedAuthority> authorize(UserVo userVo) throws NoSuchItemException,ServiceException {
//      if ( userVo == null ) {
//         throw new IllegalArgumentException("userVo can not be null");
//      }
//      UserVo userVoFromDB = userService.getByLoginName(userVo.getLoginNm());
//      User user = userService.voToDo(userVoFromDB);
//      LOG.debug("got user from the ldap (enterprise) or our db (site), about to synchronize.");
//      synchronizeUserData(userVo, user);
//      LOG.debug("just synchronized, about to update the login state of the user.");
//      Set<GrantedAuthority> authorities = updateLoginState(user, userVo);
//      if (user != null) {
//         for (Role role : user.getRoles()) {
//            authorities.add(role);
//         }
//      }
//      return authorities;
//   }

   /**
    * Synchronize data between the user and the UserVo.
    * <p>
    * This method may change both the user and the userVo objects.
    * </p>
    * 
    * @param userVo
    *           source of data transfer, can't be null
    * @param user
    *           destination of data transfer, can't be null
    */
   abstract protected void synchronizeUserData(UserVo userVo, User user) 
      throws NoSuchItemException, ServiceException;

   /**
    * Update login state of user.
    * <p>
    * Based on the data in the user and userVo, update the login state of the
    * userVo. This method may also update the user object based on the updated
    * state of the userVo.
    * <p>
    * 
    * @param user
    *           user, can't be null
    * @param userVo
    *           user value object, can't be null
    * @return list of granted authorities based on the userVo login state, can
    *         be empty, won't be null
    */
   abstract protected Set<GrantedAuthority> updateLoginState(User user, UserVo userVo) 
      throws NoSuchItemException, ServiceException;
   
   public IsuiteAuthorityService getIsuiteAuthorityService() {
      return isuiteAuthorityService;
   }
   
   public UserService getUserService() {
      return userService;
   }
}
