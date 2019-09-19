/**
 * 
 */
package gov.nwcg.isuite.framework.security;

import gov.nwcg.isuite.core.service.IsuiteAuthorityService;
import gov.nwcg.isuite.core.service.UserService;
import gov.nwcg.isuite.core.vo.UserVo;
import gov.nwcg.isuite.framework.exceptions.NoSuchItemException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Set;

import org.springframework.security.GrantedAuthority;

/**
 * Authorizes a user.
 * @author doug
 *
 */
public interface Authorizer {

   public Set<GrantedAuthority> authorize (UserVo userVo) throws NoSuchItemException,ServiceException;
   
   public IsuiteAuthorityService getIsuiteAuthorityService();
   
   public UserService getUserService();
}
