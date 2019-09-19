package gov.nwcg.isuite.framework.dbutil.pg;

import gov.nwcg.isuite.core.vo.DbAvailVo;

import java.io.File;
import java.util.Stack;

import org.springframework.context.ApplicationContext;

public class CreateDatabaseUtil extends AbstractPostgresUtil  {

	public CreateDatabaseUtil(ApplicationContext ctx){
		super(ctx);
	}

	public void createDatabase(DbAvailVo vo) throws Exception {
		boolean stat;
		
		try {
			String dataBaseName = vo.getDatabaseName();
			String pgBinDir = super.nwcgFolder+"e-ISuite"+File.separator+super.nwcgPgsqlFolder+File.separator+"bin"+File.separator;

			//System.out.print("Create tablsepace dir " + dataBaseName);
			//stat = new File(iswDataDir + dataBaseName).mkdir();
			//System.out.println(stat ? " success" : " failure");

			/*
			String[] command0 = {
					pgBaseDir + "bin/psql",
					"--username=postgres",
					"--dbname=postgres",
					"-c CREATE TABLESPACE " + dataBaseName+" OWNER isw LOCATION " + "'"
					+ iswDataDir + dataBaseName + "';" };

			Stack<String> errStack0 = new Stack<String>();

			if (runProc(command0, errStack0, pgBaseDir + "/bin") > 0) {
				throw new Exception(errStack0.toString());
			} else {
			}
			*/
			
			String[] command1 = { pgBinDir + "createdb"
					,"--username=postgres" 
					,"--owner=isw"
					, "--template=isuite_site_template",
					dataBaseName.toLowerCase() }; 
			Stack<String> errStack1 = new Stack<String>();

			if (runProc(command1, errStack1, pgBinDir) > 0) {
				throw new Exception(errStack1.toString());
			} else {
				// success
			}
		} catch (Exception e) {
			throw e;
		}
		
	}
}
