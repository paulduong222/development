package gov.nwcg.isuite.framework.dbutil.pg;

import gov.nwcg.isuite.core.vo.DbAvailVo;
import gov.nwcg.isuite.framework.util.FileUtil;

import java.io.File;
import java.util.Stack;
import java.util.UUID;

import org.springframework.context.ApplicationContext;

public class CopyDatabaseUtil extends AbstractPostgresUtil  {

	public CopyDatabaseUtil(ApplicationContext ctx){
		super(ctx);
	}
	
	public int copyDatabase(DbAvailVo sourceVo, DbAvailVo targetVo) throws Exception {
		char sepChar = java.io.File.separatorChar;
		//String pgBinDir = super.nwcgFolder+"e-ISuite"+File.separator+super.nwcgPgsqlFolder+File.separator+"bin"+File.separator;
		//String pgBinDir = super.nwcgFolder+File.separator+super.nwcgPgsqlFolder+File.separator+"bin"+File.separator;
		String pgBinDir = super.pgBaseDir+File.separator+"bin"+File.separator;
		
		File myTmpDir = FileUtil.iswCreateTmpPath();
		String dmpFileCanPath =  myTmpDir.getCanonicalPath() + sepChar + UUID.randomUUID().toString() + ".dmp";
				
		try {
			
			String[] command1 = { pgBinDir + "createdb"
					,"--host=127.0.0.1"
					,"--port=5432"
					,"--username=postgres" 
					,"--owner=isw"
					,targetVo.getDatabaseName().toLowerCase() }; 
			Stack<String> errStack1 = new Stack<String>();

			if (runProc(command1, errStack1, pgBinDir) > 0) {
				throw new Exception(errStack1.toString());
			} else {
				// success
			}
		} catch (Exception e) {
			throw e;
		}
		
		try {
			
			String[] command2 = { pgBinDir + "pg_dump"
					,"--host=127.0.0.1"
					,"--port=5432"
					,"--username=isw" 
					,"--role=isw"
					,"--format=tar"
					,"--blobs"
					,"--file=" + dmpFileCanPath
					,sourceVo.getDatabaseName().toLowerCase() }; 
			Stack<String> errStack2 = new Stack<String>();

			if (runProc(command2, errStack2, pgBinDir) > 0) {
				throw new Exception(errStack2.toString());
			} else {
				// success
			}
		} catch (Exception e) {
			throw e;
		}
		
		try {
	
			String[] command3 = { pgBinDir + "pg_restore"
					,"--host=127.0.0.1"
					,"--port=5432"
					,"--username=postgres" 
					,"--exit-on-error"
					,"--dbname=" + targetVo.getDatabaseName().toLowerCase() 
					,dmpFileCanPath }; 
			Stack<String> errStack3 = new Stack<String>();

			if (runProc(command3, errStack3, pgBinDir) > 0) {
				throw new Exception(errStack3.toString());
			} else {
				// success
			}
		} catch (Exception e) {
			throw e;
		}
		
		FileUtil.rDelete(myTmpDir);
		
		return 1;
	}
	
	public static void main(String[] args) throws Exception {
		
		String pgBinDir = "test"+File.separator+"e-ISuite";
		System.out.println(pgBinDir);
		
	}
}
