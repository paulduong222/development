package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.types.IsuiteAuthorityNameEnum;

import org.springframework.security.GrantedAuthority;

/**
 * DAO used to access the Authority table and data.
 * 
 * @author bsteiner
 */
public interface IsuiteAuthorityDao {
   
   /**
    * Retrieve a granted authority by its name.
    * 
    * @param authorityName <code>AuthorityNameEnum</code> selecting which name.
    * @return populated <code>GrantedAuthority</code> object
    */
   public GrantedAuthority getBy(IsuiteAuthorityNameEnum authorityName) throws PersistenceException;
}
