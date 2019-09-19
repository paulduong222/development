package gov.nwcg.isuite.framework.crypt;

import gov.nwcg.isuite.framework.crypt.impl.IBMFIPSMD5;
import gov.nwcg.isuite.framework.crypt.impl.IBMFIPSTripleDES;

@SuppressWarnings("unchecked")
public class FIPSEncryptorFactory {

	/**
	 * @return
	 * @throws Exception
	 */
	public static FIPSEncryptor getDefault() throws Exception {
		return getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
	}
	
	/**
	 * Returns FIPSEncryptor implementation.
	 * 
	 * @param type
	 * @return
	 * @throws Exception
	 */
	public static FIPSEncryptor getInstance(FIPSEncryptorType type) throws Exception {
		FIPSEncryptor encryptor = null;
		try{
			if(type==FIPSEncryptorType.IBMFIPSTripleDES)
				encryptor = new IBMFIPSTripleDES();
			else if(type==FIPSEncryptorType.IBMMD5)
				encryptor = new IBMFIPSMD5();
		}catch(RuntimeException re){
			System.out.println("");
		}
		if(null != encryptor)
			encryptor.initialize();
		else
			throw new Exception("Unknown FIPSEncryptor");
		
		return encryptor;
	}
}
