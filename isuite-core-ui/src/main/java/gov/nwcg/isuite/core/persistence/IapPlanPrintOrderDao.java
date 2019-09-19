package gov.nwcg.isuite.core.persistence;
  

import java.util.Collection;

import gov.nwcg.isuite.core.domain.IapPlanPrintOrder;
import gov.nwcg.isuite.core.vo.IapPlanPrintOrderVo;
import gov.nwcg.isuite.framework.core.persistence.CrudDao;
import gov.nwcg.isuite.framework.core.persistence.TransactionSupport;
import gov.nwcg.isuite.framework.exceptions.PersistenceException;

public interface IapPlanPrintOrderDao extends TransactionSupport, CrudDao<IapPlanPrintOrder>{
	
	public void merge(IapPlanPrintOrder iapPlanPrintOrder) throws PersistenceException;
	
	public IapPlanPrintOrder getByKey(Long planId, String formType, Long formId) throws PersistenceException;
	
	public Collection<IapPlanPrintOrderVo> getPicklist(Long planId) throws PersistenceException;

}
