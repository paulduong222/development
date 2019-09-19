package gov.nwcg.isuite.framework.core.session;

import org.springframework.context.ApplicationContext;


public class SessionManagementRunner implements Runnable {
	private long interval=30000;
	private ApplicationContext context=null;
	
	public SessionManagementRunner(){
	}
	
	public void startSessionMgmtRunnable() throws Exception {
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setInterval(long milliseconds) {
		this.interval = milliseconds;
	}
	
    public void run() {
    	Boolean proceed=true;
    	
    	while(proceed)
    	{
        	try{
        		synchronized(this){
	        		Thread.sleep(interval);

	        		SessionManager sessionManager = (SessionManager)context.getBean("sessionManager");
	        		
	        		//System.out.println("");
        		}
        	}catch(InterruptedException ie){
        		proceed=false;
        	}

    	}
    }
    
    public void setApplicationContext(ApplicationContext ctx){
    	this.context=ctx;
    }
    

}
