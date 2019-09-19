package gov.nwcg.isuite.framework.core.xmltransferv2;

import gov.nwcg.isuite.framework.logging.LoggingInterceptor;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;

public class AbstractXmlTransfer {
	protected LoggingInterceptor logger=null;
	protected String runMode="";
	//public Boolean isOracle=false;
	public Collection<String> logEntries = new ArrayList<String>();
	private Boolean isDevMode=false;
	public Boolean doLogging=false;
	public PrintWriter out=null;
	
	protected void log(Level level,String val){
		if(isDevMode==true)
			System.out.println(val);

		if(doLogging==true && null != out){
			out.print(val+"\n");
			out.flush();
		}
			//this.logEntries.add(val + "\n");
		
		// log errors
		if(level.toInt()==Level.ERROR_INT){
			if(null != logger){
				logger.addLog(val, level);
			}
		}
	}
	
	public void setLogger(LoggingInterceptor loger){
		this.logger=loger;
	}

	public void setRunMode(String val){
		this.runMode=val;
	}
}
