package gov.nwcg.isuite.framework.crypt.impl;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.Provider;
import java.security.Security;

import com.ibm.crypto.fips.provider.IBMJCEFIPS;

import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;

public class IBMFIPSMD5 implements FIPSEncryptor{

	public IBMFIPSMD5(){
		
	}

	@Override
	public byte[] decrypt(byte[] data) throws Exception {
		throw new Exception("not supported");
	}

	@Override
	public byte[] encrypt(byte[] data) throws Exception {
		String md5Val = "";

		try{
			Security.addProvider(new com.ibm.crypto.fips.provider.IBMJCEFIPS());
			Provider p = Security.getProvider("IBMJCEFIPS");
			IBMJCEFIPS a = (IBMJCEFIPS)p;

            MessageDigest digest = MessageDigest.getInstance("SHA2","IBMJCEFIPS");
            digest.update(data);

            BigInteger lHashInt = new BigInteger(1, digest.digest());

            md5Val=String.format("%1$032X", lHashInt);

		}catch(Exception e){
			throw e;
		}
		
		return md5Val.getBytes();
	}

	@Override
	public int getFipsComplianceLevel() throws Exception {
		Provider p = Security.getProvider("IBMJCEFIPS");
		IBMJCEFIPS fips = (IBMJCEFIPS)p;
		return fips.getFipsLevel();
	}

	@Override
	public void initialize() throws Exception {
		
	}
	
	
}
