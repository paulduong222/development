/**
 * 
 */
package gov.nwcg.isuite.core.service.impl;

import gov.nwcg.isuite.core.domain.ExternalSystem;
import gov.nwcg.isuite.core.persistence.ExternalSystemDao;
import gov.nwcg.isuite.core.service.ExternalSystemService;
import gov.nwcg.isuite.framework.exceptions.NoSuchItemException;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;
import gov.nwcg.isuite.framework.exceptions.ServiceException;
import gov.nwcg.isuite.framework.types.DataFlowDirectionEnum;
import gov.nwcg.isuite.framework.types.ExternalSystemEnum;

/**
 * @author doug
 * 
 */
public class ExternalSystemServiceImpl implements ExternalSystemService {

   private final ExternalSystemDao dao;

   /**
    * Constructor.
    * 
    * @param dao
    */
   public ExternalSystemServiceImpl(final ExternalSystemDao dao) {
      if ( dao == null ) {
         throw new IllegalArgumentException("dao can not be null");
      }
      this.dao = dao;
   }

   /*
    * (non-Javadoc)
    * 
    * @see gov.nwcg.isuite.service.update.enterprise.ExternalSystemService#getSystem(gov.nwcg.isuite.domain.update.enterprise.ExternalSystemEnum,
    *      java.lang.String,
    *      gov.nwcg.isuite.domain.update.enterprise.DataFlowDirectionEnum)
    */
   public ExternalSystem getSystem(ExternalSystemEnum type, String name, DataFlowDirectionEnum direction)
            throws ServiceException {
      ExternalSystem result = null;

      switch ( type ) {
         case OIS:
            result = getOIS();
            break;
         case ROSS:
            result = getROSS(direction);
            break;
         case ISUITE:
            result = getISuiteSite(name, direction);
            break;
      }
      return result;
   }

   /**
    * Get the ExternalSystem data for OIS
    * 
    * @return the ExternalSystem data for OIS
    * @throws ServiceException
    */
   ExternalSystem getOIS() throws ServiceException {
      try {
         return dao.getOis();
      }
      catch ( NoSuchItemException nsie ) {
         throw new ServiceException(nsie);
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
   }

   /**
    * Get the ExternalSystem data for ROSS
    * 
    * @param direction
    *           sending or receiving
    * @return the ExternalSystem data for ROSS
    * @throws ServiceException
    */
   ExternalSystem getROSS(DataFlowDirectionEnum direction) throws ServiceException {
      try {
         return dao.getRoss(direction);
      }
      catch ( NoSuchItemException nsie ) {
         throw new ServiceException(nsie);
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
   }

   ExternalSystem getISuiteSite(String name, DataFlowDirectionEnum direction) throws ServiceException {
      ExternalSystem result = null;
      try {
         result = dao.getISuiteSite(name, direction);
         if (result == null) {
            result = dao.getDefaultISuiteSite(direction);
         }
      }
      catch ( PersistenceException pe ) {
         throw new ServiceException(pe);
      }
      return result;
   }
}
