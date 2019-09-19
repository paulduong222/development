/**
 * 
 */
package gov.nwcg.isuite.framework.other.impl;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.domain.EntryInfo;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.SimpleMessageConverter;

/**
 * This converts Entry and EntryInfo objects into messages.
 * @author doug
 *
 */
public class EntryMessageConverter implements MessageConverter {

	private SimpleMessageConverter simpleMessageConverter;
	
	/**
	 * Constructor.
	 * @param simpleMessageConverter, can not be null
	 */
	public EntryMessageConverter(SimpleMessageConverter simpleMessageConverter) {
		if (simpleMessageConverter == null) {
			throw new IllegalArgumentException(
					"simpleMessageConverter can not be null");
		}
		this.simpleMessageConverter = simpleMessageConverter;
	}

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#fromMessage(javax.jms.Message)
	 */
	public Object fromMessage(Message message) throws JMSException,
			MessageConversionException {
		return simpleMessageConverter.fromMessage(message);
	}

	/* (non-Javadoc)
	 * @see org.springframework.jms.support.converter.MessageConverter#toMessage(java.lang.Object, javax.jms.Session)
	 */
	public Message toMessage(Object object, Session session) throws JMSException,
			MessageConversionException {
		if (! (object instanceof Entry)) {
			throw new JMSException("object must be of type Entry, was of type " + object.getClass().getName());
		}
		EntryInfo info = new EntryInfo((Entry)object);
		return simpleMessageConverter.toMessage(info, session);
	}

}
