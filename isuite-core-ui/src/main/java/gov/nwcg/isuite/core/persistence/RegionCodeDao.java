package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.RegionCode;
import gov.nwcg.isuite.core.vo.RegionCodeVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface RegionCodeDao extends TransactionSupport, CrudDao<RegionCode>{

   /**
    * 
    * @return {@link Collection} of {@link RegionCodeVo} objects.
    * @throws PersistenceException
    */
   public Collection<RegionCodeVo> getRegionCodes() throws PersistenceException;
}
