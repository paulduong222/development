/**
 * 
 */
package gov.nwcg.isuite.core.service;

import gov.nwcg.isuite.core.domain.Entry;

/**
 * Determines which processQ should be used based on entry.
 * @author doug
 *
 */
public interface ProcessQueueResolver {

   JmsPostingService<Entry> getPostingService(Entry entry);
}
