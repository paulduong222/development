package test;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;

import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;

public class ISWFE_Encryptor {
	
	private static final String DATA_TRANSFER_FILE = "/Users/Karen/Desktop/Encrypt/StJohns_2016MOD.isw";
	private static final String TMP_DIR = "/Users/Karen/Desktop/Encrypt/package";
	private static final String MASTER_LIST_DIR = "/Users/Karen/Desktop/Encrypt/FilesToEncrypt";
	private static final String WORK_DIR = "/Users/Karen/Desktop/Encrypt/package/work";
	
	public ISWFE_Encryptor(){
	}
	
	public static void main(String[] args) {
		try {
			packageFile();
		}catch(Exception e){
		}
	}
	
	private static void packageFile() throws Exception {
		
		try{

			ISWFileEncryption encFexp=new ISWFileEncryption();
			encFexp.tmpDir = new File(TMP_DIR);
			encFexp.aesKey = encFexp.genKey(null);
			encFexp.saveAESKey(encFexp.aesKey, encFexp.tmpDir,ISWFileEncryption.ISWPRIVKEY);
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");

//			Collection<String> xmlFileList=FileUtil.getDirFilenames(TMP_DIR);
//			if(CollectionUtility.hasValue(xmlFileList)){
			File fileTmpDir=new File(MASTER_LIST_DIR);
			for(String s : fileTmpDir.list()){
//				for(String s : xmlFileList){
//					String copyXmlFile = TMP_DIR+File.separator+s;
//					FileUtil.copyFile(MASTER_LIST_DIR+File.separator+s, copyXmlFile);
//					encFexp.encrypt(encFexp.aesKey, WORK_DIR, TMP_DIRr+"work"+File.separator+s);
					
					String workXmlFile=TMP_DIR+File.separator+"work"+File.separator+s;
					System.out.println(s);
					if(s.endsWith(".xml") || s.endsWith(".file") || s.endsWith(".pdf")){
						FileUtil.copyFile(MASTER_LIST_DIR+File.separator+s, workXmlFile);
						encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+s);
					}
//				}
			}
			System.out.println("Encrypt Complete");
			try{
				FileUtils.deleteQuietly(new File(TMP_DIR+File.separator+"work"));
			}catch(Exception ignore){
				//System.out.println("");
			}
			
			encFexp.iswPackage(encFexp.tmpDir, DATA_TRANSFER_FILE);
		}catch(Exception e){
			throw e;
		}finally{
		}
		
	}

}
