package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.TaskTypeEnum;

public class TaskTypeVo extends AbstractVo {
	private String taskType;
	
	public TaskTypeVo(){
		
	}

	public static TaskTypeVo getInstance(TaskTypeEnum type){
		TaskTypeVo vo = new TaskTypeVo();
		vo.setId(type.getId());
		vo.setTaskType(type.getDescription());
		
		return vo;
	}
	
	/**
	 * @return the taskType
	 */
	public String getTaskType() {
		return taskType;
	}

	/**
	 * @param taskType the taskType to set
	 */
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
}
