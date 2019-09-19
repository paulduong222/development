/**
 * 
 */
package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.framework.exceptions.DomainException;
import gov.nwcg.isuite.framework.security.Encryptor;

/**
 * @author mpoll
 *
 */
public class ReverseEncryptorImpl implements Encryptor {

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.security.Encryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String data) throws DomainException {
		return reverseString(data);
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.security.Encryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String data) throws DomainException {
		return reverseString(data);
	}

	static String reverseString(String source) {
		int i, len = source.length();
		StringBuffer dest = new StringBuffer(len);

		for (i = (len - 1); i >= 0; i--)
			dest.append(source.charAt(i));
		return dest.toString();
	}
}
