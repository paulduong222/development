package gov.nwcg.isuite.core.vo.dialogue;

import gov.nwcg.isuite.framework.exceptions.ErrorObject;
import gov.nwcg.isuite.framework.types.CourseOfActionTypeEnum;

import java.util.ArrayList;
import java.util.Collection;

public class CourseOfActionVo {
	
	// the pseudo-unique name for the course of action, used to recall it from a collection
	private String coaName;
	
	// type of action to take
	private CourseOfActionTypeEnum coaType=null;
	
	// message information if coaType=SHOWMESSAGE
	private MessageVo messageVo=null;
	
	// prompt information if coaType=PROMPT
	private PromptVo promptVo=null;
	
	// custom prompt information if coaType=CUSTOMPROMPT
	private CustomPromptVo customPromptVo=null;
	
	// error information if coaType=ERROR
	private Collection<ErrorObject> errorObjectVos=new ArrayList<ErrorObject>();
	
	// navigate information if coaType=NAVIGATION
	private NavigateVo navigateVo=null;
	
	// client will always set this
	private Boolean isComplete = false;

	/*
	 * set to true to notify the client that the dialogue should be terminated
	 */
	private Boolean isDialogueEnding=false;

	private Object storedObject;
	private Object storedObject1;
	private Object storedObject2;
	private Object storedObject3;
	
	public CourseOfActionVo(){
		
	}

	public CourseOfActionTypeEnum getCoaType() {
		return coaType;
	}

	public void setCoaType(CourseOfActionTypeEnum coaType) {
		this.coaType = coaType;
	}

	public MessageVo getMessageVo() {
		return messageVo;
	}

	public void setMessageVo(MessageVo messageVo) {
		this.messageVo = messageVo;
	}

	public PromptVo getPromptVo() {
		return promptVo;
	}

	public void setPromptVo(PromptVo promptVo) {
		this.promptVo = promptVo;
	}

	public Collection<ErrorObject> getErrorObjectVos() {
		return errorObjectVos;
	}

	public void setErrorObjectVos(Collection<ErrorObject> errorObjectVos) {
		this.errorObjectVos = errorObjectVos;
	}

	/**
	 * @return the isComplete
	 */
	public Boolean getIsComplete() {
		return isComplete;
	}

	/**
	 * @param isComplete the isComplete to set
	 */
	public void setIsComplete(Boolean isComplete) {
		this.isComplete = isComplete;
	}

	/**
	 * @return the coaName
	 */
	public String getCoaName() {
		return coaName;
	}

	/**
	 * @param coaName the coaName to set
	 */
	public void setCoaName(String coaName) {
		this.coaName = coaName;
	}

	/**
	 * @return the customPromptVo
	 */
	public CustomPromptVo getCustomPromptVo() {
		return customPromptVo;
	}

	/**
	 * @param customPromptVo the customPromptVo to set
	 */
	public void setCustomPromptVo(CustomPromptVo customPromptVo) {
		this.customPromptVo = customPromptVo;
	}
	
	/**
	 * @return the isDialogueEnding
	 */
	public Boolean getIsDialogueEnding() {
		return isDialogueEnding;
	}

	/**
	 * @param isDialogueEnding the isDialogueEnding to set
	 */
	public void setIsDialogueEnding(Boolean isDialogueEnding) {
		this.isDialogueEnding = isDialogueEnding;
	}

	/**
	 * @return the navigateVo
	 */
	public NavigateVo getNavigateVo() {
		return navigateVo;
	}

	/**
	 * @param navigateVo the navigateVo to set
	 */
	public void setNavigateVo(NavigateVo navigateVo) {
		this.navigateVo = navigateVo;
	}

	public Object getStoredObject() {
		return storedObject;
	}

	public void setStoredObject(Object storedObject) {
		this.storedObject = storedObject;
	}

	/**
	 * @return the storedObject1
	 */
	public Object getStoredObject1() {
		return storedObject1;
	}

	/**
	 * @param storedObject1 the storedObject1 to set
	 */
	public void setStoredObject1(Object storedObject1) {
		this.storedObject1 = storedObject1;
	}

	/**
	 * @return the storedObject2
	 */
	public Object getStoredObject2() {
		return storedObject2;
	}

	/**
	 * @param storedObject2 the storedObject2 to set
	 */
	public void setStoredObject2(Object storedObject2) {
		this.storedObject2 = storedObject2;
	}

	/**
	 * @return the storedObject3
	 */
	public Object getStoredObject3() {
		return storedObject3;
	}

	/**
	 * @param storedObject3 the storedObject3 to set
	 */
	public void setStoredObject3(Object storedObject3) {
		this.storedObject3 = storedObject3;
	}
}
