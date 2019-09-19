package gov.nwcg.isuite.core.vo;

import java.util.Collection;

public class PropagateFieldPromptVo {
	private String fieldName;
	private String fieldValue;
	private String promptUserMessage;
	private Boolean promptUserToPropagate=false;
	private Boolean promptUserToPropagateResult=false;
	private Boolean fieldAutoPropagate=false;
	private Boolean isStrikeTeam=false;
	
	public PropagateFieldPromptVo(){
		
	}

	public static Boolean hasFieldVo(String name, Collection<PropagateFieldPromptVo> vos){
		for(PropagateFieldPromptVo vo : vos){
			if(vo.getFieldName().equals(name)){
				return true;
			}
		}
		
		return false;
	}

	public static PropagateFieldPromptVo getFieldVo(String name, Collection<PropagateFieldPromptVo> vos){
		for(PropagateFieldPromptVo vo : vos){
			if(vo.getFieldName().equals(name)){
				return vo;
			}
		}
		
		return null;
	}
	
	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the fieldValue
	 */
	public String getFieldValue() {
		return fieldValue;
	}

	/**
	 * @param fieldValue the fieldValue to set
	 */
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	/**
	 * @return the promptUserToPropagate
	 */
	public Boolean getPromptUserToPropagate() {
		return promptUserToPropagate;
	}

	/**
	 * @param promptUserToPropagate the promptUserToPropagate to set
	 */
	public void setPromptUserToPropagate(Boolean promptUserToPropagate) {
		this.promptUserToPropagate = promptUserToPropagate;
	}

	/**
	 * @return the promptUserToPropagateResult
	 */
	public Boolean getPromptUserToPropagateResult() {
		return promptUserToPropagateResult;
	}

	/**
	 * @param promptUserToPropagateResult the promptUserToPropagateResult to set
	 */
	public void setPromptUserToPropagateResult(Boolean promptUserToPropagateResult) {
		this.promptUserToPropagateResult = promptUserToPropagateResult;
	}

	/**
	 * @return the fieldAutoPropagate
	 */
	public Boolean getFieldAutoPropagate() {
		return fieldAutoPropagate;
	}

	/**
	 * @param fieldAutoPropagate the fieldAutoPropagate to set
	 */
	public void setFieldAutoPropagate(Boolean fieldAutoPropagate) {
		this.fieldAutoPropagate = fieldAutoPropagate;
	}

	/**
	 * @return the promptUserMessage
	 */
	public String getPromptUserMessage() {
		return promptUserMessage;
	}

	/**
	 * @param promptUserMessage the promptUserMessage to set
	 */
	public void setPromptUserMessage(String promptUserMessage) {
		this.promptUserMessage = promptUserMessage;
	}

	/**
	 * @return the isStrikeTeam
	 */
	public Boolean getIsStrikeTeam() {
		return isStrikeTeam;
	}

	/**
	 * @param isStrikeTeam the isStrikeTeam to set
	 */
	public void setIsStrikeTeam(Boolean isStrikeTeam) {
		this.isStrikeTeam = isStrikeTeam;
	}
	
}
