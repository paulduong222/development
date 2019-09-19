package gov.nwcg.isuite.core.reports.filter;

public class TrainingSpecialistReportFilter {
	
	private Long resourceTrainingId;
	private String blankForm;
	private Long trainingSpecialistId;
	private Long trainerId;
	private String evaluationRecordNumber;
	
	public TrainingSpecialistReportFilter() {
	}

	public void setResourceTrainingId(Long resourceTrainingId) {
		this.resourceTrainingId = resourceTrainingId;
	}

	public Long getResourceTrainingId() {
		return resourceTrainingId;
	}

	public void setBlankForm(String blankForm) {
		this.blankForm = blankForm;
	}

	public String getBlankForm() {
		return blankForm;
	}

	public void setTrainingSpecialistId(Long trainingSpecialistId) {
		this.trainingSpecialistId = trainingSpecialistId;
	}

	public Long getTrainingSpecialistId() {
		return trainingSpecialistId;
	}

	public void setTrainerId(Long trainerId) {
		this.trainerId = trainerId;
	}

	public Long getTrainerId() {
		return trainerId;
	}

	public void setEvaluationRecordNumber(String evaluationRecordNumber) {
		this.evaluationRecordNumber = evaluationRecordNumber;
	}

	public String getEvaluationRecordNumber() {
		return evaluationRecordNumber;
	}

}
