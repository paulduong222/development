package gov.nwcg.isuite.framework.security.impl;

import gov.nwcg.isuite.framework.exceptions.DomainException;

public class E2SEnterpriseEncryptorImpl extends PublicPrivateKeyEncryptorImpl {

	/**
	 * @author mpoll
	 *
	 *	Enterprise To Site Enterprise Side Encryptor (handles enterprise to site encryption only)
	 */
	public E2SEnterpriseEncryptorImpl(String publicKeyFilePath) throws DomainException {
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
		// information that is being sent to the site.  A different Decryptor class will
		// be used to decrypt information from the site.
		throw new UnsupportedOperationException();
	}

}
