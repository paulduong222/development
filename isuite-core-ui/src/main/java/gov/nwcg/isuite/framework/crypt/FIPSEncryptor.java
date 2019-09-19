package gov.nwcg.isuite.framework.crypt;

public interface FIPSEncryptor {

	public void initialize() throws Exception;
	
	public int getFipsComplianceLevel() throws Exception;
	
	public byte[] encrypt(byte[] data) throws Exception ;
	
	public byte[] decrypt(byte[] data) throws Exception ;
	
}
