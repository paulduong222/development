/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.Entry;
import gov.nwcg.isuite.core.service.JmsPostingService;
import gov.nwcg.isuite.core.service.ProcessQueueResolver;

/**
 * Implementation of a ProcessQueueResolver.
 * <p>
 * This implementation directs all posts to a single posting service.
 * </p>
 * 
 * @author doug
 */
public class ProcessQueueResolverImpl implements ProcessQueueResolver {

   private final JmsPostingService<Entry> postingService;

   public ProcessQueueResolverImpl(final JmsPostingService<Entry> postingService) {
      if ( postingService == null ) {
         throw new IllegalArgumentException("postingService can not be null");
      }
      this.postingService = postingService;

   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.update.enterprise.ProcessQueueResolver#getPostingService(gov.nwcg.isuite.domain.update.enterprise.Entry)
    */
   public JmsPostingService<Entry> getPostingService(Entry entry) {
      return postingService;
   }

}
