package gov.nwcg.isuite.core.persistence;

import gov.nwcg.isuite.core.domain.SpecialPay;
import gov.nwcg.isuite.core.filter.SpecialPayFilter;
import gov.nwcg.isuite.core.vo.SpecialPayVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

import java.util.Collection;

public interface SpecialPayDao extends TransactionSupport, CrudDao<SpecialPay> {
   
   public Collection<SpecialPayVo> getPicklist(SpecialPayFilter filter) throws PersistenceException;
}