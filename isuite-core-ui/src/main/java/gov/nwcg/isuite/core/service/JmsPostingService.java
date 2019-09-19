/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.framework.core.service.TransactionService;

import javax.jms.JMSException;

/**
 * 
 * Provides for posting of JMS messages.
 * <p>
 * Item is what gets put in the message and Info is what comes out of the
 * message.
 * </p>
 * 
 * @author doug
 * 
public interface JmsPostingService<Item, Info> {
 */
public interface JmsPostingService<Item> extends TransactionService {

	/**
	 * Post a message about the specified item to the appropriate queue.
	 * <p>
	 * converts the item into an <code>Info</code> object and places that Info
	 * in the message body.
	 * </p>
	 * 
	 * @param item
	 *            item that the message is about, can not be null
	 * @throws JMSException
	 *             if needed
	 */
	public void postMessage(Item item) throws JMSException;

}
