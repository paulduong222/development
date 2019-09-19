package gov.nwcg.isuite.framework.dbutil.pg;

import gov.nwcg.isuite.core.domain.SystemParameter;
import gov.nwcg.isuite.core.persistence.SystemParameterDao;
import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.ErrorEnum;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.util.FileUtil;
import gov.nwcg.isuite.framework.util.StringUtility;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Stack;

import org.springframework.context.ApplicationContext;

public class AbstractPostgresUtil {
	protected ApplicationContext context;
	//protected String pgBaseDir = "\\NWCG\\e-ISuite\\pgsql\\";
	protected String nwcgPatchDir = "\\NWCG\\e-ISuite\\app\\patches\\";

	public String pgBaseDir="";

	public String nwcgFolder="";
	public String nwcgBackupFolder="";
	public String nwcgPatchFolder="";
	public String nwcgPgsqlFolder="pgsql";

	public AbstractPostgresUtil(ApplicationContext ctx){
		this.context=ctx;
	}

	/**
	 * @param error
	 * @throws ServiceException
	 */
	protected void handleException(ErrorEnum error,String... params) throws ServiceException {
		throw new ServiceException(new ErrorObject(error,params));
	}

	protected String getSystemParamValue(SystemParameterTypeEnum paramName) throws Exception {

		SystemParameterDao spDao = (SystemParameterDao)context.getBean("systemParameterDao");

		SystemParameter entity = spDao.getByParameterName(paramName.name());

		if(null == entity)
			this.handleException(ErrorEnum._900001_ENTITY_NOT_FOUND,"SystemParameter["+paramName.name()+"]");

		return entity.getParameterValue();
	}

	protected int runProc(final String[] command, Stack<String> errStack, String pwd) throws Exception, IOException {

		int ret = 1;

		try {
			ProcessBuilder procbuilder = new ProcessBuilder(command);

			procbuilder.directory(new File(pwd));

			Process process = procbuilder.start();
			InputStream is = process.getErrorStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String stdErr;
			while ((stdErr = br.readLine()) != null) {
				//System.out.println(stdErr);
				errStack.push(new String(stdErr));
			}
			ret = process.waitFor();
			process.destroy();

		} catch (final InterruptedException e) {
			e.printStackTrace();
		}
		return (ret);
	}

    protected int runProc2(final String[] command, Stack<String> errStack, String pwd) throws Exception, IOException {

        int ret = 1;
        
        ProcessBuilder procbuilder = new ProcessBuilder(command);

        procbuilder.directory(new File(pwd));

        final Process process = procbuilder.start();
        
        final StringBuilder out = new StringBuilder();
        final Thread reader = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final InputStream is = process.getInputStream();
                    int c;
                    while ((c = is.read()) != -1) {
                        out.append((char) c);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        // ToDo Deal with out...
        
        procbuilder.redirectErrorStream(true);
        reader.start();
        ret = process.waitFor();
        process.destroy();
        return (ret);
    }
   
	public Collection<String> getAllInvoiceFileNames(String dbname) {
		Collection<String> list=new ArrayList<String>();

		try{
			String outFile=nwcgBackupFolder+"sqlout.file";
			if(FileUtil.fileExists(outFile)){
				FileUtil.deleteFile(outFile);
			}
			String pgBinDir=pgBaseDir+"bin"+File.separator;
	  		String sql = "select rpt.file_name " +
				 "from isw_time_invoice ti "+
				 "     , isw_report rpt " +
				 "where ti.deleted_date is null " +
				 "and ti.is_draft = false " +
				 "and rpt.id = ti.invoice_report_id " ;
			
			String[] command3 = { pgBinDir + "psql"
					,"--username=isw" 
					,"--host=127.0.0.1"
					,"--quiet"
					,"--dbname=" + dbname.trim().toLowerCase() 
					,"--output="+outFile
					,"-t"
					,"--command="+sql};
			
			Stack<String> errStack3 = new Stack<String>();

			ProcessBuilder procbuilder = new ProcessBuilder(command3);

			procbuilder.directory(new File(pgBinDir));

			Process process = procbuilder.start();
			InputStream is = process.getErrorStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String stdErr;
			while ((stdErr = br.readLine()) != null) {
				errStack3.push(new String(stdErr));
			}
			Object ret = process.waitFor();
			process.destroy();
			
			if(FileUtil.fileExists(outFile)){
				Collection<String> pdfs=FileUtil.getContentsByLine(outFile);
				FileUtil.deleteFile(outFile);
				for(String s : pdfs){
					if(StringUtility.hasValue(s))
						list.add(s.trim());
				}
				
			}
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
		
		
		return list;
	}

	public Collection<String> getIapAttachments(String dbname) {
		Collection<String> list=new ArrayList<String>();

		try{
			String outFile=nwcgBackupFolder+"sqlout.file";
			if(FileUtil.fileExists(outFile)){
				FileUtil.deleteFile(outFile);
			}
			String pgBinDir=pgBaseDir+"bin"+File.separator;
	  		String sql = "select filename from isw_iap_attachment ";
			
			String[] command3 = { pgBinDir + "psql"
					,"--username=isw" 
					,"--host=127.0.0.1"
					,"--quiet"
					,"--dbname=" + dbname.trim().toLowerCase() 
					,"--output="+outFile
					,"-t"
					,"--command="+sql};
			
			Stack<String> errStack3 = new Stack<String>();

			ProcessBuilder procbuilder = new ProcessBuilder(command3);

			procbuilder.directory(new File(pgBinDir));

			Process process = procbuilder.start();
			InputStream is = process.getErrorStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
			String stdErr;
			while ((stdErr = br.readLine()) != null) {
				errStack3.push(new String(stdErr));
			}
			Object ret = process.waitFor();
			process.destroy();
			
			if(FileUtil.fileExists(outFile)){
				Collection<String> pdfs=FileUtil.getContentsByLine(outFile);
				FileUtil.deleteFile(outFile);
				for(String s : pdfs){
					if(StringUtility.hasValue(s))
						list.add(s.trim());
				}
				
			}
		}catch(Exception e){
			//System.out.println(e.getMessage());
		}
		
		
		return list;
	}
	
}
