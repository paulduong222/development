package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.CountrySubdivision;
import gov.nwcg.isuite.core.filter.CountrySubdivisionFilter;
import gov.nwcg.isuite.core.vo.CountryCodeSubdivisionVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author doug
 */
public interface CountryCodeSubdivisionDao extends TransactionSupport, CrudDao<CountrySubdivision>{

   public Collection<CountryCodeSubdivisionVo> getPicklist(CountrySubdivisionFilter filter) throws PersistenceException;
   
   public Collection<CountryCodeSubdivisionVo> getGrid(CountrySubdivisionFilter filter) throws PersistenceException;
   
   /**
    * Tests to see if the newly entered code is unique
    * @param code the newly added code
    * @return true if unique, false if otherwise
    * @throws PersistenceException
    */
   public Boolean isCodeUnique(String code) throws PersistenceException;
}
