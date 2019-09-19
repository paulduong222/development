package test;

import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;

import org.springframework.context.ApplicationContext;

public class ISWFE_Decryptor {
	protected static ApplicationContext context;
	private static final String PACKAGED_FILE = "/Users/Karen/Desktop/Decrypt/StJohns_2016.isw";
	private static final String IMPORT_DIR = "/Users/Karen/Desktop/Decrypt/unpack";
	
	public ISWFE_Decryptor(){
	}
	
	public static void main(String[] args) {
		try {
			unpackFile();
		}catch(Exception e){
			
		}
	}
	
	private static void unpackFile() throws Exception {
		ISWFileEncryption decryptFexp = new ISWFileEncryption();
		
		try{
			// unpack the backup file
			decryptFexp.tmpDir = new File(IMPORT_DIR);
			FileUtil.makeDir(decryptFexp.tmpDir+File.separator+"work");
			
			decryptFexp.unArchive(decryptFexp.tmpDir, PACKAGED_FILE);
			decryptFexp.loadKey(decryptFexp.tmpDir, ISWFileEncryption.ISWPUBKEY);
			
			File fileTmpDir=new File(IMPORT_DIR);
			for(String filename : fileTmpDir.list()){
				//System.out.println(filename);
				if(!filename.equals("metadata.0") && !filename.equals("work")){
					decryptFexp.decrypt(decryptFexp.tmpDir, filename, decryptFexp.tmpDir+File.separator+"work"+File.separator);
					
					if(filename.endsWith(".xml") || filename.endsWith(".file")){
						FileUtil.copyFile(decryptFexp.tmpDir+File.separator+"work"+File.separator+filename
								 , decryptFexp.tmpDir+File.separator+filename);
					}
				}
			}
			System.out.println("Decrypt Complete");
		}catch(GeneralSecurityException gse){
			throw gse;
		}catch(IOException ioe){
			throw ioe;
		}catch(ClassNotFoundException cnfe){
			throw cnfe;
		}catch(Exception e){
			throw e;
		}finally{
			try{
				//FileUtils.deleteQuietly(new File(tmpImportDir+File.separator+"work"));
			}catch(Exception ignore){
				//System.out.println("");
			}
		}
	}
	
}
