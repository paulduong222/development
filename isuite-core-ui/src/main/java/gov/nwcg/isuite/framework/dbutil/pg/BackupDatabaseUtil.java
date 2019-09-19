package gov.nwcg.isuite.framework.dbutil.pg;

import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.util.BooleanUtility;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import org.springframework.context.ApplicationContext;

// command1
// \NWCG\e-ISuite\pgsql\bin\pg_dump --host=127.0.0.1 --username=postgres --format=tar --blobs --file=\NWCG\e-ISuite\var\backups\isuite_site_master-09132012113419.bkp isuite_site_master


public class BackupDatabaseUtil extends AbstractPostgresUtil {
	public Collection<String> pdfFilenames = new ArrayList<String>();
	public String reportOutputFolder="";
	
	public BackupDatabaseUtil(ApplicationContext ctx){
		super(ctx);
	}

	public void backupDatabase(DbAvailVo vo, String dbCredential,String destFilename, String patchLevel) throws Exception {
		ISWFileEncryption encFexp = new ISWFileEncryption();
		
		try{
			char sepChar = java.io.File.separatorChar;
			//String pgBinDir = super.nwcgFolder+"e-ISuite"+File.separator+super.nwcgPgsqlFolder+File.separator+"bin"+File.separator;
			String pgBinDir = super.pgBaseDir+File.separator+"bin"+File.separator;
			String backupDir = super.nwcgBackupFolder;

			// init file encryptor and tmpdir
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			encFexp.aesKey = encFexp.genKey(null);
			encFexp.saveAESKey(encFexp.aesKey, encFexp.tmpDir,ISWFileEncryption.ISWPRIVKEY);
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");
			
			// ensure target backup dir exists
			FileUtil.makeDir(backupDir);
			
			String[] command2 = { pgBinDir + "pg_dump"
					,"--host=127.0.0.1"
					,"--port=5432"
					,"--username=isw" 
					,"--role=isw"
					,"--format=c"
					,"--compress=9"
					,"--blobs"
					,"--file=" + encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar"
					,vo.getDatabaseName().toLowerCase() }; 
			/*
					,"--format=tar"
					,"--format=c"
					,"--compress=9"

			 */
			
			Stack<String> errStack2 = new Stack<String>();

			if (runProc(command2, errStack2, pgBinDir) > 0) {
				throw new Exception(errStack2.toString());
			} else {
				// pg dump successful
			}

			// encrypt dbTarFile
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar");
			
			// create the db metadata file
			String dbMetadata = "Version:00.02"
								+"|"+"PatchLevel:"+patchLevel
								+"|"+"Credential:"+dbCredential;
			
			// create and encrypt dbMetadata file
			FileUtil.writeFile(dbMetadata.getBytes(), encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");

			FileUtil.createFile(encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9");
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9");
			
			for(String pdffile : pdfFilenames){
				if(BooleanUtility.isTrue(FileUtil.fileExists(reportOutputFolder+pdffile.trim()))){
					FileUtil.copyFile(reportOutputFolder+pdffile.trim()
									 , encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim());
					encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim());
				}
			}

			// clean up work dir
			FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar");
			FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");
			for(String pdffile : pdfFilenames){
				if(BooleanUtility.isTrue(FileUtil.fileExists(encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim()))){
					FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim());
				}
			}
			if(BooleanUtility.isTrue(FileUtil.fileExists(encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9"))){
				FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9");
			}

			FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work");
			
			// package the final backup file
			encFexp.iswPackage(encFexp.tmpDir, backupDir+destFilename.trim()+".bak");
			
		}catch(Exception e){
			throw e;
		}finally{
			try {
				if(null != encFexp.tmpDir && encFexp.tmpDir.exists()) {
					FileUtil.rDelete(encFexp.tmpDir);
				}
			} catch (Exception fe) {
				// smother
			}
		}
	}

	public void backupDatabaseForTransfer(DbAvailVo vo
											, String finalDir
											,String destFilename
											, String patchLevel
											, String dtXml) throws Exception {
		ISWFileEncryption encFexp = new ISWFileEncryption();
		
		try{
			char sepChar = java.io.File.separatorChar;
			//String pgBinDir = super.nwcgFolder+"e-ISuite"+File.separator+super.nwcgPgsqlFolder+File.separator+"bin"+File.separator;
			String pgBinDir = super.pgBaseDir+File.separator+"bin"+File.separator;
			String backupDir = super.nwcgBackupFolder;

			// init file encryptor and tmpdir
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			encFexp.aesKey = encFexp.genKey(null);
			encFexp.saveAESKey(encFexp.aesKey, encFexp.tmpDir,ISWFileEncryption.ISWPRIVKEY);
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");
			
			// ensure target backup dir exists
			FileUtil.makeDir(backupDir);
			
			String[] command2 = { pgBinDir + "pg_dump"
					,"--host=127.0.0.1"
					,"--port=5432"
					,"--username=isw" 
					,"--role=isw"
					,"--format=c"
					,"--compress=9"
					,"--blobs"
					,"--file=" + encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar"
					,vo.getDatabaseName().toLowerCase() }; 
			/*
					,"--format=tar"
					,"--format=c"
					,"--compress=9"

			 */
			
			Stack<String> errStack2 = new Stack<String>();

			if (runProc(command2, errStack2, pgBinDir) > 0) {
				throw new Exception(errStack2.toString());
			} else {
				// pg dump successful
			}

			// encrypt dbTarFile
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar");
			
			// create the db metadata file
			//String dbMetadata = "Version:00.02"
			//					+"|"+"PatchLevel:"+patchLevel
			//					+"|"+"Credential:"+dbCredential;
			
			// create and encrypt dbMetadata file
			//FileUtil.writeFile(dbMetadata.getBytes(), encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");
			//encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");

			// create and encrypt dtXml String file
			FileUtil.writeFile(dtXml.getBytes(), encFexp.tmpDir+File.separator+"work"+File.separator+"dt.xml");
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"dt.xml");
			
			FileUtil.createFile(encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9");
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9");

			String versionFile=encFexp.tmpDir+File.separator+"work"+File.separator+"Version3.file";
			FileUtil.createFile(versionFile);
			encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+"Version3.file");
			
			for(String pdffile : pdfFilenames){
				if(BooleanUtility.isTrue(FileUtil.fileExists(reportOutputFolder+pdffile.trim()))){
					FileUtil.copyFile(reportOutputFolder+pdffile.trim()
									 , encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim());
					encFexp.encrypt(encFexp.aesKey, encFexp.tmpDir, encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim());
				}
			}

			// clean up work dir
			FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar");
			FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"dt.xml");
			FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"Version3.file");
			
			//FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");
			for(String pdffile : pdfFilenames){
				if(BooleanUtility.isTrue(FileUtil.fileExists(encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim()))){
					FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+pdffile.trim());
				}
			}
			if(BooleanUtility.isTrue(FileUtil.fileExists(encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9"))){
				FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work"+File.separator+"ftype.cf9");
			}

			FileUtil.deleteFile(encFexp.tmpDir+File.separator+"work");
			
			// package the final backup file
			encFexp.iswPackage(encFexp.tmpDir, finalDir+destFilename);
			
		}catch(Exception e){
			throw e;
		}finally{
			try {
				if(null != encFexp.tmpDir && encFexp.tmpDir.exists()) {
					FileUtil.rDelete(encFexp.tmpDir);
				}
			} catch (Exception fe) {
				// smother
				//System.out.println(fe.getMessage());
			}
		}
	}

	public static void main(String[] args){
		BackupDatabaseUtil util = new BackupDatabaseUtil(null);
		DbAvailVo vo = new DbAvailVo();
		vo.setDatabaseName("test");
		
		try{
			util.backupDatabase(vo, "test","bfile","186");
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
