package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.framework.exceptions.DomainException;

public class S2ESiteEncryptorImpl extends PublicPrivateKeyEncryptorImpl {

	/**
	 * @author mpoll
	 *
	 *	Site To Enterprise Enterprise Side Encryptor (handles site to enterprise encryption only)
	 */
	public S2ESiteEncryptorImpl(String publicKeyFilePath) throws DomainException {
		super(publicKeyFilePath, null, null,
				null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.security.Encryptor#encrypt(java.lang.String)
	 */
	public String encrypt(String data) throws DomainException {
		return super.encrypt(data);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.security.Encryptor#decrypt(java.lang.String)
	 */
	public String decrypt(String data) throws DomainException {
		// This method should never be called because this class only handles encrypting
		// information that is being sent to the enterprise.  A different Decryptor class will
		// be used to decrypt information from the enterprise.
		throw new UnsupportedOperationException();
	}

}
