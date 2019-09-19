package gov.nwcg.isuite.core.vo;

import gov.nwcg.isuite.framework.core.vo.AbstractVo;
import gov.nwcg.isuite.framework.types.TaskFrequencyTypeEnum;

public class TaskFrequencyTypeVo extends AbstractVo {
	private String frequency;
	
	public TaskFrequencyTypeVo(){
		
	}

	public static TaskFrequencyTypeVo getInstance(TaskFrequencyTypeEnum type){
		TaskFrequencyTypeVo vo = new TaskFrequencyTypeVo();
		vo.setId(type.getId());
		vo.setFrequency(type.getDescription());
		
		return vo;
	}

	/**
	 * @return the frequency
	 */
	public String getFrequency() {
		return frequency;
	}

	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}
	
}
