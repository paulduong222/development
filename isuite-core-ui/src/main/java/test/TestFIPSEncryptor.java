package test;

import java.util.Arrays;

import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorType;

public class TestFIPSEncryptor {
	
	public static void main(String[] args) throws Exception {
		// Test encrypt and decrypt method
		test();
	}
	
	public static void test() throws Exception{
		String one = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>" + 
"<ns2:UserTransfer xmlns=\"http://isuite.nwcg.gov/lib\" xmlns:ns2=\"http://isuite.nwcg.gov/UserTransfer\">" + 
"    <ns2:User>" + 
"        <LoginName>ad.admin</LoginName>" + 
"        <FirstName>ADMIN</FirstName>" + 
"        <LastName>USER</LastName>" + 
"        <HomeUnitCode>ID-BOF</HomeUnitCode>" + 
"        <PrimaryDispatchCenterCode>ID-BDC</PrimaryDispatchCenterCode>" + 
"        <Role>ROLE_ACCOUNT_MANAGER</Role>" + 
"    </ns2:User>" + 
"</ns2:UserTransfer>";
		System.out.println(one);
		FIPSEncryptor encryptor = FIPSEncryptorFactory.getInstance(FIPSEncryptorType.IBMFIPSTripleDES);
		byte[] encyptedBytes = encryptor.encrypt(one.getBytes());
		System.out.println(Arrays.toString(encyptedBytes));
		byte[] decyptedBytes = encryptor.decrypt(encyptedBytes);
		System.out.println(Arrays.toString(decyptedBytes));
		String decryptedString = new String(decyptedBytes);
		System.out.println(decryptedString);
		
	}
}
