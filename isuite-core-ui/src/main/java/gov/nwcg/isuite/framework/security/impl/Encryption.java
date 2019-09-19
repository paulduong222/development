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
public class Encryption {
	
	private static Encryptor encryptor;

	public Encryption(Encryptor encryptor) {
	   if (null == encryptor) {
	      throw new IllegalArgumentException("null cannot encrypt anything.");
	   }
		Encryption.encryptor = encryptor;
	}
	
	public static String encrypt(String data) {
		String encryptedString = null;
		try {
			encryptedString = encryptor.encrypt(data);
			///CLOVER:OFF
		} catch (DomainException e) {
			e.printStackTrace();
		}
		///CLOVER:ON
		return encryptedString;
	}
	
	public static String decrypt(String data) {
		String decryptedString = "";
		try {
			decryptedString = encryptor.decrypt(data);
			///CLOVER:OFF
		} catch (DomainException e) {
			e.printStackTrace();
		}
		///CLOVER:ON
		return decryptedString;
	}
}
