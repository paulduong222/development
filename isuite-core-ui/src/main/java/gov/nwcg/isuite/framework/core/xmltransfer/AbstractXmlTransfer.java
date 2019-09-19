package gov.nwcg.isuite.framework.core.xmltransfer;

import gov.nwcg.isuite.framework.logging.LoggingInterceptor;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.Level;

public class AbstractXmlTransfer {
	protected LoggingInterceptor logger=null;
	protected String runMode="";
	public Collection<String> logEntries = new ArrayList<String>();
	private Boolean isDevMode=false;
	
	protected void log(Level level,String val){
		if(isDevMode==true)
			System.out.println(val);
		
		this.logEntries.add(val + "\n");
		
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
