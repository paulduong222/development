package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.framework.exceptions.DomainException;

/**
 * @author mpoll
 *
 *	Site To Enterprise Site Side Encryptor (handles site to enterprise decryption only)
 */
public class S2EEnterpriseEncryptorImpl extends PublicPrivateKeyEncryptorImpl {

	public S2EEnterpriseEncryptorImpl(String keystoreFileFilePath,
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
		// information that has been sent from the site.  A different Encryptor class will
		// be used to encrypt and send information back to the site.
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
