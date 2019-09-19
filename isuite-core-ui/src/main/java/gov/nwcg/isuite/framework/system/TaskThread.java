package gov.nwcg.isuite.framework.system;

import gov.nwcg.isuite.framework.core.task.EISuiteTask;
import gov.nwcg.isuite.framework.exceptions.TaskException;


public class TaskThread extends Thread {
	public EISuiteTaskRunner runner;
	private EISuiteTask task=null;
	
	public TaskThread(){
		
	}

	public void run() {
		try {
			synchronized(this){	
				
				//runner.taskStart(task);
				//if(null==task)
				//	System.out.println("task is null");
				
				int val=task.runScheduledTask();
				
				task.postTask();
				
				runner.taskComplete(task);
				
				// .taskThreadStatus("OK " + Thread.currentThread().getName());
				
			}	
		} catch (TaskException te) {
			runner.taskFail(task, (te.getMessage().length() > 255 ? te.getMessage().substring(0, 254) : te.getMessage() ));
		} catch (Exception e){
			runner.taskFail(task, (e.getMessage().length() > 255 ? e.getMessage().substring(0, 254) : e.getMessage() ));
		}
		
		//System.out.println("end " + Thread.currentThread().getName());
	}

	/**
	 * Sets the task.
	 *
	 * @param task 
	 *			the task to set
	 */
	public void setTask(EISuiteTask task) {
		this.task = task;
	}
}
