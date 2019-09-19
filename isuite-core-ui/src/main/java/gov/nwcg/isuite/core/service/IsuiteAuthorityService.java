package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.framework.core.service.TransactionService;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.IsuiteAuthorityNameEnum;

import org.springframework.security.GrantedAuthority;

/**
 * Service to get access to the Authorities.
 * 
 * @author bsteiner
 */
public interface IsuiteAuthorityService extends TransactionService {
   
   /**
    * Retrieve the appropriate authority based on the provided authority name.
    * @param authorityName a selected <code>AuthorityNameEnum</code>.
    * @return a populated <code>GrantedAuthority</code> object.
    */
   public GrantedAuthority getBy(IsuiteAuthorityNameEnum authorityName) throws ServiceException;
}
