package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.core.domain.Message;
import gov.nwcg.isuite.core.domain.impl.MessageImpl;
import gov.nwcg.isuite.framework.other.MessageFactory;
import gov.nwcg.isuite.framework.types.MessageCauseEnum;
import gov.nwcg.isuite.framework.types.MessageSeverityEnum;

/**
 * @author mpaskett
 * 
 */
public class MessageFactoryImpl implements MessageFactory {

	/*
	 * (non-Javadoc)
	 * 
	 * @see gov.nwcg.isuite.domain.admin.MessageFactory#createMessage(gov.nwcg.isuite.domain.admin.MessageCauseEnum,
	 *      java.lang.Exception)
	 */
	public Message createMessage(MessageCauseEnum cause, Exception exception) {

//		return new MessageImpl(exception.getMessage(),
//				getDetails(exception), cause, MessageSeverityEnum.CRITICAL);
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see gov.nwcg.isuite.domain.admin.MessageFactory#createMessage(gov.nwcg.isuite.domain.admin.MessageSeverityEnum, gov.nwcg.isuite.domain.admin.MessageCauseEnum, java.lang.String, java.lang.String)
	 */
	public Message createMessage(MessageSeverityEnum severity,
			MessageCauseEnum cause, String title, String description) {

//		return new MessageImpl(title, description, cause,
//				severity);
		return null;
	}

	@SuppressWarnings("unused")
	private String getDetails(Exception exception) {
		StackTraceElement[] ste = exception.getStackTrace();
		StringBuffer sb = new StringBuffer();
		StackTraceElement se;
		String newLine = System.getProperty("line.separator");
		for (int i = 0; i < ste.length; i++) {
			se = ste[i];
			sb.append(se.toString()).append(newLine);

		}

		return sb.toString();
	}
}
