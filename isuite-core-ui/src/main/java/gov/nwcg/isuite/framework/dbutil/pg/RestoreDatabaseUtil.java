package gov.nwcg.isuite.framework.dbutil.pg;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.framework.cryptfile.ISWFileEncryption;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.CollectionUtility;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.io.File;
import java.util.Collection;
import java.util.Stack;
import java.util.StringTokenizer;

import org.springframework.context.ApplicationContext;

// command1
// \NWCG\e-ISuite\pgsql\bin\pg_restore --host=127.0.0.1 --username=postgres --clean --exit-on-error --dbname=isuite_site_master  \NWCG\e-ISuite\var\backups\isuite_site_master-09132012113419.bkp


public class RestoreDatabaseUtil extends AbstractPostgresUtil  {
	private ISWFileEncryption encFexp = new ISWFileEncryption();
	public int backupFileLevel=0;
	public int originalPatchLevel=0;
	public String reportOutputFolder="";
	
	public RestoreDatabaseUtil(ApplicationContext ctx){
		super(ctx);
	}

	public void restoreDb(String databaseName,String backupFilename,String dbpwd) throws Exception {
		char sepChar = java.io.File.separatorChar;
		//String pgBinDir = pgBaseDir + "bin" + sepChar;
		//String pgBinDir = super.nwcgFolder+"e-ISuite"+File.separator+super.nwcgPgsqlFolder+File.separator+"bin"+File.separator;
		String pgBinDir = super.pgBaseDir+File.separator+"bin"+File.separator;
		
		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");
		SystemParameter spEntity = spDao.getByParameterName(SystemParameterTypeEnum.ROSS_XML_FOLDER.name());
		String path=spEntity.getParameterValue();

		try{
			// unpack the backup file
			encFexp.tmpDir = FileUtil.iswCreateTmpPath();
			FileUtil.makeDir(encFexp.tmpDir+File.separator+"work");
			try{
				encFexp.unArchive(encFexp.tmpDir, path+backupFilename);
			}catch(Exception eee){
				//System.out.println("");
			}
			encFexp.loadKey(encFexp.tmpDir, ISWFileEncryption.ISWPUBKEY);
			encFexp.decrypt(encFexp.tmpDir, "db.tar", encFexp.tmpDir+File.separator+"work"+File.separator);
			encFexp.decrypt(encFexp.tmpDir, "db.metadata", encFexp.tmpDir+File.separator+"work"+File.separator);

			String format="tar";
			Collection<String> encFilenames=FileUtil.getDirFilenames(encFexp.tmpDir+"");
			if(CollectionUtility.hasValue(encFilenames)){
				for(String fn: encFilenames){
					if(fn.endsWith(".pdf")){
						encFexp.decrypt(encFexp.tmpDir, fn, encFexp.tmpDir+File.separator+"work"+File.separator);
						FileUtil.copyFile(encFexp.tmpDir+File.separator+"work"+File.separator+fn
								,reportOutputFolder+fn);
					}else if(fn.endsWith(".cf9")){
						format="compress9";
					}
				}
			}
			
			// get the pwd 
			StringBuffer metadata=FileUtil.getFileContents(encFexp.tmpDir+File.separator+"work"+File.separator+"db.metadata");
			StringTokenizer st = new StringTokenizer(metadata.toString(),"|");
			int i=0;
			String backupFileRevision="";
			while(st.hasMoreTokens()){
				String token = (String)st.nextToken();
				switch(i){
					case 0:
						break;
					case 1:
						StringTokenizer st1 = new StringTokenizer(token,":");
						int x=0;
						while(st1.hasMoreTokens()){
							String strX=(String)st1.nextToken();
							if(x==1){
								backupFileRevision=strX;
								backupFileLevel=Integer.parseInt(backupFileRevision);
								originalPatchLevel=Integer.parseInt(backupFileRevision);
							}
							x++;
						}
						break;
					case 2:
						StringTokenizer st2 = new StringTokenizer(token,":");
						int y=0;
						while(st2.hasMoreTokens()){
							String strY=(String)st2.nextToken();
							if(y==1)
								dbpwd=strY;
							y++;
						}
						break;
				}
				i++;
				
			}

			// create the database
			try {
				String[] command1 = { pgBinDir + "createdb"
						,"--host=127.0.0.1"
						,"--port=5432"
						,"--username=postgres" 
						,"--owner=isw"
						,databaseName.trim().toLowerCase() }; 
				Stack<String> errStack1 = new Stack<String>();

				if (runProc(command1, errStack1, pgBinDir) > 0) {
					throw new Exception(errStack1.toString());
				} else {
					// success
				}
			} catch (Exception e) {
				throw e;
			}

			if(format.equals("tar")){
				// restore the db
				try {
					String[] command3 = { pgBinDir + "pg_restore"
							,"--host=127.0.0.1"
							,"--port=5432"
							,"--username=postgres" 
							,"--exit-on-error"
							,"--dbname=" + databaseName.trim().toLowerCase() 
							,encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar" }; 
					Stack<String> errStack3 = new Stack<String>();

					if (runProc(command3, errStack3, pgBinDir) > 0) {
						throw new Exception(errStack3.toString());
					} else {
						// success
					}
				} catch (Exception e) {
					throw e;
				}
				
			}else if(format.equals("compress9")){
				// restore the db
				try {
					String[] command3 = { pgBinDir + "pg_restore"
							,"--host=127.0.0.1"
							,"--port=5432"
							,"--username=postgres" 
							,"--format=c"
							,"--exit-on-error"
							,"--dbname=" + databaseName.trim().toLowerCase() 
							,encFexp.tmpDir+File.separator+"work"+File.separator+"db.tar" }; 
					Stack<String> errStack3 = new Stack<String>();

					if (runProc(command3, errStack3, pgBinDir) > 0) {
						throw new Exception(errStack3.toString());
					} else {
						// success
					}
				} catch (Exception e) {
					throw e;
				}
			}

			// copy the invoice pdfs
			
			
			// apply patches
			try {
				Collection<String> patchFiles=FileUtil.getDirFilenames(super.nwcgPatchFolder);
				if(CollectionUtility.hasValue(patchFiles)){
					for(String patchfile : patchFiles){
						if(patchfile.startsWith("patch")){
							int dotIdx=patchfile.indexOf(".");
							int scIdx=patchfile.indexOf(".sc");
							if(scIdx==-1){
								// look for .sd
								scIdx=patchfile.indexOf(".sd");
							}
							if(scIdx > -1){
								String patchlevel=patchfile.substring(dotIdx+1, scIdx);
								int filePatchLevel=Integer.parseInt(patchlevel);
	
								if(filePatchLevel > backupFileLevel){
									String[] command3 = { pgBinDir + "psql"
											,"--username=isw" 
											,"--host=127.0.0.1"
											,"--quiet"
											,"--dbname=" + databaseName.trim().toLowerCase() 
											,"--file="+super.nwcgPatchFolder+patchfile};
									
									Stack<String> errStack3 = new Stack<String>();
	
									if (runProc(command3, errStack3, pgBinDir) > 0) {
										throw new Exception(errStack3.toString());
									} else {
										// success
										backupFileLevel=filePatchLevel;
									}
								}
							}
						}
					}
				}
				
			} catch (Exception e) {
				throw e;
			}
			
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
		
	}
	
	
	public static void main(String[] args){
		Collection<String> patchFiles=FileUtil.getDirFilenames("\\NWCG\\e-ISuite\\app\\patches\\");
		if(CollectionUtility.hasValue(patchFiles)){
			for(String patchfile : patchFiles){
				System.out.println(patchfile);
				int dotIdx=patchfile.indexOf(".");
				int scIdx=patchfile.indexOf(".sc");
				if(scIdx==-1){
					// look for .sd
					scIdx=patchfile.indexOf(".sd");
				}
				if(scIdx > -1){
					
				}
				String patchlevel=patchfile.substring(dotIdx+1, scIdx);
				System.out.println(patchlevel);
				
			}
		}
		
	}
}
