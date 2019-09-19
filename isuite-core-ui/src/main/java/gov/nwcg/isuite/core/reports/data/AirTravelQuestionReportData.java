package gov.nwcg.isuite.core.reports.data;


public class AirTravelQuestionReportData {
	private Integer position;
	private String question = "";
	private Boolean questionValue = false;
	
	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}
	
	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}
	
	/**
	 * @return the question
	 */
	public String getQuestion() {
		return question;
	}
	
	/**
	 * @param question the question to set
	 */
	public void setQuestion(String question) {
		this.question = question;
	}

	/**
	 * @return the questionValue
	 */
	public Boolean getQuestionValue() {
		return questionValue;
	}

	/**
	 * @param questionValue the questionValue to set
	 */
	public void setQuestionValue(Boolean questionValue) {
		this.questionValue = questionValue;
	}

}
