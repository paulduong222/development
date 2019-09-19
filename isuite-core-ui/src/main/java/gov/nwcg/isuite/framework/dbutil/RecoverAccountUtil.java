package gov.nwcg.isuite.framework.dbutil;

import gov.nwcg.isuite.core.domain.DbAvail;
import gov.nwcg.isuite.core.domain.impl.DbAvailImpl;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;

import java.net.InetAddress;
import java.util.Calendar;
import java.util.StringTokenizer;

import com.ibm.xml.crypto.util.Base64;

public class RecoverAccountUtil {
	private static final String delimeter="%^@";
		
	public static Boolean isSiteCodeValid(String code) throws Exception {
		// decrypt the code
		FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
		Base64 base64 = new Base64();
		byte[] b2=base64.decode(code);
		String siteCode=new String(enc.decrypt(b2));
		StringTokenizer st = new StringTokenizer(siteCode,delimeter);

		Boolean isValid=false;
		int idx=0;
		while(st.hasMoreTokens()){
			String token=(String)st.nextToken();
			switch(idx){
				case 0:
					break;
				case 1:
					if(token.equals("ST"))
						isValid=true;
					break;
			}
			idx++;
		}
		
		return isValid;
	}
	
	public static String generateRecoverCode(DbAvail entity) throws Exception {
		String hostName=InetAddress.getLocalHost().getHostName();
		byte[] hostNameBytes=hostName.getBytes();
		int hostNameByteValue=0;
		for(int i=0;i<hostNameBytes.length;i++){
			hostNameByteValue=hostNameByteValue+hostNameBytes[i];
		}
		
		byte[] dbNameBytes=entity.getName().toLowerCase().getBytes();
		int dbNameByteValue=0;
		for(int i=0;i<dbNameBytes.length;i++){
			dbNameByteValue=dbNameByteValue+dbNameBytes[i];
		}
		
		long lngDate=Calendar.getInstance().getTimeInMillis();
		long val=dbNameByteValue-hostNameByteValue+lngDate;
		
		String newCode=String.valueOf(entity.getId())
						+delimeter
						+"ST"
						+delimeter
						+val
		;
		//System.out.println(newCode);
		FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
		byte[] bytes = enc.encrypt(newCode.getBytes());
		Base64 base64 = new Base64();
		String newCodeEncoded=base64.encode(bytes);					
		
		return newCodeEncoded;
	}

	public static String generateSiteKey(String code) throws Exception {
		// decrypt the code
		FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
		Base64 base64 = new Base64();
		byte[] b2=base64.decode(code);
		String siteCode=new String(enc.decrypt(b2));
		StringTokenizer st = new StringTokenizer(siteCode,delimeter);
		
		// Construct the values
		String id="";
		String site="";
		String hostName="";
		String dbName="";
		int idx=0;
		while(st.hasMoreTokens()){
			String token=(String)st.nextToken();
			switch(idx){
				case 0:
					id=String.valueOf(Integer.parseInt(token)+100);
					break;
				case 1:
					site=token;
					break;
				case 2:
					dbName=token;
					break;
			}
			idx++;
		}
		
		String siteKey=id+hostName+dbName;
		
		//System.out.println(newCode);
		byte[] bytes = enc.encrypt(siteKey.getBytes());
		String siteKeyEncoded=base64.encode(bytes);					
		
		return siteKeyEncoded;
	}
	
	public static Boolean isSiteKeyAuthentic(String dbName,String siteCode,String siteKey) throws Exception {
		String siteCodeKey=RecoverAccountUtil.generateSiteKey(siteCode);
		siteKey = siteKey.replaceAll("fslash", "/");
		if(siteCodeKey.equals(siteKey))
			return true;
		else
			return false;
	}

	public static void main(String[] args) throws Exception{
		DbAvail entity = new DbAvailImpl();
		entity.setId(10L);
		entity.setName("eisuite");
		
		String code=RecoverAccountUtil.generateRecoverCode(entity);
		
		System.out.println("Site Code: " + code);

		code="Wm4u3nB2FLw70EgcCe1w6M7ZbcF+W1V7";
		String accessKey=RecoverAccountUtil.generateSiteKey(code);
		System.out.println("Access Key: " + accessKey);
		
		Boolean isValid=RecoverAccountUtil.isSiteKeyAuthentic("eisuite", code, accessKey);
		System.out.println(isValid);
	}

}
