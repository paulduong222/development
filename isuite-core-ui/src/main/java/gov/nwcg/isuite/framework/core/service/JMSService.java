package gov.nwcg.isuite.framework.core.service;

import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.JMSMessageTypeEnum;

import java.util.Collection;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

public class JMSService implements ApplicationContextAware {

	private static ApplicationContext context;
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext ctx) throws BeansException {
		this.context=ctx;
	}
	
	public static void sendSessionMessageToClient(final String sessionId) throws ServiceException {
		try {
			JmsTemplate jmsT = (JmsTemplate) context.getBean("jmsSessionTemplate");
			Destination dest = (Destination) context.getBean("session-k-topic");

			MessageCreator mc = new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = null;
					try {
						message = session.createTextMessage();
						message.setText(JMSMessageTypeEnum.SESSION_KILL.getDescription());
						message.setStringProperty("sessionId", sessionId);
					} catch (JMSException e) {
						e.printStackTrace();
					}
					return message;
				}
			};

			jmsT.send(dest, mc);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public static void sendUserMessageToClients(final JMSMessageTypeEnum messageType, final Collection<String> userIds)
			throws ServiceException {
		if (userIds != null) {
			for (String userId : userIds) {
				sendUserMessageToClient(messageType, userId);
			}
		} else {
			sendUserMessageToAllClients(messageType);
		}
	}
	
	public static void sendUserMessageToClient(final JMSMessageTypeEnum messageType, final String userId) throws ServiceException {
		try {
			JmsTemplate jmsT = (JmsTemplate) context.getBean("jmsUserIdTemplate");
			Destination dest = (Destination) context.getBean("user-id-topic");

			MessageCreator mc = new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = null;
					try {
						message = session.createTextMessage();
						message.setText(messageType.toString());
						message.setStringProperty("userId", userId);
					} catch (JMSException e) {
						e.printStackTrace();
					}
					return message;
				}
			};

			jmsT.send(dest, mc);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	public static void sendUserMessageToAllClients(final JMSMessageTypeEnum messageType) throws ServiceException {
		try {
			JmsTemplate jmsT = (JmsTemplate) context.getBean("jmsUserIdTemplate");
			Destination dest = (Destination) context.getBean("user-id-topic");

			MessageCreator mc = new MessageCreator() {
				@Override
				public Message createMessage(Session session) throws JMSException {
					TextMessage message = null;
					try {
						message = session.createTextMessage();
						message.setText(messageType.toString());
						message.setStringProperty("userId", "all");
					} catch (JMSException e) {
						e.printStackTrace();
					}
					return message;
				}
			};

			jmsT.send(dest, mc);

		} catch (Exception e) {
			throw new ServiceException(e);
		}
	}
	
	
	
}
