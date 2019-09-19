/**
 * 
 */
package gov.nwcg.isuite.framework.security;

import gov.nwcg.isuite.framework.exceptions.DomainException;

/**
 * Strategy for encryption/decription of data.
 * @author doug
 *
 */
public interface Encryptor {

	public String encrypt(String data) throws DomainException;

	public String decrypt(String data) throws DomainException;

}
