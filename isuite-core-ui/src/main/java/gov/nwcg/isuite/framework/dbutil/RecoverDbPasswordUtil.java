package gov.nwcg.isuite.framework.dbutil;

import java.io.File;
import java.util.Calendar;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

import com.ibm.xml.crypto.util.Base64;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptor;
import gov.nwcg.isuite.framework.crypt.FIPSEncryptorFactory;
import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;

public class RecoverDbPasswordUtil {
	private ISWFileEncryption encFexp = new ISWFileEncryption();
	private ApplicationContext context;
	
	public RecoverDbPasswordUtil(ApplicationContext ctx){
		this.context=ctx;
	}

	public String generateCode(String backupFilename) throws Exception {
		String recoverCode="";
		
		try{
			SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
			SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.ROSS_XML_FOLDER.name());
			String path=spEntity.getParameterValue();
			
			// unpack the backup file
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");
			encFexp.unArchive(encFexp.tmpDir, path+backupFilename);
			encFexp.loadKey(encFexp.tmpDir, ISWFileEncryption.ISWPUBKEY);
			encFexp.decrypt(encFexp.tmpDir, "db.tar", encFexp.tmpDir+File.separator+"work"+File.separator);
			encFexp.decrypt(encFexp.tmpDir, "db.metadata", encFexp.tmpDir+File.separator+"work"+File.separator);

			// get the pwd 
			StringBuffer metadata=FileUtil.getFileContents(encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");
			StringTokenizer st = new StringTokenizer(metadata.toString(),"|");
			int i=0;
			String dbpwdhash="";
			while(st.hasMoreTokens()){
				String token = (String)st.nextToken();
				switch(i){
					case 0:
						break;
					case 1:
						break;
					case 2:
						StringTokenizer st2 = new StringTokenizer(token,":");
						int y=0;
						while(st2.hasMoreTokens()){
							String strY=(String)st2.nextToken();
							if(y==1)
								dbpwdhash=strY;
							y++;
						}
						break;
				}
				i++;
				
			}
			
			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			Base64 base64 = new Base64();
			byte[] bytes=base64.decode(dbpwdhash);
			String dbpwd=new String(enc.decrypt(bytes));
			
			String time=String.valueOf(Calendar.getInstance().getTimeInMillis());
			
			dbpwd=time.substring(0,5)+dbpwd+"99";
			
			byte[] bytesEnc = enc.encrypt(dbpwd.getBytes());
			recoverCode=base64.encode(bytesEnc);					
			
		}catch(Exception ee){
			throw ee;
		}finally{
			try{
				if (null != encFexp.tmpDir && encFexp.tmpDir.exists()) 
					FileUtil.rDelete(encFexp.tmpDir);
			} catch (Exception fe) {
				// smother
			}
		}

		return recoverCode;
	}
	
	public String generatePassword(String code) throws Exception {
		String password="";
		
		try{
			
			FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
			Base64 base64 = new Base64();
			byte[] bytes=base64.decode(code);
			String dbpwd=new String(enc.decrypt(bytes));
			
			password=dbpwd.substring(5, dbpwd.length()-2);
			
		}catch(Exception ee){
			throw ee;
		}finally{
		}

		return password;
	}

	public static void main(String[] args) throws Exception{
		String pwd="eisuite";
		
		String time=String.valueOf(Calendar.getInstance().getTimeInMillis());
		
		pwd=time.substring(0,5)+pwd+"99";
		
		FIPSEncryptor enc = FIPSEncryptorFactory.getDefault();
		Base64 base64 = new Base64();
		byte[] bytesEncrypted=enc.encrypt(pwd.getBytes());
		String encoded=base64.encode(bytesEncrypted);
		System.out.println(encoded);
		
		RecoverDbPasswordUtil util = new RecoverDbPasswordUtil(null);
		System.out.println(util.generatePassword(encoded));
				
		System.out.println(util.generatePassword("31bhq4|zxaw=="));
	}
	
}
