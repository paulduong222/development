package gov.nwcg.isuite.core.vo;

public class TrainingSystemVo {
	private String trainingMode;
	private DateTransferVo trainingDateVo=new DateTransferVo();

	/**
	 * @return the trainingDateVo
	 */
	public DateTransferVo getTrainingDateVo() {
		return trainingDateVo;
	}

	/**
	 * @param trainingDateVo the trainingDateVo to set
	 */
	public void setTrainingDateVo(DateTransferVo trainingDateVo) {
		this.trainingDateVo = trainingDateVo;
	}

	/**
	 * @return the trainingMode
	 */
	public String getTrainingMode() {
		return trainingMode;
	}

	/**
	 * @param trainingMode the trainingMode to set
	 */
	public void setTrainingMode(String trainingMode) {
		this.trainingMode = trainingMode;
	}
	
	
}
