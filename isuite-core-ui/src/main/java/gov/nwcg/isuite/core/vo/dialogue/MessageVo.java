package gov.nwcg.isuite.core.vo.dialogue;

import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;

import gov.nwcg.isuite.framework.types.MessageTypeEnum;

public class MessageVo {
	
	protected String titleProperty="";
	
	// message property id in isuite.properties
	protected String messageProperty="";
	
	// message property parameters
	protected String[] parameters;
	
	protected MessageTypeEnum messageType=MessageTypeEnum.INFO;

	private Collection<MessageVo> additionalMessageVos = new ArrayList<MessageVo>();
	
	public MessageVo(){
		
	}

	public MessageVo(String titleProperty, String messageProperty, String[] params, MessageTypeEnum messageType) {
		super();
		this.titleProperty=titleProperty;
		this.messageProperty=messageProperty;
		this.parameters=params;
		this.messageType=messageType;
	}
	
	public String getTitleProperty() {
		return titleProperty;
	}

	public void setTitleProperty(String title) {
		this.titleProperty = title;
	}
	
	public String getMessageProperty() {
		return messageProperty;
	}

	public void setMessageProperty(String messageProperty) {
		this.messageProperty = messageProperty;
	}

	public String[] getParameters() {
		return parameters;
	}

	public void setParameters(String[] parameters) {
		this.parameters = parameters;
	}

	/**
	 * @return the messageType
	 */
	public MessageTypeEnum getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType the messageType to set
	 */
	public void setMessageType(MessageTypeEnum messageType) {
		this.messageType = messageType;
	}

	public Collection<MessageVo> getAdditionalMessageVos() {
		return additionalMessageVos;
	}

	public void setAdditionalMessageVos(Collection<MessageVo> additionalMessageVos) {
		this.additionalMessageVos = additionalMessageVos;
	}
	
	@JsonIgnore
	public Boolean isDataSavedMessage() {
		return this.messageProperty.equals("info.0030") || 
			   this.messageProperty.equals("info.0030.01") ||
			   this.messageProperty.equals("info.0030.0026") ||
			   this.messageProperty.equals("info.0030.0022");
	}
}
