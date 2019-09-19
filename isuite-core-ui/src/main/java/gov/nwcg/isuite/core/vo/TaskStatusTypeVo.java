package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.TaskStatusTypeEnum;

public class TaskStatusTypeVo extends AbstractVo {
	private String status;
	
	public TaskStatusTypeVo(){
		
	}

	public static TaskStatusTypeVo getInstance(TaskStatusTypeEnum type){
		TaskStatusTypeVo vo = new TaskStatusTypeVo();
		vo.setId(type.getId());
		vo.setStatus(type.getDescription());
		
		return vo;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
}
