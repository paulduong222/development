package gov.nwcg.isuite.framework.core.service.impl;

import gov.nwcg.isuite.framework.core.domain.Persistable;
import gov.nwcg.isuite.framework.core.domain.impl.PersistableImpl;
import gov.nwcg.isuite.framework.core.service.PersistableService;
import gov.nwcg.isuite.framework.core.vo.PersistableVo;
import gov.nwcg.isuite.framework.exceptions.ServiceException;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.security.context.SecurityContextHolder;

/**
 * This abstract class will use reflection to transliterate between
 * vos and dos to the best of it's ability.
 *  
 * @author ncollette
 *
 * @param <T> Some persistable object
 * @param <E> Some vo that maps to a persistable object.
 */

public abstract class PersistableServiceImpl<T extends Persistable, E extends PersistableVo> implements
         PersistableService<T, E> {
   private static final Logger LOG = Logger.getLogger(PersistableServiceImpl.class);
   
   public T setAuditInfo(T theDo) throws ServiceException {
      try {
         String name = null;
         try {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
         } catch (Exception e) {
            LOG.debug("There is no SecurityContext currently defined.....Audit Info name will be UNIDENTIFIED");
         }
            
         if (SecurityContextHolder.getContext().getAuthentication() != null) {
            name = SecurityContextHolder.getContext().getAuthentication().getName();
         } else {
            name = "UNIDENTIFIED";
         }
         Date dt = new Date();
         
         if (null == theDo.getId() || theDo.getId() == 0) {
            ((PersistableImpl)theDo).setCreatedBy(name);
            ((PersistableImpl)theDo).setCreatedDate(dt);
         }
         
         ((PersistableImpl)theDo).setLastModifiedDate(dt);
         ((PersistableImpl)theDo).setLastModifiedBy(name);
         
      } catch (Exception e) {
         LOG.debug("There technically shouldn't be this error occurring, should there?", e);
      }
      return theDo;
   }
   
   
}
