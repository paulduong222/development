package gov.nwcg.isuite.framework.system;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.core.persistence.TaskQueueDao;
import gov.nwcg.isuite.core.vo.GlobalCacheVo;
import gov.nwcg.isuite.core.vo.UserSessionVo;
import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.types.SystemParameterTypeEnum;
import gov.nwcg.isuite.framework.types.TaskFrequencyTypeEnum;
import gov.nwcg.isuite.framework.types.TaskStatusTypeEnum;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.context.ApplicationContext;



public class EISuiteTaskRunner implements Runnable {
	private long interval=30000;
	private ApplicationContext context=null;
	private Boolean isProcessing=false;
	private TaskQueueDao taskQueueDao = null;
	private String runMode="";
	
	public EISuiteTaskRunner(){
	}
	
	public void startTaskRunnable() throws Exception {
		Thread t = new Thread(this);
		t.start();
	}
	
	public void setInterval(long milliseconds) {
		this.interval = milliseconds;
	}
	
    public void run() {
    	Boolean proceed=true;

       	if(runMode.equalsIgnoreCase("SITE"))
       		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("YES");

    	while(proceed)
    	{
        	try{
        		synchronized(this){
	        		Thread.sleep(interval);
	        		
	        		int runTaskCount=0;
	        		
	        		//if(null==taskQueueDao)
	        			taskQueueDao = (TaskQueueDao)context.getBean("taskQueueDao");
					
					try{
						// read task queue for tasks to process
						Collection<TaskQueue> tasks = taskQueueDao.getTaskQueues();

						if(tasks.size()>0){
							for(TaskQueue tq : tasks){
								
								// need to determine if the task should run 
								if(runTask(tq)){
									tq.getTaskQueueLogs();
									//System.out.println("Processing Task ID: " + tq.getId());
									taskQueueDao.updateStatus(tq.getId(),TaskStatusTypeEnum.PROCESSING.name());
		
									EISuiteTask task = (EISuiteTask)context.getBean(tq.getTaskType().getBeanName());

									task.setTaskQueue(tq);
									
									// create and start the task
				    	        	TaskThread taskThread = new TaskThread();
				    	        	taskThread.runner=this;
				    	        	taskThread.setTask(task);
				    	        	taskThread.start();
				    	        	runTaskCount++;
								}
							}
							if(runTaskCount > 0)
								wait();
						}else{
							//System.out.println("Nothing to process");
						}
						//System.out.println("after process");
					}catch(Exception e){
						e.printStackTrace();
					}
        		}
        	}catch(InterruptedException ie){
        		proceed=false;
        	}finally{
            	if(runMode.equalsIgnoreCase("SITE"))
            		((UserSessionVo)context.getBean("userSessionVo")).setSiteForceMaster("");
        	}

    	}
    }

    public void taskStart(EISuiteTask task){
		try{
    		if(null!=taskQueueDao){
				//task.getTaskQueue().setStatus(TaskStatusTypeEnum.PROCESSING);
				taskQueueDao.save(task.getTaskQueue());
    		}
			synchronized(this)    {
				notifyAll();   
			}
		}catch(Exception e){}
    }
    
    public void taskComplete(EISuiteTask task){
		try{
			synchronized(this)    {
        		if(null!=taskQueueDao){
					//task.getTaskQueue().setStatus(TaskStatusTypeEnum.COMPLETED);
					//taskQueueDao.save(task.getTaskQueue());
        		}
				notifyAll();   
			}
		}catch(Exception e){}
    }

    public void taskFail(EISuiteTask task, String message){
		try{
			synchronized(this)    {
        		if(null!=taskQueueDao){
					//task.getTaskQueue().setStatus(TaskStatusTypeEnum.ERROR);
					//task.getTaskQueue().setStatusMessage(message);
					taskQueueDao.save(task.getTaskQueue());
        		}
				
				notifyAll();   
			}
		}catch(Exception e){}
    }
    
    /**
     * Returns whether or not the task should run or not.
     * Need to check the task frequency, start_date, end_date.
     * 
     * @param tq
     * @return
     */
    protected Boolean runTask(TaskQueue tq) {
    	Date now = Calendar.getInstance().getTime();
    	Date nextDate = tq.getNextScheduledDate();

    	if(null==nextDate)
    		return false;
    	
    	if( (tq.getFrequency()==TaskFrequencyTypeEnum.ONETIME) && (nextDate.before(now)) ){
    		return true;
    	}else if( (tq.getFrequency()==TaskFrequencyTypeEnum.DAILY) &&  (nextDate.before(now)) ) //&& (tq. .after(now)) )
    		return true;
    	else
    		return false;
    }

    public void setApplicationContext(ApplicationContext ctx){
    	this.context=ctx;
    }
    
    public static void main(String args[]) {
    	try{
	    	EISuiteTaskRunner tr = new EISuiteTaskRunner();
	    	tr.startTaskRunnable();
	    	
    	}catch(Exception e){
    		System.out.println(e.getMessage());
    	}
    }
	/**
	 * @param runMode the runMode to set
	 */
	public void setRunMode(String runMode) {
		this.runMode = runMode;
	}


}
