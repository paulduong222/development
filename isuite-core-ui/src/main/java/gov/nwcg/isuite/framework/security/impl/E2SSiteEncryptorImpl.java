package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.framework.exceptions.DomainException;

/**
 * @author mpoll
 *
 *	Enterprise To Site Site Side Encryptor (handles enterprise to site decryption only)
 */
public class E2SSiteEncryptorImpl extends PublicPrivateKeyEncryptorImpl {

	public E2SSiteEncryptorImpl(String keystoreFileFilePath,
			String keystoreAlias, String keystorePassword) throws DomainException {
		super(null, keystoreFileFilePath, keystoreAlias,
				keystorePassword);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.security.Encryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String data) throws DomainException {		
		// This method should never be called because this class only handles decrypting
		// information that has been sent from the enterprise.  A different Encryptor class will
		// be used to encrypt and send information back to the enterprise.
		throw new UnsupportedOperationException();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.security.Encryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String data) throws DomainException {
		return super.decrypt(data);
	}

}
