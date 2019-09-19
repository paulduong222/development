package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.CountryCode;
import gov.nwcg.isuite.core.filter.CountryCodeFilter;
import gov.nwcg.isuite.core.vo.CountryCodeVo;
import gov.nwcg.isuite.framework.core.filter.Filter;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

/**
 * @author doug
 *
 */
public interface CountryCodeDao extends TransactionSupport, CrudDao<CountryCode>{

   @Deprecated
   public Collection<CountryCode> getAll(Filter filter) throws PersistenceException;
   
   public Collection<CountryCodeVo> getPicklist(CountryCodeFilter filter) throws PersistenceException;
}
