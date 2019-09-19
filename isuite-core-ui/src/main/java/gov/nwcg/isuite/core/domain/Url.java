package gov.nwcg.isuite.core.domain;

import gov.nwcg.isuite.framework.core.domain.Persistable;

import java.util.Collection;

import org.springframework.security.GrantedAuthority;

/**
 * 
 * Represents a url in the system.
 * 
 * @author Karen Velasquez
 *
 */

public interface Url extends Persistable {
	
	/**
	 * The path.
	 * 
	 * @return path, will not be null
	 */
	public String getPath();
	
	/**
	 * Collection of authorities assigned to a path.
	 * 
	 * @return collection of <code>GrantedAuthority</code> objects assigned to a path, 
    * can be empty, but will not be null
	 */
	public Collection<GrantedAuthority> getAuthorityNames();

}
