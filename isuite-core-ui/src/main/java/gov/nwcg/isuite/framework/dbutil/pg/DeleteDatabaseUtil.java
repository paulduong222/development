package gov.nwcg.isuite.framework.dbutil.pg;

import gov.nwcg.isuite.core.vo.DbAvailVo;

import java.io.File;
import java.util.Stack;

import org.springframework.context.ApplicationContext;

public class DeleteDatabaseUtil extends AbstractPostgresUtil {

	public DeleteDatabaseUtil(ApplicationContext ctx){
		super(ctx);
	}

	public void deleteDatabase(DbAvailVo vo) throws Exception {
		try{
			char sepChar = java.io.File.separatorChar;
			//String pgBinDir = pgBaseDir + "bin" + sepChar;
			//String pgBinDir = super.nwcgFolder+"e-ISuite"+File.separator+super.nwcgPgsqlFolder+File.separator+"bin"+File.separator;
			String pgBinDir = super.pgBaseDir+File.separator+"bin"+File.separator;

			String terminateConnectionsCmd="SELECT pg_terminate_backend(pg_stat_activity.pid) "+
											"FROM pg_stat_activity " +
											"WHERE pg_stat_activity.datname = '"+vo.getDatabaseName().toLowerCase()+"' " +
											"AND pid <> pg_backend_pid(); ";
			String[] command1 = { pgBinDir + "psql"
					,"--host=127.0.0.1"
					,"--port=5432"
					,"--username=postgres" 
					,"--dbname=isuite_site_master"
					,"--command="+terminateConnectionsCmd
			};

			Stack<String> errStack1 = new Stack<String>();

			if (runProc(command1, errStack1, pgBinDir) > 0) {
				throw new Exception(errStack1.toString());
			} else {
				// success
			}
			
			String dropCmd="drop database " + vo.getDatabaseName().toLowerCase()+";";
			String[] command2 = { pgBinDir + "psql"
					,"--host=127.0.0.1"
					,"--port=5432"
					,"--username=postgres" 
					,"--dbname=isuite_site_master"
					,"--command="+dropCmd 
			};
			
			Stack<String> errStack2 = new Stack<String>();

			if (runProc(command2, errStack2, pgBinDir) > 0) {
				throw new Exception(errStack2.toString());
			} else {
				// success
			}

		}catch(Exception e){
			throw e;
		}
	}

	
}
