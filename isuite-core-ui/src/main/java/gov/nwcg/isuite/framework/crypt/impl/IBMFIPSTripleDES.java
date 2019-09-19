package gov.nwcg.isuite.framework.crypt.impl;

import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;

import java.security.Provider;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;

import com.ibm.crypto.fips.provider.IBMJCEFIPS;
import com.ibm.xml.crypto.util.Base64;

// TODO: need to obfuscate this class using 3rd party tool
public class IBMFIPSTripleDES implements FIPSEncryptor {

	protected static Cipher cipher=null;
	
	protected byte[] seedKeyBytes = 
		new byte[] { 
			0x55, 0x54, 0x40, 0x48, 0x4a, 0x40, 0x5a, 0x5a, 0x24, 0x54, 0x30, 0x43, 0x4b, 0x54, 0x30, 0x4e, 0x54, 0x4f, 0x4d, 0x40, 0x4c, 0x4f, 0x4e, 0x33	
		};
	
	public IBMFIPSTripleDES(){
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.crypt.FIPSEncryptor#initialize()
	 */
	public void initialize() throws Exception{
		if(null==cipher){
			Security.addProvider(new com.ibm.crypto.fips.provider.IBMJCEFIPS());
			cipher = Cipher.getInstance("TripleDES", "IBMJCEFIPS");
		}
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.crypt.FIPSEncryptor#getFipsCompliantLevel()
	 */
	public int getFipsComplianceLevel() throws Exception{
		Provider p = Security.getProvider("IBMJCEFIPS");
		IBMJCEFIPS fips = (IBMJCEFIPS)p;
		return fips.getFipsLevel();
	}
	
	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.crypt.Encryptor#decrypt(byte[])
	 */
	public byte[] decrypt(byte[] data) throws Exception {
		DESedeKeySpec spec = new DESedeKeySpec(this.seedKeyBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("TripleDES", "IBMJCEFIPS");
		SecretKey sk = keyFactory.generateSecret(spec);
		
		cipher.init(Cipher.DECRYPT_MODE , sk);
		
		byte[] decrypted = cipher.doFinal(data);
		
		return decrypted;
	}

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.framework.crypt.Encryptor#encrypt(byte[])
	 */
	public byte[] encrypt(byte[] data) throws Exception {
		
		DESedeKeySpec spec = new DESedeKeySpec(this.seedKeyBytes);
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("TripleDES", "IBMJCEFIPS");
		SecretKey sk = keyFactory.generateSecret(spec);
		
		cipher.init(Cipher.ENCRYPT_MODE, sk);
		
		byte[] encrypted = cipher.doFinal(data);
		
		return encrypted;
	}


	public static void main(String[] args){
		try{
			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			//wsautheisuite
			//Oct_44nap
			//byte[] bytes = enc.encrypt("Oct_44nap".getBytes());
			byte[] bytes = enc.encrypt("wsautheisuite".getBytes());
			
			Base64 base64 = new Base64();
			System.out.println(base64.encode(bytes));
			System.out.println(bytes);
			System.out.println(new String(enc.decrypt(bytes)));
			//System.out.println(enc.encrypt("myTraining1!".getBytes()));
			
			System.out.println("");
			byte[] b1=enc.encrypt("eisuite".getBytes());
			String s1=base64.encode(b1);
			System.out.println(s1);
			byte[] b2=base64.decode(s1);
			System.out.println(new String(enc.decrypt(b2)));
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
