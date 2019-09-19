package gov.nwcg.isuite.core.vo.dialogue;

public class PromptVo extends MessageVo{
	
	public static final int _YES=0x0001;
	public static final int _NO=0x0002;
	public static final int _OK=0x0004;
	public static final int _CANCEL=0x0008;

	//the name (internal identification) for the prompt
	protected String promptName="";
	
	// Result of the Prompt
	protected Integer promptResult;
	
	// Alert.YES, Alert.NO, Alert.OK, Alert.CANCEL
	protected int promptValue;
	
	//The default prompt value
	protected Integer defaultValue;

	protected String yesLabel="Yes";
	protected String noLabel="No";
	protected String okLabel="OK";
	protected String cancelLabel="Cancel";
	protected int buttonWidth=0;
	
	public PromptVo(){
		
	}
	
	public PromptVo(String titleProperty, String actionProperty, String[] params, int promptValues ) {
		super();
		super.setTitleProperty(titleProperty);
		super.setMessageProperty(actionProperty);
		super.setParameters(params);
		promptValue = promptValues;
	}
	
	public Integer getPromptResult() {
		return promptResult;
	}

	public void setPromptResult(Integer promptResult) {
		this.promptResult = promptResult;
	}

	public int getPromptValue() {
		return promptValue;
	}

	public void setPromptValue(int promptValue) {
		this.promptValue = promptValue;
	}

	/**
	 * @return the defaultValue
	 */
	public Integer getDefaultValue() {
		return defaultValue;
	}

	/**
	 * @param defaultValue the defaultValue to set
	 */
	public void setDefaultValue(Integer defaultValue) {
		this.defaultValue = defaultValue;
	}

	/**
	 * @return the promptName
	 */
	public String getPromptName() {
		return promptName;
	}

	/**
	 * @param promptName the promptName to set
	 */
	public void setPromptName(String promptName) {
		this.promptName = promptName;
	}

	/**
	 * @return the yesLabel
	 */
	public String getYesLabel() {
		return yesLabel;
	}

	/**
	 * @param yesLabel the yesLabel to set
	 */
	public void setYesLabel(String yesLabel) {
		this.yesLabel = yesLabel;
	}

	/**
	 * @return the noLabel
	 */
	public String getNoLabel() {
		return noLabel;
	}

	/**
	 * @param noLabel the noLabel to set
	 */
	public void setNoLabel(String noLabel) {
		this.noLabel = noLabel;
	}

	/**
	 * @return the okLabel
	 */
	public String getOkLabel() {
		return okLabel;
	}

	/**
	 * @param okLabel the okLabel to set
	 */
	public void setOkLabel(String okLabel) {
		this.okLabel = okLabel;
	}

	/**
	 * @return the cancelLabel
	 */
	public String getCancelLabel() {
		return cancelLabel;
	}

	/**
	 * @param cancelLabel the cancelLabel to set
	 */
	public void setCancelLabel(String cancelLabel) {
		this.cancelLabel = cancelLabel;
	}

	/**
	 * @return the buttonWidth
	 */
	public int getButtonWidth() {
		return buttonWidth;
	}

	/**
	 * @param buttonWidth the buttonWidth to set
	 */
	public void setButtonWidth(int buttonWidth) {
		this.buttonWidth = buttonWidth;
	}
	
}
