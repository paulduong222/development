package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.AdjustCategory;
import gov.nwcg.isuite.core.filter.AdjustCategoryFilter;
import gov.nwcg.isuite.core.vo.AdjustCategoryVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface AdjustCategoryDao extends TransactionSupport, CrudDao<AdjustCategory> {
   
   public Collection<AdjustCategoryVo> getPicklist(AdjustCategoryFilter filter) throws PersistenceException;
   
}