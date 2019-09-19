/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.service.JmsPostingService;

import javax.jms.JMSException;

import org.springframework.jms.core.support.JmsGatewaySupport;

/**
 * Implementation of the JmsPostingService that posts messages to the
 * <code>isuite.update.StartUpdateProcess</code> queue.
 * <p>
 * These messages are consumed by .....
 * </p>
 * 
 * @author doug
 * 
 */
public class JmsPostingServiceImpl extends JmsGatewaySupport implements
		JmsPostingService<Entry> {

	/* (non-Javadoc)
	 * @see gov.nwcg.isuite.service.enterprise.jms.JmsPostingService#postMessage(java.lang.Object)
	 */
	public void postMessage(Entry item) throws JMSException {
		getJmsTemplate().convertAndSend(item);
	}
}
