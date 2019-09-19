package gov.nwcg.isuite.framework.core.task;

import gov.nwcg.isuite.core.domain.TaskQueue;
import gov.nwcg.isuite.framework.exceptions.TaskException;

public interface EISuiteTask {

	public void setTaskQueue(TaskQueue taskQueue);

	public TaskQueue getTaskQueue();
	
	public int runScheduledTask() throws TaskException;
	
	public void postTask() throws TaskException;
	

}
